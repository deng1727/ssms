package com.aspire.dotcard.basevideosync.config;

import java.io.File;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class BaseVideoConfig {

	private static final JLogger logger = LoggerFactory.getLogger(BaseVideoConfig.class);

	/**
	 * 视频节目全量文件类型
	 */
	public static final String FILE_TYPE_PROGRAM="1";
	/**
     * 视频节目按小时增量文件类型
     */
    public static final String FILE_TYPE_PROGRAM_ADD_HOUR="2";

    /**
     * 视频业务产品全量文件类型
     */
    public static final String FILE_TYPE_PRD_PKG="3";
    /**
     * 视频产品包促销计费数据全量文件类型
     */
    public static final String FILE_TYPE_PKG_SALES="4";
    /**
     * 计费信息全量文件类型
     */
    public static final String FILE_TYPE_PRODUCT="5";
    /**
     * 热点主题列表全量文件类型
     */
    public static final String FILE_TYPE_HOTCONTENT="6";
    /**
     * 节目详情文件XML类型
     */
    public static final String FILE_TYPE_PROGRAM_XML="7";
    /**
     * 直播节目单文件XML类型
     */
    public static final String FILE_TYPE_LIVE_XML="8";
    /**
     * 热点主题文件XML类型
     */
    public static final String FILE_TYPE_HOTCONTENT_XML="9";
    
    /**
     * 视频榜单发布文件类型
     */
    public static final String FILE_TYPE_TOPLIST="10";
    /**
     * 产品包促销计费api请求数据
     */
    public static final String FILE_TYPE_PRD_API="11";
    /**
     * 热点主题列表api请求数据
     */
    public static final String FILE_TYPE_HOTCONTENT_API = "12";
    /**
     * 业务产品和产品促销计费api请求
     */
    public static final String FILE_TYPE_PKG_SALES_API = "13";
    /**
     * 计费信息数据Api请求
     */
    public static final String FILE_TYPE_PRODUCT_API = "14";
    
    /**
     * 普通节目数据Api请求
     */
    public static final String FILE_TYPE_PROGRAM_API = "15";
 
    /**
     * 按小时增量同步基地视频文件数据开始时间分钟数。
     */
    public static final String STARTTIME_MINUTES;
    
    /**
     * 按小时增量同步基地视频文件数据开始时间小时数。
     */
    public static final String STARTTIME_HOURS;
    
    /**
     * 校验文件最后一行的标示
     */
    public static final String FILE_END = "999999";
    
    /**
     * 于FTP处下载文件出错
     */
    public static final int EXCEPTION_FTP=1;
    
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
     * 存储数据文件的存放根路径
     */
    public static final String FTPPATH;
    
    /**
     * 同步基地视频文件数据开始时间。
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
    
    /**
     * 取文件的时间，0：为当小时，1：为前小时，2：为前两小时
     */
    public static final int GET_TIME_NUM;

    /**
     * 校验文件字段间隔符
     */
    public  static final String verDataSpacers;
    
    /**
     * 节目内容图片路径
     */
    public static final String ProgramContentImagePath;
    
    /**
     * 视频货架图片ftp目录
     */
    public static final String VideoCategoryPicFTPDir;
    
    /**
     * 线程执行最大条数
     */
    public static final int taskRunnerNum;
    
    /**
     * 任务队列最大容量。0表示不限制容量
     */
    public static final int taskMaxReceivedNum;
    
    /**
     * 发送邮件地址
     */
    public static final String mailTo[];
    /**
     * 内容获取接口的地址
     */
    public static final String baseUrl;
    /**
     * 调用系统用户名
     */
    public static final String userId;
    /**
     * api请求同步时间
     */
    public static final String APISTARTTIME;
    
    public static final int APIDURATION;
   
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("BaseVideoConfig");
  
        STARTTIME = module.getItemValue("startTime").trim();
        STARTTIME_MINUTES=module.getItemValue("startMinutes").trim();
        GET_DATE_NUM = Integer.parseInt(module.getItemValue("getDateNum").trim());
        GET_TIME_NUM =  Integer.parseInt(module.getItemValue("getTimeNum").trim());
        STARTTIME_HOURS = module.getItemValue("syncDataTimeIntervalByTime").trim();
        
        LOCALDIR = ServerInfo.getAppRootPath() + File.separator + "ftpdata"
                   + File.separator + "baseData" + File.separator + "newVideo";
        
        mailTo = module.getItemValue("mailTo").trim().split(",");
        
        FTPIP = module.getItemValue("FTPIP").trim();
        
        FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
        
        FTPNAME = module.getItemValue("FTPName").trim();
        
        FTPPAS = module.getItemValue("FTPPassWord").trim();
        
        FTPPATH = module.getItemValue("FTPPath").trim();
   
        verDataSpacers =  module.getItemValue("verDataSpacers").trim();
        
        ProgramContentImagePath = module.getItemValue("ProgramContentImagePath").trim();
        
        VideoCategoryPicFTPDir = module.getItemValue("VideoCategoryPicFTPDir").trim();
        
        taskRunnerNum = Integer.parseInt(module.getItemValue("taskRunnerNum").trim());
        
        taskMaxReceivedNum = Integer.parseInt(module.getItemValue("taskMaxReceivedNum").trim());
        
        baseUrl = module.getItemValue("baseUrl").trim();
        
        userId = module.getItemValue("userId").trim();
        
        APISTARTTIME = module.getItemValue("apiStartTime").trim();
        
        APIDURATION = Integer.parseInt(module.getItemValue("apiDuration").trim());

        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the LOCALDIR is " + LOCALDIR);
            logger.debug("the mailTo is " + module.getItemValue("mailTo"));
        }
    }
}
