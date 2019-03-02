package com.aspire.ponaadmin.web.category;

import java.util.Date;

import com.aspire.ponaadmin.web.category.rule.RuleVO;

public class CategoryRuleVO
{

	/**
	 * 规则对应的货架的货架内码
	 */
	private String cid;
	/**
	 * 货架对应的规则id
	 */
	private int ruleId;

	/**
	 * 该货架对应的规则VO
	 */
	private RuleVO ruleVO;
	/**
	 * 上次执行时间，需要精确到秒
	 */
	private Date lastExcuteTime;
	/**
	 * 货架自动更新时间生效时间
	 */
	private Date effectiveTime;
    
    /**
     * 规则名称
     */
    private String ruleName;
    
    private String cidPath;
    
	public String getCidPath()
	{
		return cidPath;
	}

	public void setCidPath(String cidPath)
	{
		this.cidPath = cidPath;
	}

	public Date getLastExcuteTime()
	{
		return lastExcuteTime;
	}

	public void setLastExcuteTime(Date lastExcuteTime)
	{
		this.lastExcuteTime = lastExcuteTime;
	}

	public RuleVO getRuleVO()
	{
		return ruleVO;
	}

	public void setRuleVO(RuleVO vo)
	{
		this.ruleVO = vo;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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
        sb.append(ruleId);
        sb.append(",ruleVO=[");
        sb.append(ruleVO);
        sb.append("],lastExcuteTime=");
//        if(null == lastExcuteTime) {
        	sb.append(lastExcuteTime);
//        }else {
//            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastExcuteTime));
//        }
        sb.append(",effectiveTime=");
//        if(null == effectiveTime) {
        	sb.append(effectiveTime);
//        }else {
//            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(effectiveTime));
//        }
           
        sb.append("}");
        return sb.toString();
    }

	public int getRuleId()
	{
		return ruleId;
	}

	public void setRuleId(int ruleId)
	{
		this.ruleId = ruleId;
	}

	public Date getEffectiveTime()
	{
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime)
	{
		this.effectiveTime = effectiveTime;
	}

    
    public String getRuleName()
    {
    
        return ruleName;
    }

    
    public void setRuleName(String ruleName)
    {
    
        this.ruleName = ruleName;
    }

}
