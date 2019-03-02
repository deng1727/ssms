package com.aspire.dotcard.syncAndroid.ssms;

import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class AndroidListTimer {
	 /**
     * 日志对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(AndroidListTimer.class);
  
	  /**
     * 启动定时任务,这个为了完成android榜单数据变化的话就及时通知数据中心。
     *
     */
    public static void start ()
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
            String ssms_Interval = module.getItemValue("ssms_Interval") ;
            interval = Integer.parseInt(ssms_Interval);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        timer.schedule(new AndroidListTask(), 1000, interval*1000*60) ;

    }

}
