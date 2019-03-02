package com.aspire.dotcard.syncAndroid.ppms;

public class PackageVO {

	/**
	 * 包名
	 */
	private String packageName;
	/**
	 * 版本号
	 */
	private String versionCode;
	/**
	 * 证书md5
	 */
	private String cermd5;
	/**
	 * 是否上架:1上架;0下架(若无此字段,可不发送)
	 */
	private int online;
	/**
	 * 计费:1免费;0付费(pushtype=1或2或3时存在)
	 */
	private String deduction;
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getCermd5() {
		return cermd5;
	}
	public void setCermd5(String cermd5) {
		this.cermd5 = cermd5;
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
	public String getDeduction() {
		return deduction;
	}
	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}
}
