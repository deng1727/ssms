package com.aspire.dotcard.wpinfo.vo;

public class AppInfoReferenceVO {

	/**
     * ��Ʒid
     */
    private String refId;
    
    /**
     * ��Ŀid
     */
    private String appId;
    
    /**
     * ��Ŀ����
     */
    private String appName;
    /**
     * �۸�
     */
    private String appPrice;
  
    /**
     * ����id
     */
    private String categoryId;
    
    /**
     * ����id
     */
    private int sortId;
    
    /**
     * ����޸�ʱ��
     */
    private String lastUpTime;

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppPrice() {
		return appPrice;
	}

	public void setAppPrice(String appPrice) {
		this.appPrice = appPrice;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public String getLastUpTime() {
		return lastUpTime;
	}

	public void setLastUpTime(String lastUpTime) {
		this.lastUpTime = lastUpTime;
	}

}
