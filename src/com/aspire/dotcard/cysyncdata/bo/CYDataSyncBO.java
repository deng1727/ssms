
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
 * ����ͬ��ҵ���߼��ࣨ����ҵ�����ݺ��������ݣ�
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
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(CYDataSyncBO.class);
    /**
     * ��¼ͬ��Ӧ�ü�¼
     */
    protected static JLogger RecordLog=LoggerFactory.getLogger("DataSyncLog");

    private static CYDataSyncBO instance = new CYDataSyncBO();
    
    /**
     * ͬ����ʼʱ��
     */
    private Date startDate;
    /**
     * ͬ������ʱ��
     */
    private Date endDate;

    /**
     * cms����ͬ��ʱ�������ϼܵ����ܷ����µĻ��档
     * key��categoryID; value��HashMap��������contentID�ļ��ϣ�ʹ��hashMap��Ҫ��Ϊ�˿��ٲ�ѯ����Ҫ��
     */
    private HashMap ttMap = new HashMap();
    
    /**
     * ���ݱ�ǩ�ָ��Ļ���
     * key��contentTag���ݱ�ǩ��value������tag�ָ����list
     */
    private HashMap tagMap = new HashMap();
    
    
 private List  CYtoMMcate = new ArrayList();
	private TaskRunner updateTaskRunner;
    
    //ORA-01461 ��������Ϊ����LONG�е�LONGֵ��ֵ
    private final int MAX_LENGTH = 1333;

    /**
     * �ֶ�ͬ��
     * @param type
     */
    public void syncCYCon(String type) {
	// ��ʼ��cyContent��ͼת��

	try {
		// ��ʼ�������ϵ��
		CYDataSyncDAO.getInstance().syncVCmDeviceResource();

		if (type != null && !type.equals("")) {
			if (type.equals("0")) {
				// ����ͬ��
				this.syncConAdd();
			} else if (type.equals("1")) {
				// ȫ��ͬ��
				this.syncConFull();
			} else {
				// ͬ�����Ͳ���
				logger.debug("ͬ�����Ͳ���" + type);

			}

		} else {
			// ͬ�����Ͳ���
			logger.debug("ͬ���������ݲ��Ϸ�" + type);

		}
	} catch (BOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		// endDate=new Date();
		logger.error("�����ֶ�ͬ����ҵ��������ʧ�ܡ�", e);
		// ����쳣�͵����ʼ����ͽӿڷ��������Ա
		this.sendMail("�����ֶ�ͬ����ҵ��������ʧ�ܣ�����ϵ����Ա��",
				SyncDataConstant.CONTENT_TYPE);
	}

}

 /**
     * ����ͬ��
     *
     */
    public void syncConAdd(){
        try
        {
        	startDate=new Date();
//        	��ʼ������ͼv_cm_content ����Ϊ��
        	CYDataSyncDAO.getInstance().initViewToTable();
        	//��ȡ��ҵ������������ӳ���ϵ
        	CYtoMMcate = CYDataSyncDAO.getInstance().getAllCYToMMMapping();
        	
            this.addSyncContentTmp(false);
            List mailInfo[]=this.syncContent(false);
            endDate=new Date();
            listToMail(mailInfo);
            //����ͬ�����
          //  CYDataSyncDAO.getInstance().insertSynResult(mailInfo);
        }
        catch (Exception e)
        {
        	 endDate=new Date();
            logger.error("��������ͬ������ʧ�ܡ�",e);
            // ����쳣�͵����ʼ����ͽӿڷ��������Ա
            this.sendMail("��������ͬ������ʧ�ܣ�����ϵ����Ա��", SyncDataConstant.CONTENT_TYPE);
        }finally
        {
        	// �����ǵ���ģʽ������ÿ��ͬ���󣬶����뽫�������
            ttMap = new HashMap();
    		tagMap = new HashMap();
        }
    }
    
    /**
     * ȫ��ͬ��
     *
     */
    public void syncConFull(){
        try
        {
        	startDate=new Date();
//        	��ʼ������ͼv_cm_content ����Ϊ��
        	CYDataSyncDAO.getInstance().initViewToTable();
//        	��ȡ��ҵ������������ӳ���ϵ
        	CYtoMMcate = CYDataSyncDAO.getInstance().getAllCYToMMMapping();
        	
            this.addSyncContentTmp(true);
            List mailInfo[]=this.syncContent(true);
            endDate=new Date();
    		listToMail(mailInfo);
        }
        catch (Exception e)
        {
        	endDate=new Date();
            logger.error("ȫ��ͬ��ʧ�ܡ�",e);
         // ����쳣�͵����ʼ����ͽӿڷ��������Ա
            this.sendMail("����ȫ��ͬ������ʧ�ܣ�����ϵ����Ա��", SyncDataConstant.CONTENT_TYPE);
        }finally
        {
        	// �����ǵ���ģʽ������ÿ��ͬ���󣬶����뽫�������
            ttMap = new HashMap();
    		tagMap = new HashMap();
        }
        
        
    }



    /**
     * ��CMS�е�����ͬ����PAS���ݿ��У������ͽ���ʼ���
     * 
     * @param isFull �Ƿ���ȫ��ͬ��
     */
    private List[] syncContent(boolean isFull)throws BOException
    {

        if (logger.isDebugEnabled())
		{
			logger.debug("syncContent()");
		}
        
		// ��ȡ���е�CMS����ͬ������
		List tacticList = null;
		try
		{
			//��ʼ�������ϵ��ͼ��� 
			//CYDataSyncDAO.getInstance().syncVCmDeviceResource();
			
			tacticList = new CYTacticBO().queryAll();
			if (null == tacticList && logger.isDebugEnabled())
			{
				logger.debug("��ȡCMS����ͬ������Ϊ�գ���������ͬ��û�������ϼܵ������¡�");
			}
			else if (logger.isDebugEnabled())
			{
				logger.debug("CMS����ͬ������:");
				for (int i = 0; i < tacticList.size(); i++)
				{
					logger.debug("[" + (i + 1) + " ] " + (CYTacticVO) tacticList.get(i));
				}
			}
		} catch (Exception e)
		{
			String result = "��ȡSMS����ͬ�������쳣����������ͬ������������ϼܵ������¡�";
			logger.error(result,e);
			throw new BOException(result, e);

			/*
			 * String[] mailTo = MailConfig.getInstance().getMailToArray();
			 * Mail.sendMail("��PPMS��������ͬ��",result, mailTo); return ;
			 */
		}

		// �����ڲ�����addSyncContentTmp��
		// ��ȡ����
		// this.addSyncContentTmp();
		// ����dao����getSyncContentTmp�õ�list;
		List list = new ArrayList();
		try
		{
			list = CYDataSyncDAO.getInstance().getSyncContentTmp();
		} catch (DAOException e)
		{
			throw new BOException("��ȡ��ʱ�������쳣", e);
		}
        
		// ��ʼ������
		CYDataSyncDAO.getInstance().prepareDate();
		// ���õ���list��Ϊ�������뷽��dealSyncContent��
		List[] mailInfo = this.dealSyncContent(list, tacticList);
		
		//��Ҫ�ٴ�ͬ�����Ѵ���gcontent�������� cm_content������ɾ����
		 list = this.againSynccontetTmp();//û�б�Ҫ�ٴ�ͬ�� if (list != null &
		 List[] againMailInfo = this.dealSyncContent(list,tacticList);
		 mailInfo = CYDataSyncBO.addList(mailInfo,againMailInfo); 
		 
            
        // ��Ҫ�����ڲ�ѯһ�Σ���ͬ��������ûͬ�����������ݲ����
        list = this.reverseSynccontetTmp();
//      ��ʼ������
		CYDataSyncDAO.getInstance().prepareDate();
        List[] reverseMailInfo = this.dealSyncContent(list, tacticList);
        mailInfo = CYDataSyncBO.addList(mailInfo, reverseMailInfo);

		
		// ��ջ��档
		CYDataSyncDAO.getInstance().clearDate();
		return mailInfo;
        
    }

    /**
	 * ����Ҫͬ���������б�������id������������ʱ����뵽���ݿ��С�
	 * 
	 * @param isFull
	 *            �Ƿ�Ϊȫ��ͬ����trueΪȫ����falseΪ����
	 * 
	 */
    private void addSyncContentTmp(boolean isFull) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addSyncContentTmp()");
        }        
        // �õ�ϵͳʱ�䣻
        long sysTime = System.currentTimeMillis();
        // �����������
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance();
            CYDataSyncDAO dao = CYDataSyncDAO.getTransactionInstance(tdb);
            // ����CYDataSyncDAO�е�addSyncContenTmpt������
            dao.addContentTmp(sysTime,isFull);
            // ����CYDataSyncDAO�е�insertSystime������ϵͳʱ����뵽���ݿ���
            dao.insertSysTime(sysTime);
            // �ύ�������
            tdb.commit();
        }
        catch (Exception e)
        {
            // ִ�лع�
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
     * ����������ʱ���е����ݡ�
     * 
     * @param list
     * @return mailInfo String[] mail������Ϣ mail������Ϣ mail[0]Ϊ���µ�Ӧ����Ϣ mail[1]����Ӧ����Ϣ mail[2]����Ӧ����Ϣ mail[3]��ʾ�������Ϣ
     */
    private List[] dealSyncContent(List list, List tacticList) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("dealSyncContent()");
        }
        // �����б�
        int size = list.size();

        /**
         * mail������Ϣ mail[0]Ϊ���µ�Ӧ����Ϣ mail[1]����Ӧ����Ϣ 
         * mail[2]����Ӧ����Ϣ mail[3]��ʾ�������Ϣ
         */
        List[] mailInfoList = new List[4];
        mailInfoList[0] = new Vector();
        mailInfoList[1] = new Vector();
        mailInfoList[2] = new Vector();
        mailInfoList[3] = new Vector();
        //���ڼ�¼Ӧ�ô���Ľ��
        //StringBuffer record=null;
       // CYsyncDataMaxNum
        String CYsyncDataMaxNum = Config.getInstance()
        .getModuleConfig()
        .getItemValue("CYsyncDataMaxNum");//��ҵ��������ͬ�����߳�����
        
        int maxNum = Integer.valueOf(CYsyncDataMaxNum).intValue();
        updateTaskRunner = new TaskRunner(maxNum,0);
        for (int i = 0; i < size; i++)
        {
           // TransactionDB tdb = null;
            // �õ�ContentTmp����
            ContentTmp tmp = ( ContentTmp ) list.get(i);
           
            
            CYDataSynOpration cm = new CYDataSynOpration(CYtoMMcate,tacticList,tmp,mailInfoList);
    		//�����첽����
    		ReflectedTask task = new ReflectedTask(cm, "cyDataSynOp", null, null);
    		//������ӵ���������
    		updateTaskRunner.addTask(task);
        }
        updateTaskRunner.waitToFinished();
        updateTaskRunner.stop();
        return mailInfoList;
    }

    /*******************************************/
    
    
    
    /***************************************/
    
  

    /**
	 * �����ʼ�
	 * 
	 * @param mailContent,�ʼ�����
	 */
    private void sendMail(String mailContent, String type)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("sendMail(" + mailContent + "," + type + ")");
        }
        // �õ��ʼ�����������
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
     * ƴװ������ͬ��������ʼ�����
     * 
     * @param errorList
     * @param size
     * @return
     */
    private String assembleSyncContentMessage( List[] mailInfo)
    {

    	List updateList = mailInfo[0];//�ɹ����¡�
		List addList = mailInfo[1];//�ɹ�����
		List deleteList = mailInfo[2];//�ɹ�����
		List errorList = mailInfo[3];//ʧ��ͬ��
		PublicUtil.removeDuplicateWithOrder(errorList);//ȥ���ظ���¼ͬ��ʧ�ܵ����⡣
		PublicUtil.removeDuplicateWithOrder(updateList);//ȥ���ظ���¼ͬ�����µ����⡣
		PublicUtil.removeDuplicateWithOrder(addList);//ȥ���ظ���¼ͬ�����ߵ����⡣
		PublicUtil.removeDuplicateWithOrder(deleteList);//ȥ���ظ���¼ͬ�����ߵ����⡣
		
		
		int size=updateList.size()+addList.size()+deleteList.size()+errorList.size();
		StringBuffer sb = new StringBuffer();
		/*
		 * sb.append("����ͬ������������ͬ������Ϊ��" + size +
		 * "�����ݣ���������"+addList.size()+",������������Ϊ"+deleteList.size()+"����ͬ�����������Ϊ��" +
		 * errorList.size() + SyncDataConstant.CHANGE_LINE);
		 */
		sb.append("��ʼʱ�䣺");
		sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
		sb.append(",����ʱ�䣺");
		sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
		sb.append("��<h4>������������</h4>");
		sb.append("����ͬ���ܹ�����<b>");
		sb.append(size);
		sb.append("</b>��Ӧ�á����гɹ�����Ӧ��<b>");
		sb.append(addList.size());
		sb.append("</b>����");
		sb.append("�ɹ�����Ӧ��<b>");
		sb.append(deleteList.size());
		sb.append("</b>����");
		sb.append("�ɹ�����Ӧ��<b>");
		sb.append(updateList.size());
		sb.append("</b>����");
		sb.append("ͬ��ʧ��<b>");
		sb.append(errorList.size());
		sb.append("</b>����");
		

		if(size>0)
		{
			sb.append("<h5>����ͬ������ϸ��Ϣ��</h5>");
		}
		boolean isFirst=true;
		for (int i = 0; i < addList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>Ӧ�����ߵ���ϢΪ��<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") "+addList.get(i) + SyncDataConstant.CHANGE_LINE);
		}

		isFirst = true;
		for (int i = 0; i < deleteList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>Ӧ�����ߵ���ϢΪ:<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") "+deleteList.get(i) + SyncDataConstant.CHANGE_LINE);
			
		}
		isFirst = true;
		for (int i = 0; i < updateList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>Ӧ�ø��µ���ϢΪ��<br>");
				isFirst = false;
			}
			if(i>=100)//ֻ��Ҫչʾ100�����ɣ��������ʼ�̫��
			{
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") ������������" + SyncDataConstant.CHANGE_LINE);
				break;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") "+updateList.get(i) + SyncDataConstant.CHANGE_LINE);
		}
		isFirst = true;
		for (int i = 0; i < errorList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>Ӧ��ͬ��ʧ�ܵ���ϢΪ��<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;"+(i+1)+") "+errorList.get(i) + SyncDataConstant.CHANGE_LINE);
		}
		return sb.toString();
    }
    /**
     * ����������������������ӵ�Ŀ��������
     * 
     * @param mailInfo Ŀ������
     * @param againMailInfo ��������
     * @return List[] ����Ŀ������
     * @author biran
     */
    public static List[] addList(List[] mailInfo, List[] againMailInfo)
    {

        // ��Ŀ������Ϊѭ���Ĵ���
        int len = mailInfo.length;
        // ���againMailInfo[0]���¼����ݻ�againMailInfo[1]�д������ݣ������ӵ�mailInfo
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
     * ����ͬ������Ϣ����mailInfo������mail
     * 
     * @param size �ܹ�ͬ�������ݵ�����
     * @param mailInfo ͬ�����ݵ���Ϣ���� mailInfo[0]Ϊ�¼��������ݣ�mailInfo[1]Ϊ������������
     * @author biran
     */
    private void listToMail(List[] mailInfo)
    {

        // �����¼��ʼ�����
        //StringBuffer offlineMail = new StringBuffer();
        // ���û���¼ܵ���������Ҫ���ʼ�
        /*if (mailInfo[0].size() != 0)
        {
            // �����ߵ���Ʒ��Ϣ���뻺������
            offlineMail.append(this.assembleGoodsOfflineMessage(mailInfo[0]));
        }*/

       /* // ��������ߵ���Ʒ������Ʒ�����ʼ�
        if (offlineMail.length() > 0)
        {
            // �����ʼ����ͽӿڷ���Ʒ���ߵ������Ϣ�������Ա.
            this.sendMail(offlineMail.toString(), SyncDataConstant.CONTENT_TYPE);
        }*/

        // �����ʼ����ͽ��������ͬ����Ϣ���ʼ��������Ա.
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
      
          /* // �õ�ϵͳʱ�䣻
           long sysTime = System.currentTimeMillis();

           // ��鵱ǰ����ͬ���Ƿ�Ϊ�״�ͬ��
           boolean firstSync = CYDataSyncDAO.getInstance().getFirstSync(sysTime);

           // firstSyncΪfalse,��ǰ���������״�ͬ�����ݣ����ٴζ�cms��������ͬ��
           if (firstSync == false)
           {*/
               // ��ѯ����/��������ݣ����뵽��ʱ����
               this.againInsSyncContentTmp();

               // �˴�����õĲ�����ԭͬ���Ĳ�����ͬ 
               // ����dao����getSyncContentTmp�õ�list; 
                try
				{
					return CYDataSyncDAO.getInstance().getSyncContentTmp();
				} catch (DAOException e)
				{
					throw new BOException("�ٴ�ͬ������",e);
				}
          // }
   }
   
    /**
     * ����Ҫͬ���������б���뵽t_synctime_tmp���С�
     * 
     * @author biran
     */
    private void againInsSyncContentTmp() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("againInsSyncContentTmp()");
        }
        // �����������
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance();
            CYDataSyncDAO dao = CYDataSyncDAO.getTransactionInstance(tdb);

            // ����CYDataSyncDAO�е�againInsSyncContentTmp��������ѯ��CMS������/���������ҵ����������ݣ����뵽t_synctime_tmp����
            dao.againInsSyncContentTmp();

            // �ύ�������
            tdb.commit();
        }
        catch (Exception e)
        {
            // ����쳣�͵����ʼ����ͽӿڷ��������Ա
            this.sendMail("��ҵ����ͬ������ʧ�ܣ�", SyncDataConstant.CONTENT_TYPE);
            // ִ�лع�
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
     * ��Ҫ�����ڲ�ѯһ�Σ���ͬ��������ûͬ�����������ݲ����
     * @return
     * @throws BOException
     */
    private List reverseSynccontetTmp() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("reverseSynccontetTmp()");
        }

        // �����ڲ�ѯ�����ݣ����뵽��ʱ����
        this.reverseSyncContentTmp();

        // ����dao����getSyncContentTmp�õ�list;
        try
        {
            return CYDataSyncDAO.getInstance().getSyncContentTmp();
        }
        catch (DAOException e)
        {
            throw new BOException("����ͬ������", e);
        }
    }
    
    /**
     * ����Ҫͬ���������б���뵽t_synctime_tmp���С�
     */
    private void reverseSyncContentTmp() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("reverseSyncContentTmp()");
        }
        
        // �����������
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance();
            
            CYDataSyncDAO dao = CYDataSyncDAO.getTransactionInstance(tdb);

            // �����ڲ�ѯ���ݣ����뵽t_synctime_tmp����
            dao.reverseSyncContentTmp();

            // �ύ�������
            tdb.commit();
        }
        catch (Exception e)
        {
            // ����쳣�͵����ʼ����ͽӿڷ��������Ա
            this.sendMail("ͬ������ʧ�ܣ�", SyncDataConstant.CONTENT_TYPE);
            // ִ�лع�
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
