package com.aspire.ponaadmin.web.category.rule;

import java.util.List;

public class RuleVO
{
	/**
	 * ����id
	 */
	private int ruleId;
	/**
	 * ��������
	 */
	private String ruleName;
	/**
	 * ��������ͣ��Ǹ�����Ʒsortid�����Ǹ��»�����Ʒ����
	 */
	private int ruleType;
	/**
	 * ��ȡ��Ʒ�������
	 */
	private List eliteConds;
	/**
	 * ��ȡ��Ʒ�������
	 */
	private List contentConds;
	/**
	 * ִ��ʱ�������� 0���죻1���ܣ�2����
	 */
	private int intervalType;
	/**
	 * ָintervalType��ִ�д�����ͨ��intervalType
	 * ��intervalCount֮��ȷ��ִ�����ڡ�
	 */
	private int intervalCount;
	/**
	 * ��һ��ʱ����֮�ڵ�ִ�����ӡ�
	 * ���IntervalType=0�����ֶ���Ч��
	 * ���IntervalType=1�����ֶα�ʾ���ܼ�ִ�С�
	 * ���IntervalType=2�����ֶα�ʾ���µĵڼ���ִ�С�
	 */
	private int excuteTime;
    /**
     * ִ��֮��������λΪ�졣
     */
    private int excuteInterval;
	/**
	 * ����ϼ����ӡ���Ʒ���ϼ�ǰ�Ƿ���Ҫ�漴����0�������100���漴��1~99 ���ͻ���С���
	 */
	private int randomFactor;
	
	
	private int maxGoodsNum;//������Ϊ������ˢ�»�������Ʒ�� �µ������Ʒ���� add by aiyan 2011-12-21
	
	

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
     * ����toString()����
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
