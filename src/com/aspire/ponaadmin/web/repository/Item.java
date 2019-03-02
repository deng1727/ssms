package com.aspire.ponaadmin.web.repository ;

/**
 * <p>资源树中的元素类</p>
 * <p>资源树中的元素类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public abstract class Item
{

    /**
     * 元素的id，在资源树中唯一
     */
    protected String id ;

    /**
     * 父元素的id
     */
    protected String parentID ;

    /**
     * 元素的路径
     */
    protected String path ;

    /**
     * 元素的类型
     */
    protected String type ;

    /**
     * 得到元素的id
     * @return，元素的id
     */
    public String getId ()
    {
        return id ;
    }

    /**
     * 得到元素的路径
     * @return，元素的路径
     */
    public String getPath ()
    {
        return path ;
    }

    /**
     * 得到元素的类型
     * @return，元素的类型
     */
    public String getType ()
    {
        return type ;
    }

    /**
     * 设置父元素的id
     * @param parentID，父元素的id
     */
    public void setParentID (String parentID)
    {
        this.parentID = parentID ;
    }

    /**
     * 设置元素的id
     * @param id，元素的id
     */
    public void setId (String id)
    {
        this.id = id ;
    }

    /**
     * 设置元素的路径
     * @param path，元素的路径
     */
    public void setPath (String path)
    {
        this.path = path ;
    }

    /**
     * 设置元素的类型
     * @param type，元素的类型
     */
    public void setType (String type)
    {
        this.type = type ;
    }

    /**
     * 得到父元素的id
     * @return，父元素的id
     */
    public String getParentID ()
    {
        return parentID ;
    }

    /**
     * toString方法
     * @return 描述信息
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
