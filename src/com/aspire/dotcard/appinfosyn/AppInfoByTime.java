/*
 * �ļ�����BaseVideoTime.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.dotcard.appinfosyn;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.appinfosyn.config.AppInfoConfig;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.sync.BaseVideoNewTask;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version 
 */
public class AppInfoByTime
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(AppInfoByTime.class);
    /**
     * 
     * ������ʱ����
     *
     */
    public static void start(){


		if (logger.isDebugEnabled())
		{
			logger.debug("start()");
		}
		// ���������еõ����������ʱ��
		String syncDataTime = AppInfoConfig.STARTTIME;
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
		Timer timer = new Timer(true);
      timer.schedule(new AppXmlTask(),date.getTime(),period);
		
		if (logger.isDebugEnabled())
		{
			logger.debug("start task date is " + date);
		}
	
}}
