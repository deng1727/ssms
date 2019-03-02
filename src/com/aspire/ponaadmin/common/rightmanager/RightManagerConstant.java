package com.aspire.ponaadmin.common.rightmanager ;

/**
 * <p>权限管理组件的常量定义类</p>
 * <p>权限管理组件的常量定义类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightManagerConstant
{

    /**
     * 权限类型：操作权限
     */
    public static final int TYPE_RIGHT = 11;

    /**
     * 权限类型：站点目录权限
     */
    public static final int TYPE_SITE = 12;

    /**
     * 用来提示权限检查不通过的页面。
     */
    public static final String ERROR_PAGE = "/web/common/error.jsp";

    /**
     * 用来权限检查用来区分操作的参数：action
     */
    public static final String URL_PARA_ACTION = "action";

    /**
     * 用来权限检查用来区分操作的参数：from
     */
    public static final String URL_PARA_FROM = "from";

    /////////////////////权限检查结果常量定义  开始

    /**
     * 检查通过，用户具有权限
     */
    public static final int RIGHTCHECK_PASSED = 0;

    /**
     * 检查不通过，用户没有权限
     */
    public static final int RIGHTCHECK_NO_RIGHT = 1;

    /**
     * 检查不通过，没有用户信息（没有登录）
     */
    public static final int RIGHTCHECK_NO_USERINFO = 2;
    /////////////////////权限检查结果常量定义  结束

    ///////////权限管理操作结果码   开始/////////////

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
     * 角色被使用
     */
    public static final int ROLE_USED = 1501 ;

    /**
     * 角色名称已经存在
     */
    public static final int ROLE_NAME_EXIST = 1502 ;

   ///////////权限管理操作结果码   结束/////////////
}
