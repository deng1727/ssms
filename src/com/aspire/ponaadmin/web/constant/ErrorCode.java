package com.aspire.ponaadmin.web.constant ;

/**
 * <p>���������</p>
 * <p>ͳһ����ϵͳ����Ҫ�õ������д������</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ErrorCode
{

    /**
     * �ɹ�
     */
    public static final int SUCC = 0 ;

    /**
     * ϵͳ�ڲ�����
     */
    public static final int FAIL = 1 ;

    /**
     * �����Ƿ�
     */
    public static final int INVALID_PARA = 2 ;

    /**
     * ���ݿ����ʧ��
     */
    public static final int DB_ERR = 3 ;

    /**
     * �ļ�IOʧ��
     */
    public static final int FILE_IO_ERR = 4 ;

//--�ڵ����-------------------------------------------------
//--������ ����-----------------------------------------------

    /**
     * �ڵ����ʧ��
     */
    public static final int WEBSITE_NODE_ADD = 2001 ;

    /**
     * �ڵ�༭ʧ��
     */
    public static final int WEBSITE_NODE_MODIFY = 2002 ;

    /**
     * �ڵ�ɾ��ʧ��
     */
    public static final int WEBSITE_NODE_DEL = 2003 ;

    /**
     * �ڵ������ظ�
     */
    public static final int WEBSITE_NODE_DOUBLE_NAME = 2004 ;

    /**
     * ȡ���ڵ㼯��ʧ��
     */
    public static final int WEBSITE_GET_LIST = 2005 ;

    /**
     * վ����Ի�����ʧ��
     */
    public static final int WEBSITE_INIT = 2006 ;

    /**
     * �ڵ�����ظ�
     */
    public static final int WEBSITE_NODE_DOUBLE_ALIASNAME = 2007 ;

    /**
     * �����������ʧ��
     */
    public static final int WEBSITE_FILECONTENT_ADD = 2008 ;

    /**
     * �������ݱ༭ʧ��
     */
    public static final int WEBSITE_FILECONTENT_MODIFY = 2009 ;

    /**
     * д�ļ�ʧ��
     */
    public static final int WEBSITE_WRITEFILE_ERROR = 2010 ;

    /**
     * �߼�ɾ���ڵ�ʧ��
     */
    public static final int WEBSITE_NODE_LOGIC_DELETE_ERROR = 2011 ;

    /**
     * �ڵ�״̬����ʧ��
     */
    public static final int WEBSITE_NODE_STATE_UPDATE_ERROR = 2012 ;

    /**
     * վ�����Ա༭ʧ��
     */
    public static final int WEBSITE_PROPERTY_UPDATE_ERROR = 2013 ;

    /**
     * ������ʧ��
     */
    public static final int PLUGIN_ADD = 2014 ;

    /**
     * ������ظ�
     */
    public static final int PLUGIN_DOUBLE_NAME = 2019 ;

    /**
     * ����༭ʧ��
     */
    public static final int PLUGIN_MODIFY = 2015 ;

    /**
     * ���ɾ��ʧ��
     */
    public static final int PLUGIN_DEL = 2016 ;

    /**
     * ������Ի�
     */
    public static final int PLUGIN_INIT = 2017 ;

    /**
     * �����ѯ
     */
    public static final int PLUGIN_SEARCH = 2018 ;

    /**
     * ���ļ������Ѿ���ռ��
     */
    public static final int FILE_NAME_EXIST = 2019 ;

    /**
     * ���ļ������Ѿ���ռ��
     */
    public static final int FILE_ALIANAME_EXIST = 2020 ;

    /**
     * ��ģ�������Ѿ���ռ��
     */
    public static final int TEMPLATE_NAME_EXIST = 2021 ;

    /**
     * ��ģ������Ѿ���ռ��
     */
    public static final int TEMPLATE_ALIANAME_EXIST = 2022 ;

    /**
     * ���ļ��������Ѿ���ռ��
     */
    public static final int WEB_INF_SITE_FOLDER_NAME_EXISTED = 2023 ;

    /**
     * ���ļ��б����Ѿ���ʹ��
     */
    public static final int WEB_INF_SITE_FOLDER_ALIASNAME_EXISTED = 2024 ;
    
    /**
     * �ļ��и���ʱ�������ļ��и��ƹ���
     */
    public static final int SITE_FOLDER_COPY_PATH_INCLUDE = 2025;
//--   ����-------------------------------------------------

    /**
     * SP��ҵ�����Ѿ�����
     */
    public static final int SP_EXIST_ERR = 3001 ;

    /**
     * �������ݸ�ʽ����
     */
    public static final int SP_FMT_ERR = 3002 ;

        /**
         * �ļ��Ѿ�����
         */
        public static final int FILE_EXIST_ERR = 3003 ;

    //--   ����-------------------------------------------------
    //--�⸶ά ����-----------------------------------------------

    /**
     * FTP�쳣��
     */
    public static final int FTP_ERR = 4001 ;

    /**
     * ��ȡ��������ҳ���쳣��
     */
    public static final int GET_WAITING_BUILD_PAGE_ERR = 4002 ;

    /**
     * ҳ������쳣��
     */
    public static final int BUILD_PAGE_ERR = 4003 ;

    /**
     * �ļ������쳣��
     */
    public static final int FILE_COPY_ERR = 4004 ;

    /**
     * ҳ�������쳣��
     */
    public static final int BUILDING_PAGE_ERR = 4005 ;

    /**
     * ��ȡ�ڵ��µĽڵ��б��쳣��
     */
    public static final int GET_NODEID_LIST_ERR = 4006 ;

    /**
     * ��ȡվ��·���쳣��
     */
    public static final int GET_WEBSIT_PATH_ERR = 4007 ;

    /**
     * ��ȡ��Դͬ��Ŀ¼�б��쳣��
     */
    public static final int GET_RSYNC_DIR_LIST_ERR = 4007 ;

    /**
     * ʱ���ʽ���쳣��
     */
    public static final int DATE_FORMAT_ERR = 4008;

    /**
     * ����쳣��
     */
    public static final int PUGIN_ERR = 4009;

    //--ë���� ����-----------------------------------------------

    /**
     * ����ҵ��LOGO��������Ϣ;
     */
    public static final int MOD_SERV_ERR = 5000;

    /**
     * ����ҵ��LOGOʧ��
     */
    public static final int MOD_SERV_LOGO_ERR = 5001;

    /**
     * ����ҵ��������Ϣʧ��
     */
    public static final int MOD_SERV_DESC_ERR = 5002;

    /**
     * ָ�������ݸ�ʽ����
     */
    public static final int TOP_DATA_FMT_ERR = 5003;

    /**
     * ָ�����Ѿ�����
     */
    public static final int TOP_KEY_EXIST = 5004;

    /**
     * ͬ��Ŀ¼��ҵ����������Ѿ�����
     */
    public static final int CATE_NAME_EXIST = 5005;

    //--��С�� ����-----------------------------------------------

    /**
     * ����Ĭ��ָ�����ɾ��
     */
    public static final int TOP_KEY_DEL_ERR = 5006 ;

    /**
     * ���������Ѿ�����
     */
    public static final int POLL_TITLE_EXIST = 5007 ;

    /**
     * ��������ѡ���Ѿ�����
     */
    public static final int POLL_OPTION_EXIST = 5008 ;

    /**
     * ��������Ѿ�����
     */
    public static final int POLL_TYPE_EXIST = 5009 ;

    /**
     * ����������е�������
     */
    public static final int POLL_TYPE_DATA = 5010 ;

    /**
     * �ڵ㴦�ڼ����У������ټ�����
     */
    public static final int LOCK_APPLY_ERROR = 6000;

}
