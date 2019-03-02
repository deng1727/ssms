/**
 * <p>
 * 动漫支持平台数据操作类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 7, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.datasync.implement.comic;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

/**
 * @author dongke
 *
 */
public class ComicPlatFormDAO {

	
	
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(ComicPlatFormDAO.class);
	/**
	 * singleton模式的实例
	 */
	private static ComicPlatFormDAO instance = new ComicPlatFormDAO();

	/**
	 * 构造方法，由singleton模式调用
	 */
	private ComicPlatFormDAO()
	{
	}

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static ComicPlatFormDAO getInstance()
	{

		return instance;
	}
	
	/**
	 * 根据动漫支持平台ID查询该ID是否已存在
	 * 存在为true,不存在为false
	 * @param platFormID
	 * @return
	 * @throws DAOException 
	 */
	public boolean isComicPlatFormIDExist(String platFormID) throws DAOException{
		if(LOG.isDebugEnabled()){
			LOG.debug("检查动漫频道支持平台是否存在");	
		}
		ResultSet rs = null;
		boolean re = false ;
		Object[] paras = {platFormID };
		try {			
				rs = DB.getInstance().queryBySQLCode(
						"comic.platform.ComicPlatFormDAO.select().ISCOMICPLATFORMIDEXIST", paras);	
			// i = rs.getRow();
				if(rs != null && rs.next()){
					re = true;
					
				}
			} catch (DAOException e) {
				LOG.error(e);
				throw new DAOException("执行动漫频道支持平台表查询发生异常:", e);
			} catch (SQLException ex) {
			LOG.error(ex);
			throw new DAOException("执行动漫频道支持平台表查询获取行数发生异常:", ex);
		}finally
		{
			DB.close(rs);
		}		
		return re;
	}
	
	/**
	 * 添加新增动漫平台
	 * @param platFormID
	 * @return
	 */
	public  int addComicPlatForm(String platFormID){
		if(LOG.isDebugEnabled()){
			LOG.debug("添加动漫频道支持平台");	
		}
		String platForm = this.getPlatFormByID(platFormID);
		Object[] paras = {platFormID,platForm };
		int num = 0 ;
		try {
			 num = DB.getInstance().executeBySQLCode(
					"comic.platform.ComicPlatFormDAO.insert().ADDCOMICPLATFORM",
					paras);
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("新增动漫支持平台，platFormID="+platFormID,e);
			return DataSyncConstants.FAILURE_ADD;
		}
		if(num<1)
		{
			return DataSyncConstants.FAILURE_ADD;
		}else
		{
			return DataSyncConstants.SUCCESS_ADD;
		}
	}
	
	/**
	 * 删除所有动漫频道支持平台
	 * @return
	 */
	public int delAllComicPlatForm(){
		
		if (LOG.isDebugEnabled())
        {
			LOG.debug("delAllComicPlatForm");
        }
		String sqlCode="comic.platform.ComicPlatFormDAO.delete().ALL";
    	try
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		} catch (DAOException e)
		{
			LOG.error(e);
			return DataSyncConstants.FAILURE_DEL;
		}		
	    return DataSyncConstants.SUCCESS_DEL;

	}
	
	
	/**
	 * 根据动漫支持平台ID获取支持平台名称
	 * @param platFormID
	 * @return
	 */
	public String getPlatFormByID(String platFormID){
		if(platFormID != null && !platFormID.equals("")){
			if(platFormID.equals("100")){
				return "S602nd";				
			}else if(platFormID.equals("101")){
				return "S603rd";				
			}else if(platFormID.equals("102")){
				return "S605th";
			}else if(platFormID.equals("200")){
				return "WM";
			}else if(platFormID.equals("300")){
				return "Kjava";
			}else if(platFormID.equals("400")){	
				return "OMS";
			}else{
				return "";				
			}			
		}
		
		return null;
	}
	
	
}
