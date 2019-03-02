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
	 * 记录日志的实例对象
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
				message = "应用信息同步  成功";
				sucessList.add(message);
			} else {
				message = "应用信息同步  失败";
				failureList.add(message);
			}

			appexport = new DeviceTopAppExport();
			exporter.export(appexport);

			if (result) {
				message = "终端适配应用信息同步  成功";
				sucessList.add(message);
			} else {
				message = "终端适配应用信息同步  失败";
				failureList.add(message);
			}
			result = true;
		} catch (Exception e) {
			log.error("同步心机任务出错", e);
			result = false;
			reason = e.getMessage();
		}
		String mailTitle;
		// 发送邮件表示本次处理结束
		Date endDate = new Date();
		StringBuffer sb = new StringBuffer();
		int totalSuccessCount = sucessList.size();
		int totalFailureCount = failureList.size();
		mailTitle = "心机平台数据文件生成结果";
		if (result) {

			sb.append("开始时间：");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<h4>处理结果：</h4>");
			sb.append("<p>其中成功导出<b>");
			sb.append(totalSuccessCount);
			sb.append("</b>个文件。");
			sb.append("失败导出<b>");
			sb.append(totalFailureCount);
			sb.append("</b>个文件。");
			if (totalSuccessCount != 0) {
				sb.append("<p>具体信息为为：<p>");

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
			sb.append("开始时间：");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<p>失败原因：<br>");
			sb.append(reason);
		}

		log.info(sb.toString());
		Mail.sendMail(mailTitle, sb.toString(), SearchFileConfig.mailTo);		
	}


}
