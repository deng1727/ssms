/*
 * �ļ�����Constants.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  ���ļ�������س���
 * �޸��ˣ� �߱���
 * �޸�ʱ�䣺2005/06/20
 * �޸����ݣ�����
 */
package com.aspire.ponaadmin.web.constant;

/**
 * <p>
 * Title: ϵͳ����������
 * </p>
 * <p>
 * Description: ������Ҫ���ڶ���ҳ��Ťת�ĳ���������ȣ�����Ӳ���롣
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All
 * Rights Reserved
 * </p>
 *
 * @author gaobaobing
 * @version 1.0.0.0
 */
public class Constants
{

    /**
     * the name in request to store message array
     */
    public static final String REQ_KEY_MESSAGE = "REQ_KEY_MESSAGE";

    /**
     * Define the forward page!
     */
    public static final String FORWARD_KEY = "forward";

    /**
     * struts���涨��Ĺ��óɹ�ҳ��
     */
    public static final String FORWARD_COMMON_SUCCESS = "commonSuccess";

    /**
     * struts���涨��Ĺ��ô���ҳ��
     */
    public static final String FORWARD_COMMON_FAILURE = "commonFailure";

    /**
     * ���óɹ�ҳ�桢ʧ��ҳ�����תurl��������
     */
    public static final String PARA_GOURL = "goURL";

    /**
     * ���óɹ�ҳ�桢ʧ��ҳ���Ŀ����targetFrame��������
     */
    public static final String PARA_TARGETFRAME = "targetFrame";

    /**
     * ���óɹ�ҳ�桢ʧ��ҳ�棬�Ƿ���Ҫ��ʾ�رհ�ť��
     */
    public static final String PARA_ISCLOSE = "isClose";

    /**
     * ���óɹ�ҳ�桢ʧ��ҳ�棬�Ƿ��Ƕ�����ʾҳ�棬������ʾ�Ļ�Ҫ��ʾͷ��β��
     */
    public static final String PARA_ISSTANDALONE = "isStandAlone";

    /**
     * ���óɹ�ҳ�桢ʧ��ҳ�棬��Ҫˢ�¸�ҳ���е����Ľڵ㣬�����ָ�����������������ˢ������
     */
    public static final String PARA_REFRESH_TREE_KEY = "refreshTreeKey";

    /**
     * Used in various action classes to define where to forward to on different
     * conditions. See the struts-config.xml file to see where the page that is
     * using this forwards to.
     */
    public static final String FORWARD_SUCCESS = "success";

    /**
     * Used in various action classes to define where to forward to on different
     * conditions. See the struts-config.xml file to see where the page that is
     * using this forwards to.
     */
    public static final String FORWARD_FAILURE = "failure";

    /**
     * ���ô���ҳ��
     */
    public static final String FAIL_PAGE = "/web/common/error.jsp";

    /**
     * ���óɹ�ҳ��
     */
    public static final String SUCC_PAGE = "/web/common/success.jsp";

    /**
     * Used in various action classes to define where to forward to on different
     * conditions. See the struts-config.xml file to see where the page that is
     * using this forwards to.
     */
    public static final String FORWARD_UNKNOWN_ERROR = "unknown-error";

    /**
     * Used in various action classes to define where to forward to on different
     * conditions. See the struts-config.xml file to see where the page that is
     * using this forwards to.
     */
    public static final String FORWARD_ADD_TOKEN = "add";

    /**
     * Used in various action classes to define where to forward to on different
     * conditions. See the struts-config.xml file to see where the page that is
     * using this forwards to.
     */
    public static final String FORWARD_UPDATE_TOKEN = "update";

    /**
     * Used in various action classes to define where to forward to on different
     * conditions. See the struts-config.xml file to see where the page that is
     * using this forwards to.
     */
    public static final String FORWARD_DELETE_TOKEN = "del";

    /**
     * Used in various action classes to define where to forward to on different
     * conditions. See the struts-config.xml file to see where the page that is
     * using this forwards to.
     */
    public static final String FORWARD_SAVE_TOKEN = "save";

    /**
     * Used in various action classes to define where to forward to on different
     * conditions. See the struts-config.xml file to see where the page that is
     * using this forwards to.
     */
    public static final String FORWARD_QUERY_TOKEN = "query";

    /**
     * ��ʱ��������С
     */
    public static final int TEMP_BUF_SIZE = 1024;

    // ==========================================================

    /**
     * ҵ�������ڵ��ʾ
     */
    public static final int SERV_CATE_ROOT = 1;

    /**
     * ҵ�������ڵ��ʾ
     */
    public static final int KEYWORD_CATE_ROOT = 5;

    /**
     * ҵ��δ������ڵ�
     */
    public static final int MYSERV_CATE_ROOT = 2;

    /**
     * ҵ������ö���־
     */
    public static final int DIR_SERV_TOPFLAG = 0;

    /**
     * SPָ�������ݱ�ʶ
     */
    public static final int TOP_DATA_TYPTE_SP = 1;

    /**
     * ҵ��ָ�������ݱ�ʶ
     */
    public static final int TOP_DATA_TYPTE_SERV = 2;

    // ==========================================================
    // վ��------------
    // ���������
    // ==========================================================

    /**
     * ɾ�����:��
     */
    public static final int DELETE_TAG_TRUE = 1;

    /**
     * ɾ�����:��
     */
    public static final int DELETE_TAG_FALSE = 0;

    /**
     * Ĭ�ϵ�����ֵ
     */
    public static final int DEFAULT_SORTFLAG = 100;

    /**
     * �лس����š�
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Ŀ¼�ָ���
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    // ==========================================================

    /**
     * ѡ��վ��·����URL;
     */
    public static final String SELECT_SITE_URL =
                        "/web/sitemgr/websiteList.do?goURL=/web/sitemgr/main.jsp";

    /**
     * ҵ�������ڵ��ʾ
     */
    public static final int NEWS_CATE_ROOT = 1;

    // =========================================================================

    /**
     * �첽������� x_dengjun ����
     */
    public final static String TASK_ISNULL = "01";// ������Ϊ��

    /**
     */
    public final static String TASK_THISTASK_EXIST = "02";// �����Ѿ�����

    /**
     */
    public final static String TASK_ID_ISNULL = "03";// ����ID����Ϊ��

    /**
     */
    public final static String TASK_USERID_ISNULL = "04";// ����userID����Ϊ��

    /**
     */
    public final static String TASK_ACTIONTYPE_ISNULL = "05";// ����actionType����Ϊ��

    /**
     */
    public final static String TASK_OVERMAXTASK = "06";// �������õ����������

    /**
     */
    public final static String TASK_GETMAXTASK_ERROR = "07";// ��ȡ������������ô���

    /**
     */
    public final static int TASK_DEFAULT_MAXTASK = 20;// Ĭ�ϵ����������

    /**
     */
    public final static int TASK_PROCESSING = 1;// ����״̬��������

    /**
     */
    public final static int TASK_PROCESSED = 2;// ����״̬�����

    /**
     */
    public final static int TASK_REQUESTSTOP = 3;// ����״̬��������ֹ

    /**
     */
    public final static int TASK_STOP = 4;// ����״̬������ֹ

    /**
     */
    public final static String TASK_TYPE_COPY = "10";// �������ͣ�����

    /**
     */
    public final static String TASK_TYPE_RELEASE = "20";// �������ͣ�����

    /**
     */
    public final static String TASK_TYPE_GENRRATE = "30";// �������ͣ�����

    /**
     */
    public final static int TASK_RESULT_SUCCESS = 1;// ����ִ�н�����ɹ�

    /**
     */
    public final static int TASK_RESULT_FAILED1 = -1;// ����ִ�н����ʧ��

    /**
     */
    public final static int TASK_RESULT_FAILED2 = -2;// ����ʧ��

    /**
     */
    public final static int TASK_RESULT_FAILED3 = -2023;// ���ļ��������Ѿ���ռ��

    /**
     */
    public final static int TASK_RESULT_FAILED4 = -2024;// ���ļ��б����Ѿ���ʹ��

    public final static int TASK_RESULT_FAILED5 = -2025;// �ļ��и���ʱ�������ļ��и��ƹ���

    // =========================================================================

    // =========================================================================
    /**
     * ������ x_dengjun ����
     */

    /**
     * // ���״̬������
     */
    public final static int AD_STATE_ONLINE = 1;

    /**
     * // ���״̬������
     */
    public final static int AD_STATE_OFFLINE = 2;

    /**
     * // ����������ͣ���ͨ���
     */
    public final static int AD_CONTENTTYPE_NORMAL = 1;

    /**
     * // ����������ͣ��ⲿ���
     */
    public final static int AD_CONTENTTYPE_EXT = 2;

    /**
     * // ����������ͣ����ֹ��
     */

    public final static int AD_CONTENTTYPE_LETTER = 3;

    /**
     * // ��λ���ͣ���������
     */
    public final static int AD_POSITIONTYPE_OPENW = 1;

    /**
     * // ��λ���ͣ������ƶ�
     */
    public final static int AD_POSITIONTYPE_FOLLOWS = 2;

    /**
     * // ��λ���ͣ��̶�λ��
     */
    public final static int AD_POSITIONTYPE_FASTNESS = 3;

    /**
     * // ��λ���ͣ�Ư���ƶ�
     */
    public final static int AD_POSITIONTYPE_FLOAT = 4;

    /**
     * // ��λ���ԣ���ͨ
     */
    public final static int AD_PROPERTY_NORMAL = 1;

    /**
     * // �����ԣ����
     */
    public final static int AD_PPROPERTY_RANDOM = 2;

    /**
     * //���������ͣ��´���
     */
    public final static int AD_OPENMODE_NEWWINDOW = 1;

    /**
     * //�򿪴������ͣ���ǰ����
     */
    public final static int AD_OPENMODE_CURWINDOW = 2;

    /**
     * // ����ļ����ͣ�FLASH
     */
    public final static String AD_FILETYPE_SWF = "1";

    /**
     * // ����ļ����ͣ�ͼƬ
     */
    public final static String AD_FILETYPE_PIC = "2";

    /**
     * // ����ļ����ͣ�swf
     */
    public final static String AD_FILE_SWF = ".swf";

    /**
     * // ����ļ����ͣ�gif
     */
    public final static String AD_FILE_GIF = ".gif";

    /**
     * // ����ļ����ͣ�jpg
     */
    public final static String AD_FILE_JPG = ".jpg";

    /**
     * // ����ļ����ͣ�jpeg
     */
    public final static String AD_FILE_JPEG = ".jpeg";

    /**
     * // ����λ��Դ�ļ�Ŀ¼1
     */
    public final static String AD_FILE_PATH1 = "in";

    /**
     * // ����λ��Դ�ļ�Ŀ¼2
     */
    public final static String AD_FILE_PATH2 = "ad";
    // =========================================================================
    // �������
    /**
     * վ�����
     */

    /**
     * // ��������
     */
    public final static String NEWS_DATA_TYPE = "01";

    /**
     * // ҵ������
     */
    public final static String SERVICE_DATA_TYPE = "00";

    /**
     * // ��������
     */
    public final static String CONTENT_DATA_TYPE = "02";

    /**
     * //�ؼ�������
     */
    public final static String KEYWORD_DATA_TYPE = "03";

    /**
     * // ��������
     */
    public final static String NEWS_BO_KEY = "news";

    /**
     * // ��������
     */
    public final static String SERVICE_BO_KEY = "service";

    /**
     * // ��������
     */
    public final static String CONTENT_BO_KEY = "content";

    /**
     * // �ؼ�������
     */
    public final static String KEYWORD_BO_KEY = "keyword";

    /**
     * // ��������
     */
    public final static String GENERATE_TYPE_ADD = "add";

    /**
     * // ȫ������
     */
    public final static String GENERATE_TYPE_ALL = "all";

    /**
     * // վ��ģ��
     */
    public final static String SITE_TEMPLATE = "sitetemplate";

    /**
     * // վ��ҳ��
     */
    public final static String SITE_PAGE = "sitepage";

    /**
     * // ������Ŀ¼
     */
    public final static String FOLDER_FLAG_INCLUDE = "include";

    /**
     * //��������Ŀ¼
     */
    public final static String FOLDER_FLAG_UNINCLUDE = "unInclude";

    /**
     * //��������Ŀ¼
     */
    public final static String FILE_FLAG_ALL = "all";

    /**
     * //��������Ŀ¼
     */
    public final static String FILE_FLAG_FILE = "file";

    /**
     * //��������Ŀ¼
     */
    public final static String FILE_FLAG_TEMPLATE = "template";

    /**
     * //ҵ������ģ���������
     */
    public final static String ROOT_PATH = "1";

    /**
     * //�»���
     */
    public final static String UNDER_LINE = "_";

    /**
     * //�ָ��
     */
    public final static String PATH_SEP = ">>";

    /**
     * HTTP��ͷ
     */
    public final static String THE_HTTP = "http://";

    /**
     * ��������
     */
    public final static String CONTEXT_NAME = "/site";

    /**
     * ����ָ���
     */
    public final static String PLUGIN_SPLIT = "\\{\\%page\\%\\}";

    /**
     * JSP�ļ���չ��
     */
    public final static String JSP_POSTFIX_NAME = ".jsp";

    /**
     * HTML�ļ���չ��
     */
    public final static String HTML_POSTFIX_NAME = ".html";
    
    /**
     * ��������������ΪmmӦ��
     */
    public final static String BLACK_LIST_MM = "M";
    
    /**
     * ��������������Ϊ��ҵ����Ӧ��
     */
    public final static String BLACK_LIST_CY = "C";
    
    /**
     * ���������������ݱ������ڴ�ҵ������ֵ
     */
    public final static String BLACK_LIST_CONTENT_SUBTYPE_CY = "6";
    
    
    /**
     * ���������������ݱ��в����ڴ�ҵ������ֵ
     */
    public final static String BLACK_LIST_CONTENT_SUBTYPE_MM = "1";
    
    /**
     * Ϊȫƽ̨��ȫ��ͨ��
     */
    public final static String ALL_CITY_PLATFORM = "0000";
    
    /**
     * Ϊȫ��ͨ�õ����ݿⱣ����ʽ
     */
    public final static String ALL_CITY_DATA = "{0000}";
}
