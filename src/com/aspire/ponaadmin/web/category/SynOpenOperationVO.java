/**
 * 
 */
package com.aspire.ponaadmin.web.category;

/**
 * @author wangminlong
 *
 */
public class SynOpenOperationVO
{
	/**
	 * 货架id
	 */
	private String categoryId;
	
	/**
	 * 渠道商代码
	 */
	private String openChannelCode;
	
	/**
	 * 渠道商名称
	 */
	private String companyName;
	
	/**
	 * 渠道ID
	 */
	private String channelId;
	
	/**
	 * 是否自动上架：0-否；1-是
	 */
	private String isAuto;
	
	public String getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(String categoryId)
	{
		this.categoryId = categoryId;
	}

	public String getOpenChannelCode()
	{
		return openChannelCode;
	}

	public void setOpenChannelCode(String openChannelCode)
	{
		this.openChannelCode = openChannelCode;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getChannelId()
	{
		return channelId;
	}

	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}

	public String getIsAuto()
	{
		return isAuto;
	}

	public void setIsAuto(String isAuto)
	{
		this.isAuto = isAuto;
	}
}
