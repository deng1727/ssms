package com.aspire.dotcard.basecomic.vo;

import java.util.Date;

/**
 * 动漫黑名单实体对象
 */
public class BlackVO
{

    private String id;
    private String contentId;
    private String contentName;
    private int contentType;
    private int contentPortal;
    private Date createDate;
    private Date lupdate;
    private int status;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getLupdate()
    {
        return lupdate;
    }

    public void setLupdate(Date lupdate)
    {
        this.lupdate = lupdate;
    }

    public int getContentType()
    {
        return contentType;
    }

    public void setContentType(int contentType)
    {
        this.contentType = contentType;
    }

    public int getContentPortal()
    {
        return contentPortal;
    }

    public void setContentPortal(int contentPortal)
    {
        this.contentPortal = contentPortal;
    }
}
