package com.aspire.dotcard.syncAndroid.dc;

import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


/**
 * messge����ȥ��������Timer���
 * @author aiyan
 *
 */
public class MessageSendTimer
{
	
	 /**
     * ��־����
     */
    protected static JLogger LOG = LoggerFactory.getLogger(MessageSendTimer.class);
    
    private MessageSendTimer()
    {
        
    }
    /**
     * ÿ5����ִ�����²�ѯ���ݿ�Ȼ��������ȥ��������
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
