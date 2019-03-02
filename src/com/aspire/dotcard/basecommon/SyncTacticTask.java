package com.aspire.dotcard.basecommon;


import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecommon.bo.SyncTacticBO;

public class SyncTacticTask extends TimerTask {

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(SyncTacticTask.class);

	/**
	 * ����run���з���
	 */
	public void run() {
		logger.info("SyncTacticTask���ػ����Զ����¼����ÿ�ʼ��...");
		SyncTacticBO.getInstance().sync();
	}



}
