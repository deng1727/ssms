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
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(DataSyncTaskCenter.class) ;

    /**
     * singletonģʽ��ʵ��
     */
    private static DataSyncTaskCenter instance = new DataSyncTaskCenter() ;
    
    private TreeMap map = new TreeMap();
    /**
     * ��ʱ��ʵ��
     */
    Timer timer = new Timer(true) ;
    
    private static final long DayIntervalLength=(long)(24 * 60 * 60 * 1000);
    private static final long WeekIntervalLength=DayIntervalLength*7;

    /**
     * ���췽������singletonģʽ����
     */
    private DataSyncTaskCenter ()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
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
    		//��������һ���߳�ִ��
    		new Thread() {
    			public void run()
    			{
    				task.run();
    			}
    			
    		}.start();
    		
    	}else
    	{
    		logger.error("ϵͳû�д�����taskName:"+taskName);
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
            	throw new IllegalArgumentException("ʱ���ʽ����");
            }
            
        }
        catch (Exception ex)
        {
            logger.error("��������Ϊ"+task.getName()+"��ʼִ��ʱ�������ʽ����"+task.getStartTime());
            throw new BOException("����"+task.getName()+",��ʼ��ʱ�����startTime:"+task.getStartTime());
        }
        //��һ��ִ��ʱ��
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
        //����ִ��ʱ��
        firstExecTime.set(Calendar.HOUR_OF_DAY, hour);
        firstExecTime.set(Calendar.MINUTE, minute);
        firstExecTime.set(Calendar.SECOND, 0);
        firstExecTime.set(Calendar.MILLISECOND, 0);
    	return firstExecTime;
    }
    /**
     * ��ȡ����Ϊ����������һ�ο�ʼִ�е�����
     * @param firstExecTime 
     * @param startDay һ�ܵĵڼ���ִ�У������һ���ʾ��һ��
     * @return
     */
    public Calendar getFirstStartDayOfWeek(Calendar firstExecTime,String start)throws BOException
    {
    	if(!start.matches("[1-7]"))
   		{
    		throw new BOException("ͬ�����ڸ�ʽ����startDayֻ����1-7�����֣�startDay="+start);
    	}
    	int startDay=Integer.parseInt(start);
    	startDay=(startDay+1)%7;//ϵͳĬ��������Ϊһ�ܵĵ�һ�죬������ϰ�߲�ͬ
    	int dayOfWeek=firstExecTime.get(Calendar.DAY_OF_WEEK);
    	if(startDay>dayOfWeek)//�����ܿ�ʼִ��
    	{
    		firstExecTime.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
    		firstExecTime.add(Calendar.DAY_OF_WEEK, startDay);
    	}else //���ܿ�ʼִ��
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
