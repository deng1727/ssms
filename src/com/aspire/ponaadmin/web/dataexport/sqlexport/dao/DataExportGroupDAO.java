package com.aspire.ponaadmin.web.dataexport.sqlexport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportFTPVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportGroupVO;

public class DataExportGroupDAO
{	
	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(DataExportGroupDAO.class);
	
	private static DataExportGroupDAO instance = new DataExportGroupDAO();
	
	private DataExportGroupDAO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static DataExportGroupDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * ��װҳ����ʾVO
	 * 
	 * @param vo
	 * @param rs
	 * @throws SQLException
	 */
	private void formatDataExportGroupPage(DataExportGroupVO vo, ResultSet rs)
			throws SQLException
	{
		vo.setGroupId(rs.getString("groupid"));
		vo.setToMail(rs.getString("ToMail"));
		vo.setMailTitle(rs.getString("MailTitle"));
		vo.setStartTime(rs.getString("StartTime"));
		vo.setTimeType(rs.getString("TimeType"));
		vo.setTimeTypeCon(rs.getString("TimeTypeCon"));
		vo.setUrl(rs.getString("Url"));
		String ftpId = rs.getString("ftpid");
		String ftpIP = rs.getString("ftpIP");
		vo.setFtpId(ftpId);
		DataExportFTPVO ftpVO = new DataExportFTPVO();
		
		if(!"".equals(ftpIP))
		{
			ftpVO.setFtpId(ftpId);
			ftpVO.setFtpIp(ftpIP);
			ftpVO.setFtpPort(rs.getString("FtpPort"));
			ftpVO.setFtpName(rs.getString("FtpName"));
			ftpVO.setFtpPassWord(rs.getString("FtpKey"));
			ftpVO.setFtpPath(rs.getString("FtpPath"));
		}
		vo.setDataExportFtpVo(ftpVO);
	}
	

	/**
	 * ��ѯ��ǰ��������
	 * 
	 * @param groupId ����id
	 * @throws DAOException
	 */
	public DataExportGroupVO queryDataExportGroup(String groupId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportGroupDAO.queryDataExportGroup( " + groupId + " ) is start...");
		}
		
		// select * from t_r_exportsql_group g, t_r_exportsql_ftp f where g.ftpid = f.ftpid(+) 
		String sqlCode = "sqlexport.DataExportGroupDAO.queryDataExportGroupList().SELECT";
		DataExportGroupVO vo = new DataExportGroupVO();
		String sql = null;
		ResultSet rs = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			sqlBuffer.append(" and g.groupId='").append(groupId).append("' ");
			sqlBuffer.append(" order by g.groupId");
			
			rs = DB.getInstance().query(sqlBuffer.toString(), null);
			
			if (rs.next())
			{
				
				formatDataExportGroupPage(vo, rs);
			}
		}
		catch (DataAccessException e)
		{
			logger.error(e);
			throw new DAOException("��ѯ���������б�ʱ�������ݿ��쳣��", e);
		}
		catch (SQLException e)
		{
			logger.error(e);
			throw new DAOException("��ѯ���������б�ʱ�������ݿ��쳣��", e);
		}
		
		return vo;
	}
	
	/**
	 * ��ѯ���������б�
	 * 
	 * @throws DAOException
	 */
	public List<DataExportGroupVO> queryDataExportGroupList() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportGroupDAO.queryDataExportGroupList(  ) is start...");
		}
		
		// select * from t_r_exportsql_group g, t_r_exportsql_ftp f where g.ftpid = f.ftpid(+) 
		String sqlCode = "sqlexport.DataExportGroupDAO.queryDataExportGroupList().SELECT";
		List<DataExportGroupVO> list = new ArrayList<DataExportGroupVO>();
		String sql = null;
		ResultSet rs = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			sqlBuffer.append(" order by g.groupId");
			
			rs = DB.getInstance().query(sqlBuffer.toString(), null);
			
			while (rs.next())
			{
				DataExportGroupVO vo = new DataExportGroupVO();
				formatDataExportGroupPage(vo, rs);
				list.add(vo);
			}
		}
		catch (DataAccessException e)
		{
			logger.error(e);
			throw new DAOException("��ѯ���������б�ʱ�������ݿ��쳣��", e);
		}
		catch (SQLException e)
		{
			logger.error(e);
			throw new DAOException("��ѯ���������б�ʱ�������ݿ��쳣��", e);
		}
		
		return list;
	}
}
