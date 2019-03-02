package com.aspire.ponaadmin.web.category;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryRuleByCarveOutConfig
{
	/**
	 * 记录日志的实例对象
	 */
	private static JLogger LOG = LoggerFactory.getLogger(CategoryRuleByCarveOutConfig.class);
    
	/**
	 * 创业大赛货架定时更新任务触发时间。
	 */
	public static String STARTTIME;
    
    /**
	 * 规则对应条件的默认获取最大值。
	 */
	public static int CONDITIONMAXVALUE;

	/**
	 * 执行规则的最大线程数
	 */
	public static int RuleRunningTaskNum;
	
	public static void loadConfig()throws Exception
	{
		LOG.info("创业大赛货架自动更新开始读取配置项");
		
		// 初始化配置项。
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"categoryAutoUpdateByCarveOut");
        
		if (module == null)
		{
			LOG.error("system-config.xml文件中找不到categoryAutoUpdateByCarveOut模块");
			System.out.println("system-config.xml文件中找不到categoryAutoUpdateByCarveOut模块,自动更新无法正确进行");
			throw new BOException("system-config.xml文件中找不到categoryAutoUpdateByCarveOut模块");

		}
		STARTTIME = module.getItemValue("startTime");
		LOG.info("startTime="+STARTTIME);
		CONDITIONMAXVALUE = Integer.parseInt(module.getItemValue("ConditionMaxValue"));
		LOG.info("ConditionMaxValue="+CONDITIONMAXVALUE);
		RuleRunningTaskNum=Integer.parseInt(module.getItemValue("RuleRunningTaskNum"));
		LOG.info("RuleRunningTaskNum="+RuleRunningTaskNum);
	}
}
