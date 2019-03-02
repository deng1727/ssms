package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>
 * 资源树中的图书节点类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author 张威
 * @version 1.0.0.0
 */
public class GBook extends GContent
{
	/**
     * 资源类型：内容类型，图书
     */
    public static final String TYPE_BOOK = "nt:gcontent:book";

    /**
     * 构造方法
     */
    public GBook()
    {
        this.type = TYPE_BOOK;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GBook(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_BOOK;
    }
    /**
     * 获取图书ID
     * @return Returns the bookID.
     */
    public String getBookID()
    {
        return getNoNullString((String) this.getProperty("contentid").getValue());
    }


    /**
     * 设置图书ID
     * @param bookID The bookID to set.
     */
    public void setBookID(String bookID)
    {
        Property pro = new Property("contentid", bookID);
        this.setProperty(pro);
    }
    /**
     * 获取图书标题
     * @return Returns the bookTitle.
     */
    public String getBookTitle()
    {
        return getNoNullString((String) this.getProperty("bookTitle").getValue());
    }


    /**
     * 设置图书标题
     * @param bookTitle The bookTitle to set.
     */
    public void setBookTitle(String bookTitle)
    {
        Property pro = new Property("bookTitle", bookTitle);
        this.setProperty(pro);
    }
    /**
     * 获取图书作者
     * @return Returns the author.
     */
    public String getAuthor()
    {
        return getNoNullString((String) this.getProperty("author").getValue());
    }


    /**
     * 设置图书作者
     * @param author The author to set.
     */
    public void setAuthor(String author)
    {
        Property pro = new Property("author", author);
        this.setProperty(pro);
    }
    /**
     * 获取图书分类
     * @return Returns the bookCat.
     */
    public String getBookCat()
    {
        return getNoNullString((String) this.getProperty("cateName").getValue());
    }


    /**
     * 设置图书分类
     * @param bookCat The bookCat to set.
     */
    public void setBookCat(String bookCat)
    {
        Property pro = new Property("cateName", bookCat);
        this.setProperty(pro);
    }
    /**
     * 获取图书内容URL
     * @return Returns the contentUrl.
     */
    public String getContentUrl()
    {
        return getNoNullString((String) this.getProperty("contentUrl").getValue());
    }


    /**
     * 设置图书内容URL
     * @param contentUrl The contentUrl to set.
     */
    public void setContentUrl(String contentUrl)
    {
        Property pro = new Property("contentUrl", contentUrl);
        this.setProperty(pro);
    }
    /**
     * 获取失效时间
     * @return Returns the invalidTime.
     */
    public String getInvalidTime()
    {
        return getNoNullString((String) this.getProperty("invalidTime").getValue());
    }


    /**
     * 设置失效时间
     * @param invalidTime The invalidTime to set.
     */
    public void setInvalidTime(String invalidTime)
    {
        Property pro = new Property("invalidTime", invalidTime);
        this.setProperty(pro);
    }
    /**
     * 获取变更类别
     * @return Returns the changeType.
     */
    public String getChangeType()
    {
        return getNoNullString((String) this.getProperty("changeType").getValue());
    }


    /**
     * 设置变更类别
     * @param changeType The changeType to set.
     */
    public void setChangeType(String changeType)
    {
        Property pro = new Property("changeType", changeType);
        this.setProperty(pro);
    }

}
