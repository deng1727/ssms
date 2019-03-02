package com.aspire.ponaadmin.web.category.rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class RuleDAO
{
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(RuleDAO.class);
	/**
	 * singletonģʽ��ʵ��
	 */
	private static RuleDAO instance = new RuleDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private RuleDAO()
	{
	}

	/**
	 * ��ȡʵ��
	 *
	 * @return ʵ��
	 */
	public static RuleDAO getInstance()
	{

		return instance;
	}

	public List getAllRules() throws DAOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("��ȡȫ������,��ʼ");
		}
		List rules = new ArrayList();
		ResultSet rs = null;

		try
		{
			Object[] paras = null;
			rs = DB.getInstance().queryBySQLCode(
					"category.rule.categoryRuleDAO.update().GETALLRULES", paras);
			while (rs.next())
			{
				RuleVO ruleVO = new RuleVO();
				ruleVO.setRuleId(rs.getInt("ruleId"));
				ruleVO.setRuleName(rs.getString("ruleName"));
				ruleVO.setRuleType(rs.getInt("ruleType"));
				ruleVO.setIntervalType(rs.getInt("intervalType"));
				ruleVO.setIntervalCount(rs.getInt("excuteinterval"));
				ruleVO.setExcuteTime(rs.getInt("excuteTime"));
				ruleVO.setRandomFactor(rs.getInt("randomfactor"));
				
				if(rs.getString("maxGoodsNum")==null){//add by aiyan 2011-12-21
					ruleVO.setMaxGoodsNum(-1);
		        }else{
		        	ruleVO.setMaxGoodsNum(rs.getInt("maxGoodsNum"));
		        }

				rules.add(ruleVO);
			}
		} catch (SQLException e)
		{
			LOG.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
		} catch (DAOException ex)
		{
			LOG.error("���ݿ�����쳣����ѯʧ��", ex);
		} finally
		{
			DB.close(rs);
		}

		if (LOG.isDebugEnabled())
		{
			LOG.debug("��ȡȫ������,����");
		}
		return rules;
	}

}
