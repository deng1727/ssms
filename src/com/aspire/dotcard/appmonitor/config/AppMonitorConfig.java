package com.aspire.dotcard.appmonitor.config;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class AppMonitorConfig {

	private static final JLogger logger = LoggerFactory.getLogger(AppMonitorConfig.class);

    /**
     * 重点应用监控 触发时间。
     */
    public static final String STARTTIME;

    /**
    * 重点应用监控 邮件发送人。
    */
   public static final String[] MAILTO;
    
   /**
    * 重点应用监控 邮件抄送人。
    */
   public static final String[] MAILCC;

   /**
    * 重点应用监控发送邮件标题。
    */
   public static final String mailTitle;
   
   /**
    * 重点应用监控发送邮件内容。
    */
   public static final String mailContent;
   
   /**
    * 数据中心（客户端门户）监控url地址。
    */
   public static final String DataCenterUrl;
   
   /**
    * 搜索系统监控url地址。
    */
   public static final String MMSearchUrl;
   
   /**
    * 线程执行最大条数
    */
   public static final int taskRunnerNum;
   
   /**
    * 任务队列最大容量。0表示不限制容量
    */
   public static final int taskMaxReceivedNum;
   
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("pivotAppMonitor");
        
        STARTTIME = module.getItemValue("startTime").trim();
        
        MAILTO = module.getItemValue("mailTo").trim().split(",");
       
        MAILCC = module.getItemValue("mailCc").trim().split(",");
        mailTitle = module.getItemValue("mailTitle").trim();
        mailContent = module.getItemValue("mailContent").trim();
        DataCenterUrl = module.getItemValue("DataCenterUrl").trim();
        MMSearchUrl = module.getItemValue("MMSearchUrl").trim();
        taskRunnerNum = Integer.parseInt(module.getItemValue("taskRunnerNum").trim());
        
        taskMaxReceivedNum = Integer.parseInt(module.getItemValue("taskMaxReceivedNum").trim());
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the mailTo is " + MAILTO);
            logger.debug("the mailCc is " + MAILCC);
            logger.debug("the mailTitle is " + mailTitle);
            logger.debug("the mailContent is " + mailContent);
        }
    }
}
