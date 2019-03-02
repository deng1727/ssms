package com.aspire.dotcard.syncAndroid.cssp.free;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * 免费应用 定时任务执行
 */
public class FreeTimer
{

    private static FreeTimer instance = new FreeTimer();

    /**
     * 构造方法
     */
    private FreeTimer()
    {

    }

    public static FreeTimer getInstance()
    {
        return instance;
    }

    /**
     * 记录日志的实例对象
     */
    private static JLogger LOG = LoggerFactory.getLogger(FreeTimer.class);

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

        String free_startDate = "2013-11-02-09";//SSMS是2013-11-01晚上发布现网，希望第二天9点开始同步。

        Date startDate = new Date();
        try
        {

            ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
            String free_Interval = module.getItemValue("free_Interval");
            try
            {
                free_startDate = module.getItemValue("free_startDate");
            }
            catch (Exception e)
            {
                LOG.error("cssp 获取free_startDate出错！", e);
            }
            String[] startArr = free_startDate.split("-");
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

            interval = Integer.parseInt(free_Interval);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        timer.schedule(new FreeTimerTask(), startDate, interval * 1000 * 60);

    }

}
