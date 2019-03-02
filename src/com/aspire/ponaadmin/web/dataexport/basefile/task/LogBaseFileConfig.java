
package com.aspire.ponaadmin.web.dataexport.basefile.task;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class LogBaseFileConfig
{

    private static final JLogger logger = LoggerFactory.getLogger(LogBaseFileConfig.class);

    /**
     * 浙江MSTORE平台生成文件定时任务触发时间。
     */
    public static final String STARTTIME;

    /**
     * 发送邮件地址
     */
    public static final String mailTo[];
    
    /**
     * 心机同步文件存放路径
     */
    public static final String FTPPAHT;
    
    
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("BaseFileConfig");
        
        STARTTIME = module.getItemValue("logFileStartTime").trim();

        mailTo = module.getItemValue("logFilemailTo").trim().split(",");
        
        FTPPAHT = module.getItemValue("logFTPPath").trim();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the mailTo is " + module.getItemValue("mailTo"));
        }
    }

}
