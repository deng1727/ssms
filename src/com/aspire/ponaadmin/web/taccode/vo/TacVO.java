package com.aspire.ponaadmin.web.taccode.vo;

public class TacVO {

	/**
     * ����id
     */
    private String id;
    /**
     * ����id
     */
    private String channelId;
    /**
     * ������
     */
    private String channelName;
	/**
     * TAC��
     */
    private String tacCode;
    
    /**
     * �ֻ�Ʒ��
     */
    private String brand;
    
    /**
     * �ֻ��ͺ�
     */
    private String device;
    
    /**
     * ����ʱ��
     */
    private String createTime;

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTacCode() {
		return tacCode;
	}

	public void setTacCode(String tacCode) {
		this.tacCode = tacCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
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
	
}
