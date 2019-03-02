package com.aspire.dotcard.baseVideo.exportfile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.exportfile.impl.ProductExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.ProgramByHourExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.ProgramExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.RecommendByHourExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.VideoByHourExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.VideoDetailExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.VideoExportFile;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SynTask
{
	private JLogger logger = LoggerFactory.getLogger(SynTask.class);

	private String lineText;

	private String tempFileName;

	private int lineNumeber;

	private String dataSpacers;

	private BaseExportFile base;

	public SynTask(String lineTest, String tempFileName, int lineNumeber,
			String dataSpacers, BaseExportFile base)
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
			logger.debug("开始处理校验文件第" + lineNumeber + "行数据。");
		}

		if (lineNumeber == 1)
		{
			// 删除第一行bom字符
			lineText = PublicUtil.delStringWithBOM(lineText);
		}

		// 临时数据
		String[] tempData = lineText.split(dataSpacers, -1);

		// 校验数据正确性，如果校验失败加入邮件信息
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(base.checkData(tempData)))
		{
			// 加入错误信息至邮件中...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText, "校验失败");
			base.setFailureCheckAdd();
			return;
		}

		String exportDBText;

		if (base instanceof VideoDetailExportFile)
		{
			exportDBText = exportDataToDBByVideoDetail(tempData);
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
		
		else if (!base.isDelTable && base instanceof VideoExportFile)
		{
			exportDBText = exportDataToDBByVideo(tempData);
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
	 * 实现类实现把数据存入库中
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	public String exportDataToDB(String[] data)
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
			if (base.isDelTable && base instanceof ProgramExportFile)
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
				return BaseVideoConfig.CHECK_DATA_SUCCESS;
			}
			else if (base instanceof VideoExportFile)
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
				return BaseVideoConfig.CHECK_DATA_SUCCESS;
			}
			else
			{
				if (!base.hasKey(key))
				{
					// 更新结果集为0,则插入
					DB.getInstance().executeBySQLCode(insertSqlCode, paras);
				}
				else
				{
					DB.getInstance().executeBySQLCode(updateSqlCode, paras);
				}
				
				if (base instanceof ProductExportFile)
				{
					// 如果是产品，则删除掉本次有的，最后剩下的，即是应该下线的
					ProductExportFile pbase = (ProductExportFile) base;
					pbase.delKeyMap(key);
				}
			}
		}
		catch (DAOException e)
		{
			logger.error("执行基地视频数据插入失败，当前数据主健为：" + key + "！" + e);
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
	public String exportDataToDBByVideo(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}

		String insertSqlCode = base.getInsertSqlCode();
		String updateSqlCode = base.getUpdateSqlCode();
		Object[] paras = base.getObject(data);
		Object[] paras_del = base.getHasObject(data);
		String key = base.getKey(data);
		try
		{
			if ("1".equals(data[5]))
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			else if ("2".equals(data[5]))
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras_del);
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
			logger.error("执行基地视频数据操作失败，当前数据主健为：" + key + "！，变更类型为：" + data[5]
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
	public String exportDataToDBByVideoDetail(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}
		String insertSqlCode = base.getInsertSqlCode();
		String updateSqlCode = base.getUpdateSqlCode();
		Object[] insertParas = base.getObject(data);
		String key = base.getKey(data);

		try
		{
			if (base.hasKey(key))
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, insertParas);
			}
			else
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, insertParas);
			}
		}
		catch (DAOException e)
		{
			logger.error("执行基地视频数据插入失败" + e);
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
		Object[] paras_del = base.getHasObject(data);
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
				DB.getInstance().executeBySQLCode(delSqlCode, paras_del);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	
	private Object[] getUpdateObject(String[] data)
	{
		Object[] object = new Object[3];

		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[0];

		return object;
	} */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	 
	private String getVideoDetailKey(String[] data)
	{
		return data[0];
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
	 
	private String getUpdateSqlCode1()
	{
		// update T_VO_VIDEODETAIL v set v.totalplaynum = v.totalplaynum +
		// v.dayplaynum, v.totaldownloadnum = v.totaldownloadnum +
		// v.daydownloadnum, v.updatetime = sysdate where v.programid = ?
		return "baseVideo.exportfile.VideoDetailExportFile.getUpdateSqlCode1";
	}*/

}
