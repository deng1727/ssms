
package com.aspire.ponaadmin.web.datafield;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * 扩展属性处理配置项
 * 
 * @author biran
 * 
 */
public class KeyBaseConfig
{

    /**
     * 日志对象。
     */
    private static final JLogger LOG = LoggerFactory.getLogger(KeyBaseConfig.class);

    /**
     * 扩展属性处理配置项
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
