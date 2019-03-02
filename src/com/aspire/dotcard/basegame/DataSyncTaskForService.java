package com.aspire.dotcard.basegame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.syncAndroid.dc.MessageSendDAO;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * ��Ϸҵ��ͬ��
 * @author aiyan
 *
 */
public class DataSyncTaskForService extends DataSyncTask
{
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	protected ServiceDealer dataDealer;
	private static JLogger LOG = LoggerFactory.getLogger(DataSyncTaskForService.class);
	private int maxProcessThread=10;
	
	public void init(DataSyncConfig config) throws Exception
	{
		super.init(config);
		this.ftp = (FtpProcessor) Class.forName(config.get("task.ftp-class"))
				.newInstance();
		this.dataReader = (DataReader) Class
				.forName(config.get("task.data-reader-class")).newInstance();
		this.dataDealer = (ServiceDealer) Class
				.forName(config.get("task.data-dealer-class")).newInstance();
		this.dataChecker = (DataChecker) Class.forName(
				config.get("task.data-checker-class")).newInstance();
		ftp.init(config); 
		dataReader.init(config);
		dataDealer.init(config);
		dataChecker.init(config);
		
		maxProcessThread=Integer.parseInt(config.get("task.maxProcessThread"));
	}

	public void doTask()throws BOException
	{
		long start = System.currentTimeMillis();
		LOG.debug("ҵ���߳̿�ʼ���С�����");
		String[] filenameList = ftp.process();	
		//String[] filenameList ={"E:/var/i_g-gameService_20130422_000001.txt"};
		if (filenameList.length == 0)
		{
			throw new BOException("û���ҵ�����������ļ��쳣",DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}
		TaskRunner runner = new TaskRunner(maxProcessThread);
		String lineText = null;
		BufferedReader reader = null;
		//��¼���ļ���ǰ�����������
		int lineNumeber = 0;
		List failureChecked=new ArrayList();
		//�Ƿ��Ѿ�ִ��ͬ����Ϊ��ֹ�����ṩ���ļ���ɾ�����е����ݡ�
		boolean isSync = false;
		try
		{
			//������Ҫ��ͬ��ǰ��Ҫ��֤��ǰͬ���������ȷ�ԡ�
			dataDealer.prepareData();
			
			for(int i =0;i<filenameList.length;i++){
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(
						filenameList[i]), this.fileEncoding));
				
				//��ȡ��Ϸ�б��ļ���
				LOG.info("��ʼ�����ļ���" + filenameList[0]);
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
							failureChecked.add(dr.get(1));
							LOG.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
							this.addStatisticCount(checkResult);
							this.addCheckFiledRow(lineNumeber);
							continue;
						}
						
					}
					//ִ����ͬ��
					isSync = true;
					// �����첽����
					Task gameTask = new ReflectedGameTask(this, dataDealer, dr, lineNumeber);
					// ������ӵ���������
					runner.addTask(gameTask);
	
				}
			}
			
			// �ȴ���������ɡ�
			runner.waitToFinished();
			LOG.info("##############���е�taskִ�����###############");
			
			Thread.sleep(2000);//����2���ӡ��ȴ�һ����־д�롣
			
			if(isSync){
				// �����������ˣ�����Ҫɾ�����ߵ���Ϸ��
				if (LOG.isDebugEnabled())
				{
					LOG.debug("��ʼɾ���Ѿ����ߵ���Ϸ");
				}
				
				dataDealer.delOldData(this,failureChecked);
				
				//�Ѱ��������ϵ������ŵ�����t_game_pkg���е�fulldevicename,fulldeviceid
				putGamePkgUA();
				
//				create index T_GAME_PKG_INDEXALL
//				on T_GAME_PKG(fulldeviceid) indextype is ctxsys.context 
//			--	parameters('lexer chinese_lexer') 
//		    -- parallel(degree 4)
				
				//execute immediate 'create index '||seq_INDEX_V_DEVICENAME_APP||' 
				//on '||tableName||'(full_deviceName) indextype is ctxsys.context 
				//parameters(''lexer chinese_lexer'') parallel(degree 4) ';
				//��t_game_pkg�е�fulldeviceid����ȫ�ļ�����
				buildIndexContext();
				
//				//���µ���Ʒ����������� ���������˵�����ˣ���ע���ˡ�
//				putGamePkgRefNum();
				
				//add by aiyan 2012-11-1
				//ҵ��ͬ����ɺ��t_game_statistcs�����ͳ����Ϣ��DOWNLOADNUM��STARTTIME��д��t_r_gcontent��MODAYORDERTIMES,LUPDATE��
				putGameStatistcs();
				
				// ��Ҫˢ���ﻯ��ͼ
				LOG.info("��ʼˢ��v_service��");
				GameSyncDAO.getInstance().refreshVService();
				LOG.info("ˢ��v_service����ɣ�");
				//MessageSendDAO.getInstance().changeStatus(-5, -1);//�ⲽ��û�б�Ҫ�ġ�remove by aiyan2013-05-20
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
		dataDealer.clearDirtyData();
		
		LOG.info("��Ϸ����ͬ�����,hehe.mygame_content:"+(System.currentTimeMillis()-start));
	}

//	private void putGamePkgRefNum() {
//		// TODO Auto-generated method stub
//		try {
//			GameSyncDAO.getInstance().putGamePkgRefNum();
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			LOG.error("������µ���Ʒ����ʱ�����ˣ�",e);
//		}
//	}

	private void putGameStatistcs() {
		// TODO Auto-generated method stub
		List l= GameSyncDAO.getInstance().queryGameStatistcs();
		for(int i=0;i<l.size();i++){
			GameSyncDAO.getInstance().updateGameStatistcs((GameStatistcs)l.get(i));
		}
		
	}

	//�����UA���ǽ�����t_game_pkg���е�fulldevicename,fulldeviceid�������
	private void putGamePkgUA() {
		// TODO Auto-generated method stub
		List pkgid = GameSyncDAO.getInstance().queryGamePkgid();
		for(int i=0;i<pkgid.size();i++){
			GameSyncDAO.getInstance().updateGamePkgUA((String)pkgid.get(i));
		}
		
	}
	
	
	//exec ctx_ddl.sync_index('T_GAME_PKG_INDEXALL')  �����������ʽ�ؽ�ȫ�������ģ�û��Ū�ɡ����Ը��˸�   drop  & create
	private void buildIndexContext() {
		// TODO Auto-generated method stub
		try {
		    String sql1 = "drop index T_GAME_PKG_INDEXALL";
			DB.getInstance().execute(sql1,null);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LOG.error("drop index T_GAME_PKG_INDEXALL����",e);
		}
		try {
			String sql2="create index T_GAME_PKG_INDEXALL on T_GAME_PKG(fulldeviceid) indextype is ctxsys.context";
			DB.getInstance().execute(sql2,null);
			LOG.info("T_GAME_PKG_INDEXALL�ɹ�ִ����ɣ�");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LOG.error("create index T_GAME_PKG_INDEXALL����",e);
		}
		
	
				
			
		
	}
	
	


}
