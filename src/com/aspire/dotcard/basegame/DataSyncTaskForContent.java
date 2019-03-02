package com.aspire.dotcard.basegame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;
import com.aspire.ponaadmin.web.datasync.implement.game.GameConfig;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * ��Ϸ����ͬ��
 * @author aiyan
 *
 */
public class DataSyncTaskForContent extends DataSyncTask
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
		//��ʼ����Ϸ������Ϣ��
		GameConfig.init(config);
	}
	
	public void doTask()throws BOException
	{
		long start = System.currentTimeMillis();
//
//		e:/temp/Gamelist20121016.tar.gz
//
//		e:/temp/Gamelist20121016

		String[] filenameList = ftp.process();
		//String[] filenameList ={"E:/var/Gamelist20130418.tar.gz"};
		//String[] filenameList ={"E:/var/i_g-gameService_20130422_000001.txt"};

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
			// ���Ƚ�ѹ���ļ�����ǰĿ¼��
			String gameFileRootPath = DataSyncTools.ungzip(filenameList[0]);
			//String gameFileRootPath = "e:/temp/Gamelist20121016";
			// ������Ϸ�ļ���Ŀ¼��·����
			((ContentDataDealer)dataDealer).setGameFileRootPath(gameFileRootPath);
			TaskRunner runner = new TaskRunner(GameConfig.maxProcessThread);
			
			//������Ҫ��ͬ��ǰ��Ҫ��֤��ǰͬ���������ȷ�ԡ�
			dataDealer.prepareData();
			
			String[] serviceinfoPath = getServiceInfoFileName(gameFileRootPath);
			for(int i=0;i<serviceinfoPath.length;i++){
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(
						gameFileRootPath+File.separator+serviceinfoPath[i]), this.fileEncoding));
				LOG.info("��ʼ�����ļ���" + serviceinfoPath[i]);
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
			// �����������ˣ�����Ҫɾ�����ߵ���Ϸ��
			if (LOG.isDebugEnabled())
			{
				LOG.debug("��ʼɾ���ϴ�ͬ���У��������޵���Ϸ��");
			}
			//ɾ���������ϴ�ͬ�����������ڱ���ͬ�������ݡ�����Ϊ������ȫ���ģ�
			dataDealer.delOldData();
			
			//���ɻ��޸���Ϸ����Ϣ��
			genGamePkg();
			
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
	
	/**
	 * ��ȡ��Ϸ�����б��ļ���
	 */
	private String[] getServiceInfoFileName(String gameFileRootPath)throws BOException
	{
		
		File dir=new File(gameFileRootPath);
		String fileNames[]=dir.list(new FilenameFilter(){

			public boolean accept(File arg0, String arg1) {
				// TODO Auto-generated method stub
				return arg1.matches("i_g-game_\\d{8}_\\d{6}.txt");
			};
		
		});
		if(fileNames!=null&&fileNames.length>0){
			return fileNames;
		}
		
		throw new BOException("�Ҳ���������Ϸ�б��ļ��쳣��");
	}
	
	private void genGamePkg(){
		String sqlCode="com.aspire.dotcard.basegame.ServiceDealer.prepareData.getPkgGameKeys";
		Set pkgGameKeys = null;
		try {
			pkgGameKeys = GameSyncDAO.getInstance().getAllKeyId(sqlCode);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			return;
		}
		// TODO Auto-generated method stub
		List pkgList = GameSyncDAO.getInstance().queryGameContent();
		for(int i=0;i<pkgList.size();i++){
			//updateGamePkg((GameContent)pkgList.get(i));
			dealPkgGame((GameContent)pkgList.get(i),pkgGameKeys);
		}
		
		delOldPKGGame(pkgGameKeys);
		
	}
	


	private void dealPkgGame(GameContent content,Set pkgGameKeys){
		try {
			//���T_GAME_PKG��
			if(pkgGameKeys.contains(content.getContentCode())){
				GameSyncDAO.getInstance().updatePkgGame(content);
				pkgGameKeys.remove(content.getContentCode());//Ϊɾ������׼������
			}else{
				GameSyncDAO.getInstance().insertPkgGame(content);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("������Ϸ������",e);
		}
		
	}
	private void delOldPKGGame(Set pkgGameKeys){
		LOG.debug("��ʼ������Ϸ����");
		if(pkgGameKeys==null||pkgGameKeys.size()==0){
			return;
		}
		Object[] arr = pkgGameKeys.toArray();
		int size = arr.length;
		int start=0;int end =0;
		String sql;
		try {
			sql = DB.getInstance().getSQLByCode("com.aspire.dotcard.basegame.delOldPKGGame");
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			LOG.error("com.aspire.dotcard.basegame.delOldPKGGameû����sql.properties����",e1);
			return;
		}
		do{
			end=end+100;
			if(end>size) end=size;
			
			StringBuffer sb = new StringBuffer();
			for(int i=start;i<end;i++){
				sb.append(",'").append(arr[i]).append("'");
			}
			if(sb.length()>0){
				try {
					String whereStr = " where pkgid in ("+sb.substring(1)+")";
					GameSyncDAO.getInstance().delOldData(sql+whereStr);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					LOG.error("��Ϸ��ɾ�������ݳ���",e);
				}
			}
			
			start = end;
		}while(end<size);
		
	}

}
