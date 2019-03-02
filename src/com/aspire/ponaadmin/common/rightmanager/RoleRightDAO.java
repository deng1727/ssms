package com.aspire.ponaadmin.common.rightmanager ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;

/**
 * <p>角色权限相关的DAO类</p>
 * <p>角色权限相关的DAO类，负责根据角色id查询权限和设定权限</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RoleRightDAO
{

    /**
     * 构造方法，由singleton模式调用
     */
    public RoleRightDAO ()
    {
    }

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(RoleRightDAO.class) ;

	/**
	 * singleton模式的实例
	 */
    private static RoleRightDAO roleRightDAO = new RoleRightDAO() ;

	/**
	 * 获取实例
	 * @return 实例
	 */
    static final RoleRightDAO getInstance ()
    {
        return roleRightDAO ;
    }

    /**
     * 设置角色具有的权限
     * @param roleID String,角色ID
     * @param IDList List,权限列表，里面保存权限ID。
     */
    final void setRoleRights (String roleID, List IDList) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setRoleRights(" + roleID + "," + IDList + ")") ;
        }
        String[] mutiSQL = new String[IDList.size()+1] ;
        Object[][] mutiparas = new Object[IDList.size()+1][] ;

        String sqlAddByRoleID = null ;
        String sqlDelByRoleID = null ;
        String sqlCodeAddByRoleID = null ;
        String sqlCodeDelByRoleID = null ;
        sqlCodeAddByRoleID =
            "rightmanager.RoleRightDAO.setRoleRights().INSERT" ;
        sqlCodeDelByRoleID =
            "rightmanager.RoleRightDAO.setRoleRights().DELETE" ;
        try
        {
            sqlAddByRoleID = SQLCode.getInstance().getSQLStatement(
                sqlCodeAddByRoleID) ;
        }
        catch (DataAccessException ex)
        {
            throw new DAOException(ex) ;
        }

        try
        {
            sqlDelByRoleID = SQLCode.getInstance().getSQLStatement(
                sqlCodeDelByRoleID) ;
        }
        catch (DataAccessException ex)
        {
            throw new DAOException(ex) ;
        }
        int i = 0 ;
        mutiSQL[i] = sqlDelByRoleID ;
        Object[] parasDel =
            {roleID} ;
        mutiparas[i] = parasDel ;
        i += 1 ;
        for(int x = 0; x < IDList.size(); x++)
        {
            mutiSQL[i] = sqlAddByRoleID ;
            Object[] paras =
                {roleID, IDList.get(x)} ;
            mutiparas[i] = paras ;
            i += 1 ;
        }
        DB.getInstance().executeMuti(mutiSQL, mutiparas) ;

    }

    /**
     * 获取角色具有的权限
     * @param roleID String,角色ID
     * @return List
     */
    final List getRoleRights (String roleID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRoleRights(" + roleID + ")") ;
        }
        String sqlCode = "rightmanager.RoleRightDAO.getRoleRights().SELECT" ;
        Object[] paras = {roleID} ;
        List list = new ArrayList() ;
        ResultSet rs = null ;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras) ;
            while (rs != null && rs.next())
            {
                String rightID = rs.getString("rightID") ;
                RightVO rightVO = RightDAO.getInstance().getRightVOByID(
                    rightID) ;
                if (rightVO != null)
                {
                    list.add(rightVO) ;
                }
                else
                {
                    logger.error("a right[" + rightID +
                                 "]not found!may be rust data!") ;
                }
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getRoleRights error.", e) ;
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close() ;
                }
                catch (Exception e)
                {
                    logger.error(e) ;
                }
            }
        }
        return list ;

    }
}
