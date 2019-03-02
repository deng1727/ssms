package com.aspire.dotcard.syncAndroid.cssp;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CSSPTimer
{

    private static CSSPTimer instance = new CSSPTimer();

    /**
     * 构造方法
     */
    private CSSPTimer()
    {

    }

    public static CSSPTimer getInstance()
    {
        return instance;
    }

    /**
     * 记录日志的实例对象
     */
    private static JLogger LOG = LoggerFactory.getLogger(CSSPTimer.class);

    /**
     * 初始化方法启动timer
     */
    public static void start()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("start()");
        }

        Timer timer = new Timer(true);
        int interval = 5;
        // String cssp_startDate = "2013-04-25-09";//SSMS是2013-04-24晚上发布现网，希望第二天9点开始同步。

        String cssp_startDate = "2013-06-08-09";// SSMS是2013-06-07开始弄。。。。

        Date startDate = new Date();
        try
        {
            ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
            String cssp_Interval = module.getItemValue("cssp_Interval");
            // <ConfigItem>
            // <Name>cssp_startDate</Name>
            // <Id>0</Id>
            // <Description>[1:必须修改项]CSSP的开始年月日,因为CSSP文件太庞大，不希望晚上发布的时候同步当天的文件，而是从第二天开始入库。</Description>
            // <Value>2013-04-25-09</Value>
            // <Reserved/>
            // </ConfigItem>
            try
            {
                cssp_startDate = module.getItemValue("cssp_startDate");
            }
            catch (Exception e)
            {
                LOG.error("cssp 获取cssp_startDate出错！", e);
            }
            String[] startArr = cssp_startDate.split("-");
            int year = Integer.parseInt(startArr[0]);
            int month = Integer.parseInt(startArr[1]) - 1;
            int date = Integer.parseInt(startArr[2]);
            int hour = Integer.parseInt(startArr[3]);
            Calendar c = Calendar.getInstance();
            c.set(year, month, date);
            c.set(Calendar.HOUR_OF_DAY, hour);// 那天9点开始,其实应该设为1点更好些。但是刚
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            startDate = c.getTime();

            interval = Integer.parseInt(cssp_Interval);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        timer.schedule(new CSSPTimerTask(), startDate, interval * 1000 * 60);

    }

}
