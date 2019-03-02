package com.aspire.ponaadmin.common.rightmanager ;

/**
 * <p>角色权限VO类</p>
 * <p>角色权限VO类，封装了角色权限</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightVO
{

    /**
     * 权限ID
     */
    protected String rightID ;

    /**
     * 权限名称
     */
    protected String name ;

    /**
     * 权限描述
     */
    protected String desc ;

    /**
     * 权限类型
     */
    protected int type;

    /**
     * 父权限的ID，如果没有父权限，就为null
     */
    protected String parentID;

    public String getDesc ()
    {
        return desc ;
    }

    public String getName ()
    {
        return name ;
    }

    public void setRightID (String rightID)
    {
        this.rightID = rightID ;
    }

    public void setDesc (String desc)
    {
        this.desc = desc ;
    }

    public void setName (String name)
    {
        this.name = name ;
    }

    public String getRightID ()
    {
        return rightID ;
    }

    public int getType()
    {
        return this.type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void setParentID(String _parentID)
    {
        this.parentID = _parentID;
    }

    public String getParentID()
    {
        return this.parentID;
    }

    /**
     * 构造方法
     */
    public RightVO ()
    {
    }

    /**
     * 覆盖equals方法，如果rightID一样，就相等。
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof RightVO)
        {
            if(((RightVO) obj).getRightID().equals(this.rightID))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查权限。所有其它类型的权限，如果检查规则不一样，都必须覆盖本方法。
     * @param id String 目标权限id
     * @return int 检查结果，都在RightManagerConstant里面定义着
     */
    public int checkRight(String id)
    {
        if(this.rightID .equals(id))
        {
            return RightManagerConstant.RIGHTCHECK_PASSED;
        }
        return RightManagerConstant.RIGHTCHECK_NO_RIGHT;
    }
}
