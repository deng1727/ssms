package com.aspire.dotcard.wpinfo.config;

import java.io.File;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class AppInfoConfig {

	private static final JLogger logger = LoggerFactory.getLogger(AppInfoConfig.class);

    
    
    
    /**
     * wp»õ¼ÜÍ¼Æ¬ftpÄ¿Â¼
     */
    public static final String AppInfoCategoryPicFTPDir;
    

   
    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("BaseAppInfoConfig");
  

        
        AppInfoCategoryPicFTPDir = module.getItemValue("AppInfoCategoryPicFTPDir").trim();
        
     
    }
}
