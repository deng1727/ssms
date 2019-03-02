package com.aspire.ponaadmin.web.repository;

/**
 * 内容产品VO类
 * 
 * @author liuhuiming
 * 
 */
public class ContentGoodsInfo
{
	// 内容对应的产品id
	private String	goodsid			= null;

	// 产品所在的类型id
	private String	categoryId		= null;

	private String	categoryPath	= null;

	public String getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId( String categoryId)
	{
		this.categoryId = categoryId;
	}

	public String getGoodsid()
	{
		return goodsid;
	}

	public void setGoodsid( String goodsid)
	{
		this.goodsid = goodsid;
	}

	public String getCategoryPath()
	{
		return categoryPath;
	}

	public void setCategoryPath( String categoryPath)
	{
		this.categoryPath = categoryPath;
	}
}
