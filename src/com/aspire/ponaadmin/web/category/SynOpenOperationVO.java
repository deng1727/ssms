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
	 * ����id
	 */
	private String categoryId;
	
	/**
	 * �����̴���
	 */
	private String openChannelCode;
	
	/**
	 * ����������
	 */
	private String companyName;
	
	/**
	 * ����ID
	 */
	private String channelId;
	
	/**
	 * �Ƿ��Զ��ϼܣ�0-��1-��
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
