package com.aspire.ponaadmin.web.datasync;

public class DataSyncConstants
{
	/**
	 * 操作成功
	 */
	public static final int SUCCESS_ADD=1;
	public static final int SUCCESS_UPDATE=2;
	public static final int SUCCESS_DEL=3;
	/**
	 * 未知错误
	 */
	public static final int FAILURE=5;
	/**
	 * 数据错误，不一致
	 */
	public static final int FAILURE_DATA_ERROR=6;
	
	/**
	 * 操作失败
	 */
	public static final int FAILURE_ADD=11;
	public static final int FAILURE_UPDATE=12;
	public static final int FAILURE_DEL=13;
	/**
	 * changeType不存在
	 */
	public static final int FAILURE_NOT_CHANGETYPE=15;
	
	/**
	 * 新增时，系统已经存在，操作失败。
	 */
	public static final int FAILURE_ADD_EXIST=21;
	/**
	 * 更新时，系统不存在，操作失败
	 */
	public static final int FAILURE_UPDATE_NOT_EXIST=22;
	/**
	 * 删除时，系统不存在，操作失败
	 */
	public static final int FAILURE_DEL_NOT_EXIST=23;
	/**
	 * 榜单同步时，不存在对应的内容错误
	 */
	public static final int FAILURE_NOT_EXIST=25;
	

	/**
	 * 检查通过
	 */
	public static final int CHECK_SUCCESSFUL=1;

	/**
	 * 检查不通过
	 */
	public static final int CHECK_FAILED=0;
	
	/**
	 * 任务运行
	 */
	public static final int TASK_RUNNING=1;
	/**
	 * 任务空闲。
	 */
	public static final int TASK_FREE=0;
	
	public static final int EXCEPTION_FTP=1;
	public static final int EXCEPTION_FILE_NOT_EXISTED=2;
	public static final int EXCEPTION_INNER_ERR=3;
	
	

}
