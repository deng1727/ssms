/*
 * �ļ�����BaseVideoConf.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ����
     */
    public static final String FILE_TYPE_CATEGORY="1";
    
    /**
     * ����
     */
    public static final String FILE_TYPE_CONTENT="2";
    
    /**
     * ��Ʒ
     */
    public static final String FILE_TYPE_REFERENCE="3";
    
    /**
     * �Ƽ�
     */
    public static final String FILE_TYPE_RECOMMEND="4";
    
    /**
     * �Ƽ�����
     */
    public static final String FILE_TYPE_RECOMMEND_LINK="5";
    
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
     * 
     */
    public  static final String verDataSpacers;
    
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
