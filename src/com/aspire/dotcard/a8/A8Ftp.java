
package com.aspire.dotcard.a8;

import java.io.IOException;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class A8Ftp
{

    private static final JLogger logger = LoggerFactory.getLogger(A8Ftp.class);
    
    
    /**
     * 获取FTPClient 的实例,使用完毕后请使用quit()方法关闭
     * @return
     * @throws Exception
     */
    public  FTPClient getFtpClient() throws IOException,FTPException
    {

        FTPClient ftp = new FTPClient(A8ParameterConfig.IP, A8ParameterConfig.PORT);
        // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
        ftp.setConnectMode(FTPConnectMode.PASV);

        // 使用给定的用户名、密码登陆ftp
        if (logger.isDebugEnabled())
        {
            logger.debug("login to FTPServer...");
        }
        ftp.login(A8ParameterConfig.USER,A8ParameterConfig.PWD );
        // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
        ftp.setType(FTPTransferType.BINARY);
        if(logger.isDebugEnabled())
        {
            logger.debug("login FTPServer successfully");
        }
       
        return ftp;

    }
    

}
