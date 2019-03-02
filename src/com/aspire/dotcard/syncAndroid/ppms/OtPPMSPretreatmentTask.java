package com.aspire.dotcard.syncAndroid.ppms;

import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class OtPPMSPretreatmentTask extends TimerTask {
	JLogger LOG = LoggerFactory.getLogger(OtPPMSPretreatmentTask.class);
	/**
     * ִ������
     */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		LOG.debug("OtPPMSPretreatmentTask   message-----change��ʼ");
		try {
			PPMSPretreatmentBO.getInstance().handlePretreatment();
		} catch (BOException e) {
			LOG.error("Throwable:OtPPMSPretreatmentTask:this error may let timer object stop!!",e);
		}
		LOG.debug("OtPPMSPretreatmentTask   message-----change����");
	}
}


