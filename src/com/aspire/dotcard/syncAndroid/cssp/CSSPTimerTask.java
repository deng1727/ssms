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
     * ��־����
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CSSPTimerTask.class);

    /**
     * ����run���з���
     */
    public void run()
    {

        try
        {
            LOG.info("CSSP �Ĵ���ʼ�ˡ�����");
            long a = System.currentTimeMillis();
            // ����Ҫ��ʼ����־�����ϵ�ָ���ļ�����������

            // push();

            // report_20130607_00000151.log
            csspReport();


            LOG.info("�������Ӧ��ʵʱ����������ͬ����ʼ....");

            long begin = System.currentTimeMillis();

            LOG.info("��ʼ���Ӧ�����ػ�ִ��־...");
            // ���Ӧ�����ػ�ִ��־
            freeDLReport();
            
            LOG.info("��ʼ���Ӧ�ö�����¼...");
            // ���Ӧ�ö�����¼
            freeDLOrder();

            LOG.info("t_a_report��ʼ��������...");
            // ���Ӧ�����ػ�ִ��־
            CSSPDAO.getInstance().insertDownloadReport();

            long end = System.currentTimeMillis();
            
            LOG.info("�������Ӧ��ʵʱ����������ͬ������====>�ܺ�ʱ " + (end - begin) + " ����");
           
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
            CSSPDAO.getInstance().realtimeDowncount();// t_a_messagesҪ����Ϣ�ĵط���

            // //��Ҫ�����t_a_push������3���ڣ���t_a_pushreport������û�д���ģ�����HANDLE_STATUS=-1�� add by aiyan 2013-04-25
            // CSSPDAO.getInstance().clearData();

            LOG.info("CSSPTimerTask time--> " + (System.currentTimeMillis() - a));
            LOG.info("CSSP �Ĵ��������30���Ӻ�����ġ�����");
        }
        catch (Exception e)
        {
            LOG.error("CSSPTimerTask ���ִ���", e);
            e.printStackTrace();
        }
    }

    private void push()
    {

        // PUSH_20130105_00000001.log ��ȡ�����������ļ���
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
        // //LOG.info("PUSH�ļ������ϣ�");
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
        // LOG.info("PUSHREPORT�ļ������ϣ�");
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
     * T_free_dl_report ���Ӧ�����ػ�ִ��־ notify.log.2013-10-28.11.bak
     */
    private void freeDLReport()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("notify.log*.bak���Ӧ�����ػ�ִ��־�ļ�������⿪ʼ");
            //System.out.println("notify.log*.bak���Ӧ�����ػ�ִ��־�ļ�������⿪ʼ....��Ҫһ��ʱ�������ĵȴ�.....");
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
            //System.out.println("notify.log*.bak���Ӧ�����ػ�ִ��־�ļ�����������====>�ܺ�ʱ��" + (end - begin) + " ����\r");
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
            //System.out.println("report_*.log���Ӧ�ö�����¼������⿪ʼ....��Ҫһ��ʱ�������ĵȴ�.....");
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
            //System.out.println("report_*.log���Ӧ�ö�����¼����������====>�ܺ�ʱ��" + (end - begin) + " ����\r");
        }
    }

}
