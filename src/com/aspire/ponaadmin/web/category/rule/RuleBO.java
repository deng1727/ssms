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
	 * ��ȡ���ݹ���id����ȡ��ǰ�Ĺ���
	 *
	 * @param ruleVO ����VO,������ruleIDȡ�û�ȡ����������
	 * @return ��ȡ����Ӧ������RuleVO
	 * @throws BOException
	 */
	public RuleVO getRuleByRuleID(RuleVO ruleVO) throws BOException
	{
		/*
		 * if (LOG.isDebugEnabled()) { LOG.debug("��ȡ������Ҫִ�еĻ��ܹ���:��ʼִ��......"); }
		 * List rules = null; try { rules =
		 * CategoryRuleDAO.getInstance().selectAllRules(); } catch (DAOException
		 * ex) { throw new BOException("��ȡ������Ҫִ�еĻ��ܹ����б�ʧ��", ex); } if
		 * (LOG.isDebugEnabled()) { LOG.debug("��ȡ������Ҫִ�еĻ��ܹ���:ִ�н���"); }
		 */

		return null;
	}

	/**
	 * ��ȡ��ǰ���еĹ���
	 *
	 * @return ����hashmap���ͣ�key��ʾruleId��value��ruleVO
	 * @throws BOException
	 */
	public HashMap getAllRules() throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("��ȡ������Ҫִ�еĻ��ܹ���:��ʼִ��......");
		}
		HashMap rulesMap = new HashMap();
		// ��ȡȫ�������б�
		List rules = null;
		try
		{
			rules = RuleDAO.getInstance().getAllRules();

		} catch (DAOException e)
		{
			throw new BOException("��ȡ������Ҫִ�еĻ��ܹ����б�ʧ��", e);
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
				//�ù�����Ҫ������ֱ��ִ��
				ruleVO.setContentConds(new ArrayList(0));
			}else
			{
				throw new BOException("Ŀǰ��֧�ִ˹������͡�ruleType="+ruleType);
			}

			rulesMap.put(new Integer(ruleId), ruleVO);
		}
		return rulesMap;
	}
}
