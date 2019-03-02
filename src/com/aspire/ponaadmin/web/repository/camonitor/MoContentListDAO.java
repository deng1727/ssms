/**
 * SSMS
 * com.aspire.ponaadmin.web.repository.camonitor MoContentListDAO.java
 * Apr 22, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.repository.camonitor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke
 *
 */
public class MoContentListDAO
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(MoContentListDAO.class);

	private static MoContentListDAO instance = new MoContentListDAO();
	private MoContentListDAO(){}
	public static MoContentListDAO getInstance(){
		return instance;
	}
	
	/**
	 * 根据机型名称查找当前货架下的机型货架
	 * @param categoryID
	 * @param deviceName
	 * @return
	 * @throws DAOException 
	 */
	public String getDeviceCategoryByDeviceName(String categoryID,String deviceName) throws DAOException{
		String newCategoryID = null;
		if (logger.isDebugEnabled())
		{
			logger.debug("getDeviceCategoryByDeviceName(" + categoryID + deviceName +") is beginning ....");
		}
	String sqlCode = "MoContentListDAO.getDeviceCategoryByDeviceName().SELECT";
	String devicen = deviceName.replaceAll(" ","");
		Object[] paras = {categoryID,devicen};
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			            if(rs.next())
			            {
			            	newCategoryID = rs.getString("id");
			            }
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return newCategoryID;
	}

}
