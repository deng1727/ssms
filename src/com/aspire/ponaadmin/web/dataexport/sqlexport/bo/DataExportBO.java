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
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(DataExportBO.class);
	
	private static DataExportBO instance = new DataExportBO();
	
	private DataExportBO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static DataExportBO getInstance()
	{
		return instance;
	}
	
	/**
	 * ��ѯ���������б�
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
			throw new BOException("��ѯ���������б�ʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * ɾ�����������б���Ϣ
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
			throw new BOException("ɾ�����������б���Ϣʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * �޸���������ִ��ʱ������һ��ִ�к�ʱ
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
			throw new BOException("ɾ�����������б���Ϣʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * ��ѯ���������б�
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
			throw new BOException("��ѯ���������б�ʱ�������ݿ��쳣��", e);
		}
		return vo;
	}
	
	/**
	 * ��ѯ���������б�
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
