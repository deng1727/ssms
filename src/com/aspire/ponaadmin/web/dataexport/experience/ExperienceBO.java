/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience ExperienceBO.java
 * Jul 9, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.experience;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.experience.ext.AllAppExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.AppExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.AppUpdateExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.CategoryAppExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.DeviceExperience;
import com.aspire.ponaadmin.web.dataexport.experience.task.ExperienceTask;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *�ֶ�ִ�� ����Ӫ�� ���ݵ��� ������
 */
public class ExperienceBO
{
	private static final JLogger LOG = LoggerFactory.getLogger(ExperienceTask.class);

    private List sucessList = new ArrayList();

    private List failureList = new ArrayList();

    private Date startDate;
    
    
    
    
    /**
     * 
     * 
     * �ֶ� ����Ӫ��ȫ������ ����
     * @param opType
     * 0, ȫ������
     * 1��Ӧ�÷�����Ϣ���ݵ���
     * 2�������ն�������Ϣ����
     * 3��ȫ���ն�������Ϣ����
     * 4����Ӧ�����ݵ���
     * 5������Ӧ����Ϣͬ������
     * 6��ȫ��Ӧ����Ϣͬ������
     * 7��Ӧ����Ϣ��������ͬ������
     * 
     */
	public void fullExport(String opType){
		
//		 TODO Auto-generated method stub
		 if (LOG.isDebugEnabled())
	        {
	            LOG.debug("�ֶ���ʼ��������Ӫ��ƽ̨�ļ�");
	        }
	        startDate = new Date();
	        sucessList.clear();
	        failureList.clear();
		 String message = "";
	        boolean result = false;
	        
	        
	       
	        
	        boolean cateType = false;//Ӧ�÷�����Ϣ
	        boolean addDeviceType = false;//�����ն�����
	        boolean fullDeviceType = false;//ȫ���ն�����
	        boolean appType = false;//����Ϣ
	        boolean addAllAppType = false;//����Ӧ����Ϣ
	        boolean fullAllAppType = false;//ȫ��Ӧ����Ϣ
	        
	        boolean appUpdateType = false;//Ӧ����Ϣ����
	        
	        if(opType != null && opType.equals("0")){
	        	//0, ȫ����������
	        	cateType = true;
	        	addDeviceType = true;
	        	appType = true;
	        	addAllAppType = true;
	        	appUpdateType = true;
	        }else if(opType != null && opType.equals("1")){
	         	//1��Ӧ�÷�����Ϣ���ݵ���
	        	cateType = true;
	        	
	        }else if(opType != null && opType.equals("2")){
//	        	2�������ն�������Ϣ����
	        	addDeviceType = true;
	        	
	        }else if(opType != null && opType.equals("3")){
//	        	3��ȫ���ն�������Ϣ����
	        	fullDeviceType = true;
	        	
	        }else if(opType != null && opType.equals("4")){
//	        	4����Ӧ�����ݵ���
	        	appType = true;
	        	
	        }else if(opType != null && opType.equals("5")){
//	        	5������Ӧ����Ϣͬ������
	        	addAllAppType = true;
	        	
	        }else if(opType != null && opType.equals("6")){
//	        	6��ȫ��Ӧ����Ϣͬ������
	        	fullAllAppType = true;
	        }else if(opType != null && opType.equals("7")){
//	        	7��Ӧ����Ϣ��������ͬ������
	        	appUpdateType = true;
	        	
	        }
	        try
	        {
			CommonExperience appexport = null;
			CreateFileByExperience exporter = new CreateFileByExperience();
			if (cateType)
			{
				// Ӧ�÷�����Ϣ
				appexport = new CategoryAppExperience();
				result = exporter.export(appexport);
				if (result)
				{
					message = "Ӧ�÷�����Ϣ����ͬ��  �ɹ�";
					sucessList.add(message);
				}
				else
				{
					message = "Ӧ�÷�����Ϣ����ͬ��  ʧ��";
					failureList.add(message);
				}
			}
			if (addDeviceType)
			{
				// �����ն�����
				appexport = new DeviceExperience();
				result = exporter.export(appexport);

				if (result)
				{
					message = "�����ն�����Ӧ����Ϣͬ��  �ɹ�";
					sucessList.add(message);
				}
				else
				{
					message = "�����ն�����Ӧ����Ϣͬ��  ʧ��";
					failureList.add(message);
				}
			}
			if (fullDeviceType)
			{
				// ȫ���ն�����
				appexport = new DeviceExperience();
				appexport.setConstraint("1");//1,ȫ����0������
				appexport.fileName = DataExportTools.parseFileName(ExperienceConfig.FullDeviceName);
				 if(!CommonExperience.FileWriteDIR.endsWith(File.separator)){
					 appexport.exportedFileName = CommonExperience.FileWriteDIR + File.separator +  appexport.fileName;
			        }else{
			        	appexport.exportedFileName = CommonExperience.FileWriteDIR +  appexport.fileName;
			        }
				result = exporter.export(appexport);

				if (result)
				{
					message = "ȫ���ն�����Ӧ����Ϣͬ��  �ɹ�";
					sucessList.add(message);
				}
				else
				{
					message = "ȫ���ն�����Ӧ����Ϣͬ��  ʧ��";
					failureList.add(message);
				}
			}
			if (appType)
			{
				appexport = new AppExperience();
				result = exporter.export(appexport);

				if (result)
				{
					message = "��Ӧ����Ϣͬ��  �ɹ�";
					sucessList.add(message);
				}
				else
				{
					message = "��Ӧ����Ϣͬ��  ʧ��";
					failureList.add(message);
				}
			}
			if (addAllAppType)
			{
				appexport = new AllAppExperience();
				result = exporter.export(appexport);

				if (result)
				{
					message = "����Ӧ����Ϣͬ��  �ɹ�";
					sucessList.add(message);
				}
				else
				{
					message = "����Ӧ����Ϣͬ��  ʧ��";
					failureList.add(message);
				}
			}
			if (fullAllAppType)
			{
				appexport = new AllAppExperience();
				appexport.setConstraint("1");//1,ȫ����0������
				appexport.fileName = DataExportTools.parseFileName(ExperienceConfig.APPFullName);
				if(!CommonExperience.FileWriteDIR.endsWith(File.separator)){
					 appexport.exportedFileName = CommonExperience.FileWriteDIR + File.separator +  appexport.fileName;
			        }else{
			        	appexport.exportedFileName = CommonExperience.FileWriteDIR +  appexport.fileName;
			        }
				result = exporter.export(appexport);
				
				if (result)
				{
					message = "ȫ��Ӧ����Ϣͬ��  �ɹ�";
					sucessList.add(message);
				}
				else
				{
					message = "ȫ��Ӧ����Ϣͬ��  ʧ��";
					failureList.add(message);
				}
			}
			if (appUpdateType)
			{
				appexport = new AppUpdateExperience();
				result = exporter.export(appexport);

				if (result)
				{
					message = "Ӧ����Ϣ��������ͬ��  �ɹ�";
					sucessList.add(message);
				}
				else
				{
					message = "Ӧ����Ϣ��������ͬ��  ʧ��";
					failureList.add(message);
				}
			}
		}
	        catch (Exception e)
	        {

	        }
	        sendResultMail(true, null);  
	}
	  /**
		 * ���ͽ���ʼ���
		 */
   private void sendResultMail(boolean result, String reason)
   {

       String mailTitle;
       // �����ʼ���ʾ���δ������
       Date endDate = new Date();
       StringBuffer sb = new StringBuffer();
       int totalSuccessCount = sucessList.size();
       int totalFailureCount = failureList.size();
       mailTitle = "�ֶ�����Ӫ�������ļ����ɽ��";
       if (result)
       {

           sb.append("��ʼʱ�䣺");
           sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append(",����ʱ�䣺");
           sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append("��<h4>��������</h4>");
           sb.append("<p>���гɹ�����<b>");
           sb.append(totalSuccessCount);
           sb.append("</b>���ļ���");
           sb.append("ʧ�ܵ���<b>");
           sb.append(totalFailureCount);
           sb.append("</b>���ļ���");
           if (totalSuccessCount != 0)
           {
               sb.append("<p>������ϢΪ��<p>");

               for (int i = 0; i < sucessList.size(); i++)
               {
                   sb.append(sucessList.get(i));
                   sb.append("<br>");
               }
               sb.append("<br>");
           }

           for (int i = 0; i < failureList.size(); i++)
           {
               sb.append(failureList.get(i));
               sb.append("<br>");
           }

       }
       else
       {
           sb.append("��ʼʱ�䣺");
           sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append(",����ʱ�䣺");
           sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append("��<p>ʧ��ԭ��<br>");
           sb.append(reason);
       }

       LOG.info(sb.toString());
       Mail.sendMail(mailTitle, sb.toString(), ExperienceConfig.mailTo);
   }
	
	
}
