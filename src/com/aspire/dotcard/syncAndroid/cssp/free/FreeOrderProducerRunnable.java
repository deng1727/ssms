package com.aspire.dotcard.syncAndroid.cssp.free;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.dotcard.syncAndroid.cssp.CSSPProducerRunnable;
import com.aspire.dotcard.syncAndroid.cssp.FTPUtil;

/**
 * notify.log文件处理
 */
public class FreeOrderProducerRunnable extends CSSPProducerRunnable
{
  
    public FreeOrderProducerRunnable(String ftpPath, String localPath, String filePrefix)
    {
        super(ftpPath, localPath, filePrefix);
        
        ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
        String ftpDir = ftpPath;// module.getItemValue("FTPDir")+"/"+changeDir;
        String localDir = localPath;// module.getItemValue("localDir")+"/"+changeDir;
        String ip = module.getItemValue("freeFTPServerIP");
        int port = Integer.parseInt(module.getItemValue("freeFTPServerPort"));
        String user = module.getItemValue("freeFTPServerUser");
        String password = module.getItemValue("freeFTPServerPassword");
        ftpUtil = new FTPUtil(ftpDir, localDir, ip, port, user, password);

    }

}
