package com.aspire.ponaadmin.web.lockLocation.vo;

public class RefrenceVO {
	private String id;
	private String contentId;
	private String name;
	private String categoryId;
	private int sortId;
	private String refNodeId;
	private int lockNum;
	private int isLock;
	private String lockTime;
	private String lockUser;
	
	
	public String getLockUser() {
		return lockUser;
	}
	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}
	public String getId() {
		return id;
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
	public void setId(String id) {
		this.id = id;
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
	public String getRefNodeId() {
		return refNodeId;
	}
	public void setRefNodeId(String refNodeId) {
		this.refNodeId = refNodeId;
	}
	public int getLockNum() {
		return lockNum;
	}
	public void setLockNum(int lockNum) {
		this.lockNum = lockNum;
	}
	public int getIsLock() {
		return isLock;
	}
	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}
	public String getLockTime() {
		return lockTime;
	}
	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}
	
	
}
