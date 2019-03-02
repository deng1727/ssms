package com.aspire.dotcard.syncAndroid.common;

public class Constant {
	public final static int CSSP_FILETYPE_PUSH=1;//t_a_cssp_log中表示类似PUSH_20130105_00000001.log的文件
	public final static int CSSP_FILETYPE_PUSHREPORT=2;//t_a_cssp_log中表示类似PUSHREPORT_20130105_00000001.log的文件

	//public final static int CSSP_FILETYPE_REPORT=3;
	public final static int MESSAGE_HANDLE_STATUS_RUNNING = -5;//t_a_messages表中消息入库中
	public final static int MESSAGE_HANDLE_STATUS_INIT = -1;//t_a_messages表中消息没有开始消费
	public final static int MESSAGE_HANDLE_STATUS_SUCC = 0;//t_a_messages表中消息消费成功
	public final static int MESSAGE_HANDLE_STATUS_FAIL=-2;//t_a_messages表中消息消费失败
	public final static String MESSAGE_CONTENT_TYPE_33="33%";//t_a_messages表中消息消费失败
	public final static String MESSAGE_CONTENT_TYPE_30="30%";//t_a_messages表中消息消费失败
//	private static String DC_TOPIC_TRAN = "DC.TOPIC.TRAN";
//	private static String DC_TOPIC_BATCH = "DC.TOPIC.BATCH";
//	private static String DC_TOPIC = "DC.TOPIC";
	
	}
