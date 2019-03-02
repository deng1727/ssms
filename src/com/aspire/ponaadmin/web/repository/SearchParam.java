package com.aspire.ponaadmin.web.repository ;

/**
 * <p>����������</p>
 * <p>���������࣬��ʾ�����е�һ��������</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class SearchParam
{

    /**
     * ���췽��
     */
	public SearchParam()
    {}

    /**
     * ���췽��
     * @param property��Ҫ��������������
     * @param operator����������
     * @param value������ֵ
     */
	public SearchParam(String property, String operator, String value)
    {
        this.isNOT = false;
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    /**
     * �����������ơ��ͽڵ���������ƶ�Ӧ��
     */
    protected String property;

    /**
     * �������
     */
    protected String operator;

    /**
     * ����ֵ��
     */
    protected Object value;

    /**
     * �ǡ����Ժ�operator���ʹ�á�
     */
    protected boolean isNOT = false ;

    /**
     * ����ģʽ��֧��or��and��
     */
    protected String mode = RepositoryConstants.SEARCH_PARAM_MODE_AND ;

    /**
     * ���ţ�������������ϣ���ȡֵֻ��������( �����ţ� ��������������
     */
    protected String bracket = RepositoryConstants.SEARCH_PARAM_BRACKET_NONE;
    
    /**
     * �Ƿ�����ý���������
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
     * �Ƿ�������������
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
