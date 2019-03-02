package com.aspire.dotcard.baseVideo.sync;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;

public class LogopathTask extends TimerTask {
	

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(LogopathTask.class);

	/**
	 * ����run���з���
	 */
	public void run() {
		logger.debug("��ʼִ��BaseVideoFileBO.getInstance().downAndUpPic();��");
		BaseVideoFileBO.getInstance().downAndUpPic();
		logger.debug("��ʼִ��BaseVideoFileBO.getInstance().downAndUpPic();�˽���");
	
	}



}
