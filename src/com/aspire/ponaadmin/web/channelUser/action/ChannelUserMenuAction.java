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
	 * ��־����
	 */
		private static final JLogger LOG = LoggerFactory .getLogger(ChannelUserMenuAction.class);
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(
	            request.getSession()) ;
		if(request.getSession() == null || userSession == null||userSession.getChannel()==null){
    		this.saveMessages(request, "�û�δ��½���ߵ�½�ѳ�ʱ��");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
		String action = request.getParameter("action");
		LOG.debug("MenuAction:action="+action);
		//��session��ȡ���û���Ϣ
		ChannelVO channelVO = userSession.getChannel();
        String channelsNO = channelVO.getChannelsNO();
		String forward = "";
		/*
		 * �鿴�û���ϸ
		 */
		if("channelInfo".equals(action)){
	     
	        //����ҵ��㣬�����������˺Ż�ȡ��������Ϣ��
	        ChannelVO channel = ChannelBO.getInstance().getChannelByNO(channelsNO);
	      //���û�VO���󴫵ݸ���д�û���Ϣҳ��
	        request.setAttribute("ChannelVO", channel);
	        forward = "channelInfo";
			return mapping.findForward(forward);
		/*
		 * �޸�����
		 */
		}else if("toUpdateChannelPwd".equals(action)){
		        request.setAttribute("channelsNO", channelsNO);
		        forward = "toUpdateChannelPwd";
				return mapping.findForward(forward);
		/*
		 * ���ܷ������
		 */
		}else if("category_main".equals(action)){
			//��ȡ�����̸�����
			List openChannelsCategoryList = OpenChannelsCategoryBO.getInstance().queryOpenChannelsCategoryList(channelVO.getChannelsId());
			request.getSession().removeAttribute("openChannelsCategoryList");
			request.getSession().setAttribute("openChannelsCategoryList", openChannelsCategoryList);
			forward = "category_main";
			return mapping.findForward(forward);
		/*
		 * ������Ʒ����
		 */
		}else if("cgy_content_main".equals(action)){
			//��ȡ�����̸�����
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
