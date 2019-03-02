package com.aspire.ponaadmin.web.dataexport.marketing;
/**
 * ¶ÔÓ¦±í×Ö¶Ît_export_toplist
 * @author zhangwei
 *
 */
public class ExportTopListVO
{
	private int id;
	private String name;
	private String categoryid;
	private int count;
	private String condition;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCategoryid()
	{
		return categoryid;
	}
	public void setCategoryid(String categoryid)
	{
		this.categoryid = categoryid;
	}
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	public String getCondition()
	{
		return condition;
	}
	public void setCondition(String condition)
	{
		this.condition = condition;
	}
	

}
