package com.aspire.ponaadmin.web.datasync.implement.htc;

public class DataHTCDownloadVO
{	
	/**
	 * 企业代码
	 */
	private String apCode;
	
	/**
	 * 业务平台应用编码
	 */
	private String appId;
	
	/**
	 * MM应用代码
	 */
	private String mmContentId;
	
	/**
	 * 下载次数
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
