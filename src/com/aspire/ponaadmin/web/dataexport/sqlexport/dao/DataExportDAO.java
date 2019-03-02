package com.aspire.ponaadmin.web.dataexport.sqlexport.dao;



import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;

public class DataExportDAO
{
	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(DataExportDAO.class);
	
	private static DataExportDAO instance = new DataExportDAO();
	
	private DataExportDAO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static DataExportDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 */
	private class DataExportPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			DataExportVO vo = (DataExportVO) content;
			
			vo.setId(String.valueOf(rs.getInt("id")));
			vo.setExportName(rs.getString("exportName"));
			//vo.setExportSql(getClobString(rs.getClob("exportSql")));
			vo.setExportType(rs.getString("exportType"));
			vo.setExportTypeOther(rs.getString("exportTypeOther"));
			vo.setExportPageNum(String.valueOf(rs.getInt("exportPageNum")));
			vo.setExportLine(String.valueOf(rs.getInt("exportLine")));
			vo.setLastTime(rs.getString("lastTime"));
			vo.setFileName(rs.getString("fileName"));
			vo.setFilePath(rs.getString("filePath"));
			vo.setEncoder(rs.getString("encoder"));
			vo.setExportByAuto(rs.getString("exportByAuto"));
			vo.setExportByUser(rs.getString("exportByUser"));
			vo.setGroupId(rs.getString("groupid"));
		}
		
		public Object createObject()
		{
			return new DataExportVO();
		}
	}
	
	/**
	 * 封装页面显示VO
	 * 
	 * @param vo
	 * @param rs
	 * @throws SQLException
	 */
	private void formatDataExportPage(DataExportVO vo, ResultSet rs)
			throws SQLException
	{
		vo.setId(String.valueOf(rs.getInt("id")));
		vo.setExportName(rs.getString("exportName"));
		Clob clobSql  = rs.getClob("exportSql") ;
		vo.setExportSql(getClobString(clobSql));
		vo.setExportType(rs.getString("exportType"));
		vo.setExportTypeOther(rs.getString("exportTypeOther"));
		vo.setExportPageNum(String.valueOf(rs.getInt("exportPageNum")));
		vo.setExportLine(String.valueOf(rs.getInt("exportLine")));
		vo.setLastTime(rs.getString("lastTime"));
		vo.setFileName(rs.getString("fileName"));
		vo.setFilePath(rs.getString("filePath"));
		vo.setEncoder(rs.getString("encoder"));
		vo.setExportByAuto(rs.getString("exportByAuto"));
		vo.setExportByUser(rs.getString("exportByUser"));
		vo.setGroupId(rs.getString("groupid"));
		vo.setIsVerf(rs.getString("isVerf"));
	}
	
	/**
	 * 查询导出任务列表
	 * 
	 * @param page
	 * @param exportByUser
	 * @throws DAOException
	 */
	public void queryDataExportList(PageResult page, String exportByUser)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.queryDataExportList( ) is start...");
		}
		
		// select * from t_r_exportsql t where 1=1
		String sqlCode = "sqlexport.DataExportDAO.queryDataExportList().SELECT";
		String sql = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List<String> paras = new ArrayList<String>();
			
			sqlBuffer.append(" and t.exportByUser = ? ");
			paras.add(exportByUser);
			
			sqlBuffer.append(" order by t.id");
			
			page.excute(sqlBuffer.toString(), paras.toArray(),
					new DataExportPageVO());
		}
		catch (DataAccessException e)
		{
			logger.error(e);
			throw new DAOException("查询导出任务列表时发生数据库异常！", e);
		}
	}
	
	/**
	 * 查询当前分组下用于自动任务的导出任务列表
	 * 
	 * @param groupId 分组id
	 * @throws DAOException
	 */
	public List<DataExportVO> queryDataExportList(String groupId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.queryDataExportList( " + groupId + " ) is start...");
		}
		
		// select * from t_r_exportsql t where 1=1
		String sqlCode = "sqlexport.DataExportDAO.queryDataExportList().SELECT";
		List<DataExportVO> list = new ArrayList<DataExportVO>();
		String sql = null;
		ResultSet rs = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			sqlBuffer.append(" and t.groupId = '").append(groupId).append("' ");
			sqlBuffer.append(" and t.exportByAuto = '2' ");
			sqlBuffer.append(" order by t.id");
			
			rs = DB.getInstance().query(sqlBuffer.toString(), null);
			
			while (rs.next())
			{
				DataExportVO vo = new DataExportVO();
				formatDataExportPage(vo, rs);
				list.add(vo);
			}
		}
		catch (DataAccessException e)
		{
			logger.error(e);
			throw new DAOException("查询导出任务列表时发生数据库异常！", e);
		}
		catch (SQLException e)
		{
			logger.error(e);
			throw new DAOException("查询导出任务列表时发生数据库异常！", e);
		}
		
		return list;
	}
	
	/**
	 * 删除导出任务列表信息
	 * 
	 * @param id
	 * @throws DAOException
	 */
	public void delDataExport(String id) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.delDataExport( ) is start...");
		}
		
		// delete from t_r_exportsql t where t.id = ?
		String sql = "sqlexport.DataExportDAO.delDataExport().remove";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, new Object[] { id });
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new DAOException("删除导出任务列表信息时发生数据库异常！", e);
		}
	}
	
	/**
	 * 修改任务的最后执行时间和最后一次执行耗时
	 * 
	 * @param vo
	 * @throws DAOException
	 */
	public void editDataExport(DataExportVO vo) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.editDataExport( ) is start...");
		}
		
		// update t_r_exportsql t set t.lasttime=to_date(?,'yyyy-mm-dd
		// hh24:mi'),t.exectime=? where t.id=?
		String sql = "sqlexport.DataExportDAO.editDataExport().update";
		
		try
		{
			DB.getInstance().executeBySQLCode(
					sql,
					new Object[] { vo.getLastTime(), vo.getExecTime(),
							vo.getId() });
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new DAOException("修改任务的最后执行时间和最后一次执行耗时时发生数据库异常！", e);
		}
	}
	
	/**
	 * 查询导出任务列表
	 * 
	 * @param id
	 * @throws DAOException
	 */
	public DataExportVO queryDataExportVO(String id) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.queryDataExportList( ) is start...");
		}
		
		DataExportVO vo = new DataExportVO();
		
		// select * from t_r_exportsql t where 1=1 and t.id = ?
		String sqlCode = "sqlexport.DataExportDAO.queryDataExportVO().SELECT";
		ResultSet rs = null;
		Object paras[] = { id };
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			
			if (rs.next())
			{
				formatDataExportPage(vo, rs);
			}
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new DAOException("查询导出任务列表时发生数据库异常！", e);
		}
		finally
		{
			DB.close(rs);
		}
		return vo;
	}
	
	/**
	 * 查询导出任务列表
	 * 
	 * @param exportSql
	 * @param exportLine
	 * @throws DAOException
	 */
	public boolean hasSqlTrue(String exportSql, String exportLine)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.hasSqlTrue( ) is start...");
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select * from ( ");
		sb.append(exportSql);
		sb.append(" ) where 1=2");
		
		RowSet rs = null;
		
		try
		{
			rs = DB.getInstance().query(sb.toString(), null);
			int columnCount = rs.getMetaData().getColumnCount();
			
			// 如果用户给定值大于当前sql查询出结果列数
			if (Integer.valueOf(exportLine) > columnCount)
			{
				return false;
			}
			else
			{
				return true;
			}
			
		}
		catch (Exception e)
		{
			logger.error(e);
			return false;
		}
		finally
		{
			DB.close(rs);
		}
	}
	
	/**
	 * 用于返回当前任务SQL语句能够查询出的结果总数
	 * 
	 * @param exportSql
	 *            任务sql语句
	 * @return 此sql语句执行返回的结果总数
	 * @throws DAOException
	 */
	public int getDataSize(String exportSql) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.getDataSize( " + exportSql
					+ " ) is start...");
		}
		
		StringBuffer sb = new StringBuffer();
		RowSet rs = null;
		int size = 0;
		
		sb.append("select count(1) countNum from ( ");
		sb.append(exportSql);
		sb.append(" )");
		
		try
		{
			rs = DB.getInstance().query(sb.toString(), null);
			
			if (rs.next())
			{
				size = rs.getInt("countNum");
			}
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new DAOException("查询导出任务列表时发生数据库异常！", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return size;
	}
	
	/**
	 * 根据导出任务SQL语句得到导出数据列表
	 * 
	 * @param exportSql
	 *            任务sql语句
	 * @param exportLine
	 *            任务sql语句获取列数
	 * @throws DAOException
	 * @throws DAOException
	 */
	public List<String[]> getDataByExportSql(String exportSql, String exportLine)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.getDataByExportSql( ) is start...");
		}
		
		List<String[]> dataList = new ArrayList<String[]>();
		int columnCount = Integer.parseInt(exportLine);
		RowSet rs = null;
		
		try
		{
			rs = DB.getInstance().query(exportSql, null);
			
			while (rs.next())
			{
				String[] temp = new String[columnCount];
				
				for (int i = 0; i < columnCount; i++)
				{
					String tempType = rs.getMetaData().getColumnTypeName(i + 1);
					if (tempType.equals("CLOB"))
					{
						try
						{
							temp[i] = getClobString(rs.getClob(i + 1));
						}
						catch (Exception e)
						{
							temp[i] = "";
							e.printStackTrace();
						}
					}
					else if (tempType.equals("DATE"))
					{
						temp[i] = getDateString(rs.getDate(i + 1));
					}
					else
					{
						temp[i] = String.valueOf(rs.getObject(i + 1));
					}
					
					if (temp[i].equals("null"))
					{
						temp[i] = "";
					}
				}
				
				dataList.add(temp);
			}
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new DAOException("查询导出任务列表时发生数据库异常！", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return dataList;
	}
	
	/**
	 * 根据导出任务SQL语句得到导出分页数据列表
	 * 
	 * @param exportSql
	 *            任务sql语句
	 * @param exportLine
	 *            任务sql语句获取列数
	 * @throws DAOException
	 * @throws DAOException
	 */
	public List<String[]> getPagingDataByExportSql(String exportSql,
			String exportLine, int start, int end) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger
					.debug("DataExportBO.getPagingDataByExportSql( ) is start...");
		}
		
		List<String[]> dataList = new ArrayList<String[]>();
		int columnCount = Integer.parseInt(exportLine);
		StringBuffer sql = new StringBuffer();
		RowSet rs = null;
		
		sql.append("SELECT * FROM (SELECT aa.*, ROWNUM RN FROM (");
		sql.append(exportSql);
		sql.append(") aa WHERE ROWNUM <= ?) WHERE RN > ?");
		
		try
		{
			rs = DB.getInstance().query(sql.toString(),
					new Object[] { end, start });
			
			while (rs.next())
			{
				String[] temp = new String[columnCount];
				
				for (int i = 0; i < columnCount; i++)
				{
					this.getDataByRs(temp, rs, i);
				}
				
				dataList.add(temp);
			}
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new DAOException("查询导出任务列表时发生数据库异常！", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return dataList;
	}
	
	/**
	 * 用于得到数据库返回的数据
	 * 
	 * @param temp
	 * @param rs
	 * @param i
	 * @throws SQLException
	 */
	private void getDataByRs(String[] temp, RowSet rs, int i)
			throws SQLException
	{
		String tempType = rs.getMetaData().getColumnTypeName(i + 1);
		
		if (tempType.equals("CLOB"))
		{
			try
			{
				temp[i] = getClobString(rs.getClob(i + 1));
			}
			catch (Exception e)
			{
				temp[i] = "";
//				logger.error(e);
//				e.printStackTrace();
			}
		}
		else if (tempType.equals("DATE"))
		{
			temp[i] = getDateString(rs.getDate(i + 1));
		}
		else
		{
			temp[i] = String.valueOf(rs.getObject(i + 1));
		}
		
		if (temp[i].equals("null"))
		{
			temp[i] = "";
		}
		
		temp[i] = getReplaceString(temp[i]);
	}
	
	/**
	 * 用于替换当前数据中的指定特殊字符
	 * 
	 * @param fromString
	 *            把什么
	 *            
	 * @return 替换成什么
	 */
	private String getReplaceString(String fromString)
	{
		String toString = fromString.replaceAll("", "");
		return toString;
	}
	
	/**
	 * 返回字符型結果
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	private String getDateString(Date date)
	{
		if (date == null)
		{
			return "";
		}
		else
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return simpleDateFormat.format(date);
		}
	}
	
	/**
	 * 返回字符型結果
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	private String getClobString(Clob clob) throws SQLException
	{
		if (clob == null)
		{
			return "";
		}
		long len = clob.length();
		return clob.getSubString(0, (int) len);
	}
	
	
}
