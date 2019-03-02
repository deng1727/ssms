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
     * ��־����
     */
    JLogger logger = LoggerFactory.getLogger(ServiceSyncThread.class);
	/**
	 * ��¼�ϴ�ͬ��ʱ��
	 */
	public static Date lastSyncDate;
	/**
	 * �Ƿ�ֹͣ��ѵ,Ҳ����Ҫ����ͬ���ˡ�
	 */
	public boolean isStopped;
	/**
	 * ��ѵ���ʱ��
	 */
	public int interval=0;
	/**
	 * ������ѵ����
	 * @param interval  ��ѵ���ʱ�䣬��λΪ��
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
            // ���ÿ�ʼСʱ
            start.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s[0]));
            // ���ÿ�ʼ����
            start.set(Calendar.MINUTE, Integer.parseInt(s[1]));

            end = Calendar.getInstance();
            // ���ý���Сʱ
            end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(e[0]));
            // ���ý�������
            end.set(Calendar.MINUTE, Integer.parseInt(e[1]));
            
            // ������õ��ǿ���ʱ��
            if ("true".equals(isNext))
            {
                // ���ڿ�ʼʱ��Ϳ�ִ��
                if(start.before(Calendar.getInstance()))
                {
                    isExe = true;
                }
                // ��С�ڿ�ʼʱ�䡣�ж��Ƿ�ҲС�ڿ����Ľ���ʱ��
                else
                {
                    // С�ڽ���ʱ��������Ϊǰһ��Ľ���ʱ��
                    if(end.after(Calendar.getInstance()))
                    {
                        isExe = true;
                    }
                }
            }
            else
            {
                // ���ڿ�ʼʱ�䡣С�ڽ���ʱ�䡣Ϊ��ִ��
                if (start.before(Calendar.getInstance()) && end.after(Calendar.getInstance()))
                {
                    isExe = true;
                }
            }
            
            // �Ƿ�ִ��
            if(!isExe)
            {
                Thread.sleep(interval);
                
                continue;
            }
            
			if(lastSyncDate==null)//��һ�β�ѯ
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
			logger.error("��ѵ�������",e);
		}
	}
	/**
	 * ������ѵ���ʱ�䡣��λ����
	 * @param seconds
	 */
	private void setInterval(int seconds)
	{
		this.interval=seconds*1000;
	}
	/**
	 * ֹͣ��ѵ����
	 */
	public void stopTask()
	{
		isStopped=true;
	}

}
