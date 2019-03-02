package com.aspire.dotcard.basemusic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.basemusic.vo.BaseMusicVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

public class BaseMusicPicDAO
{
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseMusicPicDAO.class);
	
	/**
	 * singleton模式的实例
	 */
	private static BaseMusicPicDAO instance = new BaseMusicPicDAO();
	
	/**
	 * 构造方法，由singleton模式调用
	 */
	private BaseMusicPicDAO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static BaseMusicPicDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 */
	private class BaseMusicPicPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			BaseMusicVO vo = (BaseMusicVO) content;
			vo.setMusicId(rs.getString("musicid"));
			vo.setMusicName(rs.getString("songname"));
			vo.setSingerName(rs.getString("singer"));
			vo.setMusicPic(rs.getString("music_pic"));
			vo.setCreateTime(rs.getString("createtime"));
			vo.setValidity(rs.getString("validity"));
		}
		
		public Object createObject()
		{
			return new BaseMusicVO();
		}
	}
	
	private BaseMusicVO baseMusicVoData(ResultSet rs) throws SQLException
	{
		BaseMusicVO vo = new BaseMusicVO();
		vo.setMusicId(rs.getString("musicid"));
		vo.setMusicName(rs.getString("songname"));
		vo.setSingerName(rs.getString("singer"));
		vo.setMusicPic(rs.getString("music_pic"));
		vo.setCreateTime(rs.getString("createtime"));
		vo.setValidity(rs.getString("validity"));
		return vo;
	}
	
	/**
	 * 查询基地音乐信息列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryMusicList(PageResult page, BaseMusicVO vo)
			throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryMusicList(" + vo.getMusicId() + ", "
					+ vo.getMusicName() + ") is starting ...");
		}
		
		// select * from t_mb_music_new t where t.delflag=0
		String sqlCode = "dotcard.basemusic.dao.BaseMusicPicDAO.queryMusicList";
		String sql = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List<String> paras = new ArrayList<String>();
			
			// 构造搜索的sql和参数
			if (!"".equals(vo.getMusicId()))
			{
				sqlBuffer.append(" and t.musicid =? ");
				paras.add(vo.getMusicId());
			}
			if (!"".equals(vo.getMusicName()))
			{
				sqlBuffer.append(" and t.songname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getMusicName()) + "%");
			}
			if (!"".equals(vo.getSingerName()))
			{
				sqlBuffer.append(" and t.singer like ? ");
				paras.add("%" + SQLUtil.escape(vo.getSingerName()) + "%");
			}
			
			page.excute(sqlBuffer.toString(), paras.toArray(),
					new BaseMusicPicPageVO());
		}
		catch (DataAccessException e)
		{
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
	}
	
	/**
	 * 获取当前条件下所有查询基地音乐信息。
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<BaseMusicVO> queryMusicListByExport(BaseMusicVO vo)
			throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryMusicListByExport(" + vo.getMusicId() + ", "
					+ vo.getMusicName() + ") is starting ...");
		}
		
		// select * from t_mb_music_new t where t.delflag=0
		String sqlCode = "dotcard.basemusic.dao.BaseMusicPicDAO.queryMusicList";
		List<BaseMusicVO> list = new ArrayList<BaseMusicVO>();
		ResultSet rs = null;
		String sql;
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List<String> paras = new ArrayList<String>();
			
			// 构造搜索的sql和参数
			if (!"".equals(vo.getMusicId()))
			{
				sqlBuffer.append(" and t.musicid =? ");
				paras.add(vo.getMusicId());
			}
			if (!"".equals(vo.getMusicName()))
			{
				sqlBuffer.append(" and t.songname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getMusicName()) + "%");
			}
			if (!"".equals(vo.getSingerName()))
			{
				sqlBuffer.append(" and t.singer like ? ");
				paras.add("%" + SQLUtil.escape(vo.getSingerName()) + "%");
			}
			
			rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
			
			while (rs.next())
			{
				list.add(baseMusicVoData(rs));
			}
		}
		catch (SQLException e)
		{
			logger.error("执行获取当前条件下所有查询基地音乐信息信息失败", e);
			e.printStackTrace();
		}
		catch (DataAccessException e)
		{
			logger.error("执行获取当前条件下所有查询基地音乐信息失败", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	
	/**
	 * 用于更新当前库中作者信息
	 * 
	 * @param list
	 * @param temp
	 * @throws DAOException
	 */
	public void updateBaseMusicPic(List<BaseMusicVO> list, String[] temp)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateBaseMusicPic() is starting ...");
		}
		
		// update t_mb_music_new m set m.music_pic=? where m.musicid = ?
		String sqlCode = "dotcard.basemusic.dao.BaseMusicPicDAO.updateBaseMusicPic";
		int numUpdate = 0;
		StringBuffer errorId = new StringBuffer();
		
		for (Iterator<BaseMusicVO> iterator = list.iterator(); iterator
				.hasNext();)
		{
			BaseMusicVO vo = (BaseMusicVO) iterator.next();
			
			int exe = DB.getInstance().executeBySQLCode(sqlCode,
					new Object[] { vo.getMusicPic(), vo.getMusicId() });
			
			if (exe > 0)
			{
				numUpdate++;
			}
			else
			{
				errorId.append(vo.getMusicId()).append(".");
			}
		}
		
		temp[0] = String.valueOf(numUpdate);
		temp[1] = errorId.toString();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("updateBaseMusicPic() is end ...");
		}
	}
}
