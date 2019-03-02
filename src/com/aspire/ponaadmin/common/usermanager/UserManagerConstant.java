package com.aspire.ponaadmin.common.usermanager;

/**
 * <p>用户管理组件的常量定义类</p>
 * <p>用户管理组件的常量定义类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserManagerConstant
{
    /////存放到session中的常量定义

    /**
     * 存放到session中的用户信息类的名称
     */
    static final String USER_SESSION_KEY = "PONAADMIN_WEB_USERINFO";

    /**
     * 随机生成的数字图片的值
     */
    static final String IMAGE_CODE = "image_code";

    /////用户状态定义//////

    /**
     * 用户状态-正常
     */
    public static final int STATE_NORMAL = 10;

    /**
     * 用户状态-预注册
     */
    public static final int STATE_PRE_REGISTER = 11;

    /**
     * 用户状态-待审核
     */
    public static final int STATE_TO_BE_CHECK = 12;

    /**
     * 用户状态-审核不通过
     */
    public static final int STATE_CHECK_FAIL = 13;

    /**
     * 用户状态-被锁定
     */
    public static final int STATE_LOCKED = 14;

    /**
     * 用户状态-密码被复位
     */
    public static final int STATE_PWD_RESET = 15;

	////// 用户性别定义 ////////

	/**
	 * 性别--男
	 */
    public static final String SEX_MALE = "M";

	/**
	 * 性别--女
	 */
    public static final String SEX_FEMALE = "F";

	////// 证件类型定义 ///////

	/**
	 * 证件类型--身份证
	 */
    public static final String CERT_TYPE_IDCARD = "10";

    ///////////用户管理操作结果码   开始/////////////

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
     * 用户不存在
     */
    public static final int USER_NOT_EXISTED = 1002 ;

    /**
     * 密码错误
     */
    public static final int INVALID_PWD = 1003 ;

    /**
     * 用户被锁定了
     */
    public static final int USER_LOCKED = 1004 ;

    /**
     * 注册审核不通过
     */
    public static final int REGISTER_CHECK_FAILED = 1005 ;

    /**
     * 注册请求还未审核
     */
    public static final int REGISTER_NOT_CHECK = 1006 ;

    /**
     * 密码被复位
     */
    public static final int PWD_RESETED = 1007 ;

    /**
     * 用户已经存在
     */
    public static final int USER_EXISTED = 1008 ;
    
    /**
     * 渠道商状态--正常
     */
    public static final String CHANNEL_STATUS_NORMAL = "0";
    /**
     * 渠道商状态--下线
     */
    public static final String CHANNEL_STATUS_DOWN = "1";
    ///////////用户管理操作结果码   结束/////////////

}
