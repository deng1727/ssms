package com.aspire.ponaadmin.web.channeladmin.vo;

import java.util.Date;

/**
 * �ͻ���������
 * @author zyg
 *
 */
public class OpenChannelMoVo
{
    private String channelId;//�ͻ�������id
    private String channelsId;//������id
    private String channelName;//�ͻ�����������
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
