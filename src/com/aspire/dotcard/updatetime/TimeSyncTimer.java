package com.aspire.dotcard.updatetime;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.task.DataSyncTimer;
import com.aspire.ponaadmin.web.system.Config;

public class TimeSyncTimer {
	/**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(TimeSyncTimer.class);
    /**
     * ������ʱ����
     *
     */
    
    public static void start (){
    	if(logger.isDebugEnabled())
        {
            logger.debug("start()");
        }
        //���������еõ����������ʱ��
        String syncDataTime = Config.getInstance()
                                      .getModuleConfig()
                                      .getItemValue("syncDataTime");
        //�õ�������Сʱ�ͷ���
        int syncDataHours = Integer.parseInt(syncDataTime.substring(0,2)) + 2;
        int syncDataMin = Integer.parseInt(syncDataTime.substring(2,4));
        if(logger.isDebugEnabled())
        {
            logger.debug("syncDataHours="+syncDataHours+";syncDataMin="+syncDataMin);
        }
        //�õ�long����һ��ļ������
        
        Date firstTime = new Date();
        int hours = firstTime.getHours();
        int minitue = firstTime.getMinutes();
        if(logger.isDebugEnabled())
        {
            logger.debug("now hourse is "+hours);
        }
        firstTime.setHours(syncDataHours);
        firstTime.setMinutes(syncDataMin);
        firstTime.setSeconds(0);
        if(hours > syncDataHours)
        {
        	firstTime.setDate(firstTime.getDate()+1);
        }
        //���Сʱ������жϷ���
        if(hours == syncDataHours)
        {
            if(minitue>syncDataMin)
            {
            	firstTime.setDate(firstTime.getDate()+1);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("start task date is "+firstTime);
        //�õ��������
        long period = 24 * 60 * 60 * 1000;
        
        Timer timer = new Timer();
        timer.schedule(new TimeSyncTask(), firstTime, period);
        }
    }
}
