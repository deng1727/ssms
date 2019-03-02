/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience.task ExperienceTask.java
 * Jul 6, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.experience.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

import com.aspire.ponaadmin.web.dataexport.experience.CommonExperience;
import com.aspire.ponaadmin.web.dataexport.experience.CreateFileByExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ExperienceConfig;
import com.aspire.ponaadmin.web.dataexport.experience.ext.AllAppExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.AppExperience;

import com.aspire.ponaadmin.web.dataexport.experience.ext.AppUpdateExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.CategoryAppExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.DeviceExperience;

import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *
 */
public class ExperienceTask extends TimerTask
{

	private static final JLogger LOG = LoggerFactory.getLogger(ExperienceTask.class);

    private List sucessList = new ArrayList();

    private List failureList = new ArrayList();

    private Date startDate;
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run()
	{
		// TODO Auto-generated method stub
		 if (LOG.isDebugEnabled())
	        {
	            LOG.debug("��ʼ��������Ӫ��ƽ̨�ļ�");
	        }
	        startDate = new Date();
	        sucessList.clear();
	        failureList.clear();
		 String message = "";
	        boolean result = false;
	        try
	        {
	        	CommonExperience appexport = new CategoryAppExperience();
	        	CreateFileByExperience exporter = new CreateFileByExperience();
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
        mailTitle = "����Ӫ�������ļ����ɽ��";
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
