package com.aspire.ponaadmin.web.pivot.vo;

public class PivotContentVO
{
    /**
     * ����id
     */
    private String contentId;
    
    /**
     * ��������
     */
    private String contentName;
    
    /**
     * ap����
     */
    private String apCode;
    
    /**
     * ap����
     */
    private String apName;
    
    /**
     * ����ʱ��
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
