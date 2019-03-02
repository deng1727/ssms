package com.aspire.ponaadmin.web.util;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * ��������task�Ķ�ʱ����
 * @author zhangwei
 *
 */
public class TaskArrangeTimer
{
	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(TaskArrangeTimer.class);
	private static final long DayIntervalLength = (long) (24 * 60 * 60 * 1000);
	private static final long WeekIntervalLength = DayIntervalLength * 7;

	/**
	 * ���Ŷ�ʱ�����࣬Ŀǰֻ֧�ְ����죬���ܵȹ̶�����ִ�С�
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
				if(i>0)//ͬһ����������ܱ���������
				{
					 task=(ArrangedTask)(task.getClass().newInstance());
				}
				timer.scheduleAtFixedRate(task, firstExecTime[i].getTime(), DayIntervalLength);
			}
			
		}else if (ArrangedTask.FREQUENCY_WEEK.equals(task.getFrequency()))
		{
			for(int i=0;i<firstExecTime.length;i++)
			{
				if(i>0)//ͬһ����������ܱ���������
				{
					 task=(ArrangedTask)(task.getClass().newInstance());
				}
				getFirstStartDayOfWeek(firstExecTime[i], task.getStartDayOfWeek());
				timer.schedule(task, firstExecTime[i].getTime(), WeekIntervalLength);
			}
			
		}else
		{
			throw new BOException("Ŀǰϵͳ��֧�ָ�����Ƶ�ʣ�"+task.getFrequency());
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
	 * ��ȡ����Ϊ����������һ�ο�ʼִ�е�����
	 * @param firstExecTime 
	 * @param startDay һ�ܵĵڼ���ִ�У������һ���ʾ��һ��
	 * @return
	 */
	private static Calendar getFirstStartDayOfWeek(Calendar firstExecTime, String start)
			throws BOException
	{
		if (!start.matches("[1-7]"))
		{
			throw new BOException("ͬ�����ڸ�ʽ����startDayֻ����1-7�����֣�startDay=" + start);
		}
		int startDay = Integer.parseInt(start);
		startDay = (startDay + 1) % 7;//ϵͳĬ��������Ϊһ�ܵĵ�һ�죬������ϰ�߲�ͬ
		int dayOfWeek = firstExecTime.get(Calendar.DAY_OF_WEEK);
		if (startDay > dayOfWeek)//�����ܿ�ʼִ��
		{
			firstExecTime.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
			firstExecTime.add(Calendar.DAY_OF_WEEK, startDay);
		}
		else
		//���ܿ�ʼִ��
		{
			firstExecTime.add(Calendar.DAY_OF_WEEK, 7 - dayOfWeek);
			firstExecTime.add(Calendar.DAY_OF_WEEK, startDay);
		}

		return firstExecTime;
	}

}
