package com.aspire.common.persistence.util;

/**
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * The PersistenceConstants class is a wrapper class that provides
 * helper methods for accessing various property elements defined in
 * the persistece.properties file.
 */

import java.io.FileInputStream;
import java.util.Properties;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.Constant;
import com.aspire.common.config.ServerInfo;

public final class PersistenceConstants {
    private static String persistenceFileName = null;

    private static String jdbcMode = null;
    private static String jdbcOracleDriver = null;
    private static String jdbcOracleUrl = null;
    private static String jdbcOracleUsername = null;
    private static String jdbcOraclePassword = null;

    private static String jdbcHSqlDriver = null;
    private static String jdbcHSqlUrl = null;
    private static String jdbcHSqlUsername = null;
    private static String jdbcHSqlPassword = null;

    private static String jdbcMaxconn = null;
    private static String jdbcIdletimeout = null;
    private static String jdbcCheckouttimeout = null;
    private static String jdbcMaxcheckoutcount = null;
    private static String jdbcTrace = null;
    private static String jdbcDumpinfo = null;
    private static String jdbcTracelevel = null;
    private static String jdbcReaptimeinterval = null;
    private static String jdbcReadconfigtimeinterval = null;

    private static String javaNamingFactory = null;
    private static String javaNamingProvider = null;
    private static String jndiConn = null;

    protected static JLogger logger = LoggerFactory
                                      .getLogger(PersistenceConstants.class);

    static {
        PersistenceConstants p = new PersistenceConstants();
    }

    public static String getJavaNamingFactory() {
        return javaNamingFactory;
    }

    public static String getJavaNamingProvider() {
        return javaNamingProvider;
    }

    public static String getJndiConn() {
        return jndiConn;
    }

    public static String getJdbcMode() {
        return jdbcMode;
    }

    public static String getJdbcOracleDriver() {
        return jdbcOracleDriver;
    }

    public static String getJdbcOracleUrl() {
        return jdbcOracleUrl;
    }

    public static String getJdbcOracleUsername() {
        return jdbcOracleUsername;
    }

    public static String getJdbcOraclePassword() {
        return jdbcOraclePassword;
    }

    public static String getJdbcHSqlDriver() {
        return jdbcHSqlDriver;
    }

    public static String getJdbcHSqlUrl() {
        return jdbcHSqlUrl;
    }

    public static String getJdbcHSqlUsername() {
        return jdbcHSqlUsername;
    }

    public static String getJdbcHSqlPassword() {
        return jdbcHSqlPassword;
    }

    public static String getJdbcMaxconn() {
        return jdbcMaxconn;
    }

    public static String getJdbcIdletimeout() {
        return jdbcIdletimeout;
    }

    public static String getJdbcCheckouttimeout() {
        return jdbcCheckouttimeout;
    }

    public static String getJdbcMaxcheckoutcount() {
        return jdbcMaxcheckoutcount;
    }

    public static String getjdbcTrace() {
        return jdbcTrace;
    }

    public static String getJdbcDumpinfo() {
        return jdbcDumpinfo;
    }

    public static String getJdbcTracelevel() {
        return jdbcTracelevel;
    }

    public static String getJdbcReaptimeinterval() {
        return jdbcReaptimeinterval;
    }

    public static String getJdbcReadconfigtimeinterval() {
        return jdbcReadconfigtimeinterval;
    }

    public PersistenceConstants() {
        Properties persistenceProperties = new Properties();

        try {
            persistenceFileName = Constant.PERSISTENCE_PROP;
            if (persistenceFileName != null) {
                persistenceProperties.load(new FileInputStream(
                        persistenceFileName));
            } else {
                persistenceFileName = "/persistence.properties";
                persistenceProperties.load(this.getClass().getResourceAsStream(
                        persistenceFileName));
            }

            logger.info("load persistence.properties from: "
                        + persistenceFileName);

            javaNamingFactory = persistenceProperties
                                .getProperty("java.naming.factory.initial");

            javaNamingProvider = persistenceProperties
                                 .getProperty("java.naming.provider.url");

            jndiConn = persistenceProperties.getProperty("jndi.conn");

            jdbcMode = persistenceProperties.getProperty("jdbc.mode");

            jdbcOracleDriver = persistenceProperties
                               .getProperty("jdbc.oracle.driver");

            jdbcOracleUrl = persistenceProperties
                            .getProperty("jdbc.oracle.url");

            jdbcOracleUsername = persistenceProperties
                                 .getProperty("jdbc.oracle.username");

            jdbcOraclePassword = persistenceProperties
                                 .getProperty("jdbc.oracle.password");

            jdbcHSqlDriver = persistenceProperties
                             .getProperty("jdbc.hsql.driver");

            jdbcHSqlUrl = persistenceProperties.getProperty("jdbc.hsql.url");

            jdbcHSqlUsername = persistenceProperties
                               .getProperty("jdbc.hsql.username");

            jdbcHSqlPassword = persistenceProperties
                               .getProperty("jdbc.hsql.password");

            jdbcMaxconn = persistenceProperties.getProperty("jdbc.maxconn");

            jdbcIdletimeout = persistenceProperties
                              .getProperty("jdbc.idletimeout");

            jdbcCheckouttimeout = persistenceProperties
                                  .getProperty("jdbc.checkouttimeout");

            jdbcMaxcheckoutcount = persistenceProperties
                                   .getProperty("jdbc.maxcheckoutcount");

            jdbcTrace = persistenceProperties.getProperty("jdbc.trace");

            jdbcDumpinfo = persistenceProperties.getProperty("jdbc.dumpinfo");

            jdbcTracelevel = persistenceProperties
                             .getProperty("jdbc.tracelevel");

            jdbcReaptimeinterval = persistenceProperties
                                   .getProperty("jdbc.reaptimeinterval");

            jdbcReadconfigtimeinterval = persistenceProperties
                                         .getProperty(
                    "jdbc.readconfigtimeinterval");
        } catch (Exception e) {
            logger.error(Constant._EC_FRAMEWORK_LOAD_PERSISTENCE,
                         "Ö÷»ú=" + ServerInfo.getLoaclIP()+"´íÎó=",e);
        }
    }

    public static void main(String args[]) {
        System.out.println(PersistenceConstants.getJdbcHSqlDriver());
    }

}
