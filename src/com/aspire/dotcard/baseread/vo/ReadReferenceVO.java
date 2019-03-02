
package com.aspire.dotcard.baseread.vo;

/**
 * ����ͼ����Ʒ��
 * 
 * @author x_zhailiqing
 * 
 */
public class ReadReferenceVO
{
    /**
     * ����id
     */
    private String cId;
    
    /**
     * ͼ��id
     */
    private String bookId;
    
    /**
     * ͼ������
     */
    private String bookName;
    
    /**
     * ��������
     */
    private String authorName;
    
    /**
     * ͼ������
     */
    private String typeName;
    
    /**
     * ͼ����
     */
    private String desc;
    
    /**
     * �Ƿ�Ϊ�걾
     */
    private String isFinish;
    
    /**
     * �շ�����
     */
    private String chargeType;
    
    /**
     * �Ƽ�����
     */
    private String shortRecommend;
    
    /**
     * ����
     */
    private String fee;
    
    /**
     * ����
     */
    private String sortNumber;
    
    /**
     * ����
     */
    private String rankValue;
    
    /**
     * ����޸�ʱ��
     */
    private String lastUpTime;
    
    /**
     * ����޸�ʱ��_��
     */
    private String lastUpTime_Y;
    
    /**
     * ����޸�ʱ��_��
     */
    private String lastUpTime_M;
    
    /**
     * ����޸�ʱ��_��
     */
    private String lastUpTime_D;
    
    private String verify_status;

    public String getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}

	public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public String getBookId()
    {
        return bookId;
    }

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public String getCId()
    {
        return cId;
    }

    public void setCId(String id)
    {
        cId = id;
    }

    public String getRankValue()
    {
        return rankValue;
    }

    public void setRankValue(String rankValue)
    {
        this.rankValue = rankValue;
    }

    public String getSortNumber()
    {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber)
    {
        this.sortNumber = sortNumber;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getIsFinish()
    {
        return isFinish;
    }

    public void setIsFinish(String isFinish)
    {
        this.isFinish = isFinish;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getChargeType()
    {
        return chargeType;
    }

    public void setChargeType(String chargeType)
    {
        this.chargeType = chargeType;
    }

    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }

    public String getShortRecommend()
    {
        return shortRecommend;
    }

    public void setShortRecommend(String shortRecommend)
    {
        this.shortRecommend = shortRecommend;
    }
    
	public String getLastUpTime()
	{
		return lastUpTime;
	}
    
	public void setLastUpTime(String lastUpTime)
	{
		this.lastUpTime = lastUpTime;
		this.lastUpTime_Y = lastUpTime.substring(0,4);
		this.lastUpTime_M = lastUpTime.substring(4,6);
		this.lastUpTime_D = lastUpTime.substring(6);
	}

	public String getLastUpTime_Y()
	{
		return lastUpTime_Y;
	}

	public String getLastUpTime_M()
	{
		return lastUpTime_M;
	}

	public String getLastUpTime_D()
	{
		return lastUpTime_D;
	}
}