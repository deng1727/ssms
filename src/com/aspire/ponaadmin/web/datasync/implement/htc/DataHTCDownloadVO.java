package com.aspire.ponaadmin.web.datasync.implement.htc;

public class DataHTCDownloadVO
{	
	/**
	 * ��ҵ����
	 */
	private String apCode;
	
	/**
	 * ҵ��ƽ̨Ӧ�ñ���
	 */
	private String appId;
	
	/**
	 * MMӦ�ô���
	 */
	private String mmContentId;
	
	/**
	 * ���ش���
	 */
	private String downCount;

	public String getApCode()
	{
		return apCode;
	}

	public void setApCode(String apCode)
	{
		this.apCode = apCode;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getMmContentId()
	{
		return mmContentId;
	}

	public void setMmContentId(String mmContentId)
	{
		this.mmContentId = mmContentId;
	}

	public String getDownCount()
	{
		return downCount;
	}

	public void setDownCount(String downCount)
	{
		this.downCount = downCount;
	}
}
