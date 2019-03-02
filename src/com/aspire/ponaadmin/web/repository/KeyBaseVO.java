/**
 * com.aspire.ponaadmin.web.repository KeyBaseVO.java
 * May 21, 2011
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.repository;

/**
 * @author tungke
 *
 */
public class KeyBaseVO
{

	private String keyId;
	private String 	keyName;
	private String keyTable;
	private String keyDesc;
	/**
	 * @return Returns the keyDesc.
	 */
	public String getKeyDesc()
	{
		return keyDesc;
	}
	/**
	 * @param keyDesc The keyDesc to set.
	 */
	public void setKeyDesc(String keyDesc)
	{
		this.keyDesc = keyDesc;
	}
	/**
	 * @return Returns the keyId.
	 */
	public String getKeyId()
	{
		return keyId;
	}
	/**
	 * @param keyId The keyId to set.
	 */
	public void setKeyId(String keyId)
	{
		this.keyId = keyId;
	}
	/**
	 * @return Returns the keyName.
	 */
	public String getKeyName()
	{
		return keyName;
	}
	/**
	 * @param keyName The keyName to set.
	 */
	public void setKeyName(String keyName)
	{
		this.keyName = keyName;
	}
	/**
	 * @return Returns the keyTable.
	 */
	public String getKeyTable()
	{
		return keyTable;
	}
	/**
	 * @param keyTable The keyTable to set.
	 */
	public void setKeyTable(String keyTable)
	{
		this.keyTable = keyTable;
	}
	
	
}
