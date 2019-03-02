package com.aspire.dotcard.dcmpmgr;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * �Զ�����ftp DCMP������ϵͳҪ��ִ֤��ʱ���ڵ���DCMP�����������ļ�֮��ִ�С���������ö�ʱ�����ʱ�����趨�Ķ�ʱִ��ʱ��֮�󣬵�һ��ִ��ͬ��������ʱ��
 * �ӵڶ��쿪ʼִ�С���֮���ڵ���Ϳ�ʼִ�С�
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
            logger.debug("��DCMP������ִ��ftp��ʱ����ʼ");
        }
		String startTime=FTPDCMPConfig.getInstance().getStartTime();
		String tempTime[]=startTime.split(":");
		int startHour=Integer.parseInt(tempTime[0]);
		int startMinute=Integer.parseInt(tempTime[1]);
		
		//��һ��ִ��ʱ��
		Calendar firstExecTime=Calendar.getInstance();
		Calendar curTime=Calendar.getInstance();
		//�����ǰʱ���ڼƻ�ִ��ʱ��֮��͵��ڶ��쿪ʼִ�е�һ�β���

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
		//����ִ��ʱ��
		firstExecTime.set(Calendar.HOUR_OF_DAY, startHour);
		firstExecTime.set(Calendar.MINUTE, startMinute);
		firstExecTime.set(Calendar.SECOND, 0);
		firstExecTime.set(Calendar.MILLISECOND, 0);
        if(logger.isDebugEnabled())
		{
            logger.debug("DCMP������ִ��ftp��ʱ�����һ��ִ��ʱ��Ϊ��"+firstExecTime.getTime());
        }
		//date.HOUR_OF_DAY=time;
		//date.get
		//�õ��������
        long period = 24 * 60 * 60 * 1000;
        //����Timer���schedule����������ʱ����
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
