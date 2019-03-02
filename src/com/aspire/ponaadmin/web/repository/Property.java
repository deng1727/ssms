package com.aspire.ponaadmin.web.repository ;

/**
 * <p>��Դ���е�����Ԫ���ࡣ</p>
 * <p>��Դ���е�����Ԫ���࣬�̳���Item</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class Property extends Item
{

    /**
     * ���췽��
     */
	public Property()
    {}

    /**
     * ���췽��
     * @param _name������
     * @param _value��ֵ
     */
	public Property(String _name, Object _value)
    {
        this.name = _name;
        this.value = _value;
    }

    /**
     * �������ơ�
     */
    protected String name;

    /**
     * ����ֵ
     */
    protected Object value;

    public String getName ()
    {
        return name ;
    }

    public void setValue (Object value)
    {
        this.value = value ;
    }

    public void setName (String name)
    {
        this.name = name ;
    }

    public Object getValue ()
    {
        return value ;
    }

    /**
     * toString����
     * @return ������Ϣ
     */
    public String toString()
    {
        return name+','+value;
    }

}
