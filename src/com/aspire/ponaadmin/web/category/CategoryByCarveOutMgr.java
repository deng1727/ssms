
package com.aspire.ponaadmin.web.category;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryByCarveOutMgr
{
    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryByCarveOutMgr.class);

    /**
     * singleton模式的实例
     */
    private static CategoryByCarveOutMgr instance = new CategoryByCarveOutMgr();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryByCarveOutMgr()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryByCarveOutMgr getInstance()
    {
        return instance;
    }

    /**
     * 初始化方法，系统启动的时候需要调用本方法。
     */
    public void init()
    {
        try
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("创业大赛货架自动更新初始化化任务开始！");
            }
            try
            {
                CategoryRuleByCarveOutConfig.loadConfig();
            }
            catch (Exception e)
            {
                LOG.error("读取创业大赛货架自动更新的配置项出错，货架自动更新任务终止。", e);
                System.err.println("读取创业大赛货架自动更新的配置项出错，货架自动更新任务终止。");
                return;
            }
            Calendar firstExecTime = getTriggerTime();
            long period = 24 * 60 * 60 * 1000;
            // 调用Timer类的schedule方法启动定时任务。
            Timer timer = new Timer(true);
            timer.schedule(new CategoryRuleByCarveOutTask(),
                           firstExecTime.getTime(),
                           period);
        }
        catch (BOException e)
        {
            LOG.error("创业大赛货架自动更新任务初始化失败，", e);

        }
    }

    private Calendar getTriggerTime() throws BOException
    {
        int hour, minute;
        String SynStartTime = CategoryRuleByCarveOutConfig.STARTTIME;;
        try
        {
            hour = Integer.parseInt(SynStartTime.split(":")[0]);
            minute = Integer.parseInt(SynStartTime.split(":")[1]);
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59)
            {
                throw new IllegalArgumentException("时间格式错误！");
            }
        }
        catch (Exception ex)
        {
            LOG.error("创业大赛货架自动更新开始执行时间出错，格式错误：" + SynStartTime);
            throw new BOException("创业大赛货架自动更新开始执行时间出错，格式错误，startTime:"
                                  + SynStartTime);
        }
        // 第一次执行时间
        Calendar firstExecTime = Calendar.getInstance();
        Calendar curTime = Calendar.getInstance();

        if (curTime.get(Calendar.HOUR_OF_DAY) > hour)
        {
            firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        else if (curTime.get(Calendar.HOUR_OF_DAY) == hour)
        {
            if (curTime.get(Calendar.MINUTE) >= minute)
            {
                firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        // 设置执行时间
        firstExecTime.set(Calendar.HOUR_OF_DAY, hour);
        firstExecTime.set(Calendar.MINUTE, minute);
        firstExecTime.set(Calendar.SECOND, 0);
        firstExecTime.set(Calendar.MILLISECOND, 0);
        return firstExecTime;
    }
}
