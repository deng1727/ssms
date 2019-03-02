package com.aspire.dotcard.syncAndroid.autosync.vo;

public class AutoVO
{
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 当前货架id
	 */
	private String categoryId;
	
	/**
	 * 当货架为空时是否通知对方 0：是，1：否
	 */
	private String isNullByAuto;
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getCategoryId()
	{
		return categoryId;
	}
	
	public void setCategoryId(String categoryId)
	{
		this.categoryId = categoryId;
	}
	
	public String getIsNullByAuto()
	{
		return isNullByAuto;
	}
	
	public void setIsNullByAuto(String isNullByAuto)
	{
		this.isNullByAuto = isNullByAuto;
	}
}
