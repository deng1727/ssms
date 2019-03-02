package com.aspire.ponaadmin.web.db ;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.util.PersistenceConstants;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.common.persistence.util.ServiceLocator;

/**
 * <p>事务数据库操作类</p>
 * <p>Copyright (c) 2003-2006 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class TransactionDB implements TransactionContext
{
    /**
     * 日志
     */
    private static final JLogger LOG = LoggerFactory.getLogger(TransactionDB.class) ;

    /**
     * singleton模式
     */
    private static TransactionDB instance = new TransactionDB() ;

    /**
     * 事物场景下的链接
     */
    private Connection transactionConn = null ;

    /**
     * 私有构建器
     */
    private TransactionDB ()
    {

    }

    /**
     * 获取一个非事务场景的实例
     * @return TransactionDB
     */
    public static TransactionDB getInstance ()
    {
        return instance ;
    }

    /**
     * 获取一个事务场景的实例
     * @return TransactionDB
     * @throws DAOException
     */
    public static TransactionDB getTransactionInstance () throws DAOException
    {
        try
        {
            TransactionDB transactionDB = new TransactionDB();
            transactionDB.transactionConn = transactionDB.allocateNewConnection();
            transactionDB.transactionConn.setAutoCommit(false);
            return transactionDB ;
        }
        catch (Exception ex)
        {
            throw new DAOException(ex);
        }
    }

    /**
     * 提交
     */
    public void commit()
    {
        if(this.isTransaction())
        {
            try
            {
                this.transactionConn.commit();
            }
            catch (Exception ex)
            {
                throw new java.lang.Error(ex);
            }
        }
    }

    /**
     * 回滚
     */
    public void rollback()
    {
        if(this.isTransaction())
        {
            try
            {
                this.transactionConn.rollback();
            }
            catch (Exception ex)
            {
                throw new java.lang.Error(ex);
            }
        }
    }

    /**
     * 关闭
     */
    public void close()
    {
        if(this.isTransaction())
        {
            try
            {
                this.transactionConn.close();
            }
            catch (Exception ex)
            {
                throw new java.lang.Error(ex);
            }
        }
    }

    /**
     * 是否是一个事务场景。
     * @return boolean
     */
    public boolean isTransaction()
    {
        return this.transactionConn != null;
    }

    /**
     * 获取链接
     * @return Connection
     */
    public Connection getConnection() throws DAOException
    {
        if(this.isTransaction())
        {
            return this.transactionConn;
        }
        return this.allocateNewConnection();
    }

    /**
     * 分配一个新的数据库链接
     * 根据mode的不同，可能是通过jndi获取，也可能是根据jdbc直接获取
     * @return Connection数据链接
     */
    private Connection allocateNewConnection () throws DAOException
    {
        Connection conn = null ;
        try
        {
            if (TransactionContextFactory.mode == TransactionContextFactory.MODE_JNDI)
            {
                conn = ServiceLocator.getInstance().getDBConn() ;
            }
            else
            {
                Class.forName(PersistenceConstants.getJdbcOracleDriver()) ;
                conn = java.sql.DriverManager.getConnection(
                    PersistenceConstants.getJdbcOracleUrl(),
                    PersistenceConstants.getJdbcOracleUsername(),
                    PersistenceConstants.getJdbcOraclePassword()) ;
            }
        }
        catch (Exception ex)
        {
            throw new DAOException("DB.getConnection Error" , ex) ;
        }
        return conn ;
    }

    /**
     * 通过sqlCode获取sql语句
     * @param sqlCode String sqlCode
     * @return String sql语句
     * @throws DAOException
     */
    public String getSQLByCode (String sqlCode) throws DAOException
    {
        try
        {
            return SQLCode.getInstance().getSQLStatement(sqlCode) ;
        }
        catch (Exception ex)
        {
            throw new DAOException(ex) ;
        }
    }

    /**
     * 设置PreparedStatement的sql参数
     * @param statement PreparedStatement
     * @param paras Object[]
     * @throws SQLException
     */
    private void setParas (PreparedStatement statement, Object[] paras)
        throws SQLException
    {
        if (paras != null)
        {
            StringBuffer sb = new StringBuffer() ;
            sb.append("sql paras is:[") ;
            for (int i = 0 ; i < paras.length ; i++)
            {
                if (i > 0)
                {
                    sb.append(",") ;
                }
                if (paras[i] instanceof byte[]) //二进制BLOB数据处理有点特殊，单独处理
                {
                    sb.append(paras[i]) ;
                    byte[] data = (byte[]) paras[i] ;
                    ByteArrayInputStream bais = new ByteArrayInputStream(data) ;
                    statement.setBinaryStream(i + 1, bais, data.length) ;
                }
                else if(paras[i] instanceof ParameterClob)
                {
                    sb.append(paras[i]) ;
                    ParameterClob parameterClob = (ParameterClob)paras[i];
                    Reader reader = new StringReader(parameterClob.getText());
                    statement.setCharacterStream(i + 1, reader, parameterClob.getLength());
                }
                else
                {
                    sb.append(paras[i]) ;
                    if (paras[i] == null)
                    {
                        statement.setString(i + 1, null) ;
                    }
                    else
                    {
                        statement.setObject(i + 1, paras[i]) ;
                    }
                }
            }
            sb.append("]") ;
            if (LOG.isDebugEnabled())
            {
                LOG.debug(sb.toString()) ;
            }
        }
    }

    /**
     * 执行一个sql语句
     * @param sqlCode String,sql语句对应的代码
     * @param paras Object[],sql语句需要的参数，如果没有就为null
     * @return int,几条数据库记录被更新。
     * @throws DAOException
     */
    public int executeBySQLCode (String sqlCode, Object[] paras)
        throws DAOException
    {
    	
        String sql = this.getSQLByCode(sqlCode);
        LOG.debug("有事务执行的sql是"+sql);
        return this.execute(sql, paras) ;
    }

    /**
     * 执行一个sql语句
     * @param sql String,sql语句
     * @param paras Object[],sql语句需要的参数，如果没有就为null
     * @return int,几条数据库记录被更新。
     * @throws DAOException
     */
    public int execute (String sql, Object[] paras)
        throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("execute sql is:" + sql) ;
        }
        if (LOG.isDebugEnabled() && null != paras)
        {
            LOG.debug("Object[] paras is:" + paras.toString()) ;
        }
        Connection conn = null ;
        PreparedStatement statement = null ;
        int result = 0 ;
        try
        {
            conn = this.getConnection() ;
            statement = conn.prepareStatement(sql) ;
            this.setParas(statement, paras) ;
            result = statement.executeUpdate() ;
        }
        catch (Exception e)
        {
            throw new DAOException("execute failed.", e) ;
        }
        finally
        {
            //释放资源
            this.releaseDBResource(conn,statement,null);
        }
        return result ;
    }

    /**
     * 执行一个sql语句，
     * @param sqlCode String,sql语句对应的代码
     * @param paras Object[],sql语句需要的参数，如果没有就为null
     * @param convertor ResultSetConvertor,对应的rs转换器
     * @return List,查询到的结果集合
     * @throws DAOException
     */
    public List queryBySQLCode (String sqlCode, Object[] paras ,ResultSetConvertor convertor)
        throws DAOException
    {
        String sql = this.getSQLByCode(sqlCode);
        return query(sql, paras ,convertor) ;
    }

    /**
     * 执行一个sql语句
     * @param sql String,sql语句
     * @param paras Object[],sql语句需要的参数，如果没有就为null
     * @param convertor ResultSetConvertor,对应的rs转换器
     * @return List,查询到的结果集合
     * @throws DAOException
     */
    public List query (String sql, Object[] paras, ResultSetConvertor convertor)
        throws DAOException
    {
        Connection conn = null ;
        PreparedStatement statement = null ;
        ResultSet rs = null ;
        List result = null;
        try
        {
            conn = this.getConnection() ;
            if (LOG.isDebugEnabled())
            {
                LOG.debug("query sql is:" + sql) ;
            }
            statement = conn.prepareStatement(sql) ;
            this.setParas(statement, paras) ;
            rs = statement.executeQuery() ;
            result = new ArrayList();
            while(rs!=null && rs.next())
            {
                Object obj = convertor.convert(rs);
                result.add(obj);
            }
        }
        catch (Exception e)
        {
            throw new DAOException("execute failed.", e) ;
        }
        finally
        {
            //释放资源
            this.releaseDBResource(conn,statement,rs);
        }
        return result ;
    }

    /**
     * 取出数据库序列的下一个值
     * @param sequenceName String，序列名称
     * @return int，序列值
     * @throws DAOException
     */
    public int getSeqValue (String sequenceName) throws DAOException
    {
        int svalue = 0 ;
        Statement stmt = null ;
        ResultSet rs = null ;
        Connection conn = this.getConnection();
        try
        {
            String sql = "select " + sequenceName + ".nextval from dual" ;
            stmt = conn.createStatement() ;
            rs = stmt.executeQuery(sql) ;
            if (rs.next())
            {
                svalue = rs.getInt(1) ;
            }
        }
        catch (SQLException ex)
        {
            throw new DAOException("get sequence error." , ex) ;
        }
        finally
        {
            this.releaseDBResource(conn,stmt,rs);
        }
        return svalue ;
    }

    /**
     * @param rs ResultSet
     * @param fieldName String
     * @return String
     * @throws DAOException
     */
    public static String getClobValue (ResultSet rs, String fieldName) throws DAOException
    {
        Reader claimReader = null ;
        try
        {
            java.sql.Clob claimClob = rs.getClob(fieldName) ;
            //如果该字段可以为null，并且实际值也为null，就返回null :)
            if(claimClob==null)
            {
                return null;
            }
            char[] buf = new char[1024] ;
            claimReader = claimClob.getCharacterStream() ;
            StringBuffer claimBuffer = new StringBuffer() ;
            while (true)
            {
                int readCount = claimReader.read(buf) ;
                if (readCount == -1)
                {
                    break ;
                }
                claimBuffer.append(buf, 0, readCount) ;
            }
            return claimBuffer.toString() ;
        }
        catch (Exception ex)
        {
            throw new DAOException(ex) ;
        }
        finally
        {
            if (claimReader != null)
            {
                try
                {
                    claimReader.close() ;
                }
                catch (IOException ex1)
                {
                    LOG.error(ex1);
                }
            }
        }

    }

    /**
     * 更新一个Clob的值
     * @param sqlcode String,执行的sql语句的sqlcode
     * @param paras Object[],sql参数
     * @param content String,更新的内容
     * @throws DAOException
     */
    public void updateClobBySQLCode(String sqlcode, Object[] paras, String content) throws DAOException
    {
        String sql = this.getSQLByCode(sqlcode);
        this.updateClob(sql,paras,content);
    }

    /**
     * 更新一个Clob的值
     * @param sql String,执行的sql语句
     * @param paras Object[],sql参数
     * @param content String,更新的内容
     * @throws DAOException
     */
    public void updateClob(String sql, Object[] paras, String content) throws DAOException
    {
        Connection conn = null ;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        BufferedWriter out = null;

        try
        {
            conn = this.getConnection() ;
            if(!this.isTransaction())
            {
                conn.setAutoCommit(false);
            }
            if(LOG.isDebugEnabled())
            {
                LOG.debug("updateClob sql is:"+sql);
            }
            stmt = conn.prepareStatement(sql);
            //设置执行参数
            this.setParas(stmt,paras);
            rs = stmt.executeQuery();
            java.sql.Clob clob = null;
            if (rs.next())
            {
                clob = rs.getClob(1);

                if(LOG.isDebugEnabled())
                {
                    LOG.debug("clob class type is:"+clob.getClass().getName());
                }
                if (clob instanceof oracle.sql.CLOB)
                {
                    //由于oracle驱动不支持标准jdbc，需要特殊处理
                    out = new BufferedWriter(((oracle.sql.CLOB) clob)
                            .getCharacterOutputStream());
                }
                else
                {
                    out = new BufferedWriter(clob.setCharacterStream(0));
                }
                if(content==null)
                {
                    content = "";
                }
                out.write(content) ;
                out.flush() ;
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            //非事务场景下的，要记得提交。
            if(!this.isTransaction())
            {
                try
                {
                    conn.commit();
                }
                catch(Exception e)
                {
                    LOG.error(e);
                }
            }
            this.releaseDBResource(conn,stmt,rs);
            if(out!=null)
            {
                try
                {
                    out.close();
                }
                catch (Exception ex)
                {
                    LOG.error(ex);
                }
            }
        }
    }

    /**
     * 释放资源
     * @param conn Connection，数据库链接
     * @param statement PreparedStatement，statement执行器
     * @param rs ResultSet，记录集
     */
    public void releaseDBResource(Connection conn,Statement statement,ResultSet rs)
    {
        this.close(rs);
        if (statement != null)
        {
            try
            {
                statement.close() ;
            }
            catch (SQLException ex)
            {
                LOG.error(ex) ;
            }
        }
        //非事务的场景，要关闭链接。
        if (!this.isTransaction())
        {
            try
            {
                conn.close();
            }
            catch (Exception ex)
            {
                throw new java.lang.RuntimeException("close connection error",ex);

            }
        }
    }

    /**
     * 关闭一个不使用的数据集
     * @param rs ResultSet,数据集
     */
    public void close(ResultSet rs)
    {
        if(rs!=null)
        {
            try
            {
                rs.close() ;
            }
            catch (SQLException ex)
            {
                LOG.error(ex) ;
            }
        }
    }

    /**
     * 关闭一个不使用的数据库链接
     * @param conn Connection,数据库链接
     */
    public void close(Connection conn)
    {
        if(conn!=null)
        {
            try
            {
                conn.close() ;
            }
            catch (SQLException ex)
            {
                LOG.error(ex) ;
            }
        }
    }

}
