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
 * ����ͬ��������ҵ�����ݺ��������ݣ�
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
	 * ��־����
	 */
	JLogger logger = LoggerFactory.getLogger(SynOpenOperationDAO.class);

	private static SynOpenOperationDAO dao = new SynOpenOperationDAO();

	private SynOpenOperationDAO()
	{}

	/**
	 * ����ģʽ
	 * 
	 * @return
	 */
	public static SynOpenOperationDAO getInstance()
	{

		return dao;
	}

	/**
	 * ͬ���������б�
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
	 * ͬ�����������б�
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
	 * Ϊ������Ӫ�������ݱ�������
	 */
	public String createContentIndex()
	{
		logger.info("��ʼ�Ա�" + "V_CM_OPEN_CONTEN��" + "��������:"
				+ "T_IDX_CM_OPEN_CONTEN");

		// create index IDX_VO_VIDEOID_TEMP on t_vo_video_tra (videoid, coderateid)
		String sqlCode = "category.SynOpenOperationDAO.updateOpenCateMap().createContentIndex";

		try {
			DB.getInstance().executeBatchBySQLCode(sqlCode, null);
		} catch (DAOException e) {
			logger.error("�Ա�" + "V_CM_OPEN_CONTEN��" + "��������:"
					+ "T_IDX_CM_OPEN_CONTENʱ����", e);
			return "Ϊ������Ӫ�������ݱ��������ɹ�!";
		}
		logger.info("���������ɹ�!");
		return "Ϊ������Ӫ�������ݱ�������ʧ��!";
	}
	
	/**
	 * ȫ��ͬ�������ݣ�ͨ����������Ȼ�����ʵ��
	 * 
	 * @param tableName
	 * @fromSql ������Դ��sql
	 * @throws BOException
	 *             ͨ�����̳����쳣
	 */
	public void fullSyncTables(String tableName, String fromSql)
			throws BOException
	{

		logger.info("��ʼͬ���� tableName:" + tableName);

		// ��ֹͬʱ�����������
		String timestamp = PublicUtil.getCurDateTime("HHSSS");
		String tempTableName = tableName + timestamp;
		String backupTableName = tableName + timestamp + "_bak";

		// ������ʱ��
		String createTempSql = "create table " + tempTableName + " as "
				+ fromSql;

		// ���ݾɱ�
		String backupTableSql = "alter table " + tableName + " rename to "
				+ backupTableName;

		// ����ʱ����Ϊ��ʽ��
		String replaceSql = "alter table " + tempTableName + " rename to "
				+ tableName;

		// ɾ�����ݱ�
		String dropBackupSql = "drop table " + backupTableName;

		// ɾ����ʱ��
		String dropTempSql = "drop table " + tempTableName;

		// ��ԭ���ݱ�
		String revertBackupTableSql = "alter table " + backupTableName
				+ " rename to " + tableName;

		logger.info("��ʼ������ʱ��:" + tempTableName);

		try
		{
			DB.getInstance().execute(createTempSql, null);
		}
		catch (DAOException e)
		{
			throw new BOException("������ʱ�����" + tempTableName, e);
		}

		logger.info("��ʼ���ݾɱ� :" + backupTableName);

		try
		{
			DB.getInstance().execute(backupTableSql, null);
		}
		catch (DAOException e)
		{

			throw new BOException("���ݱ�:" + tableName + "����" + tempTableName, e);
		}

		logger.info("��ʼ������ʱ����Ϊ��ʽ��:" + tempTableName);

		try
		{
			DB.getInstance().execute(replaceSql, null);

		}
		catch (DAOException e)
		{
			// ������ʱ��ʧ�ܣ���Ҫ��ԭ���ݱ���ɾ����ʱ��
			try
			{
				// ��ԭ���ݱ�
				DB.getInstance().execute(revertBackupTableSql, null);
				// ��Ҫɾ����ʱ��
				DB.getInstance().execute(dropTempSql, null);
			}
			catch (DAOException e1)
			{
				logger.error(e1);
			}
			throw new BOException("������ʱ��������" + tempTableName, e);
		}

		logger.info("ɾ�����ݱ� " + backupTableName);
		try
		{
			DB.getInstance().execute(dropBackupSql, null);
		}
		catch (DAOException e)
		{
			logger.error("ɾ�����ݱ�ʧ��:" + backupTableName);
		}
	}

	/**
	 * ���ͽ������֪ͨ
	 * 
	 * @param content
	 *            ��������
	 * @param phone
	 *            ���ͺ���
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
	 * ���������̱��������ӳ���ϵ
	 * 
	 * @return key:openchannelCode value:cateId
	 * @throws DAOException
	 */
	public Map getOpenCateMap() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ȡ�����̱��������ӳ���ϵ:��ʼִ��......");
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
			throw new DAOException("��ȡ�����̱��������ӳ���ϵ�����쳣:", e);
		}
		finally
		{
			DB.close(rs);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("��ȡ�����̱��������ӳ���ϵ:ִ�н���.");
		}

		return openCateMap;
	}

	/**
	 * ���������̱����������̶����ϵ
	 * 
	 * @return key:openchannelCode value:OpenOperationVO
	 * @throws DAOException
	 */
	public Map getOpenChannel() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ȡ�����̱����������̶����ϵ:��ʼִ��......");
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
			throw new DAOException("��ȡ�����̱����������̶����ϵ�����쳣:", e);
		}
		finally
		{
			DB.close(rs);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("��ȡ�����̱����������̶����ϵ:ִ�н���.");
		}

		return openChannelMap;
	}

	/**
	 * ���������̱�������ܱ����ӳ���ϵ
	 * 
	 * @param cateId
	 *            ���ܱ���
	 * @param cateName
	 *            ��������
	 * @param openId
	 *            �����̱���
	 * @param ruleId
	 *            ��Ӧ�Ĺ���id
	 * @throws DAOException
	 */
	public void createOpenCateMap(String cateId, String cateName,
			String channelId, String ruleId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���������̱�������ܱ����ӳ���ϵ:��ʼִ��......");
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
			throw new DAOException("���������̱�������ܱ����ӳ���ϵ�����쳣:", e);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("���������̱�������ܱ����ӳ���ϵ:ִ�н���.");
		}
	}

	/**
	 * �޸������̱�������ܱ����ӳ���ϵ
	 * 
	 * @param cateId
	 *            ���ܱ���
	 * @param cateName
	 *            ��������
	 * @param openId
	 *            �����̱���
	 * @throws DAOException
	 */
	public void updateOpenCateMap(String cateId, String cateName,
			String channelId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�޸������̱�������ܱ����ӳ���ϵ:��ʼִ��......");
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
			throw new DAOException("�޸������̱�������ܱ����ӳ���ϵ:", e);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("�޸������̱�������ܱ����ӳ���ϵ:ִ�н���.");
		}
	}

	/**
	 * �޸������̱�������ܱ���Ĺ���ӳ���ϵ
	 * 
	 * @param ruleId
	 *            ����id
	 * @param cateId
	 *            ���ܱ���
	 * @param openId
	 *            �����̱���
	 * @throws DAOException
	 */
	public void updateRuleOpenCateMap(String ruleId, String cateId,
			String channelId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�޸������̱�������ܱ���Ĺ����ӳ���ϵ:��ʼִ��......");
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
			throw new DAOException("�޸������̱�������ܱ���Ĺ����ӳ���ϵ:", e);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("�޸������̱�������ܱ���Ĺ����ӳ���ϵ:ִ�н���.");
		}
	}

	/**
	 * ɾ�������̱�������ܱ����ӳ���ϵ
	 * 
	 * @param cateId
	 *            ���ܱ���
	 * @param openId
	 *            �����̱���
	 * @throws DAOException
	 */
	public void delOpenCateMap(String cateId, String channelId)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ɾ�������̱�������ܱ����ӳ���ϵ:��ʼִ��......");
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
			throw new DAOException("ɾ�������̱�������ܱ����ӳ���ϵ:", e);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("ɾ�������̱�������ܱ����ӳ���ϵ:ִ�н���.");
		}
	}
}
