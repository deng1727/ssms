package com.aspire.dotcard.gcontent;

/**
 * PATCH 134 ADD �ն�����vo
 * @author x_liyouli
 *
 */
public class DeviceVO
{
	//�ն�����
	private String deviceName;
	
	//�ն˶�UA
	private String deviceId;
	//�ֻ�Ʒ��
	private String brand;

	public String getDeviceName()
	{
		return deviceName;
	}

	public void setDeviceName(String deviceName)
	{
		this.deviceName = deviceName;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("DeviceVO[deviceName=");
		sb.append(deviceName);
		sb.append(", brand=");
		sb.append(brand);
		sb.append("]");		
		
		return sb.toString();
	}
}
