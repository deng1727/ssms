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
 * 基地图书加载定时器
 * 
 * @author x_zhailiqing
 * 
 */
public class ContentExigenceTimer
{
	/**
	 * 记录日志的实例对象
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
	 * 启动定时导入
	 */
	public void start()
	{
		List timeList = new ArrayList();
		
		// 从配置项中获取配置值
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
					// 配置值不正确，就使用默认值
					hour = 5;
				}
				if (minute < 0 || minute > 59)
				{
					// 配置值不正确，就使用默认值
					minute = 0;
				}

				timeList.add(new TimeVO(hour, minute));
			}
		}
		catch (Exception ex)
		{
			LOG.error("从配置项中获取基地图书同步的时间BaseBookSynStartTime解析出错！");
		}


		// 间隔时间为一天24小时
		long taskIntervalMS = (long) (24 * 60 * 60 * 1000);
		Date firstStartTime = null;
		
		for (Iterator iterator = timeList.iterator(); iterator.hasNext();)
		{
			TimeVO timeVO = (TimeVO) iterator.next();
			
			Timer timer = new Timer(true);
			try
			{
				// 取当前时间
				Date date = new Date();
				// 构造第一次任务运行时间
				firstStartTime = getOneTimeByHourAndMinute(date, timeVO.hour, timeVO.minute);
				
				// 如果第一次运行时间早于当前时间，需要把第一次运行时间加一天
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
				// 设置时间有异常，就用当前的时间吧，不过如果程序没有bug，这个异常应该是不可能出现的
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
	 * 获取一天某个指定时间的time
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
