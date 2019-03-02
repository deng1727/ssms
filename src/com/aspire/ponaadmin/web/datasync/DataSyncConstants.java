package com.aspire.ponaadmin.web.datasync;

public class DataSyncConstants
{
	/**
	 * �����ɹ�
	 */
	public static final int SUCCESS_ADD=1;
	public static final int SUCCESS_UPDATE=2;
	public static final int SUCCESS_DEL=3;
	/**
	 * δ֪����
	 */
	public static final int FAILURE=5;
	/**
	 * ���ݴ��󣬲�һ��
	 */
	public static final int FAILURE_DATA_ERROR=6;
	
	/**
	 * ����ʧ��
	 */
	public static final int FAILURE_ADD=11;
	public static final int FAILURE_UPDATE=12;
	public static final int FAILURE_DEL=13;
	/**
	 * changeType������
	 */
	public static final int FAILURE_NOT_CHANGETYPE=15;
	
	/**
	 * ����ʱ��ϵͳ�Ѿ����ڣ�����ʧ�ܡ�
	 */
	public static final int FAILURE_ADD_EXIST=21;
	/**
	 * ����ʱ��ϵͳ�����ڣ�����ʧ��
	 */
	public static final int FAILURE_UPDATE_NOT_EXIST=22;
	/**
	 * ɾ��ʱ��ϵͳ�����ڣ�����ʧ��
	 */
	public static final int FAILURE_DEL_NOT_EXIST=23;
	/**
	 * ��ͬ��ʱ�������ڶ�Ӧ�����ݴ���
	 */
	public static final int FAILURE_NOT_EXIST=25;
	

	/**
	 * ���ͨ��
	 */
	public static final int CHECK_SUCCESSFUL=1;

	/**
	 * ��鲻ͨ��
	 */
	public static final int CHECK_FAILED=0;
	
	/**
	 * ��������
	 */
	public static final int TASK_RUNNING=1;
	/**
	 * ������С�
	 */
	public static final int TASK_FREE=0;
	
	public static final int EXCEPTION_FTP=1;
	public static final int EXCEPTION_FILE_NOT_EXISTED=2;
	public static final int EXCEPTION_INNER_ERR=3;
	
	

}
