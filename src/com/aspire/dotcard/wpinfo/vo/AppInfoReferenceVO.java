package com.aspire.dotcard.wpinfo.vo;

public class AppInfoReferenceVO {

	/**
     * 商品id
     */
    private String refId;
    
    /**
     * 节目id
     */
    private String appId;
    
    /**
     * 节目名称
     */
    private String appName;
    /**
     * 价格
     */
    private String appPrice;
  
    /**
     * 货架id
     */
    private String categoryId;
    
    /**
     * 排序id
     */
    private int sortId;
    
    /**
     * 最后修改时间
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
