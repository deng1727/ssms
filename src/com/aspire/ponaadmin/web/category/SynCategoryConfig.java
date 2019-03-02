package com.aspire.ponaadmin.web.category;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class SynCategoryConfig
{
	/**
	 * 记录日志的实例对象
	 */
	private static JLogger LOG = LoggerFactory.getLogger(SynCategoryConfig.class);
    
	/**
	 * 分类同步添加精品库任务触发时间。
	 */
	public static String STARTTIME;
    
    /**
	 * 指定WAP精品库的货架id
	 */
	public static String ACATEGORYID;
    
	/**
	 * 指定WWW精品库的货架id
	 */
	public static String WCATEGORYID;
    
	/**
	 * 指定MO精品库的货架id
	 */
	public static String OCATEGORYID;
	
	public static void loadConfig()throws Exception
	{
		LOG.info("分类同步添加精品库开始读取配置项");
		
		// 初始化配置项。
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"synCategory");
		if (module == null)
		{
			LOG.error("system-config.xml文件中找不到synCategory模块");
			System.out.println("system-config.xml文件中找不到synCategory模块,自动更新无法正确进行");
			throw new BOException("system-config.xml文件中找不到synCategory模块");
		}
		STARTTIME = module.getItemValue("startTime");
		LOG.info("startTime="+STARTTIME);
        ACATEGORYID = module.getItemValue("aCategoryId");
		LOG.info("指定WAP精品库的货架id="+ACATEGORYID);
        WCATEGORYID = module.getItemValue("wCategoryId");
		LOG.info("指定WWW精品库的货架id="+WCATEGORYID);
        OCATEGORYID = module.getItemValue("oCategoryId");
		LOG.info("指定MO精品库的货架id="+OCATEGORYID);	
	}

}
