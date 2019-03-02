package com.aspire.dotcard.baseVideo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class CategoryDAO {
	
	/**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryDAO instance = new CategoryDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryDAO(){
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryDAO getInstance()
    {
        return instance;
    }
    
    /**
	 * �ύ����
	 * @param tdb
	 * @param categoryId ���ܱ��
	 * @throws DAOException
	 */
	public void approvalCategory(TransactionDB tdb, String categoryId)
			throws DAOException {
		String sqlCode = "com.aspire.dotcard.baseVideo.dao.CategoryDAO.approvalCategory";

		try {
			tdb.executeBySQLCode(sqlCode, new Object[] { categoryId });
		} catch (DAOException e) {
			logger.error("���ݻ���ID��������ʱ�����쳣:", e);
			throw new DAOException("���ݻ���ID��������ʱ�����쳣:", e);
		}
	}
	 
	/**
	 * ��Ƶ����������
	 * 
	 * @param tdb
	 * @param categoryId
	 *            ���ܱ��ID
	 * @param status
	 *            ����״̬
	 * @param operation
	 *            ��������
	 * @param operator
	 *            ������
	 * @throws BOException
	 */
	public void approvalCategory(TransactionDB tdb, String categoryId,
			String status, String operation, String operator)
			throws BOException {
		ResultSet rs = null;
		try {
			rs = DB.getInstance()
					.queryBySQLCode(
							"com.aspire.dotcard.baseVideo.dao.CategoryDAO.approvalCategory.SELECT",
							new Object[] { categoryId, operation });
			if (rs != null && rs.next()) {
				if ("2".equals(status)) {
					tdb.executeBySQLCode(
							"com.aspire.dotcard.baseVideo.dao.CategoryDAO.approvalCategory.UPDATE1",
							new Object[] { operator, categoryId, operation });
				} else {
					tdb.executeBySQLCode(
							"com.aspire.dotcard.baseVideo.dao.CategoryDAO.approvalCategory.UPDATE2",
							new Object[] { operator, categoryId, operation });
				}
			} else {
				if ("2".equals(status)) {
					tdb.executeBySQLCode(
							"com.aspire.dotcard.baseVideo.dao.CategoryDAO.approvalCategory.INSERT1",
							new Object[] { operator, categoryId, operation });
				} else {
					tdb.executeBySQLCode(
							"com.aspire.dotcard.baseVideo.dao.CategoryDAO.approvalCategory.INSERT2",
							new Object[] { operator, categoryId, operation });
				}
			}
		} catch (DAOException e) {
			logger.error("������Ƶ�����������쳣", e);
			throw new BOException("������Ƶ�����������쳣");
		} catch (SQLException e) {
			logger.error("������Ƶ�����������쳣", e);
			throw new BOException("������Ƶ�����������쳣");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}

		}
	}

}
