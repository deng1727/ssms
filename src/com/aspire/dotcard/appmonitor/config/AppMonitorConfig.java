package com.aspire.dotcard.appmonitor.config;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class AppMonitorConfig {

	private static final JLogger logger = LoggerFactory.getLogger(AppMonitorConfig.class);

    /**
     * �ص�Ӧ�ü�� ����ʱ�䡣
     */
    public static final String STARTTIME;

    /**
    * �ص�Ӧ�ü�� �ʼ������ˡ�
    */
   public static final String[] MAILTO;
    
   /**
    * �ص�Ӧ�ü�� �ʼ������ˡ�
    */
   public static final String[] MAILCC;

   /**
    * �ص�Ӧ�ü�ط����ʼ����⡣
    */
   public static final String mailTitle;
   
   /**
    * �ص�Ӧ�ü�ط����ʼ����ݡ�
    */
   public static final String mailContent;
   
   /**
    * �������ģ��ͻ����Ż������url��ַ��
    */
   public static final String DataCenterUrl;
   
   /**
    * ����ϵͳ���url��ַ��
    */
   public static final String MMSearchUrl;
   
   /**
    * �߳�ִ���������
    */
   public static final int taskRunnerNum;
   
   /**
    * ����������������0��ʾ����������
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
