package com.aspire.dotcard.syncAndroid.dc;

import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


/**
 * messge发送去数据中心Timer入口
 * @author aiyan
 *
 */
public class MessageSendTimer
{
	
	 /**
     * 日志对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(MessageSendTimer.class);
    
    private MessageSendTimer()
    {
        
    }
    /**
     * 每5分钟执行以下查询数据库然后发送数据去数据中心
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
            String dc_Interval = module.getItemValue("dc_Interval") ;
            interval = Integer.parseInt(dc_Interval);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        timer.schedule(new MessageSendTack(), 1000, interval*1000*60) ;

    }
}
