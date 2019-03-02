package com.aspire.ponaadmin.web.auditManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.auditManagement.vo.AuditDetailsVO;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class AuditDetailsDao {
	
	/**
     * 存储日志的实体对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(AuditDetailsDao.class);
    
    /**
     * singleton模式的实例
     */
    private static AuditDetailsDao instance = new AuditDetailsDao();
    
    /**
     * 构造方法，由singleton实例调用
     */
    private AuditDetailsDao(){
    }
    
    /**
     * 获取实例的方法
     */
    public static AuditDetailsDao getInstance(){
        return instance;
    }
    
    private class AuditDetailsPageVo implements PageVOInterface{

        @Override
        public Object createObject()
        {
            return new AuditDetailsVO();
        }

        @Override
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
        	AuditDetailsVO vo = (AuditDetailsVO)content;
            vo.setContentId(rs.getString("contentid"));
            vo.setCateName(rs.getString("catename"));
            vo.setSpName(rs.getString("spname"));
            vo.setName(rs.getString("name"));
            vo.setLoadDate(rs.getString("loaddate"));
            vo.setMarketDate(rs.getString("marketdate"));
            vo.setRefNodeId(rs.getString("refnodeid"));
            vo.setCategoryId(rs.getString("categoryid"));
        }
        
    }
    
    public void auditDetails(PageResult page,String categoryId) throws DAOException{
        String sqlCode = "com.aspire.ponaadmin.web.auditManagement.dao.AuditDetailsDao.auditDetails";
        String sql = null;
        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            page.excute(sql, new Object[]{categoryId}, new AuditDetailsPageVo());
        }
        catch (DataAccessException e)
        {
            logger.debug("查询渠道商与根货架关系列表出错！",e);
            throw new DAOException("查询渠道商与根货架关系列表出错！",e);
        }
    }
    
    public void toAudit(String[] categoryId,String flag){
    	 String sqlCode = "com.aspire.ponaadmin.web.auditManagement.dao.AuditDetailsDao.toAudit";
    	// 进行事务操作
	        TransactionDB tdb = null;
	        try
	        {
	            tdb = TransactionDB.getTransactionInstance();
	            for(int i = 0;i < categoryId.length;i++){
	            	String[] strs = categoryId[i].split("/");
	            	tdb.executeBySQLCode(sqlCode, new Object[]{flag,strs[0],strs[1]});
	            }
	            // 提交事务操作
	            tdb.commit();
	        }
	        catch (Exception e)
	        {
	            // 执行回滚
	            tdb.rollback();
	        }
	        finally
	        {
	            if (tdb != null)
	            {
	                tdb.close();
	            }
	        }
    	 
    }

}
