package com.aspire.ponaadmin.web.category.rule.condition;


public class ConditionVO
{
	/**
	 *规则id
	 */
	private int ruleId;
	/**
	 * 获取货架的货架内码，如果CondType=0则本字段无效
	 */
	private String cid;
	/**
	 * 条件类型 0：从产品库获取；1：从精品库获取
	 */
	private int condType;
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
     * 唯一编码
     */
    private int id;
    
    /**
     * 基础条件id
     */
    private int baseCondId;
    
    /**
     * 基础条件name
     */
    private String baseCondName;


	public int getBaseCondId()
    {
        return baseCondId;
    }
    public void setBaseCondId(int baseCondId)
    {
        this.baseCondId = baseCondId;
    }
    public String getCid()
	{
		return cid;
	}
	public void setCid(String cid)
	{
		this.cid = cid;
	}
	public String getWSql()
	{
		return wSql;
	}
	public void setWSql(String wSql)
	{
		this.wSql = wSql;
	}
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	public String getOSql()
	{
		return oSql;
	}
	public void setOSql(String oSql)
	{
		this.oSql = oSql;
	}
	public int getSortId()
	{
		return sortId;
	}
	public void setSortId(int sortId)
	{
		this.sortId = sortId;
	}
	public int getRuleId()
	{
		return ruleId;
	}
	public void setRuleId(int ruleId)
	{
		this.ruleId = ruleId;
	}
	public int getCondType()
	{
		return condType;
	}
	public void setCondType(int condType)
	{
		this.condType = condType;
	}

    /**
     * 覆盖toString()方法
     *
     * @return String
     */
    public String toString()
    {

        StringBuffer sb = new StringBuffer();
        sb.append("ConditionVO{");
        sb.append("ruleId=");
        sb.append(ruleId);
        sb.append(",condType=");
        sb.append(condType);
        sb.append(",cid=");
       	sb.append(cid);
        sb.append(",wSql=[");
        sb.append(wSql);
        sb.append("],oSql=[");
        sb.append(oSql);
        sb.append("],count=");
        sb.append(count);
        sb.append(",sortId=");
        sb.append(sortId);
        sb.append("}");
        return sb.toString();
    }
    public int getId()
    {
    
        return id;
    }
    
    public void setId(int id)
    {
    
        this.id = id;
    }
    public String getBaseCondName()
    {
        return baseCondName;
    }
    public void setBaseCondName(String baseCondName)
    {
        this.baseCondName = baseCondName;
    }



}
