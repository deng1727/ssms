package com.aspire.ponaadmin.web.actionlog ;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * <p>������־��DAO��</p>
 * <p>������־��DAO��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ActionLogDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ActionLogDAO.class);
    /**
     * singletonģʽ��ʵ��
     */
    private static ActionLogDAO instance = new ActionLogDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private ActionLogDAO ()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    static final ActionLogDAO getInstance()
    {
        return instance;
    }

    /**
     * ����һ��������־��¼��������־���ݿ��
     * @param log ActionLogVO ������־��Ϣֵ�����ʵ��
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
     * ���ݹؼ��ֺ�ʱ�䷶Χ��ѯ������־
     * @param page PageResult ��ҳ����
     * @param key String ��ѯ�Ĺؼ���
     * @param startDate String ��ѯ�Ŀ�ʼ���ڣ���ʽΪYYYYMMDD
     * @param endDate String ��ѯ�Ľ������ڣ���ʽΪYYYYMMDD
     * @param result String �����������
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
     * ����logID��ѯ
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
     * ɾ��actionlog
     * @param time String
     */

    final void delActionlog(String time) throws DAOException 
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("delActionlog()");
        }
        String sqlCode = "actionlog.ActionLogDAO.delActionlog().DELETE";
        //������sql�����Ҫ�滻�Ĳ���
        Object[] paras = {time};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * �����ݿ��¼����ȡ���ݲ���װ��һ���¹����ActionLogVO����
     * @param log ActionLogVO
     * @param rs ���ݿ��¼��
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
