package com.aspire.dotcard.basecomic.vo;

public class ReferenceVO {
	private String categoryId;
	private String contentId;
	private String sortid;
	private String type;//区分是那个自定义榜单的商品（FIRST,RANK,BRAND,SYSTEM）这里是为了删除商品表上数据的辅助字段。
	private String portal;
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getSortid() {
		return sortid;
	}
	public void setSortid(String sortid) {
		this.sortid = sortid;
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
	
	
	
	

}
