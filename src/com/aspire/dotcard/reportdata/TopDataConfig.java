
package com.aspire.dotcard.reportdata;

import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.common.config.ServerInfo;

public class TopDataConfig
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(TopDataConfig.class);

    private static final String PATH_SEP = System.getProperty("file.separator");

    /**
     * ��ȡ���а���ص�������ֵ
     * 
     * @param key String
     * @return String
     */
    public static String get(String key)
    {

        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("topdataimport");
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

    /**
     * ��ȡ���а������ļ��ڱ��صı���·��
     * 
     * @return String
     */
    public static String getTopDataFilePath(String pasPath,int type)
    {
    	StringBuffer dir = new StringBuffer();
    	
        String path = ServerInfo.getAppRootPath();
        path = PublicUtil.replace(path, "/", PATH_SEP);
        if (!path.endsWith(PATH_SEP))
        {
            path = path + PATH_SEP;
        }
        if (!pasPath.endsWith(PATH_SEP))
        {
        	pasPath = pasPath + PATH_SEP;
        }
        dir.append(path);
        dir.append(pasPath);
        switch(type)
    	{
    		case TopDataConstants.CONTENT_TYPE_DAY:
    			dir.append("content");
    			dir.append(PATH_SEP);
    			dir.append("day");
    			break;
    		case TopDataConstants.CONTENT_TYPE_WEEK:
    			dir.append("content");
    			dir.append(PATH_SEP);
    			dir.append("week");
    			break;
    		case TopDataConstants.CONTENT_TYPE_MONTH:
    			dir.append("content");
    			dir.append(PATH_SEP);
    			dir.append("month");
    			break;
    	}

        return dir.toString();
    }

}
