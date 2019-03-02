package com.aspire.dotcard.updatetime;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class TimeSyncTask extends TimerTask {
	
	/**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(TimeSyncTask.class);

	@Override
	public void run() {
		
		if (logger.isDebugEnabled())
        {
            logger.debug("syncTime start...");
        }

        // 调用DataSyncBO中的syncTime方法；
        TimeBO.getInstance().syncTime();

        if (logger.isDebugEnabled())
        {
            logger.debug("syncTime end...");
          
        }
	}

}
