package com.aspire.ponaadmin.web.datasync;

import java.util.Calendar;
import java.util.Collection;
import java.util.Timer;
import java.util.TreeMap;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class DataSyncTaskCenter 
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(DataSyncTaskCenter.class) ;

    /**
     * singleton模式的实例
     */
    private static DataSyncTaskCenter instance = new DataSyncTaskCenter() ;
    
    private TreeMap map = new TreeMap();
    /**
     * 定时器实例
     */
    Timer timer = new Timer(true) ;
    
    private static final long DayIntervalLength=(long)(24 * 60 * 60 * 1000);
    private static final long WeekIntervalLength=DayIntervalLength*7;

    /**
     * 构造方法，由singleton模式调用
     */
    private DataSyncTaskCenter ()
    {
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static DataSyncTaskCenter getInstance ()
    {
        return instance ;
    }
    
    public void registerTask(DataSyncTask task)throws BOException
    {
    	map.put(task.getName(), task);
    	Calendar firstExecTime=this.getTriggerTime(task);
    	String startDay=task.getStartDay();
    	if("week".equals(task.getFrequency()))
    	{
    		getFirstStartDayOfWeek(firstExecTime, startDay);
    		timer.schedule(task, firstExecTime.getTime(), WeekIntervalLength);
    	}else if("month".equals(task.getFrequency()))
    	{
    			return ;
    	}else
    	{
    		timer.scheduleAtFixedRate(task, firstExecTime.getTime(), DayIntervalLength);
    	}
    	if (logger.isDebugEnabled())
        {
            logger.debug("schedule a task:"+task.getName()+",first start time is:" +PublicUtil.getDateString(firstExecTime.getTime(), "yyyy-MM-dd HH:mm:ss") ) ;
        }
    	
    }
    
    public Collection getAllTask()
    {
    	return map.values();

    }
    public DataSyncTask getDataSynTask(String taskName)
    {
    	return (DataSyncTask)map.get(taskName);
    }
    
    public void runTask(String taskName)
    {
    	final DataSyncTask task = (DataSyncTask)map.get(taskName);
    	if(task!=null)
    	{
    		//启用另外一个线程执行
    		new Thread() {
    			public void run()
    			{
    				task.run();
    			}
    			
    		}.start();
    		
    	}else
    	{
    		logger.error("系统没有此任务，taskName:"+taskName);
    	}
    }
    
    private Calendar getTriggerTime(DataSyncTask task)throws BOException
    {
    	int hour,minute;
    	try
        {
            String SynStartTime = task.getStartTime();
            hour = Integer.parseInt(SynStartTime.split(":")[0]);
            minute = Integer.parseInt(SynStartTime.split(":")[1]);
            if(hour<0||hour>23||minute<0||minute>59)
            {
            	throw new IllegalArgumentException("时间格式错误！");
            }
            
        }
        catch (Exception ex)
        {
            logger.error("解析任务为"+task.getName()+"开始执行时间出错，格式错误："+task.getStartTime());
            throw new BOException("任务："+task.getName()+",初始化时间出错，startTime:"+task.getStartTime());
        }
        //第一次执行时间
        Calendar firstExecTime=Calendar.getInstance();
        Calendar curTime=Calendar.getInstance();

        if(curTime.get(Calendar.HOUR_OF_DAY)>hour)
        {
        	firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
        }else if(curTime.get(Calendar.HOUR_OF_DAY)==hour)
        {
        	if(curTime.get(Calendar.MINUTE)>=minute)
        	{
        		firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
        	}
        }
        //设置执行时间
        firstExecTime.set(Calendar.HOUR_OF_DAY, hour);
        firstExecTime.set(Calendar.MINUTE, minute);
        firstExecTime.set(Calendar.SECOND, 0);
        firstExecTime.set(Calendar.MILLISECOND, 0);
    	return firstExecTime;
    }
    /**
     * 获取以周为间隔的任务第一次开始执行的日期
     * @param firstExecTime 
     * @param startDay 一周的第几天执行，比如第一天表示周一。
     * @return
     */
    public Calendar getFirstStartDayOfWeek(Calendar firstExecTime,String start)throws BOException
    {
    	if(!start.matches("[1-7]"))
   		{
    		throw new BOException("同步日期格式有误，startDay只能是1-7的数字，startDay="+start);
    	}
    	int startDay=Integer.parseInt(start);
    	startDay=(startDay+1)%7;//系统默认周日作为一周的第一天，跟我们习惯不同
    	int dayOfWeek=firstExecTime.get(Calendar.DAY_OF_WEEK);
    	if(startDay>dayOfWeek)//允许本周开始执行
    	{
    		firstExecTime.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
    		firstExecTime.add(Calendar.DAY_OF_WEEK, startDay);
    	}else //下周开始执行
    	{
    		firstExecTime.add(Calendar.DAY_OF_WEEK, 7-dayOfWeek);
    		firstExecTime.add(Calendar.DAY_OF_WEEK, startDay);
    	}
    	
    	return firstExecTime;
    }
    /*private Date getFirstStartDayOfMonth(Calendar firstExecTime,int startDay)
    {
    	return null;
    }*/
    public static void main(String arg []) throws BOException
    {
    	DataSyncTaskCenter center=new DataSyncTaskCenter();
    	Calendar firstExecTime=Calendar.getInstance();
        center.getFirstStartDayOfWeek(firstExecTime, "");
        System.out.println("day:"+firstExecTime.get(Calendar.DAY_OF_MONTH)+" week:"+firstExecTime.get(Calendar.DAY_OF_WEEK));
    }

}
