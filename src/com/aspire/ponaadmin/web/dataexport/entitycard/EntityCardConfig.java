package com.aspire.ponaadmin.web.dataexport.entitycard;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;

public class EntityCardConfig {
    /**
     * 体验营销系统数据导出 定时任务触发时间。
     */
    public static final String STARTTIME;

    /**
     *  ftp服务器中存储文件的目录.本地数据文件保存的路径。以目录分割复结尾。
     */
    public static final String LOCALDIR; 

    
    /**
     * 发送邮件地址
     */
    public static final String mailTo[];
    
   
    
    /**
     * 体验营销系统数据导出 FTP地址
     */
    public static final String FTPIP;
    
    /**
     * 体验营销系统数据导出 FTP端口
     */
    public static final int FTPPORT;
    
    /**
     * 体验营销系统数据导出FTP登录用户名
     */
    public static final String FTPNAME;
    
    /**
     * 体验营销系统数据导出FTP登录密码
     */
    public static final String FTPPAS;
    
    /**
     * 体验营销系统数据导出文件存放路径
     */
    public static final String FTPPAHT;
    
    /**
     * 体验营销系统数据导出文件格式
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
