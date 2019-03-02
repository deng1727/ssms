/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience ExperienceConfig.java
 * Jul 7, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.channel;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke 第三方渠道营销数据导出配置项
 */
public class ChannelConfig {

	private static final JLogger logger = LoggerFactory
			.getLogger(ChannelConfig.class);

	/**
	 * 体验营销系统数据导出 定时任务触发时间。
	 */
	public static final String STARTTIME;

	/**
	 * ftp服务器中存储文件的目录.本地数据文件保存的路径。以目录分割复结尾。
	 */
	public static final String LOCALDIR;

	/**
	 * 发送邮件地址
	 */
	public static final String mailTo[];



	/**
	 * 体验营销系统数据导出文件格式
	 */
	public static final String ExperEncoding;
	
	public static final String exportFreeFile;
	
	public static final String exportPayFile;
	
	public static final String exportAllFile;
	
	public static final String exportTbFile;
	
	public static final String wwwZXCategoryId;
	
	public static final String wwwZRCategoryId;
	
	public static final String wwwXBTJCategoryId;
	
	public static final String wapZXCategoryId;
	
	public static final String wapZRCategoryId;
	
	public static final String wapXBTJCategoryId;

	public static final String selfIcpCode;
	static {

		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"thirdChannel");

		STARTTIME = module.getItemValue("ChannelStartTime").trim();
		LOCALDIR = module.getItemValue("localDir").trim();
		mailTo = module.getItemValue("mailTo").trim().split(",");
//		FTPIP = module.getItemValue("FTPIP").trim();
//		FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
//		FTPNAME = module.getItemValue("FTPName").trim();
//		FTPPAS = module.getItemValue("FTPPassWord").trim();
//		FTPPAHT = module.getItemValue("FTPPath").trim();
		ExperEncoding = module.getItemValue("ChannelEncoding").trim();
		
		exportFreeFile = module.getItemValue("exportFreeFile").trim();
		exportPayFile = module.getItemValue("exportPayFile").trim();
		exportAllFile = module.getItemValue("exportAllFile").trim();
		wwwZXCategoryId = module.getItemValue("wwwZXCategoryId").trim();
		wwwZRCategoryId = module.getItemValue("wwwZRCategoryId").trim();
		wwwXBTJCategoryId = module.getItemValue("wwwXBTJCategoryId").trim();
		wapZXCategoryId = module.getItemValue("wapZXCategoryId").trim();
		wapZRCategoryId = module.getItemValue("wapZRCategoryId").trim();
		wapXBTJCategoryId = module.getItemValue("wapXBTJCategoryId").trim();
		exportTbFile = module.getItemValue("exportTbFile").trim();
		selfIcpCode = module.getItemValue("selfIcpCode").trim();
		if (logger.isDebugEnabled()) {
			// logger.debug("the STARTTIME is " + STARTTIME);
			// logger.debug("the LOCALDIR is " + LOCALDIR);

			logger.debug("the mailTo is " + module.getItemValue("mailTo"));
		}
	}

}
