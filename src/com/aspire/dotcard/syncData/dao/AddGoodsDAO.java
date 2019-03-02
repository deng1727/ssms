package com.aspire.dotcard.syncData.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class AddGoodsDAO {
	/**
     * 日志引用
     */
    JLogger logger = LoggerFactory.getLogger(AddGoodsDAO.class);

    private static AddGoodsDAO dao = new AddGoodsDAO();
    
    public static AddGoodsDAO getInstance()
    {
    	return dao;
    }
    public List<String> getCategoryId(){
    	if (logger.isDebugEnabled()) {
			logger.debug("getCategoryId() in...........");
		}
    	ResultSet rs = null;
    	String categoryid="";
    	List<String> list = new ArrayList<String>();
    	String sqlCode = "com.aspire.dotcard.syncData.dao.AddGoodsDAO.getCategoryId().select";
    	try {
    		
    		rs = DB.getInstance().queryBySQLCode(sqlCode, null);
    		
    		while(rs.next()) {
    			categoryid = rs.getString("CATEGORYID");
				list.add(categoryid);
			}
    		
		} catch (Exception e) {
			logger.debug("查询categoryid出错",e);
		}finally{
			DB.close(rs);
		}
    	return list ;
    }
}
