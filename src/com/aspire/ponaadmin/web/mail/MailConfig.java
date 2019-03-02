package com.aspire.ponaadmin.web.mail ;

import com.aspire.common.config.ModuleConfig ;
import com.aspire.common.config.ConfigFactory ;
import com.aspire.common.log.proxy.JLogger ;
import com.aspire.common.log.proxy.LoggerFactory ;

public class MailConfig
{
    private static JLogger logger = LoggerFactory.getLogger(MailConfig.class) ;

    private String modelName = "Mail" ;

    // smtp邮件地址。
    private String smtpHost ;

    //邮件发送方。
    private String fromMail ;

    //邮件用户名称。
    private String user ;

    //用户密码。
    private String password ;

    /**
     * 管理员邮件。
     */
    private String adminMail ;

    /**
     * 彩铃同步的邮件标题
     */
    private String synColorringSubject ;
    
    /**
     * 自动上架邮件标题
     */
    private String autoUpdateSubject ;
    /**
     * 同步业务的邮件标题
     */
    private String syncServiceSubject;
    
    /**
     * 同步内容的邮件标题
     */
    private String syncContentSubject;
    /**
     * 创业大赛同步内容的邮件标题
     */
    private String CYsyncContentSubject;
    /**
     * 接收人员的邮件地址
     */
    private String mailTo;
    /**
     * 同步业务接收人员的邮件地址
     */
    private String mailForData ;
    /**
     * 邮件配置项
     */
    private static MailConfig config ;

    /**
     * 取单实例
     * @return CommonConfig
     */
    public static MailConfig getInstance ()
    {
        if (config == null)
        {
            config = new MailConfig() ;
        }
        return config ;
    }

    private MailConfig ()
    {
        init() ;
    }

    /**
     * 初始化
     */
    private void init ()
    {
        this.smtpHost = this.get("smtpHost") ;
        this.fromMail = this.get("fromMail") ;
        this.user = this.get("user") ;
        this.password = this.get("password") ;
        this.adminMail = this.get("adminMail") ;
        this.synColorringSubject = this.get("synColorringSubject");
        this.syncServiceSubject = this.get("syncServiceSubject");
        this.syncContentSubject = this.get("syncContentSubject");
        this.CYsyncContentSubject = this.get("CYsyncContentSubject");
        this.mailTo = this.get("mailTo");
        this.mailForData = this.get("mailForData");
        this.autoUpdateSubject = this.get("autoUpdateSubject");
        
    }

    /**
     * 获取相关的配置项值
     * @param key String
     * @return String
     */
    private String get (String key)
    {
        ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
            this.modelName) ;
        if (module == null)
        {
            return null ;
        }
        String value = module.getItemValue(key) ;
        if (logger.isDebugEnabled())
        {
            logger.debug("config value for [" + key + "] is [" + value + "].") ;
        }
        return value ;
    }

    public String getSmtpHost ()
    {
        return this.smtpHost ;
    }

    public String getFromMail ()
    {
        return this.fromMail ;
    }

    public String getUser ()
    {
        return this.user ;
    }

    public String getPassword ()
    {
        return this.password ;
    }

    public String getSynColorringSubject ()
    {
        return this.synColorringSubject ;
    }

    public String getAdminMail()
    {
        return this.adminMail;
    }
    
   
    
    /**
     * @return Returns the mailTo.
     */
    public String getMailTo()
    {
    
        return mailTo;
    }
    
    /**
     * @return Returns the mailForData.
     */
    
    public String getMailForData() {
		return mailForData;
	}

	/**
     * @return Returns the syncContentSubject.
     */
    public String getSyncContentSubject()
    {
    
        return syncContentSubject;
    }
    /**
     * @return Returns the syncContentSubject.
     */
    public String getCYSyncContentSubject()
    {
    
        return CYsyncContentSubject;
    }

    
    /**
     * @return Returns the syncServiceSubject.
     */
    public String getSyncServiceSubject()
    {
    
        return syncServiceSubject;
    }
    
    public String getAutoUpdateSubject() {
		return autoUpdateSubject;
	}

	

	/**
     * 得到邮件接收者数组
     * @return String[]
     */
    public String[] getMailToArray()
    {
        return this.mailTo.split(",");
    }
    
    public String[] getMailForDataToArray(){
    	return this.mailForData.split(",");
    }
    
}
