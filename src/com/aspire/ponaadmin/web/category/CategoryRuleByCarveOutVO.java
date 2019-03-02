
package com.aspire.ponaadmin.web.category;

import java.util.Date;

public class CategoryRuleByCarveOutVO
{

    /**
     * id
     */
    private String id;
    
    /**
     * ��������
     */
    private String name;

    /**
     * �����Ӧ�Ļ��ܵĻ�������
     */
    private String cid;
    
    /**
     * �������� 0��ˢ�»�������Ʒ��1����������Ʒ����˳��'
     */
    private int ruleType;

    /**
     * ִ��ʱ�������� 0���죻1���ܣ�2����
     */
    private int intervalType;

    /**
     * ָintervalType��ִ�д�����ͨ��intervalType ��intervalCount֮��ȷ��ִ�����ڡ�
     */
    private int intervalCount;

    /**
     * ��һ��ʱ����֮�ڵ�ִ�����ӡ� ���IntervalType=0�����ֶ���Ч�� ���IntervalType=1�����ֶα�ʾ���ܼ�ִ�С�
     * ���IntervalType=2�����ֶα�ʾ���µĵڼ���ִ�С�
     */
    private int excuteTime;

    /**
     * ִ��֮��������λΪ�졣
     */
    private int excuteInterval;

    /**
     * ��ȡ��Ʒ������sql
     */
    private String wSql;

    /**
     * ��ȡ�ĸ���
     */
    private int count;

    /**
     * ����sql
     */
    private String oSql;

    /**
     * ���������ָһ�������Ӧ������֮���ִ�д���
     */
    private int sortId;

    /**
     * �ϴ�ִ��ʱ�䣬��Ҫ��ȷ����
     */
    private Date lastExcuteTime;

    /**
     * �����Զ�����ʱ����Чʱ��
     */
    private Date effectiveTime;
    
    /**
     * �������ID
     */
    private String baseSQLId;

    public Date getLastExcuteTime()
    {
        return lastExcuteTime;
    }

    public void setLastExcuteTime(Date lastExcuteTime)
    {
        this.lastExcuteTime = lastExcuteTime;
    }

    public String getCid()
    {
        return cid;
    }

    public void setCid(String cid)
    {
        this.cid = cid;
    }

    public Date getEffectiveTime()
    {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime)
    {
        this.effectiveTime = effectiveTime;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getExcuteInterval()
    {
        return excuteInterval;
    }

    public void setExcuteInterval(int excuteInterval)
    {
        this.excuteInterval = excuteInterval;
    }

    public int getExcuteTime()
    {
        return excuteTime;
    }

    public void setExcuteTime(int excuteTime)
    {
        this.excuteTime = excuteTime;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getIntervalType()
    {
        return intervalType;
    }

    public void setIntervalType(int intervalType)
    {
        this.intervalType = intervalType;
    }

    public String getOSql()
    {
        return oSql;
    }

    public void setOSql(String sql)
    {
        oSql = sql;
    }

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {

        this.sortId = sortId;
    }

    public String getWSql()
    {

        return wSql;
    }

    public void setWSql(String sql)
    {

        wSql = sql;
    }

    /**
	 * @return Returns the baseSQLId.
	 */
	public String getBaseSQLId()
	{
		return baseSQLId;
	}

	/**
	 * @param baseSQLId The baseSQLId to set.
	 */
	public void setBaseSQLId(String baseSQLId)
	{
		this.baseSQLId = baseSQLId;
	}

	/**
     * ����toString()����
     * 
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("CategoryRuleVO{");
        sb.append("cid=");
        sb.append(cid);
        sb.append(",ruleId=");
        sb.append(",ruleVO=[");
        sb.append("],lastExcuteTime=");
        sb.append(lastExcuteTime);
        sb.append(",effectiveTime=");
        sb.append(effectiveTime);
        sb.append("baseSQLId="+this.baseSQLId);
        sb.append("}");
        return sb.toString();
    }

    public int getIntervalCount()
    {

        return intervalCount;
    }

    public void setIntervalCount(int intervalCount)
    {

        this.intervalCount = intervalCount;
    }

    
    public String getName()
    {
        return name;
    }

    
    public void setName(String name)
    {
        this.name = name;
    }

    
    public int getRuleType()
    {
        return ruleType;
    }

    
    public void setRuleType(int ruleType)
    {
        this.ruleType = ruleType;
    }

}
