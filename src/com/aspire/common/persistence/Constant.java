package com.aspire.common.persistence;

/**
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * ���ݿ���ʿ�ܳ�����
 */

public final class Constant {
    public static String SQL_PROP = null;
    public static String PERSISTENCE_PROP = null;

    /**
     * �޷���ȡoracle���ݿ����ӣ����ݿ����Ӵ���%s
     */
    public final static int _EC_FRAMEWORK_GET_ORACONN = -501;
    /**
     * ȡ�ڴ����ݿ����ӳ���
     */
    public final static int _EC_FRAMEWORK_GET_HSQLCONN = -502;
    /**
     * ������ȷ��ʼ��JNDI������,������%s
     */
    public final static int _EC_FRAMEWORK_INIT_JNDI = -503;
    /**
     * ͨ��JNDI����ȡDataSource����,������%s
     */
    public final static int _EC_FRAMEWORK_GET_DATASOURCE = -504;
    /**
     * ͨ��JDBC DRIVERȡ���ݿ����ӳ���
     */
    public final static int _EC_FRAMEWORK_GETDBCONN = -505;
    /**
     * ����persistence.properites���ó���
     */
    public final static int _EC_FRAMEWORK_LOAD_PERSISTENCE = -506;
    /**
     * ȡ���ݿ����кų���
     */
    public final static int _EC_FRAMEWORK_GET_SEQUENCE = -507;
    /**
     * ����sql.properties�ļ�����
     */
    public final static int _EC_FRAMEWORK_LOAD_SQL = -508;
    /**
     * ȡSQL������
     */
    public final static int _EC_FRAMEWORK_GET_SQL = -509;

}
