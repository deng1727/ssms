
package com.aspire.dotcard.reportdata.cystatistic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class ListFtpProcessor
{

    private static JLogger logger = LoggerFactory.getLogger(ListFtpProcessor.class);



    /**
     * 数据文件在ftp的存放父目录
     */
    protected String ftpDirPath;

    /**
     * 保存本地数据文件的绝对父目录
     */
    protected String localDirPath;

    /**
     * 以下是ftp登录的配置信息
     */
    private String ip;

    private int port;

    private String user;

    private String password;

    public void init()
    {

        this.ip = TopListConfig.get("FTPServerIP");
        this.port = Integer.parseInt(TopListConfig.get("FTPServerPort"));
        this.user = TopListConfig.get("FTPServerUser");
        this.password = TopListConfig.get("FTPServerPassword");
        this.ftpDirPath = TopListConfig.get("ftpDirPath");
        this.localDirPath = TopListConfig.get("localDirPath");
    }

    public  List process(String fileDirName,String currentFile) throws BOException
    {

        this.init();
        FTPClient ftp = null;
        try
        {
            //本地的绝对目录
            String localDir=PublicUtil.getEndWithSlash(this.localDirPath) + fileDirName;
            // 先确保本地目录已经创建了。
            IOUtil.checkAndCreateDir(localDir);

            // 存放获取到的文件列表的list
            ArrayList fileList = new ArrayList(); 

            // 取得远程目录中文件列表
            ftp = getFTPClient();
            String ftpDir=PublicUtil.getEndWithSlash(this.ftpDirPath) + fileDirName;
            if (logger.isDebugEnabled())
            {
                logger.debug("ftpDir：" + ftpDir+"  localDir：" + localDir);
            }
            if (!"".equals(ftpDir))
            {
                ftp.chdir(ftpDir);
            }
            //獲取ftp文件列表
            String[] Remotefiles = ftp.dir();
            

            if (logger.isDebugEnabled())
            {
                logger.debug("匹配当前要获取的文件名：" + currentFile);
            }
            for (int i = 0; i < Remotefiles.length; i++)
            {
                String RemotefileName = Remotefiles[i];
                if (RemotefileName.indexOf(currentFile) != -1&&RemotefileName.indexOf(".txt") != -1)
                {
                    //获取当天时间及后缀为txt文件
                    String absolutePath = localDir + File.separator
                                          + RemotefileName;
                    absolutePath = absolutePath.replace('\\', '/');
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("获取ftp的文件开始：" + absolutePath+" Remotefiles["+i+"]:"+Remotefiles[i]);
                    }
                    ftp.get(absolutePath, Remotefiles[i]);
                    fileList.add(absolutePath);
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("成功下载文件：" + absolutePath);
                    }
                }
            }
            return fileList;
        }
        catch (Exception e)
        {
            throw new BOException("ListFtp获取文件错误",e);
        }
        finally
        {
            if (ftp != null)
            {
                try
                {
                    ftp.quit();
                }
                catch (Exception e)
                {
                }
            }

        }
    }

    private FTPClient getFTPClient() throws IOException, FTPException
    {

        FTPClient ftp = new FTPClient(ip, port);
        // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
        ftp.setConnectMode(FTPConnectMode.PASV);

        // 使用给定的用户名、密码登陆ftp
        if (logger.isDebugEnabled())
        {
            logger.debug("login to FTPServer...");
        }
        ftp.login(user, password);
        // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
        ftp.setType(FTPTransferType.BINARY);
        if (logger.isDebugEnabled())
        {
            logger.debug("login FTPServer successfully,transfer type is binary");
        }
        return ftp;
    }

   
 
}
