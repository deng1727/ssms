package com.aspire.ponaadmin.web.comic.vo;


public class ReferenceVO{
	private String id;
	private String contentId;
	private String contentName;
	private String categoryId;
	private String sortId;
	private String flowTime;
	private String type;
	private String portal;
	private String verify_status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	public String getFlowTime() {
		return flowTime;
	}
	public void setFlowTime(String flowTime) {
		this.flowTime = flowTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPortal() {
		return portal;
	}
	public void setPortal(String portal) {
		this.portal = portal;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getVerify_status() {
		return verify_status;
	}
	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}	
}
