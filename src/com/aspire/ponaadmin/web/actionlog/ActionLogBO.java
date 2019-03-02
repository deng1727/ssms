package com.aspire.ponaadmin.web.actionlog ;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.constant.ErrorCode;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>操作日志BO类</p>
 * <p>操作日志BO类，提供操作日志的接口方法</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ActionLogBO
{

    /**
     * 日志引用  
     */
    protected static JLogger logger = LoggerFactory.getLogger(ActionLogBO.class) ;

    /**
     * 日志中操作对象最大长度。
     */
    private static final int MAX_ACTIONTARGET_LEN = 300 ;

    /**
     * singleton模式的实例
     */
    private static ActionLogBO instance = new ActionLogBO() ;

    /**
     * 构造方法，由singleton模式调用
     */
	private ActionLogBO ()
    {
    }

	/**
	 * 获取实例
	 * @return 实例
	 */
    public static ActionLogBO getInstance()
    {
        return instance;
    }

    /**
     * 根据关键字、开始时间、结束时间查询操作日志。
     * @param page PageResult 分页对象
     * @param key String 查询的关键字
     * @param startDate String 查询的开始日期，格式为YYYYMMDDHHmmSS
     * @param endDate String 查询的结束日期，格式为YYYYMMDDHHmmSS
     * @throws BOException
     */
    public void queryLog(PageResult page, String key, String startDate,
			String endDate,String result) throws BOException
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug ("queryLog(" + key + "," + startDate + "," + endDate + ")") ;
        }
        if(page == null)
        {
        	throw new BOException("invalid para , page is null");
        }
        if(key == null)
        {
            key = "";
        }
        //如果startDate不填的话，为最早的日期。
        if(startDate == null || startDate.trim().equals(""))
        {
            startDate = "00000000000000";
        }
        //如果endDate不填的话，为当前日期。
        if(endDate == null || endDate.trim().equals(""))
        {
            endDate = PublicUtil.getCurDateTime("yyyyMMddHHmmss");
        }
        try
        {
            ActionLogDAO.getInstance().queryLog(page, key, startDate,
                                                            endDate,result) ;
        }
        catch (DAOException e)
        {
            logger.error(e);
        }
    }

    /**
     * 根据logID查询
     * @param logID String
     * @return ActionLogVO
     * @throws BOException
     */
    public ActionLogVO getLogByID(String logID) throws BOException
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug ("getLogByID(" + logID + ")") ;
        }
        if (logID == null || !logID.matches("\\d{1,9}"))
        {
            throw new BOException("invalid para logID");
        }
        try
        {
            return ActionLogDAO.getInstance().getLogByID(logID);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据logID查询失败！");
        }
    }

    /**
     * 对外提供的记录操作日志方法。
     * <br/>1、检查所有的参数都不能为null
     * <br/>2、记录操作日志
     * @param userID String 执行操作的用户帐号
     * @param userName String 执行操作的用户姓名
     * @param roles String 执行操作的用户具有的角色，可能有多个
     * @param actionType String 操作类型
     * @param result boolean 操作结果
     * @param actionTarget String 操作对象
     * @param IP String 访问IP
     * @param desc String 操作描述
     * @throws BOException
     */
    public void log(String userID, String userName, String roles, String actionType
    		, boolean result, String actionTarget, String IP, String desc)
    throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("log("+userID+","+userName+","+roles+","+actionType+","+result
            		+","+actionTarget+","+IP+","+desc+")");
        }
        //检查参数
        if ((actionType == null) || (actionTarget == null) ||
            (IP == null ) || (userID == null))
        {
            throw new BOException("invalid para", ErrorCode.INVALID_PARA);
        }
        desc = (desc == null ? "" : desc) ;
        userName = (userName == null ? "" : userName) ;
        roles = (roles == null ? "" : roles) ;
        //如果操作对象过长，就截长。
        if(PublicUtil.getLength(actionTarget) > MAX_ACTIONTARGET_LEN)
        {
            actionTarget = PublicUtil.formatByLen(actionTarget, MAX_ACTIONTARGET_LEN, "…");
        }
        //构造vo
        ActionLogVO log = new ActionLogVO();
        log.setUserID(userID);
        log.setUserName(userName);
        log.setIP(IP);
        log.setActionType(actionType);
        log.setActionResult(result);
        log.setActionDesc(desc);
        log.setActionTarget(actionTarget);
        log.setActionTime(PublicUtil.getCurDateTime("yyyyMMddHHmmss"));
        log.setRoles(roles);
        //添加到后台异步任务执行队列中
        WriteActionLogTask task = new WriteActionLogTask(log);
        DaemonTaskRunner.getInstance().addTask(task);
    }
}
