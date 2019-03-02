package com.aspire.ponaadmin.web.music139;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class DbTransaction {
	protected Connection conn;

	/**
	 * 日志
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(DbTransaction.class);

	public void doInTransaction(ITransactionHandler handler) {
		try {
			conn.setAutoCommit(false);
			handler.doInTransaction(conn);
			conn.commit();
		} catch (Exception e) {

		}
	}

	public void executeTransaction(List listStatement) {
		PreparedStatement[] ps = new PreparedStatement[listStatement.size()];
		try {
			conn.setAutoCommit(false);
			StatementVo vo = null;
			PreparedStatement p = null;
			boolean batch = listStatement.size() > 100 ? true : false;
			for (int i = 0, j = listStatement.size(); i < j; i++) {
				vo = (StatementVo) listStatement.get(i);
				p = conn.prepareStatement(vo.getSql());
				ps[i] = p;
				if (vo.getParams() != null && vo.getParams().length != 0) {
					for (int m = 0, n = vo.getParams().length; m < n; m++) {
						p.setObject(m + 1, vo.getParams()[m]);
					}
				}
				if (batch) {
					p.addBatch();
				} else {
					p.execute();
				}
			}
			if (batch) {
				p.executeBatch();
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			for (int i = 0, j = listStatement.size(); i < j; i++) {
				close(ps[i]);
			}
		}
	}

	public static int getseqValue(Connection conn, String sequenceName) {
		int svalue = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select " + sequenceName + ".nextval from dual";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				svalue = rs.getInt(1);
			}
		} catch (SQLException ex) {
			LOG.error(ex);
		} finally {
			releaseDBResource(conn, stmt, rs);
		}
		return svalue;
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 *            Connection，数据库链接
	 * @param statement
	 *            PreparedStatement，statement执行器
	 * @param rs
	 *            ResultSet，记录集
	 */
	public static void releaseDBResource(Connection conn, Statement statement,
			ResultSet rs) {
		close(rs);
		close(statement);
		close(conn);

	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {
				LOG.error(ex);
			}
		}
	}

	/**
	 * 关闭一个不使用的数据集
	 * 
	 * @param rs
	 *            ResultSet,数据集
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(ex);
			}
		}
	}

	/**
	 * 关闭一个不使用的数据库链接
	 * 
	 * @param conn
	 *            Connection,数据库链接
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				LOG.error(ex);
			}
		}
	}
}
