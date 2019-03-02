package com.aspire.ponaadmin.web.datasync.implement.htc;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class DataHTCDownloadDAO
{
	
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(DataHTCDownloadDAO.class);
	
	private static DataHTCDownloadDAO dao = new DataHTCDownloadDAO();
	
	public static DataHTCDownloadDAO getInstance()
	{
		
		return dao;
	}
	
	private DataHTCDownloadDAO()
	{}
	
	/**
	 * ���ڷ������ݱ�������ΪHTC����������contentid����
	 * 
	 * @return
	 * @throws DAOException
	 */
	public Set<String> queryContentByHTC() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("queryContentByHTC");
		}
		
		// select g.contentid from t_r_gcontent g where g.subtype = '16'
		String sqlCode = "datasync.implement.htc.DataHTCDownloadDAO.queryContentByHTC";
		Set<String> set = new HashSet<String>();
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			
			while (rs.next())
			{
				set.add(rs.getString("contentid"));
			}
		}
		catch (Exception e)
		{
			logger.error("��ѯ��������ΪHTC������ʱ�������� ��", e);
			throw new DAOException(e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return set;
	}
	
	/**
	 * ���ڷ��ص�ǰ���ݿ��д��ڵ�HTC�������ݼ���
	 * 
	 * @return
	 * @throws DAOException
	 */
	public Set<String> queryHTCDownload() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("queryContentByHTC");
		}
		
		// select g.contentid from T_R_HTCDOWNLOAD g
		String sqlCode = "datasync.implement.htc.DataHTCDownloadDAO.queryHTCDownload";
		Set<String> set = new HashSet<String>();
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			
			while (rs.next())
			{
				set.add(rs.getString("contentid"));
			}
		}
		catch (Exception e)
		{
			logger.error("��ѯHTC����������ʱ�������� ��", e);
			throw new DAOException(e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return set;
	}
	
	/**
	 * ��������HTC����������
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public int addHTCDownloadData(DataHTCDownloadVO vo) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("addHTCDownloadData(),contentid=" + vo.getMmContentId()
					+ ",apCode=" + vo.getApCode()+ ",appId=" + vo.getAppId());
		}
		Object paras[] = {vo.getApCode(),vo.getAppId(),vo.getMmContentId(),vo.getDownCount() };
		// insert into T_R_HTCDOWNLOAD (apCode, appId, contentid, downCount) values (?,?,?,?)
		String sqlCode = "datasync.implement.htc.DataHTCDownloadDAO.addHTCDownloadData";
		return DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * ���ڸ���HTC����������
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public int updateHTCDownloadData(DataHTCDownloadVO vo) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateHTCDownloadData(),contentid=" + vo.getMmContentId()
					+ ",apCode=" + vo.getApCode()+ ",appId=" + vo.getAppId());
		}
		Object paras[] = {vo.getDownCount(),vo.getMmContentId(), vo.getApCode(),vo.getAppId()};
		// update T_R_HTCDOWNLOAD h set h.downCount=? where h.contentid=? and apCode=? and appId=?
		String sqlCode = "datasync.implement.htc.DataHTCDownloadDAO.updateHTCDownloadData";
		return DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
}
