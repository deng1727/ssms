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
	 * 记录日志的实例对象
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(ConditionDAO.class);
	/**
	 * singleton模式的实例
	 */
	private static ConditionDAO instance = new ConditionDAO();

	/**
	 * 构造方法，由singleton模式调用
	 */
	private ConditionDAO()
	{
	}

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static ConditionDAO getInstance()
	{

		return instance;
	}

	/**
	 * 根据规则获取个条件的类型。
	 * @param ruleID
	 * @param condType 1是精品库的条件，0是非精品库的条件
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
			throw new DAOException("提取结果集到VO对象中发生异常:", e);
		} finally
		{
			DB.close(rs);
		}
		return conds;
	}
	/**
	 * 获取非精品库的条件
	 * @param ruleID
	 * @param condType 1是精品库的条件，0是非精品库的条件
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
			throw new DAOException("提取结果集到VO对象中发生异常:", e);
		} finally
		{
			DB.close(rs);
		}
		return conds;
	}

}
