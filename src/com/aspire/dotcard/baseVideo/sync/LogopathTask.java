package com.aspire.dotcard.baseVideo.sync;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;

public class LogopathTask extends TimerTask {
	

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(LogopathTask.class);

	/**
	 * 覆盖run运行方法
	 */
	public void run() {
		logger.debug("开始执行BaseVideoFileBO.getInstance().downAndUpPic();了");
		BaseVideoFileBO.getInstance().downAndUpPic();
		logger.debug("开始执行BaseVideoFileBO.getInstance().downAndUpPic();了结束");
	
	}



}
