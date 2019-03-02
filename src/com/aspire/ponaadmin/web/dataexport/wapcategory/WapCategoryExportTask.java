package com.aspire.ponaadmin.web.dataexport.wapcategory;

import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.appexp.ExportResult;

public class WapCategoryExportTask extends TimerTask {

	private static final JLogger logger = LoggerFactory
			.getLogger(WapCategoryExportTask.class);

	public static IConfig getConfig() {
		return FileConfigImpl.getInstance(WapCategoryExportBo.NAME);
	}

	public void run() {
		logger.debug("WAP�������ݵ�����������....");
		try {
			ExportData r = WapCategoryExportBo.export();
			logger.debug("WAP�������ݵ����������!");
		} catch (BOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
