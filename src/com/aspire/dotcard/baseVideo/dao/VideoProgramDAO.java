package com.aspire.dotcard.baseVideo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.baseVideo.bo.VideoProgramBO;
import com.aspire.dotcard.baseVideo.vo.ProgramVO;
import com.aspire.dotcard.baseread.vo.BookAuthorVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

public class VideoProgramDAO
{
	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(VideoProgramBO.class);
	
	private static VideoProgramDAO instance = new VideoProgramDAO();
	
	private VideoProgramDAO()
	{
	}
	
	public static VideoProgramDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 */
	private class VideoProgramPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			ProgramVO vo = (ProgramVO) content;
			vo.setProgramId(rs.getString("ProgramId"));
			vo.setVideoId(rs.getString("VideoId"));
			vo.setProgramName(rs.getString("ProgramName"));
			vo.setNodeId(rs.getString("NodeId"));
			vo.setNodeName(rs.getString("NodeName"));
			vo.setLastUpTime(rs.getString("LastUpTime"));
			vo.setShowTime(rs.getString("ShowTime"));
		}
		
		public Object createObject()
		{
			return new ProgramVO();
		}
	}
	
	/**
	 * 用于查询视频节目列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryVideoProgramList(PageResult page, ProgramVO vo)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("queryVideoProgramList(" + vo.getProgramId() + ", "
					+ vo.getProgramName() + ", " + vo.getVideoId() + ", "
					+ vo.getNodeId() + ") is starting ...");
		}
		
		// select
		// t.programid,t.programname,t.showtime,t.videoid,t.nodeid,n.nodename,
		// substr(t.lastuptime, 0, 8) lastuptime from t_vo_program
		// t, t_vo_node n where t.nodeid = n.nodeid
		String sqlCode = "video.VideoProgramDAO.queryVideoProgramList().SELECT";
		String sql = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			
			List paras = new ArrayList();
			
			// 构造搜索的sql和参数
			if (!"".equals(vo.getProgramId()))
			{
				sqlBuffer.append(" and t.ProgramId =? ");
				paras.add(vo.getProgramId());
			}
			if (!"".equals(vo.getProgramName()))
			{
				sqlBuffer.append(" and t.ProgramName like ? ");
				paras.add("%" + SQLUtil.escape(vo.getProgramName()) + "%");
			}
			if (!"".equals(vo.getVideoId()))
			{
				sqlBuffer.append(" and t.VideoId =? ");
				paras.add(vo.getVideoId());
			}
			if (!"".equals(vo.getNodeId()))
			{
				sqlBuffer.append(" and t.NodeId =? ");
				paras.add(vo.getNodeId());
			}
			
			sqlBuffer.append(" order by t.lastuptime desc");
			
			page.excute(sqlBuffer.toString(), paras.toArray(),
					new VideoProgramPageVO());
		}
		catch (DataAccessException e)
		{
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
	}
	
	private ProgramVO videoProgramVoData(ResultSet rs) throws SQLException
	{
		ProgramVO vo = new ProgramVO();
		vo.setProgramId(rs.getString("ProgramId"));
		vo.setVideoId(rs.getString("VideoId"));
		vo.setProgramName(rs.getString("ProgramName"));
		vo.setNodeId(rs.getString("NodeId"));
		vo.setNodeName(rs.getString("NodeName"));
		vo.setLastUpTime(rs.getString("LastUpTime"));
		vo.setShowTime(rs.getString("ShowTime"));
		
		return vo;
	}
	
	/**
	 * 获取当前条件下所有基地阅读作者内容信息。
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List queryListByExport(ProgramVO vo) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryListByExport(" + vo.getProgramId() + ", "
					+ vo.getProgramName() + ", " + vo.getVideoId() + ", "
					+ vo.getNodeId() + ") is starting ...");
		}
		
		// select
		// t.programid,t.programname,t.showtime,t.videoid,t.nodeid,n.nodename,
		// substr(t.lastuptime, 0, 8) lastuptime from t_vo_program
		// t, t_vo_node n where t.nodeid = n.nodeid
		String sqlCode = "video.VideoProgramDAO.queryVideoProgramList().SELECT";
		List list = new ArrayList();
		ResultSet rs = null;
		String sql;
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			
			List paras = new ArrayList();
			
			// 构造搜索的sql和参数
			if (!"".equals(vo.getProgramId()))
			{
				sqlBuffer.append(" and t.ProgramId =? ");
				paras.add(vo.getProgramId());
			}
			if (!"".equals(vo.getProgramName()))
			{
				sqlBuffer.append(" and t.ProgramName like ? ");
				paras.add("%" + SQLUtil.escape(vo.getProgramName()) + "%");
			}
			if (!"".equals(vo.getVideoId()))
			{
				sqlBuffer.append(" and t.VideoId =? ");
				paras.add(vo.getVideoId());
			}
			if (!"".equals(vo.getNodeId()))
			{
				sqlBuffer.append(" and t.NodeId =? ");
				paras.add(vo.getNodeId());
			}
			
			sqlBuffer.append(" order by t.lastuptime desc");
			
			rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
			
			while (rs.next())
			{
				list.add(videoProgramVoData(rs));
			}
		}
		catch (SQLException e)
		{
			logger.error("执行查询基地阅读指定作者内容信息失败", e);
			e.printStackTrace();
		}
		catch (DataAccessException e)
		{
			logger.error("执行查询基地阅读指定作者内容信息失败", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
}
