package com.aspire.ponaadmin.web.category.rule;

import java.util.List;

public class RuleVO
{
	/**
	 * 规则id
	 */
	private int ruleId;
	/**
	 * 规则名称
	 */
	private String ruleName;
	/**
	 * 规则的类型，是更新商品sortid，还是更新货架商品内容
	 */
	private int ruleType;
	/**
	 * 获取精品库的条件
	 */
	private List eliteConds;
	/**
	 * 获取产品库的条件
	 */
	private List contentConds;
	/**
	 * 执行时间间隔类型 0：天；1：周；2：月
	 */
	private int intervalType;
	/**
	 * 指intervalType的执行次数，通过intervalType
	 * 和intervalCount之积确定执行周期。
	 */
	private int intervalCount;
	/**
	 * 在一个时间间隔之内的执行日子。
	 * 如果IntervalType=0，本字段无效。
	 * 如果IntervalType=1，本字段表示在周几执行。
	 * 如果IntervalType=2，本字段表示在月的第几天执行。
	 */
	private int excuteTime;
    /**
     * 执行之间间隔，单位为天。
     */
    private int excuteInterval;
	/**
	 * 随机上架因子。产品库上架前是否需要随即排序。0不随机，100大随即，1~99 机型货架小随机
	 */
	private int randomFactor;
	
	
	private int maxGoodsNum;//当规则为“增量刷新货架下商品” 下的最大商品数量 add by aiyan 2011-12-21
	
	

	public List getEliteConds()
	{
		return eliteConds;
	}
	public void setEliteConds(List eliteConds)
	{
		this.eliteConds = eliteConds;
	}
	public List getContentConds()
	{
		return contentConds;
	}
	public void setContentConds(List contentConds)
	{
		this.contentConds = contentConds;
	}
	public void addContentConds(List contentConds)
	{
		if(this.contentConds==null)
		{
			this.contentConds = contentConds;
		}else
		{
			this.contentConds.addAll(contentConds);
		}
		
	}
	public int getIntervalType()
	{
		return intervalType;
	}
	public void setIntervalType(int intervalType)
	{
		this.intervalType = intervalType;
	}
	public int getIntervalCount()
	{
		return intervalCount;
	}
	public void setIntervalCount(int intervalCount)
	{
		this.intervalCount = intervalCount;
	}
	public int getExcuteTime()
	{
		return excuteTime;
	}
	public void setExcuteTime(int excuteTime)
	{
		this.excuteTime = excuteTime;
	}
	public int getRuleId()
	{
		return ruleId;
	}
	public void setRuleId(int ruleId)
	{
		this.ruleId = ruleId;
	}
	public int getRuleType()
	{
		return ruleType;
	}
	public void setRuleType(int ruleType)
	{
		this.ruleType = ruleType;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

    /**
     * 覆盖toString()方法
     *
     * @return String
     */
    public String toString()
    {

        StringBuffer sb = new StringBuffer();
        sb.append("RuleVO{");
        sb.append("ruleId=");
        sb.append(ruleId);
        sb.append(",ruleName=");
        sb.append(ruleName);
        sb.append(",ruleType=");
        sb.append(ruleType);
        sb.append(",eliteConds=");
        sb.append(eliteConds);
        sb.append(",contentConds=");
        sb.append(contentConds);
        sb.append(",intervalType=");
        sb.append(intervalType);
        sb.append(",intervalCount=");
        sb.append(intervalCount);
        sb.append(",excuteTime=");
        sb.append(excuteTime);
        
        sb.append(",maxGoodsNum=");
        sb.append(maxGoodsNum);
        
        sb.append("}");
        return sb.toString();
    }
	public int getRandomFactor()
	{
		return randomFactor;
	}
	public void setRandomFactor(int randomFactor)
	{
		this.randomFactor = randomFactor;
	}
    public int getExcuteInterval()
    {
    
        return excuteInterval;
    }
    
    public void setExcuteInterval(int excuteInterval)
    {
    
        this.excuteInterval = excuteInterval;
    }
	public int getMaxGoodsNum() {
		return maxGoodsNum;
	}
	public void setMaxGoodsNum(int maxGoodsNum) {
		this.maxGoodsNum = maxGoodsNum;
	}
    
    

}
