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
     * ���췽��
     */
    private CSSPTimer()
    {

    }

    public static CSSPTimer getInstance()
    {
        return instance;
    }

    /**
     * ��¼��־��ʵ������
     */
    private static JLogger LOG = LoggerFactory.getLogger(CSSPTimer.class);

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
        // String cssp_startDate = "2013-04-25-09";//SSMS��2013-04-24���Ϸ���������ϣ���ڶ���9�㿪ʼͬ����

        String cssp_startDate = "2013-06-08-09";// SSMS��2013-06-07��ʼŪ��������

        Date startDate = new Date();
        try
        {
            ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
            String cssp_Interval = module.getItemValue("cssp_Interval");
            // <ConfigItem>
            // <Name>cssp_startDate</Name>
            // <Id>0</Id>
            // <Description>[1:�����޸���]CSSP�Ŀ�ʼ������,��ΪCSSP�ļ�̫�Ӵ󣬲�ϣ�����Ϸ�����ʱ��ͬ��������ļ������Ǵӵڶ��쿪ʼ��⡣</Description>
            // <Value>2013-04-25-09</Value>
            // <Reserved/>
            // </ConfigItem>
            try
            {
                cssp_startDate = module.getItemValue("cssp_startDate");
            }
            catch (Exception e)
            {
                LOG.error("cssp ��ȡcssp_startDate����", e);
            }
            String[] startArr = cssp_startDate.split("-");
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

            interval = Integer.parseInt(cssp_Interval);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        timer.schedule(new CSSPTimerTask(), startDate, interval * 1000 * 60);

    }

}
