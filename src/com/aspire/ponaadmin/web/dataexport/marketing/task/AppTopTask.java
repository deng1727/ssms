package com.aspire.ponaadmin.web.dataexport.marketing.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.searchfile.SearchFileConfig;
import com.aspire.ponaadmin.web.dataexport.marketing.AppTopExport;
import com.aspire.ponaadmin.web.dataexport.marketing.CommonAppExport;
import com.aspire.ponaadmin.web.dataexport.marketing.DataExporter;
import com.aspire.ponaadmin.web.dataexport.marketing.DeviceTopAppExport;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class AppTopTask extends TimerTask {
	/**
	 * ��¼��־��ʵ������
	 */
	private static JLogger log = LoggerFactory.getLogger(AppTopTask.class);

	public void run() {
		String message = "";
		boolean result = false;
		List sucessList = new ArrayList();
		List failureList = new ArrayList();
		Date startDate = new Date();
		String reason = null;
		try {
			CommonAppExport appexport = new AppTopExport();
			DataExporter exporter = new DataExporter();
			result = exporter.export(appexport);
			if (result) {
				message = "Ӧ����Ϣͬ��  �ɹ�";
				sucessList.add(message);
			} else {
				message = "Ӧ����Ϣͬ��  ʧ��";
				failureList.add(message);
			}

			appexport = new DeviceTopAppExport();
			exporter.export(appexport);

			if (result) {
				message = "�ն�����Ӧ����Ϣͬ��  �ɹ�";
				sucessList.add(message);
			} else {
				message = "�ն�����Ӧ����Ϣͬ��  ʧ��";
				failureList.add(message);
			}
			result = true;
		} catch (Exception e) {
			log.error("ͬ���Ļ��������", e);
			result = false;
			reason = e.getMessage();
		}
		String mailTitle;
		// �����ʼ���ʾ���δ������
		Date endDate = new Date();
		StringBuffer sb = new StringBuffer();
		int totalSuccessCount = sucessList.size();
		int totalFailureCount = failureList.size();
		mailTitle = "�Ļ�ƽ̨�����ļ����ɽ��";
		if (result) {

			sb.append("��ʼʱ�䣺");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",����ʱ�䣺");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("��<h4>��������</h4>");
			sb.append("<p>���гɹ�����<b>");
			sb.append(totalSuccessCount);
			sb.append("</b>���ļ���");
			sb.append("ʧ�ܵ���<b>");
			sb.append(totalFailureCount);
			sb.append("</b>���ļ���");
			if (totalSuccessCount != 0) {
				sb.append("<p>������ϢΪΪ��<p>");

				for (int i = 0; i < sucessList.size(); i++) {
					sb.append(sucessList.get(i));
					sb.append("<br>");
				}
				sb.append("<br>");
			}

			for (int i = 0; i < failureList.size(); i++) {
				sb.append(failureList.get(i));
				sb.append("<br>");
			}

		} else {
			sb.append("��ʼʱ�䣺");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",����ʱ�䣺");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("��<p>ʧ��ԭ��<br>");
			sb.append(reason);
		}

		log.info(sb.toString());
		Mail.sendMail(mailTitle, sb.toString(), SearchFileConfig.mailTo);		
	}


}
