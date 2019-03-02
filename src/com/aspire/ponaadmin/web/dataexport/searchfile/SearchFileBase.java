/*
 * �ļ�����SearchFileBase.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  ���ڰѶ������й�������������
 */

package com.aspire.ponaadmin.web.dataexport.searchfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.exception.BOException;
import com.aspire.common.util.FileMD5Utils;
import com.aspire.dotcard.searchfile.SearchFileConfig;
import com.aspire.dotcard.searchfile.SearchFileConstants;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

//import au.com.bytecode.opencsv.CSVWriter;

/**
 * <p>
 * Title: ������ģ�������
 * </p>
 * <p>
 * Description: ���ڰѶ������й�������������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public abstract class SearchFileBase implements SearchFileObserver
{

    /**
     * ��������
     */
    protected String type;

    /**
     * �û���Դ
     */
    protected int servattr;

    /**
     * �����ļ�������
     */
    protected int relation;

    /**
     * �����ļ���
     */
    protected String fileName;

    /**
     * �����ļ�ȫ·��
     */
    protected String fileNamePath;

    /**
     * �����ļ�ȫ·��
     */
    protected String backFileNamePath;

    /**
     * ������
     */
    protected SearchFileSubject subject;

    /**
     * �������
     */
    protected BufferedWriter writer = null;

    /**
     * ��ǰ�ļ�Ҫ���ٸ��ֶ�
     */
    protected int colunmeSize = 26;

    /**
     * ���ڼ�¼��ʱ��¼
     */
    protected String temp[] = new String[colunmeSize];

    /**
     * �����¼��
     */
    protected int lineNumber = 0;
    
    /**
     * ʮ�����Ʒָ���
     */
    protected char SEPARATOR;
    
    protected char char01 = ( char ) Integer.parseInt("01", 16);
    protected char char0c = ( char ) Integer.parseInt("0c", 16);
    
    /**
     * ���ݿ��л��з��������
     */
    protected String replace = " ";

    /**
     * ���ص�ǰ�����Ƿ��Ƕ�������������
     * 
     * @param marker ���ݱ�ʶ
     * @return
     */
    public boolean isAppMarker(String marker)
    {

        String[] markers = marker.split("&");

        // ����ļ����Ͳ���ͬ�����ش�
        if (!this.type.equals(markers[0]))
        {
            return false;
        }

        // �������ΪMO���ɵ��ļ�
        // if (relation != SearchFileConstants.RELATION_O)
        // {
        // // �������ṩ�����Ͳ�Ϊ"O"
        // if (!"O".equals(markers[1]))
        // {
        // return false;
        // }
        // }

        if (relation == SearchFileConstants.RELATION_W)
          {//����WWW�ģ�����������ΪB ,����Ϸ���ص����ݸ�WWW�������ļ�
              // �������ṩ�����Ͳ�Ϊ"B" 
              if ("B".equals(markers[1]))
              {
                  return false;
              }
          }
        	
        // ���������Դ����ȫ��Ӧ��
        if (servattr == SearchFileConstants.SERVATTR_G)
        {
            // �������ṩ�����Ͳ�Ϊ"O"
            if (!"G".equals(markers[2]))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * ׼������
     * 
     */
    public boolean prepareData()
    {

        // �õ��ļ�ȫ·��
        switch (this.relation)
        {
            case SearchFileConstants.RELATION_W:
                fileNamePath = SearchFileConfig.WWWPATH + fileName;
                break;
            case SearchFileConstants.RELATION_O:
                fileNamePath = SearchFileConfig.MOPATH + fileName;
                break;
            case SearchFileConstants.RELATION_A:
                fileNamePath = SearchFileConfig.WAPATH + fileName;
                break;
            default:
                return false;
        }

        // ��Ҫ��д�����ļ����ɹ���ֱ�Ӹ�����
        backFileNamePath = fileNamePath
                           + PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss");

        // ��ʼ�������
        int i = Integer.parseInt("01",16);
        
        SEPARATOR = (char)i;
        
        try
        {
            writer = new BufferedWriter(new FileWriter(backFileNamePath));
        }
        catch (IOException e)
        {
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e1)
                {
                }
            }
            return false;
        }

        return true;
    }

    /**
     * �����ַ��ͽY��
     * 
     * @param clob
     * @return
     * @throws SQLException
     */
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
     * �ɷ����ߴ���������
     * 
     * @param rs
     * @throws BOException
     * @throws SQLException
     */
    public void addDate(ResultSet rs) throws BOException, SQLException
    {

        try
        {
            // �õ�goodsid
            String goodsid = subject.getAppGoodsId(relation, rs.getString(1));

            // ��������ݵ�idû���ϼܡ��������ļ���
            if (goodsid == null)
            {
                return;
            }
            
            // ������ʱ������
            temp[0] = goodsid;

            // �ӵ�һ�п�ʼ
            for (int i = 1; i < colunmeSize; i++)
            {
                // ����ǵ����еõ������ַ���ʽ
                if (i == 7)
                {
                	try
                	{
                		temp[i] = getClobString(rs.getClob(7));
                	}
                	catch(Exception e)
                	{
                		temp[i] = "";
                	}
                }
                else
                {
                    temp[i] = rs.getString(i);
                }

                // ����ǵڰ��С�ֱ�Ӱѵ�ǰ�����Ϊ˭������Ϣд��
                if (i == 8)
                {
                    switch (this.relation)
                    {
                        case SearchFileConstants.RELATION_W:
                            temp[i] = "W";
                            break;
                        case SearchFileConstants.RELATION_O:
                            temp[i] = "O";
                            break;
                        case SearchFileConstants.RELATION_A:
                            temp[i] = "A";
                            break;
                    }
                }
            }

            // �����ǰ����ΪMO�����ļ���������Ϊ����
            if (relation != SearchFileConstants.RELATION_O
                && RepositoryConstants.TYPE_APPTHEME.equals(this.type))
            {
                int num = Integer.parseInt(temp[9]);

                // �������1000��Ϊ0
                if (num > 1000)
                {
                    temp[9] = "0";
                }
            }
            
            String data = objectToString();

            // д���ļ�������һ��
            writer.write(data);
            writer.newLine();

            // ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
            if (lineNumber++ % 10000 == 0)
            {
                // ˢ���������ļ�
                writer.flush();
            }
        }
        catch (Exception e)
        {
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e1)
                {
                }
            }
            throw new BOException("д�������ļ��쳣 fileName=" + fileNamePath, e);
        }
    }
    
    /**
     * ������Ҫ��д��txt�С����������¸Ķ�
     * @return
     */
    private String objectToString()
    {
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < colunmeSize; i++)
        {
            sb.append(transformString(temp[i]));
            
            // ����ָ���
            if(i == colunmeSize-1)
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

    /**
     * �����ļ�
     */
    public void createFile()
    {

        try
        {
            writer.close();
        }
        catch (IOException e)
        {
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e1)
                {
                }
            }
        }

        IOUtil.deleteFile(new File(fileNamePath));// ɾ���ɵ����ݡ�
        boolean result = IOUtil.rename(new File(backFileNamePath), fileName);
        if (result)
        {
            new BOException("�������ļ�ʧ�ܣ�sourceFileName=" + backFileNamePath);
        }
    }

    /**
     * �������ڷ����ʼ�ʱ����Ϣ
     * 
     * @param isTrue �����ļ��Ƿ�ɹ�
     * @return
     */
    public String getMailContent(boolean isTrue)
    {

        StringBuffer sb = new StringBuffer();

        // �õ��Ż���Ϣ
        switch (this.relation)
        {
            case SearchFileConstants.RELATION_W:
                sb.append("WWW�Ż�");
                break;
            case SearchFileConstants.RELATION_O:
                sb.append("�ն��Ż�");
                break;
            case SearchFileConstants.RELATION_A:
                sb.append("WAP�Ż�");
                break;
            default:
                return "";
        }

        sb.append("--");

        // �ļ�����
        if (RepositoryConstants.TYPE_APPGAME.equals(this.type))
        {
            sb.append("��Ϸ");
        }
        else if (RepositoryConstants.TYPE_APPSOFTWARE.equals(this.type))
        {
            sb.append("���");
        }
        else if (RepositoryConstants.TYPE_APPTHEME.equals(this.type))
        {
            sb.append("����");
        }

        sb.append("--");

        // �õ��Ƿ�Ϊ����
        if (this.servattr == SearchFileConstants.SERVATTR_L)
        {
            sb.append("ʡ�ڰ� ");
        }
        else if (this.servattr == SearchFileConstants.SERVATTR_G)
        {
            sb.append("���Ű� ");
        }

        if (isTrue)
        {
            sb.append("�ɹ�");
            sb.append("<br>");
            sb.append("�ļ�����·��Ϊ:").append(fileNamePath);
            sb.append("<br>");
            sb.append("�ļ�MD5��Ϊ��").append(FileMD5Utils.getFileMD5(new File(fileNamePath)));
            sb.append("<br>");
        }
        else
        {
            sb.append("ʧ��");
        }

        return sb.toString();
    }
}
