package com.aspire.ponaadmin.web.category.rule.condition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.CategoryConstants;

public class ConditionDAO
{
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(ConditionDAO.class);
	/**
	 * singletonģʽ��ʵ��
	 */
	private static ConditionDAO instance = new ConditionDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private ConditionDAO()
	{
	}

	/**
	 * ��ȡʵ��
	 *
	 * @return ʵ��
	 */
	public static ConditionDAO getInstance()
	{

		return instance;
	}

	/**
	 * ���ݹ����ȡ�����������͡�
	 * @param ruleID
	 * @param condType 1�Ǿ�Ʒ���������0�ǷǾ�Ʒ�������
	 * @return
	 * @throws DAOException
	 */
	public List getConditionsByRuleID(int ruleID, int condType) throws DAOException
	{
		ResultSet rs = null;
		ConditionVO vo = null;
		List conds = new ArrayList();
		Object[] paras = {new Integer(ruleID), new Integer(condType)};
		try
		{
			rs = DB.getInstance().queryBySQLCode(
					"category.rule.ConditionDAO.select().SELECTCONDITIONSBYRULEID_ELITE", paras);

			while (rs.next())
			{
				vo = new ConditionVO();
				vo.setRuleId(rs.getInt("ruleID"));
				vo.setCid(rs.getString("cid"));
				vo.setCondType(rs.getInt("baseCondId"));
				vo.setWSql(rs.getString("wSql"));
				vo.setCount(rs.getInt("count"));
				vo.setOSql(rs.getString("oSql"));
				vo.setSortId(rs.getInt("sortId"));
				conds.add(vo);
			}

		} catch (SQLException e)
		{
			throw new DAOException("��ȡ�������VO�����з����쳣:", e);
		} finally
		{
			DB.close(rs);
		}
		return conds;
	}
	/**
	 * ��ȡ�Ǿ�Ʒ�������
	 * @param ruleID
	 * @param condType 1�Ǿ�Ʒ���������0�ǷǾ�Ʒ�������
	 * @return
	 * @throws DAOException
	 */
	public List getNotEliteConditionsByRuleID(int ruleID) throws DAOException
	{
		ResultSet rs = null;
		ConditionVO vo = null;
		List conds = new ArrayList();
		Object[] paras = {new Integer(ruleID), new Integer(CategoryConstants.CONDTYPE_ELITE)};
		try
		{
			rs = DB.getInstance().queryBySQLCode(
					"category.rule.ConditionDAO.select().SELECTCONDITIONSBYRULEID_NOT_ELITE", paras);

			while (rs.next())
			{
				vo = new ConditionVO();
				vo.setRuleId(rs.getInt("ruleID"));
				vo.setCid(rs.getString("cid"));
				vo.setCondType(rs.getInt("baseCondId"));
				vo.setWSql(rs.getString("wSql"));
				vo.setCount(rs.getInt("count"));
				vo.setOSql(rs.getString("oSql"));
				vo.setSortId(rs.getInt("sortId"));
				conds.add(vo);
			}

		} catch (SQLException e)
		{
			throw new DAOException("��ȡ�������VO�����з����쳣:", e);
		} finally
		{
			DB.close(rs);
		}
		return conds;
	}

}
