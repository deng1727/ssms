/*
 * �ļ�����BaseVideoConf.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
	 * �м�������׺
	 */
	public static final String midDefSuffix = "_MID";
	
	/**
	 * ��Ƶȫ����ʱ�������׺
	 */
	public static final String fullDefSuffix = "_FULL";
	
	
	/**
	 * ִ��shell�ű������ɵ�add�ļ�
	 */
	public static final String ADD_SHELL_FILE = "new_i_v-videodetail";
	
	/**
	 * ִ��shell�ű������ɵ�del�ļ�
	 */
	public static final String DEL_SHELL_FILE = "del_i_v-videodetail";
	
	/**
	 * ִ��shell�ű������ɵ�del�ļ�
	 */
	public static final String EXEC_SHELL_PATH ;
	
	/**
	 * ִ��shell�ű������ɵ�del�ļ�
	 */
	public static final String EXEC_SHELL_FILE = "videochenaged.sh";
	/**
     * ��Ƶ��Сʱ����
     */
    public static final String FILE_TYPE_VIDEO_ADD_HOUR="12";
    /**
     * ��Ŀ���鰴Сʱ����
     */
    public static final String FILE_TYPE_PROGRAM_ADD_HOUR="13";
    /**
     * �ȵ������Ƽ���Сʱ����
     */
    public static final String FILE_TYPE_RECOMMEND_ADD_HOUR="14";
    /**
     * ��Ʒ���۹�ϵ��Ϣ
     */
    public static final String FILE_TYPE_COST="222";
    /**
     * ֪ͨMOXPAS�Ż���UPL��
     */
    public static final String xpasUrlPortalConfig;
    
    /**
     * ��Сʱ����ͬ��������Ƶ�ļ����ݿ�ʼʱ���������
     */
    public static final String STARTTIME_MINUTES;
    
    /**
     * ��Ƶȫ��
     */
    public static final String FILE_TYPE_VIDEO="1";
    
    /**
     * ��Ƶ����
     */
    public static final String FILE_TYPE_VIDEO_ADD="11";
    
    /**
     * ����
     */
    public static final String FILE_TYPE_DEVICE="2";
    
    /**
     * ����
     */
    public static final String FILE_TYPE_CODERATE="3";
    
    /**
     * ��Ŀ������
     */
    public static final String FILE_TYPE_VIDEO_DETAIL="4";
    
    /**
     * ��Ŀ
     */
    public static final String FILE_TYPE_NODE="5";
    
    /**
     * ֱ����Ŀ��
     */
    public static final String FILE_TYPE_LIVE="6";
    
    /**
     * ���а�
     */
    public static final String FILE_TYPE_RANK="7";
    
    /**
     * ��Ʒ
     */
    public static final String FILE_TYPE_PRODUCT="8";

    /**
     * ��Ƶ��Ŀͳ��
     */
    public static final String FILE_TYPE_VIDEODETAIL="9";
    
    /**
     * ��Ƶ���ݼ��ڵ�
     */
    public static final String FILE_TYPE_COLLECT_NODE="10";
    /**
     * ��Ƶ���ݼ�
     */
    public static final String FILE_TYPE_COLLECT="15";
    
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
	 * ��Ƶ����ȫ���ļ�����shell�ű��ļ�
	 */
	public static final String EXEC_SHELL_VIDEO_FULL_IMPORT_FILE = "videofullimport.sh";
	
    
    
    
    
    
    
    
    
    
    
    
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
     * �洢�����ļ��Ĵ��·��
     */
    public static final String FTPPAHT;
    
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
     * 
     */
    public  static final String verDataSpacers;
    
    /**
     * Ĭ��ͼƬ·��
     */
    public static final String logoPath;
    
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
     * ����Ŀ�����IP����
     */
    public static final String ipListConfig;
    
    /**
     * ֪ͨ�Ż���UPL����
     */
    public static final String urlPortalConfig;
    
    /**
     * ɾ��ʧ��ʱ��������֪ͨ���ֻ��Ŷ���
     */
    public static final String phoneList;
    

    /**
     * ִ��wap�洢����
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
    
    // ���ݵı�����׺
    public static final String bakSuffix;
    //���ı�����׺
    public static final String defSuffix;
    
    //��Ҫ�޸ĵ����ͬ������
    public static final String renameTables;
    
    /**
     * Ϊ����ȷ����ÿ�ܼ�ִ��ȫ�����������
     */
    public static final int sysDayByWeek;
    
    /**
     * ȡȫ��������Ƶ�����ļ������ڣ�0:������,1:����һ,2:����һ,3:������,4:������,5:������,6:������
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
