package com.aspire.ponaadmin.web.category;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryRuleConfig
{
	/**
	 * 记录日志的实例对象
	 */
	private static JLogger LOG = LoggerFactory.getLogger(CategoryRuleConfig.class);
	/**
	 * 货架定时更新任务触发时间。
	 */
	public static String STARTTIME;
    /**
	 * 规则对应条件的默认获取最大值。
	 */
	public static int CONDITIONMAXVALUE;
	/**
	 * 根据sp信息进行商品调整的最大商品数
	 */
	public static int SPNAMESORTCOUNT;
	/**
	 * 允许同一sp的最大商品数
	 */
	public static int SPNAMEMAXCOUNT;
	/**
	 * 执行规则的最大线程数
	 */
	public static int RuleRunningTaskNum;
	
	public static void loadConfig()throws Exception
	{

		
		LOG.info("货架自动更新开始读取配置项");
		
		// 初始化配置项。
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"categoryAutoUpdate");
		if (module == null)
		{
			LOG.error("system-config.xml文件中找不到categoryAutoUpdate模块");
			System.out.println("system-config.xml文件中找不到categoryAutoUpdate模块,自动更新无法正确进行");
			throw new BOException("system-config.xml文件中找不到categoryAutoUpdate模块");

		}
		STARTTIME = module.getItemValue("startTime");
		LOG.info("startTime="+STARTTIME);
		CONDITIONMAXVALUE = Integer.parseInt(module.getItemValue("ConditionMaxValue"));
		LOG.info("ConditionMaxValue="+CONDITIONMAXVALUE);
		SPNAMESORTCOUNT = Integer.parseInt(module.getItemValue("spnameSortCount"));
		LOG.info("spnameSortCount="+SPNAMESORTCOUNT);
		SPNAMEMAXCOUNT = Integer.parseInt(module.getItemValue("spnameMaxCount"));
		LOG.info("spnameMaxCount="+SPNAMEMAXCOUNT);	
		RuleRunningTaskNum=Integer.parseInt(module.getItemValue("RuleRunningTaskNum"));
		LOG.info("RuleRunningTaskNum="+RuleRunningTaskNum);
	}

}
