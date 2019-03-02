package com.aspire.common.persistence.util;

/**
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * �ͷ����ݿ����ӵ���Դ�Ĺ����ࡣ
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
     * �ر����ӳ�
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
                             " Ӧ�ó�������=" + ServerInfo.getLoaclIP() +
                             " �����ݿ����Ӵ�=" + PersistenceConstants.getJndiConn(),
                             ex);
            }
        }
    }

    /**
     * �ر����ӳأ�����ӡ���ĸ���
     * @param conn
     * @param classObject ���ø÷�������
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
     * �ͷż�¼������
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
     * �ͷż�¼�����󣬲���ӡ���ø÷�������
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
     * �ͷ�PreparedStatement����
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
     * �ͷ�PreparedStatement������ӡ�����ĸ����з�����
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
     * �ͷ�Statement����ռ����Դ
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
