package com.aspire.dotcard.syncData.vo;

import java.util.Date;

/**
 * ���ڱ��������Դ��Ϣ����Ҫ���ڻ�������
 * @author zhangwei
 *
 */
public class DeviceResourceVO
{
	private String deviceId;
	/**
	 * 1 ��ȷ�����ϵ��2 ģ�������ϵ��3 ��ȷ��չ�����ϵ��Ĭ��Ϊ1
	 */
	private int match;
	/**
	 * ��������µ�ʱ��
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
