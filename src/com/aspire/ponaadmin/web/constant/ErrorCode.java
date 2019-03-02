package com.aspire.ponaadmin.web.constant ;

/**
 * <p>错误代码类</p>
 * <p>统一定义系统中需要用到的所有错误代码</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ErrorCode
{

    /**
     * 成功
     */
    public static final int SUCC = 0 ;

    /**
     * 系统内部错误
     */
    public static final int FAIL = 1 ;

    /**
     * 参数非法
     */
    public static final int INVALID_PARA = 2 ;

    /**
     * 数据库访问失败
     */
    public static final int DB_ERR = 3 ;

    /**
     * 文件IO失败
     */
    public static final int FILE_IO_ERR = 4 ;

//--节点错误-------------------------------------------------
//--胡春雨 增加-----------------------------------------------

    /**
     * 节点添加失败
     */
    public static final int WEBSITE_NODE_ADD = 2001 ;

    /**
     * 节点编辑失败
     */
    public static final int WEBSITE_NODE_MODIFY = 2002 ;

    /**
     * 节点删除失败
     */
    public static final int WEBSITE_NODE_DEL = 2003 ;

    /**
     * 节点命名重复
     */
    public static final int WEBSITE_NODE_DOUBLE_NAME = 2004 ;

    /**
     * 取出节点集合失败
     */
    public static final int WEBSITE_GET_LIST = 2005 ;

    /**
     * 站点初试化数据失败
     */
    public static final int WEBSITE_INIT = 2006 ;

    /**
     * 节点别名重复
     */
    public static final int WEBSITE_NODE_DOUBLE_ALIASNAME = 2007 ;

    /**
     * 文章内容添加失败
     */
    public static final int WEBSITE_FILECONTENT_ADD = 2008 ;

    /**
     * 文章内容编辑失败
     */
    public static final int WEBSITE_FILECONTENT_MODIFY = 2009 ;

    /**
     * 写文件失败
     */
    public static final int WEBSITE_WRITEFILE_ERROR = 2010 ;

    /**
     * 逻辑删除节点失败
     */
    public static final int WEBSITE_NODE_LOGIC_DELETE_ERROR = 2011 ;

    /**
     * 节点状态更新失败
     */
    public static final int WEBSITE_NODE_STATE_UPDATE_ERROR = 2012 ;

    /**
     * 站点属性编辑失败
     */
    public static final int WEBSITE_PROPERTY_UPDATE_ERROR = 2013 ;

    /**
     * 插件添加失败
     */
    public static final int PLUGIN_ADD = 2014 ;

    /**
     * 插件名重复
     */
    public static final int PLUGIN_DOUBLE_NAME = 2019 ;

    /**
     * 插件编辑失败
     */
    public static final int PLUGIN_MODIFY = 2015 ;

    /**
     * 插件删除失败
     */
    public static final int PLUGIN_DEL = 2016 ;

    /**
     * 插件初试化
     */
    public static final int PLUGIN_INIT = 2017 ;

    /**
     * 插件查询
     */
    public static final int PLUGIN_SEARCH = 2018 ;

    /**
     * 当文件名称已经被占用
     */
    public static final int FILE_NAME_EXIST = 2019 ;

    /**
     * 当文件别名已经被占用
     */
    public static final int FILE_ALIANAME_EXIST = 2020 ;

    /**
     * 当模板名称已经被占用
     */
    public static final int TEMPLATE_NAME_EXIST = 2021 ;

    /**
     * 当模板别名已经被占用
     */
    public static final int TEMPLATE_ALIANAME_EXIST = 2022 ;

    /**
     * 当文件夹名称已经被占用
     */
    public static final int WEB_INF_SITE_FOLDER_NAME_EXISTED = 2023 ;

    /**
     * 当文件夹别名已经被使用
     */
    public static final int WEB_INF_SITE_FOLDER_ALIASNAME_EXISTED = 2024 ;
    
    /**
     * 文件夹复制时不符合文件夹复制规则
     */
    public static final int SITE_FOLDER_COPY_PATH_INCLUDE = 2025;
//--   错误-------------------------------------------------

    /**
     * SP企业代码已经存在
     */
    public static final int SP_EXIST_ERR = 3001 ;

    /**
     * 导入数据格式错误
     */
    public static final int SP_FMT_ERR = 3002 ;

        /**
         * 文件已经存在
         */
        public static final int FILE_EXIST_ERR = 3003 ;

    //--   错误-------------------------------------------------
    //--吴付维 增加-----------------------------------------------

    /**
     * FTP异常。
     */
    public static final int FTP_ERR = 4001 ;

    /**
     * 获取待产生的页面异常。
     */
    public static final int GET_WAITING_BUILD_PAGE_ERR = 4002 ;

    /**
     * 页面产生异常。
     */
    public static final int BUILD_PAGE_ERR = 4003 ;

    /**
     * 文件拷贝异常。
     */
    public static final int FILE_COPY_ERR = 4004 ;

    /**
     * 页面生成异常。
     */
    public static final int BUILDING_PAGE_ERR = 4005 ;

    /**
     * 获取节点下的节点列表异常。
     */
    public static final int GET_NODEID_LIST_ERR = 4006 ;

    /**
     * 获取站点路径异常。
     */
    public static final int GET_WEBSIT_PATH_ERR = 4007 ;

    /**
     * 获取资源同步目录列表异常。
     */
    public static final int GET_RSYNC_DIR_LIST_ERR = 4007 ;

    /**
     * 时间格式化异常。
     */
    public static final int DATE_FORMAT_ERR = 4008;

    /**
     * 插件异常。
     */
    public static final int PUGIN_ERR = 4009;

    //--毛炳贵 增加-----------------------------------------------

    /**
     * 更新业务LOGO和描述信息;
     */
    public static final int MOD_SERV_ERR = 5000;

    /**
     * 更新业务LOGO失败
     */
    public static final int MOD_SERV_LOGO_ERR = 5001;

    /**
     * 更新业务描述信息失败
     */
    public static final int MOD_SERV_DESC_ERR = 5002;

    /**
     * 指标项数据格式错误
     */
    public static final int TOP_DATA_FMT_ERR = 5003;

    /**
     * 指标项已经存在
     */
    public static final int TOP_KEY_EXIST = 5004;

    /**
     * 同层目录中业务分类名称已经存在
     */
    public static final int CATE_NAME_EXIST = 5005;

    //--董小康 增加-----------------------------------------------

    /**
     * 报表默认指标项不能删除
     */
    public static final int TOP_KEY_DEL_ERR = 5006 ;

    /**
     * 调查主题已经存在
     */
    public static final int POLL_TITLE_EXIST = 5007 ;

    /**
     * 调查主题选项已经存在
     */
    public static final int POLL_OPTION_EXIST = 5008 ;

    /**
     * 调查分类已经存在
     */
    public static final int POLL_TYPE_EXIST = 5009 ;

    /**
     * 调查分类下有调查主题
     */
    public static final int POLL_TYPE_DATA = 5010 ;

    /**
     * 节点处于加锁中，不能再加锁。
     */
    public static final int LOCK_APPLY_ERROR = 6000;

}
