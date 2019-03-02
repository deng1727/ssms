package com.aspire.common.persistence.util;

/**
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * ���ݿ����ӹ����ࡣ
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.Constant;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.config.ServerInfo;

public class DataSourceFactory {
    protected static JLogger logger = LoggerFactory
                                      .getLogger(DataSourceFactory.class);
    /**
     * HSQL���ӻ���
     */
    private static List hsqlConnPool = null;
    /**
     * HSQL���ӻ����С
     */
    private static int poolSize = 100;
    /**
     * HSQL���ӻ�����е�λ��
     */
    private static int poolIdx = 0;

    private static Object connPoolLock = new Object();
    /**
     * �Ƿ��Ѿ�ע����HSQL JDBC DRIVER
     */
    private static boolean isHSqlDriverRegisted = false;

    /**
     * ȡHSql���ݿ����ӣ��������Ϊ�����ʼ�����Ӳ����أ�����ֱ�ӷ������ӡ�
     *
     * @return ���ݿ�����
     * @throws DataAccessException
     */
    public static Connection getHSqlConnection() throws DataAccessException {
        synchronized (connPoolLock) {
            if (!isHSqlDriverRegisted) {
                //ע��HSQL JDBC Driver
                try {
                    java.sql.Driver driver = (java.sql.Driver) Class.forName(
                            PersistenceConstants.getJdbcHSqlDriver())
                                             .newInstance();
                    DriverManager.registerDriver(driver);
                    isHSqlDriverRegisted = true;
                } catch (Exception e) {
                    logger.error(Constant._EC_FRAMEWORK_GET_HSQLCONN, e);
                }
            }
            if (hsqlConnPool == null || hsqlConnPool.size() == 0) {
                //��ʼ�����ӻ���
                hsqlConnPool = new ArrayList(poolSize);
                try {
                    for (int i = 0; i < poolSize; i++) {
                        hsqlConnPool.add(DriverManager.getConnection(
                                PersistenceConstants.getJdbcHSqlUrl(),
                                PersistenceConstants.getJdbcHSqlUsername(),
                                PersistenceConstants.getJdbcHSqlPassword()));
                    }
                } catch (Exception e) {
                    logger.error(Constant._EC_FRAMEWORK_GET_HSQLCONN, e);
                }
            }
        }
        synchronized (connPoolLock) {
            if (poolIdx == hsqlConnPool.size()) {
                poolIdx = 0;
            }
            return (Connection) hsqlConnPool.get(poolIdx++);
        }
    }

    /**
     * ͨ�������ļ��������õ�Context
     * @return Context
     * @throws DataAccessException
     */
    public static Context getDefaultContext() throws DataAccessException {
        //2005-03-03, modified by Fan Jingjian
        /*
         Properties containerEnv = new Properties();
         containerEnv.setProperty("java.naming.factory.initial",
         PersistenceConstants.getJavaNamingFactory());
         containerEnv.setProperty("java.naming.provider.url",
         PersistenceConstants.getJavaNamingProvider());
         */
        InitialContext ctx = null;
        try {
            //ctx = new InitialContext(containerEnv);
            ctx = new InitialContext();
        } catch (NamingException e) {
//            logger.error(Constant._EC_FRAMEWORK_INIT_JNDI, e);
            logger.error(Constant._EC_FRAMEWORK_INIT_JNDI,
                         ServerInfo.getLoaclIP(), e);
        }
        return ctx;
    }

    /**
     * ����JNDIȡ����
     * @param pJndi
     * @return DataSource
     * @throws DataAccessException
     */
    public static DataSource getDataSource(String pJndi) throws
            DataAccessException {
        return getDataSource(pJndi, getDefaultContext());
    }

    /**
     * ����JNDI��Context�õ�����
     * @param pJndi
     * @param pCtx
     * @return DataSource
     * @throws DataAccessException
     */
    private static DataSource getDataSource(String pJndi, Context pCtx) throws
            DataAccessException {
        DataSource ds = null;
        try {
            logger.debug("DataSource JNDIName is: " + pJndi);
            pCtx = (Context)pCtx.lookup("java:/comp/env");
            ds = (DataSource) pCtx.lookup(pJndi);
        } catch (NamingException e) {
        	e.printStackTrace();
//            logger.error(Constant._EC_FRAMEWORK_GET_DATASOURCE, e);
            logger.error(Constant._EC_FRAMEWORK_GET_DATASOURCE,
                         ServerInfo.getLoaclIP(), e);
        }
        return ds;
    }

    /**
     * �õ�jdbc����
     * @param jdbcDriver
     * @param jdbcUrl
     * @param jdbcUsername
     * @param jdbcPassword
     * @return ���ݿ�����
     */
    public static Connection getConnection(String jdbcDriver, String jdbcUrl,
                                           String jdbcUsername,
                                           String jdbcPassword) throws
            DataAccessException {
        Connection conn = null;
        try {
            java.sql.Driver driver = (java.sql.Driver) Class
                                     .forName(jdbcDriver).newInstance();
            DriverManager.registerDriver(driver);
            conn = DriverManager.getConnection(jdbcUrl, jdbcUsername,
                                               jdbcPassword);
        } catch (SQLException se) {
            logger.error(Constant._EC_FRAMEWORK_GETDBCONN, se);
        } catch (Exception e) {
            logger.error(Constant._EC_FRAMEWORK_GETDBCONN, e);
        }
        return conn;
    }

}
