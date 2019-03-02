package com.aspire.dotcard.basevideosync.sync;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.sync.BaseVideoTask;

/**
 * ����Ƶ������Ƶ��ʱ����timer
 */
public class BaseVideoTimer {

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoTimer.class);
	
	/**
	 * ������ʱ����
	 * 
	 */
	public static void start()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("basevideosync.sync.BaseVideoTimer start()");
		}
		// ���������еõ����������ʱ��
		String syncDataTime = BaseVideoConfig.STARTTIME;
		// �õ�������Сʱ�ͷ���
		int syncDataHours = Integer.parseInt(syncDataTime.substring(0, 2));
		int syncDataMin = Integer.parseInt(syncDataTime.substring(3, 5));
		if (logger.isDebugEnabled())
		{
			logger.debug("syncDataHours=" + syncDataHours + ";syncDataMin="
					+ syncDataMin);
		}
		
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
		Timer timer = new Timer("����Ƶ�����ع�������ʱ����", true);
		timer.schedule(new BaseVideoTask(), date.getTime(), period);
		
		if (logger.isDebugEnabled())
		{
			logger.debug("basevideosync.sync.BaseVideoTimer start task date is " + date);
		}
	}
}
