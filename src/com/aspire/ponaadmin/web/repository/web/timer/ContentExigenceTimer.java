package com.aspire.ponaadmin.web.repository.web.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.DateUtil;

/**
 * ����ͼ����ض�ʱ��
 * 
 * @author x_zhailiqing
 * 
 */
public class ContentExigenceTimer
{
	/**
	 * ��¼��־��ʵ������
	 */
	private static JLogger LOG = LoggerFactory
			.getLogger(ContentExigenceTimer.class);
	private static ContentExigenceTimer contentExigenceTimer = new ContentExigenceTimer();

	private ContentExigenceTimer()
	{}

	public static ContentExigenceTimer getInstance()
	{
		return contentExigenceTimer;
	}

	/**
	 * ������ʱ����
	 */
	public void start()
	{
		List timeList = new ArrayList();
		
		// ���������л�ȡ����ֵ
		try
		{
			String exigenceStartTime = Config.getInstance().getModuleConfig()
					.getItemValue("exigenceStartTime");

			String[] exigenceStartTimes = exigenceStartTime.split("\\|");

			for (int i = 0; i < exigenceStartTimes.length; i++)
			{
				String startTime = exigenceStartTimes[i];
				int hour = Integer.parseInt(startTime.split(":")[0]);
				int minute = Integer.parseInt(startTime.split(":")[1]);

				if (hour < 0 || hour > 23)
				{
					// ����ֵ����ȷ����ʹ��Ĭ��ֵ
					hour = 5;
				}
				if (minute < 0 || minute > 59)
				{
					// ����ֵ����ȷ����ʹ��Ĭ��ֵ
					minute = 0;
				}

				timeList.add(new TimeVO(hour, minute));
			}
		}
		catch (Exception ex)
		{
			LOG.error("���������л�ȡ����ͼ��ͬ����ʱ��BaseBookSynStartTime��������");
		}


		// ���ʱ��Ϊһ��24Сʱ
		long taskIntervalMS = (long) (24 * 60 * 60 * 1000);
		Date firstStartTime = null;
		
		for (Iterator iterator = timeList.iterator(); iterator.hasNext();)
		{
			TimeVO timeVO = (TimeVO) iterator.next();
			
			Timer timer = new Timer(true);
			try
			{
				// ȡ��ǰʱ��
				Date date = new Date();
				// �����һ����������ʱ��
				firstStartTime = getOneTimeByHourAndMinute(date, timeVO.hour, timeVO.minute);
				
				// �����һ������ʱ�����ڵ�ǰʱ�䣬��Ҫ�ѵ�һ������ʱ���һ��
				if (firstStartTime.before(date))
				{
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					Date tommorrow = calendar.getTime();
					firstStartTime = getOneTimeByHourAndMinute(tommorrow, timeVO.hour,
							timeVO.minute);
				}
			}
			catch (Exception ex)
			{
				// ����ʱ�����쳣�����õ�ǰ��ʱ��ɣ������������û��bug������쳣Ӧ���ǲ����ܳ��ֵ�
				firstStartTime = new Date();
			}
			timer.schedule(new ContentExigenceTask(), firstStartTime,
					taskIntervalMS);
		}

		if (LOG.isDebugEnabled())
		{
			LOG.debug("schedule a ContentExigenceTask,first start time is:"
					+ firstStartTime);
		}
	}

	/**
	 * ��ȡһ��ĳ��ָ��ʱ���time
	 * 
	 * @param date
	 *            Date
	 * @param hour
	 *            int
	 * @return Date
	 * @throws Exception
	 */
	private static Date getOneTimeByHourAndMinute(Date date, int hour,
			int minute)
	{
		String c = DateUtil.formatDate(date, "yyyyMMdd");
		if (hour < 10)
		{
			c = c + '0';
		}
		c = c + hour + minute + "00";
		return DateUtil.stringToDate(c, "yyyyMMddHHmmss");
	}
	
	class TimeVO
	{
		public int hour = 5;
		
		public int minute = 0;
		
		public TimeVO(int hour, int minute)
		{
			this.hour = hour;
			this.minute = minute;
		}
	}
}
