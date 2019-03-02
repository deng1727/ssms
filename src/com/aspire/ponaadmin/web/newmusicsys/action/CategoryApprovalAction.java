package com.aspire.ponaadmin.web.newmusicsys.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.newmusicsys.bo.CategoryApprovalBO;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicRefBO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;

public class CategoryApprovalAction extends BaseAction {

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(CategoryApprovalAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

		// �������л�ȡ��������
		String method = this.getParameter(request, "method").trim();

		if ("pass".equals(method)) {
			return pass(mapping, form, request, response);
		} else if ("nopass".equals(method)) {
			return nopass(mapping, form, request, response);
		} else if ("query".equals(method)) {
			return query(mapping, form, request, response);
		} else if ("approval".equals(method)) {
			return approval(mapping, form, request, response);
		} else if ("accept".equals(method)) {
			return accept(mapping, form, request, response);
		} else if ("refuse".equals(method)) {
			return refuse(mapping, form, request, response);
		} else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
			return mapping.findForward(forward);
		}
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		String forward = "query";
        NewMusicRefVO vo = new NewMusicRefVO();
        PageResult page = new PageResult(request);

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String musicId = this.getParameter(request, "musicId").trim();
        String musicName = this.getParameter(request, "musicName").trim();
        String singer = this.getParameter(request, "singer").trim();
        String showCreateTime = this.getParameter(request, "showCreateTime").trim();
        String approvalStatus = this.getParameter(request, "approvalStatus");

        String actionType = "��ѯ���ֻ�������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "��ѯ���ֻ�������Ʒ��Ϣ�ɹ�";
		String actionTarget = categoryId;
		
        vo.setCategoryId(categoryId);
        vo.setMusicId(musicId);
        vo.setMusicName(musicName);
        vo.setSinger(singer);
        vo.setShowCreateTime(showCreateTime);
        vo.setVerify_status(approvalStatus);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }
        
        Map<String,Object> map = new HashMap<String,Object>();

        try
        {
        	map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
            NewMusicRefBO.getInstance().queryNewMusicRefList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "��ѯ���ֻ�������Ʒ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "��ѯ������ָ�������µ���Ʒ�б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("musicId", musicId);
        request.setAttribute("musicName", musicName);
        request.setAttribute("singer", singer);
        request.setAttribute("showCreateTime", showCreateTime);
        request.setAttribute("categoryContent", map);
        request.setAttribute("approvalStatus", approvalStatus);
        return mapping.findForward(forward);
	}

	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		String approval = this.getParameter(request, "approval").trim();
		String[] dealRef = request.getParameterValues("dealRef");
		try {
			CategoryApprovalBO.getInstance().categoryGoodsApproval(request,
					categoryId, approval, dealRef);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "������Ʒ�ύ��������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_MUSICCATEGORY_RESULT_004");
		request.setAttribute(Constants.PARA_GOURL,
				"queryReference.do?perType=query&categoryId=" + categoryId);
		return mapping.findForward(forward);
	}

	public ActionForward pass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		String operation = this.getParameter(request, "operation").trim();
		try {
			if (operation != null && !"".equals(operation)
					&& "1".equals(operation)) {
				CategoryApprovalBO.getInstance().categoryApprovalPass(request,
						categoryId);
			} else {
				CategoryApprovalBO.getInstance().goodsApprovalPass(request,
						categoryId);
			}
		} catch (BOException e) {
			LOG.error(e);
			if (operation != null && !"".equals(operation)
					&& "1".equals(operation)) {
				this.saveMessagesValue(request, "���ֻ���������������");
			} else {
				this.saveMessagesValue(request, "������Ʒ������������");
			}
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		if (operation != null && !"".equals(operation) && "1".equals(operation)) {
			this.saveMessages(request, "RESOURCE_MUSICCATEGORY_RESULT_002");
		} else {
			this.saveMessages(request, "RESOURCE_MUSICCATEGORY_RESULT_005");
		}
		request.setAttribute(Constants.PARA_GOURL,
				"categoryApprovalList.do?approvalStatus=2&operation="
						+ operation);
		return mapping.findForward(forward);
	}

	public ActionForward nopass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		String operation = this.getParameter(request, "operation").trim();
		try {
			if (operation != null && !"".equals(operation)
					&& "1".equals(operation)) {
				CategoryApprovalBO.getInstance().categoryApprovalNoPass(
						request, categoryId);
			} else {
				CategoryApprovalBO.getInstance().goodsApprovalNoPass(request,
						categoryId);
			}
		} catch (BOException e) {
			LOG.error(e);
			if (operation != null && !"".equals(operation)
					&& "1".equals(operation)) {
				this.saveMessagesValue(request, "���ֻ���������ͨ������");
			} else {
				this.saveMessagesValue(request, "������Ʒ������ͨ������");
			}
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		if (operation != null && !"".equals(operation) && "1".equals(operation)) {
			this.saveMessages(request, "RESOURCE_MUSICCATEGORY_RESULT_003");
		} else {
			this.saveMessages(request, "RESOURCE_MUSICCATEGORY_RESULT_006");
		}
		request.setAttribute(Constants.PARA_GOURL,
				"categoryApprovalList.do?approvalStatus=2&operation="
						+ operation);
		return mapping.findForward(forward);
	}

	public ActionForward accept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "success";
		String categoryId = this.getParameter(request, "categoryId").trim();
		String[] dealRef = request.getParameterValues("dealRef");
		try {
			CategoryApprovalBO.getInstance().accept(request, categoryId,
					dealRef);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "������Ʒ������������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_MUSICCATEGORY_RESULT_005");
		return mapping.findForward(forward);
	}

	public ActionForward refuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "success";
		String categoryId = this.getParameter(request, "categoryId").trim();
		String[] dealRef = request.getParameterValues("dealRef");
		try {
			CategoryApprovalBO.getInstance().refuse(request, categoryId,
					dealRef);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "������Ʒ������ͨ������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_MUSICCATEGORY_RESULT_006");
		return mapping.findForward(forward);
	}

}
