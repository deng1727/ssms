package com.aspire.ponaadmin.web.dataexport.sqlexport.VO;

public class DataExportGroupVO
{
	/**
	 * 分组id
	 */
	private String groupId;
	
	/**
	 * 当前分组发送邮件地址
	 */
	private String toMail;
	
	/**
	 * 当前分组发送邮件标题
	 */
	private String mailTitle;
	
	/**
	 * 当前分组启动时间
	 */
	private String startTime;
	
	/**
	 * 执行时间类型 1:每天 2:每周
	 */
	private String timeType;
	
	/**
	 * 当执行时间类型为2时，当前字段对应有意义，为周几执行
	 */
	private String timeTypeCon;
	
	/**
	 * 是否上传0：否上传 其它为FTPID
	 */
	private String ftpId;
	
	/**
	 * 用于发起通知的url地址
	 */
	private String url;
	
	/**
	 * FTP对象
	 */
	private DataExportFTPVO dataExportFtpVo;

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public String getToMail()
	{
		return toMail;
	}

	public void setToMail(String toMail)
	{
		this.toMail = toMail;
	}

	public String getMailTitle()
	{
		return mailTitle;
	}

	public void setMailTitle(String mailTitle)
	{
		this.mailTitle = mailTitle;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getTimeType()
	{
		return timeType;
	}

	public void setTimeType(String timeType)
	{
		this.timeType = timeType;
	}

	public String getTimeTypeCon()
	{
		return timeTypeCon;
	}

	public void setTimeTypeCon(String timeTypeCon)
	{
		this.timeTypeCon = timeTypeCon;
	}

	public String getFtpId()
	{
		return ftpId;
	}

	public void setFtpId(String ftpId)
	{
		this.ftpId = ftpId;
	}

	public DataExportFTPVO getDataExportFtpVo()
	{
		return dataExportFtpVo;
	}

	public void setDataExportFtpVo(DataExportFTPVO dataExportFtpVo)
	{
		this.dataExportFtpVo = dataExportFtpVo;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
