
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
 * 应用信息同步
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
     * 输入参数
     */
    protected Object importParams[];

    /**
     * 限制条件
     */
    protected String constraint;

    protected String exportedFileName;

    protected String fileName;

    /**
     * 由于数据量不大，可以直接先读取到内存中
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
            LOG.info("写入FTP" + this.fileName);
        }
        catch (Exception e)
        {
            throw new BOException("写入文件出错，filename=" + this.exportedFileName, e);
        }
    }

    /**
     * 写文件至FTP指定目录中
     * 
     * @throws BOException
     * 
     */
    private void copyFileToFTP() throws BOException
    {

        FTPClient ftp = null;

        try
        {
            // 取得远程目录中文件列表
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
        
        // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
        ftp.setConnectMode(FTPConnectMode.PASV);

        // 使用给定的用户名、密码登陆ftp
        ftp.login(user, password);
        
        // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
        ftp.setType(FTPTransferType.BINARY);

        return ftp;
    }

}
