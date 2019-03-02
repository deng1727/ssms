package com.aspire.dotcard.a8;

public class SingerVO
{
	/**
	 * 歌手ID，歌手的唯一标识
	 */
	private String id;
	/**
	 * 歌手名称
	 */
	private String name;
	/**
	 * 歌手地区
	 */
	private String region;
	/**
	 * 歌手类型（有歌手名称和歌手地区组合而成）
	 */
	private String singerZone;
	/**
	 * 歌手类型，指分类下的类型
	 */
	private String type;
	/**
	 * 歌手首字母
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
	    sb.append("歌手id：");
	    sb.append(id);
	    sb.append("，歌手名称：");
	    sb.append(name);
	    sb.append("，歌手地区：");
	    sb.append(region);
	    sb.append("，歌手类型：");
	    sb.append(type);
	    sb.append("，歌手首字母：");
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
