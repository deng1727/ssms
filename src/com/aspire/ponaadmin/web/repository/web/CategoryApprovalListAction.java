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
 * ���ܷ���--��������
 * @author shiyangwang
 *
 */
public class CategoryApprovalListAction extends BaseAction{

	/**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryApprovalListAction.class) ;
    
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String operation = request.getParameter("operation");
		map.put("operation", operation);
		//����ID������Էֺŷָ�������Ҫ���д���Ϊ�Զ��Ÿ���������ȥ��ǰ��Ķ��š�
		String categoryId = this.getParameter(request, "categoryId");
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
		map.put("categoryId", categoryId);
		//�������ơ���ģ����ѯ��
		String categoryName = this.getParameter(request,"categoryName");
		map.put("categoryName", categoryName);
		//������ID
		String parentCategoryId = this.getParameter(request,"parentCategoryId");
		map.put("parentCategoryId", parentCategoryId);
		//����״̬
		String approvalStatus = this.getParameter(request,"approvalStatus");
		//��ʼ����ʱ��Ĭ���Ǵ�����״̬"2"
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
