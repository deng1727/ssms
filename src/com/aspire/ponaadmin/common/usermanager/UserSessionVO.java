package com.aspire.ponaadmin.common.usermanager ;

import java.util.List;

import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;

/**
 * <p>������session�����VO�࣬��װ�û���Ҫ������session�����ȫ����Ϣ��</p>
 * <p>������UserVO�û���Ϣ��UserAccessInfoVO�û�������Ϣ���û����еĽ�ɫ�б��û����е�Ȩ���б��û�����Ȩ�޵�վ��Ŀ¼��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserSessionVO
{

    /**
     * �û�������Ϣ
     */
	private UserVO user;

	/**
	 * �������û���Ϣ
	 */
	private ChannelVO channel;
	/**
	 * �����̸�����
	 */
	private List openChannelsCategoryList;
	/**
	 * �û�������Ϣ
	 */
    private UserAccessInfoVO accessInfo;

	/**
	 * �û����е�Ȩ��
	 */
    private List rights;

    /**
     * ���췽��
     */
	public UserSessionVO ()
    {
    }

    /**
     * ��ȡ�û�������Ϣ
     * @return �û�������Ϣ
     */
	public UserVO getUser ()
    {
        return user ;
    }

    /**
     * ��ȡ�û����е�Ȩ��
     * @return �û����е�Ȩ��
     */
	public List getRights ()
    {
        return rights ;
    }

    /**
     * �����û�������Ϣ
     * @param accessInfo �û�������Ϣ
     */
	public void setAccessInfo (UserAccessInfoVO accessInfo)
    {
        this.accessInfo = accessInfo ;
    }

    /**
     * �����û�������Ϣ
     * @param user �û�������Ϣ
     */
	public void setUser (UserVO user)
    {
        this.user = user ;
    }

    /**
     * �����û����е�Ȩ��
     * @param rights �û����е�Ȩ��
     */
	public void setRights (List rights)
    {
        this.rights = rights ;
    }

    /**
     * ��ȡ�û�������Ϣ
     * @return �û�������Ϣ
     */
	public UserAccessInfoVO getAccessInfo ()
    {
        return accessInfo ;
    }

	public ChannelVO getChannel() {
		return channel;
	}

	public void setChannel(ChannelVO channel) {
		this.channel = channel;
	}

	public List getOpenChannelsCategoryList() {
		return openChannelsCategoryList;
	}

	public void setOpenChannelsCategoryList(List openChannelsCategoryList) {
		this.openChannelsCategoryList = openChannelsCategoryList;
	}

}
