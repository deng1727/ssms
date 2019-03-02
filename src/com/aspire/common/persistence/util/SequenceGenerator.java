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
 * ȡOracle���ݿ�Sequenceֵ.<br>
 * ����ʵ��ΪSinglton(����)ģʽ����֤�õ�����ͬһ��ʵ�����������к�<br>
 * ��������ͬ������֤���к�Ψһ�ԡ�
 * <p>
 * ʹ�÷���:
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
     * ��̬��ʼ�������
     */
    static {
        sequence = new SequenceGenerator();
    }

    /**
     * ��̬��������
     * @return SequenceGenerator����
     */
    public static SequenceGenerator getInstance() {
        return sequence;
    }

    /**
     * ��̬ȡ���кŷ���
     * @param seqName ��������
     * @return ���кţ�����int
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