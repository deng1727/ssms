package com.aspire.dotcard.a8;

public class TopListVO
{
	/**
	 * ����id
	 */
	private String id;
	/**
	 * ������
	 */
	private String cateName;
	/**
	 * ��������
	 */
	private String name;
	/**
	 * �������
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
	    sb.append("�����ƣ�");
	    sb.append(cateName);
	    sb.append("������ID��");
	    sb.append(id);
	    sb.append("���������ƣ�");
	    sb.append(name);
	    sb.append("��������ţ�");
	    sb.append(sortId);
	    
	    return sb.toString();
	}

}
