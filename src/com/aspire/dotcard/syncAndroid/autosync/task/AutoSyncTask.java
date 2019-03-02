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
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(AutoSyncTask.class);
	
	/**
	 * 执行任务（业务同步和内容同步）
	 */
	public void run()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("向数据中心同步自动更新货架开始...");
		}
		
		StringBuffer msgInfo = new StringBuffer();
		String mailTitle = "向数据中心同步自动更新货架";
		String[] mailTo = Constants.MAILTO;
		List<AutoVO> list = null;
		
		long starttime = System.currentTimeMillis();
		Calendar startDate = Calendar.getInstance();
		msgInfo.append("向数据中心同步自动更新货架开始执行时间：").append(
				startDate.get(Calendar.HOUR_OF_DAY)).append(":").append(
				startDate.get(Calendar.MINUTE)).append("<br>");
		
		
		// 得到当前将要同步的任务
		try
		{
			list = AutoSyncDAO.getInstance().queryAutoList();
		}
		catch (DAOException e)
		{
			logger.error("获取要通知数据中心的自动更新货架任务时发生了错误 ！", e);
			Mail.sendMail(mailTitle, "获取要通知数据中心的自动更新货架任务时发生了错误 ！", mailTo);
			return;
		}
		
		// 发送具体操作消息
		for (AutoVO autoVO : list)
		{
			msgInfo.append(autoVO.getCategoryId()).append("货架发送消息情况:");
			msgInfo.append(AutoSyncBO.getInstance().exceAutoCategory(autoVO));
			msgInfo.append("<br>");
		}
		
		//  发送邮件
		if (list.size() == 0)
		{
			Mail.sendMail(mailTitle, "当前没有可同步的任务！", mailTo);
		}
		else
		{
			long endtime = System.currentTimeMillis();
			Calendar endDate = Calendar.getInstance();
			msgInfo.append("向数据中心同步自动更新货架执行结束时间：").append(
					endDate.get(Calendar.HOUR_OF_DAY)).append(":").append(
					endDate.get(Calendar.MINUTE)).append("<br>");
			msgInfo.append("执行总耗时为：").append(
					String.valueOf(endtime - starttime)).append("毫秒");
			
			Mail.sendMail(mailTitle, msgInfo.toString(), mailTo);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("向数据中心同步自动更新货架结束...");
		}
	}
}
