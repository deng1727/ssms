package com.aspire.ponaadmin.web.repository.web.timer;

import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.web.ContentExigenceBO;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.HttpUtil;

public class ContentExigenceTask extends TimerTask
{
	protected static JLogger logger = LoggerFactory
			.getLogger(ContentExigenceTask.class);

	/**
	 * 自动任务进行紧急上下线处理
	 */
	public void run()
	{
		logger.info("ContentExigenceTask begin!");
		
		String exigenceExeContent = Config.getInstance().getModuleConfig()
				.getItemValue("exigenceExeContent");

		// 默认值
		if(exigenceExeContent == null || "".equals(exigenceExeContent))
		{
			exigenceExeContent = "0";
		}
		
		try
		{
			if(DataSyncBO.getInstance().isLock())
			{
				logger.info("ContentExigenceTask 执行时发现，当前内容同步正在执行中！！！此次任务暂停！！！");
				throw new BOException("ContentExigenceTask 执行时发现，当前内容同步正在执行中！！！此次任务暂停！！！");
			}
			else
			{
				// 判断当前紧急上下线是否在执行中
				if (!ContentExigenceBO.getInstance().isLock())
				{
					ContentExigenceBO.getInstance().SyncContentExigence(exigenceExeContent, true, "1");
	
					Mail.sendMail("通知三大门户执行结果", sysWaken(), MailConfig
							.getInstance().getMailToArray());
				}
				else
				{
					logger.info("ContentExigenceTask 执行时发现，当前紧急上下线正在执行中！！！");
				}
			}
		}
		catch (BOException e)
		{
			logger.error(e);
		}

		logger.info("ContentExigenceTask end!");
	}

	/**
	 * 用于通知三大门户紧急上下线应用完成
	 * 
	 * @return
	 */
	private String sysWaken()
	{
		String mo_url = Config.getInstance().getModuleConfig().getItemValue(
				"MO_URL_Sync");
		String www_url = Config.getInstance().getModuleConfig().getItemValue(
				"WWW_URL_Sync");
		String wap_url = Config.getInstance().getModuleConfig().getItemValue(
				"WAP_URL_Sync");
		String search_url = Config.getInstance().getModuleConfig()
				.getItemValue("SEARCH_URL_Sync");
		StringBuffer sb = new StringBuffer();

		// 通知MO门户
		sb.append("MO门户").append(waken(mo_url));

		// 通知WWW门户
		sb.append("WWW门户").append(waken(www_url));

		// 通知WAP门户
		sb.append("WAP门户").append(waken(wap_url));

		// 通知搜索门户
		sb.append("搜索门户").append(waken(search_url));

		return sb.toString();
	}

	/**
	 * 通知门户方法
	 * 
	 * @param url
	 *            门户url
	 * @return
	 */
	private String waken(String url)
	{
		int code = 0;

		try
		{
			code = HttpUtil.getResponseCodeFromURL(url, "utf-8");
		}
		catch (Exception e)
		{
			logger.info("激活三大门户调用同步方法失败");
		}

		if (code == 200)
		{
			return "通知成功. " + "<br>";
		}
		else
		{
			return " 通知失败. url=" + url + "<br>";
		}
	}

}
