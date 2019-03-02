package com.aspire.ponaadmin.web.dataexport.appexp;

import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.wapcategory.FileConfigImpl;
import com.aspire.ponaadmin.web.dataexport.wapcategory.IConfig;

public class AppsExpTask extends TimerTask {
	private AppsExpBo bo = AppsExpBo.getInstance();

	protected static JLogger log = LoggerFactory.getLogger(AppsExpTask.class);

	public static IConfig getConfig() {
		return FileConfigImpl.getInstance(AppsExpBo.NAME);
	}

	public void run() {
		if (log.isDebugEnabled()) {
			log.debug("开始增量导出应用信息");
		}
		bo.calculateTime("Day", new Date());
		if (log.isDebugEnabled()) {
			log.debug("增量导出应用信息结束");
		}
	}

}
