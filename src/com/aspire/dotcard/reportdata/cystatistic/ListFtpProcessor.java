
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
     * �����ļ���ftp�Ĵ�Ÿ�Ŀ¼
     */
    protected String ftpDirPath;

    /**
     * ���汾�������ļ��ľ��Ը�Ŀ¼
     */
    protected String localDirPath;

    /**
     * ������ftp��¼��������Ϣ
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
            //���صľ���Ŀ¼
            String localDir=PublicUtil.getEndWithSlash(this.localDirPath) + fileDirName;
            // ��ȷ������Ŀ¼�Ѿ������ˡ�
            IOUtil.checkAndCreateDir(localDir);

            // ��Ż�ȡ�����ļ��б��list
            ArrayList fileList = new ArrayList(); 

            // ȡ��Զ��Ŀ¼���ļ��б�
            ftp = getFTPClient();
            String ftpDir=PublicUtil.getEndWithSlash(this.ftpDirPath) + fileDirName;
            if (logger.isDebugEnabled())
            {
                logger.debug("ftpDir��" + ftpDir+"  localDir��" + localDir);
            }
            if (!"".equals(ftpDir))
            {
                ftp.chdir(ftpDir);
            }
            //�@ȡftp�ļ��б�
            String[] Remotefiles = ftp.dir();
            

            if (logger.isDebugEnabled())
            {
                logger.debug("ƥ�䵱ǰҪ��ȡ���ļ�����" + currentFile);
            }
            for (int i = 0; i < Remotefiles.length; i++)
            {
                String RemotefileName = Remotefiles[i];
                if (RemotefileName.indexOf(currentFile) != -1&&RemotefileName.indexOf(".txt") != -1)
                {
                    //��ȡ����ʱ�估��׺Ϊtxt�ļ�
                    String absolutePath = localDir + File.separator
                                          + RemotefileName;
                    absolutePath = absolutePath.replace('\\', '/');
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ȡftp���ļ���ʼ��" + absolutePath+" Remotefiles["+i+"]:"+Remotefiles[i]);
                    }
                    ftp.get(absolutePath, Remotefiles[i]);
                    fileList.add(absolutePath);
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("�ɹ������ļ���" + absolutePath);
                    }
                }
            }
            return fileList;
        }
        catch (Exception e)
        {
            throw new BOException("ListFtp��ȡ�ļ�����",e);
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
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);

        // ʹ�ø������û����������½ftp
        if (logger.isDebugEnabled())
        {
            logger.debug("login to FTPServer...");
        }
        ftp.login(user, password);
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);
        if (logger.isDebugEnabled())
        {
            logger.debug("login FTPServer successfully,transfer type is binary");
        }
        return ftp;
    }

   
 
}
