package com.aspire.ponaadmin.web.channelUser.vo;

import java.util.Date;


/**
 * �������˺ű�
 * @author shiyangwang
 *
 */
public class ChannelVO {

	/**
	 * ������id
	 */
	private String channelsId;
	/**
	 * ����������
	 */
	private String channelsName;
	/**
	 * ����������������id
	 */
	private String parentChannelsId;
	/**
	 * ��������������������
	 */
	private String parentChannelsName;
	/**
	 * ����������
	 */
	private String channelsDesc;
	/**
	 * �������˺�
	 */
	private String channelsNO;
	/**
	 * ����������
	 */
	private String channelsPwd;
	/**
	 * ������״̬��0-������1-����
	 */
	private String status;
	/**
	 * ����ʱ��
	 */
	private Date createDate;
	/**
	 * �޸�ʱ��
	 */
	private Date modifyDate;
	/**
	 * ���������ͣ�0-��ʾ�ն˹�˾��1-���ն˹�˾
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
