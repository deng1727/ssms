package com.aspire.ponaadmin.web.mail ;

import com.aspire.common.config.ModuleConfig ;
import com.aspire.common.config.ConfigFactory ;
import com.aspire.common.log.proxy.JLogger ;
import com.aspire.common.log.proxy.LoggerFactory ;

public class MailConfig
{
    private static JLogger logger = LoggerFactory.getLogger(MailConfig.class) ;

    private String modelName = "Mail" ;

    // smtp�ʼ���ַ��
    private String smtpHost ;

    //�ʼ����ͷ���
    private String fromMail ;

    //�ʼ��û����ơ�
    private String user ;

    //�û����롣
    private String password ;

    /**
     * ����Ա�ʼ���
     */
    private String adminMail ;

    /**
     * ����ͬ�����ʼ�����
     */
    private String synColorringSubject ;
    
    /**
     * �Զ��ϼ��ʼ�����
     */
    private String autoUpdateSubject ;
    /**
     * ͬ��ҵ����ʼ�����
     */
    private String syncServiceSubject;
    
    /**
     * ͬ�����ݵ��ʼ�����
     */
    private String syncContentSubject;
    /**
     * ��ҵ����ͬ�����ݵ��ʼ�����
     */
    private String CYsyncContentSubject;
    /**
     * ������Ա���ʼ���ַ
     */
    private String mailTo;
    /**
     * ͬ��ҵ�������Ա���ʼ���ַ
     */
    private String mailForData ;
    /**
     * �ʼ�������
     */
    private static MailConfig config ;

    /**
     * ȡ��ʵ��
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
     * ��ʼ��
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
     * ��ȡ��ص�������ֵ
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
     * �õ��ʼ�����������
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
