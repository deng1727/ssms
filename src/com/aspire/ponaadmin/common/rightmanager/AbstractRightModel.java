package com.aspire.ponaadmin.common.rightmanager ;

import java.util.List;

/**
 * <p>权限类型的抽象模型</p>
 * <p>各种具体类型的权限类型（比如操作权限）必须继承本类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public abstract class AbstractRightModel
{

    /**
     * 权限的类型定义
     */
    protected int type;

    /**
     * 获取权限类型
     * @return int
     */
    public int getType()
    {
        return this.type;
    }

    /**
     * 判断某种类型的权限是否是本类型
     * @param _type int 要判断的类型
     * @return boolean true:是，flase:不是
     */
    public boolean isThisType(int _type)
    {
        return this.type == _type;
    }

    /**
     * 获取一个角色的某种类型的权限
     * @param roleID String 角色的ID
     * @return List 权限列表
     */
    public abstract List getRoleRight(String roleID);

    /**
     * 设置一个角色的某种类型的权限
     * @param roleID String 角色的权限
     * @param rightIDList List 权限ID的列表
     */
    public abstract void setRoleRight(String roleID, List rightIDList);

}
