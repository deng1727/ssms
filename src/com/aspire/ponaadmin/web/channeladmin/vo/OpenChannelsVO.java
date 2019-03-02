package com.aspire.ponaadmin.web.channeladmin.vo;

import java.util.Date;


public class OpenChannelsVO
{
    private String channelsId;
    private String parentChannelsId;
    private String channelsName;
    private String parentChannelsName;
    private String channelsNo;
    private String channelsDesc;
    private Date createDate;
    private Date modifyDate;
    private String status;
    private String channelsPwd;
    
    
    public String getChannelsPwd()
    {
    
        return channelsPwd;
    }

    
    public void setChannelsPwd(String channelsPwd)
    {
    
        this.channelsPwd = channelsPwd;
    }

    public String getChannelsId()
    {
    
        return channelsId;
    }
    
    public void setChannelsId(String channelsId)
    {
    
        this.channelsId = channelsId;
    }
    
    
    public String getChannelsName()
    {
    
        return channelsName;
    }
    
    public void setChannelsName(String channelsName)
    {
    
        this.channelsName = channelsName;
    }
    
    
    
    public String getParentChannelsId()
    {
    
        return parentChannelsId;
    }

    
    public void setParentChannelsId(String parentChannelsId)
    {
    
        this.parentChannelsId = parentChannelsId;
    }

    
    public String getParentChannelsName()
    {
    
        return parentChannelsName;
    }

    
    public void setParentChannelsName(String parentChannelsName)
    {
    
        this.parentChannelsName = parentChannelsName;
    }

    public String getChannelsNo()
    {
    
        return channelsNo;
    }
    
    public void setChannelsNo(String channelsNo)
    {
    
        this.channelsNo = channelsNo;
    }
    
    public String getChannelsDesc()
    {
    
        return channelsDesc;
    }
    
    public void setChannelsDesc(String channelsDesc)
    {
    
        this.channelsDesc = channelsDesc;
    }
    
    public Date getCreateDate()
    {
    
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
    
        this.createDate = createDate;
    }
    
    public Date getModifyDate()
    {
    
        return modifyDate;
    }
    
    public void setModifyDate(Date modifyDate)
    {
    
        this.modifyDate = modifyDate;
    }
    
    public String getStatus()
    {
    
        return status;
    }
    
    public void setStatus(String status)
    {
    
        this.status = status;
    }
}
