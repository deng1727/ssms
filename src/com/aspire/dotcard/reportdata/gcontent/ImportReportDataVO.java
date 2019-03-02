
package com.aspire.dotcard.reportdata.gcontent;

/**
 * ���ݶ������������ݵ���VO��
 * 
 * @author zhangwei
 * 
 */
public class ImportReportDataVO
{

    private static final long serialVersionUID = 1L;

    /**
     * ����ID
     */
    private String contentId;

    /**
     * ��������
     */
    private String contentName;

    /**
     * ��������
     */
    private long searchCount;

    /**
     * �������
     */
    private long pvCount;

    /**
     * ��������
     */
    private long subsCount;

    /**
     * ���۴���
     */
    private long remarkCount;

    /**
     * ���ִ���
     */
    private long scoreCount;

    /**
     * �Ƽ�����
     */
    private long commendCount;

    /**
     * �ղش���
     */
    private long collectionCount;

    /**
     * ƽ������
     */
    private long avgScoreCount;
    
    /**
     * ��ֵΪ���7���ۼ����붩��Ӧ�õĴ���
     */
    private long add7DaysOrderCount;
    
    /**
     * ��ֵΪ���30���ۼ����붩��Ӧ�õĴ���
     */
    private long add30DaysOrderCount;
    
    /**
     * ��ֵΪ�ն��Ż���������
     */
    private long downLoadCount;
    
    /**
     * ���ݵ����ͣ���Ϊ�գ��ܣ��£���������
     */
    private int type;

    public String toString()
    {

        StringBuffer sb = new StringBuffer();
        sb.append("[ contentId:");
        sb.append(this.contentId);

        sb.append(",contentName:");
        sb.append(this.contentName);

        sb.append(",searchCount:");
        sb.append(this.searchCount);

        sb.append(",pvCount:");
        sb.append(this.pvCount);

        sb.append(",subsCount:");
        sb.append(this.subsCount);

        sb.append(",remarkCount:");
        sb.append(this.remarkCount);

        sb.append(",scoreCount:");
        sb.append(this.scoreCount);

        sb.append(",commendCount:");
        sb.append(this.commendCount);

        sb.append(",collectionCount:");
        sb.append(this.collectionCount);

        sb.append(",avgScoreCount:");
        sb.append(this.avgScoreCount);
        
        sb.append(",add7DaysOrderCount:");
        sb.append(this.add7DaysOrderCount);
        
        sb.append(",add30DaysOrderCount:");
        sb.append(this.add30DaysOrderCount);     
        
        sb.append(",downLoadCount:");
        sb.append(this.downLoadCount); 
        return sb.toString();
    }

    
    public long getAdd7DaysOrderCount() {
		return add7DaysOrderCount;
	}


	public void setAdd7DaysOrderCount(long add7DaysOrderCount) {
		this.add7DaysOrderCount = add7DaysOrderCount;
	}


	public long getAdd30DaysOrderCount() {
		return add30DaysOrderCount;
	}


	public void setAdd30DaysOrderCount(long add30DaysOrderCount) {
		this.add30DaysOrderCount = add30DaysOrderCount;
	}


	public String getContentId()
    {
    
        return contentId;
    }

    
    public void setContentId(String contentId)
    {
    
        this.contentId = contentId;
    }

    
    public String getContentName()
    {
    
        return contentName;
    }

    
    public void setContentName(String contentName)
    {
    
        this.contentName = contentName;
    }

    
    public long getSearchCount()
    {
    
        return searchCount;
    }

    
    public void setSearchCount(long searchCount)
    {
    
        this.searchCount = searchCount;
    }

    
    public long getPvCount()
    {
    
        return pvCount;
    }

    
    public void setPvCount(long pvCount)
    {
    
        this.pvCount = pvCount;
    }

    
    public long getSubsCount()
    {
    
        return subsCount;
    }

    
    public void setSubsCount(long subsCount)
    {
    
        this.subsCount = subsCount;
    }

    
    public long getRemarkCount()
    {
    
        return remarkCount;
    }

    
    public void setRemarkCount(long remarkCount)
    {
    
        this.remarkCount = remarkCount;
    }

    
    public long getScoreCount()
    {
    
        return scoreCount;
    }

    
    public void setScoreCount(long scoreCount)
    {
    
        this.scoreCount = scoreCount;
    }

    
    public long getCommendCount()
    {
    
        return commendCount;
    }

    
    public void setCommendCount(long commendCount)
    {
    
        this.commendCount = commendCount;
    }

    
    public long getCollectionCount()
    {
    
        return collectionCount;
    }

    
    public void setCollectionCount(long collectionCount)
    {
    
        this.collectionCount = collectionCount;
    }

    
    public long getAvgScoreCount()
    {
    
        return avgScoreCount;
    }

    
    public void setAvgScoreCount(long avgScoreCount)
    {
    
        this.avgScoreCount = avgScoreCount;
    }

    
    public int getType()
    {
    
        return type;
    }


    
    public void setType(int type)
    {
    
        this.type = type;
    }


    
    public long getDownLoadCount()
    {
    
        return downLoadCount;
    }


    
    public void setDownLoadCount(long downLoadCount)
    {
    
        this.downLoadCount = downLoadCount;
    }
}
