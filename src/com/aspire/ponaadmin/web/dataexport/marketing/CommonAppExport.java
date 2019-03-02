
package com.aspire.ponaadmin.web.dataexport.marketing;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.searchfile.SearchFileConfig;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.ExportedCategory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * Ӧ����Ϣͬ��
 * 
 * @author zhangwei
 * 
 */
public abstract class CommonAppExport extends ExportedCategory
{

    private static final JLogger LOG = LoggerFactory.getLogger(CommonAppExport.class);

    public static final String FileWriteDIR = ServerInfo.getAppRootPath()
                                              + File.separator + "ftpdata"
                                              + File.separator + "marketing"
                                              + File.separator + "BSB"
                                              + File.separator;

    /**
     * �������
     */
    protected Object importParams[];

    /**
     * ��������
     */
    protected String constraint;

    protected String exportedFileName;

    protected String fileName;

    /**
     * �������������󣬿���ֱ���ȶ�ȡ���ڴ���
     * 
     * @return
     */
    protected abstract List getDBData() throws BOException;

    protected void writeToFile(List list) throws BOException
    {

        // File file =new File(this.exportedFileName);
        // FileOutputStream out;
        try
        {
            // String backupFileName=this.exportedFileName+"bak";
            DataExportTools.ExportDate(list, this.exportedFileName);
            LOG.info("export file=" + this.exportedFileName);

            copyFileToFTP();
            LOG.info("д��FTP" + this.fileName);
        }
        catch (Exception e)
        {
            throw new BOException("д���ļ�����filename=" + this.exportedFileName, e);
        }
    }

    /**
     * д�ļ���FTPָ��Ŀ¼��
     * 
     * @throws BOException
     * 
     */
    private void copyFileToFTP() throws BOException
    {

        FTPClient ftp = null;

        try
        {
            // ȡ��Զ��Ŀ¼���ļ��б�
            ftp = getFTPClient();

            if (!"".equals(SearchFileConfig.FTPPAHT))
            {
                ftp.chdir(SearchFileConfig.FTPPAHT);
            }

            ftp.put(this.exportedFileName, this.fileName);

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_FTP);
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

        String ip = SearchFileConfig.FTPIP;
        int port = SearchFileConfig.FTPPORT;
        String user = SearchFileConfig.FTPNAME;
        String password = SearchFileConfig.FTPPAS;

        FTPClient ftp = new FTPClient(ip, port);
        
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);

        // ʹ�ø������û����������½ftp
        ftp.login(user, password);
        
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);

        return ftp;
    }

}
