
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
     * ��ȡFTPClient ��ʵ��,ʹ����Ϻ���ʹ��quit()�����ر�
     * @return
     * @throws Exception
     */
    public  FTPClient getFtpClient() throws IOException,FTPException
    {

        FTPClient ftp = new FTPClient(A8ParameterConfig.IP, A8ParameterConfig.PORT);
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);

        // ʹ�ø������û����������½ftp
        if (logger.isDebugEnabled())
        {
            logger.debug("login to FTPServer...");
        }
        ftp.login(A8ParameterConfig.USER,A8ParameterConfig.PWD );
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);
        if(logger.isDebugEnabled())
        {
            logger.debug("login FTPServer successfully");
        }
       
        return ftp;

    }
    

}
