package com.aspire.ponaadmin.web.repository ;

/**
 * <p>排序参数类</p>
 * <p>排序参数类，表示排序中的一个参数。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class TaxisParam
{

    /**
     * 排序属性名称。和节点的属性名称对应。
     */
    protected String property;

    /**
     * 排序种类，升序或者降序。
     */
    protected String order = RepositoryConstants.ORDER_TYPE_DEFAULT;

    /**
     * 构造方法
     * @param property，要排序的属性名称
     * @param order，是升序还是降序排列
     */
    public TaxisParam(String property, String order)
    {
        this.property = property;
        this.order = order;
    }

    public String getOrder ()
    {
        return order ;
    }

    public String getProperty ()
    {
        return property ;
    }

    public void setOrder (String order)
    {
        this.order = order ;
    }

    public void setProperty (String property)
    {
        this.property = property ;
    }

}
