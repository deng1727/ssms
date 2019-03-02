/*
 * �ļ�����CategoryDeviceDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryCityDAO.class);

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryCityDAO()
    {

    }

    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryCityDAO categoryDeviceDAO = new CategoryCityDAO();

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static final CategoryCityDAO getInstance()
    {

        return categoryDeviceDAO;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
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
     * ���ݵ�����Ϣ���õ������б�
     * 
     * @param city ������Ϣ
     * @param cityId �����ܴ��ڵĵ�����Ϣ
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
            //����������sql�Ͳ���
            
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

                // ��������IN������
                for (int i = 0; i < cityId.size(); i++)
                {
                    String c = String.valueOf(cityId.get(i));
                    sql += c + ",";
                }

                // ȥ���Ķ���
                sql = sql.substring(0, sql.length() - 1);

                sql += ")";
            }

            //page.excute(sql, null, new CategoryCityPageVO());
            page.excute(sql, paras.toArray(), new CategoryCityPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ���ݻ����������id��ϡ��õ�����ҳ����ʾ��Ϣ
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
            // ��������IN������
            for (int i = 0; i < city.length; i++)
            {
                String c = city[i];
                s += c + ",";
            }

            // ȥ���Ķ���
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
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
        catch (DAOException e)
        {
            throw new DAOException("���ݻ����������id��ϡ��õ�����ҳ����ʾ��Ϣ��ѯʱ���������ݿ��������", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("���ݻ����������id��ϡ��õ�����ҳ����ʾ��Ϣ��ѯʱ���������ݿ��������", e);
        }
    }
    
    /**
     * ���ݸ�����id�õ��ӻ�������������Ϣ����
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
            throw new DAOException("���ݸ�����id�õ��ӻ�������������Ϣ������Ϣ��ѯʱ���������ݿ��������", e);
        }
    }
    
    /**
     * ���ݸ�����id�õ��ӻ�������������Ϣ����
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
            throw new DAOException("���ݸ�����id�õ��ӻ�������������Ϣ������Ϣ��ѯʱ���������ݿ��������", e);
        }
    }
    
    /**
     * ���ݸ�����id�õ��ӻ�������������Ϣ����
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
            throw new DAOException("���ݸ�����id�õ��ӻ�������������Ϣ������Ϣ��ѯʱ���������ݿ��������", e);
        }
    }
    
    /**
     * ���ݸ�����id�õ��ӻ�������������Ϣ����
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
            throw new DAOException("���ݸ�����id�õ��ӻ�������������Ϣ������Ϣ��ѯʱ���������ݿ��������", e);
        }
    }
}
