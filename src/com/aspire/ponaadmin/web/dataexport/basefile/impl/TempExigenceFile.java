/*
 * �ļ�����TempExigenceFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(TempExigenceFile.class);
  
    public TempExigenceFile()
    {
        this.fileName = SearchFileConfig.LOCALDIR + "temp" +File.separator + "temp_"
        + PublicUtil.getCurDateTime("yyyyMMddHHmmss") + "_00001.temp";  
        
        LOG.info(" SearchFileConfig.LOCALDIR " +  SearchFileConfig.LOCALDIR );
        LOG.info("��ǰ�����ļ�·��" + this.fileName);
        
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
        	LOG.error("���clodת���ִ��ˡ����ɿ��ˡ�����",e);
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
        robj[0] = rs.getString("goodsid");//ȥ�ص�ID
        robj[1] = obj;
        return robj;
    }
    
    /**
     * �Á�ēQһ��������
     * @param cateName
     * @return
     */
    private String getCateType(String cateName)
    {
        String temp = "";
        
        if("���".equals(cateName))
        {
            temp = "appSoftWare";
        }
        else if ("��Ϸ".equals(cateName))
        {
            temp = "appGame";
        }
        else if ("����".equals(cateName))
        {
            temp = "appTheme";
        }
        return temp;
    }
    
    /**
     * ���ڵõ����ݼ���
     */
    protected void queryDBforList() throws DAOException
    {
        int temp = 0;
        
        this.sql = "dataexport.basefile.TempExigenceFile.getODBData";
        
        // ִ��sql���õ����ݼ���
        queryDBforListByPrivate(sql, String.valueOf(temp++));
        
        this.sql = "dataexport.basefile.TempExigenceFile.getWDBData";
        
        // ִ��sql���õ����ݼ���
        queryDBforListByPrivate(sql, String.valueOf(temp++));
        
        this.sql = "dataexport.basefile.TempExigenceFile.getADBData";

        // ִ��sql���õ����ݼ���
        queryDBforListByPrivate(sql, String.valueOf(temp++));

        // ��ѯ���Ż�ɾ���������
        this.sql = "dataexport.basefile.TempExigenceFile.getODelData";
        
        // ִ��sql���õ����ݼ���
        queryDBforListByPrivate(sql, String.valueOf(temp++));
        
        // ��ѯ���Ż�ɾ���������
        this.sql = "dataexport.basefile.TempExigenceFile.getWDelData";
        
        // ִ��sql���õ����ݼ���
        queryDBforListByPrivate(sql, String.valueOf(temp++));
        
        // ��ѯ���Ż�ɾ���������
        this.sql = "dataexport.basefile.TempExigenceFile.getADelData";
        
        // ִ��sql���õ����ݼ���
        queryDBforListByPrivate(sql, String.valueOf(temp++));
    }
    
    /**
     * ���ڵõ����ݼ���
     * 
     * @param sql
     * @throws DAOException
     */
    private void queryDBforListByPrivate( String sql, String temp ) throws DAOException
    {
        // ִ��sql���õ����ݼ���
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sql, null);

            int t = 1;
            
            while (rs.next())
            {
                // �������ݼ���
                Object[] va = fromObject(rs);
                hm.put(temp + String.valueOf(t++), va[1]);
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
     * ����ָ���ָ���0X01
     * @return
     */
    private String getCompart()
    {
        int  a = 0x01;
        char r = (char)a;
        return String.valueOf(r);
    }

    /**
     * ���ڷ���0d0a���лس���
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
     * �����������չ���ܣ������ļ���
     * 
     * @throws BOException
     */
    protected void updateFileName() throws BOException
    {
        boolean result = IOUtil.rename(new File(fileName), toFileName);
        
        if (!result)
        {
            new BOException("�������ļ�ʧ�ܣ�sourceFileName=" + fileName);
        }
    }
}
