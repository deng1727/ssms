package com.aspire.dotcard.updatetime;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class TimeSyncTask extends TimerTask {
	
	/**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(TimeSyncTask.class);

	@Override
	public void run() {
		
		if (logger.isDebugEnabled())
        {
            logger.debug("syncTime start...");
        }

        // ����DataSyncBO�е�syncTime������
        TimeBO.getInstance().syncTime();

        if (logger.isDebugEnabled())
        {
            logger.debug("syncTime end...");
          
        }
	}

}
