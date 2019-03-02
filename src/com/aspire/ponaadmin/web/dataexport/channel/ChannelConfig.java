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
 * @author tungke ����������Ӫ�����ݵ���������
 */
public class ChannelConfig {

	private static final JLogger logger = LoggerFactory
			.getLogger(ChannelConfig.class);

	/**
	 * ����Ӫ��ϵͳ���ݵ��� ��ʱ���񴥷�ʱ�䡣
	 */
	public static final String STARTTIME;

	/**
	 * ftp�������д洢�ļ���Ŀ¼.���������ļ������·������Ŀ¼�ָ��β��
	 */
	public static final String LOCALDIR;

	/**
	 * �����ʼ���ַ
	 */
	public static final String mailTo[];



	/**
	 * ����Ӫ��ϵͳ���ݵ����ļ���ʽ
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
