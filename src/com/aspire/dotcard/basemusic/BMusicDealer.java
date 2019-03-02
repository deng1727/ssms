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
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(BMusicDealer.class);
//	private static Category contentRoot;//用于缓存
//	private static Category musicRoot;//用于缓存
//	private static Category  categoryRoot;
	//private HashMap categoryList=new HashMap();//用于缓存
	/**
     * 分类为空的时候需要上架的货架分类
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

		// 为了确保系统不会出错，检验同一产品包ID是否存在于系统中
		
		// 新增：调用框架将内容添加到内容根分类下；将内容上架到对应的视频货架
		if ("1".equals(changetype))
		{
			addBaseMusic.put(musicId,bv);
//			String insertSqlCode = "datasync.implement.music.BMusicDealer.insertSqlCode";
//			String paras[] = {songname,singer,validity,musicId};
//			int result = 0;
			//1，检查是否存在，存在不新增，
//			try{
//				 进行事务调用
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
//				logger.error("新增音乐内容失败，contentID=" + musicId + "，系统可能已存在该内容"+e);
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
//				logger.error("新增音乐内容失败，contentID=" + music.getContentID() + "，系统已存在该内容");
//				return DataSyncConstants.FAILURE_ADD_EXIST;
//			}	
			
			//return DataSyncConstants.SUCCESS_ADD;
			//return result;
		}

		// 更新：调用框架对内容进行更新。
		else if ("2".equals((String) changetype))
		{
			changeBaseMusic.put(musicId,bv);
		
			//更新基地音乐内容数据
//			String updateSqlCode = "datasync.implement.music.BMusicDealer.updateSqlCode";
//			String paras[] = {songname,singer,validity,musicId};
//			int result = 0;
			//1，检查是否存在，存在不新增，
			//try{
//				result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode,paras);
//			}catch(DAOException e){
//				logger.error("更新音乐内容失败，contentID=" + musicId + "，"+e);
//				result = -1;
//			}
//			
			//return DataSyncConstants.SUCCESS_UPDATE;
		//	return result;
		}
		// 下线：调用框架对商品和内容进行下线。
		else if ("3".equals(changetype))
		{
			delBaseMusic.put(musicId,bv);
			//1 先删除基地音乐货架商品表中的数据
//			int result1 = 0;
//			int result2 = 0;
//			String delBMusicReferSQLCode = "datasync.implement.music.BMusicDealer.delBMusicReferSQLCode";
//			String delBMusicSQLCode = "datasync.implement.music.BMusicDealer.delBMusicSQLCode";
//			String paras1[] = {musicId};
//			result1 = BMusicDAO.getInstance().execueSqlCode(delBMusicReferSQLCode,paras1);
//			
//			result2 = BMusicDAO.getInstance().execueSqlCode(delBMusicSQLCode,paras1);
			//2 删除基地音乐内容数据
		//	logger.error("删除了"+result1+"音乐商品，对于音乐ID=" + musicId );
			//return DataSyncConstants.SUCCESS_DEL;
		//	return result2;
		}
		else
		{
			// 异常情况记录错误信息，不处理
			logger.error("contentID为" + musicId
					+ "，Changetype类型有误,Changetype=" + changetype);
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
