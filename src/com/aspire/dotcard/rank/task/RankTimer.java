package com.aspire.dotcard.rank.task;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.system.Config;

public class RankTimer {

	protected static JLogger logger = LoggerFactory.getLogger(RankTimer.class);

	public static void start() {
		if (logger.isDebugEnabled()) {
			logger.debug("RankTimer start() in ....");
		}

		String rankDataTime = ConfigFactory.getSystemConfig()
				.getModuleConfig("rankConfig").getItemValue("startTime");

		// 得到启动的小时和分钟
		int rankDataHours = Integer.parseInt(rankDataTime.substring(0, 2));
		int rankDataMin = Integer.parseInt(rankDataTime.substring(2, 4));
		if (logger.isDebugEnabled()) {
			logger.debug("rankDataHours=" + rankDataHours + ";rankDataMin="
					+ rankDataMin);
		}
		// 得到long类型一天的间隔周期
		Date date = new Date();
		int hours = date.getHours();
		int minitue = date.getMinutes();
		if (logger.isDebugEnabled()) {
			logger.debug("now hourse is " + hours);
		}
		date.setHours(rankDataHours);
		date.setMinutes(rankDataMin);
		date.setSeconds(0);
		if (hours > rankDataHours) {
			date.setDate(date.getDate() + 1);
		}
		// 如果小时相等则判断分钟
		if (hours == rankDataHours) {
			if (minitue > rankDataMin) {
				date.setDate(date.getDate() + 1);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("start task date is " + date);
		}
		// 得到间隔周期
		long period = 24 * 60 * 60 * 1000;
		// 调用Timer类的schedule方法启动定时任务。
		Timer timer = new Timer(true);
		timer.schedule(new RankTask(), date, period);
		//new RankTask().run();
	}
}
