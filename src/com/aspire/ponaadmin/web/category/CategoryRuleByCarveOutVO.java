
package com.aspire.ponaadmin.web.category;

import java.util.Date;

public class CategoryRuleByCarveOutVO
{

    /**
     * id
     */
    private String id;
    
    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则对应的货架的货架内码
     */
    private String cid;
    
    /**
     * 规则类型 0：刷新货架下商品；1：货架下商品重排顺序'
     */
    private int ruleType;

    /**
     * 执行时间间隔类型 0：天；1：周；2：月
     */
    private int intervalType;

    /**
     * 指intervalType的执行次数，通过intervalType 和intervalCount之积确定执行周期。
     */
    private int intervalCount;

    /**
     * 在一个时间间隔之内的执行日子。 如果IntervalType=0，本字段无效。 如果IntervalType=1，本字段表示在周几执行。
     * 如果IntervalType=2，本字段表示在月的第几天执行。
     */
    private int excuteTime;

    /**
     * 执行之间间隔，单位为天。
     */
    private int excuteInterval;

    /**
     * 获取产品的条件sql
     */
    private String wSql;

    /**
     * 获取的个数
     */
    private int count;

    /**
     * 排序sql
     */
    private String oSql;

    /**
     * 排序次序，是指一个规则对应条件的之间的执行次序。
     */
    private int sortId;

    /**
     * 上次执行时间，需要精确到秒
     */
    private Date lastExcuteTime;

    /**
     * 货架自动更新时间生效时间
     */
    private Date effectiveTime;
    
    /**
     * 基础语句ID
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
     * 覆盖toString()方法
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
