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
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryDAO.class);

    /**
     * singleton模式的实例
     */
    private static CategoryDAO instance = new CategoryDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryDAO(){
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryDAO getInstance()
    {
        return instance;
    }
    
    /**
	 * 提交审批
	 * @param tdb
	 * @param categoryId 货架编号
	 * @throws DAOException
	 */
	public void approvalCategory(TransactionDB tdb, String categoryId)
			throws DAOException {
		String sqlCode = "com.aspire.dotcard.baseVideo.dao.CategoryDAO.approvalCategory";

		try {
			tdb.executeBySQLCode(sqlCode, new Object[] { categoryId });
		} catch (DAOException e) {
			logger.error("根据货架ID审批货架时发生异常:", e);
			throw new DAOException("根据货架ID审批货架时发生异常:", e);
		}
	}
	 
	/**
	 * 视频货架审批表
	 * 
	 * @param tdb
	 * @param categoryId
	 *            货架编号ID
	 * @param status
	 *            审批状态
	 * @param operation
	 *            操作对象
	 * @param operator
	 *            操作人
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
			logger.error("更新视频货架审批表异常", e);
			throw new BOException("更新视频货架审批表异常");
		} catch (SQLException e) {
			logger.error("更新视频货架审批表异常", e);
			throw new BOException("更新视频货架审批表异常");
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
