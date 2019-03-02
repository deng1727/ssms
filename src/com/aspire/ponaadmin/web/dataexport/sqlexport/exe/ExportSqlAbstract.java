package com.aspire.ponaadmin.web.dataexport.sqlexport.exe;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportGroupVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.SqlKey;
import com.aspire.ponaadmin.web.dataexport.sqlexport.bo.DataExportBO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.dao.DataExportDAO;
import com.enterprisedt.net.ftp.FTPClient;

public abstract class ExportSqlAbstract implements ExportSqlInterface
{
	/**
	 * 日志引用
	 */
	protected static JLogger Log = null;
	
	/**
	 * 导出任务信息
	 */
	protected DataExportVO vo = null;
	
	/**
	 * 导入行数变量
	 */
	protected int rowNum = 0;
	
	public ExportSqlAbstract(DataExportVO vo)
	{
		Log = LoggerFactory.getLogger(this.getClass());
		this.vo = vo;
	}
	
	/**
	 * 用于返回当前任务执行的sql语句能够得到的结果总数
	 * 
	 * @return
	 * @throws DAOException
	 */
	private int getDataSize() throws BOException
	{
		Log.info("统一生成文件模块，当前执行sql语句为:" + vo.getExportSql() + ". 导出列为："
				+ vo.getExportLine());
		
		// 此sql语句与导出项有误。请查证
		if (!DataExportDAO.getInstance().hasSqlTrue(vo.getExportSql(),
				vo.getExportLine()))
		{
			Log.error("此sql语句与导出项有误。请查证!");
			throw new BOException("此sql语句与导出项有误。请查证！");
		}
		
		Log.info("统一生成文件模块，获取当前执行sql语句获得的结果总数.");
		
		try
		{
			return DataExportDAO.getInstance().getDataSize(vo.getExportSql());
		}
		catch (DAOException e)
		{
			Log.error("执行任务sql时发生错语，请查证!", e);
			throw new BOException("此sql语句与导出项有误。请查证！",e);
		}
	}
	
	/**
	 * 用于创建目录
	 */
	private void createFilePath()
	{
		File file = new File(vo.getFileAllName());
		
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
	}
	
	/**
	 * 得到任务SQL全量数据
	 * 
	 * @return
	 */
	private List<String[]> queryDBforList()
	{
		Log.info("得到任务SQL全量数据，当前执行sql语句为:" + vo.getExportSql() + ". 导出列为："
				+ vo.getExportLine());
		
		try
		{
			return DataExportDAO.getInstance().getDataByExportSql(
					vo.getExportSql(), vo.getExportLine());
		}
		catch (DAOException e)
		{
			Log.error("执行任务sql时发生错语，请查证!", e);
			return null;
		}
	}
	
	/**
	 * 得到任务SQL分页数据
	 * 
	 * @param start
	 *            起始数
	 * @param end
	 *            结尾数
	 * @return
	 */
	private List<String[]> queryDBforPagingList(int start, int end)
	{
		Log.info("得到任务SQL分页数据，当前执行sql语句为:" + vo.getExportSql() + ". 导出列为："
				+ vo.getExportLine() + ". 起始条数为：" + start + "结束条数为：" + end);
		
		try
		{
			return DataExportDAO.getInstance().getPagingDataByExportSql(
					vo.getExportSql(), vo.getExportLine(), start, end);
		}
		catch (DAOException e)
		{
			Log.error("执行任务sql时发生错语，请查证!", e);
			return null;
		}
	}
	
	/**
	 * 分页执行数据导出
	 * 
	 * @param dataSize
	 * @param pageContentNum
	 * @throws BOException
	 */
	private void pagingDataExport(int dataSize, int pageContentNum)
			throws BOException
	{
		List<String[]> dataList = null;
		// 得到总页数
		int pageNum = (dataSize + pageContentNum - 1) / pageContentNum;
		int start = 0;
		int end = 0;
		
		Log.info("当前搜索导出分页数据每页为:" + pageContentNum + " 共分" + pageNum + "页查询.");
		
		for (int i = 0; i < pageNum; i++)
		{
			start = i * pageContentNum;
			end = (i + 1) * pageContentNum;
			
			Log.info("此次查询搜索导出分页数据为>第:" + start + "条<=第" + end + "条.");
			
			dataList = queryDBforPagingList(start, end);
			
			try
			{
				writerFile(dataList);
			}
			catch (BOException e)
			{
				Log.error("生成文件时发错，此次任务失败！", e);
				//throw new BOException("生成文件时发错，此次任务失败！", e);
			}
			if(dataList!=null)
				if(dataList!=null) dataList.clear();
		}
	}
	
	/**
	 * 用于生成文件
	 */
	public String createFile(DataExportGroupVO groupVO)
	{
		Log.info("统一生成文件模块启动，当前任务名为" + vo.getExportName());
		if (!SqlKey.isKey(vo.getExportSql().trim()))
		{
			Log.error("当前执行的sql语句有问题，只能为select语句! 当前语句为:" + vo.getExportSql());
			return "当前执行的sql语句有问题，只能为select语句! 当前语句为:" + vo.getExportSql();
		}
		int pageContentNum = Integer.parseInt(vo.getExportPageNum());
		long starttime = System.currentTimeMillis();
		Date date = new Date();
		int dataSize = 0;
		try
		{
			// 得到数据总数。        校验文件需要的数据
			dataSize = getDataSize();
		}
		catch (BOException e1)
		{
			Log.error("执行任务sql时发生错语，请查证!", e1);
			return "执行任务sql时发生错语，请查证!";
		}
		
		// 如果为空
		if (dataSize == 0)
		{
			Log.info("查询数据库时得到数据为空。此次任务失败！");
			return "查询数据库时得到数据为空。此次任务失败！";
		}
		
		// 创建目录
		createFilePath();
		
		// 写入文件
		try
		{
			// 创建文件
			createFileByName();
			// 全量导入数据
			if (pageContentNum == 0)
			{
				List<String[]> dataList = queryDBforList();
				writerFile(dataList);
				if(dataList!=null)
				{
					if(dataList!=null) dataList.clear();
				}
			}
			// 分页导入数据
			else
			{
				pagingDataExport(dataSize, pageContentNum);
			}
			
			createVeriFile(dataSize);
		}
		catch (BOException e)
		{
			Log.error("生成文件时发生错误！此次任务以失败告终。。。悲剧", e);
			return e.getMessage();
		}
		finally
		{
			// 关闭文件
			try
			{
				closeFile();
			}
			catch (BOException e)
			{
				Log.error("关闭当前任务生成文件时发生错误！此次任务以失败告终。。。悲剧", e);
				return e.getMessage();
			}
		}
		Log.info("统一生成文件模块生成文件动作完成，当前任务名为" + vo.getExportName());
		
		// 是否上传FTP
		if(!groupVO.getFtpId().equals("0"))
		{
			if (null != groupVO.getDataExportFtpVo()
					&& !"".equals(groupVO.getDataExportFtpVo().getFtpId()))
			{
				try
				{
					copyFileToFTP(groupVO);
				}
				catch (BOException e)
				{
					Log.error("此任务上传FTP时发生了错误！此次任务以失败告终。。。悲剧", e);
					return e.getMessage();
				}
			}
		}
		
		// 最后一次执行时间、执行耗时变更
		try
		{
			long endtime = System.currentTimeMillis();
			vo.setLastTime(date);
			vo.setExecTime(String.valueOf(endtime - starttime));
			DataExportBO.getInstance().editDataExport(vo);
		}
		catch (BOException e)
		{
			Log.error("最后反写任务耗时与最后执行时间时发生错误！此次任务也不能算以失败告终。。。悲剧", e);
			return e.getMessage();
		}
		
		Log.info("统一生成文件模块完成，当前任务名为" + vo.getExportName());
		
		return "success";
	}
	
	/**
	 * 当前分组任务要求上传文件至FTP
	 * @param groupVO
	 * @throws BOException
	 */
	private void copyFileToFTP(DataExportGroupVO groupVO) throws BOException
	{
		Log.info("当前任务要上传FTP，当前任务名为" + vo.getExportName());
		
        FTPClient ftp = null;

        try
        {
            // 取得远程目录中文件列表
            ftp = groupVO.getDataExportFtpVo().getFTPClient();

            if (null != groupVO.getDataExportFtpVo().getFtpPath() && !"".equals(groupVO.getDataExportFtpVo().getFtpPath()))
            {
                ftp.chdir(groupVO.getDataExportFtpVo().getFtpPath());
            }

            // 把本地全路径文件上传至指定文件名
            ftp.put(vo.getFileAllName(), vo.getToFTPFileName());

        }
        catch (Exception e)
        {
            throw new BOException("当前任务要上传FTP，但在上传文件时发生异常！", e);
        }
        finally
        {
            if (ftp != null)
            {
                try
                {
                    ftp.quit();
                }
                catch (Exception e)
                {
                }
            }
        }
		Log.info("当前任务上传FTP完成，当前任务名为" + vo.getExportName());
	}
	
	public abstract void createFileByName() throws BOException;
	
	public abstract void closeFile() throws BOException;
	
	public abstract void createVeriFile(int size) throws BOException;
	
	public abstract String writerFile(List<String[]> dataList)
			throws BOException;
}
