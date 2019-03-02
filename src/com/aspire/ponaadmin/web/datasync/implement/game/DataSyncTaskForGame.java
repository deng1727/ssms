package com.aspire.ponaadmin.web.datasync.implement.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.common.threadtask.TaskTokenCenter;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;

/**
 * game��Ϸ�ļ��Ĵ���task��������Ϸͬ���Ƚϸ��ӣ������ص�д��һ��רΪ��Ϸͬ����task�ࡣ
 * ����Ӧ�ô�����ֱ����Ϸ�����ࣨgameDealer����
 * @author zhangwei
 *
 */
public class DataSyncTaskForGame extends DataSyncTask
{
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	private GameDealer gameDealer;
	private static JLogger LOG = LoggerFactory.getLogger(DataSyncTaskForGame.class);
	public void init(DataSyncConfig config) throws Exception
	{
		super.init(config);
		this.ftp = (FtpProcessor) Class.forName(config.get("task.ftp-class"))
				.newInstance();
		this.dataReader = (DataReader) Class
				.forName(config.get("task.data-reader-class")).newInstance();
		this.gameDealer = (GameDealer) Class
				.forName(config.get("task.data-dealer-class")).newInstance();
		this.dataChecker = (DataChecker) Class.forName(
				config.get("task.data-checker-class")).newInstance();
		ftp.init(config);
		dataReader.init(config);
		gameDealer.init(config);
		dataChecker.init(config);
		//��ʼ����Ϸ������Ϣ��
		GameConfig.init(config);

	}

	/**
	 * ��Ϸ����ȫ��ͬ����ͬʱ��Ҫ�ϴ�ͼƬ�ļ�����Դ�������ϡ�
	 */
	public void doTask()throws BOException
	{
		String[] filenameList = null;
		filenameList = ftp.process();
		if (filenameList.length == 0)
		{
			throw new BOException("û���ҵ�����������ļ��쳣",DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}

		String lineText = null;
		BufferedReader reader = null;
		// ��¼���ļ���ǰ�����������
		int lineNumeber = 0;
		try
		{
			// ������Ҫ��ͬ��ǰ��Ҫ��֤��ǰͬ���������ȷ�ԡ�
			gameDealer.prepareData();

			// ���Ƚ�ѹ���ļ�����ǰĿ¼��
			String gameFileRootPath = DataSyncTools.ungzip(filenameList[0]);

			// String todayString = PublicUtil.getCurDateTime("yyyyMMdd");
			/*
			 * String serviceinfoPath = gameFileRootPath + File.separator +
			 * "SERVICEINFO" + todayString + ".000";
			 */
			String serviceinfoPath = getServiceInfoFileName(gameFileRootPath);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(
					serviceinfoPath), this.fileEncoding));

			// ������Ϸ�ļ���Ŀ¼��·����
			gameDealer.setGameFileRootPath(gameFileRootPath);
			// ��ȡ��Ϸ�б��ļ���
			LOG.info("��ʼ������Ϸ��Ϣ�ļ���" + serviceinfoPath);
			// ����̴߳�����Ϸ���ļ���
			//TaskTokenCenter.getInstance().initTokens();
			TaskRunner runner = new TaskRunner(GameConfig.maxProcessThread);

			List failureChecked=new ArrayList();
			while ((lineText = reader.readLine()) != null)
			{
				lineNumeber++;// ��¼�ļ���������
				if (LOG.isDebugEnabled())
				{
					LOG.debug("��ʼ������" + lineNumeber + "�����ݡ�");
				}
				if("".equals(lineText.trim()))//���ڿ��еļ�¼������
				{
					LOG.debug("�����ǿ��У�������lineNumeber="+lineNumeber);
					continue;
				}
				DataRecord dr = dataReader.readDataRecord(lineText);
				int checkResult = dataChecker.checkDateRecord(dr);
				if (checkResult == DataSyncConstants.CHECK_FAILED)
				{
					// ��Ҫ��ȷ������Ϸ������
					BaseGameKeyVO gameKey = new BaseGameKeyVO();
					gameKey.setIcpCode((String) dr.get(1));
					gameKey.setIcpServid((String) dr.get(3));
					gameKey.setStatus(6);
					failureChecked.add(gameKey);
					this.addStatisticCount(checkResult);//����ͳ�ơ�
                    this.addCheckFiledRow(lineNumeber);
					continue;
				}
				// �����첽����
				Task gameTask = new ReflectedGameTask(this, gameDealer, dr, lineNumeber);
				// ������ӵ���������
				runner.addTask(gameTask);
			}
			// �ȴ���������ɡ�
			runner.waitToFinished();
			System.out.println("##############���е�taskִ�����###############");
			Thread.sleep(2000);//����2���ӡ��ȴ�һ����־д�롣
			// �����������ˣ�����Ҫɾ�����ߵ���Ϸ��
			if (LOG.isDebugEnabled())
			{
				LOG.debug("��ʼɾ���Ѿ����ߵ���Ϸ");
			}
			int result[]=gameDealer.deleteOldGame(failureChecked);
			this.addSuccessDelete(result[0]);
			this.addFailureProcess(result[1]);
			// ��Ҫˢ���ﻯ��ͼ
			GameSyncDAO.getInstance().refreshVService();

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
			// clear data �����Ƿ�ͬ���ɹ������ݻ���Ҫ����ġ�
			gameDealer.clearDirtyData();
		}
	}

	/**
	 * ����ͳ����Ϣ����¼�ɹ����������£�ɾ����ͳ����Ϣ��
	 * @param flag
	 *//*
	synchronized public void addStatisticCount(int flag)
	{
		if (flag == DataSyncConstants.SUCCESS_ADD)
		{
			this.successAdd++;
		}
		else if (flag == DataSyncConstants.SUCCESS_UPDATE)
		{
			this.successUpdate++;
		}
		else if (flag == DataSyncConstants.SUCCESS_DEL)
		{
			this.successDelete++;
		}else if(flag ==DataSyncConstants.FAILURE)
		{
			this.failureProcess++;
		}
	}*/
	/**
	 * ��ȡ��Ϸ�б��ļ�����Ϸ�б��ļ���ƥ��ȡ��ʱ������ƣ��ȷ��ص�һ��ƥ��SERVICEINFO\d{8}.000���ַ���
	 * Ŀ����Ϊ���Ժ��ֶ�ͬ����ʱ��ֻ��Ҫ����tar�����ļ�������,����Ҫ����tar��������ݡ�
	 * @param gameFileRootPath
	 * @return
	 * @throws BOException
	 */
	private String getServiceInfoFileName(String gameFileRootPath)throws BOException
	{
		File dir=new File(gameFileRootPath);
		String fileNames[]=dir.list();
		for(int i=0;i<fileNames.length;i++)
		{
			if(fileNames[i].matches("SERVICEINFO\\d{8}\\.000"))
			{
				return gameFileRootPath+File.separator+fileNames[i];
			}
		}
		throw new BOException("�Ҳ���������Ϸ�б��ļ��쳣��");
	}

}
