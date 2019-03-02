package com.aspire.dotcard.reportdata.gcontent.task;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.CommonTask;
import com.aspire.dotcard.reportdata.TopDataConstants;

public class ContentWeekTask extends TimerTask
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentWeekTask.class);
    
    protected static JLogger topDatalog = LoggerFactory.getLogger("reportimp.client");
    
	public void run()
	{
		logger.debug("Week ContentWeekTask start to run...");
        topDatalog.error("Week ContentWeekTask start to run...");
        
		CommonTask.exec(TopDataConstants.CONTENT_TYPE_WEEK);
		
		logger.info("Week ContentWeekTask finished...");
        topDatalog.error("Week ContentWeekTask finished.");
	}

}
