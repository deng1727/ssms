package com.aspire.dotcard.syncAndroid.ppms;

import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class OtPPMSTimer {

	 /**
     * ��־����
     */
    protected static JLogger LOG = LoggerFactory.getLogger(OtPPMSTimer.class);
  
	  /**
     * ������ʱ����
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
        timer.schedule(new OtPPMSTimerTask(), 1000, interval*1000*60) ;

    }
}
