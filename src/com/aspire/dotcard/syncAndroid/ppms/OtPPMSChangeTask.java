package com.aspire.dotcard.syncAndroid.ppms;

import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class OtPPMSChangeTask extends TimerTask {
	JLogger LOG = LoggerFactory.getLogger(OtPPMSChangeTask.class);
	/**
     * Ö´ÐÐÈÎÎñ
     */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			PPMSPretreatmentBO.getInstance().handleChange();
		} catch (BOException e) {
			LOG.error("Throwable:OtPPMSChangeTask:this error may let timer object stop!!",e);
		}
	}

}
