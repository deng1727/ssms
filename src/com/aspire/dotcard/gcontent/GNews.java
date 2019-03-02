package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>资源树中的资讯节点类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author 张敏
 */
public class GNews extends GContent
{
    /**
     * 资源类型：业务内容，资讯类型
     */
    public static final String TYPE_NEWS = "nt:gcontent:news";
    
    /**
     * 构造方法
     */
    public GNews()
    {
        this.type = TYPE_NEWS;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GNews(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_NEWS;
    }
    
    /**
     * 获取媒体链接url
     * @return Returns the mediaUrl.
     */
    public String getMediaUrl()
    {
        return getNoNullString((String) this.getProperty("mediaUrl").getValue());
    }


    /**
     * 设置媒体链接url 
     * @param mediaUrl
     */
    public void setMediaUrl(String mediaUrl)
    {
        Property pro = new Property("mediaUrl", mediaUrl);
        this.setProperty(pro);
    }
    
    /**
     * 获取媒体图标url
     * @return Returns the iconUrl.
     */
    public String getIconUrl()
    {
        return getNoNullString((String) this.getProperty("iconUrl").getValue());
    }


    /**
     * 设置媒体图标url 
     * @param iconUrl
     */
    public void setIconUrl(String iconUrl)
    {
        Property pro = new Property("iconUrl", iconUrl);
        this.setProperty(pro);
    }
  
    /**
     * 获取头条图片url
     * @return Returns the imageUrl.
     */
    public String getImageUrl()
    {
        return getNoNullString((String) this.getProperty("imageUrl").getValue());
    }


    /**
     * 设置头条图片url 
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl)
    {
        Property pro = new Property("imageUrl", imageUrl);
        this.setProperty(pro);
    }
    
    /**
     * 获取头条内容url
     * @return Returns the hotUrl.
     */
    public String getHotUrl()
    {
        return getNoNullString((String) this.getProperty("hotUrl").getValue());
    }


    /**
     * 设置头条内容url 
     * @param hotUrl
     */
    public void setHotUrl(String hotUrl)
    {
        Property pro = new Property("hotUrl", hotUrl);
        this.setProperty(pro);
    }
    
    /**
     * 获取头条内容标题
     * @return Returns the title.
     */
    public String getTitle()
    {
        return getNoNullString((String) this.getProperty("title").getValue());
    }


    /**
     * 设置头条内容标题
     * @param title
     */
    public void setTitle(String title)
    {
        Property pro = new Property("title", title);
        this.setProperty(pro);
    }
}