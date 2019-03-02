/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience.task ExperienceTask.java
 * Jul 6, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.experience;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.experience.ext.AllAppExperience;

import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 * 
 */
public class AllAppExperienceTask extends TimerTask
{

	private static final JLogger LOG = LoggerFactory
			.getLogger(AllAppExperienceTask.class);

	private List sucessList = new ArrayList();

	private List failureList = new ArrayList();

	private Date startDate;

	public void run()
	{
		LOG.info("开始生成月度全量应用信息同步文件");
		startDate = new Date();
		sucessList.clear();
		failureList.clear();
		String message = "";
		boolean result = false;

		SimpleDateFormat sdf = new SimpleDateFormat("dd");

		// 是否是当月的指定某一号
		if (!ExperienceConfig.APPSYSMONTHDAY.equals(sdf.format(startDate)))
		{
			LOG.info("不是指定的每月的第" + ExperienceConfig.APPSYSMONTHDAY
					+ "号，不用生成月度全量应用信息同步文件！");
			return;
		}

		CommonExperience appexport = null;
		CreateFileByExperience exporter = new CreateFileByExperience();

		try
		{
			appexport = new AllAppExperience();
			appexport.setConstraint("1");// 1,全量；0，增量
			appexport.fileName = DataExportTools
					.parseFileName(ExperienceConfig.APPFullName);
			if (!CommonExperience.FileWriteDIR.endsWith(File.separator))
			{
				appexport.exportedFileName = CommonExperience.FileWriteDIR
						+ File.separator + appexport.fileName;
			}
			else
			{
				appexport.exportedFileName = CommonExperience.FileWriteDIR
						+ appexport.fileName;
			}
			result = exporter.export(appexport);

			if (result)
			{
				message = "月度全量应用信息同步  成功";
				sucessList.add(message);
			}
			else
			{
				message = "月度全量应用信息同步  失败";
				failureList.add(message);
			}
		}
		catch (Exception e)
		{
			LOG.error("生成月度全量应用信息同步文件失败", e);
		}

		sendResultMail();

		LOG.info("生成月度全量应用信息同步文件结束");
	}

	/**
	 * 发送结果邮件。
	 */
	private void sendResultMail()
	{
		String mailTitle;

		// 发送邮件表示本次处理结束
		Date endDate = new Date();
		StringBuffer sb = new StringBuffer();
		int totalSuccessCount = sucessList.size();
		int totalFailureCount = failureList.size();
		mailTitle = "月度全量应用信息同步文件生成结果";
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

		LOG.info(sb.toString());
		Mail.sendMail(mailTitle, sb.toString(), ExperienceConfig.mailTo);
	}
}
