package com.aspire.ponaadmin.web.util;

import java.util.Calendar;
import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
/**
 * �����ŵ���������task��������Ҫ�̳иø��ࡣ
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
	private String Frequency="day";//Ĭ��Ƶ���ǰ����졣
	private String startDayOfWeek="7";//ֻ����ִ��Ƶ��Ϊ�ܵ�����²���Ч

	public ArrangedTask()
	{
		try
		{
			init();//�ȳ�ʼ����������֤�����������ִ�С�
			setFirstTriggerTime(executeTime);
		} catch (BOException e)
		{
			LOG.error("��ʼ����������޷�ʵ�ָö�ʱ����taskDesc="+taskDesc,e);
		}
	}
	/**
	 * ��Ҫ��ʼ�����ֶ��У�<br>
	 * <b>��ʼִ��ʱ��</b>:��ʽΪ HH:MM(24Сʱ��)��֧�ְ��Ŷ����ʼִ��ʱ�䣬����Ӣ�ķֺ�";"�ָ�
	 *              ����һ��������Ҫÿ���1���16��ִ�����Σ�����Ϊ��01:00;16:00<br>
	 * <b>��������</b>:�������������ܡ����ֶ��ǿ�ѡ��<br>
	 * <b>ִ��Ƶ��</b>:Ĭ��Ƶ��Ϊ�죬���Ϊ�ܣ�����Ҫ���ÿ�ʼ����(1--7)���������գ�Ĭ��������(7)
	 */
	public abstract void init()throws BOException;
	/**
	 * 
	 */
	public  void run()
	{
		LOG.info("��ʼִ������"+taskDesc);
		try
		{
			doTask();
		} catch (Throwable e)
		{
			LOG.error(e);
		}
	}
	/**
	 * ��������ִ�з�����
	 */
	public abstract void doTask();
	/**
	 * ��ȡ��һ�δ�����ִ��ʱ�䡣
	 * @param executeTime
	 * @throws BOException
	 */
	private void setFirstTriggerTime(String executeTime) throws BOException
	{
		//�����һ��ִ�ж�Σ���Ҫ���⴦��һ�¡�		
		String temp[] = executeTime.split(";");
		firstExecTime = new Calendar[temp.length];
		for (int i = 0; i < temp.length; i++)
		{
			firstExecTime[i] = getFirstTriggerTime(temp[i]);
		}
		
	}
	/**
	 * ��ȡ��һ�δ�����ִ��ʱ�䡣
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
				throw new IllegalArgumentException("ʱ���ʽ����");
			}

		} catch (Exception e)
		{
			//LOG.error("��������Ϊ" + taskDesc + "��ʼִ��ʱ�������ʽ����"
					//+ executeTime);
			throw new BOException("����" + taskDesc + ",��ʼ��ʱ�����startTime:"
					+ executeTime,e);
		}
		//��һ��ִ��ʱ��
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
		//����ִ��ʱ��
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
