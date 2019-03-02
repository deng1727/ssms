/*
 * 文件名：ConentDayComparedDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.reportdata.gcontent.task;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

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
public class ConentDayComparedDAO
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentDayTask.class);

    private static ConentDayComparedDAO dao = new ConentDayComparedDAO();

    private ConentDayComparedDAO()
    {
    }

    public static ConentDayComparedDAO getInstance()
    {
        return dao;
    }

    /**
     * 在开始导入报表数据前，清空t_r_servenday_temp中前一天的过期数据
     */
    public void delOldRepData() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delOldRepData() is starting ...");
        }

        // truncate table t_r_servenday_temp
        String sqlCode = "reportdata.ConentDayComparedDAO.delOldRepData().del";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("在开始导入报表数据前，清空t_r_servenday_temp中前一天的过期数据时发生异常:",
                                   e);
        }
    }

    /**
     * 导入报表中report_servenday数据至t_r_servenday_temp
     */
    public void insertRepData() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertRepData() is starting ...");
        }

        // insert into t_r_servenday_temp value select * from report_servenday r
        String sqlCode = "reportdata.ConentDayComparedDAO.insertRepData().insert";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("导入报表中report_servenday数据至t_r_servenday_temp时发生异常:",
                                   e);
        }
    }

    /**
     * t_r_sevendayscompared表中新排序号变更为旧排序号
     */
    public void updateNewNumberToOld() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateNewNumberToOld() is starting ...");
        }

        // update t_r_sevendayscompared d set d.aoldnumber = d.anewnumber,
        // d.ooldnumber = d.onewnumber, d.anewnumber = 0, d.onewnumber = 0
        String sqlCode = "reportdata.ConentDayComparedDAO.updateNewNumberToOld().up";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, null);
        }
        catch (DAOException e)
        {
            throw new DAOException("t_r_sevendayscompared表中新排序号变更为旧排序号时发生异常:",
                                   e);
        }
    }

    /**
     * 清空临时统计表t_r_sevenday
     * 
     * @throws DAOException
     */
    public void delTempTable() throws DAOException
    {
        // truncate table t_r_sevenday
        String sqlCode = "reportdata.ConentDayComparedDAO.delTempTable().del";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("清空临时统计表t_r_sevenday时发生异常:", e);
        }
    }

    /**
     * 统计报表数据至临时统计表t_r_sevenday中
     * 
     * @throws DAOException
     */
    public void insertNewToTemp(String osId) throws DAOException
    {
        // insert into t_r_sevenday (contentid, ORDERBYID) select content_id,
        // rank() over(partition by 1 order by add_7days_down_count desc) rn
        // from t_r_servenday_temp r where r.os_id = ?
        String sqlCode = "reportdata.ConentDayComparedDAO.insertNewToTemp().insert";
        Object paras[] = { osId };

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("统计报表数据至临时统计表t_r_sevenday中时发生异常:", e);
        }
    }

    /**
     * 得到临时表中全量数据
     * 
     * @return
     * @throws DAOException
     */
    public Map getTempDate() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getTempDate() is starting ...");
        }

        // select * from t_r_sevenday
        String sqlCode = "reportdata.ConentDayComparedDAO.getTempDate().select";
        Object paras[] = null;
        ResultSet rs = null;
        Map map = new HashMap();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            while (rs.next())
            {
                map.put(rs.getString("contentid"),
                        String.valueOf(rs.getInt("orderbyid")));
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("结合统计表中数据更新七天变化量表t_r_sevendayscompared中新排序号时发生异常:",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("结合统计表中数据更新七天变化量表t_r_sevendayscompared中新排序号时发生异常:",
                                   e);
        }

        return map;
    }

    /**
     * 是否存在此内容数据
     * 
     * @param contentId
     * @return
     * @throws DAOException
     */
    public boolean hasContentId(String contentId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasContentId() is starting ...");
        }

        // select 1 from t_r_sevendayscompared s where s.contentid = ?
        String sqlCode = "reportdata.ConentDayComparedDAO.hasContentId().select";
        Object paras[] = { contentId };
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            if (rs.next())
            {
                return true;
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("结合统计表中数据更新七天变化量表t_r_sevendayscompared中新排序号时发生异常:",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("结合统计表中数据更新七天变化量表t_r_sevendayscompared中新排序号时发生异常:",
                                   e);
        }

        return false;
    }

    /**
     * 结合统计表中数据更新七天变化量表t_r_sevendayscompared中新排序号
     * 
     * @param map
     * @param osId 3:OPhone OS, 9:Android
     * @throws DAOException
     */
    public void updateNewDate(Map map, String osId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateNewCompared() is starting ...");
        }

        String[] mutiSQLCode = new String[map.size()];
        Object[][] mutiParas = new Object[map.size()][2];

        // insert into t_r_sevendayscompared s (contentid, aoldnumber,
        // anewnumber, ooldnumber, onewnumber) values (?, 0, ?, 0, 0)
        // insert into t_r_sevendayscompared s (contentid, aoldnumber,
        // anewnumber, ooldnumber, onewnumber) values (?, 0, 0, 0, ?)
        // update t_r_sevendayscompared d set d.anewnumber=? where d.contentid=?
        // update t_r_sevendayscompared d set d.onewnumber=? where d.contentid=?

        String sqlCode_update = "reportdata.ConentDayComparedDAO.updateNewDate().update_";
        String sqlCode_add = "reportdata.ConentDayComparedDAO.updateNewDate().add_";

        Set set = map.keySet();
        int temp = 0;

        for (Iterator iter = set.iterator(); iter.hasNext(); temp++)
        {
            String contentId = ( String ) iter.next();
            String orderbyId = ( String ) map.get(contentId);

            if (this.hasContentId(contentId))
            {
                mutiSQLCode[temp] = sqlCode_update + osId;
                mutiParas[temp] = new Object[] { orderbyId, contentId };
            }
            else
            {
                mutiSQLCode[temp] = sqlCode_add + osId;
                mutiParas[temp] = new Object[] { contentId, orderbyId };
            }
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQLCode, mutiParas);
        }
        catch (DAOException e)
        {
            throw new DAOException("结合统计表中数据更新七天变化量表t_r_sevendayscompared中新排序号时发生异常:",
                                   e);
        }
    }

    /**
     * 清定最终存放排序差值的结果表
     * 
     * @throws DAOException
     */
    public void delCompared() throws DAOException
    {
        // truncate table T_R_SEVENCOMPARED
        String sqlCode = "reportdata.ConentDayComparedDAO.delCompared().del";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("清定最终存放排序差值的结果表时发生异常:", e);
        }
    }

    /**
     * 清定最终存放排序差值的结果表
     * 
     * @throws DAOException
     */
    public void updateCompared() throws DAOException
    {
        // insert into T_R_SEVENCOMPARED (contentid, anumber, onumber) select
        // s.contentid, decode(s.aoldnumber,0,s.anewnumber,s.aoldnumber -
        // s.anewnumber), decode(s.ooldnumber,0,s.onewnumber,s.ooldnumber -
        // s.onewnumber) from t_r_sevendayscompared s
        String sqlCode = "reportdata.ConentDayComparedDAO.updateCompared().update";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("清定最终存放排序差值的结果表时发生异常:", e);
        }
    }
    
    /**
     * 调用存储过程，计算七天下载量排序差值
     * 
     * @param conn
     * @param spName 存储过程名称，此名称必须与sql.properties文件中的key值相对应，否则无法获取对应的语句
     */
    public void exeProcedure(String spName) throws DAOException
    {
        Connection conn = null;
        CallableStatement cs = null;
        String errorMessage;
        try
        {
            // 执行存储过程。
            logger.info("开始执行存储过程：" + spName);

            conn = DB.getInstance().getConnection();

            cs = conn.prepareCall("{call " + spName + "()}");
            
            cs.execute();
            
            logger.info("成功执行存储过程：" + spName);
        }
        catch (Exception e)
        {
            errorMessage = "调用存储过程" + spName + "失败,请立刻联系管理员！！！";
            logger.error(errorMessage, e);
            
            throw new DAOException("调用存储过程，计算七天下载量排序差值时发生异常:", e);
        }
        finally
        {
            try
            {
                cs.close();
            }
            catch (Exception ex)
            {
                logger.error("关闭CallableStatement=失败", ex);
            }
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                }
            }
        }
    }
}
