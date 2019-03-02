package com.aspire.ponaadmin.web.pivot.vo;

public class PivotDeviceVO
{
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
    
    /**
     * 平台名称
     */
    private String osName;
    
    /**
     * 创建时间
     */
    private String creDate;

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    public String getCreDate()
    {
        return creDate;
    }

    public void setCreDate(String creDate)
    {
        this.creDate = creDate;
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
