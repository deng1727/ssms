/*
 * 文件名：CityVO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.dotcard.gcontent;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class CityVO
{
    /**
     * 城市id
     */
    private String cityId;
    
    /**
     * 城市名称
     */
    private String cityName;
    
    /**
     * 省名称
     */
    private String pvcName;
    
    public String getCityId()
    {
        return cityId;
    }
    
    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }
    
    public String getCityName()
    {
        return cityName;
    }
    
    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }
    
    public String getPvcName()
    {
        return pvcName;
    }
    
    public void setPvcName(String pvcName)
    {
        this.pvcName = pvcName;
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("CityVO[cityId=");
        sb.append(cityId);
        sb.append(", cityName=");
        sb.append(cityName);
        sb.append(", pvcName=");
        sb.append(pvcName);
        sb.append("]");

        return sb.toString();
    }
}
