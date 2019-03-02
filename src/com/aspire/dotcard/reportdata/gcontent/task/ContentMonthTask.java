package com.aspire.dotcard.reportdata.gcontent.task;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.CommonTask;
import com.aspire.dotcard.reportdata.TopDataConstants;

public class ContentMonthTask extends TimerTask
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentMonthTask.class);
    
    protected static JLogger topDatalog = LoggerFactory.getLogger("reportimp.client");
    
	public void run()
	{
		logger.debug("Month ContentMonthTask start to run...");
        topDatalog.error("Month ContentMonthTask start to run...");
        
		CommonTask.exec(TopDataConstants.CONTENT_TYPE_MONTH);
		
		logger.info("Month ContentMonthTask finished...");
        topDatalog.error("Month ContentMonthTask finished.");
	}
}
