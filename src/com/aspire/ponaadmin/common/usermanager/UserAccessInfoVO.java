package com.aspire.ponaadmin.common.usermanager ;

/**
 * <p>�û�������ϢVO��</p>
 * <p>�û�������ϢVO�࣬��¼�û�����ponaadminϵͳ�ķ�����Ϣ��
 * ����������ʹ�õ��ʺš�����IP����¼ʱ�䡢�����ն˵�</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserAccessInfoVO
{

    /**
     * �����ʺ�id
     */
    private String userID ;

    /**
     * ����IP
     */
    private String IP ;

    /**
     * ��¼ʱ��
     */
    private long loginTime ;

    /**
     * �û�ʹ�õ��ն�
     */
    private String useragent ;

    /**
     * ���췽��
     */
	public UserAccessInfoVO ()
    {
    }

    /**
     * ��ȡ�û�ʹ�õ��ն�
     * @return �û�ʹ�õ��ն�
     */
	public String getUseragent ()
    {
        return useragent ;
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
     * ���õ�¼ʱ��
     * @param loginTime ��¼ʱ��
     */
	public void setLoginTime (long loginTime)
    {
        this.loginTime = loginTime ;
    }

    /**
     * �����û�ʹ�õ��ն�
     * @param useragent �û�ʹ�õ��ն�
     */
	public void setUseragent (String useragent)
    {
        this.useragent = useragent ;
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
     * ��ȡ��¼ʱ��
     * @return ��¼ʱ��
     */
	public long getLoginTime ()
    {
        return loginTime ;
    }

    /**
     * ���÷���IP
     * @param IP ����IP
     */
	public void setIP (String IP)
    {
        this.IP = IP ;
    }

    /**
     * ��ȡ����IP
     * @return ����IP
     */
	public String getIP ()
    {
        return IP ;
    }
}
