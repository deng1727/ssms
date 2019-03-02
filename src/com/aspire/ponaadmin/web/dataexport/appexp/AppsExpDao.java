package com.aspire.ponaadmin.web.dataexport.appexp;

import java.sql.SQLException;
import java.util.Date;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.util.SQLCode;

public class AppsExpDao {
	private DB db = DB.getInstance();
	protected static JLogger log = LoggerFactory.getLogger(AppsExpDao.class);

	public static final String SQL_ALL = "appsexport.allexport";

	public static final String SQL_DAY = "appsexport.dayexport";

	public RowSet queryAppsAll() throws DAOException {
		return db.queryBySQLCode(SQL_ALL, null);

	}

	public RowSet queryAppsDay(String startDate, String endDate)
			throws DAOException {
		return db.queryBySQLCode(SQL_DAY, new String[] { startDate, endDate });
	}

	public long queryRecords() throws DAOException, SQLException {
		RowSet rs = db.query("select count(*) from T_R_GCONTENT t where t.contentid like '300000%'", null);
		while (rs.next()) {
			return rs.getLong(1);
		}
		return 0;
	}

	public RowSet queryPageRecord(Long s, Long e) throws DAOException {
		StringBuffer sb = new StringBuffer(
				"SELECT * FROM (SELECT A.*, ROWNUM RN FROM (").append(SQL_ALL)
				.append("A WHERE ROWNUM <= ?) WHERE RN >= ?");
		return db.query(sb.toString(), new Long[] { e, s });

	}
}
