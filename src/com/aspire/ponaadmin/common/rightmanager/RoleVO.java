package com.aspire.ponaadmin.common.rightmanager ;

/**
 *
 * <p>Title: </p>
 * <p>Description:  角色管理VO类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:aspire </p>
 * @author zhangmin
 * @version 1.0
 */
public class RoleVO
{

   /**
    * 角色ID
    */
   private String roleID;

   /**
    * 角色名
    */
   private String name;

   /**
    * 角色描述
    */
   private String desc;

   /**
    * 角色归属省，如是多个用分号分开
    */
   private String provinces;


    public String getDesc ()
    {
        return desc ;
    }

    public String getName ()
    {
        return name ;
    }

    public void setRoleID (String roleID)
    {
        this.roleID = roleID ;
    }

    public void setDesc (String desc)
    {
        this.desc = desc ;
    }

    public void setName (String name)
    {
        this.name = name ;
    }

    public String getRoleID ()
    {
        return roleID ;
    }

    /**
     * 构造方法
     */
    public RoleVO ()
    {
    }

    /**
     * 角色信息VO的toString方法
     * @return String 用户信息
     */
    public String toString()
    {

        StringBuffer sb = new StringBuffer();
        sb.append("roleVO{");
        sb.append(roleID);
        sb.append(",");
        sb.append(name);
        sb.append(",");
        sb.append(desc);
        sb.append(",");
        sb.append(provinces);
        sb.append("}");
        return sb.toString();
    }

    /**
     * 覆盖equals方法，如果rightID一样，就相等。
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof RoleVO)
        {
            if(((RoleVO) obj).getRoleID().equals(this.roleID))
            {
                return true;
            }
        }
        return false;

    }
    public String getProvinces() {
        return provinces;
    }
    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

}
