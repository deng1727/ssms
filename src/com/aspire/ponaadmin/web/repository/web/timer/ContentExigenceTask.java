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
	 * �Զ�������н��������ߴ���
	 */
	public void run()
	{
		logger.info("ContentExigenceTask begin!");
		
		String exigenceExeContent = Config.getInstance().getModuleConfig()
				.getItemValue("exigenceExeContent");

		// Ĭ��ֵ
		if(exigenceExeContent == null || "".equals(exigenceExeContent))
		{
			exigenceExeContent = "0";
		}
		
		try
		{
			if(DataSyncBO.getInstance().isLock())
			{
				logger.info("ContentExigenceTask ִ��ʱ���֣���ǰ����ͬ������ִ���У������˴�������ͣ������");
				throw new BOException("ContentExigenceTask ִ��ʱ���֣���ǰ����ͬ������ִ���У������˴�������ͣ������");
			}
			else
			{
				// �жϵ�ǰ�����������Ƿ���ִ����
				if (!ContentExigenceBO.getInstance().isLock())
				{
					ContentExigenceBO.getInstance().SyncContentExigence(exigenceExeContent, true, "1");
	
					Mail.sendMail("֪ͨ�����Ż�ִ�н��", sysWaken(), MailConfig
							.getInstance().getMailToArray());
				}
				else
				{
					logger.info("ContentExigenceTask ִ��ʱ���֣���ǰ��������������ִ���У�����");
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
	 * ����֪ͨ�����Ż�����������Ӧ�����
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

		// ֪ͨMO�Ż�
		sb.append("MO�Ż�").append(waken(mo_url));

		// ֪ͨWWW�Ż�
		sb.append("WWW�Ż�").append(waken(www_url));

		// ֪ͨWAP�Ż�
		sb.append("WAP�Ż�").append(waken(wap_url));

		// ֪ͨ�����Ż�
		sb.append("�����Ż�").append(waken(search_url));

		return sb.toString();
	}

	/**
	 * ֪ͨ�Ż�����
	 * 
	 * @param url
	 *            �Ż�url
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
			logger.info("���������Ż�����ͬ������ʧ��");
		}

		if (code == 200)
		{
			return "֪ͨ�ɹ�. " + "<br>";
		}
		else
		{
			return " ֪ͨʧ��. url=" + url + "<br>";
		}
	}

}
