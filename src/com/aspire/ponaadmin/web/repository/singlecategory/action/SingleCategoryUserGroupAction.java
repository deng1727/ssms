package com.aspire.ponaadmin.web.repository.singlecategory.action;

import java.util.ArrayList;

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
import com.aspire.ponaadmin.web.category.CategoryRuleVO;
import com.aspire.ponaadmin.web.category.ui.CategoryUpdateBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.singlecategory.bo.SingleCategoryBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * <p>
 * 查看资源栏目的货架树的Action
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class SingleCategoryUserGroupAction extends BaseAction {

	/**
	 * 日志引用
	 */
	private static final JLogger log = LoggerFactory
			.getLogger(SingleCategoryUserGroupAction.class);

	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws BOException
	 * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (log.isDebugEnabled()) {
			log.debug("MMClientErrorAction in.....");
		}

		String opType = this.getParameter(request, "opType");
		if ("doQueryList".equals(opType)) {
			return this.doQueryList(mapping, form, request, response);
		} else if ("doQueryCategoryList".equals(opType)) {
			return this.doQueryCategoryList(mapping, form, request, response);
		}
		else if ("doAdd".equals(opType)) {
			return this.doAdd(mapping, form, request, response);
		}
		else if ("doDel".equals(opType)) {
			return this.doDel(mapping, form, request, response);
		}
		
		else if ("toUpdate".equals(opType)) {
			return this.toUpdate(mapping, form, request, response);
		}
		
		else if ("doUpdate".equals(opType)) {
			return this.doUpdate(mapping, form, request, response);
		}

		return null;
	}

	private ActionForward doUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_FAILURE;

		// 从请求中获取参数
		String code = this.getParameter(request, "code").trim();
		String name = this.getParameter(request, "name").trim();
		String categoryId = this.getParameter(request, "categoryId");
		String type = this.getParameter(request, "type").trim();
		if (log.isDebugEnabled()) {
			log.debug("从请求中获取参数：code=" + code + " name=" + name
					+ " categoryId=" + categoryId);

		}
		// 修改货架规则信息
		try {
			
			SingleCategoryBO.getInstance().updateSingleCategoryUserGroupByCode(code,name,categoryId,type);
		} catch (BOException e) {
			log.error(e);
			this.saveMessagesValue(request, "修改货架用户组信息出错");
			return mapping.findForward(forward);
		}

		this.saveMessagesValue(request, "修改货架用户组信息成功！");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL, "../singlecategory/singleCategoryUserGroupAction.do?opType=doQueryList");
		return mapping.findForward(forward);
	}

	private ActionForward toUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException {
		if (log.isDebugEnabled()) {
			log.debug("toUpdate in.............");
		}
		String forward = Constants.FORWARD_COMMON_FAILURE;

		String code = this.getParameter(request, "code").trim();
		String name = this.getParameter(request, "name").trim();
		String type = this.getParameter(request, "type").trim();
		
		try {
			
			String categoryId=SingleCategoryBO.getInstance().querySingleCategoryIdsByCode(code,type);
			request.setAttribute("code", code);
			request.setAttribute("name", name);
			request.setAttribute("type", type);
			request.setAttribute("categoryId", categoryId);

			forward = "toUpdate";
		} catch (Exception e) {
			this.saveMessages(request, "跳转到货架用户组信息更改页面失败");
		}
		return mapping.findForward(forward);
		
	}

	private ActionForward doDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException {
		String forward = Constants.FORWARD_COMMON_FAILURE;

		// 从请求中获取参数
		String code = this.getParameter(request, "code").trim();
		String type = this.getParameter(request, "type").trim();
		// 修改货架规则信息
		try {
			SingleCategoryBO.getInstance().delSingleCategoryUserGroupByCode(code,type);
		} catch (BOException e) {
			log.error(e);
			this.saveMessagesValue(request, "删除货架用户组信息出错");
			return mapping.findForward(forward);
		}

		this.saveMessagesValue(request, "删除货架用户组信息成功！");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL, "../singlecategory/singleCategoryUserGroupAction.do?opType=doQueryList");
		return mapping.findForward(forward);
	}

	public ActionForward doAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		String forward = Constants.FORWARD_COMMON_FAILURE;

		// 从请求中获取参数
		String code = this.getParameter(request, "code").trim();
		String name = this.getParameter(request, "name").trim();
		String categoryId = this.getParameter(request, "categoryId");
		String type = this.getParameter(request, "type").trim();
		
		if (log.isDebugEnabled()) {
			log.debug("从请求中获取参数：code=" + code + " name=" + name
					+ " categoryId=" + categoryId);

		}
		// 修改货架规则信息
		try {
			// 通过货架内码获取是否已存在此货架
			CategoryRuleVO categoryRule = CategoryUpdateBO.getInstance()
					.getCategoryRuleVOByID(code);
			if (code.equals(categoryRule.getCid())) {
				log.error("您输入的用户组内码已存在，请重新输入！");
				this.saveMessagesValue(request, "您输入的用户组内码已存在，请重新输入！");
				return mapping.findForward(forward);
			}

			
			SingleCategoryBO.getInstance().addSingleCategoryUserGroupByCode(code,name,categoryId,type);
		} catch (BOException e) {
			log.error(e);
			this.saveMessagesValue(request, "新增货架用户组信息出错");
			return mapping.findForward(forward);
		}

		this.saveMessagesValue(request, "新增货架用户组信息成功！");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL, "../singlecategory/singleCategoryUserGroupAction.do?opType=doQueryList");
		return mapping.findForward(forward);
	}

	private ActionForward doQueryCategoryList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws BOException {
		if (log.isDebugEnabled()) {
			log.debug("doQueryCategoryList()");
		}
		String forward = "toCategory";

		// 从请求中获取货架条件
		String categoryId = this.getParameter(request, "categoryId").trim();
		String categoryName = this.getParameter(request, "categoryName").trim();
		String relation = this.getParameter(request, "relation").trim();
		String isFirst = this.getParameter(request, "isFirst").trim();

		PageResult page = new PageResult(request);

		page.setPageSize(Integer.parseInt("10000"));

		// 根据获取的参数查询货架策略规则表信息
		if (isFirst.equals("1"))// 第一次不需要查询
		{
			page.excute(new ArrayList(0));
			request.setAttribute("notice", "请输入条件查询");
		} else {
			SingleCategoryBO.getInstance().queryCategoryList(page, categoryId,
					categoryName,relation);
		}

		// 将list放置到page中用于分页展示
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("categoryName", categoryName);
		request.setAttribute("relation", relation);
		request.setAttribute("PageResult", page);

		return mapping.findForward(forward);
	}

	private ActionForward doQueryList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (log.isDebugEnabled()) {
			log.debug("doQueryList in.............");
		}
		String forward = Constants.FORWARD_COMMON_FAILURE;

		String code = this.getParameter(request, "code").trim();
		String name = this.getParameter(request, "name").trim();
		String type = this.getParameter(request, "type").trim();
		
		// 实现分页，列表对象是保存在分页对象PageResult中的。
		PageResult page = new PageResult(request);

		page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
		try {
			SingleCategoryBO.getInstance().querySingleCategoryUserGroupList(
					page, code, name,type);
			request.setAttribute("PageResult", page);
			request.setAttribute("code", code);
			request.setAttribute("name", name);
			request.setAttribute("type", type);
			
			forward = "list";
		} catch (Exception e) {
			this.saveMessages(request, "查询货架用户组信息列表失败");
		}
		return mapping.findForward(forward);
	}
}
