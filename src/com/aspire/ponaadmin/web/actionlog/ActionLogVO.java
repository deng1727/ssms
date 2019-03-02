package com.aspire.ponaadmin.web.actionlog ;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>操作日志VO类</p>
 * <p>操作日志VO类，封装了操作日志的信息</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ActionLogVO implements PageVOInterface
{

	/**
	 * 操作日志的ID
     */
    private String logID;

    /**
     * 操作时间
     */
    private String actionTime ;

    /**
     * 用户ID
     */
    private String userID ;

    /**
     * 用户名
     */
    private String userName ;

    /**
     * 用户角色，用户进行操作时所用的角色
     */
    private String roles ;

    /**
     * 访问IP
     */
    private String IP ;

    /**
     * 操作动作
     */
    private String actionType ;

    /**
     * 操作对象
     */
    private String actionTarget ;

    /**
     * 操作结果，成功或者失败
     */
    private boolean actionResult ;

    /**
     * 描述
     */
    private String actionDesc ;

    /**
     * 构造方法
     */
    public ActionLogVO ()
    {
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    public String getUserName ()
    {
        return userName ;
    }

    /**
     * 获取用户帐号ID
     * @return 用户帐号ID
     */
    public String getUserID ()
    {
        return userID ;
    }

    /**
     * 获取操作角色，可能有多个
     * @return 操作角色
     */
    public String getRoles ()
    {
        return roles ;
    }

    /**
     * 获取操作结果
     * @return 操作结果
     */
    public boolean getActionResult ()
    {
        return actionResult ;
    }

    /**
     * 获取操作描述
     * @return 操作描述
     */
    public String getActionDesc ()
    {
        return actionDesc ;
    }

    /**
     * 获取操作类型
     * @return 操作类型
     */
    public String getActionType ()
    {
        return actionType ;
    }

    /**
     * 获取操作时间
     * @return 操作时间
     */
    public String getActionTime ()
    {
        return actionTime ;
    }

    /**
     * 获取操作对象
     * @param actionTarget 操作对象
     */
    public void setActionTarget (String actionTarget)
    {
        this.actionTarget = actionTarget ;
    }

    /**
     * 设置用户名
     * @param userName 用户名
     */
    public void setUserName (String userName)
    {
        this.userName = userName ;
    }

    /**
     * 设置用户帐号ID
     * @param userID 用户帐号ID
     */
    public void setUserID (String userID)
    {
        this.userID = userID ;
    }

    /**
     * 设置用户角色，可能有多个
     * @param roles 用户角色
     */
    public void setRoles (String roles)
    {
        this.roles = roles ;
    }

    /**
     * 设置操作结果
     * @param actionResult 操作结果
     */
    public void setActionResult (boolean actionResult)
    {
        this.actionResult = actionResult ;
    }

    /**
     * 设置操作描述
     * @param actionDesc 操作描述
     */
    public void setActionDesc (String actionDesc)
    {
        this.actionDesc = actionDesc ;
    }

    /**
     * 设置操作类型
     * @param actionType 操作类型
     */
    public void setActionType (String actionType)
    {
        this.actionType = actionType ;
    }

    /**
     * 设置操作时间
     * @param actionTime 操作时间
     */
    public void setActionTime (String actionTime)
    {
        this.actionTime = actionTime ;
    }

    /**
     * 设置日志ID
     * @param logID 日志ID
     */
    public void setLogID (String logID)
    {
        this.logID = logID ;
	}

    /**
     * 设置操作对象
     * @return 操作对象
     */
    public String getActionTarget ()
    {
        return actionTarget ;
    }

    /**
     * 获取日志ID
     * @return 日志ID
     */
    public String getLogID ()
    {
        return logID ;
    }

    /**
     * 设置访问IP
     * @param IP 访问IP
     */
    public void setIP (String IP)
    {
        this.IP = IP ;
    }

    /**
     * 获取访问IP
     * @return 访问IP
     */
    public String getIP ()
    {
        return IP ;
    }

    /**
     * 获取操作日期的显示
     * @return String 操作日期的显示
     */
    public String getDisplayActionTime()
    {
        try
        {
            String display = this.actionTime.substring(0, 4) + '-' +
                this.actionTime.substring(4, 6) + '-'
                + this.actionTime.substring(6, 8) + ' ' +
                this.actionTime.substring(8, 10)
                + ':' + this.actionTime.substring(10, 10 + 2) ;
            return display;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    /**
     * 获取操作结果的显示
     * @return String 操作结果的显示
     */
    public String getDisplayActionResult()
    {
        return this.actionResult ? "成功" : "失败" ;
    }

    /**
     *
     * @param vo VOObject
     * @param rs ResultSet
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public void CopyValFromResultSet (Object vo, ResultSet rs) throws SQLException
    {
        ActionLogDAO.getInstance().getActionLogVOFromRS((ActionLogVO) vo, rs) ;
    }

    /**
     *
     * @return VOObject
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public Object createObject ()
    {
        return new ActionLogVO() ;
    }
}
