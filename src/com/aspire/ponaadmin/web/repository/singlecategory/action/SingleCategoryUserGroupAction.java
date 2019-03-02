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
 * �鿴��Դ��Ŀ�Ļ�������Action
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
	 * ��־����
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

		// �������л�ȡ����
		String code = this.getParameter(request, "code").trim();
		String name = this.getParameter(request, "name").trim();
		String categoryId = this.getParameter(request, "categoryId");
		String type = this.getParameter(request, "type").trim();
		if (log.isDebugEnabled()) {
			log.debug("�������л�ȡ������code=" + code + " name=" + name
					+ " categoryId=" + categoryId);

		}
		// �޸Ļ��ܹ�����Ϣ
		try {
			
			SingleCategoryBO.getInstance().updateSingleCategoryUserGroupByCode(code,name,categoryId,type);
		} catch (BOException e) {
			log.error(e);
			this.saveMessagesValue(request, "�޸Ļ����û�����Ϣ����");
			return mapping.findForward(forward);
		}

		this.saveMessagesValue(request, "�޸Ļ����û�����Ϣ�ɹ���");
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
			this.saveMessages(request, "��ת�������û�����Ϣ����ҳ��ʧ��");
		}
		return mapping.findForward(forward);
		
	}

	private ActionForward doDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException {
		String forward = Constants.FORWARD_COMMON_FAILURE;

		// �������л�ȡ����
		String code = this.getParameter(request, "code").trim();
		String type = this.getParameter(request, "type").trim();
		// �޸Ļ��ܹ�����Ϣ
		try {
			SingleCategoryBO.getInstance().delSingleCategoryUserGroupByCode(code,type);
		} catch (BOException e) {
			log.error(e);
			this.saveMessagesValue(request, "ɾ�������û�����Ϣ����");
			return mapping.findForward(forward);
		}

		this.saveMessagesValue(request, "ɾ�������û�����Ϣ�ɹ���");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL, "../singlecategory/singleCategoryUserGroupAction.do?opType=doQueryList");
		return mapping.findForward(forward);
	}

	public ActionForward doAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		String forward = Constants.FORWARD_COMMON_FAILURE;

		// �������л�ȡ����
		String code = this.getParameter(request, "code").trim();
		String name = this.getParameter(request, "name").trim();
		String categoryId = this.getParameter(request, "categoryId");
		String type = this.getParameter(request, "type").trim();
		
		if (log.isDebugEnabled()) {
			log.debug("�������л�ȡ������code=" + code + " name=" + name
					+ " categoryId=" + categoryId);

		}
		// �޸Ļ��ܹ�����Ϣ
		try {
			// ͨ�����������ȡ�Ƿ��Ѵ��ڴ˻���
			CategoryRuleVO categoryRule = CategoryUpdateBO.getInstance()
					.getCategoryRuleVOByID(code);
			if (code.equals(categoryRule.getCid())) {
				log.error("��������û��������Ѵ��ڣ����������룡");
				this.saveMessagesValue(request, "��������û��������Ѵ��ڣ����������룡");
				return mapping.findForward(forward);
			}

			
			SingleCategoryBO.getInstance().addSingleCategoryUserGroupByCode(code,name,categoryId,type);
		} catch (BOException e) {
			log.error(e);
			this.saveMessagesValue(request, "���������û�����Ϣ����");
			return mapping.findForward(forward);
		}

		this.saveMessagesValue(request, "���������û�����Ϣ�ɹ���");
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

		// �������л�ȡ��������
		String categoryId = this.getParameter(request, "categoryId").trim();
		String categoryName = this.getParameter(request, "categoryName").trim();
		String relation = this.getParameter(request, "relation").trim();
		String isFirst = this.getParameter(request, "isFirst").trim();

		PageResult page = new PageResult(request);

		page.setPageSize(Integer.parseInt("10000"));

		// ���ݻ�ȡ�Ĳ�����ѯ���ܲ��Թ������Ϣ
		if (isFirst.equals("1"))// ��һ�β���Ҫ��ѯ
		{
			page.excute(new ArrayList(0));
			request.setAttribute("notice", "������������ѯ");
		} else {
			SingleCategoryBO.getInstance().queryCategoryList(page, categoryId,
					categoryName,relation);
		}

		// ��list���õ�page�����ڷ�ҳչʾ
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
		
		// ʵ�ַ�ҳ���б�����Ǳ����ڷ�ҳ����PageResult�еġ�
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
			this.saveMessages(request, "��ѯ�����û�����Ϣ�б�ʧ��");
		}
		return mapping.findForward(forward);
	}
}
