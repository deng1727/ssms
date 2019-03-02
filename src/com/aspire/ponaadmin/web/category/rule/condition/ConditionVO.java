package com.aspire.ponaadmin.web.category.rule.condition;


public class ConditionVO
{
	/**
	 *����id
	 */
	private int ruleId;
	/**
	 * ��ȡ���ܵĻ������룬���CondType=0���ֶ���Ч
	 */
	private String cid;
	/**
	 * �������� 0���Ӳ�Ʒ���ȡ��1���Ӿ�Ʒ���ȡ
	 */
	private int condType;
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
     * Ψһ����
     */
    private int id;
    
    /**
     * ��������id
     */
    private int baseCondId;
    
    /**
     * ��������name
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
     * ����toString()����
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
