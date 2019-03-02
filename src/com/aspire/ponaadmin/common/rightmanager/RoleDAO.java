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
 * <p>��ɫ��ص�DAO��</p>
 * <p>Ȩ����ص�DAO�࣬�����ṩ��ѯ�����ӣ�ɾ�����޸ĵȷ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RoleDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(RoleDAO.class);

    /**
     * ˽�й��췽��
     */
    private RoleDAO()
    {
    }

    /**
     * ����ѯ����ͷŵ�VO��
     * @param role RoleVO
     * @param rs ResultSet����ѯ���
     */
    public static void getRoleVOFromRS(RoleVO role, ResultSet rs) throws SQLException
    {
        role.setRoleID(rs.getString("ROLEID"));
        role.setName(rs.getString("NAME"));
        role.setDesc(rs.getString("DESCS"));
        role.setProvinces(rs.getString("Provinces"));
    }

    /**
     * ��ӽ�ɫ�����ݿ�
     * 
     * @param role RoleVO��Ҫ��ӵĽ�ɫ
     */
    static final void addRole(RoleVO role)  throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("addRole("+role+")");
        }
        String sqlCode = "rightmanager.RoleDAO.addRole().INSERT";
        //������sql�����Ҫ�滻�Ĳ���,
        //����ʡ����penglq��20060224
        Object[] paras = {role.getName(), role.getDesc(),role.getProvinces()};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * �����ݿ�ɾ����ɫ
     * @param roleID String��Ҫɾ���Ľ�ɫ
     */
    static final void delRole(String roleID) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("delRole("+roleID+")");
        }
        String sqlCode = "rightmanager.RoleDAO.delRole().DELETE";
        Object[] paras = {roleID};
        //������sql�����Ҫ�滻�Ĳ���
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * ��ȡϵͳ���н�ɫ�б�
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
     * ������ɫ�ķ�����ʵ�ַ�ҳ
     * @param page PageResult����ҳ��
     * @param name String����������
     * @param desc String����������
     * @param rightType int��Ȩ������
     * @param rightID String��Ȩ��id
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
        //����������sql�Ͳ���
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
                //δ֪���ͣ���¼������־
                logger.error("unknown rightType[" + rightType + "[ found! ") ;
            }
        }
        sql.append(" order by name");

        page.excute(sql.toString(), paras.toArray(), new RolePageVO()) ;

    }

    /**
     * ����ID��ȡ��Ӧ�Ľ�ɫ
     * @param roleID String����ɫID
     * @return RoleVO
     */
    static final RoleVO getRoleByID(String roleID) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getRoleByID("+roleID+")");
        }
        String sqlCode = "rightmanager.RoleDAO.getRoleByID().SELECT";
        //������sql�����Ҫ�滻�Ĳ���
        Object[] paras = {roleID};
        ResultSet rs = null;
        RoleVO roleVO = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if(rs != null && rs.next())
            {
                //����ѯ����ŵ�vo��
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
     * ���ݽ�ɫ���Ʋ���
     * @param name String����ɫ����
     * @return List
     */
    static final List getRoleByName(String name) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getRoleByName("+name+")");
        }
        String sqlCode = "rightmanager.RoleDAO.getRoleByName().SELECT";
        //������sql�����Ҫ�滻�Ĳ���
        Object[] paras = {"%"+name+"%"};
        List list = new ArrayList();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while(rs != null && rs.next())
            {
                //����ѯ����ŵ�vo��
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
     * �����ݿ��޸Ľ�ɫ
     * @param role RoleVO��Ҫ���µĽ�ɫ��Ϣ
     */
    static final void modRole(RoleVO role) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("modRole("+role+")");
        }
        String sqlCode = "rightmanager.RoleDAO.modRole().UPDATE";
        //������sql�����Ҫ�滻�Ĳ���
        Object[] paras = {role.getName(), role.getDesc(),role.getProvinces(), new Integer(role.getRoleID())};
        DB.getInstance().executeBySQLCode(sqlCode, paras);

    }


    /**
     * ��ѯϵͳ��ɫ�б�
     * @param page PageResult ��ҳ����
     * @param name String ��ɫ����
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
