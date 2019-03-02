package com.aspire.ponaadmin.web.channelUser.vo;

import java.io.Serializable;

public class ChannelCategoryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3085290849565680978L;
	private String id;
	private String categoryId;
	private String categoryName;
	private String parentcategoryId;
	public String getId() {
		return id;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getParentcategoryId() {
		return parentcategoryId;
	}
	public void setParentcategoryId(String parentcategoryId) {
		this.parentcategoryId = parentcategoryId;
	}
	
	
	
}
