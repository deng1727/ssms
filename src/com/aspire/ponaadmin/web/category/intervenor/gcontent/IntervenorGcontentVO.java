/*
 * 
 */
package com.aspire.ponaadmin.web.category.intervenor.gcontent;

/**
 * �������ݶ���
 * @author x_wangml
 *
 */
public class IntervenorGcontentVO
{	
	
	/**
	 * �ۺ�Ӧ��id
	 * 
	 */
	private String appId;
	
    /**
     * ����id
     */
    private String intervenorId;
    
    /**
     * ����id
     */
    private String id;
    
    /**
     * �����������е�����
     */
    private int sortId;
    
    /**
     * �������
     */
    private String name;
    
    /**
     * �ṩ��
     */
    private String spName;
    
    /**
     * ����ʱ��
     */
    private String marketDate;
    
    /**
     * ����id����
     */
    private String contentId;
    
    /**
     * ����ָ����Ԥλ��
     */
    private int startSortid;
    
    /**
     * �����ڰ��е�ִ������λ��
     */
    private int categorySort;
    
    
    
    
    public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getContentId()
    {
    
        return contentId;
    }

    
    public void setContentId(String contentId)
    {
    
        this.contentId = contentId;
    }

    
    public String getId()
    {
    
        return id;
    }

    
    public void setId(String id)
    {
    
        this.id = id;
    }

    
    public String getIntervenorId()
    {
    
        return intervenorId;
    }

    
    public void setIntervenorId(String intervenorId)
    {
    
        this.intervenorId = intervenorId;
    }

    
    public String getMarketDate()
    {
    
        return marketDate;
    }

    
    public void setMarketDate(String marketDate)
    {
    
        this.marketDate = marketDate;
    }

    
    public String getName()
    {
    
        return name;
    }

    
    public void setName(String name)
    {
    
        this.name = name;
    }

    
    public String getSpName()
    {
    
        return spName;
    }

    
    public void setSpName(String spName)
    {
    
        this.spName = spName;
    }

    public int getSortId()
    {
    
        return sortId;
    }
    
    public void setSortId(int sortId)
    {
    
        this.sortId = sortId;
    }


    
    public int getStartSortid()
    {
    
        return startSortid;
    }


    
    public void setStartSortid(int startSortid)
    {
    
        this.startSortid = startSortid;
    }


    
    public int getCategorySort()
    {
    
        return categorySort;
    }


    
    public void setCategorySort(int categorySort)
    {
    
        this.categorySort = categorySort;
    }
}
