package com.aspire.dotcard.baseVideo.vo;

public class VideoCategoryVO
{
    /**
     * id
     */
    private String id;
    
    /**
     * ��id
     */
    private String parentId;
    
    /**
     * ��������
     */
    private String baseId;
    
    /**
     * ����������
     */
    private String baseParentId;
    
    /**
     * ��������
     */
    private String baseType;
    
    /**
     * ��������
     */
    private String baseName;
    
    /**
     * �����
     */
    private int sortId;
    
    /**
     * �Ƿ����Ż���ʾ
     */
    private int isShow;
    
    /**
     * ���
     */
    private String desc;
    
    /**
     * ��ƷID
     */
    private String productId;
    
    private String video_status;

    public String getVideo_status() {
		return video_status;
	}

	public void setVideo_status(String video_status) {
		this.video_status = video_status;
	}

	public String getBaseId()
    {
        return baseId;
    }

    public void setBaseId(String baseId)
    {
        this.baseId = baseId;
    }

    public String getBaseName()
    {
        return baseName;
    }

    public void setBaseName(String baseName)
    {
        this.baseName = baseName;
    }

    public String getBaseParentId()
    {
        return baseParentId;
    }

    public void setBaseParentId(String baseParentId)
    {
        this.baseParentId = baseParentId;
    }

    public String getBaseType()
    {
        return baseType;
    }

    public void setBaseType(String baseType)
    {
        this.baseType = baseType;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getIsShow()
    {
        return isShow;
    }

    public void setIsShow(int isShow)
    {
        this.isShow = isShow;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {
        this.sortId = sortId;
    }

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}
    
}
