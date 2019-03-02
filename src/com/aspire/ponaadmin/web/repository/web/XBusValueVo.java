/*
 * 
 */
package com.aspire.ponaadmin.web.repository.web;


/**
 * @author x_wangml
 *
 */
public class XBusValueVo
{
    /**
     * 常量换行符
     */
    private static final String nextLine = "\r\n";
    
    /**
     * 商品id
     */
    private String id;
    
    /**
     * 商品goodId
     */
    private String goodId;
    
    /**
     * 排序后的序号
     */
    private String sortId;
    
    /**
     * 商品路径
     */
    private String path;
    
    /**
     * 商品类型
     */
    private String type;

    
    public String getGoodId()
    {
    
        return goodId;
    }

    public String getId()
    {
    
        return id;
    }

    
    public void setId(String id)
    {
    
        this.id = id;
    }

    
    public String getSortId()
    {
    
        return sortId;
    }

    
    public void setSortId(String sortId)
    {
    
        this.sortId = sortId;
    }
    
    public String getPath()
    {
    
        return path;
    }

    public void setGoodId(String goodId)
    {
    
        this.goodId = goodId;
    }

    public void setPath(String path)
    {
    
        this.path = path;
    }
    
    public String getType()
    {
    
        return type;
    }

    
    public void setType(String type)
    {
    
        this.type = type;
    }
    
    /**
     * 用于生成发送的xml数据内容
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("<good>").append(nextLine);
        str.append("<id>").append(this.id).append("</id>").append(nextLine);
        str.append("<path>").append(this.path).append("</path>").append(nextLine);
        str.append("<type>").append(this.type).append("</type>").append(nextLine);
        str.append("<goodId>").append(this.goodId).append("</goodId>").append(nextLine);
        str.append("<sortId>").append(this.sortId).append("</sortId>").append(nextLine);
        str.append("</good>").append(nextLine);
        return str.toString();
    }
}
