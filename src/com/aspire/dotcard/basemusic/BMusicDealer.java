package com.aspire.dotcard.basemusic;



import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.db.TransactionDB;


public class BMusicDealer 
{
	/**
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(BMusicDealer.class);
//	private static Category contentRoot;//���ڻ���
//	private static Category musicRoot;//���ڻ���
//	private static Category  categoryRoot;
	//private HashMap categoryList=new HashMap();//���ڻ���
	/**
     * ����Ϊ�յ�ʱ����Ҫ�ϼܵĻ��ܷ���
     */
	private static  BMusicDealer  instance = new BMusicDealer();

	private BMusicDealer(){
		
	}
		public static BMusicDealer getInstance(){
			return instance;
		}

	
	public void dealDataRecrod(List record,HashMap addBaseMusic,HashMap changeBaseMusic,HashMap delBaseMusic) throws Exception
	{
		
		BMusicVO bv = new BMusicVO();
		
		String musicId = (String) record.get(1);
		bv.setMusicId(musicId);
		bv.setSongname((String) record.get(2));
		bv.setSinger((String) record.get(3));
		bv.setValidity((String) record.get(4));
		//String songname = (String) record.get(2);
		//String singer = (String) record.get(3);
		//String validity = (String) record.get(4);
		String changetype  = (String) record.get(5);
		
		//TransactionDB tdb = null;

		// Ϊ��ȷ��ϵͳ�����������ͬһ��Ʒ��ID�Ƿ������ϵͳ��
		
		// ���������ÿ�ܽ�������ӵ����ݸ������£��������ϼܵ���Ӧ����Ƶ����
		if ("1".equals(changetype))
		{
			addBaseMusic.put(musicId,bv);
//			String insertSqlCode = "datasync.implement.music.BMusicDealer.insertSqlCode";
//			String paras[] = {songname,singer,validity,musicId};
//			int result = 0;
			//1������Ƿ���ڣ����ڲ�������
//			try{
//				 �����������
//				 tdb = TransactionDB.getTransactionInstance();
//				BMusicDAO dao = BMusicDAO.getTransactionInstance(tdb);
//                
//				result = dao.execueSqlCode(insertSqlCode,paras);
				//System.out.println("count===="+this.getexecutedCount());
				//if(this.getexecutedCount() >= 200){
					
					//this.initexecutedCount();
					
				//}
//					tdb.commit();
				//this.executedCount ++;
//			}catch(DAOException e){
//				logger.error("������������ʧ�ܣ�contentID=" + musicId + "��ϵͳ�����Ѵ��ڸ�����"+e);
//				result = -1;
//			}finally
//            {
//                if (tdb != null)
//                {
//                	tdb.close();
//                }
//            }
//			if (old != null)
//			{
//				logger.error("������������ʧ�ܣ�contentID=" + music.getContentID() + "��ϵͳ�Ѵ��ڸ�����");
//				return DataSyncConstants.FAILURE_ADD_EXIST;
//			}	
			
			//return DataSyncConstants.SUCCESS_ADD;
			//return result;
		}

		// ���£����ÿ�ܶ����ݽ��и��¡�
		else if ("2".equals((String) changetype))
		{
			changeBaseMusic.put(musicId,bv);
		
			//���»���������������
//			String updateSqlCode = "datasync.implement.music.BMusicDealer.updateSqlCode";
//			String paras[] = {songname,singer,validity,musicId};
//			int result = 0;
			//1������Ƿ���ڣ����ڲ�������
			//try{
//				result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode,paras);
//			}catch(DAOException e){
//				logger.error("������������ʧ�ܣ�contentID=" + musicId + "��"+e);
//				result = -1;
//			}
//			
			//return DataSyncConstants.SUCCESS_UPDATE;
		//	return result;
		}
		// ���ߣ����ÿ�ܶ���Ʒ�����ݽ������ߡ�
		else if ("3".equals(changetype))
		{
			delBaseMusic.put(musicId,bv);
			//1 ��ɾ���������ֻ�����Ʒ���е�����
//			int result1 = 0;
//			int result2 = 0;
//			String delBMusicReferSQLCode = "datasync.implement.music.BMusicDealer.delBMusicReferSQLCode";
//			String delBMusicSQLCode = "datasync.implement.music.BMusicDealer.delBMusicSQLCode";
//			String paras1[] = {musicId};
//			result1 = BMusicDAO.getInstance().execueSqlCode(delBMusicReferSQLCode,paras1);
//			
//			result2 = BMusicDAO.getInstance().execueSqlCode(delBMusicSQLCode,paras1);
			//2 ɾ������������������
		//	logger.error("ɾ����"+result1+"������Ʒ����������ID=" + musicId );
			//return DataSyncConstants.SUCCESS_DEL;
		//	return result2;
		}
		else
		{
			// �쳣�����¼������Ϣ��������
			logger.error("contentIDΪ" + musicId
					+ "��Changetype��������,Changetype=" + changetype);
			//return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
		}
	}

	public void init(DataSyncConfig config) throws Exception
	{
		

	}

	public void clearDirtyData()
	{
		
	}

	public void prepareData()
	{
		
	}

}
