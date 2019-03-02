package com.aspire.common.persistence.util;

/**
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * 释放数据库连接等资源的工具类。
 * @CheckItem@ OPT-yanfeng-20041019 remove debug log to prevent deadlock on alert report
 */
import java.sql.Connection;
import java.sql.SQLException;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.config.ServerInfo;

public final class ClearDBResource {

    protected static JLogger logger = LoggerFactory
                                      .getLogger(ClearDBResource.class);

    public ClearDBResource() {}

    /**
     * 关闭连接池
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                logger.error(-218,
                             " 应用程序主机=" + ServerInfo.getLoaclIP() +
                             " ，数据库连接串=" + PersistenceConstants.getJndiConn(),
                             ex);
            }
        }
    }

    /**
     * 关闭连接池，并打印是哪个类
     * @param conn
     * @param classObject 调用该方法的类
     */
    public static void closeConnection(Connection conn, Object clazz) {
        if (conn != null) {
            StringBuffer infoStr = new StringBuffer();
            infoStr.append("\n");
            infoStr.append("********** Jdbc Debug:***********\n");
            infoStr.append(clazz.getClass().getName());

            try {
                if (!conn.isClosed()) {
                    conn.close();
                }

                infoStr.append("\nConnction already closed!\n");
            } catch (SQLException ex) {
                infoStr.append("\nUnable to close database connection!\n");
                infoStr.append(ex.getMessage());
                logger.error(infoStr.toString(), ex);
            }
        }
    }

    /**
     * 释放记录集对象
     * @param rs
     */
    public static void closeResultSet(java.sql.ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.error("Unable to close database ResultSet", ex);
            }
        }
    }

    /**
     * 释放记录集对象，并打印调用该方法的类
     * @param rs
     */
    public static void closeResultSet(java.sql.ResultSet rs, Object clazz) {
        StringBuffer infoStr = new StringBuffer();
        infoStr.append("\n");
        infoStr.append("********** Jdbc Debug:***********\n");
        infoStr.append(clazz.getClass().getName());

        if (rs != null) {
            try {
                rs.close();
                infoStr.append("\nresultSet already closed!");
            } catch (SQLException ex) {
                infoStr.append("\nUnable to close database ResultSet");
                logger.error(infoStr, ex);
            }
        }
    }

    /**
     * 释放PreparedStatement对象
     * @param stmt
     */
    public static void closeStatment(java.sql.PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                logger.error("Unable to close database PreparedStatement ", ex);
            }
        }
    }

    /**
     * 释放PreparedStatement，并打印是在哪个类中发生。
     * @param stmt
     * @param clazz
     */
    public static void closeStatment(java.sql.PreparedStatement stmt,
                                     Object clazz) {
        StringBuffer infoStr = new StringBuffer();
        infoStr.append("\n");
        infoStr.append("********** Jdbc Debug:***********\n");
        infoStr.append(clazz.getClass().getName());

        if (stmt != null) {
            try {
                stmt.close();
                infoStr.append("\nPreparedStatement already closed!\n");
            } catch (SQLException ex) {
                infoStr
                        .append(
                                "\nUnable to close database PreparedStatement!\n");
                logger.error("Unable to close database PreparedStatement ", ex);
            }
        }
    }

    /**
     * 释放Statement对象占用资源
     * @param stmt
     */
    public static void closeStatment(java.sql.Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                logger.error("Unable to close database PreparedStatement ", ex);
            }
        }
    }

    public static void closeStatment(java.sql.Statement stmt, Object clazz) {
        StringBuffer infoStr = new StringBuffer();
        infoStr.append("\n");
        infoStr.append("********** Jdbc Debug:***********\n");
        infoStr.append(clazz.getClass().getName());

        if (stmt != null) {
            try {
                stmt.close();
                infoStr.append("\nStatement already closed!\n");
            } catch (SQLException ex) {
                infoStr
                        .append(
                                "\nUnable to close database PreparedStatement!\n");
                logger.error("Unable to close database PreparedStatement ", ex);
            }
        }
    }
}
