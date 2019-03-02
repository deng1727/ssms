package com.aspire.ponaadmin.web.auditManagement.vo;

public class AuditDetailsVO {
	
	private String contentId;
	
	private String name;
	
	private String cateName;
	
	private String marketDate;
	
	private String loadDate;
	
	private String spName;
	
	private String refNodeId;
	
	private String categoryId;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getRefNodeId() {
		return refNodeId;
	}

	public void setRefNodeId(String refNodeId) {
		this.refNodeId = refNodeId;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public AuditDetailsVO() {
		super();
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getMarketDate() {
		return marketDate;
	}

	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}

	public String getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(String loadDate) {
		this.loadDate = loadDate;
	}

}
