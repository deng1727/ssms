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
 * <p>��ɫȨ����ص�DAO��</p>
 * <p>��ɫȨ����ص�DAO�࣬������ݽ�ɫid��ѯȨ�޺��趨Ȩ��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RoleRightDAO
{

    /**
     * ���췽������singletonģʽ����
     */
    public RoleRightDAO ()
    {
    }

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(RoleRightDAO.class) ;

	/**
	 * singletonģʽ��ʵ��
	 */
    private static RoleRightDAO roleRightDAO = new RoleRightDAO() ;

	/**
	 * ��ȡʵ��
	 * @return ʵ��
	 */
    static final RoleRightDAO getInstance ()
    {
        return roleRightDAO ;
    }

    /**
     * ���ý�ɫ���е�Ȩ��
     * @param roleID String,��ɫID
     * @param IDList List,Ȩ���б����汣��Ȩ��ID��
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
     * ��ȡ��ɫ���е�Ȩ��
     * @param roleID String,��ɫID
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
