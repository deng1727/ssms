/**
 *  <p>ZCOM����ͬ����ʱ��</p>
 * <p>Copyright (c) 2003-2010 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * SSMS
 * com.aspire.dotcard.synczcom.task ZcomDataSyncTimer.java
 * Apr 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.synczcom.task;

import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

import com.aspire.ponaadmin.web.system.Config;

/**
 * @author tungke
 *
 */
public class ZcomDataSyncTimer {

	 /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ZcomDataSyncTimer.class);
  
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
                                      // .getItemValue("zcomSyncDataTime");
                                      .getItemValue("syncDataTime");
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
       // date.setMinutes(syncDataMin);
        date.setMinutes(syncDataMin-5);//�ӻ�5������������MM����ͬ��������
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
        timer.schedule(new ZcomDataSyncTask(),date,period);
    }
}
