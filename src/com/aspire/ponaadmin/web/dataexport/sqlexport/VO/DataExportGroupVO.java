package com.aspire.ponaadmin.web.dataexport.sqlexport.VO;

public class DataExportGroupVO
{
	/**
	 * ����id
	 */
	private String groupId;
	
	/**
	 * ��ǰ���鷢���ʼ���ַ
	 */
	private String toMail;
	
	/**
	 * ��ǰ���鷢���ʼ�����
	 */
	private String mailTitle;
	
	/**
	 * ��ǰ��������ʱ��
	 */
	private String startTime;
	
	/**
	 * ִ��ʱ������ 1:ÿ�� 2:ÿ��
	 */
	private String timeType;
	
	/**
	 * ��ִ��ʱ������Ϊ2ʱ����ǰ�ֶζ�Ӧ�����壬Ϊ�ܼ�ִ��
	 */
	private String timeTypeCon;
	
	/**
	 * �Ƿ��ϴ�0�����ϴ� ����ΪFTPID
	 */
	private String ftpId;
	
	/**
	 * ���ڷ���֪ͨ��url��ַ
	 */
	private String url;
	
	/**
	 * FTP����
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
