package com.aspire.ponaadmin.web.repository;

/**
 * ���ݲ�ƷVO��
 * 
 * @author liuhuiming
 * 
 */
public class ContentGoodsInfo
{
	// ���ݶ�Ӧ�Ĳ�Ʒid
	private String	goodsid			= null;

	// ��Ʒ���ڵ�����id
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
