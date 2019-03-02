
package com.aspire.ponaadmin.web.datafield.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.datafield.vo.ContentExtQueryVO;
import com.aspire.ponaadmin.web.datafield.vo.ContentExtVO;

/**
 * <p>
 * 应用活动属性管理查询的DAO类
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
public class ContentExtDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ContentExtDAO.class);

    /**
     * singleton模式的实例
     */
    private static ContentExtDAO instance = new ContentExtDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private ContentExtDAO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static final ContentExtDAO getInstance()
    {

        return instance;
    }

    /**
     * 根据名称、提供商等字段查询应用。
     * 
     * @param page
     * @param contentID 内容ID
     * @param name 内容名称
     * @param spName 提供商
     * @param type 类型:1团购，2秒杀
     * @param isrecomm 类型:0，不推荐；1，推荐
     * @throws DAOException
     */
    public void queryContentExtList(PageResult page, String contentID,
                                    String name, String spName, String type,
                                    String isrecomm,String date) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryContentExtList(contentID=" + contentID
                         + " name=" + name + " spName=" + spName + " type="
                         + type + " isrecomm=" + isrecomm  + " date=" + date + ")");
        }

        String sqlCode = "datafield.ContentExtDAO.queryContentExtList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(contentID))
            {
                //sql += " and contentID ='" + contentID + "'";
            	sqlBuffer.append(" and contentID = ? ");
            	paras.add(contentID);
            }
            if (!"".equals(name))
            {
                //sql += " and name like'%" + name + "%'";
            	sqlBuffer.append(" and name like ? ");
            	paras.add("%"+SQLUtil.escape(name)+"%");
            }
            if (!"".equals(spName))
            {
                //sql += " and spName like'%" + spName + "%'";
            	sqlBuffer.append(" and spName like ? ");
            	paras.add("%"+SQLUtil.escape(spName)+"%");
            }

            if (!"".equals(type))
            {
                //sql += " and type =" + type;
            	sqlBuffer.append(" and type =? ");
            	paras.add(type);
            }
            if (!"".equals(isrecomm))
            {
                //sql += " and isrecomm =" + isrecomm;
            	sqlBuffer.append(" and isrecomm = ? ");
            	paras.add(isrecomm);
            }
            if (!"".equals(date))
            {
                //sql += " and dateStart  <= '" + date+ "' and dateEnd >='" + date+ "'";
            	sqlBuffer.append(" and dateStart  <= ? and dateEnd >= ? ");
            	paras.add(date);
            	paras.add(date);
            }
            //sql += " order by id";
            sqlBuffer.append(" order by id");
            

            //page.excute(sql, null, new ContentExtVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new ContentExtVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("queryContentExtList is error", e);
        }
    }

    /**
     * 根据名称、提供商等字段查询应用。
     * 
     * @param page
     * @param contentID 内容ID
     * @param name 内容名称
     * @param spName 提供商
     * @param type 类型:1团购，2秒杀
     * @throws DAOException
     */
    public void queryContentExtQueryList(PageResult page, String contentID,
                                         String name, String spName)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryContentExtQueryList(contentID=" + contentID
                         + " name=" + name + " spName=" + spName + ")");
        }

        String sqlCode = "datafield.ContentExtDAO.queryContentExtQueryList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(contentID))
            {
                //sql += " and t.contentID ='" + contentID + "'";
            	sqlBuffer.append(" and t.contentID = ? ");
            	paras.add(contentID);
            	
            }
            if (!"".equals(name))
            {
                //sql += " and t.name like'%" + name + "%'";
            	sqlBuffer.append(" and t.name like ?");
            	paras.add("%"+SQLUtil.escape(name)+"%");
            }
            if (!"".equals(spName))
            {
                //sql += " and t.spName like'%" + spName + "%'";
            	sqlBuffer.append(" and t.spName like ? ");
            	paras.add("%"+SQLUtil.escape(spName)+"%");
            }

            //sql += " order by t.contentID";
            sqlBuffer.append(" order by t.contentID");

            //page.excute(sql, null, new ContentExtQueryVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new ContentExtQueryVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("queryContentExtQueryList is error", e);
        }
    }

    /**
     * 从数据库记录集获取数据并封装到一个新构造的ContentVO对象
     * 
     * @param vo ContentVO
     * @param rs 数据库记录集
     */
    public final void getContentExtVOFromRS(ContentExtVO vo, ResultSet rs)
                    throws SQLException
    {

        vo.setId(rs.getString("id"));

        vo.setContentID(rs.getString("contentID"));

        vo.setName(rs.getString("name"));

        vo.setSpName(rs.getString("spName"));

        vo.setType(rs.getInt("type"));

        vo.setDiscount(rs.getInt("discount"));

        vo.setDateStart(rs.getString("dateStart"));

        vo.setDateEnd(rs.getString("dateEnd"));

        vo.setTimeStart(rs.getString("timeStart"));

        vo.setTimeEnd(rs.getString("timeEnd"));

        vo.setLupDate(rs.getString("lupDate"));

        vo.setUserid(rs.getString("userid"));

        vo.setMobilePrice(rs.getInt("mobilePrice"));

        vo.setExpPrice(rs.getDouble("expPrice"));
        vo.setIcpcode(rs.getString("icpcode"));
        vo.setIsrecomm(rs.getString("isrecomm"));
    }

    /**
     * 从数据库记录集获取数据并封装到一个新构造的ContentVO对象
     * 
     * @param vo ContentVO
     * @param rs 数据库记录集
     */
    public final void getContentExtQueryVOFromRS(ContentExtQueryVO vo,
                                                 ResultSet rs)
                    throws SQLException
    {

        vo.setContentID(rs.getString("contentID"));
        vo.setName(rs.getString("name"));
        vo.setSpName(rs.getString("spName"));
        vo.setIcpcode(rs.getString("icpcode"));
        vo.setMobilePrice(rs.getInt("mobilePrice"));
        vo.setSubType(rs.getString("subType"));
        vo.setMarketDate(rs.getString("marketDate"));
        vo.setServAttr(rs.getString("servAttr"));
        vo.setChargetime(rs.getString("chargetime"));
    }

    public void contentExtSave(String contentID, String name, String spName,
                               String icpcode, String type, String mobilePrice,
                               String discount, String dateStart,
                               String dateEnd, String timeStart,
                               String timeEnd, String userid, double expPrice,
                               String isrecomm) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("contentExtSave(contentID=" + contentID + " name="
                         + name + " spName=" + spName + " icpcode=" + icpcode
                         + " icpcode=" + icpcode + " type=" + type
                         + " mobilePrice=" + mobilePrice + " discount="
                         + discount + " dateStart=" + dateStart + " dateEnd="
                         + dateEnd + " timeStart=" + timeStart + " timeEnd="
                         + timeEnd + " userid=" + userid + " expPrice="
                         + expPrice + " isrecomm=" + isrecomm);
        }

        String sqlCode = "datafield.ContentExtDAO.contentExtSave().INSERT";
        Object[] paras = { contentID, name, spName, type, discount, dateStart,
                        dateEnd, timeStart, timeEnd, userid, mobilePrice,
                        ( int ) expPrice + "", icpcode, isrecomm };
        DB.getInstance().executeBySQLCode(sqlCode, paras);

    }

    public int contentIsOnly(String contentID, String dateStart,
                             String dateEnd, String timeStart, String timeEnd)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("contentIsOnly(contentID=" + contentID + " dateStart="
                         + dateStart + " dateEnd=" + dateEnd + " timeStart="
                         + timeStart + " timeEnd=" + timeEnd);
        }

        String sqlCode = "datafield.ContentExtDAO.contentIsOnly().SELECT";
        Object[] paras = { contentID, dateStart, dateEnd, timeStart, timeEnd };
        ResultSet rs = null;
        int ret = 0;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {

                ret = rs.getInt(1);

            }
        }
        catch (SQLException e)
        {
            throw new DAOException("contentIsOnly error", e);
        }
        finally
        {
            DB.close(rs);
        }
        return ret;
    }

    public int contentIsOnly(String contentID, String dateStart,
                             String dateEnd, String timeStart, String timeEnd,
                             String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("contentIsOnly(contentID=" + contentID + " dateStart="
                         + dateStart + " dateEnd=" + dateEnd + " timeStart="
                         + timeStart + " timeEnd=" + timeEnd + " id=" + id);
        }

        String sqlCode = "datafield.ContentExtDAO.contentIsOnly().SELECT1";
        Object[] paras = { contentID, dateStart, dateEnd, timeStart, timeEnd,
                        id };
        ResultSet rs = null;
        int ret = 0;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {

                ret = rs.getInt(1);

            }
        }
        catch (SQLException e)
        {
            throw new DAOException("contentIsOnly error", e);
        }
        finally
        {
            DB.close(rs);
        }
        return ret;
    }

    public void idDel(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("idDel(" + id + ")");
        }

        String sqlCode = "datafield.ContentExtDAO.idDel().DELETE";
        Object[] paras = { id };
        DB.getInstance().executeBySQLCode(sqlCode, paras);

    }
    
    public ContentExtVO queryContentExtID(String id) throws DAOException
    {

        String sqlCode = "datafield.ContentExtDAO.queryContentExtID().SELECT";
        
        
        Object[] paras = { id };
        ResultSet rs = null;
        ContentExtVO vo = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {
                vo = new ContentExtVO();
                vo.setContentID(rs.getString("contentid"));
                vo.setName(rs.getString("name"));
                vo.setSpName(rs.getString("spname"));
                vo.setMobilePrice(rs.getInt("mobileprice"));
                vo.setIcpcode(rs.getString("icpcode"));

            }
        }
        catch (SQLException e)
        {
            throw new DAOException("queryContentExtID error", e);
        }
        finally
        {
            DB.close(rs);
        }
        return vo;
    }

    public ContentExtVO getContentExtID(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getContentExtID(" + id + ")");
        }
        String sqlCode = "datafield.ContentExtDAO.getContentExtID().SELECT";
        Object[] paras = { id };
        ResultSet rs = null;
        ContentExtVO vo = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {
                vo = new ContentExtVO();
                getContentExtVOFromRS(vo, rs);

            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getContentExtID error", e);
        }
        finally
        {
            DB.close(rs);
        }
        return vo;
    }

    public void contentExtUpdate(String id, String type, String discount,
                                 String dateStart, String dateEnd,
                                 String timeStart, String timeEnd,
                                 String userid, double expPrice, String isrecomm)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("contentExtUpdate(" + id + ")");
        }
        String sqlCode = "datafield.ContentExtDAO.contentExtUpdate().UPDATE";
        Object[] paras = { type, discount, dateStart, dateEnd, timeStart,
                        timeEnd, userid, ( int ) expPrice + "", isrecomm, id };
        DB.getInstance().executeBySQLCode(sqlCode, paras);

    }
}
