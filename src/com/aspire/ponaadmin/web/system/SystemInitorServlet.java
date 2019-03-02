
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
 * 系统初始化类
 * </p>
 * <p>
 * 初始化系统，包括：环境信息、日志、数据库、异步任务管理器
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
     * 初始化方法
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

            // 设置默认的时区
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));

            /** **系统名称***** */
            ServerInfo.setSystemName(config.getInitParameter("systemName"));

            /** **设置服务器运行路径***** */
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

            /** **初始化日志系统***** */
            String logConfig = config.getInitParameter("logConfig");
            if (!logConfig.startsWith("/"))
                logConfig = "/" + logConfig;
            logConfig = prefix + logConfig;
            // 根据操作系统不同修改操作系统的目录符号
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

            /** ****数据库配置********** */
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

            /** *******系统配置初始化*************** */
            Config.getInstance().init();
            
            
            //初始化mportal访问路径
            String mportalConfigFile = config.getInitParameter("mportalConfigFile");
            if (!mportalConfigFile.startsWith("/"))
            {
            	mportalConfigFile = "/" + mportalConfigFile;
            }
            mportalConfigFile = prefix + mportalConfigFile;
            mportalConfigFile = PublicUtil.toOSDir(mportalConfigFile);
            MportalHelper.init(mportalConfigFile);
            
            

            /** *****初始化版本信息**** */
            initProductInfo(config);

            /** *******后台异步任务执行管理器初始化*********** */
            DaemonTaskRunner.getInstance().init();

            /** *******初始化权限控制*********** */
//            RightManagerBO.getInstance().registerRightModel(RightModel.getInstance());
            

            /** *******初始化日志控制*********** */
            DelActionLog.getInstance();

            /** *******货架管理中心*********** */
            Repository.getInstance().init(prefix + "/conf/repository_db.xml");

            /** *******提示语的初始化*********** */
            ResourceUtil.init();
            /** *******商品中心数据同步*********** */
            
            /** *****PPMS数据同步***** */
            DataSyncTimer.start();
            
            /** 货架更新邮件发送***/
            MailTimer.start();
            /** *****PPMS创业大赛数据同步***** */
            CYDataSyncTimer.start();

            /* *//** *****DCMP资讯内容同步******* */
            /*
             * new DCMPTask().start();
             */

            /** *******彩铃数据同步*********** */
          //  ColorringLoadTimer.start();

            /** *******基地音乐数据同步*********** */
            BaseMusicLoadTimer.getInstance().start();

            /** 基地图书数据同步* */
            RBookLoadTimer.getInstance().start();

            /** *******产品运营属性同步******* */
            TopDataImportTimer.start();
            
            /** ******启动1.3触点应用和MM应用******* */
            EssentialTimer.start();
            /** *******A8音乐数据同步******* */
            // A8MusicImportTimer.start();
            /** *******A8歌手数据同步******* */
            // A8SingerImportTimer.start();
            /** *******A8榜单数据同步******* */
            // A8TopListTimer.start();
            /** *******初始化DataSync数据同步模块******* */
            DataSyncInitor.getInstance().init(prefix + "/conf/datasync.xml");

            /** *******货架自动更新任务初始化******************* */
            // del by wml 20101228
            CategoryMgr.getInstance().init();
            // CategoryMgr.getInstance().loadConfig();
            
            /** *************数据导入**************** */
            RankTimer.start();
            /** *******货架自动导出 任务初始化******************* */
            //CategoryExportTimer.start(); del by wml 131207

            /** *******索引文件导出 任务初始化******************* */
            TaskArrangeTimer.arrangeTask(new SearchFileGenerateTask());
            TaskArrangeTimer.arrangeTask(new SearchFileGenerateCYTask());
            
            /** *******音乐货架歌曲试听日统计数据报表导入 任务初始化******************* */
           // TaskArrangeTimer.arrangeTask(new A8StatisticImportTask());
            /** *******推荐(最新)(精品)(黑名单)应用榜日统计数据报表导入 任务初始化******************* */
            TaskArrangeTimer.arrangeTask(new AppStatisticImportTask());

            /** *******资费变更监控******************* */
            //new ServiceSyncThread(SyncDataConfig.ServiceQueryInterval).start();//del by dongke 20110622

            /** *******人工干预榜单定时任务 初始化******************* */
            // del by wml 20101229
            TaskArrangeTimer.arrangeTask(new IntervenorTask());
            // 加载自动更新应用文件
            CategoryUpdateConfig.getInstance()
                                .init(prefix + "/conf/categoryupdateconfig.xml");
            /** ****** 体验营销劳动竞赛活动 数据生成 ******** */
            ExperienceTimer.start();

            /**
             * 第三方渠道营销数据生成
             */
            ThirdChannelMkTimer.getInstance().start();

            /**
             * 实体卡需要的AP信息数据导出
             */
            EntityCardTimer.getInstance().start();
            
            /** 心机同步任务启动* */
            //AppTopTimer.getInstance().start();

            /** *******创业大赛货架自动更新任务初始化******************* */
            CategoryByCarveOutMgr.getInstance().init();

            /**
             * 同步精品库内容任务初始化
             */
            // del by wml 20101229
            // SynCategoryMgr.getInstance().init();
            SynCategoryMgr.getInstance().loadConfig();

            /**
             * MM基地业务数字内容推荐开始读取配置项
             */
            BaseRecommConfig.loadConfig();
            WapCategoryExportTimer.start();

            /**
             * AP刷榜预警
             */
            ApWarnTask.getInstance().start();
            //圈子营销，飞信139邮箱，139说客数据导出
            CircleTimer.start();
            /**
             * 新139音乐数据导入任务 add by dongke 20110503
             */
            //New139MusicTimer.start();//---------------XXXXXXXXXXXXXXXXXXXXXXX
            
            /** *******报表榜单数据文件处理任务*********** */
            TopListTask.getInstance().start();

            // wml+用于为浙江MSTORE平台生成文件应用
            BaseFileTimer.getInstance().start();
            
            // wml+用于日志维表文件与对账文件生成应用
            LogBaseFileTimer.getInstance().start();
            
            //动漫基地数据导入
            BaseComicLoadTimer.getInstance().start();
            
            // wml+视频数据接入
            BaseVideoNewTime.start();
            
            // wml+视频数据接入
            com.aspire.dotcard.baseread.timer.RBookLoadTimer.getInstance().start();
            
            //基地业务自动上下架(类似于MM的自动任务一样的东西，呵呵)
            SyncTacticTimer.getInstance().start();
            
            //视频LOGOPATH线程的处理。
            //  LogopathTimer.getInstance().start();
            
            // 紧急上下线自动任务
            ContentExigenceTimer.getInstance().start();
            
            // wml+生成月度全量应用信息同步文件
            AllAppExperienceTimer.start();
            
            //商品库优化开始
            //LOG.info("商品库优化还没有上，等等吧....hehe！！！");
            PPMSTimer.start();//商品库优化还没有上线，故先注销。
            OtPPMSTimer.start();//33商品上下线
            OtPPMSPretreatment.start();//扫描消息表写入预处理表
            OtPPMSChange.start();//扫描预处理表写入t_a_ppms_receive_change表
            MessageSendTimer.start();//消息发送的
            AndroidListTimer.start();//android榜单的
            CSSPTimer.start();//cssp数据导入
            //商品库优化结束
            
            //AWMS的货架和商品同步
            AwmsTimer.start();
            
            // 统一导出模块启动
            DataExportTimer.start();
            
            BaseColorComicTime.start();
            
            // 向数据中心同步自动更新货架
            AutoSyncTimer.start();
            
            // 每天监控重点系列指定时间（一周）内和娱乐IAP榜单数量
            IapMonitorTimer.start();
            
            //视频按小时增量同步
            BaseVideoByHourTime.start();
            AppInfoByTime.start();
            //新基地视频同步
            BaseVideoTimer.start();
            //新基地视频按小时增量同步
            com.aspire.dotcard.basevideosync.sync.BaseVideoByHourTimer.start();
            AppMonitorTimer.start();
            //新基地视频api请求数据同步
            com.aspire.dotcard.basevideosync.sync.BaseVideoApiRequestTimer.start();
            
//            List<String> list = DataSyncDAO.getInstance().checkTSynResult();
//            System.out.println(list.toString());
//            DataSyncDAO.getInstance().updateTClmsContentTag(list);
            
            
            /*暂时不执行，待功能完成后开放*/
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
        /** **系统配置文件***** */

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
     * 载入产品相关信息。
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
