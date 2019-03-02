package com.aspire.dotcard.iapMonitor.timer;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.iapMonitor.config.IapMonitorConfig;

public class IapMonitorTimer {

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(IapMonitorTimer.class);
	
	/**
	 * ������ʱ����
	 * 
	 */
	public static void start()
	{
		logger.info("ÿ�����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������������");
		
		String startTime = IapMonitorConfig.STARTTIME;
		
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
			date
					.set(Calendar.DAY_OF_MONTH,
							date.get(Calendar.DAY_OF_MONTH) + 1);
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
		Timer timer = new Timer("ÿ�����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������", true);
		timer.schedule(new IapMonitorTask(), date.getTime(), period);
		
		logger.info("��ǰ����ִ��ʱ�䣺" + date.getTime().toString()
				+ "��ǰ��������ÿ�����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������");
		
		logger.info("ÿ�����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������������");
	}
}
