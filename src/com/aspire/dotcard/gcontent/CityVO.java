/*
 * �ļ�����CityVO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ����id
     */
    private String cityId;
    
    /**
     * ��������
     */
    private String cityName;
    
    /**
     * ʡ����
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
