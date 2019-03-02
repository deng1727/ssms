package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

public class GRead extends GContent
{

	/**
     * 资源类型：内容类型，基地图书
     */
    public static final String TYPE_READ = "nt:gcontent:read";

    /**
     * 构造方法
     */
    public GRead()
    {
        this.type = TYPE_READ;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GRead(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_READ;
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
//////////
    /**
     * 获取费用类型
     * @return Returns the chargeType.
     * 费用类型：0免费；1按本计费；2、按章计费；3、按字计费
     * 
     */
    public String getChargeType()
    {
        return getNoNullString((String) this.getProperty("chargeType").getValue());
    }


    /**
     * 设置费用类型
     * @param changeType The chargeType to set.
     * 费用类型：0免费；1按本计费；2、按章计费；3、按字计费
     */
    public void setChargeType(String chargeType)
    {
        Property pro = new Property("chargeType", chargeType);
        this.setProperty(pro);
    }
    
    /**
     * 获取费率
     * @return Returns the Fee.
     * 费率，单位：分
     */
    public String getFee()
    {
        return getNoNullString((String) this.getProperty("fee").getValue());
    }


    /**
     * 设置费率
     * @param changeType The Fee to set.
     * 费率，单位：分
     */
    public void setFee(String fee)
    {
        Property pro = new Property("fee", fee);
        this.setProperty(pro);
    }
    
    /**
     * 获取是否完本
     * @return Returns the isFinish.
     * 是否完本：0、未完本；1、已完本 
     */
    public String getIsFinish()
    {
        return getNoNullString((String) this.getProperty("isFinish").getValue());
    }


    /**
     * 设置是否完本
     * @param changeType The isFinish to set.
     * 是否完本：0、未完本；1、已完本 
     */
    public void setIsFinish(String isFinish)
    {
        Property pro = new Property("isFinish", isFinish);
        this.setProperty(pro);
    }
    
    
    
    /**
     * 获取作者描述
     * @return Returns the handBook.
     */
    public String getHandBook()
    {
        return getNoNullString((String) this.getProperty("handBook").getValue());
    }


    /**
     * 设置作者描述
     * @param handBook
     */
    public void setHandBook(String handBook)
    {
        Property pro = new Property("handBook", handBook);
        this.setProperty(pro);
    }
    
    /**
     * 获取歌手名
     * @return Returns the singer.
     */
    public String getSinger()
    {
        return getNoNullString((String) this.getProperty("singer").getValue());
    }


    /**
     * 设置歌手名
     * @param singer The singer to set.
     */
    public void setSinger(String singer)
    {
        Property pro = new Property("singer", singer);
        this.setProperty(pro);
    }



}
