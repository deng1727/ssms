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
	 * ��¼��־��ʵ������
	 */
	protected static JLogger logger = LoggerFactory.getLogger(TWGameDAO.class);
	
	/**
	 * singletonģʽ��ʵ��
	 */
	private static TWGameDAO instance = new TWGameDAO();
	
	/**
	 * ���췽������singletonģʽ����
	 */
	private TWGameDAO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static TWGameDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
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
	 * ���ڲ�ѯͼ����Ϸ�б�
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
			
			// ����������sql�Ͳ���
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
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		}
	}
	
	/**
	 * ��ȡ��ǰ����������ͼ����Ϸ������Ϣ��
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
			
			// ����������sql�Ͳ���
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
			logger.error("ִ�в�ѯ��ǰ����������ͼ����Ϸ������Ϣʧ��", e);
			throw new DAOException("ִ�в�ѯ��ǰ����������ͼ����Ϸ������Ϣʧ��", e);
		}
		catch (DataAccessException e)
		{
			logger.error("ִ�в�ѯ��ǰ����������ͼ����Ϸ������Ϣʧ��", e);
			throw new DAOException("ִ�в�ѯ��ǰ����������ͼ����Ϸ������Ϣʧ��", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	
	/**
	 * ���ڵõ����ݿ���ͼ����Ϸ��ȫ��id
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
			logger.error("���ڵõ����ݿ���ͼ����Ϸ��ȫ��idʱ��������", e);
			throw new DAOException("���ڵõ����ݿ���ͼ����Ϸ��ȫ��idʱ��������", e);
		}
		finally
		{
			DB.close(rs);
		}
		return set;
	}
	
	/**
	 * ���ڵõ����ݿ���ͼ����Ϸ����ס��ȫ��id
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
			logger.error("���ڵõ����ݿ���ͼ����Ϸ����ס��ȫ��idʱ��������", e);
			throw new DAOException("���ڵõ����ݿ���ͼ����Ϸ����ס��ȫ��idʱ��������", e);
		}
		finally
		{
			DB.close(rs);
		}
		return set;
	}
	
	/**
	 * ���ڸ��µ�ǰͼ����Ϸ������Ϣ
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
				logger.error("ִ�в�ѯ�����Ķ�ָ������������Ϣʧ��", e);
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
