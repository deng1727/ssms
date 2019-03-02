package com.aspire.ponaadmin.common.rightmanager ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.usermanager.UserDAO;
import com.aspire.ponaadmin.common.usermanager.UserVO;

/**
 * <p>�û���ɫ��DAO��</p>
 * <p>�û���ɫ��DAO��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class UserRoleDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(UserRoleDAO.class);

    /**
     * ���췽������singletonģʽ����
     */
    private UserRoleDAO ()
    {
    }

	/**
	 * singletonģʽ��ʵ��
	 */
    private static UserRoleDAO urDAO = new UserRoleDAO();

	/**
	 * ��ȡʵ��
	 * @return ʵ��
	 */
    static final UserRoleDAO getInstance()
    {
        return urDAO;
    }

    /**
     * ��ȡ�û����еĽ�ɫ
     * @param userID String �û�ID
     * @return List
     */
    final List getUserRoles(String userID) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getUserRoles("+userID+")");
        }
        String sqlCode = "rightmanager.UserRoleDAO.getUserRoles().SELECT";
        Object[] paras = {userID};
        List list = new ArrayList();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while(rs != null && rs.next())
            {
                RoleVO roleVO = new RoleVO() ;
                RoleDAO.getRoleVOFromRS(roleVO, rs) ;
                list.add(roleVO) ;
            }
        }
        catch(SQLException e)
        {
            throw new DAOException("getUserRoles error.", e);

        }
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                } catch(Exception e)
                {
					logger.error(e);
                }
            }

        }
        return list;
    }

    /**
     * �����û����еĽ�ɫ
     * @param userID String,�û�ID
     * @param roleIDList List����ɫID�б�
     */
    final void setUserRoles(String userID, List roleIDList) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("setUserRoles("+userID+roleIDList+")");
        }
        String[] mutiSQL = new String[roleIDList.size()+1];
        Object[][] mutiparas = new Object[roleIDList.size()+1][];
        String sqlCodeAddByUserID = "rightmanager.UserRoleDAO.setUserRoles().INSERT";
        String sqlCodeDelByUserID = "rightmanager.UserRoleDAO.delUserRoles().DELETE";
        Collection c = roleIDList;
        Iterator it = c.iterator();
        String sqlAddByUserID = null ;
        try
        {
            sqlAddByUserID = SQLCode.getInstance().getSQLStatement(sqlCodeAddByUserID) ;
        }
        catch (DataAccessException ex)
        {
            throw new DAOException(ex);
        }
        String sqlDelByUserID = null;
        try
        {
            sqlDelByUserID = SQLCode.getInstance().getSQLStatement(sqlCodeDelByUserID);
        }
        catch (DataAccessException ex)
        {
            throw new DAOException(ex);
        }
        int i = 0;
        mutiSQL[i] = sqlDelByUserID;
        Object[] parasDel = {userID};
        mutiparas[i] = parasDel;
        i += 1;
        while(it.hasNext())
        {
            mutiSQL[i] = sqlAddByUserID;
            Object[] paras = {userID, it.next()};
            mutiparas[i] = paras;
            i += 1;
        }
        DB.getInstance().executeMuti(mutiSQL, mutiparas);
    }

    /**
     * ��������ĳ����ɫ��һ���û�
     * @param roleID String����ɫID
     * @param userIDList List���û�ID�б�
     */
    final void setRoleUsers(String roleID, List userIDList) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("setRoleUsers("+roleID+userIDList+")");
        }
        String[] mutiSQL = new String[userIDList.size()+1];
        Object[][] mutiparas = new Object[userIDList.size()+1][];
        String sqlCodeAddByRoleID = "rightmanager.UserRoleDAO.setUserRoles().INSERT";
        String sqlCodeDelByRoleID = "rightmanager.UserRoleDAO.delRoleUsers().DELETE";
        Collection c = userIDList;
        Iterator it = c.iterator();
        String sqlAddByRoleID = null ;
        try
        {
            sqlAddByRoleID = SQLCode.getInstance().getSQLStatement(sqlCodeAddByRoleID) ;
        }
        catch (DataAccessException ex)
        {
            throw new DAOException(ex);
        }
        String sqlDelByRoleID = null;
        try
        {
            sqlDelByRoleID = SQLCode.getInstance().getSQLStatement(sqlCodeDelByRoleID);
        }
        catch (DataAccessException ex)
        {
            throw new DAOException(ex);
        }
        int i = 0;
        mutiSQL[i] = sqlDelByRoleID;
        Object[] parasDel = {roleID};
        mutiparas[i] = parasDel;
        i += 1;
        while(it.hasNext())
        {
            mutiSQL[i] = sqlAddByRoleID;
            Object[] paras = {it.next(), roleID};
            mutiparas[i] = paras;
            i += 1;
        }
        DB.getInstance().executeMuti(mutiSQL, mutiparas);
    }

    /**
     * �޸�����ĳ����ɫ���û�������n����ȥ��n����
     * @param roleID String����ɫID
     * @param newUserIDs String[]�����ӵ�n��
     * @param delUserIDs String[]��ȥ����n��
     */
    final void modRoleUsers (String roleID, String[] newUserIDs,
                             String[] delUserIDs) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("modRoleUsers(" + roleID + ")") ;
        }
        int count = newUserIDs.length + delUserIDs.length ;
        String[] mutiSQLCode = new String[count] ;
        Object[][] mutiparas = new Object[count][] ;
        String sqlCodeAddByRoleID =
            "rightmanager.UserRoleDAO.setUserRoles().INSERT" ;
        String sqlCodeDelByRoleID =
            "rightmanager.UserRoleDAO.modRoleUsers().DELETE" ;
        for (int i = 0 ; i < delUserIDs.length ; i++)
        {
            mutiSQLCode[i] = sqlCodeDelByRoleID ;
            Object[] parasDel = {delUserIDs[i], roleID} ;
            mutiparas[i] = parasDel ;
        }
        int pos = delUserIDs.length ;
        for (int i = 0 ; i < newUserIDs.length ; i++)
        {
            mutiSQLCode[pos + i] = sqlCodeAddByRoleID ;
            Object[] parasAdd = {newUserIDs[i], roleID} ;
            mutiparas[pos + i] = parasAdd ;
        }
        DB.getInstance().executeMutiBySQLCode(mutiSQLCode, mutiparas) ;
    }

    /**
     * ��ȡ����ĳ����ɫ��һ���û�
     * @param roleID String,��ɫID
     * @return List
     */
    final List getRoleUsers(String roleID) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getRoleUsers("+roleID+")");
        }
        String sqlCode = "rightmanager.UserRoleDAO.getRoleUsers().SELECT";
        Object[] paras = {roleID};
        List list = new ArrayList();
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        try
        {
            while(rs != null && rs.next())
            {
                UserVO user = new UserVO() ;
                UserDAO.getUserVOFromRS(user, rs) ;
                list.add(user) ;
            }
        }
        catch(SQLException e)
        {
            throw new DAOException("getRoleUsers error.", e);

        }
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                } catch(Exception e)
                {
					logger.error(e);
                }
            }

        }
        return list;

    }
}
