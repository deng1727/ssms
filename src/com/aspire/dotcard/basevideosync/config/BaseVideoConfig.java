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
	 * ��Ƶ��Ŀȫ���ļ�����
	 */
	public static final String FILE_TYPE_PROGRAM="1";
	/**
     * ��Ƶ��Ŀ��Сʱ�����ļ�����
     */
    public static final String FILE_TYPE_PROGRAM_ADD_HOUR="2";

    /**
     * ��Ƶҵ���Ʒȫ���ļ�����
     */
    public static final String FILE_TYPE_PRD_PKG="3";
    /**
     * ��Ƶ��Ʒ�������Ʒ�����ȫ���ļ�����
     */
    public static final String FILE_TYPE_PKG_SALES="4";
    /**
     * �Ʒ���Ϣȫ���ļ�����
     */
    public static final String FILE_TYPE_PRODUCT="5";
    /**
     * �ȵ������б�ȫ���ļ�����
     */
    public static final String FILE_TYPE_HOTCONTENT="6";
    /**
     * ��Ŀ�����ļ�XML����
     */
    public static final String FILE_TYPE_PROGRAM_XML="7";
    /**
     * ֱ����Ŀ���ļ�XML����
     */
    public static final String FILE_TYPE_LIVE_XML="8";
    /**
     * �ȵ������ļ�XML����
     */
    public static final String FILE_TYPE_HOTCONTENT_XML="9";
    
    /**
     * ��Ƶ�񵥷����ļ�����
     */
    public static final String FILE_TYPE_TOPLIST="10";
    /**
     * ��Ʒ�������Ʒ�api��������
     */
    public static final String FILE_TYPE_PRD_API="11";
    /**
     * �ȵ������б�api��������
     */
    public static final String FILE_TYPE_HOTCONTENT_API = "12";
    /**
     * ҵ���Ʒ�Ͳ�Ʒ�����Ʒ�api����
     */
    public static final String FILE_TYPE_PKG_SALES_API = "13";
    /**
     * �Ʒ���Ϣ����Api����
     */
    public static final String FILE_TYPE_PRODUCT_API = "14";
    
    /**
     * ��ͨ��Ŀ����Api����
     */
    public static final String FILE_TYPE_PROGRAM_API = "15";
 
    /**
     * ��Сʱ����ͬ��������Ƶ�ļ����ݿ�ʼʱ���������
     */
    public static final String STARTTIME_MINUTES;
    
    /**
     * ��Сʱ����ͬ��������Ƶ�ļ����ݿ�ʼʱ��Сʱ����
     */
    public static final String STARTTIME_HOURS;
    
    /**
     * У���ļ����һ�еı�ʾ
     */
    public static final String FILE_END = "999999";
    
    /**
     * ��FTP�������ļ�����
     */
    public static final int EXCEPTION_FTP=1;
    
    /**
     * �ļ�������
     */
    public static final int EXCEPTION_FILE_NOT_EXISTED=2;
    
    /**
     * ��ȡ�ļ�д�����ݿ�ʱ�����쳣
     */
    public static final int EXCEPTION_INNER_ERR=3;
    
    /**
     * У���ļ��ɹ����
     */
    public static final String CHECK_DATA_SUCCESS = "success";
    
    /**
     * ��鲻ͨ��
     */
    public static final String CHECK_FAILED="failed";
    
    /**
     * �������ݿ�����ɹ����
     */
    public static final String EXPORT_DATA_SUCCESS = "success"; 
    
    
    
    /**
     * �洢�����ļ���FTP��ַ
     */
    public static final String FTPIP;
    
    /**
     * �洢�����ļ���FTP�˿�
     */
    public static final int FTPPORT;
    
    /**
     * �洢�����ļ���FTP��¼�û���
     */
    public static final String FTPNAME;
    
    /**
     * �洢�����ļ���FTP��¼����
     */
    public static final String FTPPAS;
    
    /**
     * �洢�����ļ��Ĵ�Ÿ�·��
     */
    public static final String FTPPATH;
    
    /**
     * ͬ��������Ƶ�ļ����ݿ�ʼʱ�䡣
     */
    public static final String STARTTIME;

    /**
     *  ftp�������д洢�����ļ���Ŀ¼.���������ļ������·������Ŀ¼�ָ��β��
     */
    public static final String LOCALDIR;
    
    /**
     * ȡ�ļ������ڣ�0��Ϊ���죬1��Ϊǰһ�죬2��Ϊǰ����
     */
    public static final int GET_DATE_NUM;
    
    /**
     * ȡ�ļ���ʱ�䣬0��Ϊ��Сʱ��1��ΪǰСʱ��2��Ϊǰ��Сʱ
     */
    public static final int GET_TIME_NUM;

    /**
     * У���ļ��ֶμ����
     */
    public  static final String verDataSpacers;
    
    /**
     * ��Ŀ����ͼƬ·��
     */
    public static final String ProgramContentImagePath;
    
    /**
     * ��Ƶ����ͼƬftpĿ¼
     */
    public static final String VideoCategoryPicFTPDir;
    
    /**
     * �߳�ִ���������
     */
    public static final int taskRunnerNum;
    
    /**
     * ����������������0��ʾ����������
     */
    public static final int taskMaxReceivedNum;
    
    /**
     * �����ʼ���ַ
     */
    public static final String mailTo[];
    /**
     * ���ݻ�ȡ�ӿڵĵ�ַ
     */
    public static final String baseUrl;
    /**
     * ����ϵͳ�û���
     */
    public static final String userId;
    /**
     * api����ͬ��ʱ��
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
