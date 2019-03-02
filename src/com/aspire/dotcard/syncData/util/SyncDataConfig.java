
package com.aspire.dotcard.syncData.util;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.system.Config;

public class SyncDataConfig
{

    /**
     * 资费变更轮训的间隔时间，单位为秒
     */
    public static int ServiceQueryInterval;

    /**
     * 资费变更轮训的启始时间
     */
    public static String ServiceQueryIntervalStartTime;

    /**
     * 资费变更轮训的结束时间
     */
    public static String ServiceQueryIntervalEndTime;

    /**
     * 资费变更轮训的结束时间是否是跨天后的时间
     */
    public static String ServiceQueryIntervalTimeIsNext;

    /**
     * 日志对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(SyncDataConfig.class);

    static
    {
        try
        {
            // 从配置项中得到任务的启动时间
            String interval = Config.getInstance()
                                    .getModuleConfig()
                                    .getItemValue("ServiceQueryInterval");
            ServiceQueryInterval = Integer.parseInt(interval);

            // 从配置项中得到任务的启动时间
            ServiceQueryIntervalStartTime = Config.getInstance()
                                                  .getModuleConfig()
                                                  .getItemValue("ServiceQueryIntervalStartTime");

            // 从配置项中得到任务的结束时间
            ServiceQueryIntervalEndTime = Config.getInstance()
                                                .getModuleConfig()
                                                .getItemValue("ServiceQueryIntervalEndTime");

            // 资费变更轮训的结束时间是否是跨天后的时间
            ServiceQueryIntervalTimeIsNext = Config.getInstance()
                                                   .getModuleConfig()
                                                   .getItemValue("ServiceQueryIntervalTimeIsNext");

        }
        catch (Exception e)
        {
            logger.error("资费变更轮训的间隔时间配置有误", e);
        }

    }

}
