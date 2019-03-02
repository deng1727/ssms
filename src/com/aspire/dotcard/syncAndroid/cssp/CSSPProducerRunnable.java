package com.aspire.dotcard.syncAndroid.cssp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CSSPProducerRunnable implements Runnable
{

    private static JLogger logger = LoggerFactory.getLogger(CSSPProducerRunnable.class);

    protected String localPath;
    protected String filePrefix;
    protected FTPUtil ftpUtil;

    public CSSPProducerRunnable(String ftpPath, String localPath, String filePrefix)
    {
        ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
        String ftpDir = ftpPath;// module.getItemValue("FTPDir")+"/"+changeDir;
        String localDir = localPath;// module.getItemValue("localDir")+"/"+changeDir;
        String ip = module.getItemValue("FTPServerIP");
        int port = Integer.parseInt(module.getItemValue("FTPServerPort"));
        String user = module.getItemValue("FTPServerUser");
        String password = module.getItemValue("FTPServerPassword");

        ftpUtil = new FTPUtil(ftpDir, localDir, ip, port, user, password);
        this.localPath = localDir;
        this.filePrefix = filePrefix;
    }

    public String[] getLocalName()
    {
        String day = filePrefix.substring(filePrefix.indexOf("_") + 1);
        File filePath = new File(localPath + "/backup/" + day);
        if (!filePath.exists())
        {
            filePath.mkdirs();
        }
        return filePath.list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
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
                if (name.indexOf(filePrefix) != -1)
                {
                    return true;
                }
                return false;
            }

        });

    }

    public String[] getNeedFiles()
    {
        String[] remoteFiles = getRemoteFiles();
        String[] localName = getLocalName();

        List<String> list = new LinkedList<String>();
        for (int i = 0; remoteFiles!=null && i < remoteFiles.length; i++)
        {
            boolean f = false;
            for (int j = 0; localName!=null && j < localName.length; j++)
            {
                if (remoteFiles[i].equals(localName[j]))
                {
                    f = true;
                    break;
                }
            }
            if (!f)
            {
                list.add(remoteFiles[i]);
            }
        }
        String[] result = {}; // 创建空数组
        return list.toArray(result); // List to Array

    }

    public void run()
    {
        try
        {
            // 获取一个FTP;
            if (!ftpUtil.getFTP())
            {
                return;
            }
            // TODO Auto-generated method stub
            String[] needFiles = getNeedFiles();
            ftpUtil.process(needFiles);
        }
        catch (Exception e)
        {
            logger.error("下载文件出错！", e);
        }
        finally
        {
            ftpUtil.closeFTP();
        }

    }

}
