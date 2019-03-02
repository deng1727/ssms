package com.aspire.ponaadmin.web.repository ;

/**
 * <p>��Դ���е�Ԫ����</p>
 * <p>��Դ���е�Ԫ����</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public abstract class Item
{

    /**
     * Ԫ�ص�id������Դ����Ψһ
     */
    protected String id ;

    /**
     * ��Ԫ�ص�id
     */
    protected String parentID ;

    /**
     * Ԫ�ص�·��
     */
    protected String path ;

    /**
     * Ԫ�ص�����
     */
    protected String type ;

    /**
     * �õ�Ԫ�ص�id
     * @return��Ԫ�ص�id
     */
    public String getId ()
    {
        return id ;
    }

    /**
     * �õ�Ԫ�ص�·��
     * @return��Ԫ�ص�·��
     */
    public String getPath ()
    {
        return path ;
    }

    /**
     * �õ�Ԫ�ص�����
     * @return��Ԫ�ص�����
     */
    public String getType ()
    {
        return type ;
    }

    /**
     * ���ø�Ԫ�ص�id
     * @param parentID����Ԫ�ص�id
     */
    public void setParentID (String parentID)
    {
        this.parentID = parentID ;
    }

    /**
     * ����Ԫ�ص�id
     * @param id��Ԫ�ص�id
     */
    public void setId (String id)
    {
        this.id = id ;
    }

    /**
     * ����Ԫ�ص�·��
     * @param path��Ԫ�ص�·��
     */
    public void setPath (String path)
    {
        this.path = path ;
    }

    /**
     * ����Ԫ�ص�����
     * @param type��Ԫ�ص�����
     */
    public void setType (String type)
    {
        this.type = type ;
    }

    /**
     * �õ���Ԫ�ص�id
     * @return����Ԫ�ص�id
     */
    public String getParentID ()
    {
        return parentID ;
    }

    /**
     * toString����
     * @return ������Ϣ
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Item[");
        sb.append(id);
        sb.append(',');
        sb.append(parentID);
        sb.append(',');
        sb.append(path);
        sb.append(',');
        sb.append(type);
        sb.append("]");
        return sb.toString();
    }

}
