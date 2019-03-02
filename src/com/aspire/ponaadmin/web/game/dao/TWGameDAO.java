package com.aspire.ponaadmin.web.game.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.game.vo.TWGameVO;

public class TWGameDAO
{
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger logger = LoggerFactory.getLogger(TWGameDAO.class);
	
	/**
	 * singleton模式的实例
	 */
	private static TWGameDAO instance = new TWGameDAO();
	
	/**
	 * 构造方法，由singleton模式调用
	 */
	private TWGameDAO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static TWGameDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 */
	private class TWGamePageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			TWGameVO vo = (TWGameVO) content;
			vo.setGameId(rs.getString("cpserviceid"));
			vo.setGameName(rs.getString("servicename"));
			vo.setGameDesc(rs.getString("servicedesc"));
			vo.setCPName(rs.getString("cpname"));
			vo.setOldPrice(rs.getInt("oldprice"));
			vo.setFee(rs.getInt("fee"));
			vo.setSortId(rs.getString("sortid"));
		}
		
		public Object createObject()
		{
			return new TWGameVO();
		}
	}
	
	private TWGameVO pageTWGameData(ResultSet rs) throws SQLException
	{
		TWGameVO vo = new TWGameVO();
		vo.setGameId(rs.getString("cpserviceid"));
		vo.setGameName(rs.getString("servicename"));
		vo.setGameDesc(rs.getString("servicedesc"));
		vo.setCPName(rs.getString("cpname"));
		vo.setOldPrice(rs.getInt("oldprice"));
		vo.setFee(rs.getInt("fee"));
		vo.setSortId(rs.getString("sortid"));
		
		return vo;
	}
	
	/**
	 * 用于查询图文游戏列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryTWGameList(PageResult page, TWGameVO vo)
			throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryTWGameList(" + vo.getGameId() + ", "
					+ vo.getGameName() + ") is starting ...");
		}
		
		// select
		// t.cpserviceid,t.servicename,t.servicedesc,t.cpname,t.oldprice,t.fee,s.sortid
		// from t_game_tw_new t, T_GAME_TW_SORT s where t.cpserviceid =
		// s.gameid(+)
		String sqlCode = "twGame.TWGameDAO.queryTWGameList().SELECT";
		String sql = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List<String> paras = new ArrayList<String>();
			
			// 构造搜索的sql和参数
			if (!"".equals(vo.getGameId()))
			{
				sqlBuffer.append(" and t.cpserviceid =? ");
				paras.add(vo.getGameId());
			}
			if (!"".equals(vo.getGameName()))
			{
				sqlBuffer.append(" and t.servicename like ? ");
				paras.add("%" + SQLUtil.escape(vo.getGameName()) + "%");
			}
			
			sqlBuffer.append(" order by s.sortid desc nulls last");
			
			page.excute(sqlBuffer.toString(), paras.toArray(),
					new TWGamePageVO());
		}
		catch (DataAccessException e)
		{
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
	}
	
	/**
	 * 获取当前条件下所有图文游戏内容信息。
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<TWGameVO> queryListByExport(String gameId, String gameName)
			throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryListByExport(" + gameId + ", " + gameName
					+ ") is starting ...");
		}
		
		// select
		// t.cpserviceid,t.servicename,t.servicedesc,t.cpname,t.oldprice,t.fee,s.sortid
		// from t_game_tw_new t, T_GAME_TW_SORT s where t.cpserviceid =
		// s.gameid(+)
		String sqlCode = "twGame.TWGameDAO.queryTWGameList().SELECT";
		List<TWGameVO> list = new ArrayList<TWGameVO>();
		ResultSet rs = null;
		String sql;
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List<String> paras = new ArrayList<String>();
			
			// 构造搜索的sql和参数
			if (!"".equals(gameId))
			{
				sqlBuffer.append(" and t.cpserviceid =? ");
				paras.add(gameId);
			}
			if (!"".equals(gameName))
			{
				sqlBuffer.append(" and t.servicename like ? ");
				paras.add("%" + SQLUtil.escape(gameName) + "%");
			}
			
			sqlBuffer.append(" order by s.sortid desc nulls last");
			
			rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
			
			while (rs.next())
			{
				list.add(pageTWGameData(rs));
			}
		}
		catch (SQLException e)
		{
			logger.error("执行查询当前条件下所有图文游戏内容信息失败", e);
			throw new DAOException("执行查询当前条件下所有图文游戏内容信息失败", e);
		}
		catch (DataAccessException e)
		{
			logger.error("执行查询当前条件下所有图文游戏内容信息失败", e);
			throw new DAOException("执行查询当前条件下所有图文游戏内容信息失败", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	
	/**
	 * 用于得到数据库中图文游戏的全量id
	 * 
	 * @return
	 * @throws DAOException
	 */
	public Set<String> getTWIdKeySet() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getTWIdKeySet() is starting ...");
		}
		// select t.cpserviceid from t_game_tw_new t
		String sqlCode = "twGame.TWGameDAO.getTWIdKeySet().SELECT";
		Set<String> set = new HashSet<String>();
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			
			while (rs.next())
			{
				set.add(rs.getString("cpserviceid"));
			}
		}
		catch (SQLException e)
		{
			logger.error("用于得到数据库中图文游戏的全量id时发生错误", e);
			throw new DAOException("用于得到数据库中图文游戏的全量id时发生错误。", e);
		}
		finally
		{
			DB.close(rs);
		}
		return set;
	}
	
	/**
	 * 用于得到数据库中图文游戏排序住处全量id
	 * 
	 * @return
	 * @throws DAOException
	 */
	public Set<String> getSortIdKeySet() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getTWIdKeySet() is starting ...");
		}
		// select t.gameid from T_GAME_TW_SORT t
		String sqlCode = "twGame.TWGameDAO.getSortIdKeySet().SELECT";
		Set<String> set = new HashSet<String>();
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			
			while (rs.next())
			{
				set.add(rs.getString("gameId"));
			}
		}
		catch (SQLException e)
		{
			logger.error("用于得到数据库中图文游戏排序住处全量id时发生错误", e);
			throw new DAOException("用于得到数据库中图文游戏排序住处全量id时发生错误。", e);
		}
		finally
		{
			DB.close(rs);
		}
		return set;
	}
	
	/**
	 * 用于更新当前图文游戏排序信息
	 * 
	 * @param list
	 * @param temp
	 * @throws DAOException
	 */
	public void updateTWGameSortId(List<TWGameVO> list, String[] temp)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateTWGameSortId() is starting ...");
		}
		
		// update T_GAME_TW_SORT s set s.sortid=? where s.gameid=?
		String sqlCode_U = "twGame.TWGameDAO.updateTWGameSortId().update";
		// insert into T_GAME_TW_SORT(sortid, gameid) values (?,?)
		String sqlCode_A = "twGame.TWGameDAO.updateTWGameSortId().add";
		
		int numUpdate = 0;
		StringBuffer errorId = new StringBuffer();
		
		for (Iterator<TWGameVO> iterator = list.iterator(); iterator.hasNext();)
		{
			TWGameVO vo = iterator.next();
			
			int exe = 0;
			
			try
			{
				if ("U".equals(vo.getDbType()))
				{
					exe = DB.getInstance()
							.executeBySQLCode(
									sqlCode_U,
									new Object[] { vo.getSortId(),
											vo.getGameId() });
				}
				else if ("A".equals(vo.getDbType()))
				{
					exe = DB.getInstance()
							.executeBySQLCode(
									sqlCode_A,
									new Object[] { vo.getSortId(),
											vo.getGameId() });
				}
			}
			catch (DAOException e)
			{
				logger.error("执行查询基地阅读指定作者内容信息失败", e);
			}
			
			if (exe > 0)
			{
				numUpdate++;
			}
			else
			{
				errorId.append(vo.getGameId()).append("</br>");
			}
		}
		
		temp[0] = String.valueOf(numUpdate);
		temp[1] = errorId.toString();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryListByExport() is end ...");
		}
	}
}
