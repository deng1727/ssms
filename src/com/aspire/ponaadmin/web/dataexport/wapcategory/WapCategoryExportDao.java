package com.aspire.ponaadmin.web.dataexport.wapcategory;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;

public class WapCategoryExportDao {
	public static final String EXPORT_NULL_PARENTID = "wapcategoryexport.nullparentId";

	public static final String EXPORT_NOT_NULL_PARENTID = "wapcategoryexport.notnullparentId";

	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger LOG = LoggerFactory
			.getLogger(WapCategoryExportDao.class);

	public static RowSet queryFromRoot() throws DAOException {
		RowSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(EXPORT_NULL_PARENTID, null);
		} catch (DAOException e) {
			LOG.error(-214, "导出wap货架时数据库查询错误", e);
			throw e;
		}
		return rs;
	}

	public static RowSet querySpecifiedParentId(String[] parentIds)
			throws DAOException {
		RowSet rs = null;
		StringBuffer s = new StringBuffer(
				"select t.categoryid, t.name,t.parentcategoryid from t_r_category t connect by prior t.categoryid = t.parentcategoryid  start with t.parentcategoryid in(");
		for (int i = 0, j = parentIds.length; i < j; i++) {
			if (i == j - 1) {
				s.append("?");
			} else {
				s.append("?,");
			}
		}
		s.append(")");
		String temp = s.toString();
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("QUERY SQL =" + temp);
			}
			rs = DB.getInstance().query(temp, parentIds);
		} catch (DAOException e) {
			LOG.error(-214, "导出wap货架时数据库查询错误", e);
			throw e;
		}
		return rs;
	}
}
