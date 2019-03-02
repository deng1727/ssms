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
     * �洢��־��ʵ�����
     */
    protected static JLogger logger = LoggerFactory.getLogger(AuditDetailsDao.class);
    
    /**
     * singletonģʽ��ʵ��
     */
    private static AuditDetailsDao instance = new AuditDetailsDao();
    
    /**
     * ���췽������singletonʵ������
     */
    private AuditDetailsDao(){
    }
    
    /**
     * ��ȡʵ���ķ���
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
            logger.debug("��ѯ������������ܹ�ϵ�б����",e);
            throw new DAOException("��ѯ������������ܹ�ϵ�б����",e);
        }
    }
    
    public void toAudit(String[] categoryId,String flag){
    	 String sqlCode = "com.aspire.ponaadmin.web.auditManagement.dao.AuditDetailsDao.toAudit";
    	// �����������
	        TransactionDB tdb = null;
	        try
	        {
	            tdb = TransactionDB.getTransactionInstance();
	            for(int i = 0;i < categoryId.length;i++){
	            	String[] strs = categoryId[i].split("/");
	            	tdb.executeBySQLCode(sqlCode, new Object[]{flag,strs[0],strs[1]});
	            }
	            // �ύ�������
	            tdb.commit();
	        }
	        catch (Exception e)
	        {
	            // ִ�лع�
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
