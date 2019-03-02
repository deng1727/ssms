/*
 * 文件名：BaseVideoTime.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.dotcard.baseVideo.sync;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version 
 */
public class BaseVideoByHourTime
{
    /**
     * 日志对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseVideoByHourTime.class);
    /**
     * 启动定时任务
     *
     */
    public static void start()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("start()");
        }
        //从配置项中得到任务的启动时间
        String syncDataTime = BaseVideoConfig.STARTTIME_MINUTES;
        //得到启动的小时和分钟
        int syncDataMin = Integer.parseInt(syncDataTime);
        if(logger.isDebugEnabled())
        {
            logger.debug("syncDataMin="+syncDataMin);
        }
        
        //得到long类型一天的间隔周期
        Date date = new Date();
        int hours = date.getHours();
        int minitue = date.getMinutes();
        if(logger.isDebugEnabled())
        {
            logger.debug("now hourse is "+hours);
        }
        date.setMinutes(syncDataMin);
        date.setSeconds(0);
        if(minitue > syncDataMin)
        {
            date.setHours(date.getHours()+1);
        }
       
        if(logger.isDebugEnabled())
        {
            logger.debug("start task date is "+date);
        }
        //得到间隔周期
        long period = 1 * 60 * 60 * 1000;
        //调用Timer类的schedule方法启动定时任务。
        Timer timer = new Timer(true);
        timer.schedule(new BaseVideoByHourTask(),date,period);
    }
}
