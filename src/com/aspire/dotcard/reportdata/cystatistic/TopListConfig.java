
package com.aspire.dotcard.reportdata.cystatistic;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * ����������ļ���������������� Ӧ��top�񵥡���������񵥡���ҵ������Ʒ
 * 
 * @author biran
 * 
 */
public class TopListConfig
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(TopListConfig.class);

    public static int getCyUpdateTaskNum()
    {

        return Integer.parseInt(get("cyUpdateTaskNum"));
    }

    public static int getCyMaxReceivedNum()
    {

        return Integer.parseInt(get("cyMaxReceivedNum"));
    }

    public static int getFileDay()
    {

        return Integer.parseInt(get("fileDay"));
    }

    /**
     * ����������ļ����������������ֵ
     * 
     * @param key String
     * @return String
     */
    public static String get(String key)
    {

        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("topListConfig");

        if (module == null)
        {
            return null;
        }
        String value = module.getItemValue(key);
        if (LOG.isDebugEnabled())
        {
            LOG.debug("config value for [" + key + "] is [" + value + "].");
        }
        return value;
    }

}
