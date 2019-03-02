package com.aspire.ponaadmin.web.dataexport.marketing.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.searchfile.SearchFileConfig;
import com.aspire.ponaadmin.web.util.DateUtil;

public class AppTopTimer {
	/**
	 * 记录日志的实例对象
	 */
	private static JLogger LOG = LoggerFactory.getLogger(AppTopTimer.class);
	private static AppTopTimer timer = new AppTopTimer();

	private AppTopTimer() {

	}

	public static AppTopTimer getInstance() {
		return timer;
	}

	/**
	 * 启动定时导入
	 */
	public void start() {
		// 系统默认的基地音乐同步执行时间 5:00
		int hour = 5;
		int minute = 0;
		// 从配置项中获取配置值
		try {
			String rbStartTime = SearchFileConfig.APPTOP_STARTTIME;
			hour = Integer.parseInt(rbStartTime.split(":")[0]);
			minute = Integer.parseInt(rbStartTime.split(":")[1]);
		} catch (Exception ex) {
			LOG.error("从配置项中获取基地图书同步的时间AppTopStarttime解析出错！");
		}
		if (hour < 0 || hour > 23) {
			// 配置值不正确，就使用默认值
			hour = 5;
		}
		if (minute < 0 || minute > 59) {
			// 配置值不正确，就使用默认值
			minute = 0;
		}

		// 间隔时间为一天24小时
		long taskIntervalMS = (long) (24 * 60 * 60 * 1000);
		Timer timer = new Timer(true);
		Date firstStartTime = null;
		try {
			// 取当前时间
			Date date = new Date();
			// 构造第一次任务运行时间
			firstStartTime = getOneTimeByHourAndMinute(date, hour, minute);
			// 如果第一次运行时间早于当前时间，需要把第一次运行时间加一天
			if (firstStartTime.before(date)) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date tommorrow = calendar.getTime();
				firstStartTime = getOneTimeByHourAndMinute(tommorrow, hour,
						minute);
			}
		} catch (Exception ex) {
			// 设置时间有异常，就用当前的时间吧，不过如果程序没有bug，这个异常应该是不可能出现的
			firstStartTime = new Date();
		}
		timer.schedule(new AppTopTask(), firstStartTime, taskIntervalMS);
		if (LOG.isDebugEnabled()) {
			LOG.debug("schedule a AppTopTask,first start time is:"
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
			int minute) {
		String c = DateUtil.formatDate(date, "yyyyMMdd");
		if (hour < 10) {
			c = c + '0';
		}
		c = c + hour + minute + "00";
		return DateUtil.stringToDate(c, "yyyyMMddHHmmss");
	}
}
