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
import com.aspire.dotcard.gcontent.DeviceVO;
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
public class CategoryDeviceDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryDeviceDAO.class);

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryDeviceDAO()
    {
    }

    /**
     * singleton模式的实例
     */
    private static CategoryDeviceDAO categoryDeviceDAO = new CategoryDeviceDAO();

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static final CategoryDeviceDAO getInstance()
    {
        return categoryDeviceDAO;
    }

    /**
     * 应用类分页读取VO的实现类
     */
    private class CategoryDevicePageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            DeviceVO vo = ( DeviceVO ) content;
            vo.setDeviceId(String.valueOf(rs.getInt("device_id")));
            vo.setDeviceName(rs.getString("device_name"));
            vo.setBrand(rs.getString("brand_name"));
        }

        public Object createObject()
        {

            return new DeviceVO();
        }
    }

    /**
     * 根据机型信息。得到机型列表
     * 
     * @param device 机型信息
     * @return
     * @throws DAOException
     */
    public void queryDeviceList(PageResult page, DeviceVO device)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryDeviceList(" + device.toString()
                         + ") is starting ...");
        }

        // select t.device_id,t.device_name,b.brand_name from t_device
        // t,t_device_brand b where t.brand_id=b.brand_id
        String sqlCode = "repository.CategoryDeviceDAO.queryDeviceList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            

            if (!"".equals(device.getDeviceId()))
            {
                //sql += " and t.device_id =" + device.getDeviceId() + "";
            	sqlBuffer.append(" and t.device_id = ? ");
            	paras.add(device.getDeviceId());
            }
            if (!"".equals(device.getDeviceName()))
            {
//                sql += " and t.device_name like('%" + device.getDeviceName()
//                       + "%')";
            	
            	sqlBuffer.append(" and t.device_name like  ? ");
            	paras.add("%"+SQLUtil.escape(device.getDeviceName())+"%");
            }
            if (!"".equals(device.getBrand()))
            {
                //sql += " and b.brand_name like('%" + device.getBrand() + "%')";
            	sqlBuffer.append(" and b.brand_name like ? ");
            	paras.add("%"+SQLUtil.escape(device.getBrand())+"%");
            }

            //page.excute(sql, null, new CategoryDevicePageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new CategoryDevicePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
    
    /**
     * 用于得到当前货架关联了哪些机型信息
     * @param categoryId
     * @return
     */
    public List queryDeviceByCategoryId(String categoryId)  throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到当前货架关联了哪些机型信息:开始执行......");
        }
        
        ResultSet rs = null;
        DeviceVO vo = null;
        List list = new ArrayList();
        try
        {
            // select * from t_r_device_category t where t.categoryid=?
            rs = DB.getInstance()
                   .queryBySQLCode("repository.CategoryDeviceDAO.queryDeviceByCategoryId().query",
                                   new Object[] { categoryId });
            while (rs.next())
            {
                vo = new DeviceVO();
                vo.setDeviceId(String.valueOf(rs.getInt("deviceId")));
                vo.setDeviceName(rs.getString("deviceName"));
                list.add(vo);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("提取结果集到VO对象中发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("得到当前货架关联了哪些机型信息:执行结束.");
        }
        return list;
    }

    /**
     * 用于保存机型信息与货架的关联关系
     * @param categoryId
     * @param devices
     * @throws BOException
     */
    public void saveDeviceToCategory(String categoryId, String[] devices)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("saveDeviceToCategory(" + categoryId
                         + ") is starting ...");
        }

        // insert into t_r_device_category (categoryid, deviceid, devicename) values (?,?,?)
        String sqlCode = "repository.CategoryDeviceDAO.saveDeviceToCategory().save";

        String[] mutiSQL = new String[devices.length];
        Object paras[][] = new String[devices.length][3];

        for (int i = 0; i < devices.length; i++)
        {
            String device = devices[i];
            String[] temp = device.split(";");

            mutiSQL[i] = sqlCode;
            paras[i][0] = categoryId;
            paras[i][1] = temp[0];
            paras[i][2] = temp[1];
        }
        
        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQL, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("保存机型信息与货架的关联关系时发生异常:", e);
        }
    }
    
    /**
     * 用于删除货架与机型的关联关系数据
     * @param categoryId
     */
    public void delDeviceToCategory(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delDeviceToCategory(" + categoryId
                         + ") is starting ...");
        }

        // delete from t_r_device_category t where t.categoryid=?
        String sqlCode = "repository.CategoryDeviceDAO.delDeviceToCategory().delete";

        try
        {
            DB.getInstance()
                    .executeBySQLCode(sqlCode,
                                      new Object[] { categoryId});
        }
        catch (DAOException e)
        {
            logger.error("CategoryDeviceDAO.delDeviceToCategory():",
                         e);
            throw new DAOException(e);
        }
    }
}
