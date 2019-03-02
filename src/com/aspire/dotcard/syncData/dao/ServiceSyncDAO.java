package com.aspire.dotcard.syncData.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ServiceSyncDAO
{
	 /**
     * 日志引用
     */
    JLogger logger = LoggerFactory.getLogger(ServiceSyncDAO.class);

    private static ServiceSyncDAO dao = new ServiceSyncDAO();
    
    public static ServiceSyncDAO getInstance()
    {
    	return dao;
    }
    
   
    /**
     * 同步单个资费信息
     * @param icpservId
     * @throws DAOException
     */
    public void syncOneRecord(String icpservId)throws DAOException
    {
        String sqlCode = "SyncService.ServiceSyncDAO.syncOneRecord().UPDATE";
        Object paras[]= {icpservId};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }
    /**
     * 获取当前所有变更的资费
     * @param lastSyncDate
     * @param curDate
     * @return
     * @throws DAOException
     */
    public List getFeeChangedServices(Date lastSyncDate,Date curDate)throws DAOException
    {
        String sqlCode = "SyncService.ServiceSyncDAO.getFeeChangedServices().SELECT";
        Object paras[]= {new Timestamp(lastSyncDate.getTime()),new Timestamp(curDate.getTime())};
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        List list = new ArrayList();
        try
		{
			while (rs.next())
			{
				list.add(rs.getString("icpservid"));
			}
            
        }
        catch (SQLException e)
        {
            throw new DAOException("查询资费变更信息出错",e);
        }finally{
        	//add by tungke for close
        	DB.close(rs);
        	
        }
        // 将各个ContentTmp放入list中返回
        return list;
    }
    /**
     * 第一次更新所有的价格
     * @return
     */
    public java.util.Date firstUpdateAllMobilePrice()throws DAOException
    {
    	java.util.Date date=new java.util.Date();
        String sqlCode = "SyncService.ServiceSyncDAO.firstUpdateAllMobilePrice().UPDATE";
        //Object paras[]= {new Timestamp(date.getTime())};
        
        DB.getInstance().executeBySQLCode(sqlCode, null);
    	return date;
    }
    
    
}
