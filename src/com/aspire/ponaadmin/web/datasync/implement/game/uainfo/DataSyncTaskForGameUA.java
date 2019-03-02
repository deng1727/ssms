package com.aspire.ponaadmin.web.datasync.implement.game.uainfo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;

public class DataSyncTaskForGameUA extends DataSyncTask
{
	private FtpProcessor ftp;
	private UAInfoDealer dataDealer;
	private static JLogger LOG = LoggerFactory.getLogger(DataSyncTaskForGameUA.class);
	public void init(DataSyncConfig config) throws Exception
	{
		super.init(config);
		this.ftp = (FtpProcessor) Class.forName(config.get("task.ftp-class"))
				.newInstance();
		this.dataDealer = (UAInfoDealer) Class
				.forName(config.get("task.data-dealer-class")).newInstance();
		ftp.init(config);
		dataDealer.init(config);
	}

	public void doTask()throws BOException
	{

		String[] filenameList = ftp.process();	
		if (filenameList.length == 0)
		{
			throw new BOException("没有找到本次任务的文件异常",DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}

		String lineText = null;
		BufferedReader reader = null;
		//记录当文件当前处理的行数。
		int lineNumeber = 0;
		try
		{
			dataDealer.setSyncTask(this);
			//首先需要在同步前需要保证当前同步任务的正确性。
			dataDealer.prepareData();

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(
					filenameList[0]), this.fileEncoding));

			//读取游戏列表文件。
			LOG.info("开始处理文件uainfo.data：" + filenameList[0]);

			DataRecord dr=new DataRecord();
			int uaCount=1;
			while ((lineText = reader.readLine()) != null)
			{
				lineNumeber++;//记录文件的行数。
				if (LOG.isDebugEnabled())
				{
					LOG.debug("第" + lineNumeber + "行UA信息为："+lineText);
				}
				if(!"".equals(lineText.trim()))//非空字符才处理。
				{
					dr.put(uaCount++, lineText);
				}
			}
			dataDealer.dealDataRecrod(dr);
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
		dataDealer.clearDirtyData();
	}

}
