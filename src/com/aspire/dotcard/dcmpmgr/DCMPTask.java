package com.aspire.dotcard.dcmpmgr;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * 自动触发ftp DCMP的任务。系统要保证执行时间在当天DCMP服务器产生文件之后执行。如果触发该定时任务的时间在设定的定时执行时间之后，第一次执行同步操作的时间
 * 从第二天开始执行。反之，在当天就开始执行。
 * @author zhangwei
 *
 */
public class DCMPTask extends TimerTask
{
	JLogger logger=LoggerFactory.getLogger(DCMPTask.class);
	
	public void start()
	{
        if(logger.isDebugEnabled())
		{
            logger.debug("从DCMP服务器执行ftp定时任务开始");
        }
		String startTime=FTPDCMPConfig.getInstance().getStartTime();
		String tempTime[]=startTime.split(":");
		int startHour=Integer.parseInt(tempTime[0]);
		int startMinute=Integer.parseInt(tempTime[1]);
		
		//第一次执行时间
		Calendar firstExecTime=Calendar.getInstance();
		Calendar curTime=Calendar.getInstance();
		//如果当前时间在计划执行时间之后就到第二天开始执行第一次操作

		if(curTime.get(Calendar.HOUR_OF_DAY)>startHour)
		{
			firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
		}else if(curTime.get(Calendar.HOUR_OF_DAY)==startHour)
		{
		    if(curTime.get(Calendar.MINUTE)>=startMinute)
		    {
		        firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
		    }
		}
		//设置执行时间
		firstExecTime.set(Calendar.HOUR_OF_DAY, startHour);
		firstExecTime.set(Calendar.MINUTE, startMinute);
		firstExecTime.set(Calendar.SECOND, 0);
		firstExecTime.set(Calendar.MILLISECOND, 0);
        if(logger.isDebugEnabled())
		{
            logger.debug("DCMP服务器执行ftp定时任务第一次执行时间为："+firstExecTime.getTime());
        }
		//date.HOUR_OF_DAY=time;
		//date.get
		//得到间隔周期
        long period = 24 * 60 * 60 * 1000;
        //调用Timer类的schedule方法启动定时任务。
        Timer timer = new Timer(true);
        timer.schedule(new DCMPTask(),firstExecTime.getTime(),period);
	}

	public void run()
	{
		
		FTPDCMPBO.syncNewsFromDCMP();
	}
	
	public static void main(String arg[])
	{
		
	}


}
