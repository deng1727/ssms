package com.aspire.dotcard.basegame;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;


public class StopDealer implements DataDealer
{
	private JLogger LOG=LoggerFactory.getLogger(StopDealer.class);

	private Map gamestopmap = new HashMap();
	
	public int dealDataRecrod(DataRecord record)
	{
		//LOG.info("开始处理信息。");
		try{
			GameStop gameStop = new GameStop();
			turnFields(record, gameStop);
			String sspid = gameStop.getServiceCode()+"|"+GameSyncDAO.getInstance().getMMProvinceId(gameStop.getProvinceId());
			if(gamestopmap.containsKey(sspid)){//已有做更新
				GameSyncDAO.getInstance().updateGameStop(gameStop);
				gamestopmap.put(sspid, "1");//修改为更新状态
			}else{//没有 做插入操作
			GameSyncDAO.getInstance().insertGameStop(gameStop);
			gamestopmap.put(sspid, "1");
			}
			return DataSyncConstants.SUCCESS_ADD;
		}catch(Exception e){
			LOG.error("处理基地游戏暂停出现异常",e);
			e.printStackTrace();
			return DataSyncConstants.FAILURE;
		}

	}
	
	public void  delOldData() throws Exception{
		// TODO Auto-generated method stub
		
		//String sql ="select count(1) from t_gamestop where status=1";
		//String sqlCode = "com.aspire.dotcard.basegame.StopDealer.delOldData.SELECT";
		//int count = BaseComicDAO.getInstance().count(sqlCode,null);
		
		 
		Set ts =	gamestopmap.entrySet();
		Iterator it =	ts.iterator();
		List  delList = new ArrayList();
		while(it.hasNext()){
			Entry ey = (Entry) it.next();
			String status = (String)ey.getValue();
			 if(status.equals("0")){//删除状态
				// String keys = (String) ey.getKey();
				// String 
				 delList.add(ey.getKey());
			 }
		}
		
		if(delList.size()<(gamestopmap.size()-1)){//删除没有被更新的数据，
			
			for(int i =0;i<delList.size();i++){
				try{
				String keys = (String)delList.get(i);
				String [] spid = keys.split("[|]");
				 //spid[1] =  GameSyncDAO.getInstance().getMMProvinceId(spid[1]);
				
				//delete from t_gamestop t where  t.servicecode=? and t.mmprovinceid=?
				String sqlCode1 = "com.aspire.dotcard.basegame.StopDealer.delOldData.DELETE";
				DB.getInstance().executeBySQLCode(sqlCode1, spid);
				}catch(DAOException e){
					LOG.error(e);
					e.printStackTrace();
				}
			}
			
			//TransactionDB tdb = null;
			
				//sql="delete from t_gamestop where status=0";
				//String sqlCode1 = "com.aspire.dotcard.basegame.StopDealer.delOldData.DELETE";
				//sql="update t_gamestop set status=0 where status=1";
				//String sqlCode2 = "com.aspire.dotcard.basegame.StopDealer.delOldData.UPDATE";
				
				//事务模式
				/*tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(sqlCode1, null);
				tdb.executeBySQLCode(sqlCode2, null);
				tdb.commit();*/
				//DB.getInstance().executeBySQLCode(sqlText, null);

				//非事务模式
//				//sql="delete from t_gamestop where status=0";
//				String sqlCode1 = "com.aspire.dotcard.basegame.StopDealer.delOldData.DELETE";
//				//sql="update t_gamestop set status=0 where status=1";
//				String sqlCode2 = "com.aspire.dotcard.basegame.StopDealer.delOldData.UPDATE";
//				BaseComicDAO.getInstance().executeBySQLCode(sqlCode1,null);
//				BaseComicDAO.getInstance().executeBySQLCode(sqlCode2,null);
			
			}else{
				LOG.error("删除数据量过大");
			}
				
			
		//}
	}
	
	private void turnFields(DataRecord record,
			GameStop gameStop) {
		// TODO Auto-generated method stub
		gameStop.setServiceCode((String)record.get(1));
		gameStop.setPkgid((String)record.get(2));
		gameStop.setContentCode((String)record.get(3));
		gameStop.setIsStop((String)record.get(4));
		gameStop.setProvinceId((String)record.get(5));
		gameStop.setProvinceName((String)record.get(6));
		gameStop.setStopTime((String)record.get(7));
		gameStop.setOperateType((String)record.get(8));
		
	}

	/**
	 * 准备工作
	 */
	public void prepareData() throws Exception
	{
		// alter table T_GAMESTOP drop constraint T_GAMESTOP_KEY cascade
		//String sqlCode = "com.aspire.dotcard.basegame.StopDealer.prepareData";
		//select t.servicecode||'|'||t.mmprovinceid from t_gamestop t
		String sqlCode = "com.aspire.dotcard.basegame.StopDealer.prepareDataSelect";
		ResultSet rs = null;
		try
		{
			 rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next()){
				String spid = rs.getString(1);
				gamestopmap.put(spid, "0");
			}
		}
		catch (DAOException e)
		{
			LOG.error(e);
		}finally
		{
			DB.close(rs);
		}
	}

	/**
	 * 收尾工作
	 */
	public void clearDirtyData()
	{
		gamestopmap = new HashMap();
		// delete from T_GAMESTOP v  where v.rowid > (select min(vt.rowid) from T_GAMESTOP vt  where vt.servicecode=v.servicecode and vt.mmprovinceid = v.mmprovinceid)
		/*String sqlText = "com.aspire.dotcard.basegame.StopDealer.clearDirtyData.del";
		try
		{
			DB.getInstance().executeBySQLCode(sqlText, null);
		}
		catch (DAOException e)
		{
			LOG.error("删除T_GAMESTOP表中多余数据时发生错误！！！", e);
		}*/
		
		// alter table T_GAMESTOP add constraint T_GAMESTOP_key primary key (SERVICECODE, MMPROVINCEID)
		/*String sqlCode = "com.aspire.dotcard.basegame.StopDealer.clearDirtyData";
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e)
		{
			LOG.error(e);
		}*/
	}

	
	public void init(DataSyncConfig config) throws Exception
	{

	}
}
