package com.aspire.dotcard.cysyncdata.task;

import java.util.Date;
import java.util.Timer;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.system.Config;

/**
 * <p>����ͬ����ʱ��</p>
 * <p>Copyright (c) 2003-2007 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CYDataSyncTimer
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(CYDataSyncTimer.class);
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
        String syncDataTime = Config.getInstance()
                                      .getModuleConfig()
                                      .getItemValue("CYsyncDataTime");
        //�õ�������Сʱ�ͷ���
        int syncDataHours = Integer.parseInt(syncDataTime.substring(0,2));
        int syncDataMin = Integer.parseInt(syncDataTime.substring(2,4));
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
        Timer cytimer = new Timer(true);
        cytimer.schedule(new CYDataSyncTask(),date,period);
    }
    
    public static void main(String[] args)
    {
        Date date = new Date(System.currentTimeMillis());
        date.setDate(date.getDate()+20);
        date.setHours(4);
        date.setMinutes(0);
        System.out.println(date);
        start();
    }
}
