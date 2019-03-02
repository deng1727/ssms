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
 * ȫ��ͬ��
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
			throw new BOException("û���ҵ�����������ļ��쳣",DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}
		//������Ҫ��ͬ��ǰ��Ҫ��֤��ǰͬ���������ȷ�ԡ�
		try {
			dataDealer.prepareData();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int k = 0; k < filenameList.length; k++) {
			String lineText = null;
			BufferedReader reader = null;
			//��¼���ļ���ǰ�����������
			int lineNumeber = 0;
			try
			{
				
	
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(
						filenameList[k]), this.fileEncoding));
	
				//��ȡ��Ϸ�б��ļ���
				LOG.info("��ʼ�����ļ���" + filenameList[k]);
	
				while ((lineText = reader.readLine()) != null)
				{
					lineNumeber++;//��¼�ļ���������
					if(lineNumeber==1)//ɾ����һ��bom�ַ�
					{
						lineText=PublicUtil.delStringWithBOM(lineText);
					}
					if (LOG.isDebugEnabled())
					{
						LOG.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
					}
					if("".equals(lineText.trim()))//���ڿ��еļ�¼������
					{
						LOG.debug("�����ǿ��У�������lineNumeber="+lineNumeber);
						continue;
					}
					DataRecord dr = dataReader.readDataRecord(lineText);
					if (dataChecker != null)
					{
						int checkResult = dataChecker.checkDateRecord(dr);
	
						if (checkResult == DataSyncConstants.CHECK_FAILED)
						{				
							LOG.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
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
						LOG.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
						this.addStatisticCount(DataSyncConstants.FAILURE);
	                    this.addDealFiledRow(lineNumeber);
						continue;
					}
					this.addStatisticCount(result);//����ͳ����Ϣ��
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
		
		//�ļ�������Ϻ���������
		try{
			//ɾ���������ϴ�ͬ�����������ڱ���ͬ�������ݡ�����Ϊ������ȫ���ģ�
			dataDealer.delOldData();
			dataDealer.clearDirtyData();
		}catch(Exception e){
			LOG.error("�����ļ������ݿ����ݳ���",e);
		}
		
	}

}
