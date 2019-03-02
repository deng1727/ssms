package com.aspire.ponaadmin.web.coManagement.action;

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
import com.aspire.ponaadmin.web.coManagement.bo.CooperationBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

public class CoManagementList extends BaseAction {
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(CoManagementList.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("doPerform()");
		}

		// 从请求中获取操作类型
		String method = this.getParameter(request, "method").trim();

		if ("query".equals(method)) {
			return query(mapping, form, request, response);
		} else if ("add".equals(method)) {
			return add(mapping, form, request, response);
		} else if ("edit".equals(method)) {
			return edit(mapping, form, request, response);
		} else if ("save".equals(method)) {
			return save(mapping, form, request, response);
		}  else if ("detail".equals(method)) {
			return detail(mapping, form, request, response);
		} else if ("update".equals(method)) {
			return update(mapping, form, request, response);
		} else if ("operation".equals(method)) {
			return operation(mapping, form, request, response);
		}else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}

	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "list";

		// 从请求中获取合作商条件
		String cooperationId = this.getParameter(request, "cooperationId")
				.trim();
		String cooperationName = this.getParameter(request, "cooperationName")
				.trim();

		PageResult page = new PageResult(request);
		page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

		CooperationBO.getInstance().queryCooperationList(page, cooperationId,
				cooperationName);

		request.setAttribute("cooperationId", cooperationId);
		request.setAttribute("cooperationName", cooperationName);
		request.setAttribute("PageResult", page);

		return mapping.findForward(forward);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "add";

		return mapping.findForward(forward);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "edit";
		String cooperationId = this.getParameter(request, "radio")
				.trim();
		Map<String,Object> map = new HashMap<String,Object>();
		map = CooperationBO.getInstance().queryCooperation(cooperationId);
		request.setAttribute("cooperationInfo", map);
		return mapping.findForward(forward);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_SUCCESS;

		String cooperationId = this.getParameter(request, "cooperationId")
				.trim();
		String cooperationName = this.getParameter(request, "cooperationName")
				.trim();	
		String[] cooperationTypes = request.getParameterValues("cooperationType");
		StringBuffer cooperationType = new StringBuffer("");
		for (String str : cooperationTypes) {
			cooperationType.append(str + ",");
		}
		String channelNumber = this.getParameter(request, "channelNumber")
				.trim();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cooperationId", cooperationId);
		map.put("cooperationName", cooperationName);
		map.put("cooperationType",
				cooperationType.toString().substring(0,
						cooperationType.toString().length() - 1));
		map.put("channelNumber", channelNumber);

		String actionType = "添加合作商";
		boolean actionResult = true;
		String actionDesc = "添加合作商成功";
		String actionTarget = cooperationId;

		try {
			//先判断配置项的货架是否存在
        	if(!CooperationBO.getInstance().isExistInCategory()){
        		actionType = "配置项的货架不存在";
        		actionDesc = "配置项的货架不存在";
        		this.saveMessagesValue(request, "配置项的货架不存在,请重新配置");
        		request
                .setAttribute(Constants.PARA_GOURL,
                        "../cooperation/cooperationList.do?method=add");
        		return mapping.findForward(forward);
        		
        	}
        	
			CooperationBO.getInstance().insertCooperation(map);
		} catch (BOException e) {
			logger.error("添加合作商发生异常：", e);

			// 写操作日志
			actionResult = false;
			actionDesc = "添加合作商出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);

			this.saveMessagesValue(request, "添加合作商出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		this.saveMessages(request, "RESOURCE_COOPERATION_RESULT_001");
		request.setAttribute(Constants.PARA_GOURL,
				"cooperationList.do?method=query");

		return mapping.findForward(forward);
	}
	
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException {
		String forward = "detail";
		String cooperationId = this.getParameter(request, "cooperationId")
				.trim();
		Map<String,Object> map = new HashMap<String,Object>();
		map = CooperationBO.getInstance().queryCooperation(cooperationId);
		request.setAttribute("cooperationInfo", map);
		return mapping.findForward(forward);
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;

		String cooperationId = this.getParameter(request, "cooperationId")
				.trim();	
		String[] cooperationTypes = request.getParameterValues("cooperationType");
		StringBuffer cooperationType = new StringBuffer("");
		for (String str : cooperationTypes) {
			cooperationType.append(str + ",");
		}
		String channelNumber = this.getParameter(request, "channelNumber")
				.trim();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cooperationId", cooperationId);
		map.put("cooperationType",
				cooperationType.toString().substring(0,
						cooperationType.toString().length() - 1));
		map.put("channelNumber", channelNumber);

		String actionType = "更新合作商";
		boolean actionResult = true;
		String actionDesc = "更新合作商成功";
		String actionTarget = cooperationId;
		
		

		try {
			CooperationBO.getInstance().updateCooperation(map);
		} catch (BOException e) {
			logger.error("更新合作商发生异常：", e);

			// 写操作日志
			actionResult = false;
			actionDesc = "更新合作商出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);

			this.saveMessagesValue(request, "更新合作商出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		
		this.saveMessages(request, "RESOURCE_COOPERATION_RESULT_002");
		request.setAttribute(Constants.PARA_GOURL,
				"cooperationList.do?method=query");

		return mapping.findForward(forward);
	}
	
	public ActionForward operation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException {
		String forward = "list";

		String cooperationId = this.getParameter(request, "cooperationId")
				.trim();	
		String type = this.getParameter(request, "type")
				.trim();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cooperationId", cooperationId);
		map.put("type", type);

		String actionType = "更新合作商状态信息";
		boolean actionResult = true;
		String actionDesc = "更新合作商状态信息成功";
		String actionTarget = cooperationId;
		
		

		try {
			CooperationBO.getInstance().operationCooperation(map);
		} catch (BOException e) {
			logger.error("更新合作商状态信息发生异常：", e);

			// 写操作日志
			actionResult = false;
			actionDesc = "更新合作商状态信息出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);

			this.saveMessagesValue(request, "更新合作商状态信息出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		PageResult page = new PageResult(request);
		page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

		CooperationBO.getInstance().queryCooperationList(page, null,
				null);
		request.setAttribute("PageResult", page);

		return mapping.findForward(forward);
	}

}
