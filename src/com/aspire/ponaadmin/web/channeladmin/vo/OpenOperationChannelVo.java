package com.aspire.ponaadmin.web.channeladmin.vo;

import java.util.Date;

/**
 * ������Ӫ������
 * @author zyg
 *
 */
public class OpenOperationChannelVo
{
    private String channelId;//������Ӫ����id
    private String channelsId;//������id
    private Date createDate;//����ʱ��
    
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
