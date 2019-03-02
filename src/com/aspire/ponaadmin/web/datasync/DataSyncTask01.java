package com.aspire.ponaadmin.web.datasync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.implement.video.VideoDAO;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * ����ͬ����һ�δ���һ���ļ���
 * �����ڲ�Ʒ������Ϣ����ͬ��������
 * @author zhangwei
 *
 */
public class DataSyncTask01 extends DataSyncTask
{
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	protected DataDealer dataDealer;
	private static JLogger LOG = LoggerFactory.getLogger(DataSyncTask01.class);

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
			throw new BOException("û���ҵ�����������ļ��쳣",
					DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}
		String lineText = null;
		BufferedReader reader = null;
		// �����ļ��ɹ����������
		int lineNumeber = 0;
		try
		{
			//������Ҫ��ͬ��ǰ��Ҫ��֤��ǰͬ���������ȷ�ԡ�
			dataDealer.prepareData();

			for (int i = 0; i < filenameList.length; i++)
			{
				//reader = new BufferedReader(new FileReader(filenameList[0]));
				if (LOG.isDebugEnabled())
				{
					LOG.debug("��ʼ�����ļ���" + filenameList[i]);
				}
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(
						filenameList[i]), this.fileEncoding));
                
                File file = new File(filenameList[i]);
                
                // ����ļ�Ϊ��
                if(file.length() == 0)
                {
                    throw new BOException("���ļ�Ϊ��",
                                        DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
                }
                
                // ������¼ӵ����������ͣ�����ɾ�����ݿ�
                VideoDAO.getInstance().delVideoData(dataDealer);
                
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
					
					if (result == DataSyncConstants.FAILURE_NOT_CHANGETYPE)
					{
						LOG.error("û�д�changeType����,����ʧ��");
					}
					else if (result == DataSyncConstants.FAILURE_ADD_EXIST)
					{
						LOG.error("����ʧ�ܣ���Ӧ�����Ѵ���");
					}
					else if (result == DataSyncConstants.FAILURE_UPDATE_NOT_EXIST)
					{
						LOG.error("����ʧ�ܣ���Ӧ���ݲ�����");
					}
					else if (result == DataSyncConstants.FAILURE_DEL_NOT_EXIST)
					{
						LOG.error("ɾ��ʧ�ܣ���Ӧ���ݲ�����");
					}
					if(result != DataSyncConstants.SUCCESS_ADD &&
							result != DataSyncConstants.SUCCESS_UPDATE &&
							result != DataSyncConstants.SUCCESS_DEL&&
							result != DataSyncConstants.CHECK_FAILED ){
						 
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
