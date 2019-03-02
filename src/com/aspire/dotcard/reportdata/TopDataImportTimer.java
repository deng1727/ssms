
package com.aspire.dotcard.reportdata;

/**
 * <p>Title: IMALL PAS</p>
 * <p>Description: ��ʱ�����ࡣ</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author x_liyunhong
 * @version 1.0
 */

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.gcontent.task.ContentDayTask;
import com.aspire.dotcard.reportdata.gcontent.task.ContentMonthTask;
import com.aspire.dotcard.reportdata.gcontent.task.ContentWeekTask;

/**
 * ��Ʒ/�������а����ݵ���Timer,�������ڰ���ÿ�ա�ÿ�ܡ�ÿ��
 * @author x_liyouli
 *
 */
public class TopDataImportTimer
{

    /**
     * ���췽��
     */
    private TopDataImportTimer()
    {
    }

    /**
     * ��¼��־��ʵ������
     */
    private static JLogger LOG = LoggerFactory.getLogger(TopDataImportTimer.class);
    
    protected static JLogger topDatalog = LoggerFactory.getLogger("reportimp.client");


    /**
     * ��ʼ����������timer
     */
    public static void start()
    {
        LOG.debug("Goods TopDataImportTimer.start()......");        
        topDatalog.error("TopDataImportTimer.start().....");
        		

        //���������ݵ����һ�ο�ʼ����ʱ�� 
        Date contentStartDay = getStartDay(TopDataConstants.CONTENT_TYPE_DAY);        
        
        //���������ݵ����һ�ο�ʼ����ʱ��
        Date contentStartWeek = getStartDay(TopDataConstants.CONTENT_TYPE_WEEK);
        
        //���������ݵ����һ�ο�ʼ����ʱ��
        Date contentStartMonth = getStartDay(TopDataConstants.CONTENT_TYPE_MONTH);
        
        //�������еļ�����ڶ���һ��
        long period = ( long ) (24 * 60 * 60 * 1000);
        
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new ContentDayTask(), contentStartDay, period);
        topDatalog.error("First Content ContentDayTask run time is:" + contentStartDay.toString());
        
        timer.scheduleAtFixedRate(new ContentWeekTask(), contentStartWeek, period);
        topDatalog.error("First Content ContentWeekTask run time is:" + contentStartWeek.toString());
        
        timer.scheduleAtFixedRate(new ContentMonthTask(), contentStartMonth, period);
        topDatalog.error("First Content ContentMonthTask run time is:" + contentStartMonth.toString());

    }
    
    /**
     * �������ļ�ȡ�������ݵ����������
     * �������Ĭ��ֵ��23��00
     * x_liyouli 2008-03-03 patch082 add
     * lyl 2008-6-12 patch120 modify
     * @return
     */
    private static Date getStartDay(int type)
    {
    	int hour;
    	int minute;
    	
    	//���������л�ȡ�����ݵ���������, ����ʱ��HH:MM
        try
        {
        	String timeStr;
        	switch(type)
        	{
        		case TopDataConstants.CONTENT_TYPE_DAY:
        			timeStr = TopDataConfig.get("ContentImportTime_Day");
        			break;
        		case TopDataConstants.CONTENT_TYPE_WEEK:
        			timeStr = TopDataConfig.get("ContentImportTime_Week");
        			break;
        		case TopDataConstants.CONTENT_TYPE_MONTH:
        			timeStr = TopDataConfig.get("ContentImportTime_Month");
        			break;
        		default:
        			timeStr = "23:00";
        	}
        	
        	String[] temp = timeStr.split(":");
        	hour = Integer.parseInt(temp[0]);
        	minute = Integer.parseInt(temp[1]);
            LOG.debug("the hourStr :"+timeStr);            
        }
        catch (Exception ex)
        {
            LOG.error(ex);
            // ��ȡ��������������ֵ����ȷ����ʹ��Ĭ��ֵ
            hour = 23;
            minute = 0;
        }
        
        Calendar c = Calendar.getInstance();
        //����Сʱ
        c.set(Calendar.HOUR_OF_DAY, hour);
        //���÷���
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        if(c.before(Calendar.getInstance()))
        {
        	//�������ʱ���������ʱ���ǰ��,�ͽ���������ʱ���ƺ�һ��
        	c.add(Calendar.DATE, 1);
        }
    	return c.getTime();
    }

}
