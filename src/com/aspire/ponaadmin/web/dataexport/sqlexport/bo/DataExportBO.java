package com.aspire.ponaadmin.web.dataexport.sqlexport.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.dao.DataExportDAO;

public class DataExportBO
{
	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(DataExportBO.class);
	
	private static DataExportBO instance = new DataExportBO();
	
	private DataExportBO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static DataExportBO getInstance()
	{
		return instance;
	}
	
	/**
	 * 查询导出任务列表
	 * 
	 * @param page
	 * @param exportByUser
	 * @throws BOException
	 */
	public void queryDataExportList(PageResult page , String exportByUser)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.queryDataExportList( ) is start...");
		}
		
		try
		{
			DataExportDAO.getInstance().queryDataExportList(page, exportByUser);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询导出任务列表时发生数据库异常！", e);
		}
	}
	
	/**
	 * 删除导出任务列表信息
	 * 
	 * @param id
	 * @throws BOException
	 */
	public void delDataExport(String id) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.delDataExport( ) is start...");
		}
		
		try
		{
			DataExportDAO.getInstance().delDataExport(id);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("删除导出任务列表信息时发生数据库异常！", e);
		}
	}
	
	/**
	 * 修改任务的最后执行时间和最后一次执行耗时
	 * 
	 * @param vo
	 * @throws BOException
	 */
	public void editDataExport(DataExportVO vo) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.editDataExport( ) is start...");
		}
		
		try
		{
			DataExportDAO.getInstance().editDataExport(vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("删除导出任务列表信息时发生数据库异常！", e);
		}
	}
	
	/**
	 * 查询导出任务列表
	 * 
	 * @param id
	 * @throws BOException
	 */
	public DataExportVO queryDataExportVO(String id) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.queryDataExportList( ) is start...");
		}
		
		DataExportVO vo;
		
		try
		{
			vo = DataExportDAO.getInstance().queryDataExportVO(id);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询导出任务列表时发生数据库异常！", e);
		}
		return vo;
	}
	
	/**
	 * 查询导出任务列表
	 * 
	 * @param exportSql
	 * @throws BOException
	 */
	public boolean hasSqlTrue(String exportSql, String exportLine)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataExportBO.hasSqlTrue( ) is start...");
		}
		
		return DataExportDAO.getInstance().hasSqlTrue(exportSql, exportLine);
	}
}
