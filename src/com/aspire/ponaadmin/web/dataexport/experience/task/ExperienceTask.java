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
	            LOG.debug("开始生成体验营销平台文件");
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
	                message = "应用分类信息数据同步  成功";
	                sucessList.add(message);
	            }
	            else
	            {
	                message = "应用分类信息数据同步  失败";
	                failureList.add(message);
	            }
	            appexport = new DeviceExperience();
	            result = exporter.export(appexport);

	            if (result)
	            {
	                message = "增量终端适配应用信息同步  成功";
	                sucessList.add(message);
	            }
	            else
	            {
	                message = "增量终端适配应用信息同步  失败";
	                failureList.add(message);
	            }
	            appexport = new AppExperience();
	            result = exporter.export(appexport);

	            if (result)
	            {
	                message = "榜单应用信息同步  成功";
	                sucessList.add(message);
	            }
	            else
	            {
	                message = "榜单应用信息同步  失败";
	                failureList.add(message);
	            }
	            
	            appexport = new AllAppExperience();
	            result = exporter.export(appexport);

	            if (result)
	            {
	                message = "增量应用信息同步  成功";
	                sucessList.add(message);
	            }
	            else
	            {
	                message = "增量应用信息同步  失败";
	                failureList.add(message);
	            }
	            appexport = new AppUpdateExperience();
	            result = exporter.export(appexport);

	            if (result)
	            {
	                message = "应用信息更新数据同步  成功";
	                sucessList.add(message);
	            }
	            else
	            {
	                message = "应用信息更新数据同步  失败";
	                failureList.add(message);
	            }
	        }
	        catch (Exception e)
	        {

	        }
	        sendResultMail(true, null);  
	}
	  /**
     * 发送结果邮件。
     */
    private void sendResultMail(boolean result, String reason)
    {

        String mailTitle;
        // 发送邮件表示本次处理结束
        Date endDate = new Date();
        StringBuffer sb = new StringBuffer();
        int totalSuccessCount = sucessList.size();
        int totalFailureCount = failureList.size();
        mailTitle = "体验营销数据文件生成结果";
        if (result)
        {

            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("。<h4>处理结果：</h4>");
            sb.append("<p>其中成功导出<b>");
            sb.append(totalSuccessCount);
            sb.append("</b>个文件。");
            sb.append("失败导出<b>");
            sb.append(totalFailureCount);
            sb.append("</b>个文件。");
            if (totalSuccessCount != 0)
            {
                sb.append("<p>具体信息为：<p>");

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
            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("。<p>失败原因：<br>");
            sb.append(reason);
        }

        LOG.info(sb.toString());
        Mail.sendMail(mailTitle, sb.toString(), ExperienceConfig.mailTo);
    }
}
