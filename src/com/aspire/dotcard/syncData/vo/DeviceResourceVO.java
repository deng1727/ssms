package com.aspire.dotcard.syncData.vo;

import java.util.Date;

/**
 * 用于保存机型资源信息。主要用于机型适配
 * @author zhangwei
 *
 */
public class DeviceResourceVO
{
	private String deviceId;
	/**
	 * 1 精确适配关系；2 模糊适配关系；3 精确扩展适配关系，默认为1
	 */
	private int match;
	/**
	 * 程序包更新的时间
	 */
	private Date proSubmitDate;
	
	public String getDeviceId()
	{
		return deviceId;
	}
	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}
	public int getMatch()
	{
		return match;
	}
	public void setMatch(int match)
	{
		this.match = match;
	}
	public Date getProSubmitDate()
	{
		return proSubmitDate;
	}
	public void setProSubmitDate(Date proSubmitDate)
	{
		this.proSubmitDate = proSubmitDate;
	}

}
