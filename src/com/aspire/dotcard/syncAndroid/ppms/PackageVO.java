package com.aspire.dotcard.syncAndroid.ppms;

public class PackageVO {

	/**
	 * ����
	 */
	private String packageName;
	/**
	 * �汾��
	 */
	private String versionCode;
	/**
	 * ֤��md5
	 */
	private String cermd5;
	/**
	 * �Ƿ��ϼ�:1�ϼ�;0�¼�(���޴��ֶ�,�ɲ�����)
	 */
	private int online;
	/**
	 * �Ʒ�:1���;0����(pushtype=1��2��3ʱ����)
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
