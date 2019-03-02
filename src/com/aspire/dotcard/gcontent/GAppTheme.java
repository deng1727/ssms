package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;


public class GAppTheme extends GAppContent 
{
	/**
     * 资源类型：业务内容，应用主题分类
     */
    public static final String TYPE_APPTHEME = "nt:gcontent:appTheme";
    
    /**
     * 构造方法
     */
    public GAppTheme()
    {
       this.type=TYPE_APPTHEME;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GAppTheme(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_APPTHEME;
    }
    /**
     * 获取操作指南
     * @return Returns the handBook.  保存创业大赛开发者高校信息  add by dongke 2011
     */
    public String getHandBook()
    {
        return getNoNullString((String) this.getProperty("handBook").getValue());
    }


    /**
     * 设置操作指南 
     * @param handBook  保存创业大赛开发者高校信息  add by dongke 2011
     */
    public void setHandBook(String handBook)
    {
        Property pro = new Property("handBook", handBook);
        this.setProperty(pro);
    }
	
    /**
     * 获取操作指南图片地址
     * @return Returns the handBookPicture.
     */
    public String getHandBookPicture()
    {
        return getNoNullString((String) this.getProperty("handBookPicture").getValue());
    }

    /**
     * 设置操作指南图片地址
     * @param handBookPicture
     */
    public void setHandBookPicture(String handBookPicture)
    {
        Property pro = new Property("handBookPicture", handBookPicture);
        this.setProperty(pro);
    }
}
