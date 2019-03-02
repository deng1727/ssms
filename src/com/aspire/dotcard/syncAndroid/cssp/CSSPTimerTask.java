package com.aspire.dotcard.syncAndroid.cssp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.cssp.free.FreeDLOrderBuilder;
import com.aspire.dotcard.syncAndroid.cssp.free.FreeDLReportBuilder;
import com.aspire.dotcard.syncAndroid.cssp.free.FreeOrderConsumerRunnable;
import com.aspire.dotcard.syncAndroid.cssp.free.FreeOrderProducerRunnable;
import com.aspire.dotcard.syncAndroid.cssp.free.FreeReportConsumerRunnable;
import com.aspire.dotcard.syncAndroid.cssp.free.FreeReportProducerRunnable;

public class CSSPTimerTask extends TimerTask
{

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 日志引用
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CSSPTimerTask.class);

    /**
     * 覆盖run运行方法
     */
    public void run()
    {

        try
        {
            LOG.info("CSSP 的处理开始了。。。");
            long a = System.currentTimeMillis();
            // 这里要开始把日志机器上的指定文件下载下来。

            // push();

            // report_20130607_00000151.log
            csspReport();


            LOG.info("货架免费应用实时下载量数据同步开始....");

            long begin = System.currentTimeMillis();

            LOG.info("开始免费应用下载回执日志...");
            // 免费应用下载回执日志
            freeDLReport();
            
            LOG.info("开始免费应用订购记录...");
            // 免费应用订购记录
            freeDLOrder();

            LOG.info("t_a_report开始导入数据...");
            // 免费应用下载回执日志
            CSSPDAO.getInstance().insertDownloadReport();

            long end = System.currentTimeMillis();
            
            LOG.info("货架免费应用实时下载量数据同步结束====>总耗时 " + (end - begin) + " 毫秒");
           
            // try {
            // new CSSPImport(new PushBuilder()).importFile();
            // } catch (Exception e) {
            // // TODO Auto-generated catch block
            // LOG.error(e);
            // }
            // try {
            // new CSSPImport(new PushReportBuilder()).importFile();
            // } catch (Exception e) {
            // // TODO Auto-generated catch block
            // LOG.error(e);
            // }
            CSSPDAO.getInstance().realtimeDowncount();// t_a_messages要发消息的地方。

            // //需要清理表t_a_push（保留3天内）和t_a_pushreport（保留没有处理的，就是HANDLE_STATUS=-1） add by aiyan 2013-04-25
            // CSSPDAO.getInstance().clearData();

            LOG.info("CSSPTimerTask time--> " + (System.currentTimeMillis() - a));
            LOG.info("CSSP 的处理结束，30分钟后回来的。。。");
        }
        catch (Exception e)
        {
            LOG.error("CSSPTimerTask 出现错误！", e);
            e.printStackTrace();
        }
    }

    private void push()
    {

        // PUSH_20130105_00000001.log 获取类似这样的文件。
        // Date date = new Date();
        // String day = df.format(date);
        // String filePrefix = "PUSH_" + day;
        //		
        // ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
        // String ftpDir = module.getItemValue("FTPDir");
        // String localDir = module.getItemValue("localDir");
        // //syncFiles|syncFiles1|syncFiles2|syncFiles3|syncFiles4
        // String[] typeDirs = new String[]{"down_log","down_return_log"};
        // String[] changeDirs = new String[]{"ftpFiles","ftpFiles1","ftpFiles2","ftpFiles3","ftpFiles4","ftpFiles5"};
        // for(int i=0;i<changeDirs.length;i++){
        // new CSSPProducerRunnable(
        // ftpDir+"/"+typeDirs[0]+"/"+changeDirs[i],
        // localDir+"/"+typeDirs[0]+"/"+changeDirs[i],
        // filePrefix).run();
        // }
        // for(int i=0;i<changeDirs.length;i++){
        // new CSSPConsumerRunnable(
        // filePrefix,
        // new PushBuilder(),
        // localDir+"/"+typeDirs[0]+"/"+changeDirs[i]).run();
        // }
        // //LOG.info("PUSH文件入库完毕！");
        // filePrefix = "PUSHREPORT_" + day;
        // for(int i=0;i<changeDirs.length;i++){
        // new CSSPProducerRunnable(
        // ftpDir+"/"+typeDirs[1]+"/"+changeDirs[i],
        // localDir+"/"+typeDirs[1]+"/"+changeDirs[i],
        // filePrefix).run();
        // }
        // for(int i=0;i<changeDirs.length;i++){
        // new CSSPConsumerRunnable(
        // filePrefix,
        // new PushReportBuilder(),
        // localDir+"/"+typeDirs[1]+"/"+changeDirs[i]).run();
        // }
        // LOG.info("PUSHREPORT文件入库完毕！");
        // end...
    }

    private void csspReport()
    {
        Date date = new Date();
        String day = df.format(date);
        String filePrefix = "report_" + day;

        ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
        String ftpDir = module.getItemValue("FTPDir");
        String localDir = module.getItemValue("localDir");
        // syncFiles|syncFiles1|syncFiles2
        String typeDirs = "order_log";
        String[] changeDirs = new String[] { "ftpFiles", "ftpFiles1", "ftpFiles2" };
        for (int i = 0; i < changeDirs.length; i++)
        {
            new CSSPProducerRunnable(ftpDir + "/" + typeDirs + "/" + changeDirs[i], localDir + "/" + typeDirs + "/" + changeDirs[i], filePrefix).run();
        }
        for (int i = 0; i < changeDirs.length; i++)
        {
            new CSSPConsumerRunnable(filePrefix, new ReportBuilder(), localDir + "/" + typeDirs + "/" + changeDirs[i]).run();
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
            //System.out.println("notify.log*.bak免费应用下载回执日志文件解析入库开始....需要一段时间请耐心等待.....");
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
            //System.out.println("notify.log*.bak免费应用下载回执日志文件解析入库结束====>总耗时：" + (end - begin) + " 毫秒\r");
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
            //System.out.println("report_*.log免费应用订购记录解析入库开始....需要一段时间请耐心等待.....");
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
            //System.out.println("report_*.log免费应用订购记录解析入库结束====>总耗时：" + (end - begin) + " 毫秒\r");
        }
    }

}
