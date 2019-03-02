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
 *����Ӫ��ϵͳ���ݵ���������
 */
public class ExperienceConfig
{


    private static final JLogger logger = LoggerFactory.getLogger(ExperienceConfig.class);

    /**
     * ����Ӫ��ϵͳ���ݵ��� ��ʱ���񴥷�ʱ�䡣
     */
    public static final String STARTTIME;

    /**
     *  ftp�������д洢�ļ���Ŀ¼.���������ļ������·������Ŀ¼�ָ��β��
     */
    public static final String LOCALDIR; 

    
    /**
     * �����ʼ���ַ
     */
    public static final String mailTo[];
    
   
    
    /**
     * ����Ӫ��ϵͳ���ݵ��� FTP��ַ
     */
    public static final String FTPIP;
    
    /**
     * ����Ӫ��ϵͳ���ݵ��� FTP�˿�
     */
    public static final int FTPPORT;
    
    /**
     * ����Ӫ��ϵͳ���ݵ���FTP��¼�û���
     */
    public static final String FTPNAME;
    
    /**
     * ����Ӫ��ϵͳ���ݵ���FTP��¼����
     */
    public static final String FTPPAS;
    
    /**
     * ����Ӫ��ϵͳ���ݵ����ļ����·��
     */
    public static final String FTPPAHT;
    
    /**
     * ����Ӫ��ϵͳ���ݵ����ļ���ʽ
     */
    public static final String ExperEncoding;
    /**
     * Ӧ�÷�����Ϣͬ���ļ���
     */
    public static final String categoryName;
    /**
     * Ӧ�÷�����Ϣͬ���ļ��зָ���
     */
    public static final String categoryLineSep;
    /**
     * Ӧ�÷�����Ϣͬ���ļ��зָ���
     */
    public static final String categoryColumnSep;
    /**
     * ȫ��������Ϣͬ���ļ���
     */
    public static final String FullDeviceName;
    /**
     * ����������Ϣͬ���ļ���
     */
    public static final String deviceName;
    /**
     * ������Ϣͬ���ļ��зָ���
     */
    public static final String deviceLineSep;
    /**
     * ������Ϣͬ���ļ��зָ���
     */
    public static final String deviceColumnSep;
    /**
     * ȫ��Ӧ����Ϣͬ���ļ���
     */
    public static final String APPFullName;
    /**
     * ����Ӧ����Ϣͬ���ļ���
     */
    public static final String APPNewName;
    /**
     * ����Ӧ����Ϣͬ���ļ��зָ���
     */
    public static final String APPNewColumnSep;
    
    /**
     * Ӧ����Ϣ����ͬ���ļ���
     */
    public static final String APPUpdateName;
    /**
     * Ӧ����Ϣ����ͬ���ļ��зָ���
     */
    public static final String APPUpdateColumnSep;
   
    /**
     * ��Ӧ����Ϣͬ���ļ���
     */
    public static final String APPName;
    /**
     * ��Ӧ����Ϣͬ���ļ��зָ���
     */
    public static final String APPLineSep;
    /**
     * ��Ӧ����Ϣͬ���ļ��зָ���
     */
    public static final String APPColumnSep;
    
    /**
     * ��������ȫ��ͬ���ļ�����
     */
    public static final String APPSYSMONTHDAY;
    
    /**
     * ��������ȫ��ͬ���ļ���ʼʱ��
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
