package com.aspire.dotcard.syncAndroid.autosync.vo;

public class AutoVO
{
	/**
	 * id
	 */
	private String id;
	
	/**
	 * ��ǰ����id
	 */
	private String categoryId;
	
	/**
	 * ������Ϊ��ʱ�Ƿ�֪ͨ�Է� 0���ǣ�1����
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
