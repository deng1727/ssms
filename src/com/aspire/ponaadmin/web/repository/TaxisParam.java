package com.aspire.ponaadmin.web.repository ;

/**
 * <p>���������</p>
 * <p>��������࣬��ʾ�����е�һ��������</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class TaxisParam
{

    /**
     * �����������ơ��ͽڵ���������ƶ�Ӧ��
     */
    protected String property;

    /**
     * �������࣬������߽���
     */
    protected String order = RepositoryConstants.ORDER_TYPE_DEFAULT;

    /**
     * ���췽��
     * @param property��Ҫ�������������
     * @param order���������ǽ�������
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
