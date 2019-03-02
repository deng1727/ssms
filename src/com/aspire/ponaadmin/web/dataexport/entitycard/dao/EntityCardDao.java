package com.aspire.ponaadmin.web.dataexport.entitycard.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.entitycard.EntityCardConfig;

public class EntityCardDao {
	private static JLogger logger = LoggerFactory.getLogger(EntityCardDao.class);
	
	private static EntityCardDao dao = new EntityCardDao();
	
	public static EntityCardDao getInstance(){
		return dao;
	}
	
	/**
	 * 查询所有的供应商信息
	 * @return
	 * @throws DAOException
	 */
	public List getAllAPData()throws DAOException{
		logger.debug("EntityCardDao.getAllAPData begin!");
		ResultSet rs = null;
		String sqlCode = "dataexport.entitycard.EntityCardDao.getAllAPData";
		List data = new ArrayList();
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			StringBuffer sb = null;
			while (rs.next()){
				sb = new StringBuffer();
				sb.append(rs.getString("COMPANYCODE"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("COMPANYNAME"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("ICPTYPE"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("opflag"));				
				data.add(sb.toString());
			}
		}catch (Exception e){
			logger.error("查询WWW最新信息数据出错,"+e);
            throw new DAOException("查询WWW最新信息数据出错", e);			
		}finally{
			DB.close(rs);
		}
		logger.debug("EntityCardDao.getAllAPData end!");
		return data;
	}
	
	/**
	 * 查询所有产品信息，只限于下载计费方式的MM免费应用（即不包含下载计费的收费业务，先体验后付费业务、包月业务）
	 * @return
	 * @throws DAOException
	 */
	public List getAllAPOperData()throws DAOException{
		logger.debug("EntityCardDao.getAllAPOperData begin!");
		ResultSet rs = null;
		String sqlCode = "dataexport.entitycard.EntityCardDao.getAllAPOperData";
		List data = new ArrayList();
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			StringBuffer sb = null;
			while (rs.next()){
				sb = new StringBuffer();
				sb.append(rs.getString("icpcode"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("contentid"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("name"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("icpservid"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("servname"));
				sb.append(EntityCardConfig.columnSep);				
				sb.append(rs.getString("type"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("billflag"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("fee"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("valid_date"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("expiredate"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("opflag"));				
				data.add(sb.toString());
			}
		}catch (Exception e){
			logger.error("查询WWW最新信息数据出错,"+e);
            throw new DAOException("查询WWW最新信息数据出错", e);			
		}finally{
			DB.close(rs);
		}
		logger.debug("EntityCardDao.getAllAPOperData end!");
		return data;
	}	
	
	/**
	 * 查询增量的供应商信息
	 * @return
	 * @throws DAOException
	 */
	public List getIncrementAPData()throws DAOException{
		logger.debug("EntityCardDao.getAllAPData begin!");
		ResultSet rs = null;
		String sqlCode = "dataexport.entitycard.EntityCardDao.getIncrementAPData";
		List data = new ArrayList();
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			StringBuffer sb = null;
			while (rs.next()){
				sb = new StringBuffer();
				sb.append(rs.getString("COMPANYCODE"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("COMPANYNAME"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("ICPTYPE"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("opflag"));				
				data.add(sb.toString());
			}
		}catch (Exception e){
			logger.error("查询WWW最新信息数据出错,"+e);
            throw new DAOException("查询WWW最新信息数据出错", e);			
		}finally{
			DB.close(rs);
		}
		logger.debug("EntityCardDao.getAllAPData end!");
		return data;
	}
	
	/**
	 * 查询增量产品信息，只限于下载计费方式的MM免费应用（即不包含下载计费的收费业务，先体验后付费业务、包月业务）
	 * @return
	 * @throws DAOException
	 */
	public List getIncrementAPOperData()throws DAOException{
		logger.debug("EntityCardDao.getAllAPOperData begin!");
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sqlCode = "dataexport.entitycard.EntityCardDao.getIncrementAPOperData";
		List data = new ArrayList();
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			StringBuffer sb = null;
			while (rs.next()){
				sb = new StringBuffer();
				sb.append(rs.getString("icpcode"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("contentid"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("name"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("icpservid"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("servname"));
				sb.append(EntityCardConfig.columnSep);				
				sb.append(rs.getString("type"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("billflag"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("fee"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("valid_date"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs.getString("expiredate"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs.getString("opflag"));				
				data.add(sb.toString());
			}
			sqlCode = "dataexport.entitycard.EntityCardDao.getDelAPOperData";
			rs1 = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs1.next()){
				sb = new StringBuffer();
				sb.append(rs1.getString("icpcode"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs1.getString("contentid"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs1.getString("name"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs1.getString("icpservid"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs1.getString("servname"));
				sb.append(EntityCardConfig.columnSep);				
				sb.append(rs1.getString("type"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs1.getString("billflag"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs1.getString("fee"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs1.getString("valid_date"));
				sb.append(EntityCardConfig.columnSep);	
				sb.append(rs1.getString("expiredate"));
				sb.append(EntityCardConfig.columnSep);
				sb.append(rs1.getString("opflag"));				
				data.add(sb.toString());
			}			
		}catch (Exception e){
			logger.error("查询WWW最新信息数据出错,"+e);
            throw new DAOException("查询WWW最新信息数据出错", e);			
		}finally{
			DB.close(rs);
			DB.close(rs1);
		}
		logger.debug("EntityCardDao.getAllAPOperData end!");
		return data;
	}	
}
