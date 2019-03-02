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
     * FTP����(?)���Ҵ���
     */
    public static final int _EC_NET_FTP_OPEN=-130;
    /**
    * FTPĿ¼(?)�л�����
    */
    public static final int _EC_NET_FTP_CHANGEDIR=-131;
    /**
    * FTPĿ¼�б����
    */
    public static final int _EC_NET_FTP_LISTDIR=-132;

    /**
    * FTP�����ļ�(?)����
    */
    public static final int _EC_NET_FTP_GETFILE=-133;
    /**
    * FTPɾ���ļ�(?)����
    */
    public static final int _EC_NET_FTP_DELFILE=-134;
    /**
    * FTP��½����(?)����
    */
    public static final int _EC_NET_FTP_LOGON=-135;
    /**
    * FTP�˳�����(?)����
    */
    public static final int _EC_NET_FTP_LOGOUT=-136;
    /**
    * JNDI��ʼ������
    */
    public static final int _EC_NET_JMS_CREATE=-137;
    /**
    * JNDI����(?)����
    */
    public static final int _EC_NET_JMS_LOOKUP=-138;
    /**
    * JMS����TopicConnection����
    */
    public static final int _EC_NET_JMS_CREATETC=-139;
    /**
    * JMS����TopicSession����
    */
    public static final int _EC_NET_JMS_CREATETS=-140;
    /**
    * JMS����Durable Subscriber����
    */
    public static final int _EC_NET_JMS_CREATEDS=-141;
    /**
    * JMS����Message Listener����
    */
    public static final int _EC_NET_JMS_SETLISTENER=-142;
    /**
    * JMS����TopicConnection����
    */
    public static final int _EC_NET_JMS_STARTCONN=-143;
    /**
    * JMS��Message��ȡMessageBody����
    */
    public static final int _EC_NET_JMS_GETEVENT=-144;
    /**
    * �ļ�(?)δ�ҵ�
    */
    public static final int _EC_FILE_NOT_FOUND=-320;
    /**
    * ת��(?)�����ִ���
    */
    public static final int _EC_LOG_NUMBERTRANS=-600;
    /**
    * ����(?)Ϊ�մ���
    */
    public static final int _EC_LOG_NULL_OBJ=-601;
    /**
    * ����PreparedStatement����
    */
    public static final int _EC_DB_PREPAREST=-230;
    /**
    * ��ʼ��LogCenter����
    */
    public static final int _EC_LOGCENTER_INIT=-602;
    /**
    * ��һ�µ��������ͣ����ܱ�ת��Ϊ?
    */
    public static final int _EC_LOG_CAST=-603;
    /**
    * ����������̻���д��־����
    */
    public static final int _EC_LOG_WRITE=-604;


}
