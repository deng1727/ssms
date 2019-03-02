package com.aspire.dotcard.essential.task;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basemusic.BaseMusicLoadTimer;
import com.aspire.ponaadmin.web.system.Config;
/**
 * 1.3触点应用与MM应用
 * @author dengshaobo
 *
 */
public class EssentialTimer {
	

	
	
	protected static JLogger logger = LoggerFactory
			.getLogger(EssentialTimer.class);

	public static void start() {
		if (logger.isDebugEnabled()) {
			logger.debug("EssentialTimer start() in ....");
		}

		String essentialDataTime = Config.getInstance().getModuleConfig().getItemValue("essentialDataTime");
		
		// 得到启动的小时和分钟
		int essentialDataHours = Integer.parseInt(essentialDataTime.substring(
				0, 2));
		int essentialDataMin = Integer.parseInt(essentialDataTime.substring(2,
				4));
		if (logger.isDebugEnabled()) {
			logger.debug("essentialDataHours=" + essentialDataHours
					+ ";essentialDataMin=" + essentialDataMin);
		}
		// 得到long类型一天的间隔周期
		Date date = new Date();
		int hours = date.getHours();
		int minitue = date.getMinutes();
		if (logger.isDebugEnabled()) {
			logger.debug("now hourse is " + hours);
		}
		date.setHours(essentialDataHours);
		date.setMinutes(essentialDataMin);
		date.setSeconds(0);
		if (hours > essentialDataHours) {
			date.setDate(date.getDate() + 1);
		}
		// 如果小时相等则判断分钟
		if (hours == essentialDataHours) {
			if (minitue > essentialDataMin) {
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
		timer.schedule(new EssentialTask(), date, period);
	   // new EssentialTask().run();
	}

}
