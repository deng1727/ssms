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

		// �õ�������Сʱ�ͷ���
		int rankDataHours = Integer.parseInt(rankDataTime.substring(0, 2));
		int rankDataMin = Integer.parseInt(rankDataTime.substring(2, 4));
		if (logger.isDebugEnabled()) {
			logger.debug("rankDataHours=" + rankDataHours + ";rankDataMin="
					+ rankDataMin);
		}
		// �õ�long����һ��ļ������
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
		// ���Сʱ������жϷ���
		if (hours == rankDataHours) {
			if (minitue > rankDataMin) {
				date.setDate(date.getDate() + 1);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("start task date is " + date);
		}
		// �õ��������
		long period = 24 * 60 * 60 * 1000;
		// ����Timer���schedule����������ʱ����
		Timer timer = new Timer(true);
		timer.schedule(new RankTask(), date, period);
		//new RankTask().run();
	}
}
