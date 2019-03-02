package com.aspire.ponaadmin.web.actionlog ;

import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.log.proxy.JLogger;
import java.util.Date;

/**
 * <p>删除日志类</p>
 * <p>操作离当前时间6个月前的日志</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class DelActionLog implements Runnable
{

    /** Automatically generated javadoc for: THE_SIXTY_SIXTY */
    private static final int THE_SIXTY_SIXTY = 3600;

    /** Automatically generated javadoc for: THE_KILO */
    private static final int THE_KILO = 1000;

    /** Automatically generated javadoc for: THE_MONTH */
    private static final int THE_MONTH = 12;

    /** Automatically generated javadoc for: THE_YEAR */
    private static final int THE_YEAR = 1900;

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(DelActionLog.class) ;

    /**
     * singleton模式的实例
     */
    private static DelActionLog instance = new DelActionLog() ;

    /**
     * 构造线程实例
     */
    private Thread selfThread = null ;

    /**
     * 构造方法，由singleton模式调用
     */
    private DelActionLog ()
    {
        if (selfThread == null)
        {
            //为线程分配实例
            selfThread = new Thread(this) ;
        }
        //启动线程
        selfThread.start() ;
    }

    /**
     * 运行线程
     */

    public void run ()
    {
        while (true)
        {
            try
            {
                Date date = new Date();
                int hour = date.getHours();

                if ((hour >= 2 ) && (hour < 4))
                {
                    //得到年份
                    int year = date.getYear()+THE_YEAR;
                    //得到月份
                    int month = date.getMonth()+1;
                    //得到天数
                    int day = date.getDate();
                    String time = null;
                    //计算6个月前的日期
                    month -= 6;
                    if (month <= 0)
                    {
                        year -= 1;
                        month += THE_MONTH;
                    }
                    //计算最终的时间
                    if(month < 10)
                    {
                        time = year+"0"+month+""+day+"000000";
                    }
                    else
                    {
                        time = year + "" + month + "" + day + "000000" ;
                    }
                    logger.debug("the time is:"+time);
                        //执行删除操作
                    ActionLogDAO.getInstance().delActionlog(time) ;
                    logger.debug("del success!");
                }

            }
            catch (Throwable e)
            {
                logger.error("DelActionLog error", e);
            }
            finally
            {
                try
                {
                   Thread.sleep(THE_KILO * THE_SIXTY_SIXTY) ;
                }
                catch(InterruptedException k)
                {
                    logger.error("InterruptedException:", k);
                }
            }
        }
    }

        /**
         * 获取实例
         * @return 实例
         */
    public static DelActionLog getInstance()
    {
        return instance;
    }

}
