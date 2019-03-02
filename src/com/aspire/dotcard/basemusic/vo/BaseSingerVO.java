package com.aspire.dotcard.basemusic.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseSingerVO {

	/**
	 * 歌手ID
	 */
	private String singerId;
	
	/**
	 * 歌手姓名
	 */
	private String singerName;
	
	/**
	 * 简介
	 */
	private String description;
    
    /**
     * 歌手名称首字母
     */
    private String nameLetter;
    
    /**
     * 歌手分类
     */
    private String type;
    
    /**
     * 图片路径
     */
    private String imgUrl;
    
    /**
     * 更新时间
     */
    private Date update;
    
    /**
     * 显示时间
     */
    private String showDate;
    
    
    private String showName;
    
    private String showDesc;
    
    /**
     * 时间设置
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

	public String getSingerId()
	{
		return singerId;
	}

	public void setSingerId(String singerId)
	{
		this.singerId = singerId;
	}

	public String getSingerName()
	{
		return singerName;
	}

	public void setSingerName(String singerName)
	{
		this.singerName = singerName;
		setShowName(singerName);
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
		setShowDesc(description);
	}

	public String getNameLetter()
	{
		return nameLetter;
	}

	public void setNameLetter(String nameLetter)
	{
		this.nameLetter = nameLetter;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getUpdate()
	{
		return update.toString();
	}

	public void setUpdate(Date update)
	{
		this.update = update;
		setShowDate(update);
	}

	public void setShowDate(Date showDate)
	{
		this.showDate = this.sdf.format(showDate);
	}
	
	public String getShowDate()
	{
		return showDate;
	}

	public String getShowName()
	{
		return showName;
	}

	public void setShowName(String showName)
	{
		if(null != showName && showName.length() > 10)
		{
			this.showName = showName.substring(0, 10) + "...";
		}
		else
		{
			this.showName = showName;
		}
	}

	public String getShowDesc()
	{
		return showDesc;
	}

	public void setShowDesc(String showDesc)
	{
		if(null != showDesc && showDesc.length() > 10)
		{
			this.showDesc = showDesc.substring(0, 10) + "...";
		}
		else
		{
			this.showDesc = showDesc;
		}
	}
}
