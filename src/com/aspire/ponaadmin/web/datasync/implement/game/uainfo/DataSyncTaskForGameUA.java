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
			throw new BOException("û���ҵ�����������ļ��쳣",DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}

		String lineText = null;
		BufferedReader reader = null;
		//��¼���ļ���ǰ�����������
		int lineNumeber = 0;
		try
		{
			dataDealer.setSyncTask(this);
			//������Ҫ��ͬ��ǰ��Ҫ��֤��ǰͬ���������ȷ�ԡ�
			dataDealer.prepareData();

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(
					filenameList[0]), this.fileEncoding));

			//��ȡ��Ϸ�б��ļ���
			LOG.info("��ʼ�����ļ�uainfo.data��" + filenameList[0]);

			DataRecord dr=new DataRecord();
			int uaCount=1;
			while ((lineText = reader.readLine()) != null)
			{
				lineNumeber++;//��¼�ļ���������
				if (LOG.isDebugEnabled())
				{
					LOG.debug("��" + lineNumeber + "��UA��ϢΪ��"+lineText);
				}
				if(!"".equals(lineText.trim()))//�ǿ��ַ��Ŵ���
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
