/*
 * 文件名：BaseBook.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.baserecomm.basebook;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public class BaseBookVO
{
    /**
     * 基地图书id
     */
    private String bookId;
    
    /**
     * 内容id
     */
    private String contentId;
    
    /**
     * 基地图书名称
     */
    private String bookName;
    
    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 基地图书类型
     */
    private String bookType;
    
    /**
     * 基地图书简介
     */
    private String bookDesc;
    
    /**
     * 基地图书关键字
     */
    private String key;
    
    /**
     * 是否是有效数据
     */
    private String validityData;
    
    public String getAuthorName()
    {
        return authorName;
    }

    
    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    
    public String getBookDesc()
    {
        return bookDesc;
    }

    
    public void setBookDesc(String bookDesc)
    {
        this.bookDesc = bookDesc;
    }

    
    public String getBookId()
    {
        return bookId;
    }

    
    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    
    public String getBookName()
    {
        return bookName;
    }

    
    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    
    public String getBookType()
    {
        return bookType;
    }

    
    public void setBookType(String bookType)
    {
        this.bookType = bookType;
    }

    
    public String getContentId()
    {
        return contentId;
    }

    
    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }


    
    public String getKey()
    {
        return key;
    }


    
    public void setKey(String key)
    {
        this.key = key;
    }


    
    public String getValidityData()
    {
        return validityData;
    }


    
    public void setValidityData(String validityData)
    {
        this.validityData = validityData;
    }
}
