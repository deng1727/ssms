package com.aspire.ponaadmin.web.comic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryEmailDAO {

	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(CategoryEmailDAO.class);

	private static CategoryEmailDAO dao = new CategoryEmailDAO();

	private CategoryEmailDAO() {
	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static CategoryEmailDAO getInstance() {
		return dao;
	}

	/**
	 * 获取邮箱地址
	 * 
	 * @param map
	 *            status:审批状态 0 提交审批 1 审批发布 ;categoryId:货架Id;operation:处理方式
	 * @return
	 * @throws DAOException
	 */
	public String getMailAddress(Map<String, Object> map) throws DAOException {
		ResultSet rs = null;
		try {
			if (Integer.parseInt(map.get("status").toString()) == 0) {
				rs = DB.getInstance()
						.queryBySQLCode(
								"com.aspire.ponaadmin.web.comic.dao.CategoryEmailDAO.getMailAddress2",
								new Object[] { map.get("operation") });
			} else {
				rs = DB.getInstance()
						.queryBySQLCode(
								"com.aspire.ponaadmin.web.comic.dao.CategoryEmailDAO.getMailAddress1",
								new Object[] { map.get("operation")  });
			}

			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (DAOException e) {
			logger.error("查询邮件地址表发生异常:", e);
			throw new DAOException("查询邮件地址表发生异常:", e);
		} catch (SQLException e) {
			logger.error("查询邮件地址表发生异常:", e);
			throw new DAOException("查询邮件地址表发生异常:", e);
		}
		return null;
	}

}
