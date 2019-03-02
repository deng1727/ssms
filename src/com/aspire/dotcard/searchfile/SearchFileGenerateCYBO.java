
package com.aspire.dotcard.searchfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import au.com.bytecode.opencsv.CSVWriter;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.FileMD5Utils;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SearchFileGenerateCYBO
{

    private static final JLogger LOG = LoggerFactory.getLogger(SearchFileGenerateBO.class);

    private List sucessList = new ArrayList();

    private List failureList = new ArrayList();

    private Date startDate;
    private Map goodsidMappingForCY;
   

    /**
     * ʮ�����Ʒָ���
     */
    private char SEPARATOR = ( char ) Integer.parseInt("01", 16);;
    
    private char char01 = ( char ) Integer.parseInt("01", 16);
    private char char0c = ( char ) Integer.parseInt("0c", 16);

    /**
     * ���ݿ��л��з��������
     */
    private String replace = " ";

    private static SearchFileGenerateCYBO BO = new SearchFileGenerateCYBO();

    public static SearchFileGenerateCYBO getInstance()
    {

        return BO;
    }

    public void generateAllSearchFile()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ʼ���������ļ�");
        }
        startDate = new Date();
        sucessList.clear();
        failureList.clear();
       
        try
        {
            prepareAllData();
        }
        catch (DAOException e)
        {
            this.sendResultMail(false, e.getMessage());
            return;
        }
       

       
        generateSearchFileByCYDate("�г�PK",
                                   "cy_data_market",
                                   "cy_data_market.txt");
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�����г�PK�����ļ����,��ʼ�����ռ�PK����");
        }
        generateSearchFileByCYDate("�ռ�PK",
                                   "cy_data_ultimate",
                                   "cy_data_ultimate.txt");

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�����г�PK�����ļ����,��ʼ����2011��ҵ���� ����");
        }
        
        generateSearchFileByCYDate("2012��ҵ����",
                "cy_data_2012",
                "cy_data_2012.txt");

        //add by aiyan 2013-06-09
        generateSearchFileByCYDate("2013��ҵ����",
                "cy_data_2013",
                "cy_data_2013.txt");

        
        sendResultMail(true, null);

       
    }

    public void prepareAllData() throws DAOException
    {
        goodsidMappingForCY = SearchFileGenerateDAO.getInstance()
                                                   .getAllIDMappingGoodsIdByCY();
        
    }

   

    /**
     * 
     */
  /*  public boolean generateSearchFile(String typeName, String contentType,
                                      String fileName)
    {

        return generateSearchFile(typeName,
                                  contentType,
                                  SearchFileConstants.RELATION_O,
                                  null,
                                  fileName);
    }*/

    /**
     * ���ɲ��ϴ������ļ���
     * 
     * @param contentType ��ȡ����������
     * @param relation Ϊĳ���Ż������������ݡ�W ����WWW��O��ʾ�ն�
     * @param servAttr ʡ�ڰ滹�Ǽ��Ű档���Ϊnull���򲻿��Ǹ����ԡ�
     * @param fileName ���ɵ��ļ�����
     * @return �Ƿ����ɳɹ� true ��ʾ�ɹ���false ��ʾʧ�ܡ�
     */
   /* public boolean generateSearchFile(String typeName, String contentType,
                                      int relation, String servAttr,
                                      String fileName)
    {

        String fileNamePath;
        String portal;
        switch (relation)
        {
            case SearchFileConstants.RELATION_W:
                fileNamePath = SearchFileConfig.WWWPATH + fileName;
                portal = "WWW�Ż�";
                break;
            case SearchFileConstants.RELATION_O:
                fileNamePath = SearchFileConfig.MOPATH + fileName;
                portal = "�ն��Ż�";
                break;
            case SearchFileConstants.RELATION_A:
                fileNamePath = SearchFileConfig.WAPATH + fileName;
                portal = "WAP�Ż�";
                break;
            default:
                return false;
        }
        
         * if(SearchFileConstants.RELATION_W.equals(relation)) {
         * 
         * fileNamePath= SearchFileConfig.WWWPATH + fileName; portal="WWW�Ż�";
         * }else { fileNamePath= SearchFileConfig.MOPATH + fileName;
         * portal="�ն��Ż�"; }
         
        // �����ʼ�ͳ�ơ�
        StringBuffer message = new StringBuffer();
        
         * message.append(DBPersistencyCFG.getInstance()
         * .getNodeCFG(contentType) .getTypeDesc());
         

        message.append(portal);
        message.append("--");
        message.append(typeName);
        message.append("--");
        if ("L".equals(servAttr))
        {
            message.append("ʡ�ڰ� ");
        }
        else if ("G".equals(servAttr))
        {
            message.append("���Ű� ");
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ʼ���������ļ����ͣ�" + message.toString());
        }

        try
        {
            ResultSet rs = SearchFileGenerateDAO.getInstance()
                                                .getDataByType(relation,
                                                               contentType,
                                                               servAttr);
            // ��Ҫ��д�����ļ����ɹ���ֱ�Ӹ�����
            String backFileName = fileNamePath
                                  + PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss");
            writeFile(relation, backFileName, rs, 21);// Ŀǰ���е��������ǹ̶���
            IOUtil.deleteFile(new File(fileNamePath));// ɾ���ɵ����ݡ�
            boolean result = IOUtil.rename(new File(backFileName), fileName);
            if (result)
            {
                new BOException("�������ļ�ʧ�ܣ�sourceFileName=" + backFileName);
            }
            message.append("�ɹ�");
            sucessList.add(message.toString());
        }
        catch (BOException e)
        {
            LOG.error(e);
            message.append("ʧ��");
            failureList.add(message);
            return false;
        }
        return true;

    }*/

    /**
     * д���ļ�
     */
    private void writeFile(int relation, String fileNamePath, ResultSet rs,
                           int colunmeSize) throws BOException
    {

        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(fileNamePath));
            String temp[] = new String[colunmeSize];
            int lineNumber = 0;
            while (rs.next())
            {
            	lineNumber ++;
                String id = rs.getString(1);
             // String goodsid = getAppGoodsId(relation, id);// ( String )
                String goodsid = (String)goodsidMappingForCY.get(id);// ( String )
               
                // goodsidMapping.get(id);
                if (goodsid == null)
                {
                    // �����ݵ�idû���ϼܡ�
                	LOG.error("�����ݵ�id"+id+"û���ϼ�");
                    continue;
                }
                temp[0] = goodsid;
                for (int i = 1; i < colunmeSize; i++)// �ӵ�һ�п�ʼ
                {
                    if (i == 7)
                    {
                        temp[i] = getClobString(rs.getClob(7));
                    }
                    else
                    {
                        temp[i] = rs.getString(i);
                    }

                }

                writer.write(objectToString(temp));
                writer.newLine();

                if (lineNumber % 10000 == 0)// ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
                {
                    writer.flush();
                }

            }
            writer.close();
            rs.close();
            if (LOG.isDebugEnabled())
            {
                LOG.debug("д���ļ����");
            }
        }
        catch (IOException e)
        {
            throw new BOException("д�������ļ��쳣 fileName=" + fileNamePath, e);
        }
        catch (SQLException e)
        {
            throw new BOException("��ȡ���ݿ��쳣,��д���fileName=" + fileNamePath, e);
        }
        catch (Throwable e)
        {
            throw new BOException("fileName=" + fileNamePath, e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
                if (rs != null)
                {
                    rs.close();
                }

            }
            catch (Exception e1)
            {
            }
        }

    }

    /**
     * д���ļ�
     */
    private void writeFileCy(int relation, String fileNamePath, ResultSet rs,
                             int colunmeSize) throws BOException
    {

        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(fileNamePath));
            String temp[] = new String[colunmeSize];
            int lineNumber = 0;
            Map hm = new HashMap();
            while (rs.next())
            {
                String id = rs.getString(2);
                if (hm.get(id) == null)
                {

                    for (int i = 0; i < colunmeSize; i++)// �ӵ�һ�п�ʼ
                    {

                        if (i == 7)
                        {
                        	try
                        	{
                        		temp[i] = getClobString(rs.getClob(8));
                        	}
                        	catch(NullPointerException e)
                        	{
                        		temp[i] = "";
                        	}
                        }
                        else
                        {
                            temp[i] = rs.getString(i + 1);
                        }

                    }
                    writer.write(objectToString(temp));
                    writer.newLine();
                    hm.put(id, "");
                    if (lineNumber % 10000 == 0)// ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
                    {
                        writer.flush();
                    }

                }
                else
                {
                    if (LOG.isDebugEnabled())
                    {
                        LOG.debug("�ظ��ļ�¼��" + id);
                    }

                }
            }
            writer.close();
            rs.close();
        }
        catch (IOException e)
        {
            throw new BOException("д�������ļ��쳣 fileName=" + fileNamePath, e);
        }
        catch (SQLException e)
        {
            throw new BOException("��ȡ���ݿ��쳣,��д���fileName=" + fileNamePath, e);
        }
        catch (Throwable e)
        {
            throw new BOException("fileName=" + fileNamePath, e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
                if (rs != null)
                {
                    rs.close();
                }

            }
            catch (Exception e1)
            {
            }
        }

    }

    

    /**
     * д���t_r_gcontent ���ֳ�������
     * 
     * @param type
     * @param fileName
     * @return
     */
    public boolean generateSearchFileByOtherType(String typeName, String type,
                                                 String fileName)
    {

        // �����ʼ�ͳ�ơ�
        StringBuffer message = new StringBuffer();
        message.append(typeName);
        message.append("--");
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ʼ���������ļ����ͣ�" + message.toString());
        }
        String fileNamePath = "";// SearchFileConfig.MOPATH + fileName;;
        try
        {
            ResultSet rs = SearchFileGenerateDAO.getInstance()
                                                .getDataByOthreType(type);
            String backFileName = "";
            boolean result = false;
            // ��Ҫ��д�����ļ����ɹ���ֱ�Ӹ�����
            if (SearchFileConstants.DISSERTATION.equals(type))
            {
                // ר��
                fileNamePath = SearchFileConfig.SUBJECT + fileName;
                backFileName = fileNamePath
                               + PublicUtil.getCurDateTime("yyyy_MM_dd");
                writeFileByOtherTypeNormal(backFileName, rs, 21);// Ŀǰ���е��������ǹ̶���

                IOUtil.deleteFile(new File(fileNamePath));// ɾ���ɵ����ݡ�
                result = IOUtil.rename(new File(backFileName), fileName);
            }
            else if (SearchFileConstants.NEWMUSIC.equals(type))
            {

                String moFileNamePath = SearchFileConfig.MOPATH
                                        + "music_new.txt";
                String mobackFileName = moFileNamePath
                                        + PublicUtil.getCurDateTime("yyyy_MM_dd");

                String wapFileNamePath = SearchFileConfig.WAPATH + fileName;

                String wapbackFileName = wapFileNamePath
                                         + PublicUtil.getCurDateTime("yyyy_MM_dd");

                String wwwbackFileName = null;
                writeFileByOtherTypeForAll(mobackFileName,
                                           wapbackFileName,
                                           wwwbackFileName,
                                           8,
                                           rs,
                                           19);// Ŀǰ���е��������ǹ̶���

                IOUtil.deleteFile(new File(moFileNamePath));// ɾ���ɵ����ݡ�
                result = IOUtil.rename(new File(mobackFileName),
                                       "music_new.txt");

                IOUtil.deleteFile(new File(wapFileNamePath));// ɾ���ɵ����ݡ�
                result = IOUtil.rename(new File(wapbackFileName), fileName);
                // fileNamePath = SearchFileConfig.WAPATH + fileName;
                // backFileName = fileNamePath
                // + PublicUtil.getCurDateTime("yyyy_MM_dd");
                // writeFileByOtherType(backFileName, rs, 19);// Ŀǰ���е��������ǹ̶���
                //               
                //               
                // IOUtil.deleteFile(new File(fileNamePath));// ɾ���ɵ����ݡ�
                // result = IOUtil.rename(new File(backFileName), fileName);
            }
            else if (SearchFileConstants.READ.equals(type))
            {
                // �Ķ����� ֻ����mo�Ż�����wap�Ż�����
                String moFileNamePath = SearchFileConfig.MOPATH + fileName;
                String mobackFileName = moFileNamePath
                                        + PublicUtil.getCurDateTime("yyyy_MM_dd");
                String wapFileNamePath = SearchFileConfig.WAPATH + fileName;

                String wapbackFileName = wapFileNamePath
                                         + PublicUtil.getCurDateTime("yyyy_MM_dd");

                String wwwbackFileName = null;
                writeFileByOtherTypeForAll(mobackFileName,
                                           wapbackFileName,
                                           wwwbackFileName,
                                           8,
                                           rs,
                                           19);// Ŀǰ���е��������ǹ̶���

                IOUtil.deleteFile(new File(moFileNamePath));// ɾ���ɵ����ݡ�
                result = IOUtil.rename(new File(mobackFileName), fileName);

                IOUtil.deleteFile(new File(wapFileNamePath));// ɾ���ɵ����ݡ�
                result = IOUtil.rename(new File(wapbackFileName), fileName);

            }
            else
            {
                fileNamePath = SearchFileConfig.MOPATH + fileName;
                backFileName = fileNamePath
                               + PublicUtil.getCurDateTime("yyyy_MM_dd");
                writeFileByOtherType(backFileName, rs, 19);// Ŀǰ���е��������ǹ̶���

                IOUtil.deleteFile(new File(fileNamePath));// ɾ���ɵ����ݡ�
                result = IOUtil.rename(new File(backFileName), fileName);
            }

            if (result)
            {
                new BOException("�������ļ�ʧ�ܣ�sourceFileName=" + backFileName);
            }
            message.append("�ɹ�");
            sucessList.add(message.toString());
        }
        catch (BOException e)
        {
            LOG.error(e);
            message.append("ʧ��");
            failureList.add(message);
            return false;
        }
        return true;

    }

    /**
     * д���t_r_gcontent ���ֳ�������
     * 
     * @param type
     * @param fileName
     * @return
     */
    public boolean generateSearchFileByCYDate(String typeName, String type,
                                              String fileName)
    {
        // �����ʼ�ͳ�ơ�
        StringBuffer message = new StringBuffer();
        String fileNamePath = "";
        String backFileName = "";
        String portal = "�ն��Ż�";
        boolean result = false;

        message.append(portal);
        message.append("--");
        message.append(typeName);
        message.append("--");

        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ʼ���������ļ����ͣ�" + message.toString());
        }

        try
        {
            ResultSet rs = SearchFileGenerateDAO.getInstance()
                                                .getDataByOthreType(type);

            // ��Ҫ��д�����ļ����ɹ���ֱ�Ӹ�����
            fileNamePath = SearchFileConfig.BUSINESS + fileName;
            backFileName = fileNamePath
                           + PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss");
            if (LOG.isDebugEnabled())
            {
                LOG.debug("�����ݿ��ȡ���ݳɹ�,׼��д�ļ�");
            }
            if (SearchFileConstants.CY_DATA_2010.equals(type)
                || SearchFileConstants.MARKET_PK.equals(type)
                || SearchFileConstants.ULTIMATE_PK.equals(type))
            {
                // Ŀǰ���е��������ǹ̶���
                writeFileCy(SearchFileConstants.RELATION_O,
                            backFileName,
                            rs,
                            31);
            }
            else
            {
                // Ŀǰ���е��������ǹ̶���
                writeFile(SearchFileConstants.RELATION_O, backFileName, rs, 31);
            }

            // ɾ���ɵ����ݡ�
            IOUtil.deleteFile(new File(fileNamePath));

            result = IOUtil.rename(new File(backFileName), fileName);

            if (!result)
            {
                new BOException("�������ļ�ʧ�ܣ�sourceFileName=" + backFileName);
            }

            message.append("�ɹ�");
            
            message.append("<br>");
            message.append("�ļ�����·��Ϊ:").append(fileNamePath);
            message.append("<br>");
            message.append("�ļ�MD5��Ϊ��").append(FileMD5Utils.getFileMD5(new File(fileNamePath)));
            message.append("<br>");
            
            sucessList.add(message.toString());
        }
        catch (BOException e)
        {
            LOG.error(e);
            message.append("ʧ��");
            failureList.add(message);
            return false;
        }
        return true;
    }

    private void writeFileByOtherType(String fileNamePath, ResultSet rs,
                                      int colunmeSize) throws BOException
    {

        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(fileNamePath));
            String temp[] = new String[colunmeSize];
            int lineNumber = 0;
            while (rs.next())
            {
                String id = rs.getString(1);
                temp[0] = id;
                for (int i = 1; i < colunmeSize; i++)// �ӵ�һ�п�ʼ
                {
                    temp[i] = rs.getString(i);
                }
                writer.write(objectToString(temp));
                writer.newLine();
                if (lineNumber % 10000 == 0)// ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
                {
                    writer.flush();
                }

            }
            writer.close();
            rs.close();
        }
        catch (IOException e)
        {
            throw new BOException("д�������ļ��쳣 fileName=" + fileNamePath, e);
        }
        catch (SQLException e)
        {
            throw new BOException("��ȡ���ݿ��쳣,��д���fileName=" + fileNamePath, e);
        }
        catch (Throwable e)
        {
            throw new BOException("fileName=" + fileNamePath, e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
                if (rs != null)
                {
                    rs.close();
                }

            }
            catch (Exception e1)
            {
            }
        }

    }

    /**
     * 
     * @desc ͬһ������д����Ż�
     * @author dongke Jun 9, 2011
     * @param moFileNamePath
     * @param wapFileNamePath
     * @param wwwFileNamePath
     * @param targetnum
     * @param rs
     * @param colunmeSize
     * @throws BOException
     */
    private void writeFileByOtherTypeForAll(String moFileNamePath,
                                            String wapFileNamePath,
                                            String wwwFileNamePath,
                                            int targetnum, ResultSet rs,
                                            int colunmeSize) throws BOException
    {

        BufferedWriter moWriter = null;
        BufferedWriter wapWriter = null;
        BufferedWriter wwwWriter = null;
        try
        {
            if (null != moFileNamePath && !"".equals(moFileNamePath))
            {
                moWriter = new BufferedWriter(new FileWriter(moFileNamePath));
            }
            if (null != wapFileNamePath && !"".equals(wapFileNamePath))
            {
                wapWriter = new BufferedWriter(new FileWriter(wapFileNamePath));
            }
            if (null != wwwFileNamePath && !"".equals(wwwFileNamePath))
            {
                wwwWriter = new BufferedWriter(new FileWriter(wwwFileNamePath));
            }
            String moTemp[] = new String[colunmeSize];
            String wwwTemp[] = new String[colunmeSize];
            String wapTemp[] = new String[colunmeSize];
            int lineNumber = 0;
            while (rs.next())
            {
                String id = rs.getString(1);
                moTemp[0] = id;
                wwwTemp[0] = id;
                wapTemp[0] = id;
                for (int i = 1; i < colunmeSize; i++)// �ӵ�һ�п�ʼ
                {
                    moTemp[i] = rs.getString(i);
                    wwwTemp[i] = rs.getString(i);
                    wapTemp[i] = rs.getString(i);

                }
                if (null != moFileNamePath && !"".equals(moFileNamePath))
                {
                    moTemp[targetnum] = "O";
                    moWriter.write(objectToString(moTemp));
                    moWriter.newLine();
                }
                if (null != wapFileNamePath && !"".equals(wapFileNamePath))
                {
                    wapTemp[targetnum] = "A";
                    wapWriter.write(objectToString(wapTemp));
                    wapWriter.newLine();
                }
                if (null != wwwFileNamePath && !"".equals(wwwFileNamePath))
                {
                    wwwTemp[targetnum] = "W";
                    wwwWriter.write(objectToString(wwwTemp));
                    wwwWriter.newLine();
                }
                if (lineNumber % 10000 == 0)// ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
                {
                    if (null != moFileNamePath && !"".equals(moFileNamePath))
                    {
                        moWriter.flush();
                    }
                    if (null != wapFileNamePath && !"".equals(wapFileNamePath))
                    {
                        wapWriter.flush();
                    }
                    if (null != wwwFileNamePath && !"".equals(wwwFileNamePath))
                    {
                        wwwWriter.flush();
                    }
                }

            }
            if (null != moFileNamePath && !"".equals(moFileNamePath))
            {
                moWriter.close();
            }
            if (null != wapFileNamePath && !"".equals(wapFileNamePath))
            {
                wapWriter.close();
            }
            if (null != wwwFileNamePath && !"".equals(wwwFileNamePath))
            {
                wwwWriter.close();
            }
            // moWriter.close();
            rs.close();
        }
        catch (IOException e)
        {
            throw new BOException("д�������ļ��쳣 fileName=" + moFileNamePath, e);
        }
        catch (SQLException e)
        {
            throw new BOException("��ȡ���ݿ��쳣,��д���fileName=" + moFileNamePath, e);
        }
        catch (Throwable e)
        {
            throw new BOException("fileName=" + moFileNamePath, e);
        }
        finally
        {
            try
            {
                if (moWriter != null)
                {
                    moWriter.close();
                }
                if (wapWriter != null)
                {
                    wapWriter.close();
                }
                if (wwwWriter != null)
                {
                    wwwWriter.close();
                }
                if (rs != null)
                {
                    rs.close();
                }

            }
            catch (Exception e1)
            {
            }
        }

    }

    private void writeFileByOtherTypeNormal(String fileNamePath, ResultSet rs,
                                            int colunmeSize) throws BOException
    {

        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(fileNamePath));
            String temp[] = new String[colunmeSize];
            int lineNumber = 0;
            while (rs.next())
            {
                // String id = rs.getString(1);
                // temp[0] = id;
                for (int i = 1; i <= colunmeSize; i++)// �ӵ�һ�п�ʼ
                {
                    temp[i - 1] = rs.getString(i);
                }
                writer.write(objectToString(temp));
                writer.newLine();
                if (lineNumber % 10000 == 0)// ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
                {
                    writer.flush();
                }

            }
            writer.close();
            rs.close();
        }
        catch (IOException e)
        {
            throw new BOException("д�������ļ��쳣 fileName=" + fileNamePath, e);
        }
        catch (SQLException e)
        {
            throw new BOException("��ȡ���ݿ��쳣,��д���fileName=" + fileNamePath, e);
        }
        catch (Throwable e)
        {
            throw new BOException("fileName=" + fileNamePath, e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
                if (rs != null)
                {
                    rs.close();
                }

            }
            catch (Exception e1)
            {
            }
        }

    }

    /*
     * private boolean uploadRemoteFile(String fileNamePath,String
     * remoteFileName) throws BOException { FTPClient ftp=null; String
     * backFileName=remoteFileName+PublicUtil.getCurDateTime("yyyy_mm_dd"); try {
     * ftp= PublicUtil.getFTPClient(SearchFileConfig.IP, SearchFileConfig.PORT,
     * SearchFileConfig.USER, SearchFileConfig.PWD, SearchFileConfig.FTPDIR);
     * ftp.put(fileNamePath, backFileName);//���ϴ��������ļ�����ɺ��ٽ��и�����
     * ftp.delete(remoteFileName); ftp.rename(backFileName, remoteFileName); }
     * catch (Exception e) { throw new BOException("�ϴ�ftp�ļ�����",e); }finally {
     * if(ftp!=null) { try { ftp.quit(); } catch (Exception e) { } } } return
     * true; }
     */
    /**
     * @param type ����������������
     * @param sourceFilePath ��������Դ�ļ���
     * @param disFileName Ŀ���ļ�����
     */
    /*
     * private boolean copySameDateSource(String type,String
     * sourceFileName,String disFileName) { StringBuffer
     * message=getMessage(type, disFileName); if(LOG.isDebugEnabled()) {
     * LOG.debug("��ʼ���������ļ�="+sourceFileName+",�����������ļ����ͣ�"+message.toString()); }
     * String sourceFilePath=SearchFileConfig.LOCALDIR+sourceFileName; String
     * disFileNamePath=SearchFileConfig.LOCALDIR+disFileName; try {
     * IOUtil.copy(sourceFilePath, disFileNamePath);
     * uploadRemoteFile(disFileNamePath,disFileName); message.append("�ɹ�"); }
     * catch (Exception e) { LOG.error(e); message.append("ʧ��"); return false; }
     * return true; }
     */
    /**
     * ���ͽ���ʼ���
     */
    private void sendResultMail(boolean result, String reason)
    {

        String mailTitle;
        // �����ʼ���ʾ���δ������
        Date endDate = new Date();
        StringBuffer sb = new StringBuffer();
        int totalSuccessCount = sucessList.size();
        int totalFailureCount = failureList.size();
        mailTitle = "�������������ļ����ɽ��";
        if (result)
        {

            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("��<h4>��������</h4>");
            sb.append("<p>���гɹ�����<b>");
            sb.append(totalSuccessCount);
            sb.append("</b>���ļ���");
            sb.append("ʧ�ܵ���<b>");
            sb.append(totalFailureCount);
            sb.append("</b>���ļ���");
            if (totalSuccessCount != 0)
            {
                sb.append("<p>������ϢΪΪ��<p>");

                for (int i = 0; i < sucessList.size(); i++)
                {
                    sb.append(sucessList.get(i));
                    sb.append("<br>");
                }
                sb.append("<br>");
            }

            for (int i = 0; i < failureList.size(); i++)
            {
                sb.append(failureList.get(i));
                sb.append("<br>");
            }

        }
        else
        {
            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("��<p>ʧ��ԭ��<br>");
            sb.append(reason);
        }

        LOG.info(sb.toString());
        Mail.sendMail(mailTitle, sb.toString(), SearchFileConfig.mailTo);
    }

    private String getClobString(Clob clob) throws SQLException
    {

        if (clob == null)
        {
            return "";
        }
        long len = clob.length();
        return clob.getSubString(0, ( int ) len);
    }

    /**
     * ������Ҫ��д��txt�С����������¸Ķ�
     * 
     * @return
     */
    private String objectToString(String[] temp)
    {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < temp.length; i++)
        {
            sb.append(transformString(temp[i]));

            // ����ָ���
            if (i == temp.length - 1)
            {
            }
            else
            {
                sb.append(this.SEPARATOR);
            }
        }

        return sb.toString();
    }

    /**
     * �����滻Ҫ����������ַ�
     * @param temp
     * @return
     */
    private String replaceString(String temp)
    {
        temp = temp.replaceAll("\r", "");
        temp = temp.replaceAll("\f", "");
        temp = temp.replaceAll(String.valueOf(char01), "");
        temp = temp.replaceAll(String.valueOf(char0c), "");
        temp = temp.replaceAll("\n", replace);
        	
        return temp;
    }
    
    /**
     * �����ݿ��д��ڵĻ��з�ת��Ϊ�ո�
     * 
     * @param temp
     * @return
     */
    private String transformString(String temp)
    {
        if (null != temp)
        {
        	temp = replaceString(temp);
        }
        else
        {
            temp = "";
        }
        
        return temp;
    }

}
