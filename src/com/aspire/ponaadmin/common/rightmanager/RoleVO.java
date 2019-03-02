package com.aspire.ponaadmin.common.rightmanager ;

/**
 *
 * <p>Title: </p>
 * <p>Description:  ��ɫ����VO��</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:aspire </p>
 * @author zhangmin
 * @version 1.0
 */
public class RoleVO
{

   /**
    * ��ɫID
    */
   private String roleID;

   /**
    * ��ɫ��
    */
   private String name;

   /**
    * ��ɫ����
    */
   private String desc;

   /**
    * ��ɫ����ʡ�����Ƕ���÷ֺŷֿ�
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
     * ���췽��
     */
    public RoleVO ()
    {
    }

    /**
     * ��ɫ��ϢVO��toString����
     * @return String �û���Ϣ
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
     * ����equals���������rightIDһ��������ȡ�
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
