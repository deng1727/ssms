package com.aspire.ponaadmin.web.util;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * 公共安排task的定时器。
 * @author zhangwei
 *
 */
public class TaskArrangeTimer
{
	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(TaskArrangeTimer.class);
	private static final long DayIntervalLength = (long) (24 * 60 * 60 * 1000);
	private static final long WeekIntervalLength = DayIntervalLength * 7;

	/**
	 * 安排定时任务类，目前只支持按照天，和周等固定日期执行。
	 * @param task
	 * @throws BOException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void arrangeTask(ArrangedTask task) throws BOException, InstantiationException, IllegalAccessException
	{
		Timer timer = new Timer();
		Calendar[] firstExecTime = task.getFirstTriggerTime();
		if (ArrangedTask.FREQUENCY_DAY.equals(task.getFrequency()))
		{
			for(int i=0;i<firstExecTime.length;i++)
			{
				if(i>0)//同一个任务对象不能被安排两次
				{
					 task=(ArrangedTask)(task.getClass().newInstance());
				}
				timer.scheduleAtFixedRate(task, firstExecTime[i].getTime(), DayIntervalLength);
			}
			
		}else if (ArrangedTask.FREQUENCY_WEEK.equals(task.getFrequency()))
		{
			for(int i=0;i<firstExecTime.length;i++)
			{
				if(i>0)//同一个任务对象不能被安排两次
				{
					 task=(ArrangedTask)(task.getClass().newInstance());
				}
				getFirstStartDayOfWeek(firstExecTime[i], task.getStartDayOfWeek());
				timer.schedule(task, firstExecTime[i].getTime(), WeekIntervalLength);
			}
			
		}else
		{
			throw new BOException("目前系统不支持该任务频率："+task.getFrequency());
		}
		for(int i=0;i<firstExecTime.length;i++)
		{

			LOG.info("schedule a task timer:"
					+ task.getTaskDesc()
					+ ",first start time is:"
					+ PublicUtil.getDateString(firstExecTime[i].getTime(),
							"yyyy-MM-dd HH:mm:ss") + ",frequency is:"
					+ task.getFrequency());
		}
			
		

	}

	/**
	 * 获取以周为间隔的任务第一次开始执行的日期
	 * @param firstExecTime 
	 * @param startDay 一周的第几天执行，比如第一天表示周一。
	 * @return
	 */
	private static Calendar getFirstStartDayOfWeek(Calendar firstExecTime, String start)
			throws BOException
	{
		if (!start.matches("[1-7]"))
		{
			throw new BOException("同步日期格式有误，startDay只能是1-7的数字，startDay=" + start);
		}
		int startDay = Integer.parseInt(start);
		startDay = (startDay + 1) % 7;//系统默认周日作为一周的第一天，跟我们习惯不同
		int dayOfWeek = firstExecTime.get(Calendar.DAY_OF_WEEK);
		if (startDay > dayOfWeek)//允许本周开始执行
		{
			firstExecTime.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
			firstExecTime.add(Calendar.DAY_OF_WEEK, startDay);
		}
		else
		//下周开始执行
		{
			firstExecTime.add(Calendar.DAY_OF_WEEK, 7 - dayOfWeek);
			firstExecTime.add(Calendar.DAY_OF_WEEK, startDay);
		}

		return firstExecTime;
	}

}
