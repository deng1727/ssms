
package com.aspire.ponaadmin.web.pivot.dao;

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
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.pivot.vo.PivotDeviceVO;

public class PivotDeviceDAO
{
    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(PivotDeviceDAO.class);

    /**
     * singleton模式的实例
     */
    private static PivotDeviceDAO instance = new PivotDeviceDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private PivotDeviceDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static PivotDeviceDAO getInstance()
    {
        return instance;
    }

    /**
     * 应用类分页读取VO的实现类
     */
    private class PivotDevicePageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            PivotDeviceVO vo = ( PivotDeviceVO ) content;

            vo.setDeviceId(String.valueOf(rs.getInt("device_id")));
            vo.setDeviceName(rs.getString("device_name"));
            vo.setBrandName(rs.getString("brand_name"));
            vo.setOsName(rs.getString("os_name"));
            vo.setCreDate(String.valueOf(rs.getDate("CreDate")));
        }

        public Object createObject()
        {
            return new PivotDeviceVO();
        }
    }

    /**
     * 用于用于查询重点机型列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryPivotDeviceList(PageResult page, PivotDeviceVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryPivotDeviceList() is starting ...");
        }

        // select * from t_pivot_device t where 1=1
        String sqlCode = "pivot.PivotDeviceDAO.queryPivotDeviceList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(vo.getDeviceId()))
            {
                //sql += " and t.device_id ='" + vo.getDeviceId() + "'";
            	sqlBuffer.append(" and t.device_id =? ");
            	paras.add(vo.getDeviceId());
            }
            if (!"".equals(vo.getDeviceName()))
            {
//                sql += " and upper(t.device_name) like('%" + vo.getDeviceName().toUpperCase()
//                       + "%')";
            	sqlBuffer.append(" and upper(t.device_name) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getDeviceName().toUpperCase())+"%");
            }
            if (!"".equals(vo.getBrandName()))
            {
//                sql += " and upper(t.brand_name) like('%"
//                       + vo.getBrandName().toUpperCase() + "%')";
            	sqlBuffer.append(" and upper(t.brand_name) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBrandName().toUpperCase())+"%");
            }
            if (!"".equals(vo.getOsName()))
            {
//                sql += " and upper(t.os_name) like('%"
//                       + vo.getOsName().toUpperCase() + "%')";
            	sqlBuffer.append(" and upper(t.os_name) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getOsName().toUpperCase())+"%");
            }

            //sql += " order by t.device_id asc";
            sqlBuffer.append(" order by t.device_id asc");

            //page.excute(sql, null, new PivotDevicePageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new PivotDevicePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 用于移除指定重点机型
     * 
     * @param deviceId 重点机型id列
     * @throws DAOException
     */
    public void removeDeviceID(String[] deviceId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("removeDeviceID() is starting ...");
        }

        // delete from t_pivot_device r where r.device_id = ?
        String sql = "pivot.PivotDeviceDAO.removeDeviceID().remove";
        String sqlCode[] = new String[deviceId.length];
        Object[][] object = new Object[deviceId.length][1];

        for (int i = 0; i < deviceId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = deviceId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("移除指定重点机型时发生异常:", e);
        }
    }

    /**
     * 校驗文件中數據是否在机型表中存在
     * 
     * @param list
     * @throws DAOException
     */
    public String verifyDeviceId(List list) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("verifyDeviceId() is starting ...");
        }

        // select 1 from t_device c where c.device_id = ?
        String sql = "pivot.PivotDeviceDAO.verifyDeviceId().select";
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        // 迭代查詢
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql.toString(),
                                                     new Object[] { temp });
                // 如果不存在相應數據
                if (!rs.next())
                {
                    list.remove(i);
                    i--;
                    sb.append(temp).append(". ");
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("校驗文件中數據是否在机型表中存在时发生异常:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }

        return sb.toString();
    }
    
    /**
     * 检验列表中是否存在原表数据
     * 
     * @param list
     * @throws DAOException
     */
    public void hasDeviceId(List list) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasDeviceId() is starting ...");
        }

        // select 1 from t_pivot_device c where c.device_id = ?
        String sql = "pivot.PivotDeviceDAO.hasDeviceId().select";
        ResultSet rs = null;

        // 迭代查詢
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql.toString(),
                                                     new Object[] { temp });
                // 如果存在相應數據
                if (rs.next())
                {
                    list.remove(i);
                    i--;
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("检验列表中是否存在原表数据时发生异常:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }
    }
    

    /**
     * 添加机型id列表
     * 
     * @param String
     */
    public void addDeviceId(String[] deviceId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addDeviceId() is starting ...");
        }

        // insert into t_pivot_device (device_id, device_name, brand_name,
        // os_name) select d.device_id, d.device_name, b.brand_name, s.osname
        // from t_device d, t_device_brand b, t_device_os s where b.brand_id =
        // d.brand_id and s.os_id = d.os_id and d.device_id = ?
        String sql = "pivot.PivotDeviceDAO.addDeviceId().add";

        String sqlCode[] = new String[deviceId.length];
        Object[][] object = new Object[deviceId.length][1];

        for (int i = 0; i < deviceId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = deviceId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("添加重点机型id列表时发生异常:", e);
        }
    }
}
