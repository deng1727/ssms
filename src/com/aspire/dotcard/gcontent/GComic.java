/**
 * <p>
 *  资源树中的动漫节点类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 4, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * @author dongke
 *
 */
public class GComic extends GContent {

	
	/**
     * 资源类型：业务内容，视频类型
     */
    public static final String TYPE_COMIC = "nt:gcontent:comic";
    
   /**
    * 构造方法
    *
    */
    public GComic(){   	
    	this.type = TYPE_COMIC;
    }
    
    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GComic(String nodeID){
    	super (nodeID);
    	this.type = TYPE_COMIC;
    	
    }
    

    

    
    
    /**
     * 动漫作者
     * @return Returns the author.
     */
    public String getAuthor()
    {
        return getNoNullString((String) this.getProperty("author").getValue());
    }


    /**
     * 动漫作者
     * @param author
     */
    public void setAuthor(String author)
    {
        Property pro = new Property("author", author);
        this.setProperty(pro);
    }
    
    

    
    
    /**
     * 动漫内容URL
     * @return Returns the contentUrl.
     */
    public String getContentUrl()
    {
        return getNoNullString((String) this.getProperty("contentUrl").getValue());
    }


    /**
     * 动漫内容URL
     * @param contentUrl
     */
    public void setContentUrl(String contentUrl)
    {
        Property pro = new Property("contentUrl", contentUrl);
        this.setProperty(pro);
    }
    
    
    /**
     * 失效时间
     * @return Returns the invalidTime.
     */
    public String getInvalidTime()
    {
        return getNoNullString((String) this.getProperty("invalidTime").getValue());
    }


    /**
     * 失效时间
     * @param invalidTime
     */
    public void setInvalidTime(String invalidTime)
    {
        Property pro = new Property("invalidTime", invalidTime);
        this.setProperty(pro);
    }
    
    
 
    
    
    
    /**
     * 变更类型，1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现。
     * @return Returns the Changetype.
     */
    public String getChangeType()
    {
        return getNoNullString((String) this.getProperty("changeType").getValue());
    }


    /**
     * 变更类型，1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现。
     * @param changetype
     */
    public void setChangeType(String changeType)
    {
        Property pro = new Property("changeType", changeType);
        this.setProperty(pro);
    }
    
    
    /**
     * toString方法
     * @return 描述信息
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("GComic[");
        sb.append(this.getProperty("author"));
        sb.append(',');
        sb.append(this.getProperty("contentUrl"));
        sb.append(',');
        sb.append(this.getProperty("invalidTime"));
        sb.append(',');
        sb.append(this.getProperty("changeType"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }
    
}
