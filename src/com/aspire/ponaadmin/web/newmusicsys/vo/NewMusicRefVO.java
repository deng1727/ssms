/*
 * 文件名：NewMusicRefVO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.newmusicsys.vo;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class NewMusicRefVO
{
    /**
     * 音乐id
     */
    private String musicId;
    
    /**
     * 货架id
     */
    private String categoryId;
    
    /**
     * 音乐乐称
     */
    private String musicName;
    
    /**
     * 音乐图片地址
     */
    private String musicPic;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 排序id
     */
    private int sortId;
    
    /**
     * 歌手信息
     */
    private String singer;
    
    /**
     * 有效期
     */
    private String validity;
    
    /**
     * 更新时间
     */
    private String updateTime;
    
    /**
     * 音乐标签信息
     */
    private String tags;

    /**
     * 彩铃类型
     */
    private Integer colorType;
    
    private int onlineType;
    
    private int coloType;
    
    private int ringType;
    
    private int songType;
    
    private String showCreateTime;
    
    private String showCreateTime_Y;
    private String showCreateTime_M;
    private String showCreateTime_D;
    
    private String verify_status;
    
    public String getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}

	public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getMusicId()
    {
        return musicId;
    }

    public void setMusicId(String musicId)
    {
        this.musicId = musicId;
    }

    public String getMusicName()
    {
        return musicName;
    }

    public void setMusicName(String musicName)
    {
        this.musicName = musicName;
    }

    public String getMusicPic()
    {
        return musicPic;
    }

    public void setMusicPic(String musicPic)
    {
        this.musicPic = musicPic;
    }

    public String getSinger()
    {
        return singer;
    }

    public void setSinger(String singer)
    {
        this.singer = singer;
    }

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {
        this.sortId = sortId;
    }

    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getValidity()
    {
        return validity;
    }

    public void setValidity(String validity)
    {
        this.validity = validity;
    }

	/**
	 * @return Returns the colorType.
	 */
	public Integer getColorType()
	{
		return colorType;
	}

	/**
	 * @param colorType The colorType to set.
	 */
	public void setColorType(Integer colorType)
	{
		this.colorType = colorType;
	}

	public int getOnlineType()
	{
		return onlineType;
	}

	public void setOnlineType(int onlineType)
	{
		this.onlineType = onlineType;
	}

	public int getColoType()
	{
		return coloType;
	}

	public void setColoType(int coloType)
	{
		this.coloType = coloType;
	}

	public int getRingType()
	{
		return ringType;
	}

	public void setRingType(int ringType)
	{
		this.ringType = ringType;
	}

	public int getSongType()
	{
		return songType;
	}

	public void setSongType(int songType)
	{
		this.songType = songType;
	}

	public void setShowCreateTime(String showCreateTime)
	{
		this.showCreateTime = showCreateTime;
		if(!"".equals(showCreateTime))
		{
			this.showCreateTime_Y = showCreateTime.substring(0,4);
			this.showCreateTime_M = showCreateTime.substring(5,7);
			this.showCreateTime_D = showCreateTime.substring(8,10);
		}
	}

	public String getShowCreateTime()
	{
		return showCreateTime;
	}

	public String getShowCreateTime_Y()
	{
		return showCreateTime_Y;
	}

	public String getShowCreateTime_M()
	{
		return showCreateTime_M;
	}

	public String getShowCreateTime_D()
	{
		return showCreateTime_D;
	}

	
}
