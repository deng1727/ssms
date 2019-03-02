package com.aspire.ponaadmin.web.repository.web;

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
import com.aspire.ponaadmin.web.repository.CategoryApprovalListBO;

/**
 * 货架分类--审批发布
 * @author shiyangwang
 *
 */
public class CategoryApprovalListAction extends BaseAction{

	/**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryApprovalListAction.class) ;
    
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String operation = request.getParameter("operation");
		map.put("operation", operation);
		//货架ID，多个以分号分隔。所以要进行处理为以逗号隔开。并且去掉前后的逗号。
		String categoryId = this.getParameter(request, "categoryId");
		//处理英文输入法的逗号。
		if(categoryId.contains(";")){
			categoryId = categoryId.replace(";", ",");
		}
		//处理中文输入法的逗号。
		if(categoryId.contains("；")){
			categoryId = categoryId.replace("；", ",");
		}
		//处理以逗号开始
		if(categoryId.startsWith(",")&&categoryId.length()>1){
			categoryId = categoryId.substring(1);
		}
		//处理以逗号结束
		if(categoryId.endsWith(",")&&categoryId.length()>1){
			categoryId = categoryId.substring(0,categoryId.length()-1);
		}
		map.put("categoryId", categoryId);
		//货架名称。（模糊查询）
		String categoryName = this.getParameter(request,"categoryName");
		map.put("categoryName", categoryName);
		//父货架ID
		String parentCategoryId = this.getParameter(request,"parentCategoryId");
		map.put("parentCategoryId", parentCategoryId);
		//审批状态
		String approvalStatus = this.getParameter(request,"approvalStatus");
		//开始加载时，默认是待审批状态"2"
		if("".equals(approvalStatus)){
			approvalStatus = "2";
		}
		map.put("approvalStatus", approvalStatus);
		PageResult page = new PageResult(request);
		CategoryApprovalListBO.getInstance().queryCategoryApprovalList(page, map);
		request.setAttribute("PageResult", page);
		request.setAttribute("operation", operation);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("categoryName", categoryName);
		request.setAttribute("parentCategoryId", parentCategoryId);
		request.setAttribute("approvalStatus", approvalStatus);
		
		return mapping.findForward("categoryApprovalList");
	}

}
