package com.aspire.dotcard.a8;

public class TopListVO
{
	/**
	 * ¸èÇúid
	 */
	private String id;
	/**
	 * °ñµ¥Ãû³Æ
	 */
	private String cateName;
	/**
	 * ¸èÇúÃû³Æ
	 */
	private String name;
	/**
	 * ÅÅĞòĞòºÅ
	 */
	private int sortId;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getCateName()
	{
		return cateName;
	}
	public void setCateName(String cateName)
	{
		this.cateName = cateName;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getSortId()
	{
		return sortId;
	}
	public void setSortId(int sortId)
	{
		this.sortId = sortId;
	}
	public String toString()
	{
		StringBuffer sb=new StringBuffer();
	    sb.append("°ñµ¥Ãû³Æ£º");
	    sb.append(cateName);
	    sb.append("£¬¸èÇúID£º");
	    sb.append(id);
	    sb.append("£¬¸èÇúÃû³Æ£º");
	    sb.append(name);
	    sb.append("£¬ÅÅĞòĞòºÅ£º");
	    sb.append(sortId);
	    
	    return sb.toString();
	}

}
