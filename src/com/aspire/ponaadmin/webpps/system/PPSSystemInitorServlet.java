package com.aspire.ponaadmin.webpps.system;

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
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightModel;
import com.aspire.ponaadmin.web.constant.ResourceUtil;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.system.ProductInfo;
import com.aspire.ponaadmin.web.system.SystemInitorServlet;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.webpps.common.helper.BbsSSOHelper;

public class PPSSystemInitorServlet extends HttpServlet{

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
            
            
            //初始化电子流社区管理中心访问路径
            String bbsssoConfigFile = config.getInitParameter("bbsssoConfigFile");
            if (!bbsssoConfigFile.startsWith("/"))
            {
            	bbsssoConfigFile = "/" + bbsssoConfigFile;
            }
            bbsssoConfigFile = prefix + bbsssoConfigFile;
            bbsssoConfigFile = PublicUtil.toOSDir(bbsssoConfigFile);
            BbsSSOHelper.init(bbsssoConfigFile);
            
            

            /** *****初始化版本信息**** */
            initProductInfo(config);

            /** *******后台异步任务执行管理器初始化*********** */
            DaemonTaskRunner.getInstance().init();

            /** *******初始化权限控制*********** */
//            RightManagerBO.getInstance().registerRightModel(RightModel.getInstance());

            /** *******货架管理中心*********** */
            Repository.getInstance().init(prefix + "/conf/repository_db.xml");

            /** *******提示语的初始化*********** */
            ResourceUtil.init();
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
