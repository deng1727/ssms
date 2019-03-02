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
     * ���췽��
     */
    private SyncTacticTimer ()
    {

    }
public static SyncTacticTimer getInstance(){
	return instance;
}
    /**
     * ��¼��־��ʵ������
     */
    private static JLogger LOG = LoggerFactory.getLogger(SyncTacticTimer.class) ;

    /**
     * ��ʼ����������timer
     */
    public  void start ()
    {
        //ϵͳĬ��ͬ��ִ��ʱ�� 4:50
        int hour = 4;
        int minute = 50;
        //�ӻ���ҵ��ͨ������
        try
        {
        	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("basecommon");
            String startTime = module.getItemValue("startTime") ;
            hour = Integer.parseInt(startTime.split(":")[0]);
            minute = Integer.parseInt(startTime.split(":")[1]);
        }
        catch (Exception ex)
        {
            LOG.error("�ӻ���ҵ��ͨ�����õ�ʱ��startTime��������");
        }
        if(hour<0||hour>23)
        {
            //����ֵ����ȷ����ʹ��Ĭ��ֵ
            hour = 4 ;
        }
        if(minute<0||minute>59)
        {
            //����ֵ����ȷ����ʹ��Ĭ��ֵ
            minute = 50 ;
        }

        //���ʱ��Ϊһ��24Сʱ
        long taskIntervalMS = (long) (24 * 60 * 60 * 1000) ;
        Timer timer = new Timer(true) ;
        Date firstStartTime = null;
        try
        {
            //ȡ��ǰʱ��
            Date date = new Date();
            //�����һ����������ʱ��
            firstStartTime = getOneTimeByHourAndMinute(date,hour,minute);
            //�����һ������ʱ�����ڵ�ǰʱ�䣬��Ҫ�ѵ�һ������ʱ���һ��
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
            //����ʱ�����쳣�����õ�ǰ��ʱ��ɣ������������û��bug������쳣Ӧ���ǲ����ܳ��ֵ�
            firstStartTime = new Date();
        }    
        timer.schedule(new SyncTacticTask(), firstStartTime, taskIntervalMS) ;
        //timer.schedule(new SyncTacticTask(), 1000, taskIntervalMS) ;
        LOG.info("schedule a SyncTacticTask,first start time is:" + firstStartTime) ;
        
    }

    /**
     * ��ȡһ��ĳ��ָ��ʱ���time
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
