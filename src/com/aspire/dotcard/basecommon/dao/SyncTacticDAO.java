package com.aspire.dotcard.basecommon.dao;


import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecommon.vo.SyncTacticEntity;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class SyncTacticDAO {
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(SyncTacticDAO.class);
	/**
	 * singletonģʽ��ʵ��
	 */
	private static SyncTacticDAO instance = new SyncTacticDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private SyncTacticDAO() {
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static SyncTacticDAO getInstance(){
		return instance;
	}
	
	public List getAllBaseSyncTactic(){
		List entityList = new ArrayList();
		//String sql = "select * from t_sync_tactic_base";
		String sqlCode = "com.aspire.dotcard.basecommon.bo.getAllBaseSyncTactic";
		try {
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next()){
				SyncTacticEntity entity = new SyncTacticEntity();
				entity.setId(rs.getString("ID"));
				entity.setDelSql(rs.getString("DEL_SQL"));
				entity.setInsertSql(rs.getString("INSERT_SQL"));
				entity.setEffectivetime(rs.getDate("EFFECTIVETIME"));
				entity.setLuptime(rs.getDate("LUPTIME"));
				entity.setTimeConsuming(rs.getLong("TIME_CONSUMING"));
				entityList.add(entity);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("��ѯ�����Զ����¼�ͬ�����Գ���",e);
		}
			
		return entityList;
	}

	public void execute(SyncTacticEntity entity) throws BOException {
		// TODO Auto-generated method stub
		TransactionDB tdb = null;
		try{
			//����ģʽ
			long start = System.currentTimeMillis();
			tdb = TransactionDB.getTransactionInstance();
			tdb.execute(entity.getDelSql(), null);
			tdb.execute(entity.getInsertSql(), null);
			tdb.execute("update t_sync_tactic_base set luptime= sysdate,time_consuming=? where id = ?",new Object[]{(System.currentTimeMillis()-start)+"",entity.getId()});
			tdb.commit();
		}catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("�������¼�������̳���",e);
				throw new BOException("�������¼�������̳���",e);
		}finally{
			if(tdb!=null){
				tdb.close();
			}
		}
		
	}




}
