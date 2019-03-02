package com.aspire.ponaadmin.web.repository ;

/**
 * <p>搜索参数类</p>
 * <p>搜索参数类，表示搜索中的一个参数。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class SearchParam
{

    /**
     * 构造方法
     */
	public SearchParam()
    {}

    /**
     * 构造方法
     * @param property，要搜索的属性名称
     * @param operator，操作符号
     * @param value，搜索值
     */
	public SearchParam(String property, String operator, String value)
    {
        this.isNOT = false;
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    /**
     * 搜索属性名称。和节点的属性名称对应。
     */
    protected String property;

    /**
     * 运算符。
     */
    protected String operator;

    /**
     * 搜索值。
     */
    protected Object value;

    /**
     * 非。可以和operator结合使用。
     */
    protected boolean isNOT = false ;

    /**
     * 复合模式，支持or和and。
     */
    protected String mode = RepositoryConstants.SEARCH_PARAM_MODE_AND ;

    /**
     * 括号，用于条件的组合，其取值只有左括号( 右括号） ，或者是无内容
     */
    protected String bracket = RepositoryConstants.SEARCH_PARAM_BRACKET_NONE;
    
    /**
     * 是否对引用进行搜索。
     */
    protected boolean isSearchRef = false ;
    
    public String getBracket ()
    {
        return bracket ;
    }

    public String getOperator ()
    {
        return operator ;
    }

    public String getMode ()
    {
        return mode ;
    }

    public String getProperty ()
    {
        return property ;
    }

    /**
     * 是否设置了条件否定
     * @return
     */
    public boolean isIsNOT ()
    {
        return isNOT ;
    }

    public void setValue (Object value)
    {
        this.value = value ;
    }

    public void setOperator (String operator)
    {
        this.operator = operator ;
    }

    public void setMode (String mode)
    {
        this.mode = mode ;
    }

    public void setBracket (String bracket)
    {
        this.bracket = bracket ;
    }

    public void setProperty (String property)
    {
        this.property = property ;
    }

//    public void setIsNOT (boolean isNOT)
//    {
//        this.isNOT = isNOT ;
//    }

    public Object getValue ()
    {
        return value ;
    }
    
    /**
     * @return Returns the isSearchRef.
     */
    public boolean isSearchRef()
    {
    
        return isSearchRef;
    }
    
    /**
     * @param isSearchRef The isSearchRef to set.
     */
    public void setSearchRef(boolean isSearchRef)
    {
    
        this.isSearchRef = isSearchRef;
    }

}
