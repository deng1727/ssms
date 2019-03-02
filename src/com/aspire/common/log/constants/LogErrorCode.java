package com.aspire.common.log.constants;

/**
 * <p>Title: LogErrorCode</p>
 * <p>Description: the error code for log subsystem</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author YanFeng
 * @version 1.0
 * history:
 * created at 29/5/2003 by YanFeng
 */
public interface LogErrorCode
{

    /**
     * FTP主机(?)查找错误
     */
    public static final int _EC_NET_FTP_OPEN=-130;
    /**
    * FTP目录(?)切换错误
    */
    public static final int _EC_NET_FTP_CHANGEDIR=-131;
    /**
    * FTP目录列表错误
    */
    public static final int _EC_NET_FTP_LISTDIR=-132;

    /**
    * FTP下拉文件(?)错误
    */
    public static final int _EC_NET_FTP_GETFILE=-133;
    /**
    * FTP删除文件(?)错误
    */
    public static final int _EC_NET_FTP_DELFILE=-134;
    /**
    * FTP登陆主机(?)错误
    */
    public static final int _EC_NET_FTP_LOGON=-135;
    /**
    * FTP退出主机(?)错误
    */
    public static final int _EC_NET_FTP_LOGOUT=-136;
    /**
    * JNDI初始化错误
    */
    public static final int _EC_NET_JMS_CREATE=-137;
    /**
    * JNDI查找(?)错误
    */
    public static final int _EC_NET_JMS_LOOKUP=-138;
    /**
    * JMS创建TopicConnection错误
    */
    public static final int _EC_NET_JMS_CREATETC=-139;
    /**
    * JMS创建TopicSession错误
    */
    public static final int _EC_NET_JMS_CREATETS=-140;
    /**
    * JMS创建Durable Subscriber错误
    */
    public static final int _EC_NET_JMS_CREATEDS=-141;
    /**
    * JMS设置Message Listener错误
    */
    public static final int _EC_NET_JMS_SETLISTENER=-142;
    /**
    * JMS激活TopicConnection错误
    */
    public static final int _EC_NET_JMS_STARTCONN=-143;
    /**
    * JMS从Message获取MessageBody错误
    */
    public static final int _EC_NET_JMS_GETEVENT=-144;
    /**
    * 文件(?)未找到
    */
    public static final int _EC_FILE_NOT_FOUND=-320;
    /**
    * 转化(?)到数字错误
    */
    public static final int _EC_LOG_NUMBERTRANS=-600;
    /**
    * 对象(?)为空错误
    */
    public static final int _EC_LOG_NULL_OBJ=-601;
    /**
    * 创建PreparedStatement错误
    */
    public static final int _EC_DB_PREPAREST=-230;
    /**
    * 初始化LogCenter错误
    */
    public static final int _EC_LOGCENTER_INIT=-602;
    /**
    * 不一致的数据类型，不能被转化为?
    */
    public static final int _EC_LOG_CAST=-603;
    /**
    * 磁盘满或磁盘坏，写日志错误
    */
    public static final int _EC_LOG_WRITE=-604;


}
