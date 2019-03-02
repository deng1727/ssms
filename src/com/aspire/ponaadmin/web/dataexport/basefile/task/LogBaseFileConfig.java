
package com.aspire.ponaadmin.web.dataexport.basefile.task;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class LogBaseFileConfig
{

    private static final JLogger logger = LoggerFactory.getLogger(LogBaseFileConfig.class);

    /**
     * �㽭MSTOREƽ̨�����ļ���ʱ���񴥷�ʱ�䡣
     */
    public static final String STARTTIME;

    /**
     * �����ʼ���ַ
     */
    public static final String mailTo[];
    
    /**
     * �Ļ�ͬ���ļ����·��
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
