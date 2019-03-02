package com.aspire.ponaadmin.web.music139;

import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.appexp.AppsExpTask;

public class Music139Task extends TimerTask {
	public Music139Task(AbstractMusic139SynBo d) {
		this.bo = d;

	}

	protected static JLogger log = LoggerFactory.getLogger(AppsExpTask.class);

	AbstractMusic139SynBo bo;

	public void run() {
		if (log.isDebugEnabled()) {
			log.debug("¿ªÊ¼" + bo.getOperationName());
		}
		bo.synchroMusic139Data();
		if (log.isDebugEnabled()) {
			log.debug(bo.getOperationName() + "½áÊø");
		}
	}

	public Music139Config getConfig() {
		return bo.config;
	}
	
	public AbstractMusic139SynBo getBo(){
		return bo;
	}

}
