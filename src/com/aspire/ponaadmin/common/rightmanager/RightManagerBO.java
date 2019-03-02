package com.aspire.ponaadmin.common.rightmanager ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;

/**
 * <p>权限管理组件BO类</p>
 * <p>权限管理组件BO类，提供外部调用的全部接口</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightManagerBO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(RightManagerBO.class);

    /**
     * 用来保存AbstractRightModel模型的map，key是模型的类型
     */
    private HashMap rightModelMap = new HashMap();

    /**
     * singleton模式的实例
     */
    private static RightManagerBO instance = new RightManagerBO();

    /**
     * 构造方法，由singleton模式调用
     */
    private RightManagerBO ()
    {
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static RightManagerBO getInstance()
    {
        return instance;
    }

    /**
     * 注册权限模型，应该在初始化的时候调用。
     * @param model AbstractRightModel
     */
    public void registerRightModel(AbstractRightModel model)
    {
        logger.debug("registerRightModel(" + model.getType() + ")") ;
        //可能需要同步，但是考虑到这些都是在初始化时候调用的，所以不加同步。
        this.rightModelMap.put(new Integer(model.getType()), model);
    }

    /**
     * 添加角色到数据库
     * @param role RoleVO，要添加的角色
     * @throws BOException
     */

    public void addRole (RoleVO role)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addRole(" + role + ")") ;
        }
        if (role == null)
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            String newName = role.getName();
            List list = getRoleByName(newName);
            for(int i = 0; i < list.size(); i++)
            {
                RoleVO vo = (RoleVO) list.get(i);
                if(newName.equals(vo.getName()))
                {
                    throw new  BOException("角色已经存在", RightManagerConstant.ROLE_NAME_EXIST);
                }

            }
            RoleDAO.addRole(role) ;

        }
        catch (DAOException e)
        {
            throw new BOException("addRole error", e) ;
        }
    }

    /**
     * 删除角色
     * @param roleID String 要删除的角色ID
     * @return int 返回结果码，见ErrorCode类，SUCC成功，FAIL内部错误，ROLE_USED角色被使用了
     * @throws BOException
     */
    public int delRole (String roleID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delRole(" + roleID + ")") ;
        }
        if (roleID == null || roleID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            //如果角色被使用了，不能删除。
            if(UserRoleDAO.getInstance().getRoleUsers(roleID).size() != 0)
            {
                return RightManagerConstant.ROLE_USED;
            }
            RoleDAO.delRole(roleID) ;
        }
        catch (DAOException e)
        {
            logger.error(e);
            return RightManagerConstant.FAIL;
        }
        return RightManagerConstant.SUCC;
    }

    /**
     * 获取系统所有角色列表
     * @return List 角色列表
     * @throws BOException
     */
    public List getAllRole ()
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllRole()") ;
        }
        try
        {
            return RoleDAO.getAllRole() ;
        }
        catch (DAOException e)
        {
            throw new BOException("getAllRole error", e) ;
        }
    }


    /**
     * 获取系统所有角色列表，实现分页
     * @param page PageResult 分页对象
     * @param name String 角色名称
     * @throws BOException
     */
    public void getAllRole (PageResult page, String name)
        throws BOException
    {
        logger.debug("getAllRole(" + page + ")") ;
        if(page == null)
        {
                throw new BOException("page is null!", RightManagerConstant.INVALID_PARA);
        }
        try
        {
            RoleDAO.getAllRole(page, name) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getAllRole error", e) ;
        }
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
    public void searchRole (PageResult page, String name, String desc,
                            int rightType, String rightID)
        throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("searchRole(" + page + "," + name + "," + desc + "," +
                         rightType + "," + rightID + ")") ;
        }
        if(page == null)
        {
                throw new BOException("page is null!", RightManagerConstant.INVALID_PARA);
        }
        try
        {
            RoleDAO.searchRole(page, name,desc,rightType,rightID) ;
        }
        catch (DAOException e)
        {
            throw new BOException("searchRole error", e) ;
        }
    }

    /**
     * 根据ID获取对应的角色
     * @param roleID String,角色ID
     * @return RoleVO
     * @throws BOException
     */
    public RoleVO getRoleByID (String roleID)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRoleByID(" + roleID + ")") ;
        }
        if (roleID == null || roleID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return RoleDAO.getRoleByID(roleID) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getRoleByID error", e) ;
        }
    }

    /**
     * 根据角色名称查找
     * @param name String,角色名称
     * @return list
     * @throws BOException
     */
    public List getRoleByName (String name)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRoleByName(" + name + ")") ;
        }
        if (name == null || name.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return RoleDAO.getRoleByName(name) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getRoleByName error", e) ;
        }
    }

    /**
     * 修改角色信息
     * @param role RoleVO，要更新的角色信息
     * @throws BOException
     */
    public void modRoleInfo (RoleVO role)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("modRoleInfo(" + role + ")") ;
        }
        if ((role == null) || (role.getRoleID() == null) || role.getRoleID().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            String newName = role.getName();
            String id = role.getRoleID();
            List list = getRoleByName(newName);
            for(int i = 0; i < list.size(); i++)
            {
                RoleVO vo = (RoleVO) list.get(i);
                if(newName.equals(vo.getName()) && !id.equals(vo.getRoleID()))
                {
                    throw new  BOException("角色已经存在", RightManagerConstant.ROLE_NAME_EXIST);
                }
            }
            RoleDAO.modRole(role) ;
       }
        catch (DAOException e)
        {
            throw new BOException("modRole error", e) ;
        }

    }

    /**
     * 设置用户具有的角色
     * @param userID String,用户ID
     * @param roleIDList List,角色ID列表
     * @throws BOException
     */
    public void setUserRoles (String userID, List roleIDList)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setUserRoles(" + userID + roleIDList + ")") ;
        }
        if ((userID == null) || userID.trim().equals("") || (roleIDList == null))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            UserRoleDAO.getInstance().setUserRoles(userID, roleIDList) ;
        }
        catch (DAOException e)
        {
            throw new BOException("setUserRoles error", e) ;
        }
    }

    /**
     * 获取用户具有的角色
     * @param userID String,用户ID
     * @return List
     * @throws BOException
     */
    public List getUserRoles (String userID)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getUserRoles(" + userID + ")") ;
        }
        if (userID == null || userID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return UserRoleDAO.getInstance().getUserRoles(userID) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getUserRoles error", e) ;
        }
    }

    /**
     * 设置属于某个角色的一批用户
     * @param roleID String,角色ID
     * @param userIDList List,用户ID列表
     * @throws BOException
     */
    public void setRoleUsers (String roleID, List userIDList)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setRoleUsers(" + roleID + ")") ;
        }
        if ((roleID == null) || roleID.trim().equals("") || (userIDList == null))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            UserRoleDAO.getInstance().setRoleUsers(roleID, userIDList) ;
        }
        catch (DAOException e)
        {
            throw new BOException("setRoleUsers error", e) ;
        }
    }

    /**
     * 修改属于某个角色的用户，增加n个并去掉n个。
     * @param roleID String，角色ID
     * @param newUserIDs String[]，增加的n个
     * @param delUserIDs String[]，去掉的n个
     * @throws BOException
     */
    public void modRoleUsers (String roleID, String[] newUserIDs,
                              String[] delUserIDs)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("modRoleUsers(" + roleID + ")") ;
        }
        if ((roleID == null) || roleID.trim().equals("") || (newUserIDs == null) ||
            (delUserIDs == null))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }
        try
        {
            UserRoleDAO.getInstance().modRoleUsers(roleID, newUserIDs,
                                                   delUserIDs) ;
        }
        catch (DAOException e)
        {
            throw new BOException("modRoleUsers error", e) ;
        }
    }

    /**
     * 获取属于某个角色的一批用户
     * @param roleID String,角色ID
     * @return List
     * @throws BOException
     */
    public List getRoleUsers (String roleID)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRoleUsers(" + roleID + ")") ;
        }
        if (roleID == null || roleID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return UserRoleDAO.getInstance().getRoleUsers(roleID) ;
        }
        catch (DAOException e)
        {
            throw new BOException("getRoleUsers error", e) ;
        }
    }

    /**
     * 获取角色具有的权限
     * @param roleID String,角色ID
     * @param type int，权限类型
     * @return List
     * @throws BOException
     */
    public List getRoleRights (String roleID, int type)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRoleRights(" + roleID + "," + type + ")") ;
        }
        if (roleID == null || roleID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }
        List rightList = null;
        try
        {
            AbstractRightModel model = (AbstractRightModel) this.rightModelMap.
                get(new Integer(type)) ;
            //如果没有支持的权限模型，就抛出异常
            if(model == null)
            {
                throw new BOException("right type [" + type + "] not support!");
            }
            rightList = model.getRoleRight(roleID);
        }
        catch (Exception e)
        {
            throw new BOException("getRoleRights error", e);
        }
        return rightList;
    }

    /**
     * 设置角色具有的权限
     * @param roleID String,角色ID
     * @param type int,权限类型1：操作权限2：站点目录权限。
     * @param IDList List,权限列表，里面保存权限ID。如果type为1，则存放rightID列表；如果type为2，则存放nodeID列表。
     * @throws BOException
     */
    public void setRoleRights (String roleID, int type, List IDList)
        throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setRoleRights(" + roleID + type + IDList + ")") ;
        }
        if ((roleID == null) || roleID.trim().equals("") || (IDList == null) ||
            ((type != 1) && (type != 2)))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }
        AbstractRightModel model = ( AbstractRightModel ) this.rightModelMap.get(new Integer(type));
        // 如果没有支持的权限模型，就抛出异常
        if (model == null)
        {
            throw new BOException("right type [" + type + "] not support!");
        }
        model.setRoleRight(roleID, IDList);
    }

    /**
     * 获取一个http请求uri对应页面所属于的权限
     * 
     * @param pageURI http请求uri
     * @return 页面对应的权限
     * @throws BOException
     */
    public RightVO getRightOfPageURI(String pageURI) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRightOfPageURI(" + pageURI + ")") ;
        }

        if (pageURI == null || pageURI.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", RightManagerConstant.INVALID_PARA) ;
        }

        try
        {
            return RightDAO.getInstance().getRightOfPageURI(pageURI);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("获取一个http请求uri对应页面所属于的权限失败！",e);
        }
    }

    /**
     * 获取系统所有权限列表。不包含站点目录权限。
     * @return list,权限列表，找不到为null
     * @throws BOException
     */

    public List getAllRight() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllRights") ;
        }
        try
        {
            return RightDAO.getInstance().getAllRight();
        }
        catch (DAOException e)
        {
            throw new BOException("getAllRights error", e) ;
        }
    }

    /**
     * 根据权限id获取权限对象
     * @param rightID String 权限id
     * @return RightVO 权限对象
     * @throws BOException
     */
    public RightVO getRightByID(String rightID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRightByID("+rightID+")") ;
        }
        if(rightID == null || rightID.trim().equals(""))
        {
            throw new BOException("invalid parameter!",
                                  RightManagerConstant.INVALID_PARA) ;
        }
        try
        {
            return RightDAO.getInstance().getRightVOByID(rightID);
        }
        catch(Exception e)
        {
            throw new BOException("getRightByID failed", e);
        }
    }

    /**
     * 获取用户的所有权限
     * @param userID String 用户帐号ID
     * @return HashMap 用户的权限hash表
     * @throws BOException
     */
    public List getUserRights(String userID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getUserRights("+userID+")") ;
        }

        List roleList = this.getUserRoles(userID);
        List allRightList = new ArrayList();

        //取出所有的权限模型AbstractRightModel
        Object[] models = this.rightModelMap.values().toArray();

        for(int i = 0; i < roleList.size(); i++)
        {
            RoleVO role = (RoleVO) roleList.get(i);
            for(int k = 0; k < models.length; k++)
            {
                int rightType = ((AbstractRightModel) models[k]).getType();
                List rightList = this.getRoleRights(role.getRoleID(), rightType);
                allRightList.addAll(rightList);
            }
        }
        return allRightList;
    }

    /**
     * 检查用户是否拥有进行某个操作的权限
     * @param userSession UserSessionVO 用户信息
     * @param rightID String 权限id
     * @return int 具体值代表的意义请参见RightManagerConstant类
     * @throws BOException
     * @see com.aspire.ponaadmin.common.rightmanager.RightManagerConstant
     */
    public int checkUserRight(UserSessionVO userSession, String rightID) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("checkUserRight(" + rightID + ")") ;
        }
        //先检查是否可以获取到用户权限信息
        if ((userSession == null) || (userSession.getRights() == null))
        {
            //没有用户权限信息
            return RightManagerConstant.RIGHTCHECK_NO_USERINFO;
        }

        //判断是否具有权限
        List rightList = userSession.getRights();
        for(int i = 0; i < rightList.size(); i++)
        {
            RightVO right = (RightVO) rightList.get(i);
            if(right.checkRight(rightID) == RightManagerConstant.RIGHTCHECK_PASSED)
            {
                return RightManagerConstant.RIGHTCHECK_PASSED;
            }
        }

        //走到这里说明没有权限
        return RightManagerConstant.RIGHTCHECK_NO_RIGHT;
    }
}
