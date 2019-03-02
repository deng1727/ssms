/*
 * 文件名：ResourceConstants.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  该文件定义相关资源，与数据库resourseInfo对应。
 * 修改人： 高宝兵
 * 修改时间：2005/06/20
 * 修改内容：初稿
 */
package com.aspire.ponaadmin.web.constant;

/**
 * <p>Title: 系统常量定义类</p>
 * <p>Description: 所有需要定义到数据库的提示语言等资源的变量定义类。</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: ASPire TECHNOLOGIES (SHENZHEN) LIMITED</p>
 * @author gaobaobing
 * @version 1.0.0.0
 */

public final class ResourceConstants 
{

	/**
	 * 系统内部错误，统一的未知错误提示。
	 */
	public static final String WEB_ERR_SYSTEM = "WEB_ERR_SYSTEM";

	////////////////////////
	//用户管理部分的提示信息 开始

	/**
	 * 附加码错误
	 */
	public static final String WEB_ERR_IMGCODE = "WEB_ERR_IMGCODE";

	/**
	 * 用户已经存在
	 */
	public static final String WEB_ERR_REG_USERNAME = "WEB_ERR_REG_USERNAME";

	/**
	 * 用户不存在
	 */
	public static final String WEB_ERR_USER_NOT_EXISTED = "WEB_ERR_USER_NOT_EXISTED";

	/**
	 * 用户提交过注册信息后（状态已经不是预注册或者注册审核失败），又再次进行提交。
	 */
	public static final String WEB_FILLINFO_ERR = "WEB_FILLINFO_ERR";

	/**
	 * 用户或者密码错误
	 */
	public static final String WEB_INF_INVALID_PWD = "WEB_INF_INVALID_PWD";

	/**
	 * 密码错误
	 */
	public static final String WEB_INF_PWD_ERROR = "WEB_INF_PWD_ERROR";

	/**
	 * 密码复位成功
	 */
	public static final String WEB_INF_RESETPWD_OK = "WEB_INF_RESETPWD_OK";

	/**
	 * 密码复位失败
	 */
	public static final String WEB_INF_RESETPWD_ERR = "WEB_INF_RESETPWD_ERR";

	/**
	 * 用户锁定成功
	 */
	public static final String WEB_INF_LOCKUSER_OK = "WEB_INF_LOCKUSER_OK";

	/**
	 * 用户锁定失败
	 */
	public static final String WEB_INF_LOCKUSER_ERR = "WEB_INF_LOCKUSER_ERR";

	/**
	 * 用户解锁成功
	 */
	public static final String WEB_INF_UNLOCKUSER_OK = "WEB_INF_UNLOCKUSER_OK";

	/**
	 * 用户解锁失败
	 */
	public static final String WEB_INF_UNLOCKUSER_ERR = "WEB_INF_UNLOCKUSER_ERR";

	/**
	 * 修改密码成功
	 */
	public static final String WEB_INF_MODPWD_OK = "WEB_INF_MODPWD_OK";

	/**
	 * 修改密码失败
	 */
	public static final String WEB_INF_MODPWD_ERR = "WEB_INF_MODPWD_ERR";

	/**
	 * 修改个人信息成功
	 */
	public static final String WEB_INF_MODUSERINFO_OK = "WEB_INF_MODUSERINFO_OK";

	/**
	 * 修改个人信息失败
	 */
	public static final String WEB_INF_MODUSERINFO_ERR = "WEB_INF_MODUSERINFO_ERR";

	/**
	 * 注册成功，到了待审核状态。
	 */
	public static final String WEB_INF_REGISTER_SUCC = "WEB_INF_REGISTER_SUCC";

	/**
	 * 通过了用户的注册审核
	 */
	public static final String WEB_INF_REG_CHECK_SUCC = "WEB_INF_REG_CHECK_SUCC";

	/**
	 * 用户注册审核失败
	 */
	public static final String WEB_INF_REG_CHECK_ERR = "WEB_INF_REG_CHECK_ERR";

	/**
	 * 不通过用户的注册审核
	 */
	public static final String WEB_INF_REG_CHECK_FAIL = "WEB_INF_REG_CHECK_FAIL";

	/**
	 * 用户未登录
	 */
	public static final String WEB_ERR_NOT_LOGIN = "WEB_ERR_NOT_LOGIN";

	/**
	 * 用户预注册完毕，提示用户输入详细信息
	 */
	public static final String WEB_INF_REG_USER_DETAIL = "WEB_INF_REG_USER_DETAIL";

	/**
	 * 提示用户审核仍未通过
	 */
	public static final String WEB_INF_USER_WAIT_FOR_CHECK = "WEB_INF_USER_WAIT_FOR_CHECK";

	/**
	 * 提示用户审核不通过
	 */
	public static final String WEB_INF_USER_REG_CHECK_FAIL = "WEB_INF_USER_REG_CHECK_FAIL";

	/**
	 * 提示用户帐号被锁定了
	 */
	public static final String WEB_INF_USER_LOCKED = "WEB_INF_USER_LOCKED";

	/**
	 * 提示用户帐号被锁定了
	 */
	public static final String WEB_INF_PWD_RESETED = "WEB_INF_PWD_RESETED";

	/**
	 * 用户注册的时候系统保存用户注册信息失败
	 */
	public static final String WEB_INF_REG_FAIL = "WEB_INF_REG_FAIL";

	//用户管理部分的提示信息 结束
	////////////////////////

	////////////////////////
	//权限管理部分的提示信息 开始

	/**
	 * 保存角色信息成功
	 */
	public static final String WEB_INF_SAVEROLE_OK = "WEB_INF_SAVEROLE_OK";

	/**
	 * 保存角色信息失败
	 */
	public static final String WEB_INF_SAVEROLE_ERR = "WEB_INF_SAVEROLE_ERR";

	/**
	 * 权限设定成功
	 */
	public static final String WEB_INF_SAVERIGHT_OK = "WEB_INF_SAVERIGHT_OK";

	/**
	 * 权限设定失败
	 */
	public static final String WEB_INF_SAVERIGHT_ERR = "WEB_INF_SAVERIGHT_ERR";

	/**
	 * 删除角色成功
	 */
	public static final String WEB_INF_DELROLE_OK = "WEB_INF_DELROLE_OK";

	/**
	 * 删除角色失败
	 */
	public static final String WEB_INF_DELROLE_ERR = "WEB_INF_DELROLE_ERR";

	/**
	 * 角色被使用，无法删除
	 */
	public static final String WEB_ERR_ROLE_USED = "WEB_ERR_ROLE_USED";

	/**
	 * 角色名称已经存在！
	 */
	public static final String WEB_ERR_ROLE_EXIST = "WEB_ERR_ROLE_EXIST";

	/**
	 * 设定用户角色成功
	 */
	public static final String WEB_INF_USERROLE_OK = "WEB_INF_USERROLE_OK";

	/**
	 * 设定用户角色失败
	 */
	public static final String WEB_INF_USERROLE_ERR = "WEB_INF_USERROLE_ERR";

	/**
	 * 设定角色用户成功
	 */
	public static final String WEB_INF_ROLE_USER_OK = "WEB_INF_ROLE_USER_OK";

	/**
	 * 设定角色用户失败
	 */
	public static final String WEB_INF_ROLE_USER_ERR = "WEB_INF_ROLE_USER_ERR";

	/**
	 * 无权限进行操作
	 */
	public static final String WEB_INF_NO_RIGHT = "WEB_INF_NO_RIGHT";

	//权限管理部分的提示信息 结束

	/**
	 * 新增黑名单成功
	 */
	public static final String WEB_INF_ADD_BLACK_OK = "WEB_INF_ADD_BLACK_OK";
	/**
	 * 新增黑名单是失败
	 */
	public static final String WEB_INF_ADD_BLACK_FAIL = "WEB_INF_ADD_BLACK_FAIL";	
	
	/**
	 * 黑名单已经存在
	 */
	public static final String WEB_INF_BLACK_EXIST = "WEB_INF_BLACK_EXIST";
	
	/**
	 * 修改黑名单成功
	 */
	public static final String WEB_INF_MODIFY_BLACK_OK = "WEB_INF_MODIFY_BLACK_OK";	
	/**
	 * 修改黑名单成功
	 */
	public static final String WEB_INF_MODIFY_BLACK_FAIL = "WEB_INF_MODIFY_BLACK_FAIL";	
	
	public static final String WEB_INF_DEL_BLACK_OK = "WEB_INF_DEL_BLACK_OK";
	public static final String WEB_INF_DEL_BLACK_FAIL = "WEB_INF_DEL_BLACK_FAIL";
}