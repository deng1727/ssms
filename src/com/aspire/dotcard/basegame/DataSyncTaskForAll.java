package com.aspire.dotcard.basegame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 全量同步
 * @author aiyan
 *
 */
public class DataSyncTaskForAll extends DataSyncTask
{
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	protected DataDealer dataDealer;
	private static JLogger LOG = LoggerFactory.getLogger(DataSyncTaskForAll.class);
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

	public void doTask()throws BOException
	{

		String[] filenameList = ftp.process();	
		if (filenameList.length == 0)
		{
			throw new BOException("没有找到本次任务的文件异常",DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}
		//首先需要在同步前需要保证当前同步任务的正确性。
		try {
			dataDealer.prepareData();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int k = 0; k < filenameList.length; k++) {
			String lineText = null;
			BufferedReader reader = null;
			//记录当文件当前处理的行数。
			int lineNumeber = 0;
			try
			{
				
	
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(
						filenameList[k]), this.fileEncoding));
	
				//读取游戏列表文件。
				LOG.info("开始处理文件：" + filenameList[k]);
	
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
							this.addStatisticCount(checkResult);
							this.addCheckFiledRow(lineNumeber);
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
					if(result != DataSyncConstants.SUCCESS_ADD &&
							result != DataSyncConstants.SUCCESS_UPDATE &&
							result != DataSyncConstants.SUCCESS_DEL&&
							result != DataSyncConstants.CHECK_FAILED ){
						 
						this.addDealFiledRow(lineNumeber);
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
			}

		}
		
		//文件处理完毕后，数据清理。
		try{
			//删除存在于上次同步而不存在于本次同步的数据。（因为数据是全量的）
			dataDealer.delOldData();
			dataDealer.clearDirtyData();
		}catch(Exception e){
			LOG.error("清理文件或数据库数据出错！",e);
		}
		
	}

}
