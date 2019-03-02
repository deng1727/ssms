package com.aspire.ponaadmin.web.pivot.vo;

public class PivotContentVO
{
    /**
     * 内容id
     */
    private String contentId;
    
    /**
     * 内容名称
     */
    private String contentName;
    
    /**
     * ap编码
     */
    private String apCode;
    
    /**
     * ap名称
     */
    private String apName;
    
    /**
     * 创建时间
     */
    private String creDate;

    public String getApCode()
    {
        return apCode;
    }

    public void setApCode(String apCode)
    {
        this.apCode = apCode;
    }

    public String getApName()
    {
        return apName;
    }

    public void setApName(String apName)
    {
        this.apName = apName;
    }

    public String getContentId()
    {
        return contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    public String getContentName()
    {
        return contentName;
    }

    public void setContentName(String contentName)
    {
        this.contentName = contentName;
    }

    public String getCreDate()
    {
        return creDate;
    }

    public void setCreDate(String creDate)
    {
        this.creDate = creDate;
    }
}
