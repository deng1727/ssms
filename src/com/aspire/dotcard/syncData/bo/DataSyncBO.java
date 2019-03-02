
package com.aspire.dotcard.syncData.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.MapUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.tactic.TacticBO;
import com.aspire.dotcard.syncData.tactic.TacticVO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.dotcard.syncData.vo.ContentTmp;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.web.ContentExigenceBO;
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
public class DataSyncBO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(DataSyncBO.class);

    /**
     * 记录同步应用记录
     */
    protected static JLogger RecordLog = LoggerFactory.getLogger("DataSyncLog");

    private static DataSyncBO instance = new DataSyncBO();

    /**
     * 获取标签的个数
     */
    private static int TAG_SIZE = Integer.parseInt(Config.getInstance()
                                                         .getModuleConfig()
                                                         .getItemValue("TAG_SIZE"));

    /**
     * 同步开始时间
     */
    private Date startDate;

    /**
     * 同步结束时间
     */
    private Date endDate;

    /**
     * cms内容同步时，内容上架到货架分类下的缓存。 key：categoryID;
     * value：HashMap，其中是contentID的集合（使用hashMap主要是为了快速查询的需要）
     */
    private HashMap ttMap = new HashMap();

    /**
     * 内容标签分隔的缓存 key：contentTag内容标签；value：内容tag分隔后的list
     */
    private HashMap tagMap = new HashMap();

    /**
     * ORA-01461 ：仅可以为插入LONG列的LONG值赋值
     */
    private final int MAX_LENGTH = 1333;

	private TaskRunner dataSynTaskRunner;
	
	private boolean isLock = false;
	
	private DataSyncBO(){
		
	}

    /**
     * 得到单例模式
     * 
     */
    public static DataSyncBO getInstance()
    {

        return instance;
    }

    /**
     * 将CMS中的业务同步到PAS数据库中
     * 
     */
    public void syncService()
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("syncService()");
        }
        String mailContent = "同步业务成功！";
        // 调用DataSyncDAO中的syncService方法；
        try
        {
            DataSyncDAO.getInstance().syncService();
        }
        catch (DAOException e)
        {
            logger.error(e);
            mailContent = "同步业务失败！";
        }
        // 将业务同步结果发邮件给相关人员
        finally
        {
            this.sendMail(mailContent, SyncDataConstant.SERVICE_TYPE);
        }
    }

    /**
     * 增量同步
     * 
     * @param resourceType 内容同步时适配关系同步类型
     * @throws Exception 
     */
    public void syncConAdd(String resourceType, String isSyn)
    {

    	
    	
    	String content=null;//短信内容
    	List[] mailInfo=new ArrayList[4];//同步操作结果集合
        try
        {
        	if(ContentExigenceBO.getInstance().isLock())
        	{
        		logger.info("当前在执行紧急上下线，这个内容同步动作被暂停！！！");
        		throw new BOException("当前在执行紧急上下线，这个内容同步动作被暂停！！！");
        	}
        	
        	if(isLock){
        		logger.info("不能重复执行syncConAdd方法！,hehe...");
        		throw new BOException("不能重复执行syncConAdd方法！");
        	}
        	
        	isLock = true;
        	
        	
            startDate = new Date();
            // 初始化将视图v_cm_content 创建为表    保留
            DataSyncDAO.getInstance().initViewToTable();
            
            // MTK平台应用计费点对应关系模型同步过程 
            //DataSyncDAO.getInstance().initMTKViewToTable();
            
            this.addSyncContentTmp(false);
            mailInfo = this.syncContent(false, resourceType, isSyn);
            
            
            // 2016-11-14 dengshaobo
            List<String> list = DataSyncDAO.getInstance().checkTSynResult();
            logger.debug("checklist================================" + list);
            DataSyncDAO.getInstance().updateTClmsContentTag(list);
            
            endDate = new Date();
            listToMail(mailInfo);
          
            
            content=this.assemblePhoneMsg("增量",mailInfo);
            // 插入同步结果
            DataSyncDAO.getInstance().insertSynResult(mailInfo);
        }
        catch(BOException e1){
            this.sendMail(e1.getMessage(), SyncDataConstant.CONTENT_TYPE);
        }
        catch (Exception e)
        {
            endDate = new Date();
            logger.error("本次增量同步内容失败。", e);
            // 如果异常就调用邮件发送接口发给相关人员
            content="本次增量内容同步失败，请联系管理员！";
            this.sendMail(content, SyncDataConstant.CONTENT_TYPE);
        }
        finally
        {
        	this.sendPhoneMsg("增量内容同步", content);	
        	
            // 由于是单例模式，故在每次同步后，都必须将缓存清掉
            ttMap = new HashMap();
            tagMap = new HashMap();
            isLock = false;
            
        }
    }

    /**
     * 全量同步
     * 
     * @param resourceType 内容同步时适配关系同步类型
     * @param isSyn 是否是紧急上线应用是否存入历史表
     */
    public void syncConFull(String resourceType, String isSyn)
    {
    	String content=null;//短信内容
    	List[] mailInfo=new ArrayList[4];//同步操作结果集合
        try
        {
        	if(ContentExigenceBO.getInstance().isLock())
        	{
        		logger.info("当前在执行紧急上下线，这个内容同步动作被暂停！！！");
        		throw new BOException("当前在执行紧急上下线，这个内容同步动作被暂停！！！");
        	}
        	
        	if(isLock){
        		logger.info("不能重复执行syncConFull方法！,hehe...");
        		throw new BOException("不能重复执行syncConFull方法！");
        	}
        	
        	isLock = true;
        	
        	
            startDate = new Date();
            // 初始化将视图v_cm_content 创建为表
            DataSyncDAO.getInstance().initViewToTable();
            
            // MTK平台应用计费点对应关系模型同步过程
            DataSyncDAO.getInstance().initMTKViewToTable();

            this.addSyncContentTmp(true);
            mailInfo = this.syncContent(true, resourceType, isSyn);
            endDate = new Date();
            listToMail(mailInfo);
            
            
            //获取短信通知信息
            content=this.assemblePhoneMsg("全量",mailInfo);
        }
        catch(BOException e1){
            this.sendMail(e1.getMessage(), SyncDataConstant.CONTENT_TYPE);
        }
        
        catch (Exception e)
        {
            endDate = new Date();
            logger.error("全量同步失败。", e);
            // 如果异常就调用邮件发送接口发给相关人员
            content="本次增量内容同步失败，请联系管理员！";
            this.sendMail("本次全量同步内容失败，请联系管理员！", SyncDataConstant.CONTENT_TYPE);
        }
        finally
        {
        	this.sendPhoneMsg("全量内容同步", content);	
        	
            // 由于是单例模式，故在每次同步后，都必须将缓存清掉
            ttMap = new HashMap();
            tagMap = new HashMap();
            isLock = false;
        }

    }
    /**
     * 根据操作结果，发送短息通知
     * @param msgInfo
     * @param content
     */
    public void sendPhoneMsg(String type,String content){
    	String[] phones=null;
    	String phoneArray=Config.getInstance().readConfigItem("phone");
    	if(phoneArray!=null){
    		phones=phoneArray.trim().split("\\s*,\\s*");//获取短信通知电话号码
    	}
    	DataSyncDAO dao=DataSyncDAO.getInstance();
    		if(phones!=null && phones.length>0){
    			for(int i=0;i<phones.length;i++){
    				try {
						dao.sendMsg(phones[i], content);//发送短信
					} catch (DAOException e) {
						logger.error(type+"操作中，给手机"+phones[i]+"发送短信失败！"+e);
					}
    			}
    		}
    }
    /**
     * 拼装内容同步，自动更新 结果短信通知的消息内容
     * @return
     */
    private String assemblePhoneMsg(String type,List[] msgInfo){
    	
    	StringBuffer sb=new StringBuffer();
        List updateList = msgInfo[0];// 成功更新。
        List addList = msgInfo[1];// 成功上线
        List deleteList = msgInfo[2];// 成功下线
        List errorList = msgInfo[3];// 失败同步
        PublicUtil.removeDuplicateWithOrder(errorList);// 去掉重复记录同步失败的问题。
        int size = updateList.size() + addList.size() + deleteList.size()
                   + errorList.size();
        /*
         * sb.append("内容同步结束！本地同步总数为：" + size +
         * "个内容，其中上线"+addList.size()+",个，下线内容为"+deleteList.size()+"个，同步出错的内容为：" +
         * errorList.size() + SyncDataConstant.CHANGE_LINE);
         */
        sb.append("本次"+type+"同步总共处理");
        sb.append(size);
        sb.append("个应用。其中成功上线应用");
        sb.append(addList.size());
        sb.append("个，");
        sb.append("成功下线应用");
        sb.append(deleteList.size());
        sb.append("个，");
        sb.append("成功更新应用");
        sb.append(updateList.size());
        sb.append("个，");
        sb.append("同步失败");
        sb.append(errorList.size());
        sb.append("个。");
    	return sb.toString();    	
    }
    
    /**
     * 将CMS中的内容同步到PAS数据库中，并发送结果邮件。
     * 
     * @param isFull 操作是否是全量同步
     * @param resourceType 内容同步时适配关系同步类型
     * @param isSyn 是否是紧急上线应用
     */
    private List[] syncContent(boolean isFull, String resourceType, String isSyn) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("syncContent()");
        }
        List tacticList = null;
        try
        {
            // 获取所有的CMS内容同步策略
            tacticList = new TacticBO().queryAll();
            if (null == tacticList && logger.isDebugEnabled())
            {
                logger.debug("获取CMS内容同步策略为空，本次内容同步没有内容上架到货架下。");
            }
            else if (logger.isDebugEnabled())
            {
                logger.debug("CMS内容同步策略:");
                for (int i = 0; i < tacticList.size(); i++)
                {
                    logger.debug("[" + (i + 1) + " ] "
                                 + ( TacticVO ) tacticList.get(i));
                }
            }
        }
        catch (Exception e)
        {
            String result = "获取SMS内容同步策略异常，本次内容同步不会把内容上架到货架下。";
            logger.error(result, e);
            throw new BOException(result, e);
        }
        // 全量处理适配关系视图变表
        if(SyncDataConstant.SYN_RESOURCE_TYPE_ALL.equals(resourceType))
        {
            logger.debug("此处为全量同步内容适配关系！");
            DataSyncDAO.getInstance().syncVCmDeviceResource();
        }
        // 增量处理适配关系表
        else if(SyncDataConstant.SYN_RESOURCE_TYPE_ADD.equals(resourceType))
        {
            logger.debug("此处为增量同步内容适配关系！");
            
            //这个适配关系增量同步很少执行过。。呵呵。。。危险。
            List list = null;
            try
            {
                // 获取需要同步的内容信息列表
                list = DataSyncDAO.getInstance().getSyncContentTmp();
            }
            catch (DAOException e)
            {
                throw new BOException("读取临时表数据异常", e);
            }
            
            // 提交事务操作
            TransactionDB tdb = null;
			try {
				tdb = TransactionDB.getTransactionInstance();
				DataSyncDAO.getTransactionInstance(tdb).syncVCmDeviceResourceAdd(list);
				tdb.commit();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("增量处理适配关系表出错！",e);
				throw new BOException("同步内容适配关系表时表数据异常", e);
			}finally{
				if(tdb!=null){
					tdb.close();
				}
			}
        }
        // 不处理适配关系
        else
        {
            logger.info("此处为不同步内容适配关系！");
        }

        
        List list = null;
        try
        {
            // 获取需要同步的内容信息列表
            list = DataSyncDAO.getInstance().getSyncContentTmp();
        }
        catch (DAOException e)
        {
            throw new BOException("读取临时表数据异常", e);
        }
        
        
        
        
        List[] mailInfo = new List[5];
        mailInfo[0] = Collections.synchronizedList(new ArrayList());
        mailInfo[1] = Collections.synchronizedList(new ArrayList());
        mailInfo[2] = Collections.synchronizedList(new ArrayList());
        mailInfo[3] = Collections.synchronizedList(new ArrayList());
        mailInfo[4] = Collections.synchronizedList(new ArrayList());
      
        String syncSize = Config.getInstance().getModuleConfig().getItemValue("sync_size");//数据同步时候DataSyncDAO.getInstance().prepareDate防止内存溢出，加了每次最多同步多少个CONTENT
        int SYNC_SIZE = 1000;
        try{
        	SYNC_SIZE = Integer.parseInt(syncSize);
        }catch(Exception e){
        	logger.error("sync_size,配置有误:"+syncSize,e);
        };
        if(SYNC_SIZE>1000){
        	SYNC_SIZE = 1000;
        	logger.error("sync_size,配置太大了，应小于1000，建议500到1000之间的数据:"+syncSize);
        }
        int fromIndex = 0;
        int toIndex = 0;
        DataSyncDAO.getInstance().clearDate();
        do{
        	
        	toIndex = fromIndex+SYNC_SIZE;
        	if(toIndex>list.size()){
        		toIndex = list.size();
        	}
        	logger.info("syncContent_hehe-> 同步的数据:"+fromIndex+" 到 "+toIndex+" 共计:"+(list.size()));
        	
        	List subList = list.subList(fromIndex, toIndex);
        	Set contentIdSet = new HashSet();
        	for(int i=0;i<subList.size();i++){
        		ContentTmp tmp = (ContentTmp)subList.get(i);
        		if(tmp!=null){
        			contentIdSet.add(tmp.getContentId());
        		}
        	}
	        try{
		        // 初始化缓存
		        DataSyncDAO.getInstance().prepareDate(contentIdSet);
		        // 将得到的list作为参数传入方法dealSyncContent中
		        //List[] againMailInfo = this.dealSyncContent(subList, tacticList, isSyn); //remove by aiyan
		        this.dealSyncContent(mailInfo,subList, tacticList, isSyn);
		        //mailInfo = DataSyncBO.addList(mailInfo, againMailInfo);  //remove by aiyan
	        }catch(Exception e){
	        	logger.error("同步优化的步骤里面有错误！en...",e);
	        }finally{
	        	// 清空缓存。
	            //DataSyncDAO.getInstance().clearDate();这里的清空缓存不好，改之为以下。ADD BY AIYAN 2012-07-24
	        	DataSyncDAO.getInstance().clearContentDevicesCache();
	        }
	        
	        
	        fromIndex = toIndex;
        }while(toIndex<list.size());
        
        
        
        
        
        try{
        	//初始化缓存
	        DataSyncDAO.getInstance().prepareDate();
	        
	        // 需要再次同步。把存在gcontent表但不存在 cm_content的内容删除。
	        list = this.againSynccontetTmp();// 没有必要再次同步 if (list != null &
	        //List[] againMailInfo = this.dealSyncContent(list, tacticList, isSyn); //remove by aiyan
	        this.dealSyncContent(mailInfo,list, tacticList, isSyn);
	        //mailInfo = DataSyncBO.addList(mailInfo, againMailInfo);  //remove by aiyan
	        
	        // 需要反向在查询一次，把同步过程中没同步过来的内容查出。
	        list = this.reverseSynccontetTmp();
	        
	        //List[] reverseMailInfo = this.dealSyncContent(list, tacticList, isSyn); //remove by aiyan
	        this.dealSyncContent(mailInfo,list, tacticList, isSyn);
	        //mailInfo = DataSyncBO.addList(mailInfo, reverseMailInfo);  //remove by aiyan
        }finally{
            // 清空缓存。
            DataSyncDAO.getInstance().clearDate();
        }

        return mailInfo;

    }

    /**
     * 将需要同步的内容列表并将内容id和内容最后更新时间插入到数据库中。
     * 
     * @param isFull 是否为全量同步，true为全量，false为增量
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
            DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);
            // 调用DataSyncDAO中的addSyncContenTmpt方法；
            dao.addContentTmp(sysTime, isFull);
            // 调用DataSyncDAO中的insertSystime方法将系统时间插入到数据库中
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
     * @param isSyn 是否是紧急上线应用。是否加入历史表
     * @return mailInfo String[] mail发送信息 mail发送信息 mail[0]为更新的应用信息 mail[1]上线应用信息
     *         mail[2]下线应用信息 mail[3]表示出错的信息
     */
    private void dealSyncContent(List[] mailInfo,List list, List tacticList, String isSyn)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("dealSyncContent()");
        }
        // 遍历列表
        int size = list.size();

//        /**
//         * mail发送信息 mail[0]为更新的应用信息 mail[1]上线应用信息 mail[2]下线应用信息 mail[3]表示出错的信息
//         */
//        List[] mailInfoList = new List[5];
//        mailInfoList[0] = new ArrayList();
//        mailInfoList[1] = new ArrayList();
//        mailInfoList[2] = new ArrayList();
//        mailInfoList[3] = new ArrayList();
//        mailInfoList[4] = new ArrayList();                  //remove by aiyan 2012-07-24
        String syncDataMaxNum = Config.getInstance()
        .getModuleConfig()
        .getItemValue("syncDataMaxNum");//MM内容同步多线程数量
        
        int maxNum = Integer.valueOf(syncDataMaxNum).intValue();
        dataSynTaskRunner = new TaskRunner(maxNum,0);
        
        List mttacticList = new TacticBO().queryMOTOAll();
        List htctacticList = new TacticBO().queryHTCAll();
        //2015-10-13 add,触点泛化合作渠道商与根货架列表
        List channelstacticList = new TacticBO().queryChannelsCategoryAll();
        if (null == tacticList && logger.isDebugEnabled())
        {
            logger.debug("获取CMS内容同步策略为空，本次内容同步没有内容上架到货架下。");
        }
        for (int i = 0; i < size; i++)
        {
            // 得到ContentTmp对象
            ContentTmp tmp = ( ContentTmp ) list.get(i);
            
            	//DataSynOpration cm = new DataSynOpration(tacticList,mttacticList,tmp,mailInfoList, isSyn);
            	DataSynOpration cm = new DataSynOpration(tacticList,mttacticList,htctacticList,channelstacticList,tmp,mailInfo, isSyn);//modify by aiyan 2012-07-24
        		//构造异步任务
        		ReflectedTask task = new ReflectedTask(cm, "dataSynOp", null, null);
        		//将任务加到运行器中
        		dataSynTaskRunner.addTask(task);
        }
        dataSynTaskRunner.waitToFinished();
        dataSynTaskRunner.stop();
        
        // 清空缓存
        DataSynOpration.cleanMap();
        
        //return mailInfoList; remove by aiyan
    }

    

    /**
     * 发送邮件
     * 
     * @param mailContent,邮件内容
     */
    public void sendMail(String mailContent, String type)
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
            subject = MailConfig.getInstance().getSyncContentSubject();
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
    public String assembleSyncContentMessage(List[] mailInfo)
    {

        List updateList = mailInfo[0];// 成功更新。
        List addList = mailInfo[1];// 成功上线
        List deleteList = mailInfo[2];// 成功下线
        List errorList = mailInfo[3];// 失败同步
        List deleteErrList = mailInfo[4];//应该下线但不予处理的
        PublicUtil.removeDuplicateWithOrder(errorList);// 去掉重复记录同步失败的问题。
        int size = updateList.size() + addList.size() + deleteList.size()
                   + errorList.size();
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
        sb.append("</b>个,");
        sb.append("同步过程要下线但超出阀值的应用数为<b>");
        sb.append(deleteErrList.size());
        sb.append("</b>个。");

        if (size > 0)
        {
            sb.append("<h5>本次同步的详细信息：</h5>");
        }
        boolean isFirst = true;
        for (int i = 0; i < addList.size(); i++)
        {
            if (isFirst)
            {
                sb.append("<p>应用上线的信息为：<br>");
                isFirst = false;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
                      + addList.get(i) + SyncDataConstant.CHANGE_LINE);
        }

        isFirst = true;
        for (int i = 0; i < deleteList.size(); i++)
        {
            if (isFirst)
            {
                sb.append("<p>应用下线的信息为:<br>");
                isFirst = false;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
                      + deleteList.get(i) + SyncDataConstant.CHANGE_LINE);

        }
        isFirst = true;
        for (int i = 0; i < updateList.size(); i++)
        {
            if (isFirst)
            {
                sb.append("<p>应用更新的信息为：<br>");
                isFirst = false;
            }
            if (i >= 100)// 只需要展示100条即可，不至于邮件太大
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") 。。。。。。"
                          + SyncDataConstant.CHANGE_LINE);
                break;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
                      + updateList.get(i) + SyncDataConstant.CHANGE_LINE);
        }
        isFirst = true;
        for (int i = 0; i < errorList.size(); i++)
        {
            if (isFirst)
            {
                sb.append("<p>应用同步失败的信息为：<br>");
                isFirst = false;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
                      + errorList.get(i) + SyncDataConstant.CHANGE_LINE);
        }
        
        /*
        isFirst = true;
        for (int i = 0; i < deleteErrList.size(); i++)
        {
            if (isFirst)
            {
                sb.append("<p>同步过程要下线但是超出阀值的信息为：<br>");
                isFirst = false;
            }
            if (i >= 100)// 只需要展示100条即可，不至于邮件太大
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") 。。。。。。"
                          + SyncDataConstant.CHANGE_LINE);
                break;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
                      + deleteErrList.get(i) + SyncDataConstant.CHANGE_LINE);
        }*/
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
        // StringBuffer offlineMail = new StringBuffer();
        // 如果没有下架的内容则不需要发邮件
        /*
         * if (mailInfo[0].size() != 0) { // 将下线的商品信息放入缓冲区中
         * offlineMail.append(this.assembleGoodsOfflineMessage(mailInfo[0])); }
         */

        /*
         * // 如果有下线的商品则发送商品下线邮件 if (offlineMail.length() > 0) { //
         * 调用邮件发送接口发商品下线的相关信息给相关人员. this.sendMail(offlineMail.toString(),
         * SyncDataConstant.CONTENT_TYPE); }
         */

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

        /*
         * // 得到系统时间； long sysTime = System.currentTimeMillis(); //
         * 检查当前操作同步是否为首次同步 boolean firstSync =
         * DataSyncDAO.getInstance().getFirstSync(sysTime); //
         * firstSync为false,则当前操作不是首次同步数据，需再次对cms内容数据同步 if (firstSync == false) {
         */
        // 查询新增/解除的数据，插入到临时表中
        this.againInsSyncContentTmp();

        // 此处后调用的步骤与原同步的步骤相同
        // 调用dao方法getSyncContentTmp得到list;
        try
        {
            return DataSyncDAO.getInstance().getSyncContentTmp();
        }
        catch (DAOException e)
        {
            throw new BOException("再次同步出错", e);
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
            DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);

            // 调用DataSyncDAO中的againInsSyncContentTmp方法，查询出CMS中新增/解除内容与业务关联的数据，插入到t_synctime_tmp表中
            dao.againInsSyncContentTmp();

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
            return DataSyncDAO.getInstance().getSyncContentTmp();
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
            
            DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);

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
    
    //add by aiyan 不希望内容同步有重复执行，才加这个量2012-05-16
    public boolean isLock() {
		return isLock;
	}

	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}
	
	public void toMail(Date start){
		startDate = start ;
		String[] members = MailConfig.getInstance().getMailForDataToArray();
		String subject = MailConfig.getInstance().getSyncContentSubject();
		Mail.sendMail(subject, this.createMailContent(), members);
	}

	private String  createMailContent(){
		StringBuffer content = new StringBuffer();
		String stats = "";
		//下线的应用个数
		Map<String,String> updateMaps = DataSyncDAO.getInstance().getStatsContentid("1");
		Map<String,String> addMaps = DataSyncDAO.getInstance().getStatsContentid("0");
		Map<String,String> deleteMaps = DataSyncDAO.getInstance().getStatsContentid("9");
		Map<String,String> errorMaps =  DataSyncDAO.getInstance().getStatsContentid("-2");
		logger.debug("t_a_ppms_receive_change表内昨天更新了"+updateMaps.size() +"个");
		logger.debug("t_a_ppms_receive_change表内昨天上线了"+addMaps.size() +"个");
		logger.debug("t_a_ppms_receive_change表内昨天下线了"+deleteMaps.size() +"个");
		logger.debug("t_a_ppms_receive_change表内昨天失败了"+errorMaps.size() +"个");
		//PublicUtil.removeDuplicateWithOrder(errorMaps);// 去掉重复记录同步失败的问题。
        int size = updateMaps.size() + addMaps.size() + deleteMaps.size()
                   + errorMaps.size();
        StringBuffer sb = new StringBuffer();
        /*
         * sb.append("内容同步结束！本地同步总数为：" + size +
         * "个内容，其中上线"+addList.size()+",个，下线内容为"+deleteList.size()+"个，同步出错的内容为：" +
         * errorList.size() + SyncDataConstant.CHANGE_LINE);
         */
        endDate = new Date();
        sb.append("开始时间：");
        sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append(",结束时间：");
        sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append("。<h4>处理结果概述：</h4>");
        sb.append("本次同步总共处理<b>");
        sb.append(size);
        sb.append("</b>个应用。其中成功上线应用<b>");
        sb.append(addMaps.size());
        sb.append("</b>个，");
        sb.append("成功下线应用<b>");
        sb.append(deleteMaps.size());
        sb.append("</b>个，");
        sb.append("成功更新应用<b>");
        sb.append(updateMaps.size());
        sb.append("</b>个，");
        sb.append("同步失败<b>");
        sb.append(errorMaps.size());
        sb.append("</b>个,");
        

        if (size > 0)
        {
            sb.append("<h5>本次同步的详细信息：</h5>");
        }
        boolean isFirst = true;
        int i = 1 ;
        for (Entry<String, String> vo : addMaps.entrySet())
        {
        	
            if (isFirst)
            {
                sb.append("<p>应用上线的信息为：<br>");
                isFirst = false;
            }
            //addMaps.entrySet();
            if (i >= 1000)// 只需要展示1000条即可，不至于邮件太大
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") 。。。。。。"
                          + SyncDataConstant.CHANGE_LINE);
                break;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") "
                      + vo.getKey() +"&nbsp;&nbsp;&nbsp;&nbsp;"+ vo.getValue()+ SyncDataConstant.CHANGE_LINE);
        }
        
        

        isFirst = true;
        i=1;
        for (Entry<String, String> vo : deleteMaps.entrySet())
        {	
            if (isFirst)
            {
                sb.append("<p>应用下线的信息为:<br>");
                isFirst = false;
            }
            
            if (i >= 1000)// 只需要展示1000条即可，不至于邮件太大
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") 。。。。。。"
                          + SyncDataConstant.CHANGE_LINE);
                break;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") "
                      + vo.getKey() +"&nbsp;&nbsp;&nbsp;&nbsp;"+ vo.getValue()+ SyncDataConstant.CHANGE_LINE);

        }
        isFirst = true;
        i = 1;
        for (Entry<String, String> vo : updateMaps.entrySet())
        {	
        	
            if (isFirst)
            {
                sb.append("<p>应用更新的信息为：<br>");
                isFirst = false;
            }
            
            if (i >= 1000)// 只需要展示1000条即可，不至于邮件太大
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") 。。。。。。"
                          + SyncDataConstant.CHANGE_LINE);
                break;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") "
            		+ vo.getKey() +"&nbsp;&nbsp;&nbsp;&nbsp;"+ vo.getValue()+ SyncDataConstant.CHANGE_LINE);
        }
        isFirst = true;
        i=1 ;
        for (Entry<String, String> vo : errorMaps.entrySet())
        {	
            if (isFirst)
            {
                sb.append("<p>应用同步失败的信息为：<br>");
                isFirst = false;
            }
            if (i >= 1000)// 只需要展示100条即可，不至于邮件太大
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") 。。。。。。"
                          + SyncDataConstant.CHANGE_LINE);
                break;
            }
            
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") "
                      + vo.getKey() +"&nbsp;&nbsp;&nbsp;&nbsp;"+ vo.getValue()+ SyncDataConstant.CHANGE_LINE);
        }
        
        /*
        isFirst = true;
        for (int i = 0; i < deleteErrList.size(); i++)
        {
            if (isFirst)
            {
                sb.append("<p>同步过程要下线但是超出阀值的信息为：<br>");
                isFirst = false;
            }
            if (i >= 100)// 只需要展示100条即可，不至于邮件太大
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") 。。。。。。"
                          + SyncDataConstant.CHANGE_LINE);
                break;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
                      + deleteErrList.get(i) + SyncDataConstant.CHANGE_LINE);
        }*/
		return sb.toString();
	}
	
}
