package com.aspire.ponaadmin.web.dataexport.entitycard;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;

public class EntityCardConfig {
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
    
    public static final String lineSep;
    
    public static final String columnSep;
    
    public static final String APExportFile;
    
    public static final String APOperExportFile;
 
    
	static {

		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"entitycard");

		STARTTIME = module.getItemValue("EntitycardStartTime").trim();
		LOCALDIR = module.getItemValue("localDir").trim();
		mailTo = module.getItemValue("mailTo").trim().split(",");
		FTPIP = module.getItemValue("FTPIP").trim();
		FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
		FTPNAME = module.getItemValue("FTPName").trim();
		FTPPAS = module.getItemValue("FTPPassWord").trim();
		FTPPAHT = module.getItemValue("FTPPath").trim();
		ExperEncoding = module.getItemValue("EntitycardEncoding").trim();
		lineSep = module.getItemValue("EntitycardLineSep").trim();
		columnSep = module.getItemValue("EntitycardColumnSep").trim();
		APExportFile = module.getItemValue("APExportFile").trim();
		APOperExportFile = module.getItemValue("APOperExportFile").trim();

	}    
}
