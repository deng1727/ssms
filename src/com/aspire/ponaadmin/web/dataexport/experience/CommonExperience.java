/*
 * 文件名：CommonExperience.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述： 体验营销抽象类 
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
 * <p>Title: 体验营销抽象类</p>
 * <p>Description: 体验营销抽象类</p>
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

        try
        {
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
    protected void copyFileToFTP() throws BOException
    {

        FTPClient ftp = null;

        try
        {
            // 取得远程目录中文件列表
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
        
        // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
        ftp.setConnectMode(FTPConnectMode.PASV);

        // 使用给定的用户名、密码登陆ftp
        ftp.login(user, password);
        
        // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
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
