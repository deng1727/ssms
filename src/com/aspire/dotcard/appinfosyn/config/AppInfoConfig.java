package com.aspire.dotcard.appinfosyn.config;

import java.io.File;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class AppInfoConfig {

	private static final JLogger logger = LoggerFactory.getLogger(AppInfoConfig.class);


    /**
     * 压缩文件存放目录
     */
	 public static final String ftpFileDirectory;
 
	 //public static final String APPPICTMAGEPATH;

	 public static final String RESERVERPATH;
	 /**
	* 最大线程数
	 */
		public static int AppInfoTaskNum;
		/**
		 * 运行期队列的最大容量
		 */
		public static int AppInfoTaskMaxReceivedNum;
 
    /**
     * 于FTP处下载文件出错
     */
    public static final int EXCEPTION_FTP=1;
    public static final String[] mailTo;
    /**
     * 文件不存在
     */
    public static final int EXCEPTION_FILE_NOT_EXISTED=2;
    
    /**
     * 读取文件写入数据库时发生异常
     */
    public static final int EXCEPTION_INNER_ERR=3;
    
    /**
     * 校验文件成功结果
     */
    public static final String CHECK_DATA_SUCCESS = "success";
    
    /**
     * 检查不通过
     */
    public static final String CHECK_FAILED="failed";
    
    /**
     * 保存数据库操作成功结果
     */
    public static final String EXPORT_DATA_SUCCESS = "success"; 
    
    
    /**
     * 存储数据文件的FTP地址
     */
    public static final String FTPIP;
    
    /**
     * 存储数据文件的FTP端口
     */
    public static final int FTPPORT;
    
    /**
     * 存储数据文件的FTP登录用户名
     */
    public static final String FTPNAME;
    
    /**
     * 存储数据文件的FTP登录密码
     */
    public static final String FTPPAS;
   
  
    /**
     * 任务开始时间。
     */
    public static final String STARTTIME;

    /**
     *  ftp服务器中存储数据文件的目录.本地数据文件保存的路径。以目录分割复结尾。
     */
    public static final String LOCALDIR;
    
    /**
     * 取文件的日期，0：为当天，1：为前一天，2：为前二天
     */
    public static final int GET_DATE_NUM;


 
   
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("AppXmlFileConfig");
        
        STARTTIME = module.getItemValue("startTime").trim();
        GET_DATE_NUM = Integer.parseInt(module.getItemValue("getDateNum").trim());
        
        LOCALDIR = ServerInfo.getAppRootPath() + File.separator + "ftpdata"
                   + File.separator + "baseData" + File.separator + "appinfo";
        FTPIP = module.getItemValue("FTPIP").trim();

    	 AppInfoTaskNum=Integer.valueOf(module.getItemValue("AppInfoTaskNum").trim());

    	 AppInfoTaskMaxReceivedNum=Integer.valueOf(module.getItemValue("AppInfoTaskMaxReceivedNum").trim());
     
    	 mailTo=module.getItemValue("mailTo").trim().split(",");
        RESERVERPATH=module.getItemValue("RESERVERPATH").trim();
        //APPPICTMAGEPATH = module.getItemValue("AppPicImagePath").trim();
        
        FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
        
        FTPNAME = module.getItemValue("FTPName").trim();
        
        FTPPAS = module.getItemValue("FTPPassWord").trim();
        
        ftpFileDirectory = module.getItemValue("FTPPath").trim();
        
        

        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the LOCALDIR is " + LOCALDIR);
        }
    }
}
