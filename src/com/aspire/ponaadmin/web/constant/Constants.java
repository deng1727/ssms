/*
 * 文件名：Constants.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  该文件定义相关常量
 * 修改人： 高宝兵
 * 修改时间：2005/06/20
 * 修改内容：初稿
 */
package com.aspire.ponaadmin.web.constant;

/**
 * <p>
 * Title: 系统常量定义类
 * </p>
 * <p>
 * Description: 本类主要用于定义页面扭转的常量定义类等，不可硬编码。
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
     * struts里面定义的公用成功页面
     */
    public static final String FORWARD_COMMON_SUCCESS = "commonSuccess";

    /**
     * struts里面定义的公用错误页面
     */
    public static final String FORWARD_COMMON_FAILURE = "commonFailure";

    /**
     * 公用成功页面、失败页面的跳转url参数名称
     */
    public static final String PARA_GOURL = "goURL";

    /**
     * 公用成功页面、失败页面的目标框架targetFrame参数名称
     */
    public static final String PARA_TARGETFRAME = "targetFrame";

    /**
     * 公用成功页面、失败页面，是否需要显示关闭按钮。
     */
    public static final String PARA_ISCLOSE = "isClose";

    /**
     * 公用成功页面、失败页面，是否是独立显示页面，独立显示的话要显示头和尾。
     */
    public static final String PARA_ISSTANDALONE = "isStandAlone";

    /**
     * 公用成功页面、失败页面，需要刷新父页面中的树的节点，如果不指定这个参数，将不会刷新树。
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
     * 公用错误页面
     */
    public static final String FAIL_PAGE = "/web/common/error.jsp";

    /**
     * 公用成功页面
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
     * 临时缓冲区大小
     */
    public static final int TEMP_BUF_SIZE = 1024;

    // ==========================================================

    /**
     * 业务分类跟节点标示
     */
    public static final int SERV_CATE_ROOT = 1;

    /**
     * 业务分类跟节点标示
     */
    public static final int KEYWORD_CATE_ROOT = 5;

    /**
     * 业务未分类根节点
     */
    public static final int MYSERV_CATE_ROOT = 2;

    /**
     * 业务分类置顶标志
     */
    public static final int DIR_SERV_TOPFLAG = 0;

    /**
     * SP指标项数据标识
     */
    public static final int TOP_DATA_TYPTE_SP = 1;

    /**
     * 业务指标项数据标识
     */
    public static final int TOP_DATA_TYPTE_SERV = 2;

    // ==========================================================
    // 站点------------
    // 胡春雨添加
    // ==========================================================

    /**
     * 删除标记:真
     */
    public static final int DELETE_TAG_TRUE = 1;

    /**
     * 删除标记:假
     */
    public static final int DELETE_TAG_FALSE = 0;

    /**
     * 默认的排序值
     */
    public static final int DEFAULT_SORTFLAG = 100;

    /**
     * 行回车符号。
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 目录分隔符
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    // ==========================================================

    /**
     * 选择站点路径的URL;
     */
    public static final String SELECT_SITE_URL =
                        "/web/sitemgr/websiteList.do?goURL=/web/sitemgr/main.jsp";

    /**
     * 业务分类跟节点标示
     */
    public static final int NEWS_CATE_ROOT = 1;

    // =========================================================================

    /**
     * 异步任务管理 x_dengjun 增加
     */
    public final static String TASK_ISNULL = "01";// 任务不能为空

    /**
     */
    public final static String TASK_THISTASK_EXIST = "02";// 任务已经存在

    /**
     */
    public final static String TASK_ID_ISNULL = "03";// 任务ID不能为空

    /**
     */
    public final static String TASK_USERID_ISNULL = "04";// 任务userID不能为空

    /**
     */
    public final static String TASK_ACTIONTYPE_ISNULL = "05";// 任务actionType不能为空

    /**
     */
    public final static String TASK_OVERMAXTASK = "06";// 超过配置的最大任务数

    /**
     */
    public final static String TASK_GETMAXTASK_ERROR = "07";// 获取最大任务数配置错误

    /**
     */
    public final static int TASK_DEFAULT_MAXTASK = 20;// 默认的最大任务数

    /**
     */
    public final static int TASK_PROCESSING = 1;// 任务状态，运行中

    /**
     */
    public final static int TASK_PROCESSED = 2;// 任务状态，完成

    /**
     */
    public final static int TASK_REQUESTSTOP = 3;// 任务状态，请求终止

    /**
     */
    public final static int TASK_STOP = 4;// 任务状态，被终止

    /**
     */
    public final static String TASK_TYPE_COPY = "10";// 任务类型，拷贝

    /**
     */
    public final static String TASK_TYPE_RELEASE = "20";// 任务类型，发布

    /**
     */
    public final static String TASK_TYPE_GENRRATE = "30";// 任务类型，生成

    /**
     */
    public final static int TASK_RESULT_SUCCESS = 1;// 任务执行结果，成功

    /**
     */
    public final static int TASK_RESULT_FAILED1 = -1;// 任务执行结果，失败

    /**
     */
    public final static int TASK_RESULT_FAILED2 = -2;// 加锁失败

    /**
     */
    public final static int TASK_RESULT_FAILED3 = -2023;// 当文件夹名称已经被占用

    /**
     */
    public final static int TASK_RESULT_FAILED4 = -2024;// 当文件夹别名已经被使用

    public final static int TASK_RESULT_FAILED5 = -2025;// 文件夹复制时不符合文件夹复制规则

    // =========================================================================

    // =========================================================================
    /**
     * 广告管理 x_dengjun 增加
     */

    /**
     * // 广告状态，正常
     */
    public final static int AD_STATE_ONLINE = 1;

    /**
     * // 广告状态，下线
     */
    public final static int AD_STATE_OFFLINE = 2;

    /**
     * // 广告内容类型，普通广告
     */
    public final static int AD_CONTENTTYPE_NORMAL = 1;

    /**
     * // 广告内容类型，外部广告
     */
    public final static int AD_CONTENTTYPE_EXT = 2;

    /**
     * // 广告内容类型，文字广告
     */

    public final static int AD_CONTENTTYPE_LETTER = 3;

    /**
     * // 版位类型：弹出窗口
     */
    public final static int AD_POSITIONTYPE_OPENW = 1;

    /**
     * // 版位类型：随屏移动
     */
    public final static int AD_POSITIONTYPE_FOLLOWS = 2;

    /**
     * // 版位类型：固定位置
     */
    public final static int AD_POSITIONTYPE_FASTNESS = 3;

    /**
     * // 版位类型：漂浮移动
     */
    public final static int AD_POSITIONTYPE_FLOAT = 4;

    /**
     * // 版位属性：普通
     */
    public final static int AD_PROPERTY_NORMAL = 1;

    /**
     * // 版属性：随机
     */
    public final static int AD_PPROPERTY_RANDOM = 2;

    /**
     * //开窗口类型：新窗口
     */
    public final static int AD_OPENMODE_NEWWINDOW = 1;

    /**
     * //打开窗口类型：当前窗口
     */
    public final static int AD_OPENMODE_CURWINDOW = 2;

    /**
     * // 广告文件类型，FLASH
     */
    public final static String AD_FILETYPE_SWF = "1";

    /**
     * // 广告文件类型，图片
     */
    public final static String AD_FILETYPE_PIC = "2";

    /**
     * // 广告文件类型，swf
     */
    public final static String AD_FILE_SWF = ".swf";

    /**
     * // 广告文件类型，gif
     */
    public final static String AD_FILE_GIF = ".gif";

    /**
     * // 广告文件类型，jpg
     */
    public final static String AD_FILE_JPG = ".jpg";

    /**
     * // 广告文件类型，jpeg
     */
    public final static String AD_FILE_JPEG = ".jpeg";

    /**
     * // 广告版位资源文件目录1
     */
    public final static String AD_FILE_PATH1 = "in";

    /**
     * // 广告版位资源文件目录2
     */
    public final static String AD_FILE_PATH2 = "ad";
    // =========================================================================
    // 张敏添加
    /**
     * 站点管理
     */

    /**
     * // 新闻类型
     */
    public final static String NEWS_DATA_TYPE = "01";

    /**
     * // 业务类型
     */
    public final static String SERVICE_DATA_TYPE = "00";

    /**
     * // 内容类型
     */
    public final static String CONTENT_DATA_TYPE = "02";

    /**
     * //关键字类型
     */
    public final static String KEYWORD_DATA_TYPE = "03";

    /**
     * // 新闻类型
     */
    public final static String NEWS_BO_KEY = "news";

    /**
     * // 新闻类型
     */
    public final static String SERVICE_BO_KEY = "service";

    /**
     * // 内容类型
     */
    public final static String CONTENT_BO_KEY = "content";

    /**
     * // 关键字类型
     */
    public final static String KEYWORD_BO_KEY = "keyword";

    /**
     * // 增量生成
     */
    public final static String GENERATE_TYPE_ADD = "add";

    /**
     * // 全量生成
     */
    public final static String GENERATE_TYPE_ALL = "all";

    /**
     * // 站点模板
     */
    public final static String SITE_TEMPLATE = "sitetemplate";

    /**
     * // 站点页面
     */
    public final static String SITE_PAGE = "sitepage";

    /**
     * // 包含子目录
     */
    public final static String FOLDER_FLAG_INCLUDE = "include";

    /**
     * //不包含子目录
     */
    public final static String FOLDER_FLAG_UNINCLUDE = "unInclude";

    /**
     * //不包含子目录
     */
    public final static String FILE_FLAG_ALL = "all";

    /**
     * //不包含子目录
     */
    public final static String FILE_FLAG_FILE = "file";

    /**
     * //不包含子目录
     */
    public final static String FILE_FLAG_TEMPLATE = "template";

    /**
     * //业务数据模版根分类名
     */
    public final static String ROOT_PATH = "1";

    /**
     * //下划线
     */
    public final static String UNDER_LINE = "_";

    /**
     * //分割符
     */
    public final static String PATH_SEP = ">>";

    /**
     * HTTP标头
     */
    public final static String THE_HTTP = "http://";

    /**
     * 上下文名
     */
    public final static String CONTEXT_NAME = "/site";

    /**
     * 插件分隔符
     */
    public final static String PLUGIN_SPLIT = "\\{\\%page\\%\\}";

    /**
     * JSP文件扩展名
     */
    public final static String JSP_POSTFIX_NAME = ".jsp";

    /**
     * HTML文件扩展名
     */
    public final static String HTML_POSTFIX_NAME = ".html";
    
    /**
     * 黑名单内容类型为mm应用
     */
    public final static String BLACK_LIST_MM = "M";
    
    /**
     * 黑名单内容类型为创业大赛应用
     */
    public final static String BLACK_LIST_CY = "C";
    
    /**
     * 黑名单内容在内容表中属于创业大赛的值
     */
    public final static String BLACK_LIST_CONTENT_SUBTYPE_CY = "6";
    
    
    /**
     * 黑名单内容在内容表中不属于创业大赛的值
     */
    public final static String BLACK_LIST_CONTENT_SUBTYPE_MM = "1";
    
    /**
     * 为全平台或全国通用
     */
    public final static String ALL_CITY_PLATFORM = "0000";
    
    /**
     * 为全国通用的数据库保存型式
     */
    public final static String ALL_CITY_DATA = "{0000}";
}
