/*
 * 文件名：ContentExigenceDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.repository.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.vo.ContentTmp;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ContentExigenceDAO
{
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(ContentExigenceDAO.class);
	
	/**
	 * singleton模式的实例
	 */
	private static ContentExigenceDAO instance = new ContentExigenceDAO();
	
	/**
	 * 构造方法，由singleton模式调用
	 */
	private ContentExigenceDAO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static ContentExigenceDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 */
	private class ContentExigencePageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			ContentExigenceVO vo = (ContentExigenceVO) content;
			vo.setContentId(rs.getString("contentId"));
			vo.setSysdate(String.valueOf(rs.getDate("dateTime")));
			vo.setType(getTypeName(rs.getInt("type")));
			vo.setSubType((getSubTypeName(Integer.parseInt(rs
					.getString("subtype")))));
		}
		
		/**
		 * 返回同步类型转义信息
		 * 
		 * @param type
		 * @return
		 */
		private String getTypeName(int type)
		{
			String typeName = "";
			
			switch (type)
			{
				case 0:
					typeName = "待同步";
					break;
				case 1:
					typeName = "上线";
					break;
				case 2:
					typeName = "更新";
					break;
				case 3:
					typeName = "下线";
					break;
				case 4:
					typeName = "同步失败";
					break;
			}
			
			return typeName;
		}
		
		/**
		 * 返回内容类型转义信息
		 * 
		 * @param subType
		 * @return
		 */
		private String getSubTypeName(int subType)
		{
			String typeName = "";
			
			switch (subType)
			{
				case 1:
					typeName = "mm普通应用";
					break;
				case 2:
					typeName = "widget应用";
					break;
				case 3:
					typeName = "ZCOM应用";
					break;
				case 4:
					typeName = "FMM应用";
					break;
				case 5:
					typeName = "jil应用";
					break;
				case 6:
					typeName = "MM大赛应用";
					break;
				case 7:
					typeName = "孵化应用";
					break;
				case 8:
					typeName = "孵化厂商应用";
					break;
				case 9:
					typeName = "香港MM";
					break;
				case 10:
					typeName = "OVI应用";
					break;
				case 11:
					typeName = "套餐应用";
					break;
				default:
					typeName = String.valueOf(subType);
					break;
			}
			return typeName;
		}
		
		public Object createObject()
		{
			return new ContentExigenceVO();
		}
	}
	
	/**
	 * 查询定义的将要紧急上线的内容列表
	 * 
	 * @param page
	 * @throws DAOException
	 */
	public void queryContentExigenceList(PageResult page) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryContentExigenceList() is starting ...");
		}
		
		// select * from t_exigencecontent t
		String sqlCode = "exigence.ContentExigenceDAO.queryContentExigenceList().SELECT";
		
		try
		{
			page.excuteBySQLCode(sqlCode, null, new ContentExigencePageVO());
		}
		catch (DAOException e)
		{
			throw new DAOException("查询定义的将要紧急上线的内容列表时发生数据库异常。", e);
		}
	}
	
	/**
	 * 删除紧急上线的内容列表
	 * 
	 * @param ids
	 * @throws DAOException
	 */
	public void delContentExigence(String[] ids) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delContentExigence() is starting ...");
		}
		
		// delete from t_exigencecontent e where e.contentId = ?
		String sql = "exigence.ContentExigenceDAO.delContentExigence().del";
		String sqlCode[] = new String[ids.length];;
		Object[][] object = new Object[ids.length][1];
		
		for (int i = 0; i < ids.length; i++)
		{
			sqlCode[i] = sql;
			object[i][0] = ids[i];
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("查询定义的将要紧急上线的内容列表时发生数据库异常。", e);
		}
	}
	
	/**
	 * 清空原列表
	 * 
	 * @throws DAOException
	 */
	public void delAllContentExigence() throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delAllContentExigence() is starting ...");
		}
		
		// delete from t_exigencecontent e
		String sql = "exigence.ContentExigenceDAO.delAllContentExigence().del";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, null);
		}
		catch (DAOException e)
		{
			throw new DAOException("查询定义的将要紧急上线的内容列表时发生数据库异常。", e);
		}
	}
	
	/**
	 * 反写用户导入表中的内容同步类型
	 * 
	 * @param contentList
	 *            内容id列
	 * @param type
	 *            操作类型
	 * 
	 * @throws DAOException
	 */
	public void updateContentExigenceType(List contentList, String type)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateContentExigenceType() is starting ...");
		}
		
		// update t_exigencecontent e set e.type = ? where e.contentid = ?
		String sql = "exigence.ContentExigenceDAO.updateContentExigenceType().update";
		String sqlCode[] = new String[contentList.size()];
		Object[][] object = new Object[contentList.size()][2];
		
		for (int i = 0; i < contentList.size(); i++)
		{
			sqlCode[i] = sql;
			object[i][0] = type;
			ContentTmp temp = (ContentTmp) contentList.get(i);
			object[i][1] = temp.getContentId();
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("查询定义的将要紧急上线的内容列表时发生数据库异常。", e);
		}
		
	}
	
	/**
	 * 校驗文件中數據是否在視圖中存在
	 * 
	 * @param list
	 * @throws DAOException
	 */
	public String verifyContentExigence(List list) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delAllContentExigence() is starting ...");
		}
		
		// select 1 from ppms_v_cm_content c where c.contentid = ?
		String sql = "exigence.ContentExigenceDAO.verifyContentExigence().select";
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		// 迭代查詢
		for (int i = 0; i < list.size(); i++)
		{
			String temp = (String) list.get(i);
			try
			{
				rs = DB.getInstance().queryBySQLCode(sql.toString(),
						new Object[] { temp });
				// 如果不存在相應數據
				if (!rs.next())
				{
					list.remove(i);
					i--;
					sb.append(temp).append(". ");
				}
			}
			catch (SQLException e)
			{
				throw new DAOException("查看指定货架中是否存在指定音乐时发生异常:", e);
			}
			finally
			{
				DB.close(rs);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 导入紧急上线的内容列表
	 * 
	 * @param list
	 * @throws BOException
	 */
	public void importContentExigence(List list) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("importContentExigence() is starting ...");
		}
		
		// insert into t_exigencecontent (contentid, dateTime, subtype) select
		// c.contentid, sysdate, c.servtype from ppms_v_service c where
		// c.contentid = ?
		String sql = "exigence.ContentExigenceDAO.importContentExigence().add";
		String sqlCode[] = new String[list.size()];
		Object[][] object = new Object[list.size()][1];
		
		for (int i = 0; i < list.size(); i++)
		{
			sqlCode[i] = sql;
			object[i][0] = list.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("查询定义的将要紧急上线的内容列表时发生数据库异常。", e);
		}
	}
	
	/**
	 * 得到电子流上次导入紧急上下线数据的最后时间
	 * 
	 * @return
	 * @throws DAOException
	 */
	public String getSysContentExigenceDate() throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("sysContentExigence() is starting ...");
		}
		
		// select decode(MAX(l.lasttime), '', '1999-01-01',
		// to_char(MAX(l.lasttime), 'YYYY-MM-DD HH24:MI:SS')) from
		// t_exigence_lasttime l
		String sql = "exigence.ContentExigenceDAO.getSysContentExigenceDate().sys";
		ResultSet rs = null;
		String date = "1999-01-01 00:00:00";
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sql, null);
			
			if (rs.next())
			{
				date = rs.getString(1);
			}
		}
		catch (DAOException e)
		{
			throw new DAOException("得到电子流上次导入紧急上下线数据的最后时间时发生数据库异常。", e);
		}
		catch (SQLException e)
		{
			throw new DAOException("得到电子流上次导入紧急上下线数据的最后时间时发生数据库异常。", e);
		}
		
		return date;
	}
	
	/**
	 * 用于定时执行紧急上下线任务时，取得电子流紧急上下线数据，存入临时表中
	 * 
	 * @throws DAOException
	 */
	public void sysContentExigence(String date) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("sysContentExigence() is starting ...");
		}
		
		// insert into t_exigencecontent (contentid, dateTime, subtype) select
		// c.contentid, sysdate, c.thirdapptype from ppms_v_cm_content c,
		// PPMS_V_CM_CONTENT_UPGRADE u where c.contentid = u.contentid and
		// u.conlupddate > to_date(?,'YYYY-MM-DD HH24:MI:SS')
		String sql = "exigence.ContentExigenceDAO.sysContentExigence().sys";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, new Object[] { date });
		}
		catch (DAOException e)
		{
			throw new DAOException("取得电子流紧急上下线数据，存入临时表中时发生数据库异常。", e);
		}
	}
	
	/**
	 * 用于定时执行紧急上下线任务时，写入最后一次执行系统级紧急上下线任务时间
	 * 
	 * @throws DAOException
	 */
	public void addExigenceLastTime() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("addExigenceLastTime() is starting ...");
		}
		
		// insert into t_exigence_lasttime (lasttime) values (sysdate)
		String sql = "exigence.ContentExigenceDAO.addExigenceLastTime().add";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, null);
		}
		catch (DAOException e)
		{
			throw new DAOException(
					"用于定时执行紧急上下线任务时，写入最后一次执行系统级紧急上下线任务时间时发生数据库异常。", e);
		}
	}
	
	/**
	 * 判断当前临时表中是否存在数据。
	 * 
	 * @throws DAOException
	 */
	public boolean hasExigenceContent() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("hasExigenceContent() is starting ...");
		}
		
		// select 1 from t_exigencecontent t
		String sql = "exigence.ContentExigenceDAO.hasExigenceContent().select";
		
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sql.toString(), null);
			
			// 如果存在相應數據
			if (rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (SQLException e)
		{
			throw new DAOException("查看指定货架中是否存在指定音乐时发生异常:", e);
		}
		finally
		{
			DB.close(rs);
		}
	}
	
	/**
	 * 清空紧急上下线操作历史表数据信息
	 * 
	 * @throws DAOException
	 */
	public void delGoodsChangeHis() throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delGoodsChangeHis() is starting ...");
		}
		
		// delete from t_goods_change_his
		String sql = "exigence.ContentExigenceDAO.delGoodsChangeHis().del";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, null);
		}
		catch (DAOException e)
		{
			throw new DAOException("查询定义的将要紧急上线的内容列表时发生数据库异常。", e);
		}
	}
	
	/**
	 * 用于返回当前表中处于待处理状态的数据
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<String> queryExigenceIdListByType() throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delAllContentExigence() is starting ...");
		}
		
		List<String> list = new ArrayList<String>();
		
		// select e.contentid from t_exigencecontent e where e.type = 0
		String sql = "exigence.ContentExigenceDAO.queryExigenceIdListByType().query";
		
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sql, null);
			
			while (rs.next())
			{
				list.add(rs.getString("contentid"));
			}
		}
		catch (SQLException e)
		{
			throw new DAOException("用于返回当前表中处于待处理状态的数据时发生数据库异常。", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	
	/**
	 * 紧急上下线前变更v_service表中数据信息
	 * 
	 * @throws DAOException
	 */
	public void updateServiceDate(List<String> contentIdList) throws DAOException
	{
		logger.info("updateServiceDate() is starting ...");
		
		// delete from v_service s where s.contentid in (select e.contentid from
		// t_exigencecontent e where e.type = 0)
		String sqlD = "exigence.ContentExigenceDAO.updateServiceDate().del";
		
		// insert into v_service select * from ppms_v_service s where s.contentid = ?
		String sql = "exigence.ContentExigenceDAO.updateServiceDate().add";
		
		String[] sqlO = new String[contentIdList.size()+1];
		Object[][] sqlV = new Object[contentIdList.size()+1][1];
		
		sqlO[0] = sqlD;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i+1] = sql;
			sqlV[i+1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("查询定义的将要紧急上线的内容列表时发生数据库异常。", e);
		}

		logger.info("updateServiceDate() is end ...");
	}
	
	/**
	 * 紧急上下线前增量处理视图v_cm_content中数据信息
	 * 
	 * @throws DAOException
	 */
	public void updateVcontentDate(List<String> contentIdList) throws DAOException
	{
		
		logger.info("updateVcontentDate() is starting ...");
		
		// delete from v_cm_content s where s.contentid in (select e.contentid
		// from t_exigencecontent e where e.type = 0)
		String sqlD = "exigence.ContentExigenceDAO.updateVcontentDate().del";
		
		
		// insert into v_cm_content select * from PPMS_V_CM_CONTENT s where s.contentid = ?
		String sql = "exigence.ContentExigenceDAO.updateVcontentDate().add";
		
		String[] sqlO = new String[contentIdList.size()+1];
		Object[][] sqlV = new Object[contentIdList.size()+1][1];
		
		sqlO[0] = sqlD;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i+1] = sql;
			sqlV[i+1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("紧急上下线前增量处理视图v_cm_content中数据信息时发生数据库异常。", e);
		}
		
		logger.info("updateVcontentDate() is end ...");
	}
	
	/**
	 * 紧急上下线前变更cm_ct_appgame拓展表中数据信息
	 * 
	 * @throws DAOException
	 */
	public void updateCMCTAPPDate(List<String> contentIdList) throws DAOException
	{
		logger.info("updateCMCTAPPDate() is starting ...");
		
		// delete from cm_ct_appgame s where s.contentid in (select e.contentid
		// from
		// t_exigencecontent e where e.type = 0)
		String sql_game_d = "exigence.ContentExigenceDAO.updateCMCTAPPDate().delGame";
		
		// insert into cm_ct_appgame select * from s_cm_ct_appgame s where
		// s.contentid  = ?
		String sql_game_a = "exigence.ContentExigenceDAO.updateCMCTAPPDate().addGame";
		
		String[] sqlO = new String[contentIdList.size() + 1];
		Object[][] sqlV = new Object[contentIdList.size() + 1][1];
		
		sqlO[0] = sql_game_d;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i + 1] = sql_game_a;
			sqlV[i + 1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("紧急上下线前增量处理cm_ct_appgame中数据信息时发生数据库异常。", e);
		}
		
		logger.info("cm_ct_appgame syn end ...");
		
		// delete from cm_ct_appsoftware s where s.contentid in (select
		// e.contentid from
		// t_exigencecontent e where e.type = 0)
		String sql_soft_d = "exigence.ContentExigenceDAO.updateCMCTAPPDate().delSoftware";
		
		// insert into cm_ct_appsoftware select * from s_cm_ct_appsoftware s
		// where
		// s.contentid  = ?
		String sql_soft_a = "exigence.ContentExigenceDAO.updateCMCTAPPDate().addSoftware";
		
		sqlO = new String[contentIdList.size() + 1];
		sqlV = new Object[contentIdList.size() + 1][1];
		
		sqlO[0] = sql_soft_d;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i + 1] = sql_soft_a;
			sqlV[i + 1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("紧急上下线前增量处理cm_ct_appsoftware中数据信息时发生数据库异常。", e);
		}
		
		logger.info("cm_ct_appsoftware syn end ...");
		
		// delete from cm_ct_apptheme s where s.contentid in (select e.contentid
		// from
		// t_exigencecontent e where e.type = 0)
		String sql_app_d = "exigence.ContentExigenceDAO.updateCMCTAPPDate().delTheme";
		
		// insert into cm_ct_apptheme select * from s_cm_ct_apptheme s where
		// s.contentid = ?
		String sql_app_a = "exigence.ContentExigenceDAO.updateCMCTAPPDate().addTheme";
		
		sqlO = new String[contentIdList.size() + 1];
		sqlV = new Object[contentIdList.size() + 1][1];
		
		sqlO[0] = sql_app_d;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i + 1] = sql_app_a;
			sqlV[i + 1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("紧急上下线前增量处理cm_ct_apptheme中数据信息时发生数据库异常。", e);
		}
		
		logger.info("cm_ct_apptheme syn end ...");
		
		logger.info("updateCMCTAPPDate() is end ...");
	}
}
