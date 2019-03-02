package com.aspire.dotcard.basevideosync.exportfile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.exportfile.impl.PkgSalesExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProgramByHourExportFile;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SynBaseVideoTask {

	private JLogger logger = LoggerFactory.getLogger(SynBaseVideoTask.class);
	
	/**
	 * 当前行信息
	 */
	private String lineText;
	
	/**
	 * 当前处理文件名
	 */
	private String tempFileName;
	
	/**
	 * 当前是第几行
	 */
	private int lineNumeber;
	
	/**
	 * 当前行的分隔符
	 */
	private String dataSpacers;
	
	/**
	 * 调用执行任务类
	 */
	private BaseExportFile base;
	
	public SynBaseVideoTask(String lineTest, String tempFileName,
			int lineNumeber, String dataSpacers, BaseExportFile base)
			throws InstantiationException, IllegalAccessException
	{
		this.lineText = lineTest;
		this.tempFileName = tempFileName;
		this.lineNumeber = lineNumeber;
		this.dataSpacers = dataSpacers;
		this.base = base;
	}
	
	/**
	 * 用于被多线程调用的具体执行方法
	 * 
	 * @param lineText
	 * @param tempFileName
	 */
	public void sysDataByFile()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始处理校验文件第" + lineNumeber + "行数据。文件名：" + tempFileName);
		}
		
		if (lineNumeber == 1)
		{
			// 删除第一行bom字符
			lineText = PublicUtil.delStringWithBOM(lineText);
		}
		
		// 临时数据
		String[] tempData = lineText.split(dataSpacers, -1);
		String exportDBText;

		// 校验数据正确性，如果校验失败加入邮件信息
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(base.checkData(tempData)))
		{
			// 加入错误信息至邮件中...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText, "校验失败");
			base.setFailureCheckAdd();
			return;
		}
		//视频节目详情内容导入
		if (base instanceof ProgramByHourExportFile)
		{
			exportDBText = exportDataToDBByProgramHour(tempData);
		}
		//基地视频产品包促销计费数据导入
		else if(base instanceof PkgSalesExportFile)
		{
			exportDBText = exportDataToDBByPkgSales(tempData);
		}else{
			exportDBText = exportDataToDB(tempData);
		}
		
		// 存入数据库，如果处理失败加入邮件信息
		if (!BaseVideoConfig.EXPORT_DATA_SUCCESS.equals(exportDBText))
		{
			// 加入错误信息至邮件中...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText,
					exportDBText);
			base.setFailureProcessAdd();
			return;
		}
		else
		{
			base.setSuccessAddAdd();
		}
	}
	
	/**
	 * 实现类实现把数据存入库中
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	private String exportDataToDB(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}
		
		String insertSqlCode = base.getInsertSqlCode();
		String updateSqlCode = base.getUpdateSqlCode();
		Object[] paras = base.getObject(data);
		String key = base.getKey(data);
		try
		{
			if (!base.hasKey(key))
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
				base.delKeyMap(key);
				base.setAddNumAdd();
			}
			else
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras);
				base.delKeyMap(key);
				base.setModifyNumAdd();
			}
		}
		catch (DAOException e)
		{
			logger.error("执行基地视频数据操作失败，当前数据主健为：" + key + "！", e);
			return "执行时发生数据库异常";
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/**
	 * 实现类实现把数据存入库中
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	private String exportDataToDBByPkgSales(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}
		
		String insertSqlCode = base.getInsertSqlCode();
		Object[] paras = base.getObject(data);
		String key = base.getKey(data);
		try
		{
			//产品包促销计费数据只做插入操作
			DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			base.setAddNumAdd();
		}
		catch (DAOException e)
		{
			logger.error("执行基地视频数据操作失败，当前数据主健为：" + key + "！", e);
			return "执行时发生数据库异常";
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/**
	 * 实现类实现把数据存入库中
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	private String exportDataToDBByProgramHour(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}
		
		String insertSqlCode = base.getInsertSqlCode();
		String updateSqlCode = base.getUpdateSqlCode();
		Object[] paras = base.getObject(data);
		String key = base.getKey(data);
		try
		{
			if(base.hasKey(key))
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras);
				base.setModifyNumAdd();
			}
			else
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
				base.setAddNumAdd();
			}
		}
		catch (DAOException e)
		{
			logger.error("执行基地节目数据按小时操作失败，当前数据主健为：" + key + "！" + e);
			e.printStackTrace();
			return "执行时发生数据库异常";
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
}
