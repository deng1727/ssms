package com.aspire.ponaadmin.web.channeladmin.vo;

/**
 * 渠道商货架关系表
 * @author zyg
 *
 */
public class OpenChannelsCategoryVo
{
    private String channelsId;//对应渠道商id
    private String categoryId;//对应根货架id
    
    public String getChannelsId()
    {
    
        return channelsId;
    }
    
    public void setChannelsId(String channelsId)
    {
    
        this.channelsId = channelsId;
    }
    
    public String getCategoryId()
    {
    
        return categoryId;
    }
    
    public void setCategoryId(String categoryId)
    {
    
        this.categoryId = categoryId;
    }
}
