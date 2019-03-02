/*
 * 文件名：BaseFileAbstract.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.dataexport.basefile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import au.com.bytecode.opencsv.CSVWriter;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.dataexport.basefile.task.BaseFileConfig;
import com.aspire.ponaadmin.web.dataexport.basefile.task.BaseFileConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.StringTool;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public abstract class BaseFileAbstract implements BaseFileObject
{
    /**
     * 用于存放数据集合
     */
    protected Map  hm = new HashMap();

    /**
     * 分隔符
     */
    protected String compart;

    /**
     * 生成文件名
     */
    protected String fileName;

    /**
     * 上传文件的文件名
     */
    protected String toFileName;
    
    /**
     * 生成的对账文件名
     */
    protected String checkFileName;
    
    /**
     * 上传文件的对账文件名
     */
    protected String toCheckFileName;

    /**
     * 用于得到数据要执行的sql语句
     */
    protected String sql;

    /**
     * 用于生成文件的类型
     */
    protected String fileType;

    /**
     * 是否上传至FTP
     */
    protected boolean isUpload = true;
    
    /**
     * 是否生成对账文件
     */
    protected boolean isCreateCheckFile = false;
    
    /**
     * 是否对字符中的回车换行符进行变更
     */
    protected boolean isChangeEnter = false;
    
    /**
     * 如果要变更，变更为何字符
     */
    protected String changeEnterToStr = " ";
    
    /**
     * 回车鬼换行符如何定义
     */
    protected String enterString;

    /**
     * 用于得到数据集合
     */
    protected void queryDBforList() throws DAOException
    {
        // 执行sql语句得到数据集。
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sql, null);

            while (rs.next())
            {
                // 放入数据集合
                Object[] va = fromObject(rs);
                hm.put(va[0], va[1]);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("查询基础数据集合时发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("解析单行数据时发生异常:", e);
        }
    }

    /**
     * 用于生成文件
     */
    public void createFile() throws BOException
    {
        // 得到数据集合。
        try
        {
            queryDBforList();
        }
        catch (DAOException e)
        {
            throw new BOException("查询基础数据集合时发生异常");
        }
        
        // 创建目录
        createFilePath();

        // 写入文件
        writerFile();
        
        // 用于子类可拓展功能：重命文件名
        updateFileName();
        
        // 是否生成对账文件
        if(isCreateCheckFile)
        {
            writerCheckFileFile();
        }

        // 是否上传至FTP
        if (isUpload)
        {
            // 上传文件至FTP
            copyFileToFTP();
        }
    }
    
    /**
     * 用于创建目录
     */
    private void createFilePath()
    {
        File file = new File(fileName);
        
        if(!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
    }

    /**
     * 用于把数据集合写入至文件中
     */
    private void writerFile() throws BOException
    {
        // 生成txt
        if (BaseFileConstants.FILE_TYPE_TXT.equals(this.fileType))
        {
            writeToTXTFile();
        }
        // 生成csv
        else if (BaseFileConstants.FILE_TYPE_CSV.equals(this.fileType))
        {
            writeToCSVFile();
        }
        // 生成excel
        else if (BaseFileConstants.FILE_TYPE_EXC.equals(this.fileType))
        {
            writeToExcelFile();
        }
        // 不在生成文件类型之内
        else
        {
            throw new BOException("所选生成文件类型不在指定生成类型之内");
        }
    }
    
    /**
     * 生成对账文件
     *
     */
    protected void writerCheckFileFile() throws BOException
    {
        StringBuffer sb = new StringBuffer();
        BufferedWriter writer = null;
        File file = new File(this.fileName);
        
        try
        {
            writer = new BufferedWriter(new FileWriter(checkFileName));
            

            sb.append(this.toFileName)
            .append("|")
            .append(file.length())
            .append("|")
            .append(hm.size());
            
            writer.write(sb.toString());
            // /r/n
            writer.write(13);
            writer.write(10);
            writer.write("999999");
            
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            throw new BOException("写入对账文件文件异常 fileName=" + checkFileName, e);
        }
        catch (Throwable e)
        {
            throw new BOException("出错文件名fileName=" + checkFileName, e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (Exception e1)
            {
            }
        }
    }

    /**
     * 因需求要求写入txt中。这里做如下改动
     * 
     * @return
     */
    private String objectToString(Object[] obj)
    {
        StringBuffer sb = new StringBuffer();

        for (int j = 0; j < obj.length; j++)
        {
            sb.append(this.checkFileldNull(obj[j]));

            // 加入分隔符
            if (j == obj.length - 1)
            {
            }
            else
            {
                sb.append(this.compart);
            }
        }
        return sb.toString();
    }

    /**
     * 写入至文本文件中
     */
    public void writeToTXTFile() throws BOException
    {
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(fileName));

            // 迭代数据集合
            Set ts = hm.entrySet();
            Iterator it = ts.iterator();

            int i = 0;
            while (it.hasNext())
            {
                Entry ey = ( Entry ) it.next();
                Object[] obj = ( Object[] ) ey.getValue();

                writer.write(objectToString(obj));
                // /r/n
                writer.write(13);
                writer.write(10); 

                // 防止占用太多内存，每一万行刷新到文件一下。
                if (i % 10000 == 0)
                {
                    writer.flush();
                }
                i++;
            }
            writer.close();
        }
        catch (IOException e)
        {
            throw new BOException("写入搜索文件异常 fileName=" + fileName, e);
        }
        catch (Throwable e)
        {
            throw new BOException("出错文件名fileName=" + fileName, e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (Exception e1)
            {
            }
        }
    }

    /**
     * 写入CSV文件
     * 
     * @throws BOException
     */
    public void writeToCSVFile() throws BOException
    {
        CSVWriter writer = null;
        try
        {
            writer = new CSVWriter(new FileWriter(fileName));

            Set ts = hm.entrySet();
            Iterator it = ts.iterator();
            int i = 0;

            while (it.hasNext())
            {
                Entry ey = ( Entry ) it.next();
                String[] obj = ( String[] ) ey.getValue();

                writer.writeNext(obj);

                // 防止占用太多内存，每一万行刷新到文件一下。
                if (i % 10000 == 0)
                {
                    writer.flush();
                }
                i++;
            }
            writer.close();
        }
        catch (IOException e)
        {
            throw new BOException("写入文件异常 fileName=" + fileName, e);
        }
        catch (Throwable e)
        {
            throw new BOException("fileName=" + fileName, e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (Exception e1)
            {
            }
        }
    }

    /**
     * 写入excel文件中
     * 
     * @throws BOException
     * @throws SocketException
     */
    public void writeToExcelFile() throws BOException
    {
        int maxSheetRowSize = 65535;

        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        int sheetNumber = 0;
        try
        {
            workbook = Workbook.createWorkbook(new File(fileName));
            Set ts = hm.entrySet();
            Iterator it = ts.iterator();
            int i = 0;
            while (it.hasNext())
            {
                Entry ey = ( Entry ) it.next();
                // 当前sheet的行数。
                int rowNumber = i % maxSheetRowSize;

                // 如果大于sheet最大允许的行数，则顺序创建接下来的sheet
                if (rowNumber == 0)
                {
                    sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                                 sheetNumber);
                    sheetNumber++;
                }

                Object[] obj = ( Object[] ) ey.getValue();
                for (int n = 0; n < obj.length; n++)
                {
                    sheet.addCell(new Label(n, rowNumber, ( String ) obj[n]));
                }
            }

            // 如果为空，这里要建一个sheet，否则打开文件出错
            if (sheet == null)
            {
                sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                             sheetNumber);
            }
        }
        catch (SocketException e)
        {
            throw new BOException("当用户选择取消的时候会出现这个异常，该异常不算错误。", e);
        }
        catch (Exception e)
        {
            throw new BOException("创建excel文件出错", e);
        }
        finally
        {
            try
            {
                workbook.write();
                workbook.close();
            }
            catch (Exception e)
            {
                throw new BOException("保存excel数据有误", e);
            }
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

            if (!"".equals(BaseFileConfig.FTPPAHT))
            {
                ftp.chdir(BaseFileConfig.FTPPAHT);
            }

            // 把本地全路径文件上传至指定文件名
            ftp.put(this.fileName, toFileName);

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

    protected FTPClient getFTPClient() throws IOException, FTPException
    {
        String ip = BaseFileConfig.FTPIP;
        int port = BaseFileConfig.FTPPORT;
        String user = BaseFileConfig.FTPNAME;
        String password = BaseFileConfig.FTPPAS;

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
     * 判断该字符是否超出maxLength的长度。
     * @param field 要验证的字段内容
     * @param maxLength 允许的最大长度
     * @return
     */
    protected String checkFieldLength(String field,int maxLength)
    {
        if(field==null)
        {
            return "";
        }
        // 如果超出范围截取
        if(StringTool.lengthOfHZ(field)>maxLength)
        {
            return StringTool.formatByLen(field, maxLength, "");
        }
        
        return field;
    }
    
    /**
     * 判断该字符是回车换行
     * @param temp 传进来的值
     * @return
     */
    protected String checkFileldEnter(String temp)
    {
        if(temp == null)
        {
            return "";
        }
        
        if (this.isChangeEnter)
        {
            temp = String.valueOf(temp).replaceAll(enterString,
                                                   changeEnterToStr);
        }
        return temp;
    }
    
    /**
     * 判断该字符是否为null。
     * @param temp 传进来的值
     * @return
     */
    private Object checkFileldNull(Object temp)
    {
        if(temp == null)
        {
            return "";
        }
        else
        {
            return temp;
        }
    }
    
    /**
     * 返回字符型結果
     * 
     * @param clob
     * @return
     * @throws SQLException
     */
    protected String getClobString(Clob clob) throws SQLException
    {
        if (clob == null)
        {
            return "";
        }
        long len = clob.length();
        return clob.getSubString(0, ( int ) len);
    }

    /**
     * 单行解析存放数据返回数组（可去重）
     * 
     * @desc
     * @author dongke Oct 9, 2011
     * @param rs
     * @return
     * @throws SQLException
     */
    protected abstract Object[] fromObject(ResultSet rs) throws SQLException;
    
    /**
     * 用于子类可拓展功能：重命文件名
     * 
     * @throws BOException
     */
    protected void updateFileName() throws BOException
    {
        
    }
}
