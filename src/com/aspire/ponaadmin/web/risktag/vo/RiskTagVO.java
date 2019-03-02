package com.aspire.ponaadmin.web.risktag.vo;


public class RiskTagVO {
	
	/**
     * contentID
     */
	private String contentID;
	/**
     * name
     */
	private String name;
	/**
     * 修改时间
     */
	private String risktag;
	/**
     * 是否屏蔽
     */
	private String isblack;
	/**
	 * 创建时间
	 */
	private String time ;
	
	private String company;
	
	private String type;
	
	private String stats ;

	public String getContentID() {
		return contentID;
	}

	public void setContentID(String contentID) {
		this.contentID = contentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRisktag() {
		return risktag;
	}

	public void setRisktag(String risktag) {
		this.risktag = risktag;
	}

	public String getIsblack() {
		return isblack;
	}

	public void setIsblack(String isblack) {
		this.isblack = isblack;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}
	
	

}
