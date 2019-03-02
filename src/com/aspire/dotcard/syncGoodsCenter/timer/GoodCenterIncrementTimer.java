package com.aspire.dotcard.syncGoodsCenter.timer;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncGoodsCenter.task.GoodsCenterIncrementTask;

/**
 * <p>商品中心数据同步定时器</p>
 */
public class GoodCenterIncrementTimer
{
    /**
     * 日志对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(GoodCenterIncrementTimer.class);
    /**
     * 启动定时任务
     *
     */
    public static void start()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("GoodCenterIncrementTimer start()");
        }
        //从配置项中得到任务的启动时间
//        String syncDataTime =ConfigFactory.getSystemConfig().getModuleConfig("goodscenterConfig")
//                                      .getItem("syncDataTime").getValue();
//        //得到启动的小时和分钟
//        int syncDataHours = Integer.parseInt(syncDataTime.substring(0,2));
//        int syncDataMin = Integer.parseInt(syncDataTime.substring(2,4));
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("syncDataHours="+syncDataHours+";syncDataMin="+syncDataMin);
//        }
//        //得到long类型一天的间隔周期
//        Date date = new Date();
//        int hours = date.getHours();
//        int minitue = date.getMinutes();
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("now hourse is "+hours);
//        }
//        date.setHours(syncDataHours);
//        date.setMinutes(syncDataMin);
//        date.setSeconds(0);
//        if(hours > syncDataHours)
//        {
//            date.setDate(date.getDate()+1);
//        }
//        //如果小时相等则判断分钟
//        if(hours == syncDataHours)
//        {
//            if(minitue>syncDataMin)
//            {
//                date.setDate(date.getDate()+1);
//            }
//        }
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("start task date is "+date);
//        }
        //得到间隔周期
        long period = 60 * 60 * 1000;
        //调用Timer类的schedule方法启动定时任务。
        Timer timer = new Timer(true);
//        timer.schedule(new GoodsCenterTask(),new Date(),period);
        timer.schedule(new GoodsCenterIncrementTask(),5 * 60 * 1000,period);
        
    }
    
    public static void main(String[] args)
    {
        Date date = new Date(System.currentTimeMillis());
        date.setDate(date.getDate()+20);
        date.setHours(4);
        date.setMinutes(0);
        System.out.println(date);
        start();
    }
}
