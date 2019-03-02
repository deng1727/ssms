/*
 * 文件名：ApWarnDetConfig.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.dataexport.apwarndetail;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class ApWarnDetConfig
{

    private static final JLogger logger = LoggerFactory.getLogger(ApWarnDetConfig.class);

    /**
     * 商品当日下载次数最大值
     */
    public static int MAX_DOWNLOAD_DAY;

    /**
     * 当日相同手机号码下载此商品次数最大值
     */
    public static int MAX_DOWNLOAD_USER;

    /**
     * 当日下载此商品次数总和 > 此商品当日下载次数的最大百分比
     */
    public static int MAX_USER_DOWNLOAD_QUOTIETY;

    /**
     * 手机允许最大连号数
     */
    public static int MAX_SERIES_USER;

    /**
     * 下载用户重叠率最大值
     */
    public static int MAX_USER_ITERANCE_QUOTIETY;

    /**
     * 允许下载量最大城市
     */
    public static String MAX_DOWNLOAD_CITY;

    /**
     * 查询数据库中下载量最大的几个城市
     */
    public static int MAX_DOWNLOAD_CITY_NUM;

    /**
     * 允许的下载峰值时段
     */
    public static String MAX_DOWNLOAD_TIME;

    /**
     * 初始化配置项
     * 
     * @throws Exception
     */
    public static void loadConfig() throws BOException
    {
        logger.info("刷榜二期预警开始读取配置项");

        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("AP_WARN_DET");
        if (module == null)
        {
            logger.error("system-config.xml文件中找不到AP_WARN_DET模块");
            System.out.println("system-config.xml文件中找不到AP_WARN_DET模块,自动更新无法正确进行");
            throw new BOException("system-config.xml文件中找不到AP_WARN_DET模块");
        }

        MAX_DOWNLOAD_DAY = Integer.parseInt(module.getItemValue("MAX_DOWNLOAD_DAY")
                                                  .trim());
        logger.info("MAX_DOWNLOAD_DAY = " + MAX_DOWNLOAD_DAY);

        MAX_DOWNLOAD_USER = Integer.parseInt(module.getItemValue("MAX_DOWNLOAD_USER")
                                                   .trim());
        logger.info("MAX_DOWNLOAD_USER = " + MAX_DOWNLOAD_USER);

        MAX_USER_DOWNLOAD_QUOTIETY = Integer.parseInt(module.getItemValue("MAX_USER_DOWNLOAD_QUOTIETY")
                                                            .trim());
        logger.info("MAX_USER_DOWNLOAD_QUOTIETY = "
                    + MAX_USER_DOWNLOAD_QUOTIETY);

        MAX_SERIES_USER = Integer.parseInt(module.getItemValue("MAX_SERIES_USER")
                                                 .trim());
        logger.info("MAX_SERIES_USER = " + MAX_SERIES_USER);

        MAX_USER_ITERANCE_QUOTIETY = Integer.parseInt(module.getItemValue("MAX_USER_ITERANCE_QUOTIETY")
                                                            .trim());
        logger.info("MAX_USER_ITERANCE_QUOTIETY = "
                    + MAX_USER_ITERANCE_QUOTIETY);

        MAX_DOWNLOAD_CITY = module.getItemValue("MAX_DOWNLOAD_CITY").trim();
        logger.info("MAX_DOWNLOAD_CITY = " + MAX_DOWNLOAD_CITY);

        MAX_DOWNLOAD_CITY_NUM = Integer.parseInt(module.getItemValue("MAX_DOWNLOAD_CITY_NUM")
                                                       .trim());
        logger.info("MAX_DOWNLOAD_CITY_NUM = " + MAX_DOWNLOAD_CITY_NUM);

        MAX_DOWNLOAD_TIME = module.getItemValue("MAX_DOWNLOAD_TIME").trim();
        logger.info("MAX_DOWNLOAD_TIME = " + MAX_DOWNLOAD_TIME);
    }
}
