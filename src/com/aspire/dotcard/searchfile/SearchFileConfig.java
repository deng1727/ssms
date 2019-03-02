
package com.aspire.dotcard.searchfile;

import java.io.File;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author zhangwei
 * 
 */
public class SearchFileConfig
{

    private static final JLogger logger = LoggerFactory.getLogger(SearchFileConfig.class);

    /**
     * 货架商品导出 定时任务触发时间。
     */
    public static final String STARTTIME;

    /**
     *  ftp服务器中存储excel的目录.本地数据文件保存的路径。以目录分割复结尾。
     */
    public static final String LOCALDIR; 

    /**
     * 周期，每日/每周。
     */
    public static final String frequency;

    /**
     * 发送邮件地址
     */
    public static final String mailTo[];
    
    /**
     * mo文件保存地址
     */
    public static final String MOPATH;
    
    /**
     * www文件保存地址
     */
    public static final String WWWPATH;
    /**
     * wap文件保存地址
     */
    public static final String WAPATH;
    /**
     * 专题文件保存地址
     */
    public static final String SUBJECT;
    
    /**
     * 视频节目文件保存地址
     */
    public static final String VIDEOPATH;
    
    /**
     * 创业大赛文件保存地址
     */
    public static final String BUSINESS;
    
    /**
     * 心机同步FTP地址
     */
    public static final String FTPIP;
    
    /**
     * 心机同步FTP端口
     */
    public static final int FTPPORT;
    
    /**
     * 心机同步FTP登录用户名
     */
    public static final String FTPNAME;
    
    /**
     * 心机同步FTP登录密码
     */
    public static final String FTPPAS;
    
    /**
     * 心机同步文件存放路径
     */
    public static final String FTPPAHT;
    
    /**
     * 心机同步任务启动时间
     */
    public static final String APPTOP_STARTTIME;
    
    /**
     * 用于导出2010创业大赛数据所在的货架id
     */
    public static final String CY_DATA_CATEGORYID;
    
    /**
     * 客户端重点应用标签
     */
    public static final String TAG;
    
    public static final String COMIC_PATH;
    
    public static int pageSize = 5000;
    
    public static String VIRTUAL_CATEGORY_ID;
    
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("searchFileGenerate");
        
        STARTTIME = module.getItemValue("startTime").trim();

     //   LOCALDIR = ServerInfo.getAppRootPath() + File.separator + "ftpdata"
    //               + File.separator + "searchfile" + File.separator;
        LOCALDIR =  module.getItemValue("LocalDir").trim();//sftp 20120727 改动的
        frequency = module.getItemValue("frequency").trim();
        
        mailTo = module.getItemValue("mailTo").trim().split(",");
        
        FTPIP = module.getItemValue("FTPIP").trim();
        
        FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
        
        FTPNAME = module.getItemValue("FTPName").trim();
        
        FTPPAS = module.getItemValue("FTPPassWord").trim();
        
        FTPPAHT = module.getItemValue("FTPPath").trim();
        
        APPTOP_STARTTIME = module.getItemValue("AppTopstartTime").trim();
        
        MOPATH = LOCALDIR + "mo"+File.separator;
        
        WWWPATH = LOCALDIR + "www"+File.separator;
        
        WAPATH=LOCALDIR + "wap"+File.separator;
        
        SUBJECT =LOCALDIR + "subject"+File.separator;
        
        VIDEOPATH = LOCALDIR + "video"+File.separator;
        
        BUSINESS  =LOCALDIR + "business"+File.separator;
        
        CY_DATA_CATEGORYID = module.getItemValue("cyDataCategoryId").trim();
        
        COMIC_PATH = LOCALDIR + "comic"+File.separator;
        
        TAG =  LOCALDIR + "tag" + File.separator;
        
        pageSize = Integer.parseInt(module.getItemValue("pageSize"));
        
        VIRTUAL_CATEGORY_ID = module.getItemValue("VIRTUAL_CATEGORY_ID").trim();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the LOCALDIR is " + LOCALDIR);
            logger.debug("the frequency is " + frequency);
            logger.debug("the mailTo is " + module.getItemValue("mailTo"));
        }
    }

}
