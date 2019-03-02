
package com.aspire.dotcard.reportdata;

/**
 * <p>Title: IMALL PAS</p>
 * <p>Description: 定时启动类。</p>
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
 * 商品/内容排行榜数据导入Timer,导入周期包括每日、每周、每月
 * @author x_liyouli
 *
 */
public class TopDataImportTimer
{

    /**
     * 构造方法
     */
    private TopDataImportTimer()
    {
    }

    /**
     * 记录日志的实例对象
     */
    private static JLogger LOG = LoggerFactory.getLogger(TopDataImportTimer.class);
    
    protected static JLogger topDatalog = LoggerFactory.getLogger("reportimp.client");


    /**
     * 初始化方法启动timer
     */
    public static void start()
    {
        LOG.debug("Goods TopDataImportTimer.start()......");        
        topDatalog.error("TopDataImportTimer.start().....");
        		

        //内容日数据导入第一次开始导入时间 
        Date contentStartDay = getStartDay(TopDataConstants.CONTENT_TYPE_DAY);        
        
        //内容周数据导入第一次开始导入时间
        Date contentStartWeek = getStartDay(TopDataConstants.CONTENT_TYPE_WEEK);
        
        //内容月数据导入第一次开始导入时间
        Date contentStartMonth = getStartDay(TopDataConstants.CONTENT_TYPE_MONTH);
        
        //任务运行的间隔周期都是一天
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
     * 从配置文件取得日数据导入的配置项
     * 配置项的默认值：23：00
     * x_liyouli 2008-03-03 patch082 add
     * lyl 2008-6-12 patch120 modify
     * @return
     */
    private static Date getStartDay(int type)
    {
    	int hour;
    	int minute;
    	
    	//从配置项中获取日数据导入配置项, 具体时间HH:MM
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
            // 获取不到，或者配置值不正确，就使用默认值
            hour = 23;
            minute = 0;
        }
        
        Calendar c = Calendar.getInstance();
        //设置小时
        c.set(Calendar.HOUR_OF_DAY, hour);
        //设置分钟
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        if(c.before(Calendar.getInstance()))
        {
        	//如果启动时间点在现在时间的前面,就将任务运行时间推后一天
        	c.add(Calendar.DATE, 1);
        }
    	return c.getTime();
    }

}
