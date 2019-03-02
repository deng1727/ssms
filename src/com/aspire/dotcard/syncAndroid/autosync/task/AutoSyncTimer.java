package com.aspire.dotcard.syncAndroid.autosync.task;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.autosync.conf.Constants;

public class AutoSyncTimer
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(AutoSyncTimer.class);
	
	/**
	 * ������ʱ����
	 * 
	 */
	public static void start()
	{
		logger.info("����������ͬ���Զ����»���������");
		
		String startTime = Constants.STARTTIME;
		
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
		Timer timer = new Timer("����������ͬ���Զ����»���", true);
		timer.schedule(new AutoSyncTask(), date.getTime(), period);
		
		logger.info("��ǰ����ִ��ʱ�䣺" + date.getTime().toString()
				+ "��ǰ������������������ͬ���Զ����»���");
		
		logger.info("����������ͬ���Զ����»��ܽ�����");
	}
}
