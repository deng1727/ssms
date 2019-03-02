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
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.bo.ChannelCategoryManageBO;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelCategoryManageVO;
import com.aspire.ponaadmin.web.constant.Constants;

public class ChannelCategoryManageAction extends BaseAction {

	private final static JLogger LOG = LoggerFactory.getLogger(ChannelCategoryManageAction.class);
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("ChannelCategoryManageAction()");
		// �������л�ȡ��������
		String action = this.getParameter(request, "actionType").trim();
		if("getChannelCategoryList".equals(action)){
			return getCategoryListByChannelNO(mapping,form,request,response);
		}else if("showCategoryInfo".equals(action)){
			return getCategoryByID(mapping,form,request,response);
		}
		else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
			return mapping.findForward(forward);
		}
		
	}
	
	/**
	 * �����������˺ţ���ѯ�����б�
	 * @param ChannelNO
	 * @return
	 */
	private ActionForward getCategoryListByChannelNO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "getChannelCategoryList";
		PageResult page = new PageResult(request);
		UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(
	            request.getSession()) ;
	     String channelsNO = userSession.getChannel().getChannelsNO();
		try {
			ChannelCategoryManageBO.getInstance().getCategoryListByChannelNO(page, channelsNO);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ����Ϣ�б����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}
	
	/**
	 * �鿴������ϸ
	 * @param ChannelNO
	 * @return
	 */
	private ActionForward getCategoryByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String categoryId = request.getParameter("categoryId");
		String forward = "showCategoryInfo";
		PageResult page = new PageResult(request);
		UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(
	            request.getSession()) ;
	     String channelsNO = userSession.getChannel().getChannelsNO();
		try {
			ChannelCategoryManageBO.getInstance().getCategoryInfoByID(categoryId);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ����Ϣ�б����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}
}
