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
            
            
            //��ʼ�������������������ķ���·��
            String bbsssoConfigFile = config.getInitParameter("bbsssoConfigFile");
            if (!bbsssoConfigFile.startsWith("/"))
            {
            	bbsssoConfigFile = "/" + bbsssoConfigFile;
            }
            bbsssoConfigFile = prefix + bbsssoConfigFile;
            bbsssoConfigFile = PublicUtil.toOSDir(bbsssoConfigFile);
            BbsSSOHelper.init(bbsssoConfigFile);
            
            

            /** *****��ʼ���汾��Ϣ**** */
            initProductInfo(config);

            /** *******��̨�첽����ִ�й�������ʼ��*********** */
            DaemonTaskRunner.getInstance().init();

            /** *******��ʼ��Ȩ�޿���*********** */
//            RightManagerBO.getInstance().registerRightModel(RightModel.getInstance());

            /** *******���ܹ�������*********** */
            Repository.getInstance().init(prefix + "/conf/repository_db.xml");

            /** *******��ʾ��ĳ�ʼ��*********** */
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
