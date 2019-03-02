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
	 * 应用类分页读取VO的实现类
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
	 * 查询渠道信息
	 * 
	 * @param page
	 * @param channelsNo
	 *            用户登录Id
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
			LOGGER.error("查询渠道信息发生异常:", e);
			throw new DAOException("查询渠道信息发生异常:", e);
		}
	}

	/**
	 * 查询渠道总数
	 * 
	 * @param channelsNo
	 *            用户登录Id
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
			LOGGER.debug("查询渠道总数发生异常:", e);
			throw new DAOException("查询渠道总数发生异常:", e);
		} catch (SQLException e) {
			LOGGER.debug("查询渠道总数发生异常:", e);
			throw new DAOException("查询渠道总数发生异常:", e);
		}finally
        {
            DB.close(rs);
        }
		return map;
	}

	/**
	 * 添加渠道信息
	 * 
	 * @param map
	 * @throws DAOException
	 */
	public void insertChannelContent(Map<String, Object> map)
			throws DAOException {
		String sqlCode = "com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO.insertChannelContent";
		String channelsNo;
		// 进行事务操作
		TransactionDB tdb = null;
		try {
			channelsNo = this.queryChannelsNo();
			if (channelsNo == null || this.isExitChannelNo(map.get("channelsNo").toString())) {
				throw new DAOException("查询未用的渠道号不存在");
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
			LOGGER.debug("添加渠道信息发生异常:", e);
			throw new DAOException("添加渠道信息发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}

	/**
	 * 查询未用的渠道号
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
			LOGGER.debug("查询未用的渠道号发生异常:", e);
			throw new DAOException("查询未用的渠道号发生异常:", e);
		} catch (SQLException e) {
			LOGGER.debug("查询未用的渠道号发生异常:", e);
			throw new DAOException("查询未用的渠道号发生异常:", e);
		} finally
        {
            DB.close(rs);
        }
	}

	/**
	 * 查询渠道信息
	 * 
	 * @param channelId
	 *            渠道Id
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
			LOGGER.error("查询渠道信息发生异常:", e);
			throw new DAOException("查询渠道信息发生异常:", e);
		} catch (SQLException e) {
			LOGGER.error("查询渠道信息发生异常:", e);
			throw new DAOException("查询渠道信息发生异常:", e);
		}finally
        {
            DB.close(rs);
        }

		return vo;
	}

	/**
	 * 编辑渠道信息
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
			LOGGER.debug("编辑渠道信息发生异常:", e);
			throw new DAOException("编辑渠道信息发生异常:", e);
		}
	}
	/**
	 * 渠道商的渠道号是否用完
	 * 
	 * @param channelsNo 用户登录Id
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
			LOGGER.debug("渠道商的渠道号是否用完发生异常:", e);
			throw new DAOException("渠道商的渠道号是否用完发生异常:", e);
		} catch (SQLException e) {
			LOGGER.debug("渠道商的渠道号是否用完发生异常:", e);
			throw new DAOException("渠道商的渠道号是否用完发生异常:", e);
		}finally
        {
            DB.close(rs);
        }
	}

}
