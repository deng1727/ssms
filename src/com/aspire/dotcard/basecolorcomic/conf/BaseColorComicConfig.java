/*
 * 文件名：BaseVideoConf.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.dotcard.basecolorcomic.conf;

import java.io.File;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version 
 */
public class BaseColorComicConfig
{
    private static final JLogger logger = LoggerFactory.getLogger(BaseColorComicConfig.class);
    
    /**
     * 货架
     */
    public static final String FILE_TYPE_CATEGORY="1";
    
    /**
     * 内容
     */
    public static final String FILE_TYPE_CONTENT="2";
    
    /**
     * 商品
     */
    public static final String FILE_TYPE_REFERENCE="3";
    
    /**
     * 推荐
     */
    public static final String FILE_TYPE_RECOMMEND="4";
    
    /**
     * 推荐关联
     */
    public static final String FILE_TYPE_RECOMMEND_LINK="5";
    
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
     * 
     */
    public  static final String verDataSpacers;
    
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
    
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("BaseColorComicConfig");
        
        STARTTIME = module.getItemValue("startTime").trim();

        LOCALDIR = ServerInfo.getAppRootPath() + File.separator + "ftpdata"
                   + File.separator + "baseData" + File.separator;
        
        mailTo = module.getItemValue("mailTo").trim().split(",");
        
        FTPIP = module.getItemValue("FTPIP").trim();
        
        FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
        
        FTPNAME = module.getItemValue("FTPName").trim();
        
        FTPPAS = module.getItemValue("FTPPassWord").trim();
        
        FTPPAHT = module.getItemValue("FTPPath").trim();
        
        GET_DATE_NUM = Integer.parseInt(module.getItemValue("getDateNum").trim());
        
        verDataSpacers =  module.getItemValue("verDataSpacers").trim();
        
        taskRunnerNum = Integer.parseInt(module.getItemValue("taskRunnerNum").trim());
        
        taskMaxReceivedNum = Integer.parseInt(module.getItemValue("taskMaxReceivedNum").trim());
        
        if (logger.isDebugEnabled())
        {
            logger.debug("the STARTTIME is " + STARTTIME);
            logger.debug("the LOCALDIR is " + LOCALDIR);
            logger.debug("the mailTo is " + module.getItemValue("mailTo"));
        }
    }


}
