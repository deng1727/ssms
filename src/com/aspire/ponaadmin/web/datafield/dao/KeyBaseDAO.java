
package com.aspire.ponaadmin.web.datafield.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;

/**
 * <p>
 * 扩展属性表的DAO类
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class KeyBaseDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(KeyBaseDAO.class);

    /**
     * singleton模式的实例
     */
    private static KeyBaseDAO instance = new KeyBaseDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private KeyBaseDAO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static final KeyBaseDAO getInstance()
    {

        return instance;
    }

    /**
     * 根据表名称查询表扩展字段
     * 
     * @param keytable
     * @return
     * @throws DAOException
     */
    public List getKeytable(String keytable) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getKeytable(" + keytable + ")");
        }
        String sqlCode = "datafield.KeyBaseDAO.getKeytable().SELECT";
        Object[] paras = { keytable };
        ResourceVO vo = null;
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

        List list = new ArrayList();
        // 遍历结果,将每条记录置入类ResourceVO的各个属性中
        try
        {
            while (rs.next())
            {
                vo = new ResourceVO();
                vo.setKeyid(rs.getString("keyid"));
                vo.setKeyname(rs.getString("keyname"));
                vo.setKeydesc(rs.getString("keydesc"));
                list.add(vo);
            }

        }
        catch (SQLException e)
        {
            throw new DAOException("getKeytable error", e);
        }
        finally
        {
            DB.close(rs);

        }
        return list;

    }
    
    /**
     * 根据表名称查询表扩展字段
     * 
     * @param keytable
     * @return
     * @throws DAOException
     */
    public List getKeytableByText(String keytable) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getKeytable(" + keytable + ")");
        }
        String sqlCode = "datafield.KeyBaseDAO.getKeytableByText().SELECT";
        Object[] paras = { keytable };
        ResourceVO vo = null;
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

        List list = new ArrayList();
        // 遍历结果,将每条记录置入类ResourceVO的各个属性中
        try
        {
            while (rs.next())
            {
                vo = new ResourceVO();
                vo.setKeyid(rs.getString("keyid"));
                vo.setKeyname(rs.getString("keyname"));
                vo.setKeydesc(rs.getString("keydesc"));
                vo.setKeyType(rs.getString("keytype"));
                list.add(vo);
            }

        }
        catch (SQLException e)
        {
            throw new DAOException("getKeytable error", e);
        }
        finally
        {
            DB.close(rs);

        }
        return list;

    }

    public void queryKeyBaseList(PageResult page, String keyid, String keyname,
                                 String keytable, String keydesc)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryKeyBaseList(" + keyid + "," + keyname + ","
                         + keytable + "," + keydesc + ")");
        }

        String sqlCode = "datafield.KeyBaseDAO.queryKeyBaseList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数

            if (!"".equals(keyid))
            {
                //sql += " and keyid ='" + keyid + "'";
            	sqlBuffer.append(" and keyid =? ");
            	paras.add(keyid);
            }
            if (!"".equals(keyname))
            {
                //sql += " and keyname ='" + keyname + "'";
            	sqlBuffer.append(" and keyname =? ");
            	paras.add(keyname);
            }
            if (!"".equals(keytable))
            {
                //sql += " and keytable ='" + keytable + "'";
            	sqlBuffer.append(" and keytable =? ");
            	paras.add(keytable);
            	
            }
            if (!"".equals(keydesc))
            {
                //sql += " and keydesc like'%" + keydesc + "%'";
            	sqlBuffer.append(" and keydesc like ? ");
            	paras.add("%"+keydesc+"%");
            }

            //sql += " order by keyid";
            sqlBuffer.append(" order by keyid");

            //page.excute(sql, null, new ResourceVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new ResourceVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("queryKeyBaseList is error", e);
        }
    }

    public void getKeyBaseFromRS(ResourceVO vo, ResultSet rs)
                    throws SQLException
    {

        vo.setKeyid(rs.getString("keyid"));
        vo.setKeyname(rs.getString("keyname"));
        vo.setKeytable(rs.getString("keytable"));
        vo.setKeydesc(rs.getString("keydesc"));
        vo.setKeyType(rs.getString("keytype"));

    }

    /**
     * 根据keyid查询扩展属性
     * 
     * @param keyid
     * @return
     * @throws DAOException
     */
    public ResourceVO getKeyBaseByID(String keyid) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getKeyBaseByID(" + keyid + ")");
        }
        String sqlCode = "datafield.KeyBaseDAO.getKeyBaseByID().SELECT";
        Object[] paras = { keyid };
        ResultSet rs = null;
        ResourceVO vo = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {
                vo = new ResourceVO();
                getKeyBaseFromRS(vo, rs);

            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getKeyBaseByID error", e);
        }
        finally
        {
            DB.close(rs);
        }
        return vo;
    }

    public int updateKeyBase(String keyid, String keytable, String keyname,
                             String keydesc,String keyType) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateKeyBase(" + keyid + ")");
        }
        String sqlCode = "datafield.KeyBaseDAO.updateKeyBase().INSERT";
        Object[] paras = { keytable, keyname, keydesc,keyType, keyid };
        return DB.getInstance().executeBySQLCode(sqlCode, paras);

    }

    /**
     * 批量删除扩展属性
     * 
     * @param keyBaseDels
     * @return
     * @throws BOException
     */
    public void keyBaseDel(String keyid) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("keyBaseDel(" + keyid + ")");
        }
        String[] sqlCodes = new String[] {
                        "datafield.KeyBaseDAO.keyBaseDel().KEYBASE.DELETE",
                        "datafield.KeyBaseDAO.keyBaseDel().KEYRESOURCE.DELETE", };
        Object paras[][] = { { keyid }, { keyid } };
        DB.getInstance().executeMutiBySQLCode(sqlCodes, paras);

    }

    /**
     *  新增扩展属性方法
     * @param keytable
     * @param keyname
     * @param keydesc
     * @return
     * @throws DAOException
     */
    public int insertKeyBase(String keytable, String keyname, String keydesc,String keyType)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateKeyBase(" + keytable + keyname + keydesc+keyType);
        }
        String sqlCode = "datafield.KeyBaseDAO.insertKeyBase().INSERT";
        Object[] paras = { keytable, keyname, keydesc,keyType };
        return DB.getInstance().executeBySQLCode(sqlCode, paras);

    }

    /**
     * 新增扩展属性重复判断
     * @param keyname
     * @param keytable
     * @return
     * @throws DAOException
     */
    public boolean isKebBase(String keyname, String keytable)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("isKebBase(" + keyname + "," + keytable + ")");
        }

        String sqlCode = "datafield.KeyBaseDAO.queryKeyBaseList().SELECT";
        String sql = null;
        boolean ret = true;
        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            

            if (!"".equals(keyname))
            {
                //sql += " and keyname ='" + keyname + "'";
            	sqlBuffer.append( " and keyname = ? ");
            	paras.add(keyname);
            }
            if (!"".equals(keytable))
            {
                //sql += " and keytable ='" + keytable + "'";
            	sqlBuffer.append(" and keytable = ? ");
            	paras.add(keytable);
            }
            ResultSet rs = null;
            try
            {
                rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
                if (rs.next())
                {
                    ret = false;

                }
            }
            catch (SQLException e)
            {
                throw new DAOException("getKeyBaseByID error", e);
            }
            finally
            {
                DB.close(rs);
            }
        }
        catch (DataAccessException e)
        {
            throw new DAOException("isKebBase is error", e);
        }
        return ret;
    }

}
