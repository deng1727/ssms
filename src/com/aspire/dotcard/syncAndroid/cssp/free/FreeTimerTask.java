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
 * ���Ӧ�� ��ʱ����ִ��
 */
public class FreeTimerTask extends TimerTask
{

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * ��־����
     */
    protected static JLogger LOG = LoggerFactory.getLogger(FreeTimerTask.class);

    /**
     * ����run���з���
     */
    public void run()
    {

        try
        {
            LOG.info("Free download  �Ĵ���ʼ�ˡ�����");
            long a = System.currentTimeMillis();

            if (LOG.isDebugEnabled())
            {
                System.out.println("\r\n==============================================================");
                System.out.println("�������Ӧ��ʵʱ����������ͬ����ʼ....\r");
                LOG.debug("\r\n==============================================================");
                LOG.debug("�������Ӧ��ʵʱ����������ͬ����ʼ....\r");

            }
            long begin = System.currentTimeMillis();

            // ���Ӧ�����ػ�ִ��־
            freeDLReport();

            // ���Ӧ�ö�����¼
            freeDLOrder();

            // ���Ӧ�����ػ�ִ��־
            CSSPDAO.getInstance().insertDownloadReport();

            long end = System.currentTimeMillis();
            if (LOG.isDebugEnabled())
            {
                System.out.println("�������Ӧ��ʵʱ����������ͬ������====>�ܺ�ʱ " + (end - begin) + " ����");
                System.out.println("==============================================================\r\n");
                LOG.debug("�������Ӧ��ʵʱ����������ͬ������====>�ܺ�ʱ " + (end - begin) + " ����");
                LOG.debug("==============================================================\r\n");
            }

            CSSPDAO.getInstance().realtimeDowncount();// t_a_messagesҪ����Ϣ�ĵط���

            LOG.info("FreeTimerTask time--> " + (System.currentTimeMillis() - a));
            LOG.info("Free �Ĵ��������2���Ӻ�����ġ�����");
        }
        catch (Exception e)
        {
            LOG.error("FreeTimerTask ���ִ���", e);
        }
    }

    /**
     * T_free_dl_report ���Ӧ�����ػ�ִ��־ notify.log.2013-10-28.11.bak
     */
    private void freeDLReport()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("notify.log*.bak���Ӧ�����ػ�ִ��־�ļ�������⿪ʼ");
            System.out.println("notify.log*.bak���Ӧ�����ػ�ִ��־�ļ�������⿪ʼ....��Ҫһ��ʱ�������ĵȴ�.....");
        }
        long begin = System.currentTimeMillis();

        Date date = new Date();
        String day = df2.format(date);
        String filePrefix = "notify.log." + day;
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ��ǰʱ���ļ���" + filePrefix);
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
            LOG.debug("notify.log*.bak���Ӧ�����ػ�ִ��־�ļ�����������!");
            LOG.debug("notify.log*.bak���Ӧ�����ػ�ִ��־�ļ��������====>�ܺ�ʱ��" + (end - begin) + " ����");
            System.out.println("notify.log*.bak���Ӧ�����ػ�ִ��־�ļ�����������====>�ܺ�ʱ��" + (end - begin) + " ����\r");
        }
    }

    /**
     * T_free_dl_order ���Ӧ�ö�����¼ report_20131024_00000001.log
     */
    private void freeDLOrder()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("report_*.log���Ӧ�ö�����¼������⿪ʼ");
            System.out.println("report_*.log���Ӧ�ö�����¼������⿪ʼ....��Ҫһ��ʱ�������ĵȴ�.....");
        }
        long begin = System.currentTimeMillis();

        Date date = new Date();
        String day = df.format(date);
        String filePrefix = "report_" + day;
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ��ǰʱ���ļ���" + filePrefix);
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
            LOG.debug("report_*.log���Ӧ�ö�����¼����������");
            LOG.debug("report_*.log���Ӧ�ö�����¼�������====>�ܺ�ʱ��" + (end - begin) + " ����");
            System.out.println("report_*.log���Ӧ�ö�����¼����������====>�ܺ�ʱ��" + (end - begin) + " ����\r");
        }
    }

}
