package com.aspire.ponaadmin.web.repository;

public class ReferenceVO {
	private String id;
	private String goodsId;
	private String categoryId;
	private String sortId;
	private String verify_status;
	private String delFlag;
	private String refNodeID;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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
	public String getVerify_status() {
		return verify_status;
	}
	public void setVerify_status(String verifyStatus) {
		verify_status = verifyStatus;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getRefNodeID() {
		return refNodeID;
	}
	public void setRefNodeID(String refNodeID) {
		this.refNodeID = refNodeID;
	}
	
}
