/*
 * �ļ�����CommonExperience.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������ ����Ӫ�������� 
 */
package com.aspire.ponaadmin.web.dataexport.experience;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.ExportedCategory;
import com.aspire.ponaadmin.web.dataexport.marketing.CommonAppExport;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;



/**
 * <p>Title: ����Ӫ��������</p>
 * <p>Description: ����Ӫ��������</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public abstract class CommonExperience extends ExportedCategory
{

    private static final JLogger LOG = LoggerFactory.getLogger(CommonAppExport.class);

    public static final String FileWriteDIR = ExperienceConfig.LOCALDIR;
    	
//    	ServerInfo.getAppRootPath()
//                                              + File.separator + "ftpdata"
//                                              + File.separator + "marketing"
//                                              + File.separator + "EXT"
//                                              + File.separator;

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

        try
        {
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
    protected void copyFileToFTP() throws BOException
    {

        FTPClient ftp = null;

        try
        {
            // ȡ��Զ��Ŀ¼���ļ��б�
            ftp = getFTPClient();

            if (!"".equals(ExperienceConfig.FTPPAHT))
            {
                ftp.chdir(ExperienceConfig.FTPPAHT);
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

        String ip = ExperienceConfig.FTPIP;
        int port = ExperienceConfig.FTPPORT;
        String user = ExperienceConfig.FTPNAME;
        String password = ExperienceConfig.FTPPAS;

        FTPClient ftp = new FTPClient(ip, port);
        
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);

        // ʹ�ø������û����������½ftp
        ftp.login(user, password);
        
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);

        return ftp;
    }

	/**
	 * @return Returns the constraint.
	 */
	public String getConstraint()
	{
		return constraint;
	}

	/**
	 * @param constraint The constraint to set.
	 */
	public void setConstraint(String constraint)
	{
		this.constraint = constraint;
	}
    
    
}
