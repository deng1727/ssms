package com.aspire.dotcard.basecommon;



import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.DateUtil;

public class SyncTacticTimer
{
	
	private static SyncTacticTimer instance = new SyncTacticTimer();
    /**
     * 构造方法
     */
    private SyncTacticTimer ()
    {

    }
public static SyncTacticTimer getInstance(){
	return instance;
}
    /**
     * 记录日志的实例对象
     */
    private static JLogger LOG = LoggerFactory.getLogger(SyncTacticTimer.class) ;

    /**
     * 初始化方法启动timer
     */
    public  void start ()
    {
        //系统默认同步执行时间 4:50
        int hour = 4;
        int minute = 50;
        //从基地业务通用配置
        try
        {
        	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("basecommon");
            String startTime = module.getItemValue("startTime") ;
            hour = Integer.parseInt(startTime.split(":")[0]);
            minute = Integer.parseInt(startTime.split(":")[1]);
        }
        catch (Exception ex)
        {
            LOG.error("从基地业务通用配置的时间startTime解析出错！");
        }
        if(hour<0||hour>23)
        {
            //配置值不正确，就使用默认值
            hour = 4 ;
        }
        if(minute<0||minute>59)
        {
            //配置值不正确，就使用默认值
            minute = 50 ;
        }

        //间隔时间为一天24小时
        long taskIntervalMS = (long) (24 * 60 * 60 * 1000) ;
        Timer timer = new Timer(true) ;
        Date firstStartTime = null;
        try
        {
            //取当前时间
            Date date = new Date();
            //构造第一次任务运行时间
            firstStartTime = getOneTimeByHourAndMinute(date,hour,minute);
            //如果第一次运行时间早于当前时间，需要把第一次运行时间加一天
            if(firstStartTime.before(date))
            {
                Calendar calendar = Calendar.getInstance();          
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date tommorrow = calendar.getTime();                
                firstStartTime = getOneTimeByHourAndMinute(tommorrow,hour,minute);
            }
        }
        catch (Exception ex)
        {
            //设置时间有异常，就用当前的时间吧，不过如果程序没有bug，这个异常应该是不可能出现的
            firstStartTime = new Date();
        }    
        timer.schedule(new SyncTacticTask(), firstStartTime, taskIntervalMS) ;
        //timer.schedule(new SyncTacticTask(), 1000, taskIntervalMS) ;
        LOG.info("schedule a SyncTacticTask,first start time is:" + firstStartTime) ;
        
    }

    /**
     * 获取一天某个指定时间的time
     * @param date Date
     * @param hour int
     * @return Date
     * @throws Exception
     */
    private static Date getOneTimeByHourAndMinute(Date date,int hour,int minute)
    {
        String c = DateUtil.formatDate(date,"yyyyMMdd");
        if(hour<10)
        {
            c = c + '0';
        }
        c = c + hour + minute + "00" ;
        return DateUtil.stringToDate(c,"yyyyMMddHHmmss");
    }

}
