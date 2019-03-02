package com.aspire.ponaadmin.web.channelUser.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerConstant;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.actionlog.ActionLogBO;
import com.aspire.ponaadmin.web.channelUser.bo.ChannelBO;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;
import com.aspire.ponaadmin.web.constant.Constants;

public class UpdateChannelPwdAction extends BaseAction{
/**
 * 日志引用
 */
	private static final JLogger LOG = LoggerFactory .getLogger(UpdateChannelPwdAction.class);
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "用户未登陆或者登陆已超时！");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
		LOG.debug("UpdateChannelPwdAction()");
		String logDesc = "";
		boolean actionResult = true;
		ChannelVO channelVO = UserManagerBO.getInstance().getUserSessionVO(request.getSession()).getChannel();
//		String newPwd = request.getParameter("newPwd");
//		String oldPwd = request.getParameter("oldPwd");
		
		String encode_newPwd = this.getParameter(request, "newPwd").trim();
		String encode_oldPwd = this.getParameter(request, "oldPwd").trim();
		//RSA解密
		String newPwd = decrypt(encode_newPwd,request);
		String oldPwd =decrypt(encode_oldPwd,request);
		
		/**
		 * 检查参数
		 */
		if(newPwd == null || "".equals(newPwd) || oldPwd == null || "".equals(oldPwd)){
			throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
		}
		int result = ChannelBO.getInstance().updateChannelPwd(channelVO.getChannelsNO(), oldPwd, newPwd);
		String forward = "success";
		if(result != 0){
			forward = "pwdNoMatch";
			logDesc = "与原密码不相匹配";
			actionResult = false;
			request.setAttribute("noMatchMsg", "与原密码不相匹配");
		}
		logDesc = "密码修改成功";
		request.setAttribute(Constants.PARA_GOURL, "editChannelPwd.jsp");
		request.setAttribute("ChannelVO", channelVO);
		 //写操作日志
        {
            String logActionType = "修改渠道商账号密码";
            ActionLogBO.getInstance().log(channelVO.getChannelsId(), channelVO.getChannelsNO(), "", logActionType,
                                          actionResult, "",
                                          request.getRemoteAddr(), logDesc) ;
        }
		// TODO Auto-generated method stub
		return mapping.findForward(forward) ;
	}

}
