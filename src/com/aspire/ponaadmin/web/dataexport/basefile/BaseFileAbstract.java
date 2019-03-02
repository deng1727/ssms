/*
 * �ļ�����BaseFileAbstract.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ���ڴ�����ݼ���
     */
    protected Map  hm = new HashMap();

    /**
     * �ָ���
     */
    protected String compart;

    /**
     * �����ļ���
     */
    protected String fileName;

    /**
     * �ϴ��ļ����ļ���
     */
    protected String toFileName;
    
    /**
     * ���ɵĶ����ļ���
     */
    protected String checkFileName;
    
    /**
     * �ϴ��ļ��Ķ����ļ���
     */
    protected String toCheckFileName;

    /**
     * ���ڵõ�����Ҫִ�е�sql���
     */
    protected String sql;

    /**
     * ���������ļ�������
     */
    protected String fileType;

    /**
     * �Ƿ��ϴ���FTP
     */
    protected boolean isUpload = true;
    
    /**
     * �Ƿ����ɶ����ļ�
     */
    protected boolean isCreateCheckFile = false;
    
    /**
     * �Ƿ���ַ��еĻس����з����б��
     */
    protected boolean isChangeEnter = false;
    
    /**
     * ���Ҫ��������Ϊ���ַ�
     */
    protected String changeEnterToStr = " ";
    
    /**
     * �س����з���ζ���
     */
    protected String enterString;

    /**
     * ���ڵõ����ݼ���
     */
    protected void queryDBforList() throws DAOException
    {
        // ִ��sql���õ����ݼ���
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sql, null);

            while (rs.next())
            {
                // �������ݼ���
                Object[] va = fromObject(rs);
                hm.put(va[0], va[1]);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("��ѯ�������ݼ���ʱ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("������������ʱ�����쳣:", e);
        }
    }

    /**
     * ���������ļ�
     */
    public void createFile() throws BOException
    {
        // �õ����ݼ��ϡ�
        try
        {
            queryDBforList();
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯ�������ݼ���ʱ�����쳣");
        }
        
        // ����Ŀ¼
        createFilePath();

        // д���ļ�
        writerFile();
        
        // �����������չ���ܣ������ļ���
        updateFileName();
        
        // �Ƿ����ɶ����ļ�
        if(isCreateCheckFile)
        {
            writerCheckFileFile();
        }

        // �Ƿ��ϴ���FTP
        if (isUpload)
        {
            // �ϴ��ļ���FTP
            copyFileToFTP();
        }
    }
    
    /**
     * ���ڴ���Ŀ¼
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
     * ���ڰ����ݼ���д�����ļ���
     */
    private void writerFile() throws BOException
    {
        // ����txt
        if (BaseFileConstants.FILE_TYPE_TXT.equals(this.fileType))
        {
            writeToTXTFile();
        }
        // ����csv
        else if (BaseFileConstants.FILE_TYPE_CSV.equals(this.fileType))
        {
            writeToCSVFile();
        }
        // ����excel
        else if (BaseFileConstants.FILE_TYPE_EXC.equals(this.fileType))
        {
            writeToExcelFile();
        }
        // ���������ļ�����֮��
        else
        {
            throw new BOException("��ѡ�����ļ����Ͳ���ָ����������֮��");
        }
    }
    
    /**
     * ���ɶ����ļ�
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
            throw new BOException("д������ļ��ļ��쳣 fileName=" + checkFileName, e);
        }
        catch (Throwable e)
        {
            throw new BOException("�����ļ���fileName=" + checkFileName, e);
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
     * ������Ҫ��д��txt�С����������¸Ķ�
     * 
     * @return
     */
    private String objectToString(Object[] obj)
    {
        StringBuffer sb = new StringBuffer();

        for (int j = 0; j < obj.length; j++)
        {
            sb.append(this.checkFileldNull(obj[j]));

            // ����ָ���
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
     * д�����ı��ļ���
     */
    public void writeToTXTFile() throws BOException
    {
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(fileName));

            // �������ݼ���
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

                // ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
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
            throw new BOException("д�������ļ��쳣 fileName=" + fileName, e);
        }
        catch (Throwable e)
        {
            throw new BOException("�����ļ���fileName=" + fileName, e);
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
     * д��CSV�ļ�
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

                // ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
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
            throw new BOException("д���ļ��쳣 fileName=" + fileName, e);
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
     * д��excel�ļ���
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
                // ��ǰsheet��������
                int rowNumber = i % maxSheetRowSize;

                // �������sheet����������������˳�򴴽���������sheet
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

            // ���Ϊ�գ�����Ҫ��һ��sheet��������ļ�����
            if (sheet == null)
            {
                sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                             sheetNumber);
            }
        }
        catch (SocketException e)
        {
            throw new BOException("���û�ѡ��ȡ����ʱ����������쳣�����쳣�������", e);
        }
        catch (Exception e)
        {
            throw new BOException("����excel�ļ�����", e);
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
                throw new BOException("����excel��������", e);
            }
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

            if (!"".equals(BaseFileConfig.FTPPAHT))
            {
                ftp.chdir(BaseFileConfig.FTPPAHT);
            }

            // �ѱ���ȫ·���ļ��ϴ���ָ���ļ���
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
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);
        // ʹ�ø������û����������½ftp
        ftp.login(user, password);
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);

        return ftp;
    }
    
    /**
     * �жϸ��ַ��Ƿ񳬳�maxLength�ĳ��ȡ�
     * @param field Ҫ��֤���ֶ�����
     * @param maxLength �������󳤶�
     * @return
     */
    protected String checkFieldLength(String field,int maxLength)
    {
        if(field==null)
        {
            return "";
        }
        // ���������Χ��ȡ
        if(StringTool.lengthOfHZ(field)>maxLength)
        {
            return StringTool.formatByLen(field, maxLength, "");
        }
        
        return field;
    }
    
    /**
     * �жϸ��ַ��ǻس�����
     * @param temp ��������ֵ
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
     * �жϸ��ַ��Ƿ�Ϊnull��
     * @param temp ��������ֵ
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
     * �����ַ��ͽY��
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
     * ���н���������ݷ������飨��ȥ�أ�
     * 
     * @desc
     * @author dongke Oct 9, 2011
     * @param rs
     * @return
     * @throws SQLException
     */
    protected abstract Object[] fromObject(ResultSet rs) throws SQLException;
    
    /**
     * �����������չ���ܣ������ļ���
     * 
     * @throws BOException
     */
    protected void updateFileName() throws BOException
    {
        
    }
}
