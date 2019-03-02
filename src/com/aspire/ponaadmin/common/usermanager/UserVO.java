package com.aspire.ponaadmin.common.usermanager ;

import java.util.List;
import com.aspire.ponaadmin.common.rightmanager.RoleVO;

/**
 * <p>�û�������ϢVO��</p>
 * <p>�û�������ϢVO�࣬��װ���û�������Ϣ</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserVO
{

    /**
     * �û��ʺ�ID
     */
	private String userID ;

	/**
	 * ����
	 */
    private String password ;

	/**
	 * �û���
	 */
    private String name ;

	/**
	 * �û�״̬�������ֵ�����뿴����ĳ�������
	 */
    private int state ;

	/**
	 * �Ա�
	 */
    private String sex ;

	/**
	 * ����
	 */
    private String birthday ;

	/**
	 * ֤������
	 */
    private String certType ;

	/**
	 * ֤������
	 */
    private String certID ;

	/**
	 * ��˾����
	 */
    private String companyName ;

	/**
	 * ��˾��ַ
	 */
    private String companyAddr ;

	/**
	 * ��������
	 */
    private String postcode ;

	/**
	 * ��ϵ�绰
	 */
    private String phone ;

	/**
	 * �ֻ�����
	 */
    private String mobile ;

	/**
	 * E-mail
	 */
    private String email ;

	/**
	 * QQ����
	 */
    private String QQ ;

	/**
	 * MSN����
	 */
    private String MSN ;

	/**
	 * �û��������
	 */
    private String CheckDESC;

    /**
     * �û����еĽ�ɫ��Ϣ
     */
    private String userRolesInfo;

    /**
     * �û����еĽ�ɫ
     */
    private List roleList;

    /**
     * �û���ʡ
     */
    private String MISCID;

    /**
     * �û��Ľ�ɫ����,guidepage��Ŀʹ��
     */
   private int roleLevel;

    /**
     * ���췽��
     */
	public UserVO ()
    {
    }

    /**
     * �����û�MSN
     * @param MSN �û�MSN
     */
	public void setMSN (String MSN)
    {
        this.MSN = MSN ;
    }

    /**
     * ��ȡ�û�MSN
     * @return �û�MSN
     */
	public String getMSN ()
    {
        return MSN ;
    }

    /**
     * ��ȡ�û��ʺ�ID
     * @return �û��ʺ�ID
     */
	public String getUserID ()
    {
        return userID ;
    }

    /**
     * ��ȡ�û�״̬
     * @return �û�״̬
     */
	public int getState ()
    {
        return state ;
    }

    /**
     * ��ȡ�û��Ա�
     * @return �û��Ա�
     */
	public String getSex ()
    {
        return sex ;
    }

    /**
     * ��ȡ�û��ʱ�
     * @return �û��ʱ�
     */
	public String getPostcode ()
    {
        return postcode ;
    }

    /**
     * ��ȡ��ϵ�绰
     * @return ��ϵ�绰
     */
	public String getPhone ()
    {
        return phone ;
    }

    /**
     * ��ȡ�û�����
     * @return �û�����
     */
	public String getName ()
    {
        return name ;
    }

    /**
     * ��ȡ�û��ֻ���
     * @return �û��ֻ���
     */
	public String getMobile ()
    {
        return mobile ;
    }

    /**
     * ��ȡ�û�e-mail
     * @return �û�e-mail
     */
	public String getEmail ()
    {
        return email ;
    }

    /**
     * ��ȡ�û���˾����
     * @return �û���˾����
     */
	public String getCompanyName ()
    {
        return companyName ;
    }

    /**
     * ��ȡ�û���˾��ַ
     * @return �û���˾��ַ
     */
	public String getCompanyAddr ()
    {
        return companyAddr ;
    }

    /**
     * ��ȡ�û�֤������
     * @return �û�֤������
     */
	public String getCertType ()
    {
        return certType ;
    }

    /**
     * ��ȡ�û�֤��ID
     * @return �û�֤��ID
     */
	public String getCertID ()
    {
        return certID ;
    }

    /**
     * �����û�����
     * @param birthday �û�����
     */
	public void setBirthday (String birthday)
    {
        this.birthday = birthday ;
    }

    /**
     * �����û��ʺ�ID
     * @param userID �û��ʺ�ID
     */
	public void setUserID (String userID)
    {
        this.userID = userID ;
    }

    /**
     * �����û�״̬
     * @param state �û�״̬
     */
	public void setState (int state)
    {
        this.state = state ;
    }

    /**
     * �����û��Ա�
     * @param sex �û��Ա�
     */
	public void setSex (String sex)
    {
        this.sex = sex ;
    }

    /**
     * �����û��ʱ�
     * @param postcode �û��ʱ�
     */
	public void setPostcode (String postcode)
    {
        this.postcode = postcode ;
    }

    /**
     * �����û��绰
     * @param phone �û��绰
     */
	public void setPhone (String phone)
    {
        this.phone = phone ;
    }

    /**
     * �����û�����
     * @param name �û�����
     */
	public void setName (String name)
    {
        this.name = name ;
    }

    /**
     * �����û��ֻ���
     * @param mobile �û��ֻ���
     */
	public void setMobile (String mobile)
    {
        this.mobile = mobile ;
    }

    /**
     * �û�e-mail
     * @param email �û�e-mail
     */
	public void setEmail (String email)
    {
        this.email = email ;
    }

    /**
     * �����û���˾����
     * @param companyName �û���˾����
     */
	public void setCompanyName (String companyName)
    {
        this.companyName = companyName ;
    }

    /**
     * �����û���˾��ַ
     * @param companyAddr �û���˾��ַ
     */
	public void setCompanyAddr (String companyAddr)
    {
        this.companyAddr = companyAddr ;
    }

    /**
     * �����û�֤������
     * @param certType �û�֤������
     */
	public void setCertType (String certType)
    {
        this.certType = certType ;
    }

    /**
     * �����û�֤��ID
     * @param certID �û�֤��ID
     */
	public void setCertID (String certID)
    {
        this.certID = certID ;
    }

    /**
     * �����û�����
     * @param password �û�����
     */
	public void setPassword (String password)
    {
        this.password = password ;
    }

    /**
     * �����û�ע����˵�����
     * @param CheckDESC �û�ע����˵�����
     */
	public void setCheckDESC (String CheckDESC)
    {
        this.CheckDESC = CheckDESC ;
    }

    /**
     * ��ȡ�û�����
     * @return �û�����
     */
	public String getBirthday ()
    {
        return birthday ;
    }

    /**
     * ��ȡ�û�����
     * @return �û�����
     */
	public String getPassword ()
    {
        return password ;
    }

    /**
     * ��ȡ�û�ע����˵�����
     * @return �û�ע����˵�����
     */
	public String getCheckDESC ()
    {
        return CheckDESC ;
    }

    /**
     * �����û�QQ
     * @param QQ �û�QQ
     */
	public void setQQ (String QQ)
    {
        this.QQ = QQ ;
    }

    /**
     * ��ȡ�û�QQ
     * @return �û�QQ
     */
	public String getQQ ()
    {
        return QQ ;
    }

    /**
     * �����û����еĽ�ɫ���б�
     * @param roleList �û����еĽ�ɫ���б�
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
     * ��ȡ�û����еĽ�ɫ���б�
     * @return �û����еĽ�ɫ���б�
     */
    public List getRoleList ()
    {
        return roleList ;
    }

    /**
     * ��ȡ�û����еĽ�ɫ��Ϣ
     * @return String �û����еĽ�ɫ��Ϣ
     */
    public String getUserRolesInfo()
    {
        return this.userRolesInfo;
    }

	/**
     * �û���ϢVO��toString����
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
     * ����equals���������userID��name��certIDһ�������Ҷ���Ϊ�գ�����ȡ�
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
     * ��ȡ�û���ʡ
     */
    public String getMISCID()
    {
        return MISCID;
    }

    /**
     * �����û���ʡ
     * @param miscid �û�ʡ
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
