package com.aspire.common.persistence.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.Constant;
import com.aspire.common.persistence.ServiceLocatorException;

/**
 * 取Oracle数据库Sequence值.<br>
 * 本类实现为Singlton(单体)模式，保证得到的是同一个实例，并且序列号<br>
 * 产生方法同步，保证序列号唯一性。
 * <p>
 * 使用方法:
 * </p>
 * <p>
 * SequenceGenerator sequence = SequenceGenerator.getInstance();
 * int idSeq = sequence.getSequence("XPORTAL_THEMES_SEQ");
 * </p>
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 */
public class SequenceGenerator {
    private static SequenceGenerator sequence = null;

    protected static JLogger logger = LoggerFactory
            .getLogger(SequenceGenerator.class);

    /**
     * 静态初始化代码块
     */
    static {
        sequence = new SequenceGenerator();
    }

    /**
     * 静态工厂方法
     * @return SequenceGenerator对象
     */
    public static SequenceGenerator getInstance() {
        return sequence;
    }

    /**
     * 静态取序列号方法
     * @param seqName 序列名称
     * @return 序列号，类型int
     * @throws ServiceLocatorException
     */
    public static int getSequence(String seqName)
            throws ServiceLocatorException {

        Connection conn = null;
        Statement statement = null;
        ResultSet rsCounter = null;
        int idSequence = -1;
        String sqlStr = null;

        try {
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            conn = serviceLocator.getDBConn();
            if (conn == null) {
                logger.error(Constant._EC_FRAMEWORK_GETDBCONN,
                        "A error occurred in ServiceLocator.getDBConn()");
                throw new ServiceLocatorException(
                        "A SQL error has occurred in "
                                + "ServiceLocator.getDBConn()");
            }
            sqlStr = "select " + seqName + ".nextval SID from dual ";
            statement = conn.createStatement();
            rsCounter = statement.executeQuery(sqlStr);
            if (rsCounter.next()) {
                idSequence = rsCounter.getInt(1);
            }
        } catch (ServiceLocatorException se) {
            logger.error(Constant._EC_FRAMEWORK_GET_SEQUENCE,
                    "A error occurred in ServiceLocator.getDBConn()");
            throw new ServiceLocatorException("A SQL error has occurred in "
                    + "ServiceLocator.getDBConn()");
        } catch (SQLException e) {
            logger.error(Constant._EC_FRAMEWORK_GET_SEQUENCE,
                    "A error occurred in ServiceLocator.getDBConn()");
        } finally {
            ClearDBResource.closeStatment(statement);
            ClearDBResource.closeResultSet(rsCounter);
            ClearDBResource.closeConnection(conn);
        }
        return idSequence;
    }
}