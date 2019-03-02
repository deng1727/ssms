package com.aspire.dotcard.syncAndroid.autosync.task;

import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.autosync.bo.AutoSyncBO;
import com.aspire.dotcard.syncAndroid.autosync.conf.Constants;
import com.aspire.dotcard.syncAndroid.autosync.dao.AutoSyncDAO;
import com.aspire.dotcard.syncAndroid.autosync.vo.AutoVO;
import com.aspire.ponaadmin.web.mail.Mail;



public class AutoSyncTask extends TimerTask
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(AutoSyncTask.class);
	
	/**
	 * ִ������ҵ��ͬ��������ͬ����
	 */
	public void run()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("����������ͬ���Զ����»��ܿ�ʼ...");
		}
		
		StringBuffer msgInfo = new StringBuffer();
		String mailTitle = "����������ͬ���Զ����»���";
		String[] mailTo = Constants.MAILTO;
		List<AutoVO> list = null;
		
		long starttime = System.currentTimeMillis();
		Calendar startDate = Calendar.getInstance();
		msgInfo.append("����������ͬ���Զ����»��ܿ�ʼִ��ʱ�䣺").append(
				startDate.get(Calendar.HOUR_OF_DAY)).append(":").append(
				startDate.get(Calendar.MINUTE)).append("<br>");
		
		
		// �õ���ǰ��Ҫͬ��������
		try
		{
			list = AutoSyncDAO.getInstance().queryAutoList();
		}
		catch (DAOException e)
		{
			logger.error("��ȡҪ֪ͨ�������ĵ��Զ����»�������ʱ�����˴��� ��", e);
			Mail.sendMail(mailTitle, "��ȡҪ֪ͨ�������ĵ��Զ����»�������ʱ�����˴��� ��", mailTo);
			return;
		}
		
		// ���;��������Ϣ
		for (AutoVO autoVO : list)
		{
			msgInfo.append(autoVO.getCategoryId()).append("���ܷ�����Ϣ���:");
			msgInfo.append(AutoSyncBO.getInstance().exceAutoCategory(autoVO));
			msgInfo.append("<br>");
		}
		
		//  �����ʼ�
		if (list.size() == 0)
		{
			Mail.sendMail(mailTitle, "��ǰû�п�ͬ��������", mailTo);
		}
		else
		{
			long endtime = System.currentTimeMillis();
			Calendar endDate = Calendar.getInstance();
			msgInfo.append("����������ͬ���Զ����»���ִ�н���ʱ�䣺").append(
					endDate.get(Calendar.HOUR_OF_DAY)).append(":").append(
					endDate.get(Calendar.MINUTE)).append("<br>");
			msgInfo.append("ִ���ܺ�ʱΪ��").append(
					String.valueOf(endtime - starttime)).append("����");
			
			Mail.sendMail(mailTitle, msgInfo.toString(), mailTo);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("����������ͬ���Զ����»��ܽ���...");
		}
	}
}
