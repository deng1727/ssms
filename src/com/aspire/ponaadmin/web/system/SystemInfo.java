package com.aspire.ponaadmin.web.system ;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>ϵͳ��Ϣ��</p>
 * <p>����ϵͳ�е�һЩ������Ϣ</p>
 * <p>Copyright (c) 2003-2006 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class SystemInfo
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(SystemInfo.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static SystemInfo instance = new SystemInfo();

    /**
     * webӦ�ò����������
     */
    private String webAppContext ;

    /**
     * ���췽������singletonģʽ����
     */
    private SystemInfo()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static SystemInfo getInstance()
    {
        return instance;
    }

    public void setWebAppContext (String webAppContext)
    {
        this.webAppContext = webAppContext ; 
    }

    public String getWebAppContext ()
    {
        return webAppContext ; 
    }
}
