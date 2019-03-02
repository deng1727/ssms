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
		//����ID������Էֺŷָ�������Ҫ���д���Ϊ�Զ��Ÿ���������ȥ��ǰ��Ķ��š�
		String categoryId = this.getParameter(request, "categoryId");
		String nodeId =  this.getParameter(request, "nodeId");
		//����Ӣ�����뷨�Ķ��š�
		if(categoryId.contains(";")){
			categoryId = categoryId.replace(";", ",");
		}
		//�����������뷨�Ķ��š�
		if(categoryId.contains("��")){
			categoryId = categoryId.replace("��", ",");
		}
		//�����Զ��ſ�ʼ
		if(categoryId.startsWith(",")&&categoryId.length()>1){
			categoryId = categoryId.substring(1);
		}
		//�����Զ��Ž���
		if(categoryId.endsWith(",")&&categoryId.length()>1){
			categoryId = categoryId.substring(0,categoryId.length()-1);
		}
		map.put("nodeId", nodeId);
		map.put("categoryId", categoryId);
		//�������ơ���ģ����ѯ��
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
