/**
 *  <p>ZCOM数据同步定时器</p>
 * <p>Copyright (c) 2003-2010 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * SSMS
 * com.aspire.dotcard.synczcom.task ZcomDataSyncTimer.java
 * Apr 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.synczcom.task;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

import com.aspire.ponaadmin.web.system.Config;

/**
 * @author tungke
 *
 */
public class ZcomDataSyncTimer {

	 /**
     * 日志对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(ZcomDataSyncTimer.class);
  
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
        String syncDataTime = Config.getInstance()
                                      .getModuleConfig()
                                      // .getItemValue("zcomSyncDataTime");
                                      .getItemValue("syncDataTime");
        //得到启动的小时和分钟
        int syncDataHours = Integer.parseInt(syncDataTime.substring(0,2));
        int syncDataMin = Integer.parseInt(syncDataTime.substring(2,4));
        if(logger.isDebugEnabled())
        {
            logger.debug("syncDataHours="+syncDataHours+";syncDataMin="+syncDataMin);
        }
        //得到long类型一天的间隔周期
        Date date = new Date();
        int hours = date.getHours();
        int minitue = date.getMinutes();
        if(logger.isDebugEnabled())
        {
            logger.debug("now hourse is "+hours);
        }
        date.setHours(syncDataHours);
       // date.setMinutes(syncDataMin);
        date.setMinutes(syncDataMin-5);//延缓5分钟启动，让MM内容同步先做。
        date.setSeconds(0);
        if(hours > syncDataHours)
        {
            date.setDate(date.getDate()+1);
        }
        //如果小时相等则判断分钟
        if(hours == syncDataHours)
        {
            if(minitue>syncDataMin)
            {
                date.setDate(date.getDate()+1);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("start task date is "+date);
        }
        //得到间隔周期
        long period = 24 * 60 * 60 * 1000;
        //调用Timer类的schedule方法启动定时任务。
        Timer timer = new Timer(true);
        timer.schedule(new ZcomDataSyncTask(),date,period);
    }
}
