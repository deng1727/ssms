package com.aspire.ponaadmin.web.system ;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>系统信息类</p>
 * <p>保存系统中的一些公用信息</p>
 * <p>Copyright (c) 2003-2006 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class SystemInfo
{
    /**
     * 日志对象。
     */
    protected static JLogger logger = LoggerFactory.getLogger(SystemInfo.class);

    /**
     * singleton模式的实例
     */
    private static SystemInfo instance = new SystemInfo();

    /**
     * web应用部署的上下文
     */
    private String webAppContext ;

    /**
     * 构造方法，由singleton模式调用
     */
    private SystemInfo()
    {
    }

    /**
     * 获取实例
     * @return 实例
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
