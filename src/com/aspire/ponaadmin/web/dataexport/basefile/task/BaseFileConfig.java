
package com.aspire.ponaadmin.web.dataexport.basefile.task;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class BaseFileConfig
{

    private static final JLogger logger = LoggerFactory.getLogger(BaseFileConfig.class);

    /**
     * 浙江MSTORE平台生成文件定时任务触发时间。
     */
    public static final String STARTTIME;

    /**
     * 发送邮件地址
     */
    public static final String mailTo[];
    
    /**
     * 心机同步FTP地址
     */
    public static final String FTPIP;
    
    /**
     * 心机同步FTP端口
     */
    public static final int FTPPORT;
    
    /**
     * 心机同步FTP登录用户名
     */
    public static final String FTPNAME;
    
    /**
     * 心机同步FTP登录密码
     */
    public static final String FTPPAS;
    
    /**
     * 心机同步文件存放路径
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
