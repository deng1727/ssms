package com.aspire.dotcard.a8;

public class SingerVO
{
	/**
	 * ����ID�����ֵ�Ψһ��ʶ
	 */
	private String id;
	/**
	 * ��������
	 */
	private String name;
	/**
	 * ���ֵ���
	 */
	private String region;
	/**
	 * �������ͣ��и������ƺ͸��ֵ�����϶��ɣ�
	 */
	private String singerZone;
	/**
	 * �������ͣ�ָ�����µ�����
	 */
	private String type;
	/**
	 * ��������ĸ
	 */
	private String firstLetter;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
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
	public String getRegion()
	{
		return region;
	}
	public void setRegion(String region)
	{
		this.region = region;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getFirstLetter()
	{
		return firstLetter;
	}
	public void setFirstLetter(String firstLetter)
	{
		this.firstLetter = firstLetter;
	}
	public String toString()
	{
		StringBuffer sb=new StringBuffer();
	    sb.append("����id��");
	    sb.append(id);
	    sb.append("���������ƣ�");
	    sb.append(name);
	    sb.append("�����ֵ�����");
	    sb.append(region);
	    sb.append("���������ͣ�");
	    sb.append(type);
	    sb.append("����������ĸ��");
	    sb.append(firstLetter);
	    
	    return sb.toString();
	}
	public String getSingerZone()
	{
		return singerZone;
	}
	public void setSingerZone(String singerZone)
	{
		this.singerZone = singerZone;
	}

}
