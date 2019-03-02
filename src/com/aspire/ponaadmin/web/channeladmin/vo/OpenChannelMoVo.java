package com.aspire.ponaadmin.web.channeladmin.vo;

import java.util.Date;

/**
 * 客户端渠道表
 * @author zyg
 *
 */
public class OpenChannelMoVo
{
    private String channelId;//客户端渠道id
    private String channelsId;//渠道商id
    private String channelName;//客户端渠道名称
    private Date createDate;//创建时间
    
    public String getChannelId()
    {
    
        return channelId;
    }
    
    
    public String getChannelsId()
    {
    
        return channelsId;
    }

    
    public void setChannelsId(String channelsId)
    {
    
        this.channelsId = channelsId;
    }

    public void setChannelId(String channelId)
    {
    
        this.channelId = channelId;
    }
    
    public String getChannelName()
    {
    
        return channelName;
    }
    
    public void setChannelName(String channelName)
    {
    
        this.channelName = channelName;
    }
    
    public Date getCreateDate()
    {
    
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
    
        this.createDate = createDate;
    }
}
