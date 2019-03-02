package com.aspire.dotcard.updatetime;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.task.DataSyncTimer;
import com.aspire.ponaadmin.web.system.Config;

public class TimeSyncTimer {
	/**
     * 日志对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(TimeSyncTimer.class);
    /**
     * 启动定时任务
     *
     */
    
    public static void start (){
    	if(logger.isDebugEnabled())
        {
            logger.debug("start()");
        }
        //从配置项中得到任务的启动时间
        String syncDataTime = Config.getInstance()
                                      .getModuleConfig()
                                      .getItemValue("syncDataTime");
        //得到启动的小时和分钟
        int syncDataHours = Integer.parseInt(syncDataTime.substring(0,2)) + 2;
        int syncDataMin = Integer.parseInt(syncDataTime.substring(2,4));
        if(logger.isDebugEnabled())
        {
            logger.debug("syncDataHours="+syncDataHours+";syncDataMin="+syncDataMin);
        }
        //得到long类型一天的间隔周期
        
        Date firstTime = new Date();
        int hours = firstTime.getHours();
        int minitue = firstTime.getMinutes();
        if(logger.isDebugEnabled())
        {
            logger.debug("now hourse is "+hours);
        }
        firstTime.setHours(syncDataHours);
        firstTime.setMinutes(syncDataMin);
        firstTime.setSeconds(0);
        if(hours > syncDataHours)
        {
        	firstTime.setDate(firstTime.getDate()+1);
        }
        //如果小时相等则判断分钟
        if(hours == syncDataHours)
        {
            if(minitue>syncDataMin)
            {
            	firstTime.setDate(firstTime.getDate()+1);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("start task date is "+firstTime);
        //得到间隔周期
        long period = 24 * 60 * 60 * 1000;
        
        Timer timer = new Timer();
        timer.schedule(new TimeSyncTask(), firstTime, period);
        }
    }
}
