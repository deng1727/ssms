/*
 * 
 */
package com.aspire.ponaadmin.web.category.intervenor;

import java.util.List;


/**
 * �˹���Ԥ��������
 * @author x_wangml
 *
 */
public class IntervenorVO
{
    /**
     * ���񵥶�����ֵ
     */
    public final static int TOP = -1;
    
    /**
     * ���񵥵׵���ֵ
     */
    public final static int END = -2;
    
    /**
     * ��Ԥ�������
     */
    private int id;
    
    /**
     * ��Ԥ��������
     */
    private String name;
    
    /**
     * ��Ԥ��ʼʱ��
     */
    private String startDate;
    
    /**
     * ��Ԥ����ʱ��
     */
    private String endDate;
    
    /**
     * ��Ԥ��ʼ����
     */
    private int startSortId;
    
    /**
     * ��Ԥ�������������뿪ʼ������ͬΪ�̶�����������Ϊ��������
     */
    private int endSortId;
    
    /**
     * ���ڼ�¼��񵥹���ʱ��������Ϣ
     */
    private int sortId;
    
    /**
     * �����а���������
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