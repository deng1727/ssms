/*
 * 文件名：BookCategoryVO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.book.vo;

import java.util.Date;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BookCategoryVO
{
    /**
     * 主键编码
     */
    private String id;
    
    /**
     * 货架编码
     */
    private String categoryId;
    
    /**
     * 货架名称
     */
    private String categoryName;
    
    /**
     * 货架类型
     */
    private String catalogType;
    
    /**
     * 是否在门户显示
     */
    private String type;
    
    /**
     * 简介
     */
    private String decrisption;
    
    /**
     * 父货架id
     */
    private String parentId;

    /**
     * 专区图片
     */
    private String picUrl;
    
    /**
     * 最后更新时间
     */
    private Date lupDate;
    
    /**
     * 货架总商品数
     */
    private String total;
    
    /**
     * 地市信息
     */
    private String cityId;
    
    /**
     * 图书货架排序
     */
    private int sortId;
    
    /**
     * 平台系统信息
     */
    private String platForm;

    public String getCatalogType()
    {
        return catalogType;
    }

    public void setCatalogType(String catalogType)
    {
        this.catalogType = catalogType;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getDecrisption()
    {
        return decrisption;
    }

    public void setDecrisption(String decrisption)
    {
        this.decrisption = decrisption;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Date getLupDate()
    {
        return lupDate;
    }

    public void setLupDate(Date lupDate)
    {
        this.lupDate = lupDate;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getPicUrl()
    {
        return picUrl;
    }

    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    public String getPlatForm()
    {
        return platForm;
    }

    public void setPlatForm(String platForm)
    {
        this.platForm = platForm;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {
        this.sortId = sortId;
    }
}
