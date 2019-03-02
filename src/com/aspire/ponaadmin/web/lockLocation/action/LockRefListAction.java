package com.aspire.ponaadmin.web.lockLocation.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.lockLocation.bo.LockLocationBO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class LockRefListAction extends BaseAction{

	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String nodeId =  this.getParameter(request, "nodeId");
		String categoryId =  this.getParameter(request, "categoryId");
		String contentId =  this.getParameter(request, "contentId");
		String lockNums =this.getParameter(request, "lockNums");
		//货架名称。（模糊查询）
		String name = this.getParameter(request,"name");
		map.put("categoryId", categoryId);
		map.put("contentId", contentId);
		map.put("name", name);
		PageResult page = new PageResult(request);
		LockLocationBO.getInstance().queryLockRefList(page, map);
		if("".equals(lockNums)){
			lockNums = LockLocationBO.getInstance().getLockNums(map);
		}
		request.setAttribute("PageResult", page);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("contentId", contentId);
		request.setAttribute("name", name);
		request.setAttribute("lockNums", lockNums);
		return mapping.findForward("lockRefList");
	}

	
}
