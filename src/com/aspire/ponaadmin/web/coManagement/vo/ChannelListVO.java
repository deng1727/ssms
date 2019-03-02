package com.aspire.ponaadmin.web.coManagement.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChannelListVO {

	public ChannelListVO() {
	}
	//渠道Id
	private String channelId;
	
	//渠道名称
	private String channelName;
	
	//渠道类型
	private String channelType;
	
	//合作商Id
	private String channelsId;
	
	//合作商名称
	private String channelsName;
	
	//申请时间
	private Date createDate;
	
	//渠道商描述
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
	 * 时间格式化
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
