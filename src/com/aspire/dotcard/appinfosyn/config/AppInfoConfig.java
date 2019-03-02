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
     * ѹ���ļ����Ŀ¼
     */
	 public static final String ftpFileDirectory;
 
	 //public static final String APPPICTMAGEPATH;

	 public static final String RESERVERPATH;
	 /**
	* ����߳���
	 */
		public static int AppInfoTaskNum;
		/**
		 * �����ڶ��е��������
		 */
		public static int AppInfoTaskMaxReceivedNum;
 
    /**
     * ��FTP�������ļ�����
     */
    public static final int EXCEPTION_FTP=1;
    public static final String[] mailTo;
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
     * ����ʼʱ�䡣
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
