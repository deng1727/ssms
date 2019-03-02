package com.aspire.ponaadmin.web.pivot.vo;

public class PivotDownloadVO
{
    /**
     * 内容编码
     */
    private String contentId;
    
    /**
     * 内容名称
     */
    private String contentName;
    
    /**
     * ap编码
     */
    private String apId;
    
    /**
     * ap名称
     */
    private String apName;
    
    /**
     * 平台编码
     */
    private String osName;
    
    /**
     * 机型id
     */
    private String deviceId;
    
    /**
     * 机型名称
     */
    private String deviceName;
    
    /**
     * 品牌名称
     */
    private String brandName;

    public String getApId()
    {
        return apId;
    }

    public void setApId(String apId)
    {
        this.apId = apId;
    }

    public String getApName()
    {
        return apName;
    }

    public void setApName(String apName)
    {
        this.apName = apName;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
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

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public String getOsName()
    {
        return osName;
    }

    public void setOsName(String osName)
    {
        this.osName = osName;
    }
    
}
