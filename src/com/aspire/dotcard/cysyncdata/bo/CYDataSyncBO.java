
package com.aspire.dotcard.cysyncdata.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.cysyncdata.dao.CYDataSyncDAO;
import com.aspire.dotcard.cysyncdata.tactic.CYTacticBO;
import com.aspire.dotcard.cysyncdata.tactic.CYTacticVO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.dotcard.syncData.vo.ContentTmp;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * 数据同步业务逻辑类（包括业务数据和内容数据）
 * </p>
 * <p>
 * Copyright (c) 2003-2007 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CYDataSyncBO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(CYDataSyncBO.class);
    /**
     * 记录同步应用记录
     */
    protected static JLogger RecordLog=LoggerFactory.getLogger("DataSyncLog");

    private static CYDataSyncBO instance = new CYDataSyncBO();
    
    /**
     * 同步开始时间
     */
    private Date startDate;
    /**
     * 同步结束时间
     */
    private Date endDate;

    /**
     * cms内容同步时，内容上架到货架分类下的缓存。
     * key：categoryID; value：HashMap，其中是contentID的集合（使用hashMap主要是为了快速查询的需要）
     */
    private HashMap ttMap = new HashMap();
    
    /**
     * 内容标签分隔的缓存
     * key：contentTag内容标签；value：内容tag分隔后的list
     */
    private HashMap tagMap = new HashMap();
    
    
 private List  CYtoMMcate = new ArrayList();
	private TaskRunner updateTaskRunner;
    
    //ORA-01461 ：仅可以为插入LONG列的LONG值赋值
    private final int MAX_LENGTH = 1333;

    /**
     * 手动同步
     * @param type
     */
    public void syncCYCon(String type) {
	// 初始化cyContent视图转表

	try {
		// 初始化适配关系表
		CYDataSyncDAO.getInstance().syncVCmDeviceResource();

		if (type != null && !type.equals("")) {
			if (type.equals("0")) {
				// 增量同步
				this.syncConAdd();
			} else if (type.equals("1")) {
				// 全量同步
				this.syncConFull();
			} else {
				// 同步类型不对
				logger.debug("同步类型不对" + type);

			}

		} else {
			// 同步类型不对
			logger.debug("同步类型数据不合法" + type);

		}
	} catch (BOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		// endDate=new Date();
		logger.error("本次手动同步创业大赛内容失败。", e);
		// 如果异常就调用邮件发送接口发给相关人员
		this.sendMail("本次手动同步创业大赛内容失败，请联系管理员！",
				SyncDataConstant.CONTENT_TYPE);
	}

}

 /**
     * 增量同步
     *
     */
    public void syncConAdd(){
        try
        {
        	startDate=new Date();
//        	初始化将视图v_cm_content 创建为表
        	CYDataSyncDAO.getInstance().initViewToTable();
        	//获取创业大赛二级分类映射关系
        	CYtoMMcate = CYDataSyncDAO.getInstance().getAllCYToMMMapping();
        	
            this.addSyncContentTmp(false);
            List mailInfo[]=this.syncContent(false);
            endDate=new Date();
            listToMail(mailInfo);
            //插入同步结果
          //  CYDataSyncDAO.getInstance().insertSynResult(mailInfo);
        }
        catch (Exception e)
        {
        	 endDate=new Date();
            logger.error("本次增量同步内容失败。",e);
            // 如果异常就调用邮件发送接口发给相关人员
            this.sendMail("本次增量同步内容失败，请联系管理员！", SyncDataConstant.CONTENT_TYPE);
        }finally
        {
        	// 由于是单例模式，故在每次同步后，都必须将缓存清掉
            ttMap = new HashMap();
    		tagMap = new HashMap();
        }
    }
    
    /**
     * 全量同步
     *
     */
    public void syncConFull(){
        try
        {
        	startDate=new Date();
//        	初始化将视图v_cm_content 创建为表
        	CYDataSyncDAO.getInstance().initViewToTable();
//        	获取创业大赛二级分类映射关系
        	CYtoMMcate = CYDataSyncDAO.getInstance().getAllCYToMMMapping();
        	
            this.addSyncContentTmp(true);
            List mailInfo[]=this.syncContent(true);
            endDate=new Date();
    		listToMail(mailInfo);
        }
        catch (Exception e)
        {
        	endDate=new Date();
            logger.error("全量同步失败。",e);
         // 如果异常就调用邮件发送接口发给相关人员
            this.sendMail("本次全量同步内容失败，请联系管理员！", SyncDataConstant.CONTENT_TYPE);
        }finally
        {
        	// 由于是单例模式，故在每次同步后，都必须将缓存清掉
            ttMap = new HashMap();
    		tagMap = new HashMap();
        }
        
        
    }



    /**
     * 将CMS中的内容同步到PAS数据库中，并发送结果邮件。
     * 
     * @param isFull 是否是全量同步
     */
    private List[] syncContent(boolean isFull)throws BOException
    {

        if (logger.isDebugEnabled())
		{
			logger.debug("syncContent()");
		}
        
		// 获取所有的CMS内容同步策略
		List tacticList = null;
		try
		{
			//初始化适配关系视图变表 
			//CYDataSyncDAO.getInstance().syncVCmDeviceResource();
			
			tacticList = new CYTacticBO().queryAll();
			if (null == tacticList && logger.isDebugEnabled())
			{
				logger.debug("获取CMS内容同步策略为空，本次内容同步没有内容上架到货架下。");
			}
			else if (logger.isDebugEnabled())
			{
				logger.debug("CMS内容同步策略:");
				for (int i = 0; i < tacticList.size(); i++)
				{
					logger.debug("[" + (i + 1) + " ] " + (CYTacticVO) tacticList.get(i));
				}
			}
		} catch (Exception e)
		{
			String result = "获取SMS内容同步策略异常，本次内容同步不会把内容上架到货架下。";
			logger.error(result,e);
			throw new BOException(result, e);

			/*
			 * String[] mailTo = MailConfig.getInstance().getMailToArray();
			 * Mail.sendMail("从PPMS内容数据同步",result, mailTo); return ;
			 */
		}

		// 调用内部方法addSyncContentTmp；
		// 抽取出来
		// this.addSyncContentTmp();
		// 调用dao方法getSyncContentTmp得到list;
		List list = new ArrayList();
		try
		{
			list = CYDataSyncDAO.getInstance().getSyncContentTmp();
		} catch (DAOException e)
		{
			throw new BOException("读取临时表数据异常", e);
		}
        
		// 初始化缓存
		CYDataSyncDAO.getInstance().prepareDate();
		// 将得到的list作为参数传入方法dealSyncContent中
		List[] mailInfo = this.dealSyncContent(list, tacticList);
		
		//需要再次同步。把存在gcontent表但不存在 cm_content的内容删除。
		 list = this.againSynccontetTmp();//没有必要再次同步 if (list != null &
		 List[] againMailInfo = this.dealSyncContent(list,tacticList);
		 mailInfo = CYDataSyncBO.addList(mailInfo,againMailInfo); 
		 
            
        // 需要反向在查询一次，把同步过程中没同步过来的内容查出。
        list = this.reverseSynccontetTmp();
//      初始化缓存
		CYDataSyncDAO.getInstance().prepareDate();
        List[] reverseMailInfo = this.dealSyncContent(list, tacticList);
        mailInfo = CYDataSyncBO.addList(mailInfo, reverseMailInfo);

		
		// 清空缓存。
		CYDataSyncDAO.getInstance().clearDate();
		return mailInfo;
        
    }

    /**
	 * 将需要同步的内容列表并将内容id和内容最后更新时间插入到数据库中。
	 * 
	 * @param isFull
	 *            是否为全量同步，true为全量，false为增量
	 * 
	 */
    private void addSyncContentTmp(boolean isFull) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addSyncContentTmp()");
        }        
        // 得到系统时间；
        long sysTime = System.currentTimeMillis();
        // 进行事务操作
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance();
            CYDataSyncDAO dao = CYDataSyncDAO.getTransactionInstance(tdb);
            // 调用CYDataSyncDAO中的addSyncContenTmpt方法；
            dao.addContentTmp(sysTime,isFull);
            // 调用CYDataSyncDAO中的insertSystime方法将系统时间插入到数据库中
            dao.insertSysTime(sysTime);
            // 提交事务操作
            tdb.commit();
        }
        catch (Exception e)
        {
            // 执行回滚
            tdb.rollback();
            throw new BOException("db error!", e);
        }
        finally
        {
            if (tdb != null)
            {
                tdb.close();
            }
        }
    }

    /**
     * 处理将内容临时表中的数据。
     * 
     * @param list
     * @return mailInfo String[] mail发送信息 mail发送信息 mail[0]为更新的应用信息 mail[1]上线应用信息 mail[2]下线应用信息 mail[3]表示出错的信息
     */
    private List[] dealSyncContent(List list, List tacticList) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("dealSyncContent()");
        }
        // 遍历列表
        int size = list.size();

        /**
         * mail发送信息 mail[0]为更新的应用信息 mail[1]上线应用信息 
         * mail[2]下线应用信息 mail[3]表示出错的信息
         */
        List[] mailInfoList = new List[4];
        mailInfoList[0] = new Vector();
        mailInfoList[1] = new Vector();
        mailInfoList[2] = new Vector();
        mailInfoList[3] = new Vector();
        //用于记录应用处理的结果
        //StringBuffer record=null;
       // CYsyncDataMaxNum
        String CYsyncDataMaxNum = Config.getInstance()
        .getModuleConfig()
        .getItemValue("CYsyncDataMaxNum");//创业大赛内容同步多线程数量
        
        int maxNum = Integer.valueOf(CYsyncDataMaxNum).intValue();
        updateTaskRunner = new TaskRunner(maxNum,0);
        for (int i = 0; i < size; i++)
        {
           // TransactionDB tdb = null;
            // 得到ContentTmp对象
            ContentTmp tmp = ( ContentTmp ) list.get(i);
           
            
            CYDataSynOpration cm = new CYDataSynOpration(CYtoMMcate,tacticList,tmp,mailInfoList);
    		//构造异步任务
    		ReflectedTask task = new ReflectedTask(cm, "cyDataSynOp", null, null);
    		//将任务加到运行器中
    		updateTaskRunner.addTask(task);
        }
        updateTaskRunner.waitToFinished();
        updateTaskRunner.stop();
        return mailInfoList;
    }

    /*******************************************/
    
    
    
    /***************************************/
    
  

    /**
	 * 发送邮件
	 * 
	 * @param mailContent,邮件内容
	 */
    private void sendMail(String mailContent, String type)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("sendMail(" + mailContent + "," + type + ")");
        }
        // 得到邮件接收者数组
        String[] mailTo = MailConfig.getInstance().getMailToArray();
        String subject = null;
        if (SyncDataConstant.SERVICE_TYPE.equals(type))
        {
            subject = MailConfig.getInstance().getSyncServiceSubject();
        }
        else
        {
            subject = MailConfig.getInstance().getCYSyncContentSubject();
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
            logger.debug("mail subject is:" + subject);
            logger.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
    /**
     * 拼装出内容同步结果的邮件内容
     * 
     * @param errorList
     * @param size
     * @return
     */
    private String assembleSyncContentMessage( List[] mailInfo)
    {

    	List updateList = mailInfo[0];//成功更新。
		List addList = mailInfo[1];//成功上线
		List deleteList = mailInfo[2];//成功下线
		List errorList = mailInfo[3];//失败同步
		PublicUtil.removeDuplicateWithOrder(errorList);//去掉重复记录同步失败的问题。
		PublicUtil.removeDuplicateWithOrder(updateList);//去掉重复记录同步更新的问题。
		PublicUtil.removeDuplicateWithOrder(addList);//去掉重复记录同步上线的问题。
		PublicUtil.removeDuplicateWithOrder(deleteList);//去掉重复记录同步下线的问题。
		
		
		int size=updateList.size()+addList.size()+deleteList.size()+errorList.size();
		StringBuffer sb = new StringBuffer();
		/*
		 * sb.append("内容同步结束！本地同步总数为：" + size +
		 * "个内容，其中上线"+addList.size()+",个，下线内容为"+deleteList.size()+"个，同步出错的内容为：" +
		 * errorList.size() + SyncDataConstant.CHANGE_LINE);
		 */
		sb.append("开始时间：");
		sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
		sb.append(",结束时间：");
		sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
		sb.append("。<h4>处理结果概述：</h4>");
		sb.append("本次同步总共处理<b>");
		sb.append(size);
		sb.append("</b>个应用。其中成功上线应用<b>");
		sb.append(addList.size());
		sb.append("</b>个，");
		sb.append("成功下线应用<b>");
		sb.append(deleteList.size());
		sb.append("</b>个，");
		sb.append("成功更新应用<b>");
		sb.append(updateList.size());
		sb.append("</b>个，");
		sb.append("同步失败<b>");
		sb.append(errorList.size());
		sb.append("</b>个。");
		

		if(size>0)
		{
			sb.append("<h5>本次同步的详细信息：</h5>");
		}
		boolean isFirst=true;
		for (int i = 0; i < addList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>应用上线的信息为：<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") "+addList.get(i) + SyncDataConstant.CHANGE_LINE);
		}

		isFirst = true;
		for (int i = 0; i < deleteList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>应用下线的信息为:<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") "+deleteList.get(i) + SyncDataConstant.CHANGE_LINE);
			
		}
		isFirst = true;
		for (int i = 0; i < updateList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>应用更新的信息为：<br>");
				isFirst = false;
			}
			if(i>=100)//只需要展示100条即可，不至于邮件太大
			{
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") 。。。。。。" + SyncDataConstant.CHANGE_LINE);
				break;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") "+updateList.get(i) + SyncDataConstant.CHANGE_LINE);
		}
		isFirst = true;
		for (int i = 0; i < errorList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>应用同步失败的信息为：<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") "+errorList.get(i) + SyncDataConstant.CHANGE_LINE);
		}
		return sb.toString();
    }
    /**
     * 将增量数组的内容依次增加到目标数组中
     * 
     * @param mailInfo 目标数组
     * @param againMailInfo 增量数组
     * @return List[] 返回目标数组
     * @author biran
     */
    public static List[] addList(List[] mailInfo, List[] againMailInfo)
    {

        // 以目标数组为循环的次数
        int len = mailInfo.length;
        // 如果againMailInfo[0]有下架数据或againMailInfo[1]有错误数据，则增加到mailInfo
        for (int i = 0; i < len; i++)
        {
            if (againMailInfo[i].size() != 0)
            {
                mailInfo[i].addAll(( Collection ) againMailInfo[i]);
            }
        }

        return mailInfo;
    }

    /**
     * 根据同步的信息数组mailInfo，发送mail
     * 
     * @param size 总共同步的数据的总数
     * @param mailInfo 同步数据的信息数组 mailInfo[0]为下架内容数据，mailInfo[1]为错误内容数据
     * @author biran
     */
    private void listToMail(List[] mailInfo)
    {

        // 发送下架邮件内容
        //StringBuffer offlineMail = new StringBuffer();
        // 如果没有下架的内容则不需要发邮件
        /*if (mailInfo[0].size() != 0)
        {
            // 将下线的商品信息放入缓冲区中
            offlineMail.append(this.assembleGoodsOfflineMessage(mailInfo[0]));
        }*/

       /* // 如果有下线的商品则发送商品下线邮件
        if (offlineMail.length() > 0)
        {
            // 调用邮件发送接口发商品下线的相关信息给相关人员.
            this.sendMail(offlineMail.toString(), SyncDataConstant.CONTENT_TYPE);
        }*/

        // 调用邮件发送结果将本次同步信息发邮件给相关人员.
        this.sendMail(this.assembleSyncContentMessage(mailInfo),
                      SyncDataConstant.CONTENT_TYPE);
    }
    
    /*
    * @throws BOException
    */
   private List againSynccontetTmp() throws BOException
   {

       if (logger.isDebugEnabled())
       {
           logger.debug("againSynccontetTmp()");
       }
      
          /* // 得到系统时间；
           long sysTime = System.currentTimeMillis();

           // 检查当前操作同步是否为首次同步
           boolean firstSync = CYDataSyncDAO.getInstance().getFirstSync(sysTime);

           // firstSync为false,则当前操作不是首次同步数据，需再次对cms内容数据同步
           if (firstSync == false)
           {*/
               // 查询新增/解除的数据，插入到临时表中
               this.againInsSyncContentTmp();

               // 此处后调用的步骤与原同步的步骤相同 
               // 调用dao方法getSyncContentTmp得到list; 
                try
				{
					return CYDataSyncDAO.getInstance().getSyncContentTmp();
				} catch (DAOException e)
				{
					throw new BOException("再次同步出错",e);
				}
          // }
   }
   
    /**
     * 将需要同步的内容列表插入到t_synctime_tmp表中。
     * 
     * @author biran
     */
    private void againInsSyncContentTmp() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("againInsSyncContentTmp()");
        }
        // 进行事务操作
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance();
            CYDataSyncDAO dao = CYDataSyncDAO.getTransactionInstance(tdb);

            // 调用CYDataSyncDAO中的againInsSyncContentTmp方法，查询出CMS中新增/解除内容与业务关联的数据，插入到t_synctime_tmp表中
            dao.againInsSyncContentTmp();

            // 提交事务操作
            tdb.commit();
        }
        catch (Exception e)
        {
            // 如果异常就调用邮件发送接口发给相关人员
            this.sendMail("创业大赛同步内容失败！", SyncDataConstant.CONTENT_TYPE);
            // 执行回滚
            tdb.rollback();
            throw new BOException("db error!", e);
        }
        finally
        {
            if (tdb != null)
            {
                tdb.close();
            }
        }
    }

    /**
     * 需要反向在查询一次，把同步过程中没同步过来的内容查出。
     * @return
     * @throws BOException
     */
    private List reverseSynccontetTmp() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("reverseSynccontetTmp()");
        }

        // 反向在查询的数据，插入到临时表中
        this.reverseSyncContentTmp();

        // 调用dao方法getSyncContentTmp得到list;
        try
        {
            return CYDataSyncDAO.getInstance().getSyncContentTmp();
        }
        catch (DAOException e)
        {
            throw new BOException("反查同步出错", e);
        }
    }
    
    /**
     * 将需要同步的内容列表插入到t_synctime_tmp表中。
     */
    private void reverseSyncContentTmp() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("reverseSyncContentTmp()");
        }
        
        // 进行事务操作
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance();
            
            CYDataSyncDAO dao = CYDataSyncDAO.getTransactionInstance(tdb);

            // 反向在查询数据，插入到t_synctime_tmp表中
            dao.reverseSyncContentTmp();

            // 提交事务操作
            tdb.commit();
        }
        catch (Exception e)
        {
            // 如果异常就调用邮件发送接口发给相关人员
            this.sendMail("同步内容失败！", SyncDataConstant.CONTENT_TYPE);
            // 执行回滚
            tdb.rollback();
            throw new BOException("db error!", e);
        }
        finally
        {
            if (tdb != null)
            {
                tdb.close();
            }
        }
    }
}
