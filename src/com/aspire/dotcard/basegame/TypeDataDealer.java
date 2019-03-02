package com.aspire.dotcard.basegame;

import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

public class TypeDataDealer implements DataDealer
{
	private JLogger LOG=LoggerFactory.getLogger(TypeDataDealer.class);
	private Set oldKeyIds;

	public void clearDirtyData()
	{
		oldKeyIds=null;
	}

	public int dealDataRecrod(DataRecord record)
	{
		//LOG.info("开始处理信息。");
		try{
			GameType gameType = new GameType();
		turnFieldsToGameType(record, gameType);
		if(oldKeyIds.contains(gameType.getId())){
			GameSyncDAO.getInstance().updateGameType(gameType);
			oldKeyIds.remove(gameType.getId());
			return DataSyncConstants.SUCCESS_UPDATE;
		}else{
			GameSyncDAO.getInstance().insertGameType(gameType);
			return DataSyncConstants.SUCCESS_ADD;
		}
		}catch(Exception e){
			LOG.error("处理基地游戏分类出现异常",e);
			return DataSyncConstants.FAILURE;
		}

	}
	
	public void  delOldData() throws Exception{
		if(oldKeyIds.size()==0){
			return;
		}
		Object[] arr = oldKeyIds.toArray();
		int size = arr.length;
		int start=0;int end =0;
		String sql = DB.getInstance().getSQLByCode("com.aspire.dotcard.basegame.delOldType");
		do{
			end=end+100;
			if(end>size) end=size;
			
			StringBuffer sb = new StringBuffer();
			for(int i=start;i<end;i++){
				sb.append(",'").append(arr[i]).append("'");
			}
			if(sb.length()>0){
				try {
					String whereStr = " where id in ("+sb.substring(1)+")";
					GameSyncDAO.getInstance().delOldData(sql+whereStr);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					LOG.error("游戏分类删除旧数据出错！",e);
				}
			}
			
			start = end;
		}while(end<size);
	}

	private void turnFieldsToGameType(DataRecord record,
			GameType gameType) {
		// TODO Auto-generated method stub
		gameType.setId((String)record.get(1));
		gameType.setName((String)record.get(2));
		
	}

	public void prepareData() throws Exception
	{
		
		String sqlCode="com.aspire.dotcard.basegame.getAllGameTypeKeyId";
		oldKeyIds = GameSyncDAO.getInstance().getAllKeyId(sqlCode);
		
	}

	public void init(DataSyncConfig config) throws Exception
	{

	}


}
