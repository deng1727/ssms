/*
 * �ļ�����NewMusicRefVO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ����id
     */
    private String musicId;
    
    /**
     * ����id
     */
    private String categoryId;
    
    /**
     * �����ֳ�
     */
    private String musicName;
    
    /**
     * ����ͼƬ��ַ
     */
    private String musicPic;
    
    /**
     * ����ʱ��
     */
    private String createTime;
    
    /**
     * ����id
     */
    private int sortId;
    
    /**
     * ������Ϣ
     */
    private String singer;
    
    /**
     * ��Ч��
     */
    private String validity;
    
    /**
     * ����ʱ��
     */
    private String updateTime;
    
    /**
     * ���ֱ�ǩ��Ϣ
     */
    private String tags;

    /**
     * ��������
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
