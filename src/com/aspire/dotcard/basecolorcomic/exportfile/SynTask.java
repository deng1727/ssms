package com.aspire.dotcard.basecolorcomic.exportfile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecolorcomic.conf.BaseColorComicConfig;
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
		if (!BaseColorComicConfig.CHECK_DATA_SUCCESS.equals(base
				.checkData(tempData)))
		{
			// 加入错误信息至邮件中...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText, "校验失败");
			base.setFailureCheckAdd();
			return;
		}
		
		String exportDBText = exportDataToDB(tempData);
		
		// 存入数据库，如果处理失败加入邮件信息
		if (!BaseColorComicConfig.EXPORT_DATA_SUCCESS.equals(exportDBText))
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
			if (!base.hasKey(key))
			{
				// 更新结果集为0,则插入
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			else
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras);
			}
		}
		catch (DAOException e)
		{
			logger.error("执行基地视频数据插入失败，当前数据主健为：" + key + "！", e);
			return "执行时发生数据库异常";
		}
		
		return BaseColorComicConfig.CHECK_DATA_SUCCESS;
	}
}
