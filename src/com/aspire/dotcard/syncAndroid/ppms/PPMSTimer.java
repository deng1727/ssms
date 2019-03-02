package com.aspire.dotcard.syncAndroid.ppms;

import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class PPMSTimer {

	 /**
     * 日志对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(PPMSTimer.class);
  
	  /**
     * 启动定时任务
     *
     */
    public static  void start ()
    {
        
        if(LOG.isDebugEnabled())
        {
        	LOG.debug("start()");
        }
       
    	Timer timer = new Timer(true);
    	int interval = 5;
        try
        {
        	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
			"syncAndroid");
            String ppms_Interval = module.getItemValue("ppms_Interval") ;
            interval = Integer.parseInt(ppms_Interval);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        timer.schedule(new PPMSTimerTask(), 1000, interval*1000*60) ;

    }
}
