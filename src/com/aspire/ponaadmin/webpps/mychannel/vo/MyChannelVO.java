package com.aspire.ponaadmin.webpps.mychannel.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyChannelVO {

	public MyChannelVO() {
	}

	// ����Id
	private String channelId;

	// ��������
	private String channelName;

	// ��������
	private String channelType;

	// ����ʱ��
	private Date createDate;

	private String channelDesc;

	public String getChannelDesc() {
		if (channelDesc == null)
			return "";
		return channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	/**
	 * ʱ���ʽ��
	 */
	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getCreateDate() {
		if (createDate == null) {
			return "";
		}
		return format.format(createDate);
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
