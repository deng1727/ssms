package com.aspire.dotcard.basegame;

import java.io.File;
import java.util.List;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.dotcard.gcontent.GAppGame;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class StatistcsDealer implements DataDealer
{
	private JLogger LOG=LoggerFactory.getLogger(StatistcsDealer.class);
	public void clearDirtyData()
	{
	}

	public int dealDataRecrod(DataRecord record)
	{
		//LOG.info("��ʼ������Ϣ��");
		try{
			GameStatistcs statistcs = new GameStatistcs();
			turnFieldsToGameStatistcs(record, statistcs);
			GameSyncDAO.getInstance().insertGameStatistcs(statistcs);
			return DataSyncConstants.SUCCESS_ADD;
		}catch(Exception e){
			LOG.error("���������Ϸ���ݳ����쳣",e);
			return DataSyncConstants.FAILURE;
		}

	}
	private void turnFieldsToGameStatistcs(DataRecord record,GameStatistcs statistcs) {
		// TODO Auto-generated method stub
		statistcs.setSeriveCode((String)record.get(1));
		statistcs.setDownloadNum((String)record.get(2));
		statistcs.setLoginNum((String)record.get(3));
		statistcs.setBroadcastNum((String)record.get(4));
		statistcs.setPayNum((String)record.get(5));
		statistcs.setGameLevel((String)record.get(6));
		statistcs.setCommentNum((String)record.get(7));
		statistcs.setScore((String)record.get(8));
		statistcs.setStartTime((String)record.get(9));
		statistcs.setStartNum((String)record.get(10));
	}
	
	public void  delOldData() throws Exception{
		//String sql ="select count(1) from T_GAME_STATISTCS where status=1";
		String sqlCode = "com.aspire.dotcard.basegame.StatistcsDealer.delOldData.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode,null);
		
		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ���׷���ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
			
			TransactionDB tdb = null;
			try{
				//sql="delete from T_GAME_STATISTCS where status=0";
				String sqlCode1 = "com.aspire.dotcard.basegame.StatistcsDealer.delOldData.DELETE";
				//sql="update T_GAME_STATISTCS set status=0 where status=1";
				String sqlCode2 = "com.aspire.dotcard.basegame.StatistcsDealer.delOldData.UPDATE";
				
				//����ģʽ
				tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(sqlCode1, null);
				tdb.executeBySQLCode(sqlCode2, null);
				tdb.commit();
			}catch(DAOException e){
				LOG.error(e);
			}finally{
				if(tdb!=null){
					tdb.close();
				}
			}
				
			
		}			
	}

	public void prepareData() throws Exception
	{
		//����TXTǰ��������T_GAME_STATISTCS���������ݡ�
		GameSyncDAO.getInstance().clearGameStatistcs();
	}

	public void init(DataSyncConfig config) throws Exception
	{

	}


}
