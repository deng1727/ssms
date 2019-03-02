package com.aspire.ponaadmin.webpps.mychannel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.webpps.mychannel.vo.MyChannelVO;

public class MyChannelDAO {

	private final static JLogger LOGGER = LoggerFactory
			.getLogger(MyChannelDAO.class);

	private static MyChannelDAO instance = new MyChannelDAO();

	private MyChannelDAO() {

	}

	public static MyChannelDAO getInstance() {
		return instance;
	}

	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 */
	private class MyChannelPageVO implements PageVOInterface {

		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {

			MyChannelVO vo = (MyChannelVO) content;
			vo.setChannelId(rs.getString("channelid"));
			vo.setChannelName(rs.getString("channelname"));
			vo.setChannelType(rs.getString("channeltype"));
			vo.setCreateDate(rs.getTimestamp("createdate"));
		}

		public Object createObject() {

			return new MyChannelVO();
		}
	}

	/**
	 * ��ѯ������Ϣ
	 * 
	 * @param page
	 * @param channelsNo
	 *            �û���¼Id
	 * @throws DAOException
	 */
	public void queryChannelInfoList(PageResult page, String channelsNo)
			throws DAOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("queryChannelList() is starting ...");
		}
		String sqlCode = "com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO.queryChannelInfoList";
		try {
			page.excute(DB.getInstance().getSQLByCode(sqlCode),
					new Object[] { channelsNo }, new MyChannelPageVO());
		} catch (DAOException e) {
			LOGGER.error("��ѯ������Ϣ�����쳣:", e);
			throw new DAOException("��ѯ������Ϣ�����쳣:", e);
		}
	}

	/**
	 * ��ѯ��������
	 * 
	 * @param channelsNo
	 *            �û���¼Id
	 * @return
	 * @throws DAOException
	 */
	public Map<String, Object> queryChannelsNoTotal(String channelsNo)
			throws DAOException {
		String sqlCode = "com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO.queryChannelsNoTotal";
		Map<String, Object> map = new HashMap<String, Object>();
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { channelsNo });
			if (rs.next()) {
				map.put("total", rs.getString("total"));
				map.put("used", rs.getString("used"));
			}
		} catch (DAOException e) {
			LOGGER.debug("��ѯ�������������쳣:", e);
			throw new DAOException("��ѯ�������������쳣:", e);
		} catch (SQLException e) {
			LOGGER.debug("��ѯ�������������쳣:", e);
			throw new DAOException("��ѯ�������������쳣:", e);
		}finally
        {
            DB.close(rs);
        }
		return map;
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param map
	 * @throws DAOException
	 */
	public void insertChannelContent(Map<String, Object> map)
			throws DAOException {
		String sqlCode = "com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO.insertChannelContent";
		String channelsNo;
		// �����������
		TransactionDB tdb = null;
		try {
			channelsNo = this.queryChannelsNo();
			if (channelsNo == null || this.isExitChannelNo(map.get("channelsNo").toString())) {
				throw new DAOException("��ѯδ�õ������Ų�����");
			} else {
				tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(
						sqlCode,
						new Object[] { channelsNo, map.get("channelType"),
								map.get("channelName"), map.get("channelDesc"),
								map.get("channelsId") });
				tdb.executeBySQLCode(
						"com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO.insertChannelContent.UPDATE",
						new Object[] { channelsNo });
				tdb.commit();
			}
		} catch (DAOException e) {
			tdb.rollback();
			LOGGER.debug("���������Ϣ�����쳣:", e);
			throw new DAOException("���������Ϣ�����쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}

	/**
	 * ��ѯδ�õ�������
	 * 
	 * @return
	 * @throws DAOException
	 */
	public String queryChannelsNo() throws DAOException {
		String sqlCode = "com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO.queryChannelsNo";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] {});
			if (rs.next()) {
				return rs.getString("channelsno");
			}
			return null;
		} catch (DAOException e) {
			LOGGER.debug("��ѯδ�õ������ŷ����쳣:", e);
			throw new DAOException("��ѯδ�õ������ŷ����쳣:", e);
		} catch (SQLException e) {
			LOGGER.debug("��ѯδ�õ������ŷ����쳣:", e);
			throw new DAOException("��ѯδ�õ������ŷ����쳣:", e);
		} finally
        {
            DB.close(rs);
        }
	}

	/**
	 * ��ѯ������Ϣ
	 * 
	 * @param channelId
	 *            ����Id
	 * @return
	 * @throws DAOException
	 */
	public MyChannelVO getChannelDetail(String channelId) throws DAOException {
		String sqlCode = "com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO.getChannelDetail";
		ResultSet rs = null;
		MyChannelVO vo = new MyChannelVO();
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { channelId });
			while (rs.next()) {
				vo.setChannelId(rs.getString("channelid"));
				vo.setChannelName(rs.getString("channelname"));
				vo.setChannelType(rs.getString("channeltype"));
				vo.setCreateDate(rs.getTimestamp("createdate"));
				vo.setChannelDesc(rs.getString("channelDesc"));
			}
		} catch (DAOException e) {
			LOGGER.error("��ѯ������Ϣ�����쳣:", e);
			throw new DAOException("��ѯ������Ϣ�����쳣:", e);
		} catch (SQLException e) {
			LOGGER.error("��ѯ������Ϣ�����쳣:", e);
			throw new DAOException("��ѯ������Ϣ�����쳣:", e);
		}finally
        {
            DB.close(rs);
        }

		return vo;
	}

	/**
	 * �༭������Ϣ
	 * 
	 * @param map
	 * @throws DAOException
	 */
	public void updateChannelContent(Map<String, Object> map)
			throws DAOException {
		String sqlCode = "com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO.updateChannelContent";
		try {
			DB.getInstance().executeBySQLCode(
					sqlCode,
					new Object[] { map.get("channelName"),
							map.get("channelDesc"), map.get("channelId") });
		} catch (DAOException e) {
			LOGGER.debug("�༭������Ϣ�����쳣:", e);
			throw new DAOException("�༭������Ϣ�����쳣:", e);
		}
	}
	/**
	 * �����̵��������Ƿ�����
	 * 
	 * @param channelsNo �û���¼Id
	 * @return
	 * @throws DAOException
	 */
	public boolean isExitChannelNo(String channelsNo) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO.isExitChannelNo";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] {channelsNo});
			if (rs.next()) {
				String total = rs.getString("total");
				if(total != null && Integer.parseInt(total) > 0)
				return false;
			}
			return true;
		} catch (DAOException e) {
			LOGGER.debug("�����̵��������Ƿ����귢���쳣:", e);
			throw new DAOException("�����̵��������Ƿ����귢���쳣:", e);
		} catch (SQLException e) {
			LOGGER.debug("�����̵��������Ƿ����귢���쳣:", e);
			throw new DAOException("�����̵��������Ƿ����귢���쳣:", e);
		}finally
        {
            DB.close(rs);
        }
	}

}
