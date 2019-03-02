/*
 * 文件名：SearchFileBase.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  用于把订阅者中公共方法做抽离
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
 * Title: 订阅者模版抽象类
 * </p>
 * <p>
 * Description: 用于把订阅者中公共方法做抽离
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
     * 内容类型
     */
    protected String type;

    /**
     * 用户来源
     */
    protected int servattr;

    /**
     * 生成文件的类型
     */
    protected int relation;

    /**
     * 生成文件名
     */
    protected String fileName;

    /**
     * 生成文件全路径
     */
    protected String fileNamePath;

    /**
     * 备份文件全路径
     */
    protected String backFileNamePath;

    /**
     * 发布者
     */
    protected SearchFileSubject subject;

    /**
     * 输出对象
     */
    protected BufferedWriter writer = null;

    /**
     * 当前文件要多少个字段
     */
    protected int colunmeSize = 26;

    /**
     * 用于记录临时记录
     */
    protected String temp[] = new String[colunmeSize];

    /**
     * 加入记录数
     */
    protected int lineNumber = 0;
    
    /**
     * 十六进制分隔符
     */
    protected char SEPARATOR;
    
    protected char char01 = ( char ) Integer.parseInt("01", 16);
    protected char char0c = ( char ) Integer.parseInt("0c", 16);
    
    /**
     * 数据库中换行符替代符号
     */
    protected String replace = " ";

    /**
     * 返回当前数据是否是订阅者数据类型
     * 
     * @param marker 内容标识
     * @return
     */
    public boolean isAppMarker(String marker)
    {

        String[] markers = marker.split("&");

        // 如果文件类型不相同。返回错
        if (!this.type.equals(markers[0]))
        {
            return false;
        }

        // 如果不是为MO生成的文件
        // if (relation != SearchFileConstants.RELATION_O)
        // {
        // // 且内容提供者类型不为"O"
        // if (!"O".equals(markers[1]))
        // {
        // return false;
        // }
        // }

        if (relation == SearchFileConstants.RELATION_W)
          {//对于WWW的，不导出类型为B ,即游戏基地的数据给WWW的索引文件
              // 且内容提供者类型不为"B" 
              if ("B".equals(markers[1]))
              {
                  return false;
              }
          }
        	
        // 如果数据来源不是全网应用
        if (servattr == SearchFileConstants.SERVATTR_G)
        {
            // 且内容提供者类型不为"O"
            if (!"G".equals(markers[2]))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 准备流程
     * 
     */
    public boolean prepareData()
    {

        // 得到文件全路径
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

        // 需要先写备份文件，成功后直接改名。
        backFileNamePath = fileNamePath
                           + PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss");

        // 初始化间隔符
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
     * 返回字符型結果
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
     * 由发布者传过来数据
     * 
     * @param rs
     * @throws BOException
     * @throws SQLException
     */
    public void addDate(ResultSet rs) throws BOException, SQLException
    {

        try
        {
            // 得到goodsid
            String goodsid = subject.getAppGoodsId(relation, rs.getString(1));

            // 如果该内容的id没有上架。不加入文件中
            if (goodsid == null)
            {
                return;
            }
            
            // 放入临时数组中
            temp[0] = goodsid;

            // 从第一行开始
            for (int i = 1; i < colunmeSize; i++)
            {
                // 如果是第七行得到他的字符型式
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

                // 如果是第八行。直接把当前对象的为谁生成信息写入
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

            // 如果当前不是为MO生成文件。且类型为主题
            if (relation != SearchFileConstants.RELATION_O
                && RepositoryConstants.TYPE_APPTHEME.equals(this.type))
            {
                int num = Integer.parseInt(temp[9]);

                // 如果大于1000则为0
                if (num > 1000)
                {
                    temp[9] = "0";
                }
            }
            
            String data = objectToString();

            // 写入文件对象中一行
            writer.write(data);
            writer.newLine();

            // 防止占用太多内存，每一万行刷新到文件一下。
            if (lineNumber++ % 10000 == 0)
            {
                // 刷新数据至文件
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
            throw new BOException("写入搜索文件异常 fileName=" + fileNamePath, e);
        }
    }
    
    /**
     * 因需求要求写入txt中。这里做如下改动
     * @return
     */
    private String objectToString()
    {
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < colunmeSize; i++)
        {
            sb.append(transformString(temp[i]));
            
            // 加入分隔符
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
     * 用于替换要处理的特殊字符
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
     * 把数据库中存在的换行符转换为空格
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
     * 生成文件
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

        IOUtil.deleteFile(new File(fileNamePath));// 删除旧的数据。
        boolean result = IOUtil.rename(new File(backFileNamePath), fileName);
        if (result)
        {
            new BOException("重命名文件失败，sourceFileName=" + backFileNamePath);
        }
    }

    /**
     * 返回用于发送邮件时的信息
     * 
     * @param isTrue 生成文件是否成功
     * @return
     */
    public String getMailContent(boolean isTrue)
    {

        StringBuffer sb = new StringBuffer();

        // 得到门户信息
        switch (this.relation)
        {
            case SearchFileConstants.RELATION_W:
                sb.append("WWW门户");
                break;
            case SearchFileConstants.RELATION_O:
                sb.append("终端门户");
                break;
            case SearchFileConstants.RELATION_A:
                sb.append("WAP门户");
                break;
            default:
                return "";
        }

        sb.append("--");

        // 文件类型
        if (RepositoryConstants.TYPE_APPGAME.equals(this.type))
        {
            sb.append("游戏");
        }
        else if (RepositoryConstants.TYPE_APPSOFTWARE.equals(this.type))
        {
            sb.append("软件");
        }
        else if (RepositoryConstants.TYPE_APPTHEME.equals(this.type))
        {
            sb.append("主题");
        }

        sb.append("--");

        // 得到是否为集团
        if (this.servattr == SearchFileConstants.SERVATTR_L)
        {
            sb.append("省内版 ");
        }
        else if (this.servattr == SearchFileConstants.SERVATTR_G)
        {
            sb.append("集团版 ");
        }

        if (isTrue)
        {
            sb.append("成功");
            sb.append("<br>");
            sb.append("文件所在路径为:").append(fileNamePath);
            sb.append("<br>");
            sb.append("文件MD5码为：").append(FileMD5Utils.getFileMD5(new File(fileNamePath)));
            sb.append("<br>");
        }
        else
        {
            sb.append("失败");
        }

        return sb.toString();
    }
}
