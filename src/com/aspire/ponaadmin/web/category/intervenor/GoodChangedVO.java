package com.aspire.ponaadmin.web.category.intervenor;

public class GoodChangedVO
{
	public final static  int Status_ADD=1;
	public final static  int Status_DEL=-1;
	public final static  int Status_Existed=0;
	private String id;//��Ʒ����id
	private int sortId;//��Ʒ����ֵ
	private String refNodeId;//ָ�����ݵ�id
	private int status;//��Ʒ���״̬��-1ɾ����0˳�������1����
	private int variation;///����ı��ֵ
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
