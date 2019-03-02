package com.aspire.dotcard.basevideosync.sync;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;

public class BaseVideoApiRequestTimer {

	/**
     * 日志对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseVideoApiRequestTimer.class);
    /**
     * 启动定时任务
     *
     */
    public static void start()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("basevideosync.sync.BaseVideoApiRequestTimer.start()");
        }
		// 从配置项中得到任务的启动时间
		String syncDataTime = BaseVideoConfig.APISTARTTIME;
		// 得到启动的小时和分钟
		int syncDataHours = Integer.parseInt(syncDataTime.substring(0, 2));
		int syncDataMin = Integer.parseInt(syncDataTime.substring(3, 5));
		if (logger.isDebugEnabled())
		{
			logger.debug("syncDataHours=" + syncDataHours + ";syncDataMin="
					+ syncDataMin);
		}
		
		// 得到long类型一天的间隔周期
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
		
		// 如果小时相等则判断分钟
		if (hours == syncDataHours)
		{
			if (minitue > syncDataMin)
			{
				date.set(Calendar.DAY_OF_MONTH,
						date.get(Calendar.DAY_OF_MONTH) + 1);
			}
		}
        //得到间隔周期
        long period = 24 * 60 * 60 * 1000;
        //调用Timer类的schedule方法启动定时任务。
        Timer timer = new Timer(true);
        timer.schedule(new BaseVideoApiRequestTask(),date.getTime(),period);
    }
}
