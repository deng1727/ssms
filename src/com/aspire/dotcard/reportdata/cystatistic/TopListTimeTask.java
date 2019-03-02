
package com.aspire.dotcard.reportdata.cystatistic;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.cystatistic.bo.CyListBO;
import com.aspire.dotcard.reportdata.cystatistic.bo.TopListBO;

public class TopListTimeTask extends TimerTask
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(TopListTimeTask.class);

 
    /**
     * 覆盖run运行方法
     */
    public void run()
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("TopListBO.service start...");
        }

        // 调用应用top榜单、创意孵化榜单数据处理方法
        TopListBO.getInstance().service(0);

        // 调用每日创业大赛作品运营属性数据处理方法；
      //  CyListBO.getInstance().service();
    }

  
}
