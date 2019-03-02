package com.aspire.ponaadmin.web.repository ;

/**
 * <p>资源管理的常量类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class RepositoryConstants
{

    /**
     * 资源类型：基本类型
     */
    public static final String TYPE_BASE = "nt:base";

    /**
     * 资源类型：分类类型
     */
    public static final String TYPE_CATEGORY = "nt:category";

    /**
     * 资源类型：引用类型
     */
    public static final String TYPE_REFERENCE = "nt:reference";
    /**
     * 资源类型：引用类型
     */
    public static final String TYPE_GCONTENT = "nt:gcontent";
    
    /**
     * 资源类型：彩铃类型
     */
    public static final String TYPE_COLORRING = "nt:gcontent:colorring";
    
    /**
     * 资源类型：资讯类型
     */
    public static final String TYPE_NEWS = "nt:gcontent:news";
    
    /**
     * 资源类型：应用游戏类型
     */
    public static final String TYPE_APPGAME = "nt:gcontent:appGame";

    /**
     * 资源类型：应用主题类型
     */
    public static final String TYPE_APPTHEME = "nt:gcontent:appTheme";

    /**
     * 资源类型：应用软件类型
     */
    public static final String TYPE_APPSOFTWARE = "nt:gcontent:appSoftWare";
    
    /**
     * 资源类型：A8音乐类型
     */
    public static final String TYPE_AUDIO = "nt:gcontent:audio";
    /**
     * 资源类型：基地读书类型
     */
    public static final String TYPE_READ = "nt:gcontent:read";
    
    /**
     * 资源分类的根节点的id
     */
    public static final String ROOT_CATEGORY_ID = "701";

    /**
     * 资源内容存储分类的根节点的id
     */
    public static final String ROOT_CONTENT_ID = "702";
    
    /**
     * 资源分类：彩铃频道的id
     */
    public static final String ROOT_CATEGORY_GCOLORRING_ID = "1006";

    /**
     * 资源分类：软件频道的id
     */
    public static final String ROOT_CATEGORY_GAPPSOFTWARE_ID = "1007";
    
    /**
     * 资源分类：主题频道的id
     */
    public static final String ROOT_CATEGORY_GAPPTHEME_ID = "1008";
    
    /**
     * 资源分类：游戏频道的id
     */
    public static final String ROOT_CATEGORY_GAPPGAME_ID = "1009";

    /**
     * 资源分类：资讯频道的id
     */
    public static final String ROOT_CATEGORY_GNEWS_ID = "1010";
    
    /**
     * 资源分类：应用频道的id
     */
    public static final String ROOT_CATEGORY_GAPP_ID = "1011";
    
    /**
     * 资源分类：a8音乐频道的id
     */
    public static final String ROOT_CATEGORY_GAUDIO_ID = "1012";
    
    /**
     * 资源分类：机型推荐根分类的id
     */
    public static final String ROOT_CATEGORY_DEVICE_ID = "1013";
    
    /**
     * 资源分类：视频频道的id
     */
    public static final String ROOT_CATEGORY_GVIDEO_ID = "1014";
    
    /**
     * 资源分类： 图书频道的id
     */
    public static final String ROOT_CATEGORY_BOOK_ID = "1015";
    
    /**
     * 资源分类：基地音乐频道的id
     */
    public static final String ROOT_CATEGORY_MUSIC_ID = "1016";
    /**
     * 资源分类：基地读书频道的id
     */
    public static final String ROOT_CATEGORY_READ_ID = "1017";

	    /**
     * 资源分类：动漫的id
     * add by tungke for cmnet comic 
     * 
     */
    public static final String ROOT_CATEGORY_COMIC_ID = "1018";
    
    /**
     * 资源分类：基地读书分类集合的id
     */
    public static final String ROOT_CATEGORY_READ_SUBCATE_ID = "2017";
    /**
     * 资源分类：基地读书编辑推荐榜的id
     */
    public static final String ROOT_CATEGORY_READ_TOP_ID = "2018";
    /**
     * 资源分类：基地读书专题分类的id
     */
    public static final String ROOT_CATEGORY_READ_CATALOG_ID = "2019";
    
    /**
     * 资源分类：应用频道子频道－软件
     */
    public static final String ROOT_CHILDCATEGORY_GAME_ID = "2001";

    /**
     * 资源分类：应用频道子频道－主题
     */
    public static final String ROOT_CHILDCATEGORY_THEME_ID = "2002";
    
    /**
     * 资源分类：应用频道子频道－游戏
     */
    public static final String ROOT_CHILDCATEGORY_SOFTWARE_ID = "2003";
    
    /**
     * 资源分类：应用频道子频道－移动专区
     */
    public static final String ROOT_CHILDCATEGORY_MOBILEZONE_ID = "2004";
    
    /**
     * 资源分类：彩铃频道子频道－其他分类：
     */
    public static final String ROOT_CHILDCATEGORY_COLORRING_SPECIALID = "2009";
    
    /**
     * 资源分类：基地音乐子频道－排行榜分类：2016
     */
    public static final String ROOT_CATEGORY_MUSIC_TOPLIDT_ID="2016";
    
    /**
     * 资源分类：终端音乐频道子频道－风格
     */
    public static final String ROOT_CHILDCATEGORY_STYLE_ID = "3001";

    /**
     * 资源分类：终端音乐频道子频道－语言
     */
    public static final String ROOT_CHILDCATEGORY_LANGUAGE_ID = "3002";
    
    /**
     * 资源分类：终端音乐频道子频道－地区
     */
    public static final String ROOT_CHILDCATEGORY_ZONE_ID = "3003";
    
    /**
     * 根节点的id
     */
    static final String ROOT_NODE_ID = "100";

    /**
     * 根节点的path
     */
    static final String ROOT_NODE_PATH = "{100}";

    /**
     * 操作运算符号：=
     */
    public static final String OP_EQUAL = "=";
    /**
     * 操作运算符号：忽略大小写相等。目前该运算符只适合商品查询,比较对象需要先转化为大写
     */
    public static final String OP_LIKE_IgnoreCase="like_ignore_case";

    /**
     * 操作运算符号：like
     */
    public static final String OP_LIKE = "like";

    /**
     * 操作运算符号：!=
     */
    public static final String OP_NOT_EQUAL = "!=";

    //搜索参数模式定义

    /**
     * 搜索参数模式：and
     */
    public static final String SEARCH_PARAM_MODE_AND = "and";

    /**
     * 搜索参数模式：or
     */
    public static final String SEARCH_PARAM_MODE_OR = "or";

    //搜索括号模式定义

    /**
     * 搜索括号：无内容
     */
    public static final String SEARCH_PARAM_BRACKET_NONE = "";

    /**
     * 搜索括号： 左括号
     */
    public static final String SEARCH_PARAM_BRACKET_LEFT = "(";

    /**
     * 搜索括号： 右括号
     */
    public static final String SEARCH_PARAM_BRACKET_RIGHT = ")";

    //排序模式定义

    /**
     * 排序模式定义：默认
     */
    public static final String ORDER_TYPE_DEFAULT = "";

    /**
     * 排序模式定义：升序
     */
    public static final String ORDER_TYPE_ASC = "asc";

    /**
     * 排序模式定义：降序
     */
    public static final String ORDER_TYPE_DESC = "desc";
    
    /**
     * 聚合函数操作：最大值
     */
    public static final String CONVERGE_TYPE_MAX = "max";
    
    /**
     * 聚合函数操作：最小值
     */
    public static final String CONVERGE_TYPE_MIN = "min";
    /**
     * 商品新增时，VARIATION标识值。
     */
    public static final int VARIATION_NEW=99999;
    
    /**
     * 是紧急上线应用
     */
    public static final String SYN_HIS_YES="1";
    
    /**
     * 不是紧急上线应用
     */
    public static final String SYN_HIS_NO="0";
    
    /**
     * 紧急上线应用的上架操作类型
     */
    public static final String SYN_ACTION_ADD="1";
    
    /**
     * 紧急上线应用的下架操作类型
     */
    public static final String SYN_ACTION_DEL="2";

}
