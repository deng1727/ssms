package com.aspire.ponaadmin.web.category;
/**
 * 用户货架自动更新中存储中间信息。
 * @author zhangwei
 *
 */
public class CategoryUpdateVO
{
	/**
	 * 商品的id
	 */
	private String id;
	/**
	 * goodsid
	 */
	private String goodsid;
	/**
	 * 应用提供商
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
