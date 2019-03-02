package com.aspire.dotcard.reportdata.gcontent.task;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.CommonTask;
import com.aspire.dotcard.reportdata.TopDataConstants;

public class ContentDayTask extends TimerTask
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentDayTask.class);
    
    protected static JLogger topDatalog = LoggerFactory.getLogger("reportimp.client");
    
	public void run()
	{
        logger.debug("用于统计七天下载量排序差值，任务开始...");
        
//        ConentDayComparedBO.getInstance().exe();
        
        logger.debug("用于统计七天下载量排序差值，任务结束...");
        
		logger.debug("Day ContentDayTask start to run...");
        topDatalog.error("Day ContentDayTask start to run...");
        
		CommonTask.exec(TopDataConstants.CONTENT_TYPE_DAY);
		
		logger.info("Day ContentDayTask finished...");
        topDatalog.error("Day ContentDayTask finished.");
	}
}
