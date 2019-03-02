
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
     * ������Ʒ���� ��ʱ���񴥷�ʱ�䡣
     */
    public static final String STARTTIME;

    /**
     *  ftp�������д洢excel��Ŀ¼.���������ļ������·������Ŀ¼�ָ��β��
     */
    public static final String LOCALDIR; 

    /**
     * ���ڣ�ÿ��/ÿ�ܡ�
     */
    public static final String frequency;

    /**
     * �����ʼ���ַ
     */
    public static final String mailTo[];
    
    /**
     * mo�ļ������ַ
     */
    public static final String MOPATH;
    
    /**
     * www�ļ������ַ
     */
    public static final String WWWPATH;
    /**
     * wap�ļ������ַ
     */
    public static final String WAPATH;
    /**
     * ר���ļ������ַ
     */
    public static final String SUBJECT;
    
    /**
     * ��Ƶ��Ŀ�ļ������ַ
     */
    public static final String VIDEOPATH;
    
    /**
     * ��ҵ�����ļ������ַ
     */
    public static final String BUSINESS;
    
    /**
     * �Ļ�ͬ��FTP��ַ
     */
    public static final String FTPIP;
    
    /**
     * �Ļ�ͬ��FTP�˿�
     */
    public static final int FTPPORT;
    
    /**
     * �Ļ�ͬ��FTP��¼�û���
     */
    public static final String FTPNAME;
    
    /**
     * �Ļ�ͬ��FTP��¼����
     */
    public static final String FTPPAS;
    
    /**
     * �Ļ�ͬ���ļ����·��
     */
    public static final String FTPPAHT;
    
    /**
     * �Ļ�ͬ����������ʱ��
     */
    public static final String APPTOP_STARTTIME;
    
    /**
     * ���ڵ���2010��ҵ�����������ڵĻ���id
     */
    public static final String CY_DATA_CATEGORYID;
    
    /**
     * �ͻ����ص�Ӧ�ñ�ǩ
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
        LOCALDIR =  module.getItemValue("LocalDir").trim();//sftp 20120727 �Ķ���
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
