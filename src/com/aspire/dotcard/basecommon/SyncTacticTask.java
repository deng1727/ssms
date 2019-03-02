package com.aspire.dotcard.basecommon;


import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecommon.bo.SyncTacticBO;

public class SyncTacticTask extends TimerTask {

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(SyncTacticTask.class);

	/**
	 * 覆盖run运行方法
	 */
	public void run() {
		logger.info("SyncTacticTask基地货架自动上下架配置开始了...");
		SyncTacticBO.getInstance().sync();
	}



}
