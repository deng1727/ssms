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
		LOG.info("��ʼ�����¶�ȫ��Ӧ����Ϣͬ���ļ�");
		startDate = new Date();
		sucessList.clear();
		failureList.clear();
		String message = "";
		boolean result = false;

		SimpleDateFormat sdf = new SimpleDateFormat("dd");

		// �Ƿ��ǵ��µ�ָ��ĳһ��
		if (!ExperienceConfig.APPSYSMONTHDAY.equals(sdf.format(startDate)))
		{
			LOG.info("����ָ����ÿ�µĵ�" + ExperienceConfig.APPSYSMONTHDAY
					+ "�ţ����������¶�ȫ��Ӧ����Ϣͬ���ļ���");
			return;
		}

		CommonExperience appexport = null;
		CreateFileByExperience exporter = new CreateFileByExperience();

		try
		{
			appexport = new AllAppExperience();
			appexport.setConstraint("1");// 1,ȫ����0������
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
				message = "�¶�ȫ��Ӧ����Ϣͬ��  �ɹ�";
				sucessList.add(message);
			}
			else
			{
				message = "�¶�ȫ��Ӧ����Ϣͬ��  ʧ��";
				failureList.add(message);
			}
		}
		catch (Exception e)
		{
			LOG.error("�����¶�ȫ��Ӧ����Ϣͬ���ļ�ʧ��", e);
		}

		sendResultMail();

		LOG.info("�����¶�ȫ��Ӧ����Ϣͬ���ļ�����");
	}

	/**
	 * ���ͽ���ʼ���
	 */
	private void sendResultMail()
	{
		String mailTitle;

		// �����ʼ���ʾ���δ������
		Date endDate = new Date();
		StringBuffer sb = new StringBuffer();
		int totalSuccessCount = sucessList.size();
		int totalFailureCount = failureList.size();
		mailTitle = "�¶�ȫ��Ӧ����Ϣͬ���ļ����ɽ��";
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

		LOG.info(sb.toString());
		Mail.sendMail(mailTitle, sb.toString(), ExperienceConfig.mailTo);
	}
}
