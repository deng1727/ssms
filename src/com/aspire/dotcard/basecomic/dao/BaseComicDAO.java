package com.aspire.dotcard.basecomic.dao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;

public class BaseComicDAO {
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseComicDAO.class);
	/**
	 * singleton模式的实例
	 */
	private static BaseComicDAO instance = new BaseComicDAO();

	/**
	 * 构造方法，由singleton模式调用
	 */
	private BaseComicDAO() {
	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static BaseComicDAO getInstance(){
		return instance;
	}


	public int execueSql(String sql, String[] para) throws BOException {
		// TODO Auto-generated method stub
		try {
			return DB.getInstance().execute(sql, para);
		} catch (DAOException e) {
			logger.error("数据库操作失败" + e);
			throw new BOException("数据库操作失败", e);
		}
		
	}
	public int executeBySQLCode(String sqlCode, String[] para) throws BOException {
		// TODO Auto-generated method stub
		try {
			return DB.getInstance().executeBySQLCode(sqlCode, para);
		} catch (DAOException e) {
			logger.error("数据库操作失败" + e);
			try {
				logger.error("出错的执行语句："+getDebugSql(SQLCode.getInstance().getSQLStatement(sqlCode),para));
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				logger.error(e1);
			}
			throw new BOException("数据库操作失败", e);
		}
		
	}
	
	private  String getDebugSql(String sql,String[] para){
		if(para!=null){
			for(int i=0;i<para.length;i++){
				sql = sql.replaceFirst("\\?", "'"+para[i]+"'");
			}
		}
		return sql;
	}
	
	public int count(String sqlCode, String[] para) {
		int c = -1;
		try {
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, para);
			if (rs.next()) {
				c = rs.getInt(1);
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("count的SQL" + sqlCode, e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("count的SQL" + sqlCode, e);
		}
		return c;
	}
	
	public String queryOne(String sqlCode,String[] para){
		String val = null;
		try {
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode,para);
			if(rs.next()){
				val = rs.getString(1);
			}
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return val;
	}
	
	public Set getExistsAllKey(String tableName,String key) throws DAOException {
		// TODO Auto-generated method stub
		Set existsAllKey = new HashSet();
		if(key!=null||key.trim().length()!=0){
			String[] field = key.split("\\|");
			StringBuffer sb = new StringBuffer();
			sb.append("select distinct  ");
			for(int i=0;i<field.length;i++){
				sb.append(field[i]);
				if(i!=field.length-1){//未到最后一个。
					sb.append(",");
				}
				
			}
			sb.append(" from ").append(tableName);
			
			try {
				RowSet rs = DB.getInstance().query(sb.toString(), null);
				
				while (rs.next()){
					String temp = "";
					for(int i=0;i<field.length;i++){
						temp +=rs.getString(field[i]);
						if(i!=field.length-1){//未到最后一个。
							temp+="|";
						}
						
					}
					existsAllKey.add(temp);
				}
			} catch (SQLException e) {
				logger.error("数据库操作失败",e);
				throw new DAOException("数据库操作失败，" + e);
			}
		}
		return existsAllKey;
	}




}
