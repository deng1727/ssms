package com.aspire.ponaadmin.web.channelUser.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.bo.ChannelBO;
import com.aspire.ponaadmin.web.channelUser.bo.OpenChannelsCategoryBO;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;
import com.aspire.ponaadmin.web.constant.Constants;

public class ChannelUserMenuAction extends BaseAction {

	/**
	 * 日志引用
	 */
		private static final JLogger LOG = LoggerFactory .getLogger(ChannelUserMenuAction.class);
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(
	            request.getSession()) ;
		if(request.getSession() == null || userSession == null||userSession.getChannel()==null){
    		this.saveMessages(request, "用户未登陆或者登陆已超时！");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
		String action = request.getParameter("action");
		LOG.debug("MenuAction:action="+action);
		//从session中取得用户信息
		ChannelVO channelVO = userSession.getChannel();
        String channelsNO = channelVO.getChannelsNO();
		String forward = "";
		/*
		 * 查看用户详细
		 */
		if("channelInfo".equals(action)){
	     
	        //调用业务层，根据渠道商账号获取渠道商信息。
	        ChannelVO channel = ChannelBO.getInstance().getChannelByNO(channelsNO);
	      //把用户VO对象传递给填写用户信息页面
	        request.setAttribute("ChannelVO", channel);
	        forward = "channelInfo";
			return mapping.findForward(forward);
		/*
		 * 修改密码
		 */
		}else if("toUpdateChannelPwd".equals(action)){
		        request.setAttribute("channelsNO", channelsNO);
		        forward = "toUpdateChannelPwd";
				return mapping.findForward(forward);
		/*
		 * 货架分类管理
		 */
		}else if("category_main".equals(action)){
			//获取渠道商根货架
			List openChannelsCategoryList = OpenChannelsCategoryBO.getInstance().queryOpenChannelsCategoryList(channelVO.getChannelsId());
			request.getSession().removeAttribute("openChannelsCategoryList");
			request.getSession().setAttribute("openChannelsCategoryList", openChannelsCategoryList);
			forward = "category_main";
			return mapping.findForward(forward);
		/*
		 * 货架商品管理
		 */
		}else if("cgy_content_main".equals(action)){
			//获取渠道商根货架
			List openChannelsCategoryList = OpenChannelsCategoryBO.getInstance().queryOpenChannelsCategoryList(channelVO.getChannelsId());
			request.getSession().removeAttribute("openChannelsCategoryList");
			request.getSession().setAttribute("openChannelsCategoryList", openChannelsCategoryList);
			forward = "cgy_content_main";
			return mapping.findForward(forward);
		}else{
			forward = "commonFailure";
			return mapping.findForward(forward);
		}
	}
	

}
