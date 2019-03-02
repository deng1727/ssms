package com.aspire.common.persistence.util;

/**
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * The ServiceLocator pattern is abstracts away the JNDI
 * logic necessary for retrieving a JDBC Connection or EJBHome
 * interface.
 * In the case ,no EJBHome implements.
 * @CheckItem@ OPT-yanfeng-20041019 remove error id in error log to prevent deadlock on alert report
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.sql.DataSource;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.ServiceLocatorException;
import com.aspire.common.persistence.Constant;

public class ServiceLocator {
    private static ServiceLocator serviceLocatorRef = null;
    private static Hashtable dataSourceCache = null;
    private static final String serviceName = "OracleConn";

    protected static JLogger logger = LoggerFactory
                                      .getLogger(ServiceLocator.class);

    static {
        serviceLocatorRef = new ServiceLocator();
    }

    public ServiceLocator() {
        dataSourceCache = new Hashtable();
    }

    public static ServiceLocator getInstance() {
        return serviceLocatorRef;
    }

    static private String getServiceName() throws ServiceLocatorException {
        return serviceName;
    }

    /**
     * get Connection of HSQL
     * @return Connection
     * @throws ServiceLocatorException
     */
    public Connection getHSqlConnction() throws ServiceLocatorException {
        Connection conn = null;
        try {
            conn = DataSourceFactory.getHSqlConnection();
            //conn = DataSourceFactory.getOracleConnection();
        } catch (Exception e) {
            logger.error("getHSqlConnction", e);
        }
        return conn;
    }

    /*
     * The getDBConn() method will create a JDBC connection for the
     * requested database.  It too uses a cachin algorithm to minimize
     * the number of JNDI hits that it must perform
     * if mode is "dev",then get cutome jdbc Pool.
     */
    public Connection getDBConn() throws ServiceLocatorException {
        /*Getting the JNDI Service Name*/
        Connection conn = null;
        String serviceName = getServiceName();
        try {
            /*Checking to see if the requested DataSource is in the Cache*/
            if (dataSourceCache.containsKey(serviceName)) {
                DataSource ds = (DataSource) dataSourceCache.get(serviceName);
                conn = ((DataSource) ds).getConnection();
                return conn;
            } else {
                /*
                 * The DataSource was not in the cache.  Retrieve it from JNDI
                 * and put it in the cache.
                 */
                DataSource newDataSource = DataSourceFactory
                                           .getDataSource(PersistenceConstants.
                        getJndiConn());
                dataSourceCache.put(serviceName, newDataSource);
                conn = newDataSource.getConnection();
                return conn;
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            logger.error("ServiceLocator.getDBConn", e);
            throw new ServiceLocatorException("A SQL error has occurred in "
                                              + "ServiceLocator.getDBConn()");
        } catch (Exception e) {
            logger.error("ServiceLocator.getDBConn", e);
//            logger.error(Constant._EC_FRAMEWORK_GET_ORACONN,
//                         PersistenceConstants.getJndiConn(), e);

            throw new ServiceLocatorException("An exception has occurred "
                                              +
                                              " in ServiceLocator.getDBConn()");
        }
    }
}
