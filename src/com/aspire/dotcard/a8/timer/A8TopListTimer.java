package com.aspire.dotcard.a8.timer;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.a8.A8ParameterConfig;
import com.aspire.dotcard.a8.task.A8TopListImportTask;

public class A8TopListTimer
{
	private static final JLogger logger = LoggerFactory.getLogger(A8TopListTimer.class);

	 /**
    * ��ʼ����������timer
    */
   public static void start ()
   {
       //ϵͳĬ�ϵĲ���ͬ��ִ��ʱ�� 5:00
       int hour = 5;
       int minute = 0;
       //���������л�ȡ����ֵ
       try
       {
           String SynStartTime = A8ParameterConfig.TopListSynStartTime ;
           hour = Integer.parseInt(SynStartTime.split(":")[0]);
           minute = Integer.parseInt(SynStartTime.split(":")[1]);
       }
       catch (Exception ex)
       {
           logger.error("���������л�ȡ����ͬ����ʱ��ColorringSynStartTime��������");
       }
       if(hour<0||hour>23)
       {
           //����ֵ����ȷ����ʹ��Ĭ��ֵ
           hour = 5 ;
       }
       if(minute<0||minute>59)
       {
           //����ֵ����ȷ����ʹ��Ĭ��ֵ
           minute = 0 ;
       }

       //task�ļ��ʱ��
       long taskIntervalMS;
       //ֻ���������Ƶ������Ϊweek��ʱ��ű�ʾ�������Ϊ��
       if("week".equals(A8ParameterConfig.TopListSynFrequency))
       {
       	taskIntervalMS=(long)(7*24 * 60 * 60 * 1000);
       }else
       {
       	taskIntervalMS=(long) (24 * 60 * 60 * 1000) ;
       }
        
       Timer timer = new Timer(true) ;
    	 //��һ��ִ��ʱ��
   		Calendar firstExecTime=Calendar.getInstance();
   		Calendar curTime=Calendar.getInstance();
   		//�����ǰʱ���ڼƻ�ִ��ʱ��֮��͵��ڶ��쿪ʼִ�е�һ�β���

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
   		//����ִ��ʱ��
   		firstExecTime.set(Calendar.HOUR_OF_DAY, hour);
   		firstExecTime.set(Calendar.MINUTE, minute);
   		firstExecTime.set(Calendar.SECOND, 0);
   		firstExecTime.set(Calendar.MILLISECOND, 0);
       timer.schedule(new A8TopListImportTask(), firstExecTime.getTime(), taskIntervalMS) ;
       if (logger.isDebugEnabled())
       {
           logger.debug("schedule a A8TopListImportTask,first start time is:" + firstExecTime) ;
       }
   }


}
