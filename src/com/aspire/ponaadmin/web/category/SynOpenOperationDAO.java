package com.aspire.ponaadmin.web.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * 数据同步（包括业务数据和内容数据）
 * </p>
 * <p>
 * Copyright (c) 2003-2007 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class SynOpenOperationDAO
{

	/**
	 * 日志引用
	 */
	JLogger logger = LoggerFactory.getLogger(SynOpenOperationDAO.class);

	private static SynOpenOperationDAO dao = new SynOpenOperationDAO();

	private SynOpenOperationDAO()
	{}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static SynOpenOperationDAO getInstance()
	{

		return dao;
	}

	/**
	 * 同步渠道商列表
	 * 
	 * @throws BOException
	 */
	public void synChannelBusine() throws BOException
	{
		String tableName = "V_CM_OPEN_CHANNEL";
		String sql = "select * from PPMS_V_OM_OPEN_CHANNEL t";
		this.fullSyncTables(tableName, sql);
	}

	/**
	 * 同步渠道内容列表
	 * 
	 * @throws BOException
	 */
	public void synContent() throws BOException
	{
		String tableName = "V_CM_OPEN_CONTEN";
		String sql = "select * from PPMS_V_CM_CONTENT_CHANNEL_SSMS t";
		this.fullSyncTables(tableName, sql);
	}
	
	/**
	 * 为开放运营内容数据表创建索引
	 */
	public String createContentIndex()
	{
		logger.info("开始对表：" + "V_CM_OPEN_CONTEN，" + "创建索引:"
				+ "T_IDX_CM_OPEN_CONTEN");

		// create index IDX_VO_VIDEOID_TEMP on t_vo_video_tra (videoid, coderateid)
		String sqlCode = "category.SynOpenOperationDAO.updateOpenCateMap().createContentIndex";

		try {
			DB.getInstance().executeBatchBySQLCode(sqlCode, null);
		} catch (DAOException e) {
			logger.error("对表：" + "V_CM_OPEN_CONTEN，" + "创建索引:"
					+ "T_IDX_CM_OPEN_CONTEN时出错！", e);
			return "为开放运营内容数据表创建索引成功!";
		}
		logger.info("创建索引成功!");
		return "为开放运营内容数据表创建索引失败!";
	}
	
	/**
	 * 全量同步表内容，通过别名建表然后改名实现
	 * 
	 * @param tableName
	 * @fromSql 数据来源的sql
	 * @throws BOException
	 *             通过过程出现异常
	 */
	public void fullSyncTables(String tableName, String fromSql)
			throws BOException
	{

		logger.info("开始同步表 tableName:" + tableName);

		// 防止同时操作的情况。
		String timestamp = PublicUtil.getCurDateTime("HHSSS");
		String tempTableName = tableName + timestamp;
		String backupTableName = tableName + timestamp + "_bak";

		// 创建临时表
		String createTempSql = "create table " + tempTableName + " as "
				+ fromSql;

		// 备份旧表
		String backupTableSql = "alter table " + tableName + " rename to "
				+ backupTableName;

		// 该临时表名为正式表
		String replaceSql = "alter table " + tempTableName + " rename to "
				+ tableName;

		// 删除备份表
		String dropBackupSql = "drop table " + backupTableName;

		// 删除临时表
		String dropTempSql = "drop table " + tempTableName;

		// 还原备份表
		String revertBackupTableSql = "alter table " + backupTableName
				+ " rename to " + tableName;

		logger.info("开始创建临时表:" + tempTableName);

		try
		{
			DB.getInstance().execute(createTempSql, null);
		}
		catch (DAOException e)
		{
			throw new BOException("创建临时表出错：" + tempTableName, e);
		}

		logger.info("开始备份旧表 :" + backupTableName);

		try
		{
			DB.getInstance().execute(backupTableSql, null);
		}
		catch (DAOException e)
		{

			throw new BOException("备份表:" + tableName + "出错：" + tempTableName, e);
		}

		logger.info("开始更改临时表名为正式表:" + tempTableName);

		try
		{
			DB.getInstance().execute(replaceSql, null);

		}
		catch (DAOException e)
		{
			// 更改临时表失败，需要还原备份表，并删除临时表
			try
			{
				// 还原备份表
				DB.getInstance().execute(revertBackupTableSql, null);
				// 需要删除临时表。
				DB.getInstance().execute(dropTempSql, null);
			}
			catch (DAOException e1)
			{
				logger.error(e1);
			}
			throw new BOException("更改临时表名出错：" + tempTableName, e);
		}

		logger.info("删除备份表 " + backupTableName);
		try
		{
			DB.getInstance().execute(dropBackupSql, null);
		}
		catch (DAOException e)
		{
			logger.error("删除备份表失败:" + backupTableName);
		}
	}

	/**
	 * 发送结果短信通知
	 * 
	 * @param content
	 *            短信内容
	 * @param phone
	 *            发送号码
	 * @throws DAOException
	 */
	public void sendMsg(String phone, String content) throws DAOException
	{
		String sqlCode = "com.aspire.dotcard.syncData.dao.DataSyncDAO.sendMsg";
		String[] params =
		{ phone, content };
		DB.getInstance().executeBySQLCode(sqlCode, params);
	}

	/**
	 * 返回渠道商编码与货架映射关系
	 * 
	 * @return key:openchannelCode value:cateId
	 * @throws DAOException
	 */
	public Map getOpenCateMap() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("获取渠道商编码与货架映射关系:开始执行......");
		}

		ResultSet rs = null;
		Map openCateMap = new HashMap();

		try
		{
			// select * from t_r_opencrte_map t
			rs = DB.getInstance().queryBySQLCode(
					"category.SynOpenOperationDAO.getOpenCateMap().select",
					null);

			while (rs.next())
			{
				openCateMap.put(rs.getString("openchannelcode"), rs
						.getString("categoryid")
						+ "_"
						+ rs.getString("companyName")
						+ "_"
						+ rs.getString("ruleId"));
			}

		}
		catch (SQLException e)
		{
			throw new DAOException("获取渠道商编码与货架映射关系发生异常:", e);
		}
		finally
		{
			DB.close(rs);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("获取渠道商编码与货架映射关系:执行结束.");
		}

		return openCateMap;
	}

	/**
	 * 返回渠道商编码与渠道商对象关系
	 * 
	 * @return key:openchannelCode value:OpenOperationVO
	 * @throws DAOException
	 */
	public Map getOpenChannel() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("获取渠道商编码与渠道商对象关系:开始执行......");
		}

		ResultSet rs = null;
		Map openChannelMap = new HashMap();
		SynOpenOperationVO vo = null;

		try
		{
			// select * from V_CM_OPEN_CHANNEL t
			rs = DB.getInstance().queryBySQLCode(
					"category.SynOpenOperationDAO.getOpenChannel().select",
					null);

			while (rs.next())
			{
				vo = new SynOpenOperationVO();
				vo.setOpenChannelCode(rs.getString("openchannelcode"));
				vo.setCompanyName(rs.getString("companyName"));
				vo.setChannelId(rs.getString("channelId"));
				vo.setIsAuto(rs.getString("isAuto"));
				openChannelMap.put(vo.getOpenChannelCode(), vo);
			}

		}
		catch (SQLException e)
		{
			throw new DAOException("获取渠道商编码与渠道商对象关系发生异常:", e);
		}
		finally
		{
			DB.close(rs);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("获取渠道商编码与渠道商对象关系:执行结束.");
		}

		return openChannelMap;
	}

	/**
	 * 创建渠道商编码与货架编码的映射关系
	 * 
	 * @param cateId
	 *            货架编码
	 * @param cateName
	 *            货架名称
	 * @param openId
	 *            渠道商编码
	 * @param ruleId
	 *            对应的规则id
	 * @throws DAOException
	 */
	public void createOpenCateMap(String cateId, String cateName,
			String channelId, String ruleId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("创建渠道商编码与货架编码的映射关系:开始执行......");
		}

		try
		{
			// insert into T_R_OPENCRTE_MAP (CATEGORYID, COMPANYNAME,
			// OPENCHANNELCODE, ruleid) values (?, ?, ?, ?)
			DB.getInstance().executeBySQLCode(
					"category.SynOpenOperationDAO.createOpenCateMap().create",
					new Object[]
					{ cateId, cateName, channelId, ruleId });
		}
		catch (DAOException e)
		{
			throw new DAOException("创建渠道商编码与货架编码的映射关系发生异常:", e);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("创建渠道商编码与货架编码的映射关系:执行结束.");
		}
	}

	/**
	 * 修改渠道商编码与货架编码的映射关系
	 * 
	 * @param cateId
	 *            货架编码
	 * @param cateName
	 *            货架名称
	 * @param openId
	 *            渠道商编码
	 * @throws DAOException
	 */
	public void updateOpenCateMap(String cateId, String cateName,
			String channelId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("修改渠道商编码与货架编码的映射关系:开始执行......");
		}

		try
		{
			// update T_R_OPENCRTE_MAP m set m.companyname=? where
			// m.categoryid=? and m.openchannelcode=?
			DB.getInstance().executeBySQLCode(
					"category.SynOpenOperationDAO.updateOpenCateMap().update",
					new Object[]
					{ cateName, cateId, channelId });
		}
		catch (DAOException e)
		{
			throw new DAOException("修改渠道商编码与货架编码的映射关系:", e);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("修改渠道商编码与货架编码的映射关系:执行结束.");
		}
	}

	/**
	 * 修改渠道商编码与货架编码的规则映射关系
	 * 
	 * @param ruleId
	 *            规则id
	 * @param cateId
	 *            货架编码
	 * @param openId
	 *            渠道商编码
	 * @throws DAOException
	 */
	public void updateRuleOpenCateMap(String ruleId, String cateId,
			String channelId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("修改渠道商编码与货架编码的规则的映射关系:开始执行......");
		}

		try
		{
			// update T_R_OPENCRTE_MAP m set m.ruleId=? where m.categoryid=? and
			// m.openchannelcode=?
			DB
					.getInstance()
					.executeBySQLCode(
							"category.SynOpenOperationDAO.updateRuleOpenCateMap().update",
							new Object[]
							{ ruleId, cateId, channelId });
		}
		catch (DAOException e)
		{
			throw new DAOException("修改渠道商编码与货架编码的规则的映射关系:", e);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("修改渠道商编码与货架编码的规则的映射关系:执行结束.");
		}
	}

	/**
	 * 删除渠道商编码与货架编码的映射关系
	 * 
	 * @param cateId
	 *            货架编码
	 * @param openId
	 *            渠道商编码
	 * @throws DAOException
	 */
	public void delOpenCateMap(String cateId, String channelId)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("删除渠道商编码与货架编码的映射关系:开始执行......");
		}

		try
		{
			// delete from T_R_OPENCRTE_MAP m where m.categoryid=? and
			// m.openchannelcode=?
			DB.getInstance().executeBySQLCode(
					"category.SynOpenOperationDAO.delOpenCateMap().del",
					new Object[]
					{ cateId, channelId });
		}
		catch (DAOException e)
		{
			throw new DAOException("删除渠道商编码与货架编码的映射关系:", e);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("删除渠道商编码与货架编码的映射关系:执行结束.");
		}
	}
}
