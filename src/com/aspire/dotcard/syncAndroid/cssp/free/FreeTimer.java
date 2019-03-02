package com.aspire.dotcard.syncAndroid.cssp.free;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * ���Ӧ�� ��ʱ����ִ��
 */
public class FreeTimer
{

    private static FreeTimer instance = new FreeTimer();

    /**
     * ���췽��
     */
    private FreeTimer()
    {

    }

    public static FreeTimer getInstance()
    {
        return instance;
    }

    /**
     * ��¼��־��ʵ������
     */
    private static JLogger LOG = LoggerFactory.getLogger(FreeTimer.class);

    /**
     * ��ʼ����������timer
     */
    public static void start()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("start()");
        }

        Timer timer = new Timer(true);
        int interval = 5;

        String free_startDate = "2013-11-02-09";//SSMS��2013-11-01���Ϸ���������ϣ���ڶ���9�㿪ʼͬ����

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
                LOG.error("cssp ��ȡfree_startDate����", e);
            }
            String[] startArr = free_startDate.split("-");
            int year = Integer.parseInt(startArr[0]);
            int month = Integer.parseInt(startArr[1]) - 1;
            int date = Integer.parseInt(startArr[2]);
            int hour = Integer.parseInt(startArr[3]);
            Calendar c = Calendar.getInstance();
            c.set(year, month, date);
            c.set(Calendar.HOUR_OF_DAY, hour);// ����9�㿪ʼ,��ʵӦ����Ϊ1�����Щ�����Ǹ�
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
