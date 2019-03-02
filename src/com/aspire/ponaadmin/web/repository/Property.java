package com.aspire.ponaadmin.web.repository ;

/**
 * <p>资源树中的属性元素类。</p>
 * <p>资源树中的属性元素类，继承于Item</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class Property extends Item
{

    /**
     * 构造方法
     */
	public Property()
    {}

    /**
     * 构造方法
     * @param _name，名称
     * @param _value，值
     */
	public Property(String _name, Object _value)
    {
        this.name = _name;
        this.value = _value;
    }

    /**
     * 属性名称。
     */
    protected String name;

    /**
     * 属性值
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
     * toString方法
     * @return 描述信息
     */
    public String toString()
    {
        return name+','+value;
    }

}
