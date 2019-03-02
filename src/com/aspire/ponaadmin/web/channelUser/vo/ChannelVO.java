package com.aspire.ponaadmin.web.channelUser.vo;

import java.util.Date;


/**
 * 渠道商账号表
 * @author shiyangwang
 *
 */
public class ChannelVO {

	/**
	 * 渠道商id
	 */
	private String channelsId;
	/**
	 * 渠道商名称
	 */
	private String channelsName;
	/**
	 * 所属（父）渠道商id
	 */
	private String parentChannelsId;
	/**
	 * 所属（父）渠道商名称
	 */
	private String parentChannelsName;
	/**
	 * 渠道商描述
	 */
	private String channelsDesc;
	/**
	 * 渠道商账号
	 */
	private String channelsNO;
	/**
	 * 渠道商密码
	 */
	private String channelsPwd;
	/**
	 * 渠道商状态：0-正常，1-下线
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date modifyDate;
	/**
	 * 渠道商类型，0-表示终端公司，1-非终端公司
	 */
	private String moType;
	
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
	public String getParentChannelsId() {
		return parentChannelsId;
	}
	public void setParentChannelsId(String parentChannelsId) {
		this.parentChannelsId = parentChannelsId;
	}
	public String getParentChannelsName() {
		return parentChannelsName;
	}
	public void setParentChannelsName(String parentChannelsName) {
		this.parentChannelsName = parentChannelsName;
	}
	public String getChannelsDesc() {
		return channelsDesc;
	}
	public void setChannelsDesc(String channelsDesc) {
		this.channelsDesc = channelsDesc;
	}
	public String getChannelsNO() {
		return channelsNO;
	}
	public void setChannelsNO(String channelsNO) {
		this.channelsNO = channelsNO;
	}
	public String getChannelsPwd() {
		return channelsPwd;
	}
	public void setChannelsPwd(String channelsPwd) {
		this.channelsPwd = channelsPwd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getMoType() {
		return moType;
	}
	public void setMoType(String moType) {
		this.moType = moType;
	}
}
