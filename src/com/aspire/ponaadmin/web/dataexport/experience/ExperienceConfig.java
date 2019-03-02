/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience ExperienceConfig.java
 * Jul 7, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.experience;



import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


/**
 * @author tungke
 *体验营销系统数据导出配置项
 */
public class ExperienceConfig
{


    private static final JLogger logger = LoggerFactory.getLogger(ExperienceConfig.class);

    /**
     * 体验营销系统数据导出 定时任务触发时间。
     */
    public static final String STARTTIME;

    /**
     *  ftp服务器中存储文件的目录.本地数据文件保存的路径。以目录分割复结尾。
     */
    public static final String LOCALDIR; 

    
    /**
     * 发送邮件地址
     */
    public static final String mailTo[];
    
   
    
    /**
     * 体验营销系统数据导出 FTP地址
     */
    public static final String FTPIP;
    
    /**
     * 体验营销系统数据导出 FTP端口
     */
    public static final int FTPPORT;
    
    /**
     * 体验营销系统数据导出FTP登录用户名
     */
    public static final String FTPNAME;
    
    /**
     * 体验营销系统数据导出FTP登录密码
     */
    public static final String FTPPAS;
    
    /**
     * 体验营销系统数据导出文件存放路径
     */
    public static final String FTPPAHT;
    
    /**
     * 体验营销系统数据导出文件格式
     */
    public static final String ExperEncoding;
    /**
     * 应用分类信息同步文件名
     */
    public static final String categoryName;
    /**
     * 应用分类信息同步文件行分隔符
     */
    public static final String categoryLineSep;
    /**
     * 应用分类信息同步文件列分隔符
     */
    public static final String categoryColumnSep;
    /**
     * 全量机型信息同步文件名
     */
    public static final String FullDeviceName;
    /**
     * 增量机型信息同步文件名
     */
    public static final String deviceName;
    /**
     * 机型信息同步文件行分隔符
     */
    public static final String deviceLineSep;
    /**
     * 机型信息同步文件列分隔符
     */
    public static final String deviceColumnSep;
    /**
     * 全量应用信息同步文件名
     */
    public static final String APPFullName;
    /**
     * 增量应用信息同步文件名
     */
    public static final String APPNewName;
    /**
     * 增量应用信息同步文件列分隔符
     */
    public static final String APPNewColumnSep;
    
    /**
     * 应用信息更新同步文件名
     */
    public static final String APPUpdateName;
    /**
     * 应用信息更新同步文件列分隔符
     */
    public static final String APPUpdateColumnSep;
   
    /**
     * 榜单应用信息同步文件名
     */
    public static final String APPName;
    /**
     * 榜单应用信息同步文件行分隔符
     */
    public static final String APPLineSep;
    /**
     * 榜单应用信息同步文件列分隔符
     */
    public static final String APPColumnSep;
    
    /**
     * 按月生成全量同步文件日期
     */
    public static final String APPSYSMONTHDAY;
    
    /**
     * 按月生成全量同步文件开始时间
     */
    public static final  String APPSYSMONTHSTARTTIME;
    
    
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("experiential");
        
        STARTTIME = module.getItemValue("ExperStartTime").trim();

        //LOCALDIR = ServerInfo.getAppRootPath() + File.separator + "ftpdata"
          //         + File.separator + "searchfile" + File.separator;
       
        LOCALDIR = module.getItemValue("localDir").trim();

        
        mailTo = module.getItemValue("mailTo").trim().split(",");
        
        FTPIP = module.getItemValue("FTPIP").trim();
        
        FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
        
        FTPNAME = module.getItemValue("FTPName").trim();
        
        FTPPAS = module.getItemValue("FTPPassWord").trim();
        
        FTPPAHT = module.getItemValue("FTPPath").trim();
        ExperEncoding = module.getItemValue("ExperEncoding").trim();
        categoryName = module.getItemValue("categoryName").trim();
        categoryLineSep = module.getItemValue("categoryLineSep").trim();
        categoryColumnSep = module.getItemValue("categoryColumnSep").trim();
        deviceName = module.getItemValue("deviceName").trim();
        deviceLineSep = module.getItemValue("deviceLineSep").trim();
        deviceColumnSep = module.getItemValue("deviceColumnSep").trim();
        FullDeviceName = module.getItemValue("FullDeviceName").trim();
        
        APPFullName = module.getItemValue("APPFullName").trim();
        APPNewName = module.getItemValue("APPNewName").trim();
        APPNewColumnSep = module.getItemValue("APPNewColumnSep").trim();
        
        APPUpdateName = module.getItemValue("APPUpdateName").trim();
        APPUpdateColumnSep = module.getItemValue("APPUpdateColumnSep").trim();
        APPName = module.getItemValue("APPName").trim();
        APPLineSep = module.getItemValue("APPLineSep").trim();
        APPColumnSep = module.getItemValue("APPColumnSep").trim();
        
        APPSYSMONTHDAY = module.getItemValue("APPSYSMONTHDAY").trim();
        APPSYSMONTHSTARTTIME = module.getItemValue("APPSYSMONTHSTARTTIME").trim();
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the LOCALDIR is " + LOCALDIR);
      
            logger.debug("the mailTo is " + module.getItemValue("mailTo"));
        }
    }


}
