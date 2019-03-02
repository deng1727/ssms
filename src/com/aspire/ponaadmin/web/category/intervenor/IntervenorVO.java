/*
 * 
 */
package com.aspire.ponaadmin.web.category.intervenor;

import java.util.List;


/**
 * 人工干预容器对象
 * @author x_wangml
 *
 */
public class IntervenorVO
{
    /**
     * 至榜单顶的数值
     */
    public final static int TOP = -1;
    
    /**
     * 至榜单底的数值
     */
    public final static int END = -2;
    
    /**
     * 干预容器编号
     */
    private int id;
    
    /**
     * 干预容器名称
     */
    private String name;
    
    /**
     * 干预开始时间
     */
    private String startDate;
    
    /**
     * 干预结束时间
     */
    private String endDate;
    
    /**
     * 干预开始排名
     */
    private int startSortId;
    
    /**
     * 干预结束排名，如与开始排名相同为固定排名，否则为浮动排名
     */
    private int endSortId;
    
    /**
     * 用于记录与榜单关联时的排序信息
     */
    private int sortId;
    
    /**
     * 容器中包含的内容
     */
    private List contentList;

    
    public String getEndDate()
    {
    
        return endDate;
    }
    
    public void setEndDate(String endDate)
    {
    
        this.endDate = endDate;
    }

    public int getEndSortId()
    {
    
        return endSortId;
    }

    public void setEndSortId(int endSortId)
    {
    
        this.endSortId = endSortId;
    }

    public int getId()
    {
    
        return id;
    }

    public void setId(int id)
    {
    
        this.id = id;
    }

    public String getName()
    {
    
        return name;
    }
    
    public void setName(String name)
    {
    
        this.name = name;
    }
    
    public String getStartDate()
    {
    
        return startDate;
    }
    
    public void setStartDate(String startDate)
    {
    
        this.startDate = startDate;
    }
    
    public int getStartSortId()
    {
    
        return startSortId;
    }
    
    public void setStartSortId(int startSortId)
    {
    
        this.startSortId = startSortId;
    }

    public List getContentList()
    {
    
        return contentList;
    }

    public void setContentList(List contentList)
    {
    
        this.contentList = contentList;
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("IntervenorVO{");
        sb.append("id=");
        sb.append(id);
        sb.append(",name=");
        sb.append(name);
        sb.append(",startDate=");
        sb.append(startDate);
        sb.append(",endDate=");
        sb.append(endDate);
        sb.append(",startSortId=");
        sb.append(startSortId);
        sb.append(",endSortId=");
        sb.append(endSortId);
        sb.append("}");
        return sb.toString();
    }

    
    public int getSortId()
    {
    
        return sortId;
    }

    
    public void setSortId(int sortId)
    {
    
        this.sortId = sortId;
    }
}