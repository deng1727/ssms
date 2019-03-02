
package com.aspire.dotcard.syncData.util;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.system.Config;

public class SyncDataConfig
{

    /**
     * �ʷѱ����ѵ�ļ��ʱ�䣬��λΪ��
     */
    public static int ServiceQueryInterval;

    /**
     * �ʷѱ����ѵ����ʼʱ��
     */
    public static String ServiceQueryIntervalStartTime;

    /**
     * �ʷѱ����ѵ�Ľ���ʱ��
     */
    public static String ServiceQueryIntervalEndTime;

    /**
     * �ʷѱ����ѵ�Ľ���ʱ���Ƿ��ǿ�����ʱ��
     */
    public static String ServiceQueryIntervalTimeIsNext;

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(SyncDataConfig.class);

    static
    {
        try
        {
            // ���������еõ����������ʱ��
            String interval = Config.getInstance()
                                    .getModuleConfig()
                                    .getItemValue("ServiceQueryInterval");
            ServiceQueryInterval = Integer.parseInt(interval);

            // ���������еõ����������ʱ��
            ServiceQueryIntervalStartTime = Config.getInstance()
                                                  .getModuleConfig()
                                                  .getItemValue("ServiceQueryIntervalStartTime");

            // ���������еõ�����Ľ���ʱ��
            ServiceQueryIntervalEndTime = Config.getInstance()
                                                .getModuleConfig()
                                                .getItemValue("ServiceQueryIntervalEndTime");

            // �ʷѱ����ѵ�Ľ���ʱ���Ƿ��ǿ�����ʱ��
            ServiceQueryIntervalTimeIsNext = Config.getInstance()
                                                   .getModuleConfig()
                                                   .getItemValue("ServiceQueryIntervalTimeIsNext");

        }
        catch (Exception e)
        {
            logger.error("�ʷѱ����ѵ�ļ��ʱ����������", e);
        }

    }

}
