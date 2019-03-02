/*
 * 
 */
package com.aspire.ponaadmin.web.category.intervenor.gcontent;

/**
 * 容器内容对象
 * @author x_wangml
 *
 */
public class IntervenorGcontentVO
{	
	
	/**
	 * 聚合应用id
	 * 
	 */
	private String appId;
	
    /**
     * 容器id
     */
    private String intervenorId;
    
    /**
     * 内容id
     */
    private String id;
    
    /**
     * 内容在容器中的排序
     */
    private int sortId;
    
    /**
     * 内容外称
     */
    private String name;
    
    /**
     * 提供商
     */
    private String spName;
    
    /**
     * 商用时间
     */
    private String marketDate;
    
    /**
     * 内容id外码
     */
    private String contentId;
    
    /**
     * 容器指定干预位置
     */
    private int startSortid;
    
    /**
     * 容器在榜中的执行排序位置
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
