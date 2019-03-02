package com.aspire.common.persistence.util;

/**
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * The SQLCode class is a helper class that loads all of the SQL
 * code used within the persistence framework into a private properties
 * object stored within the SQLCode class.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.Constant;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.config.ServerInfo;

public class SQLCode {
    private static SQLCode sqlCode = null;
    private static Properties sqlCache = new Properties();

    protected static JLogger logger = LoggerFactory.getLogger(SQLCode.class);

    /*Calling the SQLCode's constructor*/
    static {
        sqlCode = new SQLCode();
    }

    /*Retrieves the Singleton for the SQLCode class*/
    public static SQLCode getInstance() {
        return sqlCode;
    }

    /*
     * The SQLCode constructor loads all of the SQL code used in the
     * persistence framework from the sql.properties class into the sqlCache
     * properties object
     *
     */
    private SQLCode() {
        String sqlFileName = null;
        try {
            sqlFileName = Constant.SQL_PROP;
            if (sqlFileName != null) {
                sqlCache.load(new FileInputStream(sqlFileName));
            } else {
                sqlFileName = "/sql.properties";
                sqlCache.load(this.getClass().getResourceAsStream(sqlFileName));
            }
            logger.info("load sql.properties from: " + sqlFileName);
        } catch (IOException e) {
//            logger.error(Constant._EC_FRAMEWORK_GET_SQL, e);
            logger.error(Constant._EC_FRAMEWORK_LOAD_SQL,
                         "主机=" + ServerInfo.getLoaclIP() + "错误=", e);
        }
    }

    /*
     * The getSQLStatement() method will try to retrieve a SQL statement
     * from the SQLCache properties object based on the key passed into
     * the method.  If it can not find an the SQL statement, a DataAccess
     * Exception will be raised.
     *
     */
    public String getSQLStatement(String pSQLKeyName) throws
            DataAccessException {
//        /*Checking to see if the requested SQL statement is in the sqlCache*/
//        if (sqlCache.containsKey(pSQLKeyName)) {
//            return (String) sqlCache.get(pSQLKeyName);
//        } else {
//            throw new DataAccessException(
//                    "Unable to locate the SQL statement requested in SQLCode.getSQLCode() "
//                    + pSQLKeyName);
//        }

        String sqlStatement = null;
        try {
            if (sqlCache.containsKey(pSQLKeyName)) {
                sqlStatement = (String) sqlCache.get(pSQLKeyName);
            }
        } catch (Exception ex) {
            logger.error(Constant._EC_FRAMEWORK_GET_SQL,
                         "主机=" + ServerInfo.getLoaclIP() + "出错sql=" +
                         pSQLKeyName + "错误=", ex);
        }
        return sqlStatement;

    }

}
