package com.aspire.ponaadmin.common.rightmanager ;

import java.util.List ;

import com.aspire.common.db.DAOException;

/**
 * <p>操作权限模型类</p>
 * <p>继承于权限模型，实现操作权限的相关操作</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightModel extends AbstractRightModel
{

    /**
     * singleton模式的实例
     */
    private static RightModel instance = new RightModel();

    /**
     * 构造方法，由singleton模式调用
     */
    private RightModel ()
    {
        //权限的类型定义：操作类型权限
        this.type = 1;
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static RightModel getInstance()
    {
        return instance;
    }

    /**
     *
     * @param roleID String 角色的ID
     * @return List 权限列表
     * @todo Implement this
     *   com.aspire.ponaadmin.common.rightmanager.RightModel method
     */
    public List getRoleRight (String roleID)
    {
        List list = null;
        try
        {
            list = RoleRightDAO.getInstance().getRoleRights(roleID);
        }
        catch (DAOException e)
        {
            
        }
        return list;
    }

    /**
     *
     * @param roleID String 角色的权限
     * @param rightIDList List 权限ID的列表
     * @todo Implement this
     *   com.aspire.ponaadmin.common.rightmanager.RightModel method
     */
    public void setRoleRight (String roleID, List rightIDList)
    {
        try
        {
            RoleRightDAO.getInstance().setRoleRights(roleID, rightIDList);
        }
        catch (DAOException e)
        {

        }
    }
}
