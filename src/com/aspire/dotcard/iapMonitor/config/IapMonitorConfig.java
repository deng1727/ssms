package com.aspire.dotcard.iapMonitor.config;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class IapMonitorConfig {

	private static final JLogger logger = LoggerFactory.getLogger(IapMonitorConfig.class);

    /**
     * ÿ�����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������ ����ʱ�䡣
     */
    public static final String STARTTIME;

    
    /**
    * ÿ�����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������ �ʼ������ˡ�
    */
   public static final String[] MAILTO;
    
   /**
    * ָ������������ָ�������������ʼ�֪ͨ��
    */
   public static final int countNum;
   
   /**
    * 9���Ż������ܱ���ID��
    */
   public static final String categoryId1;
   
   /**
    * 9���Ż����ص�ϵ�л���ID��
    */
   public static final String deviceId1;
   
   /**
    * MM�����������ܱ��� ID��
    */
   public static final String categoryId2;
   
   /**
    * MM���������ص�ϵ�л���ID��
    */
   public static final String deviceId2;
   
   /**
    * 9���Ż������ܷ����ʼ����⡣
    */
   public static final String mailTitle1;
   
   /**
    * MM�����������ܷ����ʼ����⡣
    */
   public static final String mailTitle2;
   
   /**
    * 9���Ż������ܷ����ʼ����ݡ�
    */
   public static final String mailContent1;
   
   /**
    * MM�����������ܷ����ʼ����ݡ�
    */
   public static final String mailContent2;
   
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("iapSendMail");
        
        STARTTIME = module.getItemValue("startTime").trim();
        
        MAILTO = module.getItemValue("mailTo").trim().split(",");
        
        categoryId1 = module.getItemValue("categoryId1").trim();
        
        deviceId1 = module.getItemValue("deviceId1").trim();
        
        categoryId2 = module.getItemValue("categoryId2").trim();
        
        deviceId2 = module.getItemValue("deviceId2").trim();
        
        mailTitle1 = module.getItemValue("mailTitle1").trim();
        
        mailTitle2 = module.getItemValue("mailTitle2").trim();
        
        mailContent1 = module.getItemValue("mailContent1").trim();
        
        mailContent2 = module.getItemValue("mailContent2").trim(); 
        
        countNum = Integer.parseInt(module.getItemValue("countNum").trim());
        
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the mailTo is " + module.getItemValue("mailTo"));
            logger.debug("the categoryId1 is " + categoryId1);
            logger.debug("the deviceId1 is " + deviceId1);
            logger.debug("the categoryId2 is " + categoryId2);
            logger.debug("the deviceId2 is " + deviceId2);
            logger.debug("the mailTitle1 is " + mailTitle1);
            logger.debug("the mailTitle2 is " + mailTitle2);
            logger.debug("the mailContent1 is " + mailContent1);
            logger.debug("the mailContent2 is " + mailContent2);
            logger.debug("the countNum is " + countNum);
        }
    }
}
