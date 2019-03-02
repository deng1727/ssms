package com.aspire.dotcard.a8.timer;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.a8.A8ParameterConfig;
import com.aspire.dotcard.a8.task.A8MusicImportTask;


public class A8MusicImportTimer
{
	private static final JLogger logger = LoggerFactory.getLogger(A8MusicImportTimer.class);

	 /**
     * 初始化方法启动timer
     */
    public static void start ()
    {
        //系统默认的彩铃同步执行时间 5:00
        int hour = 5;
        int minute = 0;
        //从配置项中获取配置值
        try
        {
            String SynStartTime = A8ParameterConfig.MusicSynStartTime ;
            hour = Integer.parseInt(SynStartTime.split(":")[0]);
            minute = Integer.parseInt(SynStartTime.split(":")[1]);
        }
        catch (Exception ex)
        {
            logger.error("从配置项中获取彩铃同步的时间ColorringSynStartTime解析出错！");
        }
        if(hour<0||hour>23)
        {
            //配置值不正确，就使用默认值
            hour = 5 ;
        }
        if(minute<0||minute>59)
        {
            //配置值不正确，就使用默认值
            minute = 0 ;
        }

        //task的间隔时间
        long taskIntervalMS;
        //只有配置项的频率设置为week的时候才表示间隔周期为周
        if("week".equals(A8ParameterConfig.MusicSynFrequency))
        {
        	taskIntervalMS=(long)(7*24 * 60 * 60 * 1000);
        }else
        {
        	taskIntervalMS=(long) (24 * 60 * 60 * 1000) ;
        }
         
        Timer timer = new Timer(true) ;
        //第一次执行时间
        Calendar firstExecTime=Calendar.getInstance();
        Calendar curTime=Calendar.getInstance();
        //如果当前时间在计划执行时间之后就到第二天开始执行第一次操作

        if(curTime.get(Calendar.HOUR_OF_DAY)>hour)
        {
        	firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
        }else if(curTime.get(Calendar.HOUR_OF_DAY)==hour)
        {
        	if(curTime.get(Calendar.MINUTE)>=minute)
        	{
        		firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
        	}
        }
        //设置执行时间
        firstExecTime.set(Calendar.HOUR_OF_DAY, hour);
        firstExecTime.set(Calendar.MINUTE, minute);
        firstExecTime.set(Calendar.SECOND, 0);
        firstExecTime.set(Calendar.MILLISECOND, 0);
        timer.schedule(new A8MusicImportTask(), firstExecTime.getTime(), taskIntervalMS) ;
        if (logger.isDebugEnabled())
        {
            logger.debug("schedule a A8MusicImportTask,first start time is:" +firstExecTime ) ;
        }
    }

//    public static void main(String[] args)
//    {
//        try
//        {
//            Date date = new Date();
//            Date firstStartTime = getOneTimeByHourAndMinute(date,3,0);
//            System.out.println(firstStartTime) ;
//            if(firstStartTime.before(date))
//            {
//                Calendar calendar = Calendar.getInstance();               
//                calendar.add(Calendar.DAY_OF_YEAR, 1);
//                Date tommorrow = calendar.getTime();                
//                firstStartTime = getOneTimeByHourAndMinute(tommorrow,3,0);
//                System.out.println("firstStartTime===="+firstStartTime) ;
//            }            
//        }
//        catch (Exception ex)
//        {
//
//        }
//    }

}
