package com.aspire.dotcard.essential.task;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basemusic.BaseMusicLoadTimer;
import com.aspire.ponaadmin.web.system.Config;
/**
 * 1.3����Ӧ����MMӦ��
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
		
		// �õ�������Сʱ�ͷ���
		int essentialDataHours = Integer.parseInt(essentialDataTime.substring(
				0, 2));
		int essentialDataMin = Integer.parseInt(essentialDataTime.substring(2,
				4));
		if (logger.isDebugEnabled()) {
			logger.debug("essentialDataHours=" + essentialDataHours
					+ ";essentialDataMin=" + essentialDataMin);
		}
		// �õ�long����һ��ļ������
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
		// ���Сʱ������жϷ���
		if (hours == essentialDataHours) {
			if (minitue > essentialDataMin) {
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
		timer.schedule(new EssentialTask(), date, period);
	   // new EssentialTask().run();
	}

}
