package com.aspire.ponaadmin.common.rightmanager ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * <p>角色相关的DAO类</p>
 * <p>权限相关的DAO类，负责提供查询，增加，删除，修改等方法</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RoleDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(RoleDAO.class);

    /**
     * 私有构造方法
     */
    private RoleDAO()
    {
    }

    /**
     * 将查询结果释放到VO中
     * @param role RoleVO
     * @param rs ResultSet，查询结果
     */
    public static void getRoleVOFromRS(RoleVO role, ResultSet rs) throws SQLException
    {
        role.setRoleID(rs.getString("ROLEID"));
        role.setName(rs.getString("NAME"));
        role.setDesc(rs.getString("DESCS"));
        role.setProvinces(rs.getString("Provinces"));
    }

    /**
     * 添加角色到数据库
     * 
     * @param role RoleVO，要添加的角色
     */
    static final void addRole(RoleVO role)  throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("addRole("+role+")");
        }
        String sqlCode = "rightmanager.RoleDAO.addRole().INSERT";
        //定义在sql语句中要替换的参数,
        //增加省参数penglq－20060224
        Object[] paras = {role.getName(), role.getDesc(),role.getProvinces()};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 在数据库删除角色
     * @param roleID String，要删除的角色
     */
    static final void delRole(String roleID) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("delRole("+roleID+")");
        }
        String sqlCode = "rightmanager.RoleDAO.delRole().DELETE";
        Object[] paras = {roleID};
        //定义在sql语句中要替换的参数
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 获取系统所有角色列表
     * @return ArrayList
     */
    static final ArrayList getAllRole() throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllRole()");
        }
        String sqlCode = "rightmanager.RoleDAO.getAllRole().SELECT";
        Object[] paras = null;
        ResultSet rs = null;
        ArrayList list = new ArrayList();
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while(rs != null && rs.next())
            {
                RoleVO roleVO = new RoleVO();
                getRoleVOFromRS(roleVO, rs);
                list.add(roleVO);
            }
        }
        catch(SQLException e)
        {
            throw new DAOException("getRoleByID error.", e);

        }
        finally
        {
            if(rs != null)
            {
                try{
                    rs.close();
                }
                    catch(Exception e){
                        logger.error(e);
                    }
            }

        }
        return list;
    }

    /**
     * 搜索角色的方法，实现分页
     * @param page PageResult，分页器
     * @param name String，名称条件
     * @param desc String，描述条件
     * @param rightType int，权限类型
     * @param rightID String，权限id
     * @throws BOException
     */
    static final void searchRole (PageResult page, String name, String desc,
                                  int rightType, String rightID) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("searchRole(" + page + "," + name + "," + desc + "," +
                         rightType + "," + rightID + ")") ;
        }
        StringBuffer sql = new StringBuffer() ;
        List paras = new ArrayList();
        //构造搜索的sql和参数
        sql.append("select * from t_role where 1=1");
        if(!name.equals(""))
        {
            sql.append(" and name like ?");
            paras.add("%" + name + "%") ;
        }
        if(!desc.equals(""))
        {
            sql.append(" and descs like ?");
            paras.add("%" + desc + "%") ;
        }
        if(rightType!=0)
        {
            if(rightType == RightManagerConstant.TYPE_RIGHT)
            {
                sql.append(" and roleid in ");
                sql.append("(select roleid from t_roleright where RIGHTID in");
                sql.append(" (SELECT RIGHTID FROM t_right START WITH RIGHTID=? CONNECT BY PRIOR parentid = RIGHTID))");
                paras.add(rightID) ;
            }
            else if(rightType == RightManagerConstant.TYPE_SITE)
            {
                sql.append(" and roleid in ");
                sql.append("(select roleid from t_rolesiteright where NODEID in");
                sql.append(" (SELECT id FROM t_node START WITH id=? CONNECT BY PRIOR parentid = id))");
                paras.add(rightID) ;
            }
            else
            {
                //未知类型，记录错误日志
                logger.error("unknown rightType[" + rightType + "[ found! ") ;
            }
        }
        sql.append(" order by name");

        page.excute(sql.toString(), paras.toArray(), new RolePageVO()) ;

    }

    /**
     * 根据ID获取对应的角色
     * @param roleID String，角色ID
     * @return RoleVO
     */
    static final RoleVO getRoleByID(String roleID) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getRoleByID("+roleID+")");
        }
        String sqlCode = "rightmanager.RoleDAO.getRoleByID().SELECT";
        //定义在sql语句中要替换的参数
        Object[] paras = {roleID};
        ResultSet rs = null;
        RoleVO roleVO = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if(rs != null && rs.next())
            {
                //将查询结果放到vo中
                roleVO = new RoleVO() ;
                getRoleVOFromRS(roleVO, rs) ;
            }
        }
        catch(SQLException e)
        {
            throw new DAOException("getRoleByID error.", e);

        }
        finally
        {
            if(rs != null)
            {
                try{
                    rs.close();
                }
                    catch(Exception e){
                        logger.error(e);
                    }
            }

        }
        return roleVO;
    }

    /**
     * 根据角色名称查找
     * @param name String，角色名称
     * @return List
     */
    static final List getRoleByName(String name) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getRoleByName("+name+")");
        }
        String sqlCode = "rightmanager.RoleDAO.getRoleByName().SELECT";
        //定义在sql语句中要替换的参数
        Object[] paras = {"%"+name+"%"};
        List list = new ArrayList();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while(rs != null && rs.next())
            {
                //将查询结果放到vo中
                RoleVO roleVO = new RoleVO() ;
                getRoleVOFromRS(roleVO, rs) ;
                list.add(roleVO) ;
            }
        }
        catch(SQLException e)
        {
            throw new DAOException("getRoleByName error.", e);

        }
        finally
        {
            if(rs != null)
            {
                try{
                    rs.close();
                }
                    catch(Exception e){
                        logger.error(e);
                    }
            }

        }
        return list;
    }

    /**
     * 在数据库修改角色
     * @param role RoleVO，要更新的角色信息
     */
    static final void modRole(RoleVO role) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("modRole("+role+")");
        }
        String sqlCode = "rightmanager.RoleDAO.modRole().UPDATE";
        //定义在sql语句中要替换的参数
        Object[] paras = {role.getName(), role.getDesc(),role.getProvinces(), new Integer(role.getRoleID())};
        DB.getInstance().executeBySQLCode(sqlCode, paras);

    }


    /**
     * 查询系统角色列表
     * @param page PageResult 分页对象
     * @param name String 角色名称
     */
    static final void getAllRole (PageResult page, String name) throws DAOException
    {
        logger.debug("getAllRole(" + page + "," + name + ")");
        if(null == name || "".equals(name))
        {
            String sqlCode = "rightmanager.RoleDAO.getAllRole().SELECT";
            page.excuteBySQLCode(sqlCode, null, new RolePageVO());
        }
        else
        {
            String sqlCode = "rightmanager.RoleDAO.getRoleByName().SELECT";
            Object[] paras = {"%"+name+"%"};
            page.excuteBySQLCode(sqlCode, paras, new RolePageVO());
        }
    }

}
