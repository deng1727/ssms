/**
 * com.aspire.dotcard.baseVideo.vo VideoProductVO.java
 * Jul 5, 2012
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.dotcard.baseVideo.vo;

/**
 * @author tungke
 * ÊÓÆµ²úÆ·VO
 */
public class VideoProductVO
{

	private String productID;
	private String productName;
	private String feeType;
	private String startdate;
	private String feeDesc;
	private int fee;
	private String cpid;
	private String freeType;
	private String freeEffecTime;
	private String freeTimeFail;
	
	public String getFreeType()
	{
		return freeType;
	}
	public void setFreeType(String freeType)
	{
		this.freeType = freeType;
	}
	public String getFreeEffecTime()
	{
		return freeEffecTime;
	}
	public void setFreeEffecTime(String freeEffecTime)
	{
		this.freeEffecTime = freeEffecTime;
	}
	public String getFreeTimeFail()
	{
		return freeTimeFail;
	}
	public void setFreeTimeFail(String freeTimeFail)
	{
		this.freeTimeFail = freeTimeFail;
	}
	/**
	 * @return Returns the cpid.
	 */
	public String getCpid()
	{
		return cpid;
	}
	/**
	 * @param cpid The cpid to set.
	 */
	public void setCpid(String cpid)
	{
		this.cpid = cpid;
	}
	/**
	 * @return Returns the fee.
	 */
	public int getFee()
	{
		return fee;
	}
	/**
	 * @param fee The fee to set.
	 */
	public void setFee(int fee)
	{
		this.fee = fee;
	}
	/**
	 * @return Returns the feeDesc.
	 */
	public String getFeeDesc()
	{
		return feeDesc;
	}
	/**
	 * @param feeDesc The feeDesc to set.
	 */
	public void setFeeDesc(String feeDesc)
	{
		this.feeDesc = feeDesc;
	}
	/**
	 * @return Returns the feeType.
	 */
	public String getFeeType()
	{
		return feeType;
	}
	/**
	 * @param feeType The feeType to set.
	 */
	public void setFeeType(String feeType)
	{
		this.feeType = feeType;
	}
	/**
	 * @return Returns the productID.
	 */
	public String getProductID()
	{
		return productID;
	}
	/**
	 * @param productID The productID to set.
	 */
	public void setProductID(String productID)
	{
		this.productID = productID;
	}
	/**
	 * @return Returns the productName.
	 */
	public String getProductName()
	{
		return productName;
	}
	/**
	 * @param productName The productName to set.
	 */
	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	/**
	 * @return Returns the startdate.
	 */
	public String getStartdate()
	{
		return startdate;
	}
	/**
	 * @param startdate The startdate to set.
	 */
	public void setStartdate(String startdate)
	{
		this.startdate = startdate;
	}
	
	
	
}
