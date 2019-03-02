
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
public class DataSyncBO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(DataSyncBO.class);

    /**
     * ��¼ͬ��Ӧ�ü�¼
     */
    protected static JLogger RecordLog = LoggerFactory.getLogger("DataSyncLog");

    private static DataSyncBO instance = new DataSyncBO();

    /**
     * ��ȡ��ǩ�ĸ���
     */
    private static int TAG_SIZE = Integer.parseInt(Config.getInstance()
                                                         .getModuleConfig()
                                                         .getItemValue("TAG_SIZE"));

    /**
     * ͬ����ʼʱ��
     */
    private Date startDate;

    /**
     * ͬ������ʱ��
     */
    private Date endDate;

    /**
     * cms����ͬ��ʱ�������ϼܵ����ܷ����µĻ��档 key��categoryID;
     * value��HashMap��������contentID�ļ��ϣ�ʹ��hashMap��Ҫ��Ϊ�˿��ٲ�ѯ����Ҫ��
     */
    private HashMap ttMap = new HashMap();

    /**
     * ���ݱ�ǩ�ָ��Ļ��� key��contentTag���ݱ�ǩ��value������tag�ָ����list
     */
    private HashMap tagMap = new HashMap();

    /**
     * ORA-01461 ��������Ϊ����LONG�е�LONGֵ��ֵ
     */
    private final int MAX_LENGTH = 1333;

	private TaskRunner dataSynTaskRunner;
	
	private boolean isLock = false;
	
	private DataSyncBO(){
		
	}

    /**
     * �õ�����ģʽ
     * 
     */
    public static DataSyncBO getInstance()
    {

        return instance;
    }

    /**
     * ��CMS�е�ҵ��ͬ����PAS���ݿ���
     * 
     */
    public void syncService()
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("syncService()");
        }
        String mailContent = "ͬ��ҵ��ɹ���";
        // ����DataSyncDAO�е�syncService������
        try
        {
            DataSyncDAO.getInstance().syncService();
        }
        catch (DAOException e)
        {
            logger.error(e);
            mailContent = "ͬ��ҵ��ʧ�ܣ�";
        }
        // ��ҵ��ͬ��������ʼ��������Ա
        finally
        {
            this.sendMail(mailContent, SyncDataConstant.SERVICE_TYPE);
        }
    }

    /**
     * ����ͬ��
     * 
     * @param resourceType ����ͬ��ʱ�����ϵͬ������
     * @throws Exception 
     */
    public void syncConAdd(String resourceType, String isSyn)
    {

    	
    	
    	String content=null;//��������
    	List[] mailInfo=new ArrayList[4];//ͬ�������������
        try
        {
        	if(ContentExigenceBO.getInstance().isLock())
        	{
        		logger.info("��ǰ��ִ�н��������ߣ��������ͬ����������ͣ������");
        		throw new BOException("��ǰ��ִ�н��������ߣ��������ͬ����������ͣ������");
        	}
        	
        	if(isLock){
        		logger.info("�����ظ�ִ��syncConAdd������,hehe...");
        		throw new BOException("�����ظ�ִ��syncConAdd������");
        	}
        	
        	isLock = true;
        	
        	
            startDate = new Date();
            // ��ʼ������ͼv_cm_content ����Ϊ��    ����
            DataSyncDAO.getInstance().initViewToTable();
            
            // MTKƽ̨Ӧ�üƷѵ��Ӧ��ϵģ��ͬ������ 
            //DataSyncDAO.getInstance().initMTKViewToTable();
            
            this.addSyncContentTmp(false);
            mailInfo = this.syncContent(false, resourceType, isSyn);
            
            
            // 2016-11-14 dengshaobo
            List<String> list = DataSyncDAO.getInstance().checkTSynResult();
            logger.debug("checklist================================" + list);
            DataSyncDAO.getInstance().updateTClmsContentTag(list);
            
            endDate = new Date();
            listToMail(mailInfo);
          
            
            content=this.assemblePhoneMsg("����",mailInfo);
            // ����ͬ�����
            DataSyncDAO.getInstance().insertSynResult(mailInfo);
        }
        catch(BOException e1){
            this.sendMail(e1.getMessage(), SyncDataConstant.CONTENT_TYPE);
        }
        catch (Exception e)
        {
            endDate = new Date();
            logger.error("��������ͬ������ʧ�ܡ�", e);
            // ����쳣�͵����ʼ����ͽӿڷ��������Ա
            content="������������ͬ��ʧ�ܣ�����ϵ����Ա��";
            this.sendMail(content, SyncDataConstant.CONTENT_TYPE);
        }
        finally
        {
        	this.sendPhoneMsg("��������ͬ��", content);	
        	
            // �����ǵ���ģʽ������ÿ��ͬ���󣬶����뽫�������
            ttMap = new HashMap();
            tagMap = new HashMap();
            isLock = false;
            
        }
    }

    /**
     * ȫ��ͬ��
     * 
     * @param resourceType ����ͬ��ʱ�����ϵͬ������
     * @param isSyn �Ƿ��ǽ�������Ӧ���Ƿ������ʷ��
     */
    public void syncConFull(String resourceType, String isSyn)
    {
    	String content=null;//��������
    	List[] mailInfo=new ArrayList[4];//ͬ�������������
        try
        {
        	if(ContentExigenceBO.getInstance().isLock())
        	{
        		logger.info("��ǰ��ִ�н��������ߣ��������ͬ����������ͣ������");
        		throw new BOException("��ǰ��ִ�н��������ߣ��������ͬ����������ͣ������");
        	}
        	
        	if(isLock){
        		logger.info("�����ظ�ִ��syncConFull������,hehe...");
        		throw new BOException("�����ظ�ִ��syncConFull������");
        	}
        	
        	isLock = true;
        	
        	
            startDate = new Date();
            // ��ʼ������ͼv_cm_content ����Ϊ��
            DataSyncDAO.getInstance().initViewToTable();
            
            // MTKƽ̨Ӧ�üƷѵ��Ӧ��ϵģ��ͬ������
            DataSyncDAO.getInstance().initMTKViewToTable();

            this.addSyncContentTmp(true);
            mailInfo = this.syncContent(true, resourceType, isSyn);
            endDate = new Date();
            listToMail(mailInfo);
            
            
            //��ȡ����֪ͨ��Ϣ
            content=this.assemblePhoneMsg("ȫ��",mailInfo);
        }
        catch(BOException e1){
            this.sendMail(e1.getMessage(), SyncDataConstant.CONTENT_TYPE);
        }
        
        catch (Exception e)
        {
            endDate = new Date();
            logger.error("ȫ��ͬ��ʧ�ܡ�", e);
            // ����쳣�͵����ʼ����ͽӿڷ��������Ա
            content="������������ͬ��ʧ�ܣ�����ϵ����Ա��";
            this.sendMail("����ȫ��ͬ������ʧ�ܣ�����ϵ����Ա��", SyncDataConstant.CONTENT_TYPE);
        }
        finally
        {
        	this.sendPhoneMsg("ȫ������ͬ��", content);	
        	
            // �����ǵ���ģʽ������ÿ��ͬ���󣬶����뽫�������
            ttMap = new HashMap();
            tagMap = new HashMap();
            isLock = false;
        }

    }
    /**
     * ���ݲ�����������Ͷ�Ϣ֪ͨ
     * @param msgInfo
     * @param content
     */
    public void sendPhoneMsg(String type,String content){
    	String[] phones=null;
    	String phoneArray=Config.getInstance().readConfigItem("phone");
    	if(phoneArray!=null){
    		phones=phoneArray.trim().split("\\s*,\\s*");//��ȡ����֪ͨ�绰����
    	}
    	DataSyncDAO dao=DataSyncDAO.getInstance();
    		if(phones!=null && phones.length>0){
    			for(int i=0;i<phones.length;i++){
    				try {
						dao.sendMsg(phones[i], content);//���Ͷ���
					} catch (DAOException e) {
						logger.error(type+"�����У����ֻ�"+phones[i]+"���Ͷ���ʧ�ܣ�"+e);
					}
    			}
    		}
    }
    /**
     * ƴװ����ͬ�����Զ����� �������֪ͨ����Ϣ����
     * @return
     */
    private String assemblePhoneMsg(String type,List[] msgInfo){
    	
    	StringBuffer sb=new StringBuffer();
        List updateList = msgInfo[0];// �ɹ����¡�
        List addList = msgInfo[1];// �ɹ�����
        List deleteList = msgInfo[2];// �ɹ�����
        List errorList = msgInfo[3];// ʧ��ͬ��
        PublicUtil.removeDuplicateWithOrder(errorList);// ȥ���ظ���¼ͬ��ʧ�ܵ����⡣
        int size = updateList.size() + addList.size() + deleteList.size()
                   + errorList.size();
        /*
         * sb.append("����ͬ������������ͬ������Ϊ��" + size +
         * "�����ݣ���������"+addList.size()+",������������Ϊ"+deleteList.size()+"����ͬ�����������Ϊ��" +
         * errorList.size() + SyncDataConstant.CHANGE_LINE);
         */
        sb.append("����"+type+"ͬ���ܹ�����");
        sb.append(size);
        sb.append("��Ӧ�á����гɹ�����Ӧ��");
        sb.append(addList.size());
        sb.append("����");
        sb.append("�ɹ�����Ӧ��");
        sb.append(deleteList.size());
        sb.append("����");
        sb.append("�ɹ�����Ӧ��");
        sb.append(updateList.size());
        sb.append("����");
        sb.append("ͬ��ʧ��");
        sb.append(errorList.size());
        sb.append("����");
    	return sb.toString();    	
    }
    
    /**
     * ��CMS�е�����ͬ����PAS���ݿ��У������ͽ���ʼ���
     * 
     * @param isFull �����Ƿ���ȫ��ͬ��
     * @param resourceType ����ͬ��ʱ�����ϵͬ������
     * @param isSyn �Ƿ��ǽ�������Ӧ��
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
            // ��ȡ���е�CMS����ͬ������
            tacticList = new TacticBO().queryAll();
            if (null == tacticList && logger.isDebugEnabled())
            {
                logger.debug("��ȡCMS����ͬ������Ϊ�գ���������ͬ��û�������ϼܵ������¡�");
            }
            else if (logger.isDebugEnabled())
            {
                logger.debug("CMS����ͬ������:");
                for (int i = 0; i < tacticList.size(); i++)
                {
                    logger.debug("[" + (i + 1) + " ] "
                                 + ( TacticVO ) tacticList.get(i));
                }
            }
        }
        catch (Exception e)
        {
            String result = "��ȡSMS����ͬ�������쳣����������ͬ������������ϼܵ������¡�";
            logger.error(result, e);
            throw new BOException(result, e);
        }
        // ȫ�����������ϵ��ͼ���
        if(SyncDataConstant.SYN_RESOURCE_TYPE_ALL.equals(resourceType))
        {
            logger.debug("�˴�Ϊȫ��ͬ�����������ϵ��");
            DataSyncDAO.getInstance().syncVCmDeviceResource();
        }
        // �������������ϵ��
        else if(SyncDataConstant.SYN_RESOURCE_TYPE_ADD.equals(resourceType))
        {
            logger.debug("�˴�Ϊ����ͬ�����������ϵ��");
            
            //��������ϵ����ͬ������ִ�й������Ǻǡ�����Σ�ա�
            List list = null;
            try
            {
                // ��ȡ��Ҫͬ����������Ϣ�б�
                list = DataSyncDAO.getInstance().getSyncContentTmp();
            }
            catch (DAOException e)
            {
                throw new BOException("��ȡ��ʱ�������쳣", e);
            }
            
            // �ύ�������
            TransactionDB tdb = null;
			try {
				tdb = TransactionDB.getTransactionInstance();
				DataSyncDAO.getTransactionInstance(tdb).syncVCmDeviceResourceAdd(list);
				tdb.commit();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("�������������ϵ�����",e);
				throw new BOException("ͬ�����������ϵ��ʱ�������쳣", e);
			}finally{
				if(tdb!=null){
					tdb.close();
				}
			}
        }
        // �����������ϵ
        else
        {
            logger.info("�˴�Ϊ��ͬ�����������ϵ��");
        }

        
        List list = null;
        try
        {
            // ��ȡ��Ҫͬ����������Ϣ�б�
            list = DataSyncDAO.getInstance().getSyncContentTmp();
        }
        catch (DAOException e)
        {
            throw new BOException("��ȡ��ʱ�������쳣", e);
        }
        
        
        
        
        List[] mailInfo = new List[5];
        mailInfo[0] = Collections.synchronizedList(new ArrayList());
        mailInfo[1] = Collections.synchronizedList(new ArrayList());
        mailInfo[2] = Collections.synchronizedList(new ArrayList());
        mailInfo[3] = Collections.synchronizedList(new ArrayList());
        mailInfo[4] = Collections.synchronizedList(new ArrayList());
      
        String syncSize = Config.getInstance().getModuleConfig().getItemValue("sync_size");//����ͬ��ʱ��DataSyncDAO.getInstance().prepareDate��ֹ�ڴ����������ÿ�����ͬ�����ٸ�CONTENT
        int SYNC_SIZE = 1000;
        try{
        	SYNC_SIZE = Integer.parseInt(syncSize);
        }catch(Exception e){
        	logger.error("sync_size,��������:"+syncSize,e);
        };
        if(SYNC_SIZE>1000){
        	SYNC_SIZE = 1000;
        	logger.error("sync_size,����̫���ˣ�ӦС��1000������500��1000֮�������:"+syncSize);
        }
        int fromIndex = 0;
        int toIndex = 0;
        DataSyncDAO.getInstance().clearDate();
        do{
        	
        	toIndex = fromIndex+SYNC_SIZE;
        	if(toIndex>list.size()){
        		toIndex = list.size();
        	}
        	logger.info("syncContent_hehe-> ͬ��������:"+fromIndex+" �� "+toIndex+" ����:"+(list.size()));
        	
        	List subList = list.subList(fromIndex, toIndex);
        	Set contentIdSet = new HashSet();
        	for(int i=0;i<subList.size();i++){
        		ContentTmp tmp = (ContentTmp)subList.get(i);
        		if(tmp!=null){
        			contentIdSet.add(tmp.getContentId());
        		}
        	}
	        try{
		        // ��ʼ������
		        DataSyncDAO.getInstance().prepareDate(contentIdSet);
		        // ���õ���list��Ϊ�������뷽��dealSyncContent��
		        //List[] againMailInfo = this.dealSyncContent(subList, tacticList, isSyn); //remove by aiyan
		        this.dealSyncContent(mailInfo,subList, tacticList, isSyn);
		        //mailInfo = DataSyncBO.addList(mailInfo, againMailInfo);  //remove by aiyan
	        }catch(Exception e){
	        	logger.error("ͬ���Ż��Ĳ��������д���en...",e);
	        }finally{
	        	// ��ջ��档
	            //DataSyncDAO.getInstance().clearDate();�������ջ��治�ã���֮Ϊ���¡�ADD BY AIYAN 2012-07-24
	        	DataSyncDAO.getInstance().clearContentDevicesCache();
	        }
	        
	        
	        fromIndex = toIndex;
        }while(toIndex<list.size());
        
        
        
        
        
        try{
        	//��ʼ������
	        DataSyncDAO.getInstance().prepareDate();
	        
	        // ��Ҫ�ٴ�ͬ�����Ѵ���gcontent�������� cm_content������ɾ����
	        list = this.againSynccontetTmp();// û�б�Ҫ�ٴ�ͬ�� if (list != null &
	        //List[] againMailInfo = this.dealSyncContent(list, tacticList, isSyn); //remove by aiyan
	        this.dealSyncContent(mailInfo,list, tacticList, isSyn);
	        //mailInfo = DataSyncBO.addList(mailInfo, againMailInfo);  //remove by aiyan
	        
	        // ��Ҫ�����ڲ�ѯһ�Σ���ͬ��������ûͬ�����������ݲ����
	        list = this.reverseSynccontetTmp();
	        
	        //List[] reverseMailInfo = this.dealSyncContent(list, tacticList, isSyn); //remove by aiyan
	        this.dealSyncContent(mailInfo,list, tacticList, isSyn);
	        //mailInfo = DataSyncBO.addList(mailInfo, reverseMailInfo);  //remove by aiyan
        }finally{
            // ��ջ��档
            DataSyncDAO.getInstance().clearDate();
        }

        return mailInfo;

    }

    /**
     * ����Ҫͬ���������б�������id������������ʱ����뵽���ݿ��С�
     * 
     * @param isFull �Ƿ�Ϊȫ��ͬ����trueΪȫ����falseΪ����
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
            DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);
            // ����DataSyncDAO�е�addSyncContenTmpt������
            dao.addContentTmp(sysTime, isFull);
            // ����DataSyncDAO�е�insertSystime������ϵͳʱ����뵽���ݿ���
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
     * @param isSyn �Ƿ��ǽ�������Ӧ�á��Ƿ������ʷ��
     * @return mailInfo String[] mail������Ϣ mail������Ϣ mail[0]Ϊ���µ�Ӧ����Ϣ mail[1]����Ӧ����Ϣ
     *         mail[2]����Ӧ����Ϣ mail[3]��ʾ�������Ϣ
     */
    private void dealSyncContent(List[] mailInfo,List list, List tacticList, String isSyn)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("dealSyncContent()");
        }
        // �����б�
        int size = list.size();

//        /**
//         * mail������Ϣ mail[0]Ϊ���µ�Ӧ����Ϣ mail[1]����Ӧ����Ϣ mail[2]����Ӧ����Ϣ mail[3]��ʾ�������Ϣ
//         */
//        List[] mailInfoList = new List[5];
//        mailInfoList[0] = new ArrayList();
//        mailInfoList[1] = new ArrayList();
//        mailInfoList[2] = new ArrayList();
//        mailInfoList[3] = new ArrayList();
//        mailInfoList[4] = new ArrayList();                  //remove by aiyan 2012-07-24
        String syncDataMaxNum = Config.getInstance()
        .getModuleConfig()
        .getItemValue("syncDataMaxNum");//MM����ͬ�����߳�����
        
        int maxNum = Integer.valueOf(syncDataMaxNum).intValue();
        dataSynTaskRunner = new TaskRunner(maxNum,0);
        
        List mttacticList = new TacticBO().queryMOTOAll();
        List htctacticList = new TacticBO().queryHTCAll();
        //2015-10-13 add,���㷺��������������������б�
        List channelstacticList = new TacticBO().queryChannelsCategoryAll();
        if (null == tacticList && logger.isDebugEnabled())
        {
            logger.debug("��ȡCMS����ͬ������Ϊ�գ���������ͬ��û�������ϼܵ������¡�");
        }
        for (int i = 0; i < size; i++)
        {
            // �õ�ContentTmp����
            ContentTmp tmp = ( ContentTmp ) list.get(i);
            
            	//DataSynOpration cm = new DataSynOpration(tacticList,mttacticList,tmp,mailInfoList, isSyn);
            	DataSynOpration cm = new DataSynOpration(tacticList,mttacticList,htctacticList,channelstacticList,tmp,mailInfo, isSyn);//modify by aiyan 2012-07-24
        		//�����첽����
        		ReflectedTask task = new ReflectedTask(cm, "dataSynOp", null, null);
        		//������ӵ���������
        		dataSynTaskRunner.addTask(task);
        }
        dataSynTaskRunner.waitToFinished();
        dataSynTaskRunner.stop();
        
        // ��ջ���
        DataSynOpration.cleanMap();
        
        //return mailInfoList; remove by aiyan
    }

    

    /**
     * �����ʼ�
     * 
     * @param mailContent,�ʼ�����
     */
    public void sendMail(String mailContent, String type)
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
     * ƴװ������ͬ��������ʼ�����
     * 
     * @param errorList
     * @param size
     * @return
     */
    public String assembleSyncContentMessage(List[] mailInfo)
    {

        List updateList = mailInfo[0];// �ɹ����¡�
        List addList = mailInfo[1];// �ɹ�����
        List deleteList = mailInfo[2];// �ɹ�����
        List errorList = mailInfo[3];// ʧ��ͬ��
        List deleteErrList = mailInfo[4];//Ӧ�����ߵ����账���
        PublicUtil.removeDuplicateWithOrder(errorList);// ȥ���ظ���¼ͬ��ʧ�ܵ����⡣
        int size = updateList.size() + addList.size() + deleteList.size()
                   + errorList.size();
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
        sb.append("</b>��,");
        sb.append("ͬ������Ҫ���ߵ�������ֵ��Ӧ����Ϊ<b>");
        sb.append(deleteErrList.size());
        sb.append("</b>����");

        if (size > 0)
        {
            sb.append("<h5>����ͬ������ϸ��Ϣ��</h5>");
        }
        boolean isFirst = true;
        for (int i = 0; i < addList.size(); i++)
        {
            if (isFirst)
            {
                sb.append("<p>Ӧ�����ߵ���ϢΪ��<br>");
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
                sb.append("<p>Ӧ�����ߵ���ϢΪ:<br>");
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
                sb.append("<p>Ӧ�ø��µ���ϢΪ��<br>");
                isFirst = false;
            }
            if (i >= 100)// ֻ��Ҫչʾ100�����ɣ��������ʼ�̫��
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") ������������"
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
                sb.append("<p>Ӧ��ͬ��ʧ�ܵ���ϢΪ��<br>");
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
                sb.append("<p>ͬ������Ҫ���ߵ��ǳ�����ֵ����ϢΪ��<br>");
                isFirst = false;
            }
            if (i >= 100)// ֻ��Ҫչʾ100�����ɣ��������ʼ�̫��
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") ������������"
                          + SyncDataConstant.CHANGE_LINE);
                break;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
                      + deleteErrList.get(i) + SyncDataConstant.CHANGE_LINE);
        }*/
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
        // StringBuffer offlineMail = new StringBuffer();
        // ���û���¼ܵ���������Ҫ���ʼ�
        /*
         * if (mailInfo[0].size() != 0) { // �����ߵ���Ʒ��Ϣ���뻺������
         * offlineMail.append(this.assembleGoodsOfflineMessage(mailInfo[0])); }
         */

        /*
         * // ��������ߵ���Ʒ������Ʒ�����ʼ� if (offlineMail.length() > 0) { //
         * �����ʼ����ͽӿڷ���Ʒ���ߵ������Ϣ�������Ա. this.sendMail(offlineMail.toString(),
         * SyncDataConstant.CONTENT_TYPE); }
         */

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

        /*
         * // �õ�ϵͳʱ�䣻 long sysTime = System.currentTimeMillis(); //
         * ��鵱ǰ����ͬ���Ƿ�Ϊ�״�ͬ�� boolean firstSync =
         * DataSyncDAO.getInstance().getFirstSync(sysTime); //
         * firstSyncΪfalse,��ǰ���������״�ͬ�����ݣ����ٴζ�cms��������ͬ�� if (firstSync == false) {
         */
        // ��ѯ����/��������ݣ����뵽��ʱ����
        this.againInsSyncContentTmp();

        // �˴�����õĲ�����ԭͬ���Ĳ�����ͬ
        // ����dao����getSyncContentTmp�õ�list;
        try
        {
            return DataSyncDAO.getInstance().getSyncContentTmp();
        }
        catch (DAOException e)
        {
            throw new BOException("�ٴ�ͬ������", e);
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
            DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);

            // ����DataSyncDAO�е�againInsSyncContentTmp��������ѯ��CMS������/���������ҵ����������ݣ����뵽t_synctime_tmp����
            dao.againInsSyncContentTmp();

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
            return DataSyncDAO.getInstance().getSyncContentTmp();
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
            
            DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);

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
    
    //add by aiyan ��ϣ������ͬ�����ظ�ִ�У��ż������2012-05-16
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
		//���ߵ�Ӧ�ø���
		Map<String,String> updateMaps = DataSyncDAO.getInstance().getStatsContentid("1");
		Map<String,String> addMaps = DataSyncDAO.getInstance().getStatsContentid("0");
		Map<String,String> deleteMaps = DataSyncDAO.getInstance().getStatsContentid("9");
		Map<String,String> errorMaps =  DataSyncDAO.getInstance().getStatsContentid("-2");
		logger.debug("t_a_ppms_receive_change�������������"+updateMaps.size() +"��");
		logger.debug("t_a_ppms_receive_change��������������"+addMaps.size() +"��");
		logger.debug("t_a_ppms_receive_change��������������"+deleteMaps.size() +"��");
		logger.debug("t_a_ppms_receive_change��������ʧ����"+errorMaps.size() +"��");
		//PublicUtil.removeDuplicateWithOrder(errorMaps);// ȥ���ظ���¼ͬ��ʧ�ܵ����⡣
        int size = updateMaps.size() + addMaps.size() + deleteMaps.size()
                   + errorMaps.size();
        StringBuffer sb = new StringBuffer();
        /*
         * sb.append("����ͬ������������ͬ������Ϊ��" + size +
         * "�����ݣ���������"+addList.size()+",������������Ϊ"+deleteList.size()+"����ͬ�����������Ϊ��" +
         * errorList.size() + SyncDataConstant.CHANGE_LINE);
         */
        endDate = new Date();
        sb.append("��ʼʱ�䣺");
        sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append(",����ʱ�䣺");
        sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append("��<h4>������������</h4>");
        sb.append("����ͬ���ܹ�����<b>");
        sb.append(size);
        sb.append("</b>��Ӧ�á����гɹ�����Ӧ��<b>");
        sb.append(addMaps.size());
        sb.append("</b>����");
        sb.append("�ɹ�����Ӧ��<b>");
        sb.append(deleteMaps.size());
        sb.append("</b>����");
        sb.append("�ɹ�����Ӧ��<b>");
        sb.append(updateMaps.size());
        sb.append("</b>����");
        sb.append("ͬ��ʧ��<b>");
        sb.append(errorMaps.size());
        sb.append("</b>��,");
        

        if (size > 0)
        {
            sb.append("<h5>����ͬ������ϸ��Ϣ��</h5>");
        }
        boolean isFirst = true;
        int i = 1 ;
        for (Entry<String, String> vo : addMaps.entrySet())
        {
        	
            if (isFirst)
            {
                sb.append("<p>Ӧ�����ߵ���ϢΪ��<br>");
                isFirst = false;
            }
            //addMaps.entrySet();
            if (i >= 1000)// ֻ��Ҫչʾ1000�����ɣ��������ʼ�̫��
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") ������������"
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
                sb.append("<p>Ӧ�����ߵ���ϢΪ:<br>");
                isFirst = false;
            }
            
            if (i >= 1000)// ֻ��Ҫչʾ1000�����ɣ��������ʼ�̫��
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") ������������"
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
                sb.append("<p>Ӧ�ø��µ���ϢΪ��<br>");
                isFirst = false;
            }
            
            if (i >= 1000)// ֻ��Ҫչʾ1000�����ɣ��������ʼ�̫��
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") ������������"
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
                sb.append("<p>Ӧ��ͬ��ʧ�ܵ���ϢΪ��<br>");
                isFirst = false;
            }
            if (i >= 1000)// ֻ��Ҫչʾ100�����ɣ��������ʼ�̫��
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i++) + ") ������������"
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
                sb.append("<p>ͬ������Ҫ���ߵ��ǳ�����ֵ����ϢΪ��<br>");
                isFirst = false;
            }
            if (i >= 100)// ֻ��Ҫչʾ100�����ɣ��������ʼ�̫��
            {
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") ������������"
                          + SyncDataConstant.CHANGE_LINE);
                break;
            }
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
                      + deleteErrList.get(i) + SyncDataConstant.CHANGE_LINE);
        }*/
		return sb.toString();
	}
	
}
