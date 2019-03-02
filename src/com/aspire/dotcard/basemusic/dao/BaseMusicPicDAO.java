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
	 * ��¼��־��ʵ������
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseMusicPicDAO.class);
	
	/**
	 * singletonģʽ��ʵ��
	 */
	private static BaseMusicPicDAO instance = new BaseMusicPicDAO();
	
	/**
	 * ���췽������singletonģʽ����
	 */
	private BaseMusicPicDAO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static BaseMusicPicDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
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
	 * ��ѯ����������Ϣ�б�
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
			
			// ����������sql�Ͳ���
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
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		}
	}
	
	/**
	 * ��ȡ��ǰ���������в�ѯ����������Ϣ��
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
			
			// ����������sql�Ͳ���
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
			logger.error("ִ�л�ȡ��ǰ���������в�ѯ����������Ϣ��Ϣʧ��", e);
			e.printStackTrace();
		}
		catch (DataAccessException e)
		{
			logger.error("ִ�л�ȡ��ǰ���������в�ѯ����������Ϣʧ��", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	
	/**
	 * ���ڸ��µ�ǰ����������Ϣ
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
