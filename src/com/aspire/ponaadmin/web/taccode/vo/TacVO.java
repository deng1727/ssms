package com.aspire.ponaadmin.web.taccode.vo;

public class TacVO {

	/**
     * 主键id
     */
    private String id;
    /**
     * 渠道id
     */
    private String channelId;
    /**
     * 渠道名
     */
    private String channelName;
	/**
     * TAC码
     */
    private String tacCode;
    
    /**
     * 手机品牌
     */
    private String brand;
    
    /**
     * 手机型号
     */
    private String device;
    
    /**
     * 创建时间
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
