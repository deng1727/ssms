/*
 * 文件名：CategoryDeviceDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.repository;

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
import com.aspire.dotcard.gcontent.CityVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

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
public class CategoryCityDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryCityDAO.class);

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryCityDAO()
    {

    }

    /**
     * singleton模式的实例
     */
    private static CategoryCityDAO categoryDeviceDAO = new CategoryCityDAO();

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static final CategoryCityDAO getInstance()
    {

        return categoryDeviceDAO;
    }

    /**
     * 应用类分页读取VO的实现类
     */
    private class CategoryCityPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            CityVO vo = ( CityVO ) content;
            vo.setCityId(String.valueOf(rs.getInt("cityid")));
            vo.setCityName(rs.getString("cityname"));
            vo.setPvcName(rs.getString("provname"));
        }

        public Object createObject()
        {

            return new CityVO();
        }
    }

    /**
     * 根据地域信息。得到地域列表
     * 
     * @param city 地域信息
     * @param cityId 父货架存在的地域信息
     * @return
     * @throws DAOException
     */
    public void queryCityList(PageResult page, CityVO city, List cityId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryCityList(" + city.toString()
                         + ") is starting ...");
        }

        // select c.cityid, c.cityname, p.provname from mo_city c, mo_province p where c.provinceid = p.provinceid
        String sqlCode = "repository.CategoryCityDAO.queryCityList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(city.getCityName()))
            {
               // sql += " and c.cityname like('%" + city.getCityName() + "%')";
            	 sql += " and c.cityname like  ? ";
            	 paras.add("%"+SQLUtil.escape(city.getCityName())+"%");
            	 
            }
            if (!"".equals(city.getPvcName()))
            {
//                sql += " and p.provname like('%" + city.getPvcName()
//                       + "%')";
            	
                sql += " and p.provname like ? ";
                paras.add("%"+SQLUtil.escape(city.getPvcName())+"%");
                
            }

            if (cityId.size() >= 1)
            {
                sql += " and c.cityid in(";

                // 迭代放入IN条件中
                for (int i = 0; i < cityId.size(); i++)
                {
                    String c = String.valueOf(cityId.get(i));
                    sql += c + ",";
                }

                // 去最后的逗号
                sql = sql.substring(0, sql.length() - 1);

                sql += ")";
            }

            //page.excute(sql, null, new CategoryCityPageVO());
            page.excute(sql, paras.toArray(), new CategoryCityPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 根据货架所存城市id组合。得到用于页面显示信息
     * 
     * @param city
     * @return
     */
    public String queryCityListByCityId(String[] city) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryCityListByCityId() is starting ...");
        }

        String sqlCode = "repository.CategoryCityDAO.queryCityList().SELECT";
        ResultSet rs = null;
        StringBuffer temp = new StringBuffer();
        StringBuffer ret = new StringBuffer();

        try
        {
            temp.append(SQLCode.getInstance().getSQLStatement(sqlCode));

            String s = "";
            // 迭代放入IN条件中
            for (int i = 0; i < city.length; i++)
            {
                String c = city[i];
                s += c + ",";
            }

            // 去最后的逗号
            if (s.length() > 0)
            {
                s = s.substring(0, s.length() - 1);
            }

            temp.append(" and c.cityid in (");
            temp.append(s);
            temp.append(")");

            rs = DB.getInstance().query(temp.toString(), new Object[] {});

            while (rs.next())
            {
                ret.append(rs.getString("cityname")).append("; ");
            }

            return ret.toString();
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
        catch (DAOException e)
        {
            throw new DAOException("根据货架所存城市id组合。得到用于页面显示信息查询时，发生数据库操作错误。", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("根据货架所存城市id组合。得到用于页面显示信息查询时，发生数据库操作错误。", e);
        }
    }
    
    /**
     * 根据父货架id得到子货架所有区域信息集合
     * @param pcategoryId
     * @return
     * @throws DAOException
     */
    public List queryCityListByPcategoryId(String pcategoryId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryCityListByPcategoryId(" + pcategoryId + ") is starting ...");
        }

        // select c.id, c.categoryid, c.cityid from t_r_category c where c.parentcategoryid=?
        String sqlCode = "repository.CategoryCityDAO.queryCityListByPcategoryId().SELECT";
        ResultSet rs = null;
        List ret = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {pcategoryId});

            while (rs.next())
            {
                ret.add(rs.getString("cityid"));
            }

            return ret;
        }
        catch (SQLException e)
        {
            throw new DAOException("根据父货架id得到子货架所有区域信息集合信息查询时，发生数据库操作错误。", e);
        }
    }
    
    /**
     * 根据父货架id得到子货架所有区域信息集合
     * @param pcategoryId
     * @return
     * @throws DAOException
     */
    public List queryCityListByNewMusicPcategoryId(String pcategoryId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryCityListByPcategoryId(" + pcategoryId + ") is starting ...");
        }

        // select c.cityid from t_mb_category_new c where c.parentcategoryid=?
        String sqlCode = "repository.CategoryCityDAO.queryCityListByNewMusicPcategoryId().SELECT";
        ResultSet rs = null;
        List ret = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {pcategoryId});

            while (rs.next())
            {
                ret.add(rs.getString("cityid"));
            }

            return ret;
        }
        catch (SQLException e)
        {
            throw new DAOException("根据父货架id得到子货架所有区域信息集合信息查询时，发生数据库操作错误。", e);
        }
    }
    
    /**
     * 根据父货架id得到子货架所有区域信息集合
     * @param pcategoryId
     * @return
     * @throws DAOException
     */
    public List queryCityListByBookPcategoryId(String pcategoryId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryCityListByBookPcategoryId(" + pcategoryId + ") is starting ...");
        }

        // select c.cityid from t_rb_category c where c.parentid = ?
        String sqlCode = "repository.CategoryCityDAO.queryCityListByBookPcategoryId().SELECT";
        ResultSet rs = null;
        List ret = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {pcategoryId});

            while (rs.next())
            {
                ret.add(rs.getString("cityid"));
            }

            return ret;
        }
        catch (SQLException e)
        {
            throw new DAOException("根据父货架id得到子货架所有区域信息集合信息查询时，发生数据库操作错误。", e);
        }
    }
    
    /**
     * 根据父货架id得到子货架所有区域信息集合
     * @param pcategoryId
     * @return
     * @throws DAOException
     */
    public List queryCityListByReadPcategoryId(String pcategoryId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryCityListByReadPcategoryId(" + pcategoryId + ") is starting ...");
        }

        // select c.cityid from t_rb_category c where c.parentid = ?
        String sqlCode = "repository.CategoryCityDAO.queryCityListByReadPcategoryId().SELECT";
        ResultSet rs = null;
        List ret = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {pcategoryId});

            while (rs.next())
            {
                ret.add(rs.getString("cityid"));
            }

            return ret;
        }
        catch (SQLException e)
        {
            throw new DAOException("根据父货架id得到子货架所有区域信息集合信息查询时，发生数据库操作错误。", e);
        }
    }
}
