package com.aspire.dotcard.iapMonitor.timer;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.iapMonitor.config.IapMonitorConfig;

public class IapMonitorTimer {

	/**
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(IapMonitorTimer.class);
	
	/**
	 * 启动定时任务
	 * 
	 */
	public static void start()
	{
		logger.info("每天监控重点系列指定时间（一周）内和娱乐IAP榜单数量启动！");
		
		String startTime = IapMonitorConfig.STARTTIME;
		
		// 得到启动的小时和分钟
		int syncDataHours = Integer.parseInt(startTime.substring(0, 2));
		int syncDataMin = Integer.parseInt(startTime.substring(2, 4));
		
		// 得到long类型一天的间隔周期
		Calendar date = Calendar.getInstance();
		int hours = date.get(Calendar.HOUR_OF_DAY);
		int minitue = date.get(Calendar.MINUTE);
		
		date.set(Calendar.HOUR_OF_DAY, syncDataHours);
		date.set(Calendar.MINUTE, syncDataMin);
		date.set(Calendar.SECOND, 0);
		
		if (hours > syncDataHours)
		{
			date
					.set(Calendar.DAY_OF_MONTH,
							date.get(Calendar.DAY_OF_MONTH) + 1);
		}
		
		// 如果小时相等则判断分钟
		if (hours == syncDataHours)
		{
			if (minitue > syncDataMin)
			{
				date.set(Calendar.DAY_OF_MONTH,
						date.get(Calendar.DAY_OF_MONTH) + 1);
			}
		}
		
		// 得到间隔周期
		long period = 24 * 60 * 60 * 1000;
		
		// 调用Timer类的schedule方法启动定时任务。
		Timer timer = new Timer("每天监控重点系列指定时间（一周）内和娱乐IAP榜单数量", true);
		timer.schedule(new IapMonitorTask(), date.getTime(), period);
		
		logger.info("当前任务执行时间：" + date.getTime().toString()
				+ "当前任务名：每天监控重点系列指定时间（一周）内和娱乐IAP榜单数量");
		
		logger.info("每天监控重点系列指定时间（一周）内和娱乐IAP榜单数量结束！");
	}
}
