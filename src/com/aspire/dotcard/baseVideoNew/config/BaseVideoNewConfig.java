/*
 * 文件名：BaseVideoConf.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.dotcard.baseVideoNew.config;

import java.io.File;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ServerInfo;
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
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class BaseVideoNewConfig
{
	private static final JLogger logger = LoggerFactory.getLogger(BaseVideoNewConfig.class);
    
	/**
	 * 中间表表名后缀
	 */
	public static final String midDefSuffix = "_MID";
	
	/**
	 * 视频全量临时表表名后缀
	 */
	public static final String fullDefSuffix = "_FULL";
	
	
	/**
	 * 执行shell脚本后生成的add文件
	 */
	public static final String ADD_SHELL_FILE = "new_i_v-videodetail";
	
	/**
	 * 执行shell脚本后生成的del文件
	 */
	public static final String DEL_SHELL_FILE = "del_i_v-videodetail";
	
	/**
	 * 执行shell脚本后生成的del文件
	 */
	public static final String EXEC_SHELL_PATH ;
	
	/**
	 * 执行shell脚本后生成的del文件
	 */
	public static final String EXEC_SHELL_FILE = "videochenaged.sh";
	/**
     * 视频按小时增量
     */
    public static final String FILE_TYPE_VIDEO_ADD_HOUR="12";
    /**
     * 节目详情按小时增量
     */
    public static final String FILE_TYPE_PROGRAM_ADD_HOUR="13";
    /**
     * 热点内容推荐按小时增量
     */
    public static final String FILE_TYPE_RECOMMEND_ADD_HOUR="14";
    /**
     * 产品打折关系信息
     */
    public static final String FILE_TYPE_COST="222";
    /**
     * 通知MOXPAS门户的UPL。
     */
    public static final String xpasUrlPortalConfig;
    
    /**
     * 按小时增量同步基地视频文件数据开始时间分钟数。
     */
    public static final String STARTTIME_MINUTES;
    
    /**
     * 视频全量
     */
    public static final String FILE_TYPE_VIDEO="1";
    
    /**
     * 视频增量
     */
    public static final String FILE_TYPE_VIDEO_ADD="11";
    
    /**
     * 机型
     */
    public static final String FILE_TYPE_DEVICE="2";
    
    /**
     * 码率
     */
    public static final String FILE_TYPE_CODERATE="3";
    
    /**
     * 节目单详情
     */
    public static final String FILE_TYPE_VIDEO_DETAIL="4";
    
    /**
     * 栏目
     */
    public static final String FILE_TYPE_NODE="5";
    
    /**
     * 直播节目单
     */
    public static final String FILE_TYPE_LIVE="6";
    
    /**
     * 排行榜
     */
    public static final String FILE_TYPE_RANK="7";
    
    /**
     * 产品
     */
    public static final String FILE_TYPE_PRODUCT="8";

    /**
     * 视频节目统计
     */
    public static final String FILE_TYPE_VIDEODETAIL="9";
    
    /**
     * 视频内容集节点
     */
    public static final String FILE_TYPE_COLLECT_NODE="10";
    /**
     * 视频内容集
     */
    public static final String FILE_TYPE_COLLECT="15";
    
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
	 * 视频物理全量文件导入shell脚本文件
	 */
	public static final String EXEC_SHELL_VIDEO_FULL_IMPORT_FILE = "videofullimport.sh";
	
    
    
    
    
    
    
    
    
    
    
    
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
     * 存储数据文件的存放路径
     */
    public static final String FTPPAHT;
    
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
     * 
     */
    public  static final String verDataSpacers;
    
    /**
     * 默认图片路径
     */
    public static final String logoPath;
    
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
     * 请求的可信任IP队列
     */
    public static final String ipListConfig;
    
    /**
     * 通知门户的UPL队列
     */
    public static final String urlPortalConfig;
    
    /**
     * 删除失败时用来短信通知的手机号队列
     */
    public static final String phoneList;
    

    /**
     * 执行wap存储过程
     */
    public static final String wapProcedureName;
    
    
    public static final String FromFTPIP;
    
    public static final int FromFTPPort;
    
    public static final String FromFTPUser;
    
    public static final String FromFTPPassword;
    
    public static final String FromNodeFTPDir;
    
    public static final String FromProgramFTPDir;
    
    public static final String nodelogoTemplocalDir;
    
    public static final String prologoTemplocalDir;
    
    public static final String ToFTPIP;
    
    public static final int ToFTPPort;
    
    public static final String ToFTPUser;
    
    public static final String ToFTPPassword;
    
    public static final String ToNodeFTPDir;
    
    public static final String ToProgramFTPDir;
    
    public static final String NodeLogoPath;
    
    public static final String ProgramLogoPath;
    
    // 备份的表名后缀
    public static final String bakSuffix;
    //入库的表名后缀
    public static final String defSuffix;
    
    //需要修改的入库同步表名
    public static final String renameTables;
    
    /**
     * 为基地确定的每周几执行全量导入的周数
     */
    public static final int sysDayByWeek;
    
    /**
     * 取全量基地视频物理文件的周期，0:星期日,1:星期一,2:星期一,3:星期三,4:星期四,5:星期五,6:星期六
     */
    public static final int GET_WEEK_NUM;
    
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("BaseVideoFileConfig");
  
        EXEC_SHELL_PATH = module.getItemValue("SHELLPATH").trim();
        STARTTIME = module.getItemValue("startTime").trim();
        STARTTIME_MINUTES=module.getItemValue("startMinutes").trim();

        LOCALDIR = ServerInfo.getAppRootPath() + File.separator + "ftpdata"
                   + File.separator + "baseData" + File.separator;
        
        mailTo = module.getItemValue("mailTo").trim().split(",");
        
        FTPIP = module.getItemValue("FTPIP").trim();
        
        FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
        
        FTPNAME = module.getItemValue("FTPName").trim();
        
        FTPPAS = module.getItemValue("FTPPassWord").trim();
        
        FTPPAHT = module.getItemValue("FTPPath").trim();
        
        GET_DATE_NUM = Integer.parseInt(module.getItemValue("getDateNum").trim());
        
        GET_TIME_NUM =  Integer.parseInt(module.getItemValue("getTimeNum").trim());
        
        verDataSpacers =  module.getItemValue("verDataSpacers").trim();
        
        logoPath = module.getItemValue("logoPath").trim();
        
        taskRunnerNum = Integer.parseInt(module.getItemValue("taskRunnerNum").trim());
        
        taskMaxReceivedNum = Integer.parseInt(module.getItemValue("taskMaxReceivedNum").trim());
        
        ipListConfig = module.getItemValue("ipListConfig").trim();
        
        urlPortalConfig = module.getItemValue("urlPortalConfig").trim();
        xpasUrlPortalConfig=module.getItemValue("xpasUrlPortalConfig").trim();
        
        phoneList = module.getItemValue("phoneList").trim();
        
        wapProcedureName = module.getItemValue("wapProcedureName").trim();
        
        
        FromFTPIP = module.getItemValue("FromFTPIP").trim();
        FromFTPPort = Integer.parseInt(module.getItemValue("FromFTPPort").trim());
        FromFTPUser = module.getItemValue("FromFTPUser").trim();
        FromFTPPassword = module.getItemValue("FromFTPPassword").trim();
        FromNodeFTPDir = module.getItemValue("FromNodeFTPDir").trim();
        FromProgramFTPDir = module.getItemValue("FromProgramFTPDir").trim();
        nodelogoTemplocalDir = module.getItemValue("nodelogoTemplocalDir").trim();
        prologoTemplocalDir = module.getItemValue("prologoTemplocalDir").trim();
        ToFTPIP = module.getItemValue("ToFTPIP").trim();
        ToFTPPort = Integer.parseInt(module.getItemValue("ToFTPPort").trim());
        ToFTPUser = module.getItemValue("ToFTPUser").trim();
        ToFTPPassword = module.getItemValue("ToFTPPassword").trim();
        ToNodeFTPDir = module.getItemValue("ToNodeFTPDir").trim();
        ToProgramFTPDir = module.getItemValue("ToProgramFTPDir").trim();
        NodeLogoPath = module.getItemValue("NodeLogoPath").trim();
        ProgramLogoPath = module.getItemValue("ProgramLogoPath").trim();
        
        bakSuffix = module.getItemValue("bakSuffix").trim();
        defSuffix = module.getItemValue("defSuffix").trim();
        renameTables = module.getItemValue("renameTables").trim();
        sysDayByWeek = Integer.parseInt(module.getItemValue("sysDayByWeek").trim());
        GET_WEEK_NUM = Integer.parseInt(module.getItemValue("getWeekNum").trim());
        
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the LOCALDIR is " + LOCALDIR);
            logger.debug("the mailTo is " + module.getItemValue("mailTo"));
        }
    }
}
