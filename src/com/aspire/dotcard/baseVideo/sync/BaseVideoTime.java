/*
 * �ļ�����BaseVideoTime.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.dotcard.baseVideo.sync;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version 
 */
public class BaseVideoTime
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseVideoTime.class);
    /**
     * ������ʱ����
     *
     */
    public static void start()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("start()");
        }
        //���������еõ����������ʱ��
        String syncDataTime = BaseVideoConfig.STARTTIME;
        //�õ�������Сʱ�ͷ���
        int syncDataHours = Integer.parseInt(syncDataTime.substring(0,2));
        int syncDataMin = Integer.parseInt(syncDataTime.substring(3,5));
        if(logger.isDebugEnabled())
        {
            logger.debug("syncDataHours="+syncDataHours+";syncDataMin="+syncDataMin);
        }
        
        //�õ�long����һ��ļ������
        Date date = new Date();
        int hours = date.getHours();
        int minitue = date.getMinutes();
        if(logger.isDebugEnabled())
        {
            logger.debug("now hourse is "+hours);
        }
        date.setHours(syncDataHours);
        date.setMinutes(syncDataMin);
        date.setSeconds(0);
        if(hours > syncDataHours)
        {
            date.setDate(date.getDate()+1);
        }
        //���Сʱ������жϷ���
        if(hours == syncDataHours)
        {
            if(minitue>syncDataMin)
            {
                date.setDate(date.getDate()+1);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("start task date is "+date);
        }
        //�õ��������
        long period = 24 * 60 * 60 * 1000;
        //����Timer���schedule����������ʱ����
        Timer timer = new Timer(true);
        timer.schedule(new BaseVideoTask(),date,period);
    }
}
