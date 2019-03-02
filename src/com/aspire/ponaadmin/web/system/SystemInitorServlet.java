
package com.aspire.ponaadmin.web.system;

import java.util.TimeZone;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.config.SystemConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.Constant;
import com.aspire.dotcard.appinfosyn.AppInfoByTime;
import com.aspire.dotcard.appmonitor.timer.AppMonitorTimer;
import com.aspire.dotcard.awms.AwmsTimer;
import com.aspire.dotcard.baseVideoNew.sync.BaseVideoByHourTime;
import com.aspire.dotcard.baseVideoNew.sync.BaseVideoNewTime;
import com.aspire.dotcard.basebook.timer.RBookLoadTimer;
import com.aspire.dotcard.basecolorcomic.sync.BaseColorComicTime;
import com.aspire.dotcard.basecomic.BaseComicLoadTimer;
import com.aspire.dotcard.basecommon.SyncTacticTimer;
import com.aspire.dotcard.basemusic.BaseMusicLoadTimer;
import com.aspire.dotcard.basevideosync.sync.BaseVideoTimer;
import com.aspire.dotcard.cysyncdata.task.CYDataSyncTimer;
import com.aspire.dotcard.essential.task.EssentialTimer;
import com.aspire.dotcard.iapMonitor.timer.IapMonitorTimer;
import com.aspire.dotcard.rank.task.RankTimer;
import com.aspire.dotcard.reportdata.TopDataImportTimer;
import com.aspire.dotcard.reportdata.appstatistic.AppStatisticImportTask;
import com.aspire.dotcard.reportdata.cystatistic.TopListTask;
import com.aspire.dotcard.searchfile.SearchFileGenerateCYTask;
import com.aspire.dotcard.searchfile.SearchFileGenerateTask;
import com.aspire.dotcard.syncAndroid.autosync.task.AutoSyncTimer;
import com.aspire.dotcard.syncAndroid.cssp.CSSPTimer;
import com.aspire.dotcard.syncAndroid.dc.MessageSendTimer;
import com.aspire.dotcard.syncAndroid.ppms.OtPPMSChange;
import com.aspire.dotcard.syncAndroid.ppms.OtPPMSPretreatment;
import com.aspire.dotcard.syncAndroid.ppms.OtPPMSTimer;
import com.aspire.dotcard.syncAndroid.ppms.PPMSTimer;
import com.aspire.dotcard.syncAndroid.ssms.AndroidListTimer;
import com.aspire.dotcard.syncData.task.DataSyncTimer;
import com.aspire.dotcard.syncData.task.MailTimer;
import com.aspire.dotcard.syncGoodsCenter.timer.GoodCenterIncrementTimer;
import com.aspire.dotcard.syncGoodsCenter.timer.GoodCenterTimer;
import com.aspire.mm.common.client.mportal.MportalHelper;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightModel;
import com.aspire.ponaadmin.web.actionlog.DelActionLog;
import com.aspire.ponaadmin.web.baserecomm.BaseRecommConfig;
import com.aspire.ponaadmin.web.category.CategoryByCarveOutMgr;
import com.aspire.ponaadmin.web.category.CategoryMgr;
import com.aspire.ponaadmin.web.category.CategoryUpdateConfig;
import com.aspire.ponaadmin.web.category.SynCategoryMgr;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorTask;
import com.aspire.ponaadmin.web.constant.ResourceUtil;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.dataexport.apwarn.timer.ApWarnTask;
import com.aspire.ponaadmin.web.dataexport.basefile.task.BaseFileTimer;
import com.aspire.ponaadmin.web.dataexport.basefile.task.LogBaseFileTimer;
import com.aspire.ponaadmin.web.dataexport.channel.task.ThirdChannelMkTimer;
import com.aspire.ponaadmin.web.dataexport.circle.task.CircleTimer;
import com.aspire.ponaadmin.web.dataexport.entitycard.timer.EntityCardTimer;
import com.aspire.ponaadmin.web.dataexport.experience.AllAppExperienceTimer;
import com.aspire.ponaadmin.web.dataexport.experience.task.ExperienceTimer;
import com.aspire.ponaadmin.web.dataexport.sqlexport.timer.DataExportTimer;
import com.aspire.ponaadmin.web.dataexport.wapcategory.WapCategoryExportTimer;
import com.aspire.ponaadmin.web.datasync.DataSyncInitor;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.web.timer.ContentExigenceTimer;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.TaskArrangeTimer;

/**
 * <p>
 * ϵͳ��ʼ����
 * </p>
 * <p>
 * ��ʼ��ϵͳ��������������Ϣ����־�����ݿ⡢�첽���������
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class SystemInitorServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    JLogger LOG = LoggerFactory.getLogger(SystemInitorServlet.class);

    /**
     * ��ʼ������
     * 
     * @param config ServletConfig
     * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException
    {
        try
        {
            super.init(config);

            // ����Ĭ�ϵ�ʱ��
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));

            /** **ϵͳ����***** */
            ServerInfo.setSystemName(config.getInitParameter("systemName"));

            /** **���÷���������·��***** */
            String prefix = null;
            String webServer = config.getInitParameter("webServer");
            if (webServer == null)
            {
                webServer = "";
            }
            if (webServer.toUpperCase().equals("TOMCAT"))
            {
                prefix = config.getServletContext().getRealPath(".")
                         + "/WEB-INF/";
                ServerInfo.setAppRootPath(prefix);
                prefix = ServerInfo.getAppRootPath();
            }
            else
            {
                prefix = ServerInfo.getAppRootPath();
            }
            this.initConfigFile(config);

            /** **��ʼ����־ϵͳ***** */
            String logConfig = config.getInitParameter("logConfig");
            if (!logConfig.startsWith("/"))
                logConfig = "/" + logConfig;
            logConfig = prefix + logConfig;
            // ���ݲ���ϵͳ��ͬ�޸Ĳ���ϵͳ��Ŀ¼����
            logConfig = PublicUtil.toOSDir(logConfig);
            /** logRefresh * */
            String logRefreshTime = config.getInitParameter("logRefresh");
            if (logRefreshTime == null)
                logRefreshTime = "60";
            LoggerFactory.configLog(logConfig, logRefreshTime);
            String errFile = config.getInitParameter("errmsgConfig");
            if (!errFile.startsWith("/"))
                errFile = "/" + errFile;
            errFile = prefix + errFile;
            errFile = PublicUtil.toOSDir(errFile);
            LoggerFactory.loadErrorInfo(errFile);

            /** ****���ݿ�����********** */
            String persistenceFile = config.getInitParameter("persistence-config");
            if (!persistenceFile.startsWith("/"))
            {
                persistenceFile = "/" + persistenceFile;
            }
            persistenceFile = prefix + persistenceFile;
            persistenceFile = PublicUtil.toOSDir(persistenceFile);
            Constant.PERSISTENCE_PROP = persistenceFile;
            String sqlFile = config.getInitParameter("sql-config");
            if (!sqlFile.startsWith("/"))
            {
                sqlFile = "/" + sqlFile;
            }
            sqlFile = prefix + sqlFile;
            sqlFile = PublicUtil.toOSDir(sqlFile);
            Constant.SQL_PROP = sqlFile;

            /** *******ϵͳ���ó�ʼ��*************** */
            Config.getInstance().init();
            
            
            //��ʼ��mportal����·��
            String mportalConfigFile = config.getInitParameter("mportalConfigFile");
            if (!mportalConfigFile.startsWith("/"))
            {
            	mportalConfigFile = "/" + mportalConfigFile;
            }
            mportalConfigFile = prefix + mportalConfigFile;
            mportalConfigFile = PublicUtil.toOSDir(mportalConfigFile);
            MportalHelper.init(mportalConfigFile);
            
            

            /** *****��ʼ���汾��Ϣ**** */
            initProductInfo(config);

            /** *******��̨�첽����ִ�й�������ʼ��*********** */
            DaemonTaskRunner.getInstance().init();

            /** *******��ʼ��Ȩ�޿���*********** */
//            RightManagerBO.getInstance().registerRightModel(RightModel.getInstance());
            

            /** *******��ʼ����־����*********** */
            DelActionLog.getInstance();

            /** *******���ܹ�������*********** */
            Repository.getInstance().init(prefix + "/conf/repository_db.xml");

            /** *******��ʾ��ĳ�ʼ��*********** */
            ResourceUtil.init();
            /** *******��Ʒ��������ͬ��*********** */
            
            /** *****PPMS����ͬ��***** */
            DataSyncTimer.start();
            
            /** ���ܸ����ʼ�����***/
            MailTimer.start();
            /** *****PPMS��ҵ��������ͬ��***** */
            CYDataSyncTimer.start();

            /* *//** *****DCMP��Ѷ����ͬ��******* */
            /*
             * new DCMPTask().start();
             */

            /** *******��������ͬ��*********** */
          //  ColorringLoadTimer.start();

            /** *******������������ͬ��*********** */
            BaseMusicLoadTimer.getInstance().start();

            /** ����ͼ������ͬ��* */
            RBookLoadTimer.getInstance().start();

            /** *******��Ʒ��Ӫ����ͬ��******* */
            TopDataImportTimer.start();
            
            /** ******����1.3����Ӧ�ú�MMӦ��******* */
            EssentialTimer.start();
            /** *******A8��������ͬ��******* */
            // A8MusicImportTimer.start();
            /** *******A8��������ͬ��******* */
            // A8SingerImportTimer.start();
            /** *******A8������ͬ��******* */
            // A8TopListTimer.start();
            /** *******��ʼ��DataSync����ͬ��ģ��******* */
            DataSyncInitor.getInstance().init(prefix + "/conf/datasync.xml");

            /** *******�����Զ����������ʼ��******************* */
            // del by wml 20101228
            CategoryMgr.getInstance().init();
            // CategoryMgr.getInstance().loadConfig();
            
            /** *************���ݵ���**************** */
            RankTimer.start();
            /** *******�����Զ����� �����ʼ��******************* */
            //CategoryExportTimer.start(); del by wml 131207

            /** *******�����ļ����� �����ʼ��******************* */
            TaskArrangeTimer.arrangeTask(new SearchFileGenerateTask());
            TaskArrangeTimer.arrangeTask(new SearchFileGenerateCYTask());
            
            /** *******���ֻ��ܸ���������ͳ�����ݱ����� �����ʼ��******************* */
           // TaskArrangeTimer.arrangeTask(new A8StatisticImportTask());
            /** *******�Ƽ�(����)(��Ʒ)(������)Ӧ�ð���ͳ�����ݱ����� �����ʼ��******************* */
            TaskArrangeTimer.arrangeTask(new AppStatisticImportTask());

            /** *******�ʷѱ�����******************* */
            //new ServiceSyncThread(SyncDataConfig.ServiceQueryInterval).start();//del by dongke 20110622

            /** *******�˹���Ԥ�񵥶�ʱ���� ��ʼ��******************* */
            // del by wml 20101229
            TaskArrangeTimer.arrangeTask(new IntervenorTask());
            // �����Զ�����Ӧ���ļ�
            CategoryUpdateConfig.getInstance()
                                .init(prefix + "/conf/categoryupdateconfig.xml");
            /** ****** ����Ӫ���Ͷ������ �������� ******** */
            ExperienceTimer.start();

            /**
             * ����������Ӫ����������
             */
            ThirdChannelMkTimer.getInstance().start();

            /**
             * ʵ�忨��Ҫ��AP��Ϣ���ݵ���
             */
            EntityCardTimer.getInstance().start();
            
            /** �Ļ�ͬ����������* */
            //AppTopTimer.getInstance().start();

            /** *******��ҵ���������Զ����������ʼ��******************* */
            CategoryByCarveOutMgr.getInstance().init();

            /**
             * ͬ����Ʒ�����������ʼ��
             */
            // del by wml 20101229
            // SynCategoryMgr.getInstance().init();
            SynCategoryMgr.getInstance().loadConfig();

            /**
             * MM����ҵ�����������Ƽ���ʼ��ȡ������
             */
            BaseRecommConfig.loadConfig();
            WapCategoryExportTimer.start();

            /**
             * APˢ��Ԥ��
             */
            ApWarnTask.getInstance().start();
            //Ȧ��Ӫ��������139���䣬139˵�����ݵ���
            CircleTimer.start();
            /**
             * ��139�������ݵ������� add by dongke 20110503
             */
            //New139MusicTimer.start();//---------------XXXXXXXXXXXXXXXXXXXXXXX
            
            /** *******����������ļ���������*********** */
            TopListTask.getInstance().start();

            // wml+����Ϊ�㽭MSTOREƽ̨�����ļ�Ӧ��
            BaseFileTimer.getInstance().start();
            
            // wml+������־ά���ļ�������ļ�����Ӧ��
            LogBaseFileTimer.getInstance().start();
            
            //�����������ݵ���
            BaseComicLoadTimer.getInstance().start();
            
            // wml+��Ƶ���ݽ���
            BaseVideoNewTime.start();
            
            // wml+��Ƶ���ݽ���
            com.aspire.dotcard.baseread.timer.RBookLoadTimer.getInstance().start();
            
            //����ҵ���Զ����¼�(������MM���Զ�����һ���Ķ������Ǻ�)
            SyncTacticTimer.getInstance().start();
            
            //��ƵLOGOPATH�̵߳Ĵ���
            //  LogopathTimer.getInstance().start();
            
            // �����������Զ�����
            ContentExigenceTimer.getInstance().start();
            
            // wml+�����¶�ȫ��Ӧ����Ϣͬ���ļ�
            AllAppExperienceTimer.start();
            
            //��Ʒ���Ż���ʼ
            //LOG.info("��Ʒ���Ż���û���ϣ��ȵȰ�....hehe������");
            PPMSTimer.start();//��Ʒ���Ż���û�����ߣ�����ע����
            OtPPMSTimer.start();//33��Ʒ������
            OtPPMSPretreatment.start();//ɨ����Ϣ��д��Ԥ�����
            OtPPMSChange.start();//ɨ��Ԥ�����д��t_a_ppms_receive_change��
            MessageSendTimer.start();//��Ϣ���͵�
            AndroidListTimer.start();//android�񵥵�
            CSSPTimer.start();//cssp���ݵ���
            //��Ʒ���Ż�����
            
            //AWMS�Ļ��ܺ���Ʒͬ��
            AwmsTimer.start();
            
            // ͳһ����ģ������
            DataExportTimer.start();
            
            BaseColorComicTime.start();
            
            // ����������ͬ���Զ����»���
            AutoSyncTimer.start();
            
            // ÿ�����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������
            IapMonitorTimer.start();
            
            //��Ƶ��Сʱ����ͬ��
            BaseVideoByHourTime.start();
            AppInfoByTime.start();
            //�»�����Ƶͬ��
            BaseVideoTimer.start();
            //�»�����Ƶ��Сʱ����ͬ��
            com.aspire.dotcard.basevideosync.sync.BaseVideoByHourTimer.start();
            AppMonitorTimer.start();
            //�»�����Ƶapi��������ͬ��
            com.aspire.dotcard.basevideosync.sync.BaseVideoApiRequestTimer.start();
            
//            List<String> list = DataSyncDAO.getInstance().checkTSynResult();
//            System.out.println(list.toString());
//            DataSyncDAO.getInstance().updateTClmsContentTag(list);
            
            
            /*��ʱ��ִ�У���������ɺ󿪷�*/
            GoodCenterTimer.start();
            
            GoodCenterIncrementTimer.start();
            
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param config
     */
    private void initConfigFile(ServletConfig config)
    {
        /** **ϵͳ�����ļ�***** */

        String configFileName = config.getInitParameter("configFile");
        SystemConfig systemConfig = ConfigFactory.getSystemConfig();
        systemConfig.init();
        String configFile = configFileName.replace('/', ServerInfo.FS.charAt(0));
        if (!configFile.startsWith(ServerInfo.FS))
        {
            configFile = ServerInfo.FS + configFile;
        }
        String fullFileName = ServerInfo.getAppRootPath() + configFile;
        systemConfig.setConfigFile(fullFileName);
        systemConfig.load();

    }

    /**
     * �����Ʒ�����Ϣ��
     * 
     * @param config ServletConfig
     */
    private void initProductInfo(ServletConfig config)
    {
        String versionFileName = config.getInitParameter("versionFile");
        String patchPath = config.getInitParameter("patchPath");
        String isInternal = config.getInitParameter("isInternal");
        String patchFilter = config.getInitParameter("patchFilter");
        ProductInfo.setVersionFile(versionFileName);
        ProductInfo.setPatchPath(patchPath);
        ProductInfo.setIsInternal(isInternal);
        ProductInfo.setPatchFilter(patchFilter);
        ProductInfo.setServContext(config.getServletContext());
        ProductInfo.load();
    }
}
