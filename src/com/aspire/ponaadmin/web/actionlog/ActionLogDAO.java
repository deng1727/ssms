package com.aspire.ponaadmin.web.actionlog ;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * <p>操作日志的DAO类</p>
 * <p>操作日志的DAO类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ActionLogDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ActionLogDAO.class);
    /**
     * singleton模式的实例
     */
    private static ActionLogDAO instance = new ActionLogDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private ActionLogDAO ()
    {
    }

    /**
     * 获取实例
     * @return 实例
     */
    static final ActionLogDAO getInstance()
    {
        return instance;
    }

    /**
     * 插入一条操作日志记录到操作日志数据库表。
     * @param log ActionLogVO 操作日志信息值对象的实例
     */
    public void addLog(ActionLogVO log) throws DAOException 
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("addLog("+log+")");
        }
        String sqlCode = "actionlog.ActionLogDAO.addLog().INSERT";
        int actionResult = 0;
        if(log.getActionResult())
        {
            actionResult = 1;
        }
        int logID = DB.getSeqValue("SEQ_ACTIONLOG_ID");
        Object[] paras = {new Integer(logID), log.getActionTime(), log.getUserID()
            , log.getUserName(), log.getRoles(), log.getIP()
            , log.getActionType() , log.getActionTarget()
            , new Integer(actionResult) , log.getActionDesc()
            };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 根据关键字和时间范围查询操作日志
     * @param page PageResult 分页对象
     * @param key String 查询的关键字
     * @param startDate String 查询的开始日期，格式为YYYYMMDD
     * @param endDate String 查询的结束日期，格式为YYYYMMDD
     * @param result String 操作结果条件
     */
    public void queryLog(PageResult page, String key, String startDate, String endDate, String result) throws DAOException 
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug ("queryLog(" + key + "," + startDate + "," + endDate + ")") ;
        }
        key = '%' + key + '%';
        String sqlCode = "actionlog.ActionLogDAO.queryLog().SELECT";
        if(!("-1".equals(result)))
        {
            Object[] paras_result = {startDate, endDate, key, key, key, key, result};
            sqlCode = "actionlog.ActionLogDAO.queryLog().result.SELECT";
            page.excuteBySQLCode(sqlCode, paras_result, new ActionLogVO());
           
        }else
        {
            Object[] paras = {startDate, endDate, key, key, key, key};
            page.excuteBySQLCode(sqlCode, paras, new ActionLogVO());  
        } 
    }

    /**
     * 根据logID查询
     * @param logID String
     * @return ActionLogVO
     */
    public ActionLogVO getLogByID(String logID) throws DAOException 
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("addLog("+logID+")");
        }
        String sqlCode = "actionlog.ActionLogDAO.getLogByID().SELECT";
        Object[] paras = {new Integer(logID)};
        ResultSet rs = null;
        ActionLogVO log = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if(rs.next())
            {
                log = new ActionLogVO();
                this.getActionLogVOFromRS(log, rs);
            }
        }
        catch(SQLException e)
        {
            throw new DAOException("getLogByID error", e);
        }
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(Exception e)
                {
                    logger.error(e);
                }
            }
        }
        DB.getInstance().executeBySQLCode(sqlCode, paras);
        return log;
    }

    /**
     * 删除actionlog
     * @param time String
     */

    final void delActionlog(String time) throws DAOException 
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("delActionlog()");
        }
        String sqlCode = "actionlog.ActionLogDAO.delActionlog().DELETE";
        //定义在sql语句中要替换的参数
        Object[] paras = {time};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 从数据库记录集获取数据并封装到一个新构造的ActionLogVO对象
     * @param log ActionLogVO
     * @param rs 数据库记录集
     */
    final void getActionLogVOFromRS(ActionLogVO log, ResultSet rs) throws SQLException
    {
        log.setLogID(rs.getString("logid"));
        log.setActionTime(rs.getString("ActionTime"));
        log.setUserID(rs.getString("userid"));
        log.setUserName(rs.getString("username"));
        log.setRoles(rs.getString("roles"));
        log.setIP(rs.getString("ip"));
        log.setActionType(rs.getString("actiontype"));
        log.setActionTarget(rs.getString("actiontarget"));
        log.setActionResult(rs.getInt("actionresult") == 1);
        log.setActionDesc(rs.getString("actiondesc"));

    }
}
