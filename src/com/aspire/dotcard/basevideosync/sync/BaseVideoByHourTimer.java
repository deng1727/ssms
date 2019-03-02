package com.aspire.dotcard.basevideosync.sync;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;

public class BaseVideoByHourTimer {

	/**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseVideoByHourTimer.class);
    /**
     * ������ʱ����
     *
     */
    public static void start()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("basevideosync.sync.BaseVideoByHourTimer.start()");
        }
        //���������еõ����������ʱ��
        String syncDataTime = BaseVideoConfig.STARTTIME_MINUTES;
        //�õ�������Сʱ�ͷ���
        int syncDataMin = Integer.parseInt(syncDataTime);
        if(logger.isDebugEnabled())
        {
            logger.debug("syncDataMin="+syncDataMin);
        }
        
        //�õ�long����һ��ļ������
        Date date = new Date();
        int hours = date.getHours();
        int minitue = date.getMinutes();
        if(logger.isDebugEnabled())
        {
            logger.debug("now hourse is "+hours);
        }
        date.setMinutes(syncDataMin);
        date.setSeconds(0);
        if(minitue > syncDataMin)
        {
            date.setHours(date.getHours()+1);
        }
       
        if(logger.isDebugEnabled())
        {
            logger.debug("start task date is "+date);
        }
        //�õ��������
        long period = 1 * 60 * 60 * 1000;
        //����Timer���schedule����������ʱ����
        Timer timer = new Timer(true);
        timer.schedule(new BaseVideoByHourTask(),date,period);
    }
}
