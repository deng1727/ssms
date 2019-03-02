package com.aspire.dotcard.syncAndroid.cssp.free;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.cssp.CSSPDAO;

/**
 * 免费应用 定时任务执行
 */
public class FreeTimerTask extends TimerTask
{

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 日志引用
     */
    protected static JLogger LOG = LoggerFactory.getLogger(FreeTimerTask.class);

    /**
     * 覆盖run运行方法
     */
    public void run()
    {

        try
        {
            LOG.info("Free download  的处理开始了。。。");
            long a = System.currentTimeMillis();

            if (LOG.isDebugEnabled())
            {
                System.out.println("\r\n==============================================================");
                System.out.println("货架免费应用实时下载量数据同步开始....\r");
                LOG.debug("\r\n==============================================================");
                LOG.debug("货架免费应用实时下载量数据同步开始....\r");

            }
            long begin = System.currentTimeMillis();

            // 免费应用下载回执日志
            freeDLReport();

            // 免费应用订购记录
            freeDLOrder();

            // 免费应用下载回执日志
            CSSPDAO.getInstance().insertDownloadReport();

            long end = System.currentTimeMillis();
            if (LOG.isDebugEnabled())
            {
                System.out.println("货架免费应用实时下载量数据同步结束====>总耗时 " + (end - begin) + " 毫秒");
                System.out.println("==============================================================\r\n");
                LOG.debug("货架免费应用实时下载量数据同步结束====>总耗时 " + (end - begin) + " 毫秒");
                LOG.debug("==============================================================\r\n");
            }

            CSSPDAO.getInstance().realtimeDowncount();// t_a_messages要发消息的地方。

            LOG.info("FreeTimerTask time--> " + (System.currentTimeMillis() - a));
            LOG.info("Free 的处理结束，2分钟后回来的。。。");
        }
        catch (Exception e)
        {
            LOG.error("FreeTimerTask 出现错误！", e);
        }
    }

    /**
     * T_free_dl_report 免费应用下载回执日志 notify.log.2013-10-28.11.bak
     */
    private void freeDLReport()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("notify.log*.bak免费应用下载回执日志文件解析入库开始");
            System.out.println("notify.log*.bak免费应用下载回执日志文件解析入库开始....需要一段时间请耐心等待.....");
        }
        long begin = System.currentTimeMillis();

        Date date = new Date();
        String day = df2.format(date);
        String filePrefix = "notify.log." + day;
        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取当前时间文件：" + filePrefix);
        }
        // filePrefix = "notify.log.2013-10-17";
        ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
        String ftpDir = module.getItemValue("FTPfreeDLReportDir");
        String localDir = module.getItemValue("localfreeDLReportDir");
        String typeDirs = module.getItemValue("dLReportTypeDirs");
        String dLReportChangeDirs = module.getItemValue("dLReportChangeDirs");
        String[] changeDirs = StringUtils.isNotEmpty(dLReportChangeDirs) ? dLReportChangeDirs.split("[|]") : null;

        if (StringUtils.isNotEmpty(typeDirs))
        {
            ftpDir = ftpDir + "/" + typeDirs;
        }

        if (StringUtils.isNotEmpty(typeDirs))
        {
            localDir = localDir + "/" + typeDirs;
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("freeDLReport()->ftpDir:" + ftpDir);
            LOG.debug("freeDLReport()->localDir:" + localDir);
        }

        if (changeDirs != null)
        {
            for (int i = 0; i < changeDirs.length; i++)
            {
                String ftpPath = ftpDir;
                if (StringUtils.isNotEmpty(changeDirs[i]))
                {
                    ftpPath = ftpPath + "/" + changeDirs[i];
                }
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("freeDLReport()->ftpPath:" + ftpPath);
                }
                String localPath = localDir;
                if (StringUtils.isNotEmpty(changeDirs[i]))
                {
                    localPath = localPath + "/" + changeDirs[i];
                }
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("freeDLReport()->localPath:" + localPath);
                }

                new FreeReportProducerRunnable(ftpPath, localPath, filePrefix).run();
            }

            for (int i = 0; i < changeDirs.length; i++)
            {
                String localPath = localDir;

                if (StringUtils.isNotEmpty(changeDirs[i]))
                {
                    localPath = localPath + "/" + changeDirs[i];
                }
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("freeDLReport()->localPath:" + localPath);
                }
                new FreeReportConsumerRunnable(filePrefix, new FreeDLReportBuilder(), localPath).run();
            }
        }
        else
        {
            new FreeReportProducerRunnable(ftpDir, localDir, filePrefix).run();
            new FreeReportConsumerRunnable(filePrefix, new FreeDLReportBuilder(), localDir).run();
        }

        long end = System.currentTimeMillis();
        if (LOG.isDebugEnabled())
        {
            LOG.debug("notify.log*.bak免费应用下载回执日志文件解析入库结束!");
            LOG.debug("notify.log*.bak免费应用下载回执日志文件解析入库====>总耗时：" + (end - begin) + " 毫秒");
            System.out.println("notify.log*.bak免费应用下载回执日志文件解析入库结束====>总耗时：" + (end - begin) + " 毫秒\r");
        }
    }

    /**
     * T_free_dl_order 免费应用订购记录 report_20131024_00000001.log
     */
    private void freeDLOrder()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("report_*.log免费应用订购记录解析入库开始");
            System.out.println("report_*.log免费应用订购记录解析入库开始....需要一段时间请耐心等待.....");
        }
        long begin = System.currentTimeMillis();

        Date date = new Date();
        String day = df.format(date);
        String filePrefix = "report_" + day;
        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取当前时间文件：" + filePrefix);
        }
        // filePrefix = "report_20131024";
        ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
        String ftpDir = module.getItemValue("FTPfreeDLOrderDir");
        String localDir = module.getItemValue("localfreeDLOrderDir");
        String typeDirs = module.getItemValue("freeDLOrderTypeDirs");
        String freeDLOrderChangeDirs = module.getItemValue("freeDLOrderChangeDirs");
        String[] changeDirs = StringUtils.isNotEmpty(freeDLOrderChangeDirs) ? freeDLOrderChangeDirs.split("[|]") : null;

        if (StringUtils.isNotEmpty(typeDirs))
        {
            ftpDir = ftpDir + "/" + typeDirs;
        }

        if (StringUtils.isNotEmpty(typeDirs))
        {
            localDir = localDir + "/" + typeDirs;
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("freeDLOrder()->ftpDir:" + ftpDir);
            LOG.debug("freeDLOrder()->localDir:" + localDir);
        }

        if (changeDirs != null)
        {
            for (int i = 0; i < changeDirs.length; i++)
            {
                String ftpPath = ftpDir;
                if (StringUtils.isNotEmpty(changeDirs[i]))
                {
                    ftpPath = ftpPath + "/" + changeDirs[i];
                }
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("freeDLOrder()->ftpPath:" + ftpPath);
                }
                String localPath = localDir;
                if (StringUtils.isNotEmpty(changeDirs[i]))
                {
                    localPath = localPath + "/" + changeDirs[i];
                }
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("freeDLOrder()->localPath:" + localPath);
                }

                new FreeOrderProducerRunnable(ftpPath, localPath, filePrefix).run();
            }

            for (int i = 0; i < changeDirs.length; i++)
            {
                String localPath = localDir;

                if (StringUtils.isNotEmpty(changeDirs[i]))
                {
                    localPath = localPath + "/" + changeDirs[i];
                }
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("freeDLOrder()->localPath:" + localPath);
                }
                new FreeOrderConsumerRunnable(filePrefix, new FreeDLOrderBuilder(), localPath).run();
            }
        }
        else
        {
            new FreeOrderProducerRunnable(ftpDir, localDir, filePrefix).run();
            new FreeOrderConsumerRunnable(filePrefix, new FreeDLOrderBuilder(), localDir).run();
        }

        long end = System.currentTimeMillis();
        if (LOG.isDebugEnabled())
        {
            LOG.debug("report_*.log免费应用订购记录解析入库结束");
            LOG.debug("report_*.log免费应用订购记录解析入库====>总耗时：" + (end - begin) + " 毫秒");
            System.out.println("report_*.log免费应用订购记录解析入库结束====>总耗时：" + (end - begin) + " 毫秒\r");
        }
    }

}
