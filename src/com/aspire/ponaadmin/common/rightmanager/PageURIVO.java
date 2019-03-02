package com.aspire.ponaadmin.common.rightmanager ;

/**
 * <p>页面权限信息VO类</p>
 * <p>页面权限信息VO类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class PageURIVO
{

    /**
     * 页面URI
     */
    private String pageURI;

    /**
     * 页面描述
     */
    private String desc;

    /**
     * 对应的权限ID
     */
    private String rightID;

    /**
     * 获取页面URI
     * @return String 页面URI
     */
    public String getPageURI ()
    {
        return pageURI ;
    }

    /**
     * 设置页面URI
     * @param URI String 页面URI
     */
    public void setPageURI (String URI)
    {
        this.pageURI = URI ;
    }

    /**
     * 获取页面描述
     * @return String 页面描述
     */
    public String getDesc ()
    {
        return desc ;
    }

    /**
     * 设置页面描述
     * @param desc String 页面描述
     */
    public void setDesc (String desc)
    {
        this.desc = desc ;
    }

    /**
     * 获取对应的权限ID
     * @param rightID String 对应的权限ID
     */
    public void setRightID (String rightID)
    {
        this.rightID = rightID ;
    }

    /**
     * 设置对应的权限ID
     * @return String 对应的权限ID
     */
    public String getRightID ()
    {
        return rightID ;
    }
}
