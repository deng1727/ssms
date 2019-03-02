package com.aspire.dotcard.syncGoodsCenter.timer;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncGoodsCenter.task.GoodsCenterIncrementTask;

/**
 * <p>��Ʒ��������ͬ����ʱ��</p>
 */
public class GoodCenterIncrementTimer
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(GoodCenterIncrementTimer.class);
    /**
     * ������ʱ����
     *
     */
    public static void start()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("GoodCenterIncrementTimer start()");
        }
        //���������еõ����������ʱ��
//        String syncDataTime =ConfigFactory.getSystemConfig().getModuleConfig("goodscenterConfig")
//                                      .getItem("syncDataTime").getValue();
//        //�õ�������Сʱ�ͷ���
//        int syncDataHours = Integer.parseInt(syncDataTime.substring(0,2));
//        int syncDataMin = Integer.parseInt(syncDataTime.substring(2,4));
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("syncDataHours="+syncDataHours+";syncDataMin="+syncDataMin);
//        }
//        //�õ�long����һ��ļ������
//        Date date = new Date();
//        int hours = date.getHours();
//        int minitue = date.getMinutes();
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("now hourse is "+hours);
//        }
//        date.setHours(syncDataHours);
//        date.setMinutes(syncDataMin);
//        date.setSeconds(0);
//        if(hours > syncDataHours)
//        {
//            date.setDate(date.getDate()+1);
//        }
//        //���Сʱ������жϷ���
//        if(hours == syncDataHours)
//        {
//            if(minitue>syncDataMin)
//            {
//                date.setDate(date.getDate()+1);
//            }
//        }
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("start task date is "+date);
//        }
        //�õ��������
        long period = 60 * 60 * 1000;
        //����Timer���schedule����������ʱ����
        Timer timer = new Timer(true);
//        timer.schedule(new GoodsCenterTask(),new Date(),period);
        timer.schedule(new GoodsCenterIncrementTask(),5 * 60 * 1000,period);
        
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
