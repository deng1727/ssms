package com.aspire.common.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.PersistenceConstants;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.common.persistence.util.ServiceLocator;
import com.aspire.ponaadmin.web.repository.persistency.db.LobExecutor;

public class DB {

	private static JLogger logger = LoggerFactory.getLogger(DB.class);

	// singleton模式

	private static DB instance = new DB();

	private DB() {

	}

	public static DB getInstance() {

		return instance;
	}

	public static final int MODE_JNDI = 0;

	public static final int MODE_JDBC_DIRECT = 1;

	private int mode = MODE_JNDI;

	public void setMode(int _mode) {

		if ((_mode == MODE_JNDI) || (_mode == MODE_JDBC_DIRECT)) {
			this.mode = _mode;
		}
	}

	/**
	 * 获取数据库链接方法。 根据mode的不同，可能是通过jndi获取，也可能是根据jdbc直接获取
	 * 
	 * @return Connection数据链接
	 */
	public Connection getConnection() throws DAOException {

		Connection conn = null;
		try {
			if (mode == MODE_JNDI) 
			{
				conn = ServiceLocator.getInstance().getDBConn();
			} 
			else 
			{
				Class.forName(PersistenceConstants.getJdbcOracleDriver());
				conn = java.sql.DriverManager.getConnection(
						PersistenceConstants.getJdbcOracleUrl(),
						PersistenceConstants.getJdbcOracleUsername(),
						PersistenceConstants.getJdbcOraclePassword());
			}
		} catch (SQLException e) {
			throw new DAOException("DB.getConnection Error" + e);
		} catch (Exception ex) {
			throw new DAOException("DB.getConnection Error" + ex);
		}
		return conn;
	}

	/**
	 * 通过sqlCode获取sql语句
	 * 
	 * @param sqlCode
	 *            String sqlCode
	 * @return String sql语句
	 */
	public String getSQLByCode(String sqlCode) throws DAOException {
		try {
			return SQLCode.getInstance().getSQLStatement(sqlCode);
		} catch (DataAccessException ex) {
			throw new DAOException(ex);
		}
	}

	/**
	 * 设置PreparedStatement的sql参数
	 * 
	 * @param statement
	 *            PreparedStatement
	 * @param paras
	 *            Object[]
	 * @throws SQLException
	 */
	private void setParas(PreparedStatement statement, Object[] paras)
			throws SQLException {

		if (paras != null) {
			StringBuffer sb = new StringBuffer();
			sb.append("sql paras is:[");
			for (int i = 0; i < paras.length; i++) {
				if (i > 0) {
					sb.append(",");
				}
				if (paras[i] instanceof byte[]) // 二进制BLOB数据处理有点特殊，单独处理
				{
					byte[] data = (byte[]) paras[i];
					ByteArrayInputStream bais = new ByteArrayInputStream(data);
					statement.setBinaryStream(i + 1, bais, data.length);
				} else {
					sb.append(paras[i]);
					if (paras[i] == null) {
						statement.setString(i + 1, null);
					} else {
						if (paras[i] instanceof String
								&& ((String) paras[i]).length() > 4000) {
							String temp = (String) paras[i];
							Reader reader = new StringReader(temp);
							statement.setCharacterStream(i + 1, reader, temp
									.length());
						} else {

							statement.setObject(i + 1, paras[i]);
						}
					}
				}

				// if(paras[i] instanceof String)
				// {
				//
				// }
				// else if(paras[i] instanceof Integer)
				// {
				//
				// }
				// else if(paras[i] instanceof Float)
				// {
				//
				// }
				// else if(paras[i] instanceof Boolean)
				// {
				//
				// }
				// else if(paras[i] instanceof Date)
				// {
				//
				// }
				// else
				// {
				//
				// }
			}
			sb.append("]");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
		}
	}

	/**
	 * 执行一个sql语句
	 * 
	 * @param sqlCode
	 *            String,sql语句对应的代码
	 * @param paras
	 *            Object[],sql语句需要的参数，如果没有就为null
	 * @return int,几条数据库记录被更新。
	 * @throws DAOException
	 */
	public int executeBySQLCode(String sqlCode, Object[] paras)
			throws DAOException {

		String sql = "";
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		} catch (Exception e) {
			throw new DAOException("get SQLCode failed.", e);
		}
		return this.execute(sql, paras);
	}
	
	/**
	 * 执行一个sql语句
	 * 
	 * @param sqlCode
	 *            String,sql语句对应的代码
	 * @param paras
	 *            Object[],sql语句需要的参数，如果没有就为null
	 * @return int,几条数据库记录被更新。
	 * @throws DAOException
	 */
	public int executeBySQLCodeWithClob(String sqlCode, Object[] paras,String sqlClobQuery,String sqlClobUpdate,String[] clobValue)
			throws DAOException {

		String sql = "";
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		} catch (Exception e) {
			throw new DAOException("get SQLCode failed.", e);
		}
//		return this.execute(sql, paras);
		
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			if (logger.isDebugEnabled()) {
				logger.debug("execute sql is:" + sql);
			}
			statement = conn.prepareStatement(sql);
			this.setParas(statement, paras);
			statement.executeUpdate();
			statement = conn.prepareStatement(sqlClobQuery);
			rs = statement.executeQuery();
			if(rs.next()){
				PreparedStatement pstmt=conn.prepareStatement(sqlClobUpdate);
				for (int i=0;i<clobValue.length;i++){
					java.sql.Clob clob = rs.getClob(i+1);
					java.io.Writer ww = clob.setCharacterStream(01);
					ww.write(clobValue[i]);
					ww.flush();
					ww.close();
					pstmt.setClob(i+1,clob);
				}
				pstmt.executeUpdate();
				pstmt.close();
			}
			conn.commit();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("execute failed.", e);
		} finally {
			// 释放资源
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	
	public int executeBySQLCodeWithClob(String sqlClobQuery,
			String sqlClobUpdate, String[] clobValue) throws DAOException {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(sqlClobQuery);
			rs = statement.executeQuery();
			if (rs.next()) {
				PreparedStatement pstmt = conn.prepareStatement(sqlClobUpdate);
				for (int i = 0; i < clobValue.length; i++) {
					java.sql.Clob clob = rs.getClob(i + 1);
					java.io.Writer ww = clob.setCharacterStream(01);
					ww.write(clobValue[i]);
					ww.flush();
					ww.close();
					pstmt.setClob(i + 1, clob);
				}
				pstmt.executeUpdate();
				pstmt.close();
			}
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("execute failed.", e);
		} finally {
			// 释放资源
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return result;
	}
	
	/**
	 * 执行一个sql语句
	 * 
	 * @param sql
	 *            String,sql语句
	 * @param paras
	 *            Object[],sql语句需要的参数，如果没有就为null
	 * @return int,几条数据库记录被更新。
	 * @throws DAOException
	 */
	public int execute(String sql, Object[] paras) throws DAOException {

		Connection conn = null;
		PreparedStatement statement = null;
		int result = 0;
		try {
			conn = this.getConnection();
			if (logger.isDebugEnabled()) {
				logger.debug("execute sql is:" + sql);
			}
			statement = conn.prepareStatement(sql);
			this.setParas(statement, paras);
			result = statement.executeUpdate();
		} catch (Exception e) {
			logger.equals(e);
			throw new DAOException("execute failed.", e);
		} finally {
			// 释放资源
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return result;
	}
	/**
	 * 执行一个sql语句
	 * 
	 * @param sqlCode
	 *            String,sql语句对应的代码
	 * @param paras
	 *            Object[],sql语句需要的参数，如果没有就为null
	 * @return ResultSet,查询到的结果集合
	 * @throws DAOException
	 */
	public RowSet queryBySQLCode(String sqlCode, Object[] paras)
			throws DAOException {

		String sql = "";
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		} catch (Exception e) {
			throw new DAOException("get SQLCode failed.", e);
		}
		return query(sql, paras);
	}

	/**
	 * 执行一个sql语句
	 * 
	 * @param sql
	 *            String,sql语句
	 * @param paras
	 *            Object[],sql语句需要的参数，如果没有就为null
	 * @return ResultSet,查询到的结果集合
	 * @throws DAOException
	 */
	public RowSet query(String sql, Object[] paras) throws DAOException {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		OracleCachedRowSet crs = null;
		try {
			conn = this.getConnection();
			if (logger.isDebugEnabled()) {
				logger.debug("query sql is:" + sql);
			}
			statement = conn.prepareStatement(sql);
			this.setParas(statement, paras);
			rs = statement.executeQuery();
			crs = new OracleCachedRowSet();
			crs.populate(rs);
			logger.debug("crs.size():" + crs.size());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("execute failed.", e);
		} finally {
			// 释放资源
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return crs;
	}

	
	/**
	 * 
	 *@desc 容错的批量sql处理（中途有某一条执行失败不影响下面执行）
	 *@author dongke
	 *May 31, 2011
	 * @param mutiSQLCode
	 * @param mutiParas
	 * @throws DAOException
	 */

	public void executeMutiBySQLCodeFaild(String[] mutiSQLCode, Object[][] mutiParas)
			throws DAOException {

		try {
			for (int i = 0; i < mutiSQLCode.length; i++) {
				mutiSQLCode[i] = SQLCode.getInstance().getSQLStatement(
						mutiSQLCode[i]);
			}
		} catch (Exception e) {
			throw new DAOException("get SQLCode failed.", e);
		}

		executeMutiFaild(mutiSQLCode, mutiParas);

	}

	/**
	 * 
	 *@desc 容错的批量sql处理（中途有某一条执行失败不影响下面执行）
	 *@author dongke
	 *May 31, 2011
	 * @param mutiSQL
	 * @param mutiParas
	 * @throws DAOException
	 */
	public void executeMutiFaild(String[] mutiSQL, Object[][] mutiParas)
			throws DAOException {

		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			String sql = null;
			Object[] paras = null;
			for (int i = 0; i < mutiSQL.length; i++)
			{
				sql = mutiSQL[i];
				try
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("execute sql is:" + sql);
					}
					statement = conn.prepareStatement(sql);
					if (mutiParas != null) // 兼容空的情况
					{
						paras = mutiParas[i];
						this.setParas(statement, paras);
					}
					statement.executeUpdate();

					// add by wfw
					// statement.close();
				} catch (Exception e)
				{
					logger.error("execute sql is error :" + sql);
				} finally
				{
					statement.close();
				}

			}
			conn.commit();
		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException sqle) {
				logger.error(sqle);
			}
			throw new DAOException("execute failed.", e);
		}
		// safl
		finally {
			// 释放资源
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
	/**
	 * 执行一个sql语句
	 * 
	 * @param sqlCode
	 *            String,sql语句对应的代码
	 * @param paras
	 *            Object[],sql语句需要的参数，如果没有就为null
	 * @return int,几条数据库记录被更新。
	 * @throws DAOException
	 */
	public int executeInsertBySQLCode(String sqlCode, Object[] paras)
			throws DAOException {

		String sql = "";
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		} catch (Exception e) {
			throw new DAOException("get SQLCode failed.", e);
		}
		return this.execute(sql, paras);
	}
	/**
	 * 执行一个sql语句
	 * 
	 * @param sqlCode
	 *            String,sql语句对应的代码
	 * @param paras
	 *            Object[],sql语句需要的参数，如果没有就为null
	 * @return int,几条数据库记录被更新。
	 * @throws DAOException
	 */
	public int executeInsertImBySQLCode(String sqlCode, Object[] paras)
			throws DAOException {

		String sql = "";
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		} catch (Exception e) {
			throw new DAOException("get SQLCode failed.", e);
		}
		return this.execute(sql, paras);
	}
	/**
	 * 执行一个sql语句
	 * 
	 * @param sqlCode
	 *            String,sql语句对应的代码
	 * @param paras
	 *            Object[],sql语句需要的参数，如果没有就为null
	 * @return int,几条数据库记录被更新。
	 * @throws DAOException
	 */
	public int executeInsertIm2BySQLCode(String sqlCode, Object[] paras)
			throws DAOException {

		String sql = "";
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		} catch (Exception e) {
			throw new DAOException("get SQLCode failed.", e);
		}
		return this.execute(sql, paras);
	}
	public void executeMutiBySQLCode(String[] mutiSQLCode, Object[][] mutiParas)
			throws DAOException {

		try {
			for (int i = 0; i < mutiSQLCode.length; i++) {
				mutiSQLCode[i] = SQLCode.getInstance().getSQLStatement(
						mutiSQLCode[i]);
			}
		} catch (Exception e) {
			throw new DAOException("get SQLCode failed.", e);
		}

		executeMuti(mutiSQLCode, mutiParas);

	}

	public void executeMuti(String[] mutiSQL, Object[][] mutiParas)
			throws DAOException {

		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			String sql = null;
			Object[] paras = null;
			for (int i = 0; i < mutiSQL.length; i++) {
				sql = mutiSQL[i];
				if (logger.isDebugEnabled()) {
					logger.debug("execute sql is:" + sql);
				}
				statement = conn.prepareStatement(sql);
				if (mutiParas != null) //兼容空的情况
				{
					paras = mutiParas[i];
					this.setParas(statement, paras);
				}
				statement.executeUpdate();

				// add by wfw
				statement.close();
			}
			conn.commit();
		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException sqle) {
				logger.error(sqle);
			}
			throw new DAOException("execute failed.", e);
		}
		// safl
		finally {
			// 释放资源
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 根据不同的批量参数执行一个sql：同一个sql语句但是参数不同。
	 * 
	 * @param sql
	 *            要执行的sql语句
	 * @param mutiParas
	 *            批量参数
	 * @throws com.aspire.ponaadmin.common.exception.DAOException
	 */
	public void executeBatch(String sql, Object[][] mutiParas)
			throws DAOException {

		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			if (logger.isDebugEnabled()) {
				logger.debug("execute sql is:" + sql);
			}
			Object[] paras = null;
			statement = conn.prepareStatement(sql);
			for (int i = 0; i < mutiParas.length; i++) {
				paras = mutiParas[i];
				this.setParas(statement, paras);
				// statement.executeUpdate() ;
				statement.addBatch();
			}
			statement.executeBatch();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException sqle) {
				logger.error(sqle);
			}
			throw new DAOException("execute failed.", e);
		} finally {
			// 释放资源
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 根据不同的批量参数执行一个sql：同一个sql语句但是参数不同。
	 * 
	 * @param sqlCode
	 *            要执行的sql语句对应的sqlCode代码
	 * @param mutiParas
	 *            批量参数
	 */
	public void executeBatchBySQLCode(String sqlCode, Object[][] mutiParas)
			throws DAOException {

		String sql = "";
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		} catch (Exception e) {
			throw new DAOException("get SQLCode failed.", e);
		}
		this.executeBatch(sql, mutiParas);
	}

	public static void close(Connection conn) {

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
			}
		}

	}

	public static void close(ResultSet rs, PreparedStatement pstmt,
			Connection conn) {

		close(rs);
		close(pstmt);
		close(conn);
	}

	public static void close(PreparedStatement pstmt, Connection conn) {

		close(pstmt);
		close(conn);
	}

	public static void commit(Connection conn) {

		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException ex) {
			}
		}
	}

	public static void setAutoCommit(Connection conn, boolean autoCommit) {

		if (conn != null) {
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
			}
		}
	}

	public static void roolback(Connection conn) {

		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
		}
	}

	public static void close(ResultSet rs, PreparedStatement pstmt) {

		close(rs);
		close(pstmt);
	}

	public static void close(ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
			}
		}

	}

	public static void close(PreparedStatement pstmt) {

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException ex) {
			}
		}

	}

	public static void close(Statement stmt) {

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
			}
		}

	}

	/**
	 * 
	 * 取出数据库序列
	 * 
	 * @param sname
	 *            序列名称
	 * @return 序列值
	 * @throws DAOException --
	 *             hucy --增加
	 */
	public static int getSeqValue(String sname) throws DAOException {
		Connection con = null;
		try {
			con = DB.getInstance().getConnection();
			return getSeqValue(sname, con);
		} catch (DAOException ex) {
			throw ex;
		} finally {
			DB.close(con);
		}
	}

	/**
	 * 
	 * 取出数据库序列
	 * 
	 * @param sname
	 *            序列名称
	 * @param con
	 *            数据库连接
	 * @return 序列值
	 * @throws DAOException --
	 *             hucy --增加
	 */
	public static int getSeqValue(String sname, Connection con)
			throws DAOException {

		int svalue = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select " + sname + ".nextval from dual";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				svalue = rs.getInt(1);
			}

		} catch (SQLException ex) {
			throw new DAOException("get sequence error :" + ex);
		} finally {
			DB.close(rs);
			DB.close(stmt);

		}

		return svalue;
	}

	/**
	 * 提供一个公共的方法给各个分页列表。返回的list中 list【0】＝返回记录总数。 list【1】＝cachedRowSetImpl 输入参数：
	 * countSql=查询记录总数的sql searchSql=组装的查询条件。 startNum=分页查询的起始页 endNum=分页查询的起始页
	 * recordcount=传入的记录总数。如果为0则需要查数据库。>0则需要查数据库获取总数。
	 * 如果countSql传入的是null或“”或“null”则自动构造获取总数的记录。
	 * paras[],如果构造的查询语句需要参数，则可以存于该变量中。 added by gaobb.20050627
	 * 
	 * @return List
	 * @param countSql
	 * @param searchSql
	 * @param startNum
	 * @param end
	 * @param paras
	 * @param recordCount
	 * @throws com.aspire.ponaadmin.common.exception.DAOException
	 */
	public List getAllSearchListForDiv(String countSql, String searchSql,
			int startNum, int end, Object[] paras, int recordCount)
			throws DAOException {
		List list = new ArrayList(2);
		RowSet temp_rs = null;
		if (recordCount > 0) // 传入的recordcount大于0。不用从数据库中获取。优化性能。
		{
			list.add(String.valueOf(recordCount));
			logger.debug("不需要重新查总记录数据。记录总数为：" + recordCount);
		} else // 查询进来，还没有记录总数目。需要重新获取。
		{

			if ((countSql == null) || "".equals(countSql.trim())
					|| "null".equalsIgnoreCase(countSql.trim())) {
				countSql = "select count(*) from " + searchSql;
				if (logger.isDebugEnabled()) {
					logger.debug("countSql is null and auto construct is=="
							+ countSql);
				}
			}
			try {
				temp_rs = this.query(countSql, paras);

				if (temp_rs.next()) {
					recordCount = temp_rs.getInt(1);
				}
				logger.debug("获取的记录总数目＝＝" + recordCount);
				list.add(String.valueOf(recordCount));
			} catch (Exception ex) {
				logger.error("获取的记录总数目出错!");
				temp_rs = null;
				throw new DAOException("获取的记录总数目出错!" + ex);
			}
		}
		/** *******以上是获取记录总数********************** */

		temp_rs = null; // 清空记录。
		try {
			temp_rs = this.query(searchSql, paras);
			list.add(temp_rs);
		} catch (Exception ex) {
			logger.error("获取的记录出错!");
			temp_rs = null;
			list = null;
			throw new DAOException("获取的记录出错!" + ex);
		}
		/** *******以上是获取记录对象********************** */
		return list;
	}

	/**
	 * @param rs
	 *            ResultSet
	 * @param fieldName
	 *            String
	 * @return String
	 * @throws com.aspire.ponaadmin.common.exception.DAOException
	 */
	public static String getClobValue(ResultSet rs, String fieldName)
			throws DAOException {
		java.io.Reader claimReader = null;
		try {
			// oracle.sql.CLOB claimClob = (oracle.sql.CLOB)
			// rs.getClob(fieldName) ;
			java.sql.Clob claimClob = null; //add by aiyan 2012-12-21 begin 我在做商品批量导入优化的时候搞的这个东东，呵呵。。。
			try{
				claimClob = rs.getClob(fieldName);
			}catch(Exception e){
				//logger.error("getClobValue->rs:"+rs);
				//logger.error("getClobValue->fieldName:"+fieldName);
				//logger.error("当CLOB的值为null的时候，这里容易报空指针，我这里容错一下，在WEBLOG8木有这问题的。");
			}                               //add by aiyan 2012-12-21 end  我在做商品批量导入优化的时候搞的这个东东，呵呵。。。
			if (claimClob != null) {
				claimReader = claimClob.getCharacterStream();
				StringBuffer claimBuffer = new StringBuffer("");
				int claimChar;
				while ((claimChar = claimReader.read()) >= 0) {
					claimBuffer.append((char) claimChar);
				}
				return claimBuffer.toString();
			} else {
				return null;
			}
		} catch (IOException ex) {
			throw new DAOException("获取Clob字段出错", ex);
		} catch (SQLException ex) {
			throw new DAOException("获取Clob字段出错", ex);
		} finally {
			if (claimReader != null) {
				try {
					claimReader.close();
				} catch (IOException ex1) {
				}
			}
		}

	}

	/**
	 * prepareParams
	 * 
	 * @param statement
	 * @param paras
	 * @throws java.sql.SQLException
	 */
	public void prepareParams(PreparedStatement statement, Object[] paras)
			throws SQLException {
		this.setParas(statement, paras);
	}

	public void executeMuti(List executorList) throws DAOException {

		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			String sql = null;
			Object[] paras = null;
			List lobes = new ArrayList();
			for (int i = 0; i < executorList.size(); i++) {
				Executor executor = (Executor) executorList.get(i);
				if (!(executor instanceof LobExecutor)) {
					sql = executor.getSql();
					paras = executor.getParas();
					if (logger.isDebugEnabled()) {
						logger.debug("execute sql is:" + sql);
					}
					statement = conn.prepareStatement(sql);
					this.setParas(statement, paras);
					statement.executeUpdate();

					// add by wfw
					statement.close();
				} else {
					LobExecutor lobe = (LobExecutor) executor;
					lobes.add(lobe);
				}
			}
			if (lobes.size() > 0) {// 最后执行更新大字段，确保在insert时能够select出结果
				for (int i = 0; i < lobes.size(); i++) {
					exeLob((LobExecutor) lobes.get(i), conn);
				}
			}
			conn.commit();
		} catch (Exception e) {
			logger.error(e);
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException sqle) {
				logger.error(sqle);
			}
			throw new DAOException("execute failed.", e);
		}
		// safl
		finally {
			DB.close(statement, conn);
		}
	}

	private void exeLob(LobExecutor lobe, Connection conn) {
		PreparedStatement upLobQuery = null;
		PreparedStatement emptyLobsPrs = null;
		ResultSet rs = null;
		try {
			// 清空原有的clob，
			// 此处亦可，预防由null clob
			emptyLobsPrs = conn.prepareStatement(lobe.getEmptyClobSql());
			emptyLobsPrs.setString(1, lobe.getKey());
			emptyLobsPrs.executeUpdate();
			// 查询clob，并更新
			upLobQuery = conn.prepareStatement(lobe.getQueryLobSql());
			upLobQuery.setString(1, lobe.getKey());

			rs = upLobQuery.executeQuery();
			ResultSetMetaData rsm = rs.getMetaData();
			int colums = rsm.getColumnCount();
			while (rs.next()) {// only on row
				for (int n = 0; n < colums; n++) {
					if (lobe.getText(n) == null || "".equals(lobe.getText(n))) {
						continue;
					}
					int type = rs.getMetaData().getColumnType(n + 1);
					if (type == Types.CLOB) {
						Clob c = rs.getClob(n + 1);
						String temp = lobe.getText(n);
						Writer w = c.setCharacterStream(0l);
						w.write(temp);
						w.flush();
						w.close();
					}
				}
			}
		} catch (SQLException e) {
			logger.debug(e);
		} catch (IOException e) {
			logger.debug(e);
		} finally {
			DB.close(rs);
			DB.close(upLobQuery);
			DB.close(emptyLobsPrs);
		}
	}

	/**
	 * @param rs
	 *            ResultSet
	 * @param fieldName
	 *            String
	 * @return String
	 * @throws com.aspire.ponaadmin.common.exception.DAOException
	 */
	public static byte[] getBlobValue(ResultSet rs, String fieldName)
			throws DAOException {
		byte[] content = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			// oracle.sql.CLOB claimClob = (oracle.sql.CLOB)
			// rs.getClob(fieldName) ;
			Blob blob = rs.getBlob(fieldName);
			if (blob != null) {
				in = new BufferedInputStream(blob.getBinaryStream());
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				out = new BufferedOutputStream(bout);
				byte[] buf = new byte[1024];
				while (true) {
					int count = in.read(buf);
					if (count <= 0) {
						break;
					}
					out.write(buf, 0, count);
				}
				out.flush();
				content = bout.toByteArray();
			}
		} catch (IOException ex) {
			throw new DAOException("获取Blob字段出错", ex);
		} catch (SQLException ex) {
			throw new DAOException("获取Blob字段出错", ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex1) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException ex1) {
				}
			}
		}
		return content;
	}

}
