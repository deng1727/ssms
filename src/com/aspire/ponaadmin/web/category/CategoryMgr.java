
package com.aspire.ponaadmin.web.category;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryMgr
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryMgr.class);

    /**
     * singleton模式的实例
     */
    private static CategoryMgr instance = new CategoryMgr();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryMgr()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryMgr getInstance()
    {
        return instance;
    }
    
    public void loadConfig()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("货架自动更新初始化配置文件开始！");
        }

        try
        {
            CategoryRuleConfig.loadConfig();
        }
        catch (Exception e)
        {
            LOG.error("读取货架自动更新的配置项出错，货架自动更新任务终止。", e);
            System.err.println("读取货架自动更新的配置项出错，货架自动更新任务终止。");
            return;
        }
    }

    public void start()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("货架自动更细初始化化任务开始！");
        }

        new CategoryRuleTask().run();
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
                LOG.debug("货架自动更细初始化化任务开始！");
            }
            /*
             * //初始化配置项。 ModuleConfig module = ConfigFactory.getSystemConfig()
             * .getModuleConfig("categoryAutoUpdate"); if (module == null) {
             * throw new
             * BOException("system-config.xml文件中找不到categoryAutoUpdate模块"); }
             * 
             * try { STARTTIME = module.getItemValue("startTime");
             * CONDITIONMAXVALUE=Integer.parseInt(module.getItemValue("ConditionMaxValue"));
             * SPNAMESORTCOUNT=Integer.parseInt(module.getItemValue("spnameSortCount"));
             * SPNAMEMAXCOUNT=Integer.parseInt(module.getItemValue("spnameMaxCount")); }
             * catch (Exception e) { LOG.error("读取货架自动更新的配置项出错，货架自动任务终止。",e);
             * return ; }
             */
            try
            {
                CategoryRuleConfig.loadConfig();
            }
            catch (Exception e)
            {
                LOG.error("读取货架自动更新的配置项出错，货架自动更新任务终止。", e);
                System.err.println("读取货架自动更新的配置项出错，货架自动更新任务终止。");
                return;
            }
            Calendar firstExecTime = getTriggerTime();
            long period = 24 * 60 * 60 * 1000;
            // 调用Timer类的schedule方法启动定时任务。
            Timer timer = new Timer(true);
            timer.schedule(new CategoryRuleTask(),
                           firstExecTime.getTime(),
                           period);
        }
        catch (BOException e)
        {
            LOG.error("货架更新自动任务初始化失败，", e);

        }

    }

    /**
     * 直接执行所有货架的规则处理逻辑 干的事情和CategoryTimerTask里面应该是一样的，只是这里可以让客户代码直接调用
     */
    public void runAllCategoryRules()
    {
        // 本方法暂时先不实现
    }

    private Calendar getTriggerTime() throws BOException
    {

        int hour, minute;
        String SynStartTime = CategoryRuleConfig.STARTTIME;;
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
            LOG.error("货架自动更新开始执行时间出错，格式错误：" + SynStartTime);
            throw new BOException("货架自动更新开始执行时间出错，格式错误，startTime:"
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
