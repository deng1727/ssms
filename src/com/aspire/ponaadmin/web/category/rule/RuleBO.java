package com.aspire.ponaadmin.web.category.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.CategoryConstants;
import com.aspire.ponaadmin.web.category.rule.condition.ConditionBO;

public class RuleBO
{
	private static RuleBO instance = new RuleBO();

	private JLogger LOG = LoggerFactory.getLogger(RuleBO.class);

	public static RuleBO getInstance()
	{
		return instance;
	}

	/**
	 * 获取根据规则id，获取当前的规则
	 *
	 * @param ruleVO 规则VO,根据其ruleID取得获取或排序条件
	 * @return 获取了相应条件的RuleVO
	 * @throws BOException
	 */
	public RuleVO getRuleByRuleID(RuleVO ruleVO) throws BOException
	{
		/*
		 * if (LOG.isDebugEnabled()) { LOG.debug("获取所有需要执行的货架规则:开始执行......"); }
		 * List rules = null; try { rules =
		 * CategoryRuleDAO.getInstance().selectAllRules(); } catch (DAOException
		 * ex) { throw new BOException("获取所有需要执行的货架规则列表失败", ex); } if
		 * (LOG.isDebugEnabled()) { LOG.debug("获取所有需要执行的货架规则:执行结束"); }
		 */

		return null;
	}

	/**
	 * 获取当前所有的规则。
	 *
	 * @return 返回hashmap类型，key表示ruleId，value是ruleVO
	 * @throws BOException
	 */
	public HashMap getAllRules() throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("获取所有需要执行的货架规则:开始执行......");
		}
		HashMap rulesMap = new HashMap();
		// 获取全部规则列表
		List rules = null;
		try
		{
			rules = RuleDAO.getInstance().getAllRules();

		} catch (DAOException e)
		{
			throw new BOException("获取所有需要执行的货架规则列表失败", e);
		}

		for (int i = 0; i < rules.size(); i++)
		{
			RuleVO ruleVO = (RuleVO) rules.get(i);
			int ruleId = ruleVO.getRuleId();
			int ruleType = ruleVO.getRuleType();

			if(CategoryConstants.RULETYPE_REORDER == ruleType) {
				ruleVO.addContentConds(ConditionBO.getInstance().getNotEliteConditions(ruleId));
				/*ruleVO.addContentConds(ConditionBO.getInstance().getConditions(ruleId,CategoryConstants.CONDTYPE_PROD_OWNER));
				ruleVO.addContentConds(ConditionBO.getInstance().getConditions(ruleId,CategoryConstants.CONDTYPE_PROD_OTHERS));
				ruleVO.addContentConds(ConditionBO.getInstance().getConditions(ruleId,CategoryConstants.CONDTYPE_PROD_BASEGAME));*/
			}else if(CategoryConstants.RULETYPE_REFRESH==ruleType||CategoryConstants.RULETYPE_OPERATION==ruleType
					||CategoryConstants.RULETYPE_ADD_REFRESH == ruleType){//RULETYPE_ADD_REFRESH add by aiyan 2011-12-20
				
				ruleVO.setEliteConds(ConditionBO.getInstance().getConditions(ruleId, CategoryConstants.CONDTYPE_ELITE));
				ruleVO.addContentConds(ConditionBO.getInstance().getNotEliteConditions(ruleId));
				/*ruleVO.addContentConds(ConditionBO.getInstance().getConditions(ruleId,CategoryConstants.CONDTYPE_PROD_OWNER));
				ruleVO.addContentConds(ConditionBO.getInstance().getConditions(ruleId,CategoryConstants.CONDTYPE_PROD_OTHERS));
				ruleVO.addContentConds(ConditionBO.getInstance().getConditions(ruleId,CategoryConstants.CONDTYPE_PROD_BASEGAME));*/
			}else if(CategoryConstants.RULETYPE_BOOK_COMMEND==ruleType)
			{
				//该规则不需要条件。直接执行
				ruleVO.setContentConds(new ArrayList(0));
			}else
			{
				throw new BOException("目前不支持此规则类型。ruleType="+ruleType);
			}

			rulesMap.put(new Integer(ruleId), ruleVO);
		}
		return rulesMap;
	}
}
