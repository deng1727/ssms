/**
 * SSMS
 * com.aspire.dotcard.cysyncdata.vo CYToMMMappingVO.java
 * Jul 19, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.cysyncdata.vo;

/**
 * @author tungke
 *
 */
public class CYToMMMappingVO
{
	
	/**
	 * MM二级分类ID
	 */
	private String appCateId;
	/**
	 * MM二级分类名称
	 */
	private String appCateName;
	/**
	 *MM 一级分类ID
	 */
	private String cateId;
	/**
	 * MM一级分类名称
	 */
	private String cateName;
	
	/**
	 * 创业大赛二级分类名称
	 */
	private String CYAppCateName;
	/**
	 * 创业大赛一级分类ID
	 */
	private String CYCateId;
	/**
	 * @return Returns the appCateId.
	 */
	public String getAppCateId()
	{
		return appCateId;
	}
	/**
	 * @param appCateId The appCateId to set.
	 */
	public void setAppCateId(String appCateId)
	{
		this.appCateId = appCateId;
	}
	/**
	 * @return Returns the appCateName.
	 */
	public String getAppCateName()
	{
		return appCateName;
	}
	/**
	 * @param appCateName The appCateName to set.
	 */
	public void setAppCateName(String appCateName)
	{
		this.appCateName = appCateName;
	}
	/**
	 * @return Returns the cateId.
	 */
	public String getCateId()
	{
		return cateId;
	}
	/**
	 * @param cateId The cateId to set.
	 */
	public void setCateId(String cateId)
	{
		this.cateId = cateId;
	}
	/**
	 * @return Returns the cateName.
	 */
	public String getCateName()
	{
		return cateName;
	}
	/**
	 * @param cateName The cateName to set.
	 */
	public void setCateName(String cateName)
	{
		this.cateName = cateName;
	}
	/**
	 * @return Returns the cYAppCateName.
	 */
	public String getCYAppCateName()
	{
		return CYAppCateName;
	}
	/**
	 * @param appCateName The cYAppCateName to set.
	 */
	public void setCYAppCateName(String appCateName)
	{
		CYAppCateName = appCateName;
	}
	/**
	 * @return Returns the cYCateId.
	 */
	public String getCYCateId()
	{
		return CYCateId;
	}
	/**
	 * @param cateId The cYCateId to set.
	 */
	public void setCYCateId(String cateId)
	{
		CYCateId = cateId;
	}
	
	
	
	
	

}
