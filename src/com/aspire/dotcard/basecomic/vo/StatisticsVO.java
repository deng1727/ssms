package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class StatisticsVO extends Validateable implements VO
{
	
	private String id;// 内容标识
	private String type;// 内容类型
	private String downloadNum;// 下载次数
	private String averagemark;// 动漫星级
	private String favoritesNum;// 收藏人数
	private String bookedNum;// 预订人数
	
	private String weekNum;// 点击周次数
	private String monthNum;// 点击月次数
	private String weekFlowersNum;// 鲜花周次数
	private String monthFlowersNum;// 鲜花月次数 0930+
	
	public StatisticsVO(String[] field)
	{
		if (field != null && field.length >= 10)
		{
			this.id = field[0];
			this.type = field[1];
			this.downloadNum = field[2];
			this.weekNum = field[3];
			this.monthNum = field[4];
			this.averagemark = field[5];
			this.weekFlowersNum = field[6];
			this.monthFlowersNum = field[7];
			this.favoritesNum = field[8];
			this.bookedNum = field[9];
			
		}
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getDownloadNum()
	{
		return downloadNum;
	}
	
	public void setDownloadNum(String downloadNum)
	{
		this.downloadNum = downloadNum;
	}
	
	public String getAveragemark()
	{
		return averagemark;
	}
	
	public void setAveragemark(String averagemark)
	{
		this.averagemark = averagemark;
	}
	
	public String getFavoritesNum()
	{
		return favoritesNum;
	}
	
	public void setFavoritesNum(String favoritesNum)
	{
		this.favoritesNum = favoritesNum;
	}
	
	public String getBookedNum()
	{
		return bookedNum;
	}
	
	public void setBookedNum(String bookedNum)
	{
		this.bookedNum = bookedNum;
	}
	
	public void validate()
	{
		if (isEmpty(id))
		{
			this.addFieldError("id不能为空");
		}
		if (isEmpty(type))
		{
			this.addFieldError("type不能为空");
		}
		if (isEmpty(downloadNum))
		{
			this.addFieldError("downloadNum不能为空");
		}
		if (isEmpty(averagemark))
		{
			this.addFieldError("averagemark不能为空");
		}
		if (isEmpty(favoritesNum))
		{
			this.addFieldError("favoritesNum不能为空");
		}
		if (isEmpty(weekFlowersNum))
		{
			this.addFieldError("weekFlowersNum不能为空");
		}
		if (isEmpty(weekNum))
		{
			this.addFieldError("weekNum不能为空");
		}
		if (isEmpty(monthFlowersNum))
		{
			this.addFieldError("monthFlowersNum不能为空");
		}
		if (isEmpty(monthNum))
		{
			this.addFieldError("monthNum不能为空");
		}
		//
		if (type.equals("116")||type.equals("220"))
		{
			this.addFieldError("type ="+type+"类型为116或220，过滤掉");
		}
		
	}
	
	public String getKey()
	{
		return id;
	}
	
	public String getWeekNum()
	{
		return weekNum;
	}
	
	public void setWeekNum(String weekNum)
	{
		this.weekNum = weekNum;
	}
	
	public String getMonthNum()
	{
		return monthNum;
	}
	
	public void setMonthNum(String monthNum)
	{
		this.monthNum = monthNum;
	}
	
	public String getWeekFlowersNum()
	{
		return weekFlowersNum;
	}
	
	public void setWeekFlowersNum(String weekFlowersNum)
	{
		this.weekFlowersNum = weekFlowersNum;
	}
	
	public String getMonthFlowersNum()
	{
		return monthFlowersNum;
	}
	
	public void setMonthFlowersNum(String monthFlowersNum)
	{
		this.monthFlowersNum = monthFlowersNum;
	}
	
}
