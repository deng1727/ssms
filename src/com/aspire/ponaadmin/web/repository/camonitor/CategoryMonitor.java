/**
 * SSMS
 * com.aspire.ponaadmin.web.repository.camonitor CategoryMonitor.java
 * Apr 20, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.repository.camonitor;

import java.io.Serializable;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke
 *
 */
public class CategoryMonitor implements Serializable
{

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(CategoryMonitor.class);

	public String id;
	public String name;
	public String categoryid;
	public String parentcategoryid;
	public String categorytype;
	public String fullName;
	
	/**
	 * @return Returns the categoryid.
	 */
	public String getCategoryid()
	{
		return categoryid;
	}

	/**
	 * @param categoryid The categoryid to set.
	 */
	public void setCategoryid(String categoryid)
	{
		this.categoryid = categoryid;
	}

	/**
	 * @return Returns the categorytype.
	 */
	public String getCategorytype()
	{
		return categorytype;
	}

	/**
	 * @param categorytype The categorytype to set.
	 */
	public void setCategorytype(String categorytype)
	{
		this.categorytype = categorytype;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Returns the parentcategoryid.
	 */
	public String getParentcategoryid()
	{
		return parentcategoryid;
	}

	/**
	 * @param parentcategoryid The parentcategoryid to set.
	 */
	public void setParentcategoryid(String parentcategoryid)
	{
		this.parentcategoryid = parentcategoryid;
	}

	/**
	 * @return Returns the fullName.
	 */
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * @param fullName The fullName to set.
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

}
