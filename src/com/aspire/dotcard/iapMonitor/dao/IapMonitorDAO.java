package com.aspire.dotcard.iapMonitor.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.iapMonitor.vo.ContentVO;

public class IapMonitorDAO {

	/**
	 * 日志引用
	 */
	JLogger logger = LoggerFactory.getLogger(IapMonitorDAO.class);
	
	private static IapMonitorDAO dao = new IapMonitorDAO();
	
	private IapMonitorDAO()
	{}
	
	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static IapMonitorDAO getInstance()
	{
		return dao;
	}
	
	/**
	 * 查看和娱乐IAP榜单数量
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public int queryCountNum(String categoryId,String deviceId) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryCountNum() is starting ...");
		}

		String sqlCode = "iapMonitor.IapMonitorDAO.queryCountNum().SELECT";
		ResultSet rs = null;
		int countNum = 0;

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { deviceId,categoryId});
			if (rs != null && rs.next()) {
				countNum = rs.getInt("countNum");
			}
		} catch (SQLException e) {
			throw new DAOException("返回当前货架下是否还存在着商品信息发生异常:", e);
		}

		finally {
			DB.close(rs);
		}

		return countNum;
	}
	
	/**
	 * 查看和娱乐IAP榜单数量
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public int queryCountNumByCategoryId(String categoryId) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryCountNum() is starting ...");
		}

		//select count(1) as countNum from v_iap_monitor m where m.categoryid = ?
		String sqlCode = "iapMonitor.IapMonitorDAO.queryCountNumByCategoryId().SELECT";
		ResultSet rs = null;
		int countNum = 0;

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] {categoryId});
			if (rs != null && rs.next()) {
				countNum = rs.getInt("countNum");
			}
		} catch (SQLException e) {
			throw new DAOException("返回当前货架下是否还存在着商品信息发生异常:", e);
		}

		finally {
			DB.close(rs);
		}

		return countNum;
	}
	
	/**
	 * 查看重点系列指定时间（一周）内和娱乐IAP榜单数量
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 * @throws DataAccessException 
	 */
	public Map<String,Integer> queryCountNumByDeviceIds(String categoryId,String deviceId) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryCountNumByDeviceIds() is starting ...");
		}

		Map<String, Integer> map = new HashMap<String, Integer>();
		//select count(1) countNum,m.DEVICE_ID deviceId from v_iap_monitor m where m.categoryid = '100000905' and m.DEVICE_ID in (?) group by device_id
		String sqlCode = "iapMonitor.IapMonitorDAO.queryCountNumByDeviceIds().SELECT";
		ResultSet rs = null;

		String sql = DB.getInstance().getSQLByCode(sqlCode);
		StringBuffer sqlBuffer = new StringBuffer(sql);
		sqlBuffer.append(" and m.DEVICE_ID in (").append(deviceId).append(")");
		sqlBuffer.append(" group by device_id ");

		try {
			rs = DB.getInstance().query(sqlBuffer.toString(),
					new Object[] { categoryId});
			while (rs != null && rs.next()) {
				map.put(rs.getString("deviceId"), rs.getInt("countNum"));
			}
		} catch (SQLException e) {
			throw new DAOException("返回当前货架下是否还存在着商品信息发生异常:", e);
		}

		finally {
			DB.close(rs);
		}
		return map;
	}
	
	/**
	 * 查看重点系列指定时间（一周）内和娱乐IAP榜单数量
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public ArrayList<ContentVO> queryContentVOByDeviceIds(String categoryId,String deviceId) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryCountNumByDeviceIds() is starting ...");
		}
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		//select m.DEVICE_ID,m.contentid,m.contentname from v_iap_monitor m where m.categoryid = ? and m.DEVICE_ID in (?)
		String sqlCode = "iapMonitor.IapMonitorDAO.queryContentVOByDeviceIds().SELECT";
		ResultSet rs = null;
		ArrayList<ContentVO> list = new ArrayList<ContentVO>();
		
		String sql = DB.getInstance().getSQLByCode(sqlCode);
		StringBuffer sqlBuffer = new StringBuffer(sql);
		sqlBuffer.append(" and m.DEVICE_ID in (").append(deviceId).append(")");
		
		try {
			rs = DB.getInstance().query(sqlBuffer.toString(),
					new Object[] { categoryId});
			while (rs != null && rs.next()) {
				ContentVO content = new ContentVO();
				content.setContentId(rs.getString("contentid"));
				content.setContentName(rs.getString("contentname"));
				content.setDeviceId(rs.getString("DEVICE_ID"));
				list.add(content);
			}
		} catch (SQLException e) {
			throw new DAOException("返回当前货架下是否还存在着商品信息发生异常:", e);
		}

		finally {
			DB.close(rs);
		}
		return list;
	}
}
