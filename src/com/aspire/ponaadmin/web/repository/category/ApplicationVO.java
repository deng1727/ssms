package com.aspire.ponaadmin.web.repository.category;

public class ApplicationVO {

	public ApplicationVO() {
	}
	/**
	 * Ӧ��ID
	 */
	private String appid;
	/**
	 * Ӧ������
	 */
	private String appName;
	/**
	 * ��Ӧ�ö�Ӧ����ʵλ��
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
