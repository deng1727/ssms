package com.aspire.dotcard.baseread.vo;

public class BookUpdateVO
{

    private String contentId;

    private String updatTime;

    public String getContentId()
    {
        return contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    public String getUpdatTime()
    {
        return updatTime;
    }

    public void setUpdatTime(String updatTime)
    {
        this.updatTime = updatTime;
    }

    public boolean setValue(String[] data)
    {
        if (data.length != 2)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
            || "".equals(data[1].trim()))
        {
            return false;
        }
        contentId = data[0].trim();
        updatTime = data[1].trim();
        
        return true;
    }
}
