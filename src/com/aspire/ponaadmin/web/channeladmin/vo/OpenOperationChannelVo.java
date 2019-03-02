package com.aspire.ponaadmin.web.channeladmin.vo;

import java.util.Date;

/**
 * 开发运营渠道表
 * @author zyg
 *
 */
public class OpenOperationChannelVo
{
    private String channelId;//开放运营渠道id
    private String channelsId;//渠道商id
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
    
    public Date getCreateDate()
    {
    
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
    
        this.createDate = createDate;
    }
}
