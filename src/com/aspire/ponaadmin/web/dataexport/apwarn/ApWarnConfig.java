package com.aspire.ponaadmin.web.dataexport.apwarn;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ApWarnConfig {
	private static final JLogger logger = LoggerFactory
			.getLogger(ApWarnConfig.class);

	/**
	 * 体验营销系统数据导出 定时任务触发时间。
	 */
	public static final String STARTTIME;

	/**
	 * 发送邮件地址
	 */
	public static final String mailTo[];

	public static final int freeWarnMaxDayOrder;
	
	public static final int freeWarnDayOrderLessYDayOrder;
	
	public static final int freeWarnDayOrderLess7DayOrder;
	
	public static final int freeWarnDayOrderYDayOrderPercent;
	
	public static final int freeWarnDayOrder7DayOrderPercent;

	public static final int payWarnMaxDayOrder;
	
	public static final int payWarnDayOrderLessYDayOrder;
	
	public static final int payWarnDayOrderLess7DayOrder;
	
	public static final int payWarnDayOrderYDayOrderPercent;
	
	public static final int payWarnDayOrder7DayOrderPercent;
	
	public static final String localAttachFile;
	
	static {

		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"AP_WARN");

		STARTTIME = module.getItemValue("StartTime").trim();
		mailTo = module.getItemValue("mailTo").trim().split(",");
		
		// FTPIP = module.getItemValue("FTPIP").trim();
		// FTPPORT = Integer.parseInt(module.getItemValue("FTPPort").trim());
		// FTPNAME = module.getItemValue("FTPName").trim();
		// FTPPAS = module.getItemValue("FTPPassWord").trim();
		// FTPPAHT = module.getItemValue("FTPPath").trim();

		freeWarnMaxDayOrder = Integer.parseInt(module.getItemValue("freeWarnMaxDayOrder").trim());
		freeWarnDayOrderLessYDayOrder = Integer.parseInt(module.getItemValue("freeWarnDayOrderLessYDayOrder").trim());
		freeWarnDayOrderLess7DayOrder = Integer.parseInt(module.getItemValue("freeWarnDayOrderLess7DayOrder").trim());
		freeWarnDayOrderYDayOrderPercent = Integer.parseInt(module.getItemValue("freeWarnDayOrderYDayOrderPercent").trim());
		freeWarnDayOrder7DayOrderPercent = Integer.parseInt(module.getItemValue("freeWarnDayOrder7DayOrderPercent").trim());
		payWarnMaxDayOrder = Integer.parseInt(module.getItemValue("payWarnMaxDayOrder").trim());
		payWarnDayOrderLessYDayOrder = Integer.parseInt(module.getItemValue("payWarnDayOrderLessYDayOrder").trim());
		payWarnDayOrderLess7DayOrder = Integer.parseInt(module.getItemValue("payWarnDayOrderLess7DayOrder").trim());
		payWarnDayOrderYDayOrderPercent = Integer.parseInt(module.getItemValue("payWarnDayOrderYDayOrderPercent").trim());
		payWarnDayOrder7DayOrderPercent = Integer.parseInt(module.getItemValue("payWarnDayOrder7DayOrderPercent").trim());
		localAttachFile = module.getItemValue("localAttachFile").trim();
		if (logger.isDebugEnabled()) {
			logger.debug("the mailTo is " + module.getItemValue("mailTo"));
		}
	}
}
