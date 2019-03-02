
package com.aspire.dotcard.baseread.vo;

/**
 * ����VO ��Ӧר����Ϣͬ�������¡�����
 * 
 * @author x_zhailiqing
 * 
 */
public class RCategoryVO
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
     * ����ID
     */
    private String cateId;

    /**
     * ���ܸ�id
     */
    private String cateParentId;

    /**
     * ��������
     */
    private String cateName;

    /**
     * ���
     */
    private String description;

    /**
     * ��������
     */
    private String catalogType;

    /**
     * ����id
     */
    private int sortId;

    /**
     * ����ʱ��
     */
    private String businessTime;
    
    /**
     * ר��ͼƬ
     */
    private String picUrl;

    /**
     * 1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ���
     */
    private int changeType;

    public String getBusinessTime()
    {
        return businessTime;
    }

    public void setBusinessTime(String businessTime)
    {
        this.businessTime = businessTime;
    }

    public String getCatalogType()
    {
        return catalogType;
    }

    public void setCatalogType(String catalogType)
    {
        this.catalogType = catalogType;
    }

    public String getCateId()
    {
        return cateId;
    }

    public void setCateId(String cateId)
    {
        this.cateId = cateId;
    }

    public String getCateName()
    {
        return cateName;
    }

    public void setCateName(String cateName)
    {
        this.cateName = cateName;
    }

    public String getCateParentId()
    {
        return cateParentId;
    }

    public void setCateParentId(String cateParentId)
    {
        this.cateParentId = cateParentId;
    }

    public int getChangeType()
    {
        return changeType;
    }

    public void setChangeType(int changeType)
    {
        this.changeType = changeType;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {
        this.sortId = sortId;
    }

    /**
     * ��ֵ
     * 
     * @param data
     * @return
     */
    public boolean setValue(String[] data)
    {
        // ר��
        if (data.length != 7)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
            || "".equals(data[1].trim()) || null == data[6]
            || "".equals(data[6].trim()))
        {
            return false;
        }

        int changeType;
        try
        {
            changeType = Integer.parseInt(data[6]);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        if (changeType != 1 && changeType != 2 && changeType != 3)
        {
            return false;
        }
        this.cateId = data[0].trim();
        this.cateName = data[1].trim();
        this.description = data[2].trim();
        this.cateParentId = data[3].trim();
        this.picUrl = data[4].trim();
        this.businessTime = data[5].trim();
        this.changeType = changeType;

        return true;
    }
}
