package com.aspire.ponaadmin.web.repository.category;

public class ApplicationVO {

	public ApplicationVO() {
	}
	/**
	 * 应用ID
	 */
	private String appid;
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 该应用对应的真实位置
	 */
	private String position;
	
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
