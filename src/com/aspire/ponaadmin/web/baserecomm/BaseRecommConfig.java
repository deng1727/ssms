/*
 * 文件名：BaseRecommConfig.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.baserecomm;

import java.io.File;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class BaseRecommConfig
{

    /**
     * 记录日志的实例对象
     */
    private static JLogger LOG = LoggerFactory.getLogger(BaseRecommConfig.class);

    /**
     * 本地生成目录。
     */
    public static String LOCALFILEPATH;

    /**
     * 导出文件上传FTP服务地址
     */
    public static String FTPIP;

    /**
     * 导出文件上传的FTP服务端口
     */
    public static int FTPPORT;

    /**
     * 导出文件上传的FTP的登录用户名
     */
    public static String FTPUSER;

    /**
     * 导出文件上传的FTP的登录密码
     */
    public static String FTPPASS;

    /**
     * FTP存放目录
     */
    public static String FTPDIR;

    /**
     * 文件中只可以存在最多数据
     */
    public static int DATANUM;

    public static void loadConfig() throws Exception
    {
        LOG.info("MM基地业务数字内容推荐开始读取配置项");

        // 初始化配置项。
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("baseDataSys");
        if (module == null)
        {
            LOG.error("system-config.xml文件中找不到baseDataSys模块");
            System.out.println("system-config.xml文件中找不到baseDataSys模块,自动更新无法正确进行");
            throw new BOException("system-config.xml文件中找不到baseDataSys模块");

        }
        LOCALFILEPATH = module.getItemValue("localFilePath");
        LOG.info("localFilePath=" + LOCALFILEPATH);
        FTPIP = module.getItemValue("FTPServerIP");
        LOG.info("FTPServerIP=" + FTPIP);
        FTPPORT = Integer.parseInt(module.getItemValue("FTPServerPort"));
        LOG.info("FTPServerPort=" + FTPPORT);
        FTPUSER = module.getItemValue("FTPServerUser");
        LOG.info("FTPServerUser=" + FTPUSER);
        FTPPASS = module.getItemValue("FTPServerPassword");
        LOG.info("FTPServerPassword=" + FTPPASS);
        FTPDIR = module.getItemValue("FTPDir");
        LOG.info("FTPDir=" + FTPDIR);
        DATANUM = Integer.parseInt(module.getItemValue("fileDataNum"));
        LOG.info("fileDataNum=" + DATANUM);

        LOCALFILEPATH = LOCALFILEPATH.endsWith(File.separator) ? LOCALFILEPATH
                        : LOCALFILEPATH + File.separator;
    }

}
