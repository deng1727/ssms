package com.aspire.ponaadmin.web.category;
/**
 * �û������Զ������д洢�м���Ϣ��
 * @author zhangwei
 *
 */
public class CategoryUpdateVO
{
	/**
	 * ��Ʒ��id
	 */
	private String id;
	/**
	 * goodsid
	 */
	private String goodsid;
	/**
	 * Ӧ���ṩ��
	 */
	private String spName;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getGoodsid()
	{
		return goodsid;
	}
	public void setGoodsid(String goodsid)
	{
		this.goodsid = goodsid;
	}
	public String getSpName()
	{
		return spName;
	}
	public void setSpName(String spName)
	{
		this.spName = spName;
	}
	public boolean equals(Object object)
	{
		if(object instanceof CategoryUpdateVO)
		{
			CategoryUpdateVO vo=(CategoryUpdateVO) object;
			if(vo.getId().equals(this.id))
			{
				return true;
			}else
			{
				return false;
			}
		}else
		{
			return false;
		}
		
	}
	public int hashCode()
	{
		return this.id.hashCode();
	}
}
