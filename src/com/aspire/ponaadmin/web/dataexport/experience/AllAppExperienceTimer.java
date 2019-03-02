/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience.task ExperienceTimer.java
 * Jul 6, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.experience;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke
 * 
 */
public class AllAppExperienceTimer
{

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(AllAppExperienceTimer.class);

	/**
	 * ������ʱ����
	 * 
	 */
	public static void start()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("start()");
		}
		// ���������еõ����������ʱ��
		String syncDataTime = ExperienceConfig.APPSYSMONTHSTARTTIME;
		// �õ�������Сʱ�ͷ���
		int syncDataHours = 0;
		int syncDataMin = 0;
		if (syncDataTime.length() == 4)
		{
			syncDataHours = Integer.parseInt(syncDataTime.substring(0, 2));
			syncDataMin = Integer.parseInt(syncDataTime.substring(2, 4));
		}
		else if (syncDataTime.length() == 5)
		{
			syncDataHours = Integer.parseInt(syncDataTime.substring(0, 2));
			syncDataMin = Integer.parseInt(syncDataTime.substring(3, 5));
		}
		else
		{
			logger.error("ExperStartTime format is wrong :" + syncDataTime);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("syncDataHours=" + syncDataHours + ";syncDataMin="
					+ syncDataMin);
		}
		// �õ�long����һ��ļ������
		Date date = new Date();
		int hours = date.getHours();
		int minitue = date.getMinutes();
		if (logger.isDebugEnabled())
		{
			logger.debug("now hourse is " + hours);
		}
		date.setHours(syncDataHours);
		date.setMinutes(syncDataMin);
		date.setSeconds(0);
		if (hours > syncDataHours)
		{
			date.setDate(date.getDate() + 1);
		}
		// ���Сʱ������жϷ���
		if (hours == syncDataHours)
		{
			if (minitue > syncDataMin)
			{
				date.setDate(date.getDate() + 1);
			}
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("start task date is " + date);
		}
		// �õ��������
		long period = 24 * 60 * 60 * 1000;
		// ����Timer���schedule����������ʱ����
		Timer timer = new Timer(true);
		timer.schedule(new AllAppExperienceTask(), date, period);
	}
}
