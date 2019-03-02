
package com.aspire.ponaadmin.web.category.intervenor;


import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class IntervenorConfig
{

    private static final JLogger logger = LoggerFactory.getLogger(IntervenorConfig.class);

    /**
     * 人工干预 定时任务触发时间。
     */
    public static final String STARTTIME;
    
    /**
     * 人工干预最大同步多线程数量
     */
    public static final int INTERVENORMAXNUM;
   
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("intervenor");
        
        STARTTIME = module.getItemValue("startTime").trim();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
        }
        
        INTERVENORMAXNUM = Integer.valueOf(module.getItemValue("intervenorMaxNum")).intValue();

        if (logger.isDebugEnabled())
        {
            logger.debug("the intervenorMaxNum is " + INTERVENORMAXNUM);
        }
    }

}
