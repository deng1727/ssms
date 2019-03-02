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
 * <p>�������ݿ������</p>
 * <p>Copyright (c) 2003-2006 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class TransactionDB implements TransactionContext
{
    /**
     * ��־
     */
    private static final JLogger LOG = LoggerFactory.getLogger(TransactionDB.class) ;

    /**
     * singletonģʽ
     */
    private static TransactionDB instance = new TransactionDB() ;

    /**
     * ���ﳡ���µ�����
     */
    private Connection transactionConn = null ;

    /**
     * ˽�й�����
     */
    private TransactionDB ()
    {

    }

    /**
     * ��ȡһ�������񳡾���ʵ��
     * @return TransactionDB
     */
    public static TransactionDB getInstance ()
    {
        return instance ;
    }

    /**
     * ��ȡһ�����񳡾���ʵ��
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
     * �ύ
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
     * �ع�
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
     * �ر�
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
     * �Ƿ���һ�����񳡾���
     * @return boolean
     */
    public boolean isTransaction()
    {
        return this.transactionConn != null;
    }

    /**
     * ��ȡ����
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
     * ����һ���µ����ݿ�����
     * ����mode�Ĳ�ͬ��������ͨ��jndi��ȡ��Ҳ�����Ǹ���jdbcֱ�ӻ�ȡ
     * @return Connection��������
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
     * ͨ��sqlCode��ȡsql���
     * @param sqlCode String sqlCode
     * @return String sql���
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
     * ����PreparedStatement��sql����
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
                if (paras[i] instanceof byte[]) //������BLOB���ݴ����е����⣬��������
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
     * ִ��һ��sql���
     * @param sqlCode String,sql����Ӧ�Ĵ���
     * @param paras Object[],sql�����Ҫ�Ĳ��������û�о�Ϊnull
     * @return int,�������ݿ��¼�����¡�
     * @throws DAOException
     */
    public int executeBySQLCode (String sqlCode, Object[] paras)
        throws DAOException
    {
    	
        String sql = this.getSQLByCode(sqlCode);
        LOG.debug("������ִ�е�sql��"+sql);
        return this.execute(sql, paras) ;
    }

    /**
     * ִ��һ��sql���
     * @param sql String,sql���
     * @param paras Object[],sql�����Ҫ�Ĳ��������û�о�Ϊnull
     * @return int,�������ݿ��¼�����¡�
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
            //�ͷ���Դ
            this.releaseDBResource(conn,statement,null);
        }
        return result ;
    }

    /**
     * ִ��һ��sql��䣬
     * @param sqlCode String,sql����Ӧ�Ĵ���
     * @param paras Object[],sql�����Ҫ�Ĳ��������û�о�Ϊnull
     * @param convertor ResultSetConvertor,��Ӧ��rsת����
     * @return List,��ѯ���Ľ������
     * @throws DAOException
     */
    public List queryBySQLCode (String sqlCode, Object[] paras ,ResultSetConvertor convertor)
        throws DAOException
    {
        String sql = this.getSQLByCode(sqlCode);
        return query(sql, paras ,convertor) ;
    }

    /**
     * ִ��һ��sql���
     * @param sql String,sql���
     * @param paras Object[],sql�����Ҫ�Ĳ��������û�о�Ϊnull
     * @param convertor ResultSetConvertor,��Ӧ��rsת����
     * @return List,��ѯ���Ľ������
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
            //�ͷ���Դ
            this.releaseDBResource(conn,statement,rs);
        }
        return result ;
    }

    /**
     * ȡ�����ݿ����е���һ��ֵ
     * @param sequenceName String����������
     * @return int������ֵ
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
            //������ֶο���Ϊnull������ʵ��ֵҲΪnull���ͷ���null :)
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
     * ����һ��Clob��ֵ
     * @param sqlcode String,ִ�е�sql����sqlcode
     * @param paras Object[],sql����
     * @param content String,���µ�����
     * @throws DAOException
     */
    public void updateClobBySQLCode(String sqlcode, Object[] paras, String content) throws DAOException
    {
        String sql = this.getSQLByCode(sqlcode);
        this.updateClob(sql,paras,content);
    }

    /**
     * ����һ��Clob��ֵ
     * @param sql String,ִ�е�sql���
     * @param paras Object[],sql����
     * @param content String,���µ�����
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
            //����ִ�в���
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
                    //����oracle������֧�ֱ�׼jdbc����Ҫ���⴦��
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
            //�����񳡾��µģ�Ҫ�ǵ��ύ��
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
     * �ͷ���Դ
     * @param conn Connection�����ݿ�����
     * @param statement PreparedStatement��statementִ����
     * @param rs ResultSet����¼��
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
        //������ĳ�����Ҫ�ر����ӡ�
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
     * �ر�һ����ʹ�õ����ݼ�
     * @param rs ResultSet,���ݼ�
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
     * �ر�һ����ʹ�õ����ݿ�����
     * @param conn Connection,���ݿ�����
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
