
package com.aspire.ponaadmin.web.dataexport.basefile.task;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class BaseFileConfig
{

    private static final JLogger logger = LoggerFactory.getLogger(BaseFileConfig.class);

    /**
     * �㽭MSTOREƽ̨�����ļ���ʱ���񴥷�ʱ�䡣
     */
    public static final String STARTTIME;

    /**
     * �����ʼ���ַ
     */
    public static final String mailTo[];
    
    /**
     * �Ļ�ͬ��FTP��ַ
     */
    public static final String FTPIP;
    
    /**
     * �Ļ�ͬ��FTP�˿�
     */
    public static final int FTPPORT;
    
    /**
     * �Ļ�ͬ��FTP��¼�û���
     */
    public static final String FTPNAME;
    
    /**
     * �Ļ�ͬ��FTP��¼����
     */
    public static final String FTPPAS;
    
    /**
     * �Ļ�ͬ���ļ����·��
     */
    public static final String FTPPAHT;
    
    
    
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("BaseFileConfig");
        
        STARTTIME = module.getItemValue("startTime").trim();

        mailTo = module.getItemValue("mailTo").trim().split(",");
        
        FTPIP = module.getItemValue("FTPIP").trim();
        
        FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
        
        FTPNAME = module.getItemValue("FTPName").trim();
        
        FTPPAS = module.getItemValue("FTPPassWord").trim();
        
        FTPPAHT = module.getItemValue("FTPPath").trim();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the mailTo is " + module.getItemValue("mailTo"));
        }
    }

}
