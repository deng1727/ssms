package com.aspire.ponaadmin.common.usermanager ;

import java.util.List;
import com.aspire.ponaadmin.common.rightmanager.RoleVO;

/**
 * <p>用户个人信息VO类</p>
 * <p>用户个人信息VO类，封装了用户个人信息</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserVO
{

    /**
     * 用户帐号ID
     */
	private String userID ;

	/**
	 * 密码
	 */
    private String password ;

	/**
	 * 用户名
	 */
    private String name ;

	/**
	 * 用户状态，具体的值意义请看本类的常量定义
	 */
    private int state ;

	/**
	 * 性别
	 */
    private String sex ;

	/**
	 * 生日
	 */
    private String birthday ;

	/**
	 * 证件类型
	 */
    private String certType ;

	/**
	 * 证件号码
	 */
    private String certID ;

	/**
	 * 公司名称
	 */
    private String companyName ;

	/**
	 * 公司地址
	 */
    private String companyAddr ;

	/**
	 * 邮政编码
	 */
    private String postcode ;

	/**
	 * 联系电话
	 */
    private String phone ;

	/**
	 * 手机号码
	 */
    private String mobile ;

	/**
	 * E-mail
	 */
    private String email ;

	/**
	 * QQ号码
	 */
    private String QQ ;

	/**
	 * MSN号码
	 */
    private String MSN ;

	/**
	 * 用户审核描述
	 */
    private String CheckDESC;

    /**
     * 用户具有的角色信息
     */
    private String userRolesInfo;

    /**
     * 用户具有的角色
     */
    private List roleList;

    /**
     * 用户的省
     */
    private String MISCID;

    /**
     * 用户的角色级别,guidepage项目使用
     */
   private int roleLevel;

    /**
     * 构造方法
     */
	public UserVO ()
    {
    }

    /**
     * 设置用户MSN
     * @param MSN 用户MSN
     */
	public void setMSN (String MSN)
    {
        this.MSN = MSN ;
    }

    /**
     * 获取用户MSN
     * @return 用户MSN
     */
	public String getMSN ()
    {
        return MSN ;
    }

    /**
     * 获取用户帐号ID
     * @return 用户帐号ID
     */
	public String getUserID ()
    {
        return userID ;
    }

    /**
     * 获取用户状态
     * @return 用户状态
     */
	public int getState ()
    {
        return state ;
    }

    /**
     * 获取用户性别
     * @return 用户性别
     */
	public String getSex ()
    {
        return sex ;
    }

    /**
     * 获取用户邮编
     * @return 用户邮编
     */
	public String getPostcode ()
    {
        return postcode ;
    }

    /**
     * 获取联系电话
     * @return 联系电话
     */
	public String getPhone ()
    {
        return phone ;
    }

    /**
     * 获取用户姓名
     * @return 用户姓名
     */
	public String getName ()
    {
        return name ;
    }

    /**
     * 获取用户手机号
     * @return 用户手机号
     */
	public String getMobile ()
    {
        return mobile ;
    }

    /**
     * 获取用户e-mail
     * @return 用户e-mail
     */
	public String getEmail ()
    {
        return email ;
    }

    /**
     * 获取用户公司名称
     * @return 用户公司名称
     */
	public String getCompanyName ()
    {
        return companyName ;
    }

    /**
     * 获取用户公司地址
     * @return 用户公司地址
     */
	public String getCompanyAddr ()
    {
        return companyAddr ;
    }

    /**
     * 获取用户证件类型
     * @return 用户证件类型
     */
	public String getCertType ()
    {
        return certType ;
    }

    /**
     * 获取用户证件ID
     * @return 用户证件ID
     */
	public String getCertID ()
    {
        return certID ;
    }

    /**
     * 设置用户生日
     * @param birthday 用户生日
     */
	public void setBirthday (String birthday)
    {
        this.birthday = birthday ;
    }

    /**
     * 设置用户帐号ID
     * @param userID 用户帐号ID
     */
	public void setUserID (String userID)
    {
        this.userID = userID ;
    }

    /**
     * 设置用户状态
     * @param state 用户状态
     */
	public void setState (int state)
    {
        this.state = state ;
    }

    /**
     * 设置用户性别
     * @param sex 用户性别
     */
	public void setSex (String sex)
    {
        this.sex = sex ;
    }

    /**
     * 设置用户邮编
     * @param postcode 用户邮编
     */
	public void setPostcode (String postcode)
    {
        this.postcode = postcode ;
    }

    /**
     * 设置用户电话
     * @param phone 用户电话
     */
	public void setPhone (String phone)
    {
        this.phone = phone ;
    }

    /**
     * 设置用户姓名
     * @param name 用户姓名
     */
	public void setName (String name)
    {
        this.name = name ;
    }

    /**
     * 设置用户手机号
     * @param mobile 用户手机号
     */
	public void setMobile (String mobile)
    {
        this.mobile = mobile ;
    }

    /**
     * 用户e-mail
     * @param email 用户e-mail
     */
	public void setEmail (String email)
    {
        this.email = email ;
    }

    /**
     * 设置用户公司名称
     * @param companyName 用户公司名称
     */
	public void setCompanyName (String companyName)
    {
        this.companyName = companyName ;
    }

    /**
     * 设置用户公司地址
     * @param companyAddr 用户公司地址
     */
	public void setCompanyAddr (String companyAddr)
    {
        this.companyAddr = companyAddr ;
    }

    /**
     * 设置用户证件类型
     * @param certType 用户证件类型
     */
	public void setCertType (String certType)
    {
        this.certType = certType ;
    }

    /**
     * 设置用户证件ID
     * @param certID 用户证件ID
     */
	public void setCertID (String certID)
    {
        this.certID = certID ;
    }

    /**
     * 设置用户密码
     * @param password 用户密码
     */
	public void setPassword (String password)
    {
        this.password = password ;
    }

    /**
     * 设置用户注册审核的描述
     * @param CheckDESC 用户注册审核的描述
     */
	public void setCheckDESC (String CheckDESC)
    {
        this.CheckDESC = CheckDESC ;
    }

    /**
     * 获取用户生日
     * @return 用户生日
     */
	public String getBirthday ()
    {
        return birthday ;
    }

    /**
     * 获取用户密码
     * @return 用户密码
     */
	public String getPassword ()
    {
        return password ;
    }

    /**
     * 获取用户注册审核的描述
     * @return 用户注册审核的描述
     */
	public String getCheckDESC ()
    {
        return CheckDESC ;
    }

    /**
     * 设置用户QQ
     * @param QQ 用户QQ
     */
	public void setQQ (String QQ)
    {
        this.QQ = QQ ;
    }

    /**
     * 获取用户QQ
     * @return 用户QQ
     */
	public String getQQ ()
    {
        return QQ ;
    }

    /**
     * 设置用户具有的角色的列表
     * @param roleList 用户具有的角色的列表
     */
    public void setRoleList (List roleList)
    {
        this.roleList = roleList ;
        StringBuffer tmp = new StringBuffer();
        for(int i = 0; i < roleList.size(); i++)
        {
            if(i > 0)
            {
                tmp.append(",");
            }
            RoleVO role = (RoleVO) roleList.get(i);
            tmp.append(role.getName());
        }
        this.userRolesInfo = tmp.toString();
    }

    /**
     * 获取用户具有的角色的列表
     * @return 用户具有的角色的列表
     */
    public List getRoleList ()
    {
        return roleList ;
    }

    /**
     * 获取用户具有的角色信息
     * @return String 用户具有的角色信息
     */
    public String getUserRolesInfo()
    {
        return this.userRolesInfo;
    }

	/**
     * 用户信息VO的toString方法
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("user{");
        sb.append(userID);
        sb.append(",");
        sb.append(password);
        sb.append(",");
        sb.append(name);
        sb.append(",");
        sb.append(state);
        sb.append(",");
        sb.append(sex);
        sb.append(",");
        sb.append(birthday);
        sb.append(",");
        sb.append(certType);
        sb.append(",");
        sb.append(certID);
        sb.append(",");
        sb.append(companyName);
        sb.append(",");
        sb.append(companyAddr);
        sb.append(",");
        sb.append(postcode);
        sb.append(",");
        sb.append(phone);
        sb.append(",");
        sb.append(mobile);
        sb.append(",");
        sb.append(email);
        sb.append(",");
        sb.append(QQ);
        sb.append(",");
        sb.append(MSN);
        sb.append("}");
        return sb.toString();
    }

    /**
     * 覆盖equals方法，如果userID，name，certID一样，并且都不为空，就相等。
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof UserVO)
        {
            UserVO user = (UserVO) obj;
            if(this.userID.equals(user.getUserID()))
            {
                if(this.name != null && this.name.equals(user.getName()))
                {
                    if(this.certID != null && this.certID.equals(user.getCertID()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * 获取用户的省
     */
    public String getMISCID()
    {
        return MISCID;
    }

    /**
     * 设置用户的省
     * @param miscid 用户省
     */
    public void setMISCID(String miscid)
    {
        MISCID = miscid;
    }
  public int getRoleLevel() {
    return roleLevel;
  }
  public void setRoleLevel(int roleLevel) {
    this.roleLevel = roleLevel;
  }
}
