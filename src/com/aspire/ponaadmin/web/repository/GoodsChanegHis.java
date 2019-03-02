/*
 * 文件名：GoodsChanegHis.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.repository;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class GoodsChanegHis
{
    /**
     * 变成商品id
     */
    private String goodsId;
    
    /**
     * 货架类型
     */
    private String type;
    
    /**
     * 货架id
     */
    private String  cid;
    
    /**
     * 操作类型
     */
    private String action;
    
    /**
     * 内容类型
     */
    private String subType;
    
    /**
     * 内容id
     */
    private String contentid;
    
    /**
     * 路径
     */
    private String path;
    
    /**
     * 一级分类
     */
    private String cateType;

    
    public String getAction()
    {
        return action;
    }
    
    public void setAction(String action)
    {
        this.action = action;
    }
    
    public String getCid()
    {
        return cid;
    }
    
    public void setCid(String cid)
    {
        this.cid = cid;
    }

    public String getGoodsId()
    {
        return goodsId;
    }
    
    public void setGoodsId(String goodsId)
    {
        this.goodsId = goodsId;
    }

    public String getSubType()
    {
        return subType;
    }

    public void setSubType(String subType)
    {
        this.subType = subType;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        
        sb.append("goodsId=").append(goodsId);
        sb.append(",type=").append(type);
        sb.append(",cid=").append(cid);
        sb.append(",action=").append(action);
        sb.append(",subType=").append(subType);
        
        return sb.toString();
    }

    public String getCateType()
    {
        return cateType;
    }

    public void setCateType(String cateType)
    {
        this.cateType = cateType;
    }

    public String getContentid()
    {
        return contentid;
    }

    public void setContentid(String contentid)
    {
        this.contentid = contentid;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
}
