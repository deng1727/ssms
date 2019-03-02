package com.aspire.ponaadmin.web.dataexport.basefile.impl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.dataexport.basefile.BaseFileAbstract;
import com.aspire.ponaadmin.web.dataexport.basefile.task.BaseFileConstants;
import com.aspire.ponaadmin.web.dataexport.basefile.task.LogBaseFileConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

public class CategoryBaseFile extends BaseFileAbstract
{
    public CategoryBaseFile()
    {
        // select * from t_r_category t where t.delflag=0 and t.state=1
        this.sql = "dataexport.basefile.CategoryBaseFile.getDBData";

        this.fileName = ServerInfo.getAppRootPath() + File.separator
                        + "ftpdata" + File.separator + "Log" + File.separator
                        + "ssms_category_"
                        + PublicUtil.getCurDateTime("yyyyMMdd") + "_001.log";

        this.checkFileName = ServerInfo.getAppRootPath() + File.separator
                             + "ftpdata" + File.separator + "Log"
                             + File.separator + "ssms_category_"
                             + PublicUtil.getCurDateTime("yyyyMMdd")
                             + ".999999";

        this.fileType = BaseFileConstants.FILE_TYPE_TXT;

        this.compart = getCompart();

        this.isUpload = true;
        
        this.isCreateCheckFile = true;
        
        this.isChangeEnter = true;
        
        this.enterString = getEnterString();

        this.toFileName = "ssms_category_"
                          + PublicUtil.getCurDateTime("yyyyMMdd") + "_001.log";

        this.toCheckFileName = "ssms_category_"
                               + PublicUtil.getCurDateTime("yyyyMMdd")
                               + ".999999";
    }
    
    /**
     * 用于返回0d0a换行回车符
     * @return
     */
    private String getEnterString()
    {
        int a = 13;
        int b = 10;
        char x = (char)a;
        char y = (char)b;
        
        return String.valueOf(x)+String.valueOf(y);
    }
    
    /**
     * 返回指定分隔符0X1F
     * @return
     */
    private String getCompart()
    {
        int  a = 0x1f;
        char r = (char)a;
        return String.valueOf(r);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.dataexport.basefile.BaseFileAbstract#fromObject(java.sql.ResultSet)
     */
    protected Object[] fromObject(ResultSet rs) throws SQLException
    {
        String[] obj = new String[20];
        
        obj[0] = rs.getString("ID");
        obj[1] = rs.getString("NAME");
        obj[2] = this.checkFileldEnter(rs.getString("DESCS"));
        obj[3] = String.valueOf(rs.getInt("SORTID"));
        obj[4] = String.valueOf(rs.getInt("CTYPE"));
        obj[5] = rs.getString("CATEGORYID");
        obj[6] = String.valueOf(rs.getInt("DELFLAG"));
        obj[7] = rs.getString("CHANGEDATE");
        obj[8] = String.valueOf(rs.getInt("STATE"));
        obj[9] = rs.getString("PARENTCATEGORYID");
        obj[10] = rs.getString("RELATION");
        obj[11] = rs.getString("PICURL");
        obj[12] = String.valueOf(rs.getInt("STATISTIC"));
        obj[13] = String.valueOf(rs.getInt("DEVICECATEGORY"));
        obj[14] = rs.getString("PLATFORM");
        obj[15] = rs.getString("CITYID");
        obj[16] = rs.getString("STARTDATE");
        obj[17] = rs.getString("ENDDATE");
        obj[18] = rs.getString("MULTIURL");
        obj[19] = rs.getString("OTHERNET");
  
        Object [] robj = new Object[2];
        robj[0] = rs.getString("ID");//去重的ID
        robj[1] = obj;
        return robj;
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
            ftp = this.getFTPClient();

            if (!"".equals(LogBaseFileConfig.FTPPAHT))
            {
                ftp.chdir(LogBaseFileConfig.FTPPAHT);
            }

            // 把本地全路径文件上传至指定文件名
            ftp.put(this.fileName, toFileName);
            
            if(this.isCreateCheckFile)
            {
                ftp.put(this.checkFileName, toCheckFileName);    
            }

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
}
