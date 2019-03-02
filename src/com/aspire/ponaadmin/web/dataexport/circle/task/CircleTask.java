/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience.task FetionTask.java
 * Jul 6, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.circle.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.circle.CircleBO;
import com.aspire.ponaadmin.web.dataexport.experience.ExperienceConfig;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *
 */
public class CircleTask extends TimerTask
{

	private static final JLogger LOG = LoggerFactory.getLogger(CircleTask.class);

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
	            LOG.debug("��ʼ���ɷ��ţ�139���䣬139˵��Ӫ���ļ�");
	        }
	        startDate = new Date();
	        sucessList.clear();
	        failureList.clear();
		 String message = "";
	        boolean result = false;
	        try
	        {
	        	CircleBO cb = new CircleBO();
	        	cb.fullExport();
	        }
	        catch (Exception e)
	        {

	        }
	       // sendResultMail(true, null);  
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
        mailTitle = "Ȧ��Ӫ�������ļ����ɽ��";
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
