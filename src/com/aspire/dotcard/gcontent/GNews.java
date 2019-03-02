package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>��Դ���е���Ѷ�ڵ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author ����
 */
public class GNews extends GContent
{
    /**
     * ��Դ���ͣ�ҵ�����ݣ���Ѷ����
     */
    public static final String TYPE_NEWS = "nt:gcontent:news";
    
    /**
     * ���췽��
     */
    public GNews()
    {
        this.type = TYPE_NEWS;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GNews(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_NEWS;
    }
    
    /**
     * ��ȡý������url
     * @return Returns the mediaUrl.
     */
    public String getMediaUrl()
    {
        return getNoNullString((String) this.getProperty("mediaUrl").getValue());
    }


    /**
     * ����ý������url 
     * @param mediaUrl
     */
    public void setMediaUrl(String mediaUrl)
    {
        Property pro = new Property("mediaUrl", mediaUrl);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡý��ͼ��url
     * @return Returns the iconUrl.
     */
    public String getIconUrl()
    {
        return getNoNullString((String) this.getProperty("iconUrl").getValue());
    }


    /**
     * ����ý��ͼ��url 
     * @param iconUrl
     */
    public void setIconUrl(String iconUrl)
    {
        Property pro = new Property("iconUrl", iconUrl);
        this.setProperty(pro);
    }
  
    /**
     * ��ȡͷ��ͼƬurl
     * @return Returns the imageUrl.
     */
    public String getImageUrl()
    {
        return getNoNullString((String) this.getProperty("imageUrl").getValue());
    }


    /**
     * ����ͷ��ͼƬurl 
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl)
    {
        Property pro = new Property("imageUrl", imageUrl);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡͷ������url
     * @return Returns the hotUrl.
     */
    public String getHotUrl()
    {
        return getNoNullString((String) this.getProperty("hotUrl").getValue());
    }


    /**
     * ����ͷ������url 
     * @param hotUrl
     */
    public void setHotUrl(String hotUrl)
    {
        Property pro = new Property("hotUrl", hotUrl);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡͷ�����ݱ���
     * @return Returns the title.
     */
    public String getTitle()
    {
        return getNoNullString((String) this.getProperty("title").getValue());
    }


    /**
     * ����ͷ�����ݱ���
     * @param title
     */
    public void setTitle(String title)
    {
        Property pro = new Property("title", title);
        this.setProperty(pro);
    }
}