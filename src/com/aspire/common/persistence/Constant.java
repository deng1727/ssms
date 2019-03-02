package com.aspire.common.persistence;

/**
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * 数据库访问框架常量类
 */

public final class Constant {
    public static String SQL_PROP = null;
    public static String PERSISTENCE_PROP = null;

    /**
     * 无法获取oracle数据库链接，数据库连接串：%s
     */
    public final static int _EC_FRAMEWORK_GET_ORACONN = -501;
    /**
     * 取内存数据库连接出错
     */
    public final static int _EC_FRAMEWORK_GET_HSQLCONN = -502;
    /**
     * 不能正确初始化JNDI上下文,主机：%s
     */
    public final static int _EC_FRAMEWORK_INIT_JNDI = -503;
    /**
     * 通过JNDI名称取DataSource出错,主机：%s
     */
    public final static int _EC_FRAMEWORK_GET_DATASOURCE = -504;
    /**
     * 通过JDBC DRIVER取数据库连接出错
     */
    public final static int _EC_FRAMEWORK_GETDBCONN = -505;
    /**
     * 加载persistence.properites配置出错
     */
    public final static int _EC_FRAMEWORK_LOAD_PERSISTENCE = -506;
    /**
     * 取数据库序列号出错
     */
    public final static int _EC_FRAMEWORK_GET_SEQUENCE = -507;
    /**
     * 加载sql.properties文件出错
     */
    public final static int _EC_FRAMEWORK_LOAD_SQL = -508;
    /**
     * 取SQL语句出错
     */
    public final static int _EC_FRAMEWORK_GET_SQL = -509;

}
