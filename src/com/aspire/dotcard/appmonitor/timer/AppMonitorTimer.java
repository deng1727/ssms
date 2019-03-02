package com.aspire.dotcard.appmonitor.timer;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.appmonitor.config.AppMonitorConfig;

public class AppMonitorTimer {

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(AppMonitorTimer.class);
	
	/**
	 * ������ʱ����
	 * 
	 */
	public static void start()
	{
		logger.info("�ص�Ӧ�ü��������");
		
		String startTime = AppMonitorConfig.STARTTIME;
		
		// �õ�������Сʱ�ͷ���
		int syncDataHours = Integer.parseInt(startTime.substring(0, 2));
		int syncDataMin = Integer.parseInt(startTime.substring(2, 4));
		
		// �õ�long����һ��ļ������
		Calendar date = Calendar.getInstance();
		int hours = date.get(Calendar.HOUR_OF_DAY);
		int minitue = date.get(Calendar.MINUTE);
		
		date.set(Calendar.HOUR_OF_DAY, syncDataHours);
		date.set(Calendar.MINUTE, syncDataMin);
		date.set(Calendar.SECOND, 0);
		
		if (hours > syncDataHours)
		{
			date.set(Calendar.DAY_OF_MONTH,date.get(Calendar.DAY_OF_MONTH) + 1);
		}
		
		// ���Сʱ������жϷ���
		if (hours == syncDataHours)
		{
			if (minitue > syncDataMin)
			{
				date.set(Calendar.DAY_OF_MONTH,
						date.get(Calendar.DAY_OF_MONTH) + 1);
			}
		}
		
		// �õ��������
		long period = 24 * 60 * 60 * 1000;
		
		// ����Timer���schedule����������ʱ����
		Timer timer = new Timer("�ص�Ӧ�ü��", true);
		timer.schedule(new AppMonitorTask(), date.getTime(), period);
//		new AppMonitorTask().run();
		logger.info("��ǰ����ִ��ʱ�䣺" + date.getTime().toString()
				+ "��ǰ���������ص�Ӧ�ü��");
		
		logger.info("�ص�Ӧ�ü�ؽ�����");
	}
}
