/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor.category;

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
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorTools;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * @author x_wangml
 * 
 */
public class IntervenorCategoryAction extends BaseAction {

	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(IntervenorCategoryAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		if (logger.isDebugEnabled()) {
			logger.debug("doPerform()");
		}

		// 从请求中获取操作类型
		String action = this.getParameter(request, "actionType").trim();

		if ("listCategory".equals(action)) {
			return listCategory(mapping, form, request, response);
		} else if ("relatingView".equals(action)) {
			return relatingView(mapping, form, request, response);
		} else if ("setSortId".equals(action)) {
			return setSortId(mapping, form, request, response);
		} else if ("del".equals(action)) {
			return del(mapping, form, request, response);
		} else if ("addIntervenor".equals(action)) {
			return addIntervenor(mapping, form, request, response);
		} else if ("relating".equals(action)) {
			return relating(mapping, form, request, response);
		} else if ("exe".equals(action)) {
			return exe(mapping, form, request, response);
		} else if ("exeList".equals(action)) {
			return exeList(mapping, form, request, response);
		} else if("relatingView2".equals(action)){
			return relatingView2(mapping, form, request, response);
		} else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}
	}

	/**
	 * 用于查询榜单信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward listCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String forward = "listCategory";
		PageResult page = new PageResult(request);
		String id = this.getParameter(request, "id").trim();
		String name = this.getParameter(request, "name").trim();

		try {
			IntervenorCategoryBO.getInstance().queryCategoryVOList(page, id,
					name);
		} catch (BOException e) {
			logger.error(e);
			this.saveMessagesValue(request, "查询榜单信息列表出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("actionType", forward);
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}

	/**
	 * 用于添加容器时显示的容器列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward addIntervenor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String forward = "addIntervenor";
		PageResult page = new PageResult(request);
		String isFirst = this.getParameter(request, "isFirst").trim();
		String id = this.getParameter(request, "id").trim();
		String name = this.getParameter(request, "name").trim();

		// 如果是第一次进入页面，不查询结果
		if (!"1".equals(isFirst)) {
			try {
				IntervenorCategoryBO.getInstance().queryIntervenorVOList(page,
						id, name);
			} catch (BOException e) {
				logger.error(e);
				this.saveMessagesValue(request, "添加容器时显示的容器列表出错");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
		}

		request.setAttribute("actionType", forward);
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}

	/**
	 * 用于显示当前榜单与容器的关联情况
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward relatingView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String forward = "relatingView";
		PageResult page = new PageResult(request);
		String id = this.getParameter(request, "id").trim();
		String isAdd = this.getParameter(request, "isAdd").trim();
		String type = this.getParameter(request, "type").trim();

		
		Category vo = null;

		try
		{
			// 由于后来的变动。这里不在用第二种方法获取。如果是新增。那么要在全表中对他进行查询。唉。想都用一个。。。
			if ("true".equals(isAdd) && !"2".equals(type))
			{
				IntervenorCategoryBO.getInstance().queryCategoryList(page, id,
						"");
			}
			else
			{
				IntervenorCategoryBO.getInstance().queryCategoryVOList(page,
						id, "");
			}
			
			if (page.getPageInfo() != null && page.getPageInfo().size() > 0)
			{
				vo = (Category) page.getPageInfo().get(0);
			}
			
			IntervenorCategoryBO.getInstance().queryIntervenorVOByCategory(
					page, id);
		} catch (BOException e) {
			logger.error(e);
			this.saveMessagesValue(request, "查询榜单与容器的关联情况时出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		if(vo == null)
		{
			forward = Constants.FORWARD_COMMON_SUCCESS;
			this.saveMessagesValue(request, "删除榜单与容器的关联成功！");
			request.setAttribute(Constants.PARA_GOURL,
					"categoryList.do?actionType=listCategory");
			return mapping.findForward(forward);
		}
		
		request.setAttribute("actionType", forward);
		request.setAttribute("vo", vo);
		request.setAttribute("id", id);
		request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}

	// add categorys add by aiyan 2012-03-07
	private ActionForward relatingView2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String forward = "relatingView2";
		PageResult page = new PageResult(request);
		String id = this.getParameter(request, "id").trim();
		List list = null;
		try {

				IntervenorCategoryBO.getInstance().queryCategoryList(page, id,
						"");

			if (page.getPageInfo() != null && page.getPageInfo().size() > 0) {
				list = page.getPageInfo();
			}
		} catch (BOException e) {
			logger.error(e);
			this.saveMessagesValue(request, "查询榜单与容器的关联情况时出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("actionType", forward);
		request.setAttribute("list", list);
		return mapping.findForward(forward);
	}

	/**
	 * 用于设置榜单内容器的排序信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward setSortId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String forward = Constants.FORWARD_COMMON_FAILURE;

		// 从请求中获取
		String categoryId = this.getParameter(request, "id").trim();
		String intervenorId = this.getParameter(request, "intervenorId").trim();
		String sortId = this.getParameter(request, "sortId").trim();

		String actionType = "设置榜单内容器的排序";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId + "-" + intervenorId + "-" + sortId;
		
		if (logger.isDebugEnabled()) {
			logger.debug("从请求中获取干预categoryId：" + categoryId + ", intervenorId="
					+ intervenorId + ", sortId=" + sortId);
		}

		try {
			// 设置榜单内容器的排序信息
			IntervenorCategoryBO.getInstance().setSortId(categoryId,
					intervenorId, sortId);
		} catch (BOException e) {
			// 写操作日志
			actionResult = false;
			actionDesc = "设置榜单内容器的排序时出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			logger.error(e);
			this.saveMessagesValue(request, "设置榜单内容器的排序时出错");
			return mapping.findForward(forward);
		}
		// 写操作日志
		actionDesc = "设置榜单内容器的排序成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		ActionForward actionForward = new ActionForward();
		actionForward
				.setPath("/web/intervenor/categoryRelatingView.do?actionType=relatingView&id="
						+ categoryId);
		return actionForward;
	}

	/**
	 * 用于删除榜单中的容器信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String forward = Constants.FORWARD_COMMON_FAILURE;

		// 从请求中获取
		String categoryId = this.getParameter(request, "categoryId").trim();
		String intervenorId = this.getParameter(request, "intervenorId").trim();
		String type = "&type=1";
		
		String actionType = "删除榜单中的容器信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId + "-" + intervenorId;

		if (logger.isDebugEnabled()) {
			logger.debug("从请求中获取干预categoryId：" + categoryId + ", intervenorId="
					+ intervenorId);
		}

		try {
			// 设置榜单内容器的排序信息
			IntervenorCategoryBO.getInstance().delIntervenorIdByCategoryId(
					categoryId, intervenorId);
		} catch (BOException e) {
			// 写操作日志
			actionResult = false;
			actionDesc = "删除榜单中的容器信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			logger.error(e);
			this.saveMessagesValue(request, "删除榜单中的容器信息时出错");
			return mapping.findForward(forward);
		}

		try
		{
			Integer.valueOf(categoryId);
		}
		catch (Exception e)
		{	
			type = "&type=2";
		}
		// 写操作日志
		actionDesc = "删除榜单中的容器信息成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
		
		ActionForward actionForward = new ActionForward();
		actionForward
				.setPath("/web/intervenor/categoryRelatingView.do?actionType=relatingView&isAdd=true&id="
						+ categoryId + type);
		return actionForward;
	}

	/**
	 * 用于关联榜单信息与人工干预容器的关系
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward relating(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String forward = Constants.FORWARD_COMMON_FAILURE;
		
		// 从请求中获取信息
		String categoryId = this.getParameter(request, "categoryId").trim();
		String intervenorId = this.getParameter(request, "intervenorId").trim();
		String type = this.getParameter(request, "type").trim();
		
		String actionType = "关联人工干预容器信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId + "-" + intervenorId;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("从请求中获取信息：categoryId=" + categoryId
					+ ", intervenorId=" + intervenorId);
		}
		
		String[] c = categoryId.split(",");
		for (int i = 0; i < c.length; i++)
		{
			try
			{
				// 关联人工干预容器
				if (!IntervenorCategoryBO.getInstance().hasInternor(c[i],
						intervenorId))
				{
					IntervenorCategoryBO.getInstance()
							.addCategoryVOByIntervenor(c[i], intervenorId, type);
				}
			}
			catch (BOException e)
			{
				// 写操作日志
				actionResult = false;
				actionDesc = "关联人工干预容器信息出错!";
		        this.actionLog(request,
		                       actionType,
		                       actionTarget,
		                       actionResult,
		                       actionDesc);
		        
				logger.error(e);
				this.saveMessagesValue(request, "关联人工干预容器信息出错");
				return mapping.findForward(forward);
			}
			
		}
		// 写操作日志
		actionDesc = "关联人工干预容器信息成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "关联人工干预容器信息成功！");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"categoryList.do?actionType=listCategory");
		return mapping.findForward(forward);
	}

	/**
	 * 执行容器干预方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward exe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String forward = Constants.FORWARD_COMMON_FAILURE;

		// 从请求中获取信息
		String categoryId = this.getParameter(request, "id").trim();

		String actionType = "执行容器干预方法";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		
		if (logger.isDebugEnabled()) {
			logger.debug("从请求中获取信息：categoryId=" + categoryId);
		}

		// 执行容器干预方法
		try {
			IntervenorTools.getInstance().intervenorCategory(categoryId,
					RepositoryConstants.SYN_HIS_NO);
		} catch (BOException e) {
			// 写操作日志
			actionResult = false;
			actionDesc = "执行容器干预方法出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			logger.error(e);
			this.saveMessagesValue(request, "执行容器干预方法出错");
			return mapping.findForward(forward);
		}
		// 写操作日志
		actionDesc = "执行容器干预成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "执行容器干预成功！");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"categoryList.do?actionType=listCategory");
		return mapping.findForward(forward);
	}

	/**
	 * 执行容器批量紧急上线干预方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward exeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String forward = Constants.FORWARD_COMMON_FAILURE;

		// 从请求中获取信息
		String[] categoryIds = request.getParameterValues("exeId");
		System.out.println(categoryIds + "==============");
		if (logger.isDebugEnabled()) {
			logger.debug("从请求中获取信息：categoryId=" + categoryIds);
		}

		// 执行容器干预方法
		try {
			for (int i = 0; i < categoryIds.length; i++) {
				String temp = categoryIds[i];
				IntervenorTools.getInstance().intervenorCategory(temp,
						RepositoryConstants.SYN_HIS_YES);
				
				//查询t_a_messages表中的categoryId
				String categoryId = IntervenorCategoryBO.getInstance().queryCategoryListById(temp);
				//根据查询到的categoryId,去消息表中填消息
				IntervenorCategoryBO.getInstance().insertMessagesList(categoryId) ;
				
			}
		} catch (BOException e) {
			logger.error(e);
			this.saveMessagesValue(request, "执行容器批量紧急上线干预方法出错");
			return mapping.findForward(forward);
		}
	
		this.saveMessagesValue(request, "执行容器批量紧急上线干预成功！");
		
		
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"categoryList.do?actionType=listCategory");
		return mapping.findForward(forward);
	}
}
