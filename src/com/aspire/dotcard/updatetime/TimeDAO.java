package com.aspire.dotcard.updatetime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.system.Config;

public class TimeDAO {
	

	/**
	 * 日志引用
	 */
	JLogger logger = LoggerFactory.getLogger(TimeDAO.class);
	
	
	private static TimeDAO dao = new TimeDAO();

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static TimeDAO getInstance() {

		return dao;
	}

	/**
	 * 支持事务的数据库操作器，如果为空表示是非事务类型的操作
	 */
	private TransactionDB transactionDB;

	/**
	 * 获取事务类型TransactionDB的实例 如果已经指定了，用已经指定的。如果没有指定，自己创建一个，注意自己创建的直接用不支持事务类型的即可
	 * 
	 * @return TransactionDB
	 */
	private TransactionDB getTransactionDB() {

		if (this.transactionDB != null) {
			return this.transactionDB;
		}
		return TransactionDB.getInstance();
	}

	/**
	 * 获取事务类型的DAO实例
	 * 
	 * @return AwardDAO
	 */

	public static TimeDAO getTransactionInstance(TransactionDB transactionDB) {

		TimeDAO dao = new TimeDAO();
		dao.transactionDB = transactionDB;
		return dao;
	}

	
 	/**
 	 *   从t_syn_result表中获取到contentid
 	 * 	2016-11-14 dengshaobo 
 	 */
    public List<String> checkTSynResult (){
    	Connection conn = null ;
    	PreparedStatement ps = null ;
    	Date day = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String dateString = sdf.format(day);
    	Object[] paras = {dateString,"1","2"};
    	logger.debug("paras=====================" + paras);
//    	String sqlCode = "SyncData.DataSyncDAO.checkTSynResult().SELECT";
    	String sql = "select contentid from t_syn_result where substr(syntime,1,10) = '" + paras[0]+ "' and ( syntype = " + paras[1] + " or syntype = "+ paras[2] + ")";
    	System.out.println("sql==================================" + sql);
    	ResultSet rs = null;
    	List<String> list = new ArrayList<String>();
    	try {
//    		rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
//    		logger.debug("sqlcode=====================" + sqlCode);
//    		while(rs.next() && rs !=null  ){
//    			list.add(rs.getString("contentid"));
    		
    		conn = DB.getInstance().getConnection();
    		ps = conn.prepareStatement(sql);
    		rs = ps.executeQuery();
    		while(rs.next()){
    			list.add(rs.getString("contentid"));
    		}
		} catch (Exception e) {
			
			logger.error("查询数据库出错" + e);
		}finally{
			DB.close(conn);
			DB.close(ps);
			DB.close(rs);
		
		}
    	logger.debug("list=====================" + list);
    	return list ;
    	
    }
    /**
     * 通过contentid字段把T_CLMS_CONTENTTAG表中的opdate更新成当前时间
     * @param list 从t_syn_result获取到的contentid
     */
    
    public void updateTClmsContentTag(List<String> list){
    	Connection conn = null ;
    	PreparedStatement ps = null ;	
    	try {
    		for (int i = 0; i < list.size(); i++) {
    			String contentid = list.get(i);
    			String sql = "update T_CLMS_CONTENTTAG set opdate = sysdate where contentid = '" + contentid +"'" ;
    			System.out.println("sql====================="+sql);
    			logger.debug("sql===================="+ sql);
    			conn = DB.getInstance().getConnection();
    			ps = conn.prepareStatement(sql);
    			ps.execute();
    		}
		} catch (Exception e) {
			
			logger.error("数据库链接错误"+ e);
			
			
		}finally{
			if(conn!=null){
				try {
					DB.close(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					DB.close(ps);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
 
}
