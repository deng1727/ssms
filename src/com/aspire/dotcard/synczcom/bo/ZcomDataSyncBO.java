/**
 * SSMS
 * com.aspire.dotcard.synczcom.bo ZcomDataSyncBO.java
 * Apr 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.synczcom.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.tactic.TacticBO;
import com.aspire.dotcard.syncData.tactic.TacticVO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.dotcard.synczcom.dao.ZcomDataSyncDAO;
import com.aspire.dotcard.synczcom.vo.ZcomContentTmp;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *
 */
public class ZcomDataSyncBO {

	
	  /**
     * ͬ����ʼʱ��
     */
    private Date startDate;
    /**
     * ͬ������ʱ��
     */
    private Date endDate;
	 /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ZcomDataSyncBO.class);
   
	 private static ZcomDataSyncBO instance = new ZcomDataSyncBO();
	 private ZcomDataSyncBO()
	    {

	    }

	    /**
	     * �õ�����ģʽ
	     * 
	     */
	    public static ZcomDataSyncBO getInstance()
	    {

	        return instance;
	    }
	
	    /**
	     * �ֶ�ͬ��
	     * @param type
	     */
	    public void syncZcomCon(String type) {
		// ��ʼ��zcomContent��ͼת��

		try {
			// ��ʼ�������ϵ��
			ZcomDataSyncDAO.getInstance().syncVCmDeviceResource();

			if (type != null && !type.equals("")) {
				if (type.equals("0")) {
					// ����ͬ��
					this.syncZcomConAdd();
				} else if (type.equals("1")) {
					// ȫ��ͬ��
					this.syncZcomConFull();
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
			logger.error("�����ֶ�ͬ������ʧ�ܡ�", e);
			// ����쳣�͵����ʼ����ͽӿڷ��������Ա
			this.sendMail("�����ֶ�ͬ��Zcom����ʧ�ܣ�����ϵ����Ա��",
					SyncDataConstant.CONTENT_TYPE);
		}

	}
	    
	    /**
		 * ȫ��ͬ��
		 * 
		 */
	    public void syncZcomConFull(){
	    	
	    	
	        try
	        {
	        	
	        	startDate=new Date();
	           
	    
	        	 // �õ�ϵͳʱ�䣻
	            long sysTime = System.currentTimeMillis();
	        	//ZcomDataSyncDAO.getInstance().updateZcomContent(sysTime,false);
	            //��ʼ��zcomContent��ͼת��
	            ZcomDataSyncDAO.getInstance().initZcomContentViewToTable();
	            //��ʼ����ʱ�� --true ȫ��ͬ��
	            ZcomDataSyncDAO.getInstance().addZcomContentTmp(sysTime,true);
	            //��ȡzcom������Ϣ
	            Hashtable parentmap = ZcomDataSyncDAO.getInstance().getZcomParentId();
	            //��ȡ��ʱ����Ϣ
	            List list = ZcomDataSyncDAO.getInstance().getZcomSyncContentTmp();
	            //��ʼ����
	            List[] mailInfo = this.dealZcomSyncContent(list,parentmap);
	            
	            //�����������¼Ҫɾ����
	            List dellist = ZcomDataSyncDAO.getInstance().getDelCmsNotExistZcomContent();
	            mailInfo[2].addAll(dellist);
	            //ɾ��cms�в����ڵ�
	            int delcount = ZcomDataSyncDAO.getInstance().delCmsNotExistZcomContent();
	            logger.error("ɾ����"+delcount+"����PPMS�ﲻ���ڵ�");
//	          ������ϵͳִ������ͬ��ʱ����뵽��t_lastsynctime_zcom
	            ZcomDataSyncDAO.getInstance().insertSysTime(sysTime);
	            endDate=new Date();           
	    		listToMail(mailInfo);
	        }
	        catch (Exception e)
	        {
	        	// endDate=new Date();
	            logger.error("����ȫ��ͬ������ʧ�ܡ�",e);
	            // ����쳣�͵����ʼ����ͽӿڷ��������Ա
	            this.sendMail("����ȫ��ͬ������ʧ�ܣ�����ϵ����Ա��", SyncDataConstant.CONTENT_TYPE);
	        }finally
	        {
	        	// �����ǵ���ģʽ������ÿ��ͬ���󣬶����뽫�������
	           // ttMap = new HashMap();
	    		//tagMap = new HashMap();
	        }
	    }
	/**
     * ����ͬ��
     *
     */
    public void syncZcomConAdd(){
    	
    	
        try
        {
        	
        	startDate=new Date();
           
    
        	 // �õ�ϵͳʱ�䣻
            long sysTime = System.currentTimeMillis();
        	//ZcomDataSyncDAO.getInstance().updateZcomContent(sysTime,false);
            //��ʼ��ZcomContent��ͼת��
            ZcomDataSyncDAO.getInstance().initZcomContentViewToTable();
            //��ʼ����ʱ�� --false ����ͬ��
            ZcomDataSyncDAO.getInstance().addZcomContentTmp(sysTime,false);
            //��ȡzcom������Ϣ
            Hashtable parentmap = ZcomDataSyncDAO.getInstance().getZcomParentId();
            //��ȡ��ʱ����Ϣ
            List list = ZcomDataSyncDAO.getInstance().getZcomSyncContentTmp();
            //��ʼ����
            List[] mailInfo = this.dealZcomSyncContent(list,parentmap);
            
            //�����������¼Ҫɾ����
            List dellist = ZcomDataSyncDAO.getInstance().getDelCmsNotExistZcomContent();
            mailInfo[2].addAll(dellist);
            //ɾ��cms�в����ڵ�
            int delcount = ZcomDataSyncDAO.getInstance().delCmsNotExistZcomContent();
            logger.error("ɾ����"+delcount+"����PPMS�ﲻ���ڵ�");
//          ������ϵͳִ������ͬ��ʱ����뵽��t_lastsynctime_zcom
            ZcomDataSyncDAO.getInstance().insertSysTime(sysTime);
            endDate=new Date();           
    		listToMail(mailInfo);
        }
        catch (Exception e)
        {
        	// endDate=new Date();
            logger.error("��������ͬ������ʧ�ܡ�",e);
            // ����쳣�͵����ʼ����ͽӿڷ��������Ա
            this.sendMail("��������ͬ������ʧ�ܣ�����ϵ����Ա��", SyncDataConstant.CONTENT_TYPE);
        }finally
        {
        	// �����ǵ���ģʽ������ÿ��ͬ���󣬶����뽫�������
           // ttMap = new HashMap();
    		//tagMap = new HashMap();
        }
    }
    
    
    
    
    /**
     * ����������ʱ���е����ݡ�
     * 
     * @param list
     * @return mailInfo String[] mail������Ϣ mail������Ϣ mail[0]Ϊ���µ�Ӧ����Ϣ mail[1]����Ӧ����Ϣ mail[2]����Ӧ����Ϣ mail[3]��ʾ�������Ϣ
     */
    private List[] dealZcomSyncContent(List list, Hashtable parentmap) throws BOException
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
        mailInfoList[0] = new ArrayList();
        mailInfoList[1] = new ArrayList();
        mailInfoList[2] = new ArrayList();
        mailInfoList[3] = new ArrayList();
        Hashtable tempdelht = new Hashtable();
        Hashtable tempaddht = new Hashtable();
        Hashtable tempupdateht = new Hashtable();
        //���ڼ�¼Ӧ�ô���Ľ��
        StringBuffer record=null;
        for (int i = 0; i < size; i++)
        {
            TransactionDB tdb = null;
            // �õ�ContentTmp����
            ZcomContentTmp tmp = ( ZcomContentTmp ) list.get(i);
            try
            {
                // �����������
                tdb = TransactionDB.getTransactionInstance();
                ZcomDataSyncDAO dao = ZcomDataSyncDAO.getTransactionInstance(tdb);             
                record=new StringBuffer(tmp.getContentId());
                record.append(" | ");
                record.append(tmp.getLupdDate());
                record.append(" | ");
               
                	int result = dao.insertIntoZcomPPs(tmp,parentmap);
                  if(result > 0){
                	  if(result == 1){
                		  //mailInfoList[1].add(tmp);//����
                		  tempaddht.put(tmp.getContentId(),tmp);
                	  }else if (result == 2){
                		  //mailInfoList[0].add(tmp);//�޸�
                		  tempupdateht.put(tmp.getContentId(),tmp);
                	  }               	  
                	  // ����DataSyncDAO��delSynccontetTmp����ɾ��t_syncContent_tmp���е���ʼ�¼
                      dao.delZcomContentTemp(tmp.getContentId());
                  }else{
                	 // mailInfoList[3].add(tmp);//�����
                	  tempdelht.put(tmp.getContentId(),tmp);
                  }
              
                // �ύ����
                tdb.commit();

            }
            catch (Exception e)
            {
                // �ع�
                tdb.rollback();
                logger.error(e);
                // ��¼��������ContentTmp����
                //mailInfoList[3].add(tmp);
                tmp.setOptype("�쳣��"+e);
                tempdelht.put(tmp.getContentId(),tmp);
                record.append('0');
            }
            finally
            {
                if (tdb != null)
                {
                    tdb.close();
                }
            }
        }
        //�ŵ�hashtable�����contentidȥ��
        for(Iterator itr = tempdelht.keySet().iterator(); itr.hasNext();){ 
			String key = (String) itr.next(); 
			mailInfoList[3].add((ZcomContentTmp) tempdelht.get(key));//�����
			} 
        //�ŵ�hashtable�����contentidȥ��
        for(Iterator itr = tempaddht.keySet().iterator(); itr.hasNext();){ 
			String key = (String) itr.next(); 
			mailInfoList[1].add((ZcomContentTmp) tempaddht.get(key));//�����
			}
        //�ŵ�hashtable�����contentidȥ��
        for(Iterator itr = tempupdateht.keySet().iterator(); itr.hasNext();){ 
			String key = (String) itr.next(); 
			mailInfoList[0].add((ZcomContentTmp) tempupdateht.get(key));//�����
			}
       
        return mailInfoList;
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

        this.sendMail(this.assembleSyncContentMessage(mailInfo),
                      SyncDataConstant.CONTENT_TYPE);
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
            subject = "ZCOM "+MailConfig.getInstance().getSyncServiceSubject();
        }
        else
        {
            subject = "ZCOM "+MailConfig.getInstance().getSyncContentSubject();
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
            logger.debug("mail subject is:" + subject);
            logger.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
}
