package com.aspire.dotcard.baseVideoNew.exportfile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.ProgramByHourExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.RecommendByHourExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.VideoByHourExportFile;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.VideoExportFile;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SynBaseVideoTask
{
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
		boolean flag = true;
		if (tempFileName.indexOf(BaseVideoNewConfig.DEL_SHELL_FILE) > 0)
		{
			flag = false;
		}
		// 校验数据正确性，如果校验失败加入邮件信息
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(base.checkData(tempData,flag)))
		{
			// 加入错误信息至邮件中...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText, "校验失败");
			base.setFailureCheckAdd();
			return;
		}
		
		logger
				.debug("当前文件名为："
						+ tempFileName
						+ "， 判断是否为删除文件: "
						+ (tempFileName
								.indexOf(BaseVideoNewConfig.DEL_SHELL_FILE) > 0));
		
		if (base instanceof VideoExportFile)
		{
			exportDBText = exportDataToDBVideo(tempData);
		}
		// 如果是生成的删除文件
		else if (tempFileName.indexOf(BaseVideoNewConfig.DEL_SHELL_FILE) > 0)
		{
			exportDBText = exportDataToDBDel(tempData);
		}
		//视频按小时增量
		else if (base instanceof VideoByHourExportFile)
		{
			exportDBText = exportDataToDBByVideoHour(tempData);
		}
		////节目详情按小时增量
		else if (base instanceof ProgramByHourExportFile)
		{
			exportDBText = exportDataToDBByProgramHour(tempData);
		}
		////热点内容推荐数据按小时增量
		else if (base instanceof RecommendByHourExportFile)
		{
			exportDBText = exportDataToDBByRecommendHour(tempData);
		}
		else
		{
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
	 * 实现类实现把数据存入库中针对视频文件
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	private String exportDataToDBVideo(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}
		
		String insertSqlCode = base.getInsertSqlCode();
		String delSqlCode = base.getDelSqlCode();
		String key = base.getKey(data);
		Object[] paras = base.getObject(data);
		Object[] paras_d = base.getDelKey(key);
		try
		{
			if ("1".equals(data[5]))
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			else
			{
				DB.getInstance().executeBySQLCode(delSqlCode, paras_d);
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
				if(base.isCollect){
					base.setAddNumAdd();
				}
			}
			else
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras);
				base.delKeyMap(key);
                if(base.isCollect){
                	base.setModifyNumAdd();
				}
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
	 * 用于把删除类型的数据写入中间表中
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	private String exportDataToDBDel(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}
		
		String delSqlCode = base.getDelSqlCode();
		String key = base.getKey(data);
		try
		{
			DB.getInstance().executeBySQLCode(delSqlCode, base.getDelKey(key));
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
	public String exportDataToDBByVideoHour(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}

		String insertSqlCode = base.getInsertSqlCode();
		String updateSqlCode = base.getUpdateSqlCode();
		String delSqlCode = base.getDelSqlCode();
		Object[] paras = base.getObject(data);
		String key = base.getKey(data);
		try
		{
			if ("1".equals(data[5]))
			{
				//视频ID数据量很大，不做ID存在处理，新增失败做更新处理
				try {
					
					DB.getInstance().executeBySQLCode(insertSqlCode, paras);
					
				} catch (DAOException e) {
					
					try {
						DB.getInstance().executeBySQLCode(updateSqlCode, paras);
					} catch (DAOException ex) {
						logger.error("执行基地视频数据按小时操作失败，当前数据主健为：" + key + "！，变更类型为：" + data[5]
						+ ". " + ex);
						 
						 return "执行时发生数据库异常";
					}
					
				}
				
			}
			else if ("2".equals(data[5]))
			{
				DB.getInstance().executeBySQLCode(delSqlCode, new Object[]{data[0],data[1]});
			}
			else
			{
				logger.error("当前行数据变更类型有误，当前数据主健为：" + key + "！，变更类型为："
						+ data[5]);
				return "执行时发生数据库异常";
			}
		}
		catch (DAOException e)
		{
			logger.error("执行基地视频数据按小时操作失败，当前数据主健为：" + key + "！，变更类型为：" + data[5]
					+ ". " + e);
			e.printStackTrace();
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
	public String exportDataToDBByProgramHour(String[] data)
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
			}
			else
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			
		}
		catch (DAOException e)
		{
			logger.error("执行基地节目详情数据按小时操作失败，当前数据主健为：" + key + "！" + e);
			e.printStackTrace();
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
	public String exportDataToDBByRecommendHour(String[] data)
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
			}
			else
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			
		}
		catch (DAOException e)
		{
			logger.error("执行基地热点内容推荐数据按小时增量操作失败，当前数据主健为：" + key + "！" + e);
			e.printStackTrace();
			return "执行时发生数据库异常";
		}

		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
}
