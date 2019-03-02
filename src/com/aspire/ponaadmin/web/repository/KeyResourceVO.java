/**
 * com.aspire.ponaadmin.web.repository KeyResource.java
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
public class KeyResourceVO
{

	private String keyId;
	private String tid;
	private String keyName;
	private String keyDesc;
	private String value;
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
	 * @return Returns the value.
	 */
	public String getValue()
	{
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	/**
	 * @return Returns the tid.
	 */
	public String getTid()
	{
		return tid;
	}
	/**
	 * @param tid The tid to set.
	 */
	public void setTid(String tid)
	{
		this.tid = tid;
	}
	
	
	
}
