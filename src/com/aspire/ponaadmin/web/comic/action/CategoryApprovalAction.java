package com.aspire.ponaadmin.web.comic.action;

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
import com.aspire.ponaadmin.web.comic.bo.CategoryApprovalBO;
import com.aspire.ponaadmin.web.comic.bo.ReferenceBO;
import com.aspire.ponaadmin.web.comic.vo.ReferenceVO;
import com.aspire.ponaadmin.web.constant.Constants;

public class CategoryApprovalAction extends BaseAction {

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(CategoryApprovalAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

		// 从请求中获取操作类型
		String method = this.getParameter(request, "method").trim();

		if ("pass".equals(method)) {
			return pass(mapping, form, request, response);
		} else if ("nopass".equals(method)) {
			return nopass(mapping, form, request, response);
		} else if ("query".equals(method)) {
			return query(mapping, form, request, response);
		}else if("approval".equals(method)){
			return approval(mapping, form, request, response);
		} else if("accept".equals(method)){
			return accept(mapping, form, request, response);
		}else if("refuse".equals(method)){
			return refuse(mapping, form, request, response);
		}else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		String categoryId = request.getParameter("categoryId") == null ? ""
				: request.getParameter("categoryId");
		String contentId = request.getParameter("contentId") == null ? ""
				: request.getParameter("contentId");
		String contentName = request.getParameter("contentName") == null ? ""
				: request.getParameter("contentName");
		String aprovalStatus = request.getParameter("aprovalStatus") == null ? ""
				: request.getParameter("aprovalStatus");

		String actionType = "查询动漫货架下商品信息";
		boolean actionResult = true;
		String actionDesc = "查询动漫货架下商品信息成功";
		String actionTarget = categoryId;

		String forward = "query";
		ReferenceVO vo = new ReferenceVO();
		PageResult page = new PageResult(request);
		Map<String, Object> map = new HashMap<String, Object>();

		// 从请求中获取货架内码

		vo.setCategoryId(categoryId);
		vo.setContentId(contentId);
		vo.setContentName(contentName);
		vo.setVerify_status(aprovalStatus);
		try {
			map = CategoryApprovalBO.getInstance().queryCategoryListItem(
					categoryId);
			ReferenceBO.getInstance().queryReferenceList(page, vo);
		} catch (BOException e) {
			LOG.error(e);

			// 写操作日志
			actionResult = false;
			actionDesc = "查询动漫货架下商品信息出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);

			this.saveMessagesValue(request, "查询指定货架下的商品列表出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// 写操作日志
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);

		request.setAttribute("categoryContent", map);
		request.setAttribute("PageResult", page);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("contentId", contentId);
		request.setAttribute("contentName", contentName);
		return mapping.findForward(forward);
	}
	
	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		String contentId = request.getParameter("contentId")==null?"":request.getParameter("contentId");
    	String contentName = request.getParameter("contentName")==null?"":request.getParameter("contentName");
    	String aprovalStatus = request.getParameter("aprovalStatus")==null?"":request.getParameter("aprovalStatus");
    	String approval = this.getParameter(request, "approval").trim();
    	String[] dealRef = request.getParameterValues("dealRef");
		try {
			CategoryApprovalBO.getInstance().categoryGoodsApproval(request, categoryId,approval,dealRef);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "动漫商品提交审批出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_ANIMALCATEGORY_RESULT_004");
		request.setAttribute(Constants.PARA_GOURL,
				"referenceTree.do?method=query&contentId="
						+ contentId+"&categoryId=" + categoryId + "&contentName=" + contentName + "&aprovalStatus=" + aprovalStatus);
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
				this.saveMessagesValue(request, "动漫货架审批发布出错");
			} else {
				this.saveMessagesValue(request, "动漫商品审批发布出错");
			}
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		if (operation != null && !"".equals(operation) && "1".equals(operation)) {
			this.saveMessages(request, "RESOURCE_ANIMALCATEGORY_RESULT_002");
		} else {
			this.saveMessages(request, "RESOURCE_ANIMALCATEGORY_RESULT_005");
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
				this.saveMessagesValue(request, "动漫货架审批不通过出错");
			} else {
				this.saveMessagesValue(request, "动漫商品审批不通过出错");
			}
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		if (operation != null && !"".equals(operation) && "1".equals(operation)) {
			this.saveMessages(request, "RESOURCE_ANIMALCATEGORY_RESULT_003");
		} else {
			this.saveMessages(request, "RESOURCE_ANIMALCATEGORY_RESULT_006");
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
			CategoryApprovalBO.getInstance().accept(request, categoryId,dealRef);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "动漫商品审批发布出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_ANIMALCATEGORY_RESULT_005");
		return mapping.findForward(forward);
	}
	
	public ActionForward refuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "success";
		String categoryId = this.getParameter(request, "categoryId").trim();
    	String[] dealRef = request.getParameterValues("dealRef");
		try {
			CategoryApprovalBO.getInstance().refuse(request, categoryId,dealRef);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "动漫商品审批不通过出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_ANIMALCATEGORY_RESULT_006");
		return mapping.findForward(forward);
	}

}
