/*
 * 文件名：BookRefVO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.book.vo;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BookRefVO
{
    /**
     * 货架id
     */
    private String cId;
    
    /**
     * 图书id
     */
    private String bookId;
    
    /**
     * 图书名称
     */
    private String bookName;
    
    /**
     * 作者名称
     */
    private String authorName;
    
    /**
     * 图书类型
     */
    private String typeName;
    
    /**
     * 图书简介
     */
    private String desc;
    
    /**
     * 是否为完本
     */
    private String isFinish;
    
    /**
     * 收费类型
     */
    private String chargeType;
    
    /**
     * 推荐短语
     */
    private String shortRecommend;
    
    /**
     * 费用
     */
    private String fee;
    
    /**
     * 排序
     */
    private String sortNumber;
    
    /**
     * 评分
     */
    private String rankValue;

    public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
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

    public String getCId()
    {
        return cId;
    }

    public void setCId(String id)
    {
        cId = id;
    }

    public String getRankValue()
    {
        return rankValue;
    }

    public void setRankValue(String rankValue)
    {
        this.rankValue = rankValue;
    }

    public String getSortNumber()
    {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber)
    {
        this.sortNumber = sortNumber;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getIsFinish()
    {
        return isFinish;
    }

    public void setIsFinish(String isFinish)
    {
        this.isFinish = isFinish;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getChargeType()
    {
        return chargeType;
    }

    public void setChargeType(String chargeType)
    {
        this.chargeType = chargeType;
    }

    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }

    public String getShortRecommend()
    {
        return shortRecommend;
    }

    public void setShortRecommend(String shortRecommend)
    {
        this.shortRecommend = shortRecommend;
    }
}
