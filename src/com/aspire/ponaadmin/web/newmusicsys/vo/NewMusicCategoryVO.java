
package com.aspire.ponaadmin.web.newmusicsys.vo;

public class NewMusicCategoryVO
{

    /**
     * ����id
     */
    private String categoryId;

    /**
     * ��������
     */
    private String categoryName;

    /**
     * ������id
     */
    private String parentCategoryId;

    /**
     * ��������
     */
    private String type;

    /**
     * �Ƿ�ɾ��
     */
    private String delFlag;

    /**
     * ����ʱ��
     */
    private String createTime;

    /**
     * ������ʱ��
     */
    private String lupdDate;

    /**
     * ������Ϣ
     */
    private String desc;

    /**
     * ��������
     */
    private int sortId;

    /**
     * ������Ʒ����
     */
    private int sum;

    /**
     * ר��id
     */
    private String albumId;

    /**
     * ר������
     */
    private String albumPic;

    /**
     * ר������
     */
    private int rate;

    /**
     * ר������
     */
    private String albumSinger;
    
    /**
     * ����id
     */
    private String cityId;
    
    /**
     * ƽ̨id
     */
    private String platForm;

    /**
     * ���ֻ�������:1,�������;0,��ͨ����
     */
    private String cateType;
    
    private String music_status;
    
    public String getMusic_status() {
		return music_status;
	}

	public void setMusic_status(String music_status) {
		this.music_status = music_status;
	}

	public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getPlatForm()
    {
        return platForm;
    }

    public void setPlatForm(String platForm)
    {
        this.platForm = platForm;
    }

    public String getAlbumId()
    {
        return albumId;
    }

    public void setAlbumId(String albumId)
    {
        this.albumId = albumId;
    }

    public String getAlbumPic()
    {
        return albumPic;
    }

    public void setAlbumPic(String albumPic)
    {
        this.albumPic = albumPic;
    }

    public String getAlbumSinger()
    {
        return albumSinger;
    }

    public void setAlbumSinger(String albumSinger)
    {
        this.albumSinger = albumSinger;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getLupdDate()
    {
        return lupdDate;
    }

    public void setLupdDate(String lupdDate)
    {
        this.lupdDate = lupdDate;
    }

    public String getParentCategoryId()
    {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId)
    {
        this.parentCategoryId = parentCategoryId;
    }

    public int getRate()
    {
        return rate;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {
        this.sortId = sortId;
    }

    public int getSum()
    {
        return sum;
    }

    public void setSum(int sum)
    {
        this.sum = sum;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

	/**
	 * @return Returns the cateType.
	 */
	public String getCateType()
	{
		return cateType;
	}

	/**
	 * @param cateType The cateType to set.
	 */
	public void setCateType(String cateType)
	{
		this.cateType = cateType;
	}

}
