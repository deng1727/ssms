package com.aspire.ponaadmin.common.usermanager ;

/**
 * <p>用户访问信息VO类</p>
 * <p>用户访问信息VO类，记录用户访问ponaadmin系统的访问信息。
 * 包括：访问使用的帐号、访问IP、登录时间、访问终端等</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserAccessInfoVO
{

    /**
     * 访问帐号id
     */
    private String userID ;

    /**
     * 访问IP
     */
    private String IP ;

    /**
     * 登录时间
     */
    private long loginTime ;

    /**
     * 用户使用的终端
     */
    private String useragent ;

    /**
     * 构造方法
     */
	public UserAccessInfoVO ()
    {
    }

    /**
     * 获取用户使用的终端
     * @return 用户使用的终端
     */
	public String getUseragent ()
    {
        return useragent ;
    }

    /**
     * 获取用户帐号ID
     * @return 用户帐号ID
     */
	public String getUserID ()
    {
        return userID ;
    }

    /**
     * 设置登录时间
     * @param loginTime 登录时间
     */
	public void setLoginTime (long loginTime)
    {
        this.loginTime = loginTime ;
    }

    /**
     * 设置用户使用的终端
     * @param useragent 用户使用的终端
     */
	public void setUseragent (String useragent)
    {
        this.useragent = useragent ;
    }

    /**
     * 设置用户帐号ID
     * @param userID 用户帐号ID
     */
	public void setUserID (String userID)
    {
        this.userID = userID ;
    }

    /**
     * 获取登录时间
     * @return 登录时间
     */
	public long getLoginTime ()
    {
        return loginTime ;
    }

    /**
     * 设置访问IP
     * @param IP 访问IP
     */
	public void setIP (String IP)
    {
        this.IP = IP ;
    }

    /**
     * 获取访问IP
     * @return 访问IP
     */
	public String getIP ()
    {
        return IP ;
    }
}
