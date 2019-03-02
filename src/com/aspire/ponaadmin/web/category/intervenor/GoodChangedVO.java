package com.aspire.ponaadmin.web.category.intervenor;

public class GoodChangedVO
{
	public final static  int Status_ADD=1;
	public final static  int Status_DEL=-1;
	public final static  int Status_Existed=0;
	private String id;//商品内码id
	private int sortId;//商品排序值
	private String refNodeId;//指向内容的id
	private int status;//商品变更状态，-1删除，0顺序调整，1新增
	private int variation;///排序的变更值
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public int getSortId()
	{
		return sortId;
	}
	public void setSortId(int sortId)
	{
		this.sortId = sortId;
	}
	public int  getStatus()
	{
		return status;
	}
	public void setStatus(int  status)
	{
		this.status = status;
	}
	public String getRefNodeId()
	{
		return refNodeId;
	}
	public void setRefNodeId(String refNodeId)
	{
		this.refNodeId = refNodeId;
	}
	public int getVariation()
	{
		return variation;
	}
	public void setVariation(int variation)
	{
		this.variation = variation;
	}

}
