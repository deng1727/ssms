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
            String SynStartTime = A8ParameterConfig.MusicSynStartTime ;
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
        if("week".equals(A8ParameterConfig.MusicSynFrequency))
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
