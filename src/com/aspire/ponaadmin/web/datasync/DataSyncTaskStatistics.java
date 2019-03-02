package com.aspire.ponaadmin.web.datasync;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 增量同步、一次处理一个文件。
 * 适用于产品索引信息增量同步场景。
 * @author zhangwei
 *
 */
public class DataSyncTaskStatistics extends DataSyncTask
{
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	protected DataDealer dataDealer;
	private static JLogger LOG = LoggerFactory.getLogger(DataSyncTaskStatistics.class);

	public void init(DataSyncConfig config) throws Exception
	{
		super.init(config);
		this.ftp = (FtpProcessor) Class.forName(config.get("task.ftp-class"))
				.newInstance();
		this.dataReader = (DataReader) Class
				.forName(config.get("task.data-reader-class")).newInstance();
		this.dataDealer = (DataDealer) Class
				.forName(config.get("task.data-dealer-class")).newInstance();
		this.dataChecker = (DataChecker) Class.forName(
				config.get("task.data-checker-class")).newInstance();
		ftp.init(config);
		dataReader.init(config);
		dataDealer.init(config);
		dataChecker.init(config);
	}

	public void doTask() throws BOException
	{
		String[] filenameList = ftp.process();
		if (filenameList.length == 0)
		{
			throw new BOException("没有找到本次任务的文件异常",
					DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}
		String lineText = null;
		BufferedReader reader = null;
		// 用于文件成功处理的行数
		int lineNumeber = 0;
		try
		{
			//首先需要在同步前需要保证当前同步任务的正确性。
			dataDealer.prepareData();

			for (int i = 0; i < filenameList.length; i++)
			{
				//reader = new BufferedReader(new FileReader(filenameList[0]));
				if (LOG.isDebugEnabled())
				{
					LOG.debug("开始处理文件：" + filenameList[i]);
				}
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(
						filenameList[i]), this.fileEncoding));
				while ((lineText = reader.readLine()) != null)
				{
					lineNumeber++;//记录文件的行数。
					if(lineNumeber==1)//删除第一行bom字符
					{
						lineText=PublicUtil.delStringWithBOM(lineText);
					}
					if (LOG.isDebugEnabled())
					{
						LOG.debug("开始处理第" + lineNumeber + "行数据。");
					}
					if("".equals(lineText.trim()))//对于空行的记录不处理。
					{
						LOG.debug("该行是空行，不处理。lineNumeber="+lineNumeber);
						continue;
					}
					DataRecord dr = dataReader.readDataRecord(lineText);
					if (dataChecker != null)
					{
						int checkResult = dataChecker.checkDateRecord(dr);

						if (checkResult == DataSyncConstants.CHECK_FAILED)
						{				
							LOG.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            this.addCheckFiledRow(lineNumeber);
							this.addStatisticCount(checkResult);
							continue;
						}
					}
					int result;
					try
					{
						result = dataDealer.dealDataRecrod(dr);
					} catch (Exception e)
					{
						LOG.error("第" + lineNumeber + "行数据处理失败。", e);
						this.addStatisticCount(DataSyncConstants.FAILURE);
                        this.addDealFiledRow(lineNumeber);
						continue;
					}
					this.addStatisticCount(result);//增加统计信息。
					
					if (result == DataSyncConstants.FAILURE_NOT_CHANGETYPE)
					{
						LOG.error("没有此changeType类型,处理失败");
					}
					else if (result == DataSyncConstants.FAILURE_ADD_EXIST)
					{
						LOG.error("新增失败，对应内容已存在");
					}
					else if (result == DataSyncConstants.FAILURE_UPDATE_NOT_EXIST)
					{
						LOG.error("更新失败，对应内容不存在");
					}
					else if (result == DataSyncConstants.FAILURE_DEL_NOT_EXIST)
					{
						LOG.error("删除失败，对应内容不存在");
					}
                    if (result != DataSyncConstants.FAILURE_NOT_CHANGETYPE
                        && result != DataSyncConstants.FAILURE_ADD_EXIST
                        && result != DataSyncConstants.FAILURE_UPDATE_NOT_EXIST
                        && result != DataSyncConstants.FAILURE_DEL_NOT_EXIST)
                    {

                        this.addDealFiledRow(lineNumeber);
                    }
				}
			}

		} catch (Exception e)
		{
			throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
		} finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
			} catch (IOException e)
			{
				LOG.error(e);
			}
			//clear data
			dataDealer.clearDirtyData();
		}
	}

}
