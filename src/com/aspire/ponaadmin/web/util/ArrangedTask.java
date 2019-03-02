package com.aspire.ponaadmin.web.util;

import java.util.Calendar;
import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
/**
 * 待安排的任务任务task。子类需要继承该父类。
 * @author zhangwei
 *
 */
public abstract class ArrangedTask extends TimerTask
{
	private static final JLogger LOG = LoggerFactory.getLogger(ArrangedTask.class);
	
	public static final String FREQUENCY_DAY="day";
	public static final String FREQUENCY_WEEK="week";
	public String executeTime;
	
	

	private Calendar firstExecTime[];
	private String taskDesc;
	private String Frequency="day";//默认频率是按照天。
	private String startDayOfWeek="7";//只有在执行频率为周的情况下才有效

	public ArrangedTask()
	{
		try
		{
			init();//先初始化参数，保证任务可以正常执行。
			setFirstTriggerTime(executeTime);
		} catch (BOException e)
		{
			LOG.error("初始化任务出错。无法实现该定时任务。taskDesc="+taskDesc,e);
		}
	}
	/**
	 * 需要初始化的字段有：<br>
	 * <b>开始执行时间</b>:格式为 HH:MM(24小时制)，支持安排多个开始执行时间，按照英文分号";"分隔
	 *              比如一个任务需要每天的1点和16点执行两次，配置为：01:00;16:00<br>
	 * <b>任务描述</b>:表述本次任务功能。该字段是可选的<br>
	 * <b>执行频率</b>:默认频率为天，如果为周，还需要设置开始日期(1--7)，比如周日，默认是周日(7)
	 */
	public abstract void init()throws BOException;
	/**
	 * 
	 */
	public  void run()
	{
		LOG.info("开始执行任务："+taskDesc);
		try
		{
			doTask();
		} catch (Throwable e)
		{
			LOG.error(e);
		}
	}
	/**
	 * 具体任务执行方法。
	 */
	public abstract void doTask();
	/**
	 * 获取第一次触发的执行时间。
	 * @param executeTime
	 * @throws BOException
	 */
	private void setFirstTriggerTime(String executeTime) throws BOException
	{
		//如果是一天执行多次，多要特殊处理一下。		
		String temp[] = executeTime.split(";");
		firstExecTime = new Calendar[temp.length];
		for (int i = 0; i < temp.length; i++)
		{
			firstExecTime[i] = getFirstTriggerTime(temp[i]);
		}
		
	}
	/**
	 * 获取第一次触发的执行时间。
	 * @param executeTime
	 * @throws BOException
	 */
	private Calendar getFirstTriggerTime(String executeTime) throws BOException
	{
		int hour, minute;
		try
		{
			hour = Integer.parseInt(executeTime.split(":")[0]);
			minute = Integer.parseInt(executeTime.split(":")[1]);
			if (hour < 0 || hour > 23 || minute < 0 || minute > 59)
			{
				throw new IllegalArgumentException("时间格式错误！");
			}

		} catch (Exception e)
		{
			//LOG.error("解析任务为" + taskDesc + "开始执行时间出错，格式错误："
					//+ executeTime);
			throw new BOException("任务：" + taskDesc + ",初始化时间出错，startTime:"
					+ executeTime,e);
		}
		//第一次执行时间
		Calendar execTime = Calendar.getInstance();
		Calendar curTime = Calendar.getInstance();

		if (curTime.get(Calendar.HOUR_OF_DAY) > hour)
		{
			execTime.add(Calendar.DAY_OF_MONTH, 1);
		}
		else if (curTime.get(Calendar.HOUR_OF_DAY) == hour)
		{
			if (curTime.get(Calendar.MINUTE) >= minute)
			{
				execTime.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		//设置执行时间
		execTime.set(Calendar.HOUR_OF_DAY, hour);
		execTime.set(Calendar.MINUTE, minute);
		execTime.set(Calendar.SECOND, 0);
		execTime.set(Calendar.MILLISECOND, 0);
		return execTime;
	}
	public Calendar[] getFirstTriggerTime()
	{
		return firstExecTime;
	}
	public String getTaskDesc()
	{
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc)
	{
		this.taskDesc = taskDesc;
	}
	public String getFrequency()
	{
		return Frequency;
	}
	public void setFrequency(String frequency)
	{
		Frequency = frequency;
	}
	/**
	 * 
	 * @return
	 */
	public String getStartDayOfWeek()
	{
		return startDayOfWeek;
	}
	public void setStartDayOfWeek(String startDay)
	{
		this.startDayOfWeek = startDay;
	}
/*	public static String getExecuteTime()
	{
		return executeTime;
	}*/
	public void setExecuteTime(String executeTime)
	{
		this.executeTime = executeTime;
	}

}
