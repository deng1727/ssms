package com.aspire.dotcard.syncAndroid.ppms;

import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class OtPPMSPretreatment {
	/**
     * 日志对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(OtPPMSPretreatment.class);
  
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
            String ppms_Interval = module.getItemValue("ppms_Interval_pretreat") ;
            interval = Integer.parseInt(ppms_Interval);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        timer.schedule(new OtPPMSPretreatmentTask(), 1000, interval*1000*60) ;

    }
}
