/*
 * 文件名：TempExigenceFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.dataexport.basefile.impl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.searchfile.SearchFileConfig;
import com.aspire.ponaadmin.web.dataexport.basefile.BaseFileAbstract;
import com.aspire.ponaadmin.web.dataexport.basefile.task.BaseFileConstants;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version 
 */
public class TempExigenceFile extends BaseFileAbstract
{
    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(TempExigenceFile.class);
  
    public TempExigenceFile()
    {
        this.fileName = SearchFileConfig.LOCALDIR + "temp" +File.separator + "temp_"
        + PublicUtil.getCurDateTime("yyyyMMddHHmmss") + "_00001.temp";  
        
        LOG.info(" SearchFileConfig.LOCALDIR " +  SearchFileConfig.LOCALDIR );
        LOG.info("当前生成文件路径" + this.fileName);
        
        this.toFileName = "temp_" + PublicUtil.getCurDateTime("yyyyMMddHHmmss")
                          + "_00001.txt";
        
        this.fileType = BaseFileConstants.FILE_TYPE_TXT;
        
        this.compart = getCompart();
        
        this.enterString = getEnterString();

        this.isUpload = false;
        
        this.isChangeEnter = true;
    }
    
    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.dataexport.basefile.BaseFileAbstract#fromObject(java.sql.ResultSet)
     */
    protected Object[] fromObject(ResultSet rs) throws SQLException
    {
        String[] obj = new String[28];
        
        obj[0] = rs.getString("options");
        obj[1] = getCateType(rs.getString("catename"));
        obj[2] = rs.getString("servattr");
        obj[3] = rs.getString("goodsid");
        obj[4] = rs.getString("contentid");
        obj[5] = rs.getString("name");
        obj[6] = String.valueOf(rs.getInt("nameboost"));
        obj[7] = rs.getString("singer");
        obj[8] = String.valueOf(rs.getInt("singerboost"));
        obj[9] = rs.getString("spname");
        
        obj[10] = "";
        
        try
        {
        	obj[10] = getClobString(rs.getClob("fulldevicename"));
        }
        catch(Exception e)
        {
        	LOG.error("这个clod转换又错了。换成空了。。。",e);
        }
        obj[11] = rs.getString("type");
        obj[12] = String.valueOf(rs.getInt("mobileprice"));
        obj[13] = String.valueOf(rs.getInt("averagemark"));
        obj[14] = rs.getString("marketdate");
        obj[15] = rs.getString("keywords");
        obj[16] = this.checkFileldEnter(rs.getString("introduction"));
        obj[17] = rs.getString("scantimes");
        obj[18] = rs.getString("appcateid");
        obj[19] = rs.getString("chargetime");
        obj[20] = rs.getString("pvcid");
        obj[21] = rs.getString("cityid");
        obj[22] = rs.getString("othernet");
        obj[23] = rs.getString("subtype");
        obj[24] = rs.getString("platform");
        obj[25] = rs.getString("marketingcha");
        obj[26] = rs.getString("logo3");
        obj[27] = rs.getString("logo4");
  
        Object [] robj = new Object[2];
        robj[0] = rs.getString("goodsid");//去重的ID
        robj[1] = obj;
        return robj;
    }
    
    /**
     * 用來改換一級分類類型
     * @param cateName
     * @return
     */
    private String getCateType(String cateName)
    {
        String temp = "";
        
        if("软件".equals(cateName))
        {
            temp = "appSoftWare";
        }
        else if ("游戏".equals(cateName))
        {
            temp = "appGame";
        }
        else if ("主题".equals(cateName))
        {
            temp = "appTheme";
        }
        return temp;
    }
    
    /**
     * 用于得到数据集合
     */
    protected void queryDBforList() throws DAOException
    {
        int temp = 0;
        
        this.sql = "dataexport.basefile.TempExigenceFile.getODBData";
        
        // 执行sql语句得到数据集。
        queryDBforListByPrivate(sql, String.valueOf(temp++));
        
        this.sql = "dataexport.basefile.TempExigenceFile.getWDBData";
        
        // 执行sql语句得到数据集。
        queryDBforListByPrivate(sql, String.valueOf(temp++));
        
        this.sql = "dataexport.basefile.TempExigenceFile.getADBData";

        // 执行sql语句得到数据集。
        queryDBforListByPrivate(sql, String.valueOf(temp++));

        // 查询各门户删除相关数据
        this.sql = "dataexport.basefile.TempExigenceFile.getODelData";
        
        // 执行sql语句得到数据集。
        queryDBforListByPrivate(sql, String.valueOf(temp++));
        
        // 查询各门户删除相关数据
        this.sql = "dataexport.basefile.TempExigenceFile.getWDelData";
        
        // 执行sql语句得到数据集。
        queryDBforListByPrivate(sql, String.valueOf(temp++));
        
        // 查询各门户删除相关数据
        this.sql = "dataexport.basefile.TempExigenceFile.getADelData";
        
        // 执行sql语句得到数据集。
        queryDBforListByPrivate(sql, String.valueOf(temp++));
    }
    
    /**
     * 用于得到数据集合
     * 
     * @param sql
     * @throws DAOException
     */
    private void queryDBforListByPrivate( String sql, String temp ) throws DAOException
    {
        // 执行sql语句得到数据集。
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sql, null);

            int t = 1;
            
            while (rs.next())
            {
                // 放入数据集合
                Object[] va = fromObject(rs);
                hm.put(temp + String.valueOf(t++), va[1]);
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
     * 返回指定分隔符0X01
     * @return
     */
    private String getCompart()
    {
        int  a = 0x01;
        char r = (char)a;
        return String.valueOf(r);
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
     * 用于子类可拓展功能：重命文件名
     * 
     * @throws BOException
     */
    protected void updateFileName() throws BOException
    {
        boolean result = IOUtil.rename(new File(fileName), toFileName);
        
        if (!result)
        {
            new BOException("重命名文件失败，sourceFileName=" + fileName);
        }
    }
}
