package com.aspire.dotcard.syncAndroid.cssp.free;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.cssp.CSSPProducerRunnable;
import com.aspire.dotcard.syncAndroid.cssp.FTPUtil;
import com.aspire.dotcard.syncAndroid.cssp.MyFilenameFilter;

/**
 * notify.log文件处理
 */
public class FreeReportProducerRunnable extends CSSPProducerRunnable
{
    private static JLogger LOG = LoggerFactory.getLogger(FreeReportProducerRunnable.class);

    public FreeReportProducerRunnable(String ftpPath, String localPath, String filePrefix)
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

    public String[] getLocalName()
    {
        if (StringUtils.isEmpty(filePrefix))
        {
            String[] result = {};
            return result;
        }
        String day = filePrefix.substring(filePrefix.lastIndexOf(".") + 1).replace("-", "");
        File filePath = new File(localPath + "/backup/" + day);
        if (!filePath.exists())
        {
            filePath.mkdirs();
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("本地文件路径:" + filePath.getPath());
        }
        return filePath.list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("local file:" + name);
                }
                if (name.indexOf(filePrefix) != -1)
                {
                    return true;
                }
                return false;
            }

        });
    }

    public String[] getRemoteFiles()
    {
        return ftpUtil.getFTPFileNames(new MyFilenameFilter()
        {
            @Override
            public boolean accept(String name)
            {
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("ftp file:" + name);
                }
                if (name.indexOf(filePrefix) != -1)
                {
                    return true;
                }
                return false;
            }
        });
    }
}
