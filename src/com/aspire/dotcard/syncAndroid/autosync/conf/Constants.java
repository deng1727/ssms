
package com.aspire.dotcard.syncAndroid.autosync.conf;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class  Constants
{

    private static final JLogger logger = LoggerFactory.getLogger(Constants.class);

    /**
     * 发送商品库同步自动更新的货架 触发时间。
     */
    public static final String STARTTIME;

    /**
    * 发送商品库同步自动更新的货架 邮件发送人。
    */
   public static final String[] MAILTO;
    
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("autoSyncCategory");
        
        STARTTIME = module.getItemValue("startTime").trim();
        
        MAILTO = module.getItemValue("mailTo").trim().split(",");
        
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the mailTo is " + module.getItemValue("mailTo"));
        }
    }

}
