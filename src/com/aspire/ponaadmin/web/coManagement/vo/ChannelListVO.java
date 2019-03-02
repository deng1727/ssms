package com.aspire.ponaadmin.web.coManagement.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChannelListVO {

	public ChannelListVO() {
	}
	//����Id
	private String channelId;
	
	//��������
	private String channelName;
	
	//��������
	private String channelType;
	
	//������Id
	private String channelsId;
	
	//����������
	private String channelsName;
	
	//����ʱ��
	private Date createDate;
	
	//����������
	private String channelDesc;
	
	public String getChannelDesc() {
		if(channelDesc != null){		
			return channelDesc;
		}else{
			return "";
		}
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}
	/**
	 * ʱ���ʽ��
	 */
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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

	public String getChannelsId() {
		return channelsId;
	}

	public void setChannelsId(String channelsId) {
		this.channelsId = channelsId;
	}

	public String getChannelsName() {
		return channelsName;
	}

	public void setChannelsName(String channelsName) {
		this.channelsName = channelsName;
	}

	public String getCreateDate() {
		if(createDate!=null){
			return format.format(createDate);
		}else{
			return "";
		}	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

}
