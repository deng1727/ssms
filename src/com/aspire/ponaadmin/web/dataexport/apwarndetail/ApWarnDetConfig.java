/*
 * �ļ�����ApWarnDetConfig.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��Ʒ�������ش������ֵ
     */
    public static int MAX_DOWNLOAD_DAY;

    /**
     * ������ͬ�ֻ��������ش���Ʒ�������ֵ
     */
    public static int MAX_DOWNLOAD_USER;

    /**
     * �������ش���Ʒ�����ܺ� > ����Ʒ�������ش��������ٷֱ�
     */
    public static int MAX_USER_DOWNLOAD_QUOTIETY;

    /**
     * �ֻ��������������
     */
    public static int MAX_SERIES_USER;

    /**
     * �����û��ص������ֵ
     */
    public static int MAX_USER_ITERANCE_QUOTIETY;

    /**
     * ����������������
     */
    public static String MAX_DOWNLOAD_CITY;

    /**
     * ��ѯ���ݿ������������ļ�������
     */
    public static int MAX_DOWNLOAD_CITY_NUM;

    /**
     * ��������ط�ֵʱ��
     */
    public static String MAX_DOWNLOAD_TIME;

    /**
     * ��ʼ��������
     * 
     * @throws Exception
     */
    public static void loadConfig() throws BOException
    {
        logger.info("ˢ�����Ԥ����ʼ��ȡ������");

        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("AP_WARN_DET");
        if (module == null)
        {
            logger.error("system-config.xml�ļ����Ҳ���AP_WARN_DETģ��");
            System.out.println("system-config.xml�ļ����Ҳ���AP_WARN_DETģ��,�Զ������޷���ȷ����");
            throw new BOException("system-config.xml�ļ����Ҳ���AP_WARN_DETģ��");
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
