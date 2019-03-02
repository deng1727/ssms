package com.aspire.ponaadmin.web.lockLocation.action;

import java.util.HashMap;
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

public class LockLocationListAction extends BaseAction {

	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		Map<String, Object> map = new HashMap<String, Object>();
		//货架ID，多个以分号分隔。所以要进行处理为以逗号隔开。并且去掉前后的逗号。
		String categoryId = this.getParameter(request, "categoryId");
		String nodeId =  this.getParameter(request, "nodeId");
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
		map.put("nodeId", nodeId);
		map.put("categoryId", categoryId);
		//货架名称。（模糊查询）
		String categoryName = this.getParameter(request,"categoryName");
		map.put("categoryName", categoryName);
		String isLock =  this.getParameter(request,"isLock");
		map.put("isLock", isLock);	
		PageResult page = new PageResult(request);
		LockLocationBO.getInstance().queryLockLocationList(page, map);
		request.setAttribute("PageResult", page);
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("categoryName", categoryName);
		request.setAttribute("isLock", isLock);
		request.setAttribute("lockNums", "");
		return mapping.findForward("lockLocationList");
	}

}
