package com.aspire.ponaadmin.web.repository.singlecategory.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.repository.singlecategory.bo.SingleCategoryBO;

/**
 * <p>查看资源栏目的货架树的Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class SingleCategoryTreeAction extends BaseAction
{

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(SingleCategoryTreeAction.class);

	/**
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws BOException
	 * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		List allCategoryList = null;
		allCategoryList = (List) request.getSession().getAttribute("allCategoryList");
		String userId = (String)request.getSession().getAttribute("userId");
		if(request.getParameter("userId")!=null)
		{
			userId=request.getParameter("userId");
			request.getSession().setAttribute("userId",userId);
		}
		//if (allCategoryList == null)
		/*{
			allCategoryList = SingleCategoryBO.getInstance().getAllCategoryList(userId,"1");
		}*/
	
		LOG.debug("doPerform()");
		request.setAttribute("userId", userId);
		request.setAttribute("type", "1");
		return mapping.findForward("showSingleCategoryTree");
	}
}
