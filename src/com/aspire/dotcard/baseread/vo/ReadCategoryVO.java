/*
 * �ļ�����BookCategoryVO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.dotcard.baseread.vo;

import java.util.Date;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class ReadCategoryVO
{
    /**
     * ��������
     */
    private String id;
    
    /**
     * ���ܱ���
     */
    private String categoryId;
    
    /**
     * ��������
     */
    private String categoryName;
    
    /**
     * ��������
     */
    private String catalogType;
    
    /**
     * �Ƿ����Ż���ʾ
     */
    private String type;
    
    /**
     * ���
     */
    private String decrisption;
    
    /**
     * ������id
     */
    private String parentId;

    /**
     * ר��ͼƬ
     */
    private String picUrl;
    
    /**
     * ������ʱ��
     */
    private Date lupDate;
    
    /**
     * ��������Ʒ��
     */
    private String total;
    
    /**
     * ������Ϣ
     */
    private String cityId;
    
    /**
     * ͼ���������
     */
    private int sortId;
    
    /**
     * ������id��Ӧר��id������id
     */
    private String parentCategoryId;
    
    /**
     * ƽ̨ϵͳ��Ϣ
     */
    private String platForm;
    
    private String path;
    
    private String read_status;

    public String getRead_status() {
		return read_status;
	}

	public void setRead_status(String read_status) {
		this.read_status = read_status;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getCatalogType()
    {
        return catalogType;
    }

    public void setCatalogType(String catalogType)
    {
        this.catalogType = catalogType;
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

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getDecrisption()
    {
        return decrisption;
    }

    public void setDecrisption(String decrisption)
    {
        this.decrisption = decrisption;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Date getLupDate()
    {
        return lupDate;
    }

    public void setLupDate(Date lupDate)
    {
        this.lupDate = lupDate;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getPicUrl()
    {
        return picUrl;
    }

    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    public String getPlatForm()
    {
        return platForm;
    }

    public void setPlatForm(String platForm)
    {
        this.platForm = platForm;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {
        this.sortId = sortId;
    }

    public String getParentCategoryId()
    {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId)
    {
        this.parentCategoryId = parentCategoryId;
    }
}
