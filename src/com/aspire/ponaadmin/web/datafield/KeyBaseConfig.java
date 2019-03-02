
package com.aspire.ponaadmin.web.datafield;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * ��չ���Դ���������
 * 
 * @author biran
 * 
 */
public class KeyBaseConfig
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(KeyBaseConfig.class);

    /**
     * ��չ���Դ���������
     * 
     * @param key String
     * @return String
     */
    public static String get(String key)
    {

        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("keyBaseConfig");

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
