package com.aspire.dotcard.syncData.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.dao.ServiceSyncDAO;
import com.aspire.dotcard.syncData.util.SyncDataConfig;

public class ServiceSyncThread extends Thread
{
	/**
     * 日志引用
     */
    JLogger logger = LoggerFactory.getLogger(ServiceSyncThread.class);
	/**
	 * 记录上次同步时间
	 */
	public static Date lastSyncDate;
	/**
	 * 是否停止轮训,也不需要进行同步了。
	 */
	public boolean isStopped;
	/**
	 * 轮训间隔时间
	 */
	public int interval=0;
	/**
	 * 生成轮训进程
	 * @param interval  轮训间隔时间，单位为秒
	 */
	public ServiceSyncThread(int interval)
	{
		super();
		this.setInterval(interval);
	}
	
	public void doQurey()throws Exception
	{
        String isNext = SyncDataConfig.ServiceQueryIntervalTimeIsNext;
        String[] s = SyncDataConfig.ServiceQueryIntervalStartTime.split(":");
        String[] e = SyncDataConfig.ServiceQueryIntervalEndTime.split(":");
        
        boolean isExe;
        Calendar start;
        Calendar end;
        
		while(!isStopped)
		{
            isExe = false;
            
            start = Calendar.getInstance();
            // 设置开始小时
            start.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s[0]));
            // 设置开始分钟
            start.set(Calendar.MINUTE, Integer.parseInt(s[1]));

            end = Calendar.getInstance();
            // 设置结束小时
            end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(e[0]));
            // 设置结束分钟
            end.set(Calendar.MINUTE, Integer.parseInt(e[1]));
            
            // 如果设置的是跨天时间
            if ("true".equals(isNext))
            {
                // 大于开始时间就可执行
                if(start.before(Calendar.getInstance()))
                {
                    isExe = true;
                }
                // 如小于开始时间。判断是否也小于跨天后的结束时间
                else
                {
                    // 小于结束时间可以理解为前一天的结束时间
                    if(end.after(Calendar.getInstance()))
                    {
                        isExe = true;
                    }
                }
            }
            else
            {
                // 大于开始时间。小于结束时间。为可执行
                if (start.before(Calendar.getInstance()) && end.after(Calendar.getInstance()))
                {
                    isExe = true;
                }
            }
            
            // 是否执行
            if(!isExe)
            {
                Thread.sleep(interval);
                
                continue;
            }
            
			if(lastSyncDate==null)//第一次查询
			{
				lastSyncDate= ServiceSyncDAO.getInstance().firstUpdateAllMobilePrice();
			}
			Date curDate=new Date();
			List list=ServiceSyncDAO.getInstance().getFeeChangedServices(lastSyncDate,curDate);
			for(int i=0;i<list.size();i++)
			{
				ServiceSyncDAO.getInstance().syncOneRecord((String)list.get(i));
			}
			lastSyncDate=curDate;
			Thread.sleep(interval);
		}
	}
	public void run()
	{
		try
		{
			doQurey();
		}catch(InterruptedException e)
		{
			//
		}catch (Exception e)
		{
			logger.error("轮训程序出错。",e);
		}
	}
	/**
	 * 设置轮训间隔时间。单位是秒
	 * @param seconds
	 */
	private void setInterval(int seconds)
	{
		this.interval=seconds*1000;
	}
	/**
	 * 停止轮训程序
	 */
	public void stopTask()
	{
		isStopped=true;
	}

}
