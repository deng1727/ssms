package com.aspire.ponaadmin.common.usermanager ;

import java.util.List;

import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;

/**
 * <p>保存在session里面的VO类，封装用户需要保存在session里面的全部信息。</p>
 * <p>包括：UserVO用户信息，UserAccessInfoVO用户访问信息，用户具有的角色列表，用户具有的权限列表，用户具有权限的站点目录。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserSessionVO
{

    /**
     * 用户个人信息
     */
	private UserVO user;

	/**
	 * 渠道商用户信息
	 */
	private ChannelVO channel;
	/**
	 * 渠道商根货架
	 */
	private List openChannelsCategoryList;
	/**
	 * 用户访问信息
	 */
    private UserAccessInfoVO accessInfo;

	/**
	 * 用户具有的权限
	 */
    private List rights;

    /**
     * 构造方法
     */
	public UserSessionVO ()
    {
    }

    /**
     * 获取用户个人信息
     * @return 用户个人信息
     */
	public UserVO getUser ()
    {
        return user ;
    }

    /**
     * 获取用户具有的权限
     * @return 用户具有的权限
     */
	public List getRights ()
    {
        return rights ;
    }

    /**
     * 设置用户访问信息
     * @param accessInfo 用户访问信息
     */
	public void setAccessInfo (UserAccessInfoVO accessInfo)
    {
        this.accessInfo = accessInfo ;
    }

    /**
     * 设置用户个人信息
     * @param user 用户个人信息
     */
	public void setUser (UserVO user)
    {
        this.user = user ;
    }

    /**
     * 设置用户具有的权限
     * @param rights 用户具有的权限
     */
	public void setRights (List rights)
    {
        this.rights = rights ;
    }

    /**
     * 获取用户访问信息
     * @return 用户访问信息
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
