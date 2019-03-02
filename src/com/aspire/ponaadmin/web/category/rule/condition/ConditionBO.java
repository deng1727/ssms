package com.aspire.ponaadmin.web.category.rule.condition;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * 获取规则对应的体检的BO类
 * @author zhangwei
 *
 */
public class ConditionBO
{
	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(ConditionBO.class);

	private static  ConditionBO instance=new ConditionBO();

	public static ConditionBO getInstance()
	{
		return instance;
	}
	/**
	 * 根据ruleId获取条件,从产品库或精品库获取由condType条件区分。
	 * @param ruleId 规则ID
	 * @param condType 条件类型
	 * @return
	 * @throws BOException
	 */
	public List getConditions(int ruleId,int condType) throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("获取规则ruleID="+ruleId+",condType="+condType+"对应的所有条件:开始执行......");
		}

		List conds = null;
		try
		{
			conds = ConditionDAO.getInstance().getConditionsByRuleID(ruleId,condType);

		} catch (DAOException ex)
		{
			throw new BOException("获取规则ruleID="+ruleId+",condType="+condType+"对应的所有条件列表失败", ex);
		}
		if (LOG.isDebugEnabled())
		{
			LOG.debug("获取规则ruleID="+ruleId+",condType="+condType+"对应的所有条件:执行结束");
		}

		return conds;
	}
	/**
	 * 根据ruleId获取条件,从产品库或精品库获取由condType条件区分。
	 * @param ruleId 规则ID
	 * @param condType 条件类型
	 * @return
	 * @throws BOException
	 */
	public List getNotEliteConditions(int ruleId) throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("获取规则ruleID="+ruleId+"对应的非精品库的所有条件:开始执行......");
		}

		List conds = null;
		try
		{
			conds = ConditionDAO.getInstance().getNotEliteConditionsByRuleID(ruleId);

		} catch (DAOException ex)
		{
			throw new BOException("获取规则ruleID="+ruleId+"对应的非精品库的所有条件列表失败", ex);
		}
		if (LOG.isDebugEnabled())
		{
			LOG.debug("获取规则ruleID"+ruleId+"对应的非精品库的所有条件:执行结束");
		}

		return conds;
	}

}
