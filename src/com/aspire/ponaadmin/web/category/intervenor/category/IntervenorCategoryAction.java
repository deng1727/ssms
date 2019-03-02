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
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(IntervenorCategoryAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		if (logger.isDebugEnabled()) {
			logger.debug("doPerform()");
		}

		// �������л�ȡ��������
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
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
			return mapping.findForward(forward);
		}
	}

	/**
	 * ���ڲ�ѯ����Ϣ
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
			this.saveMessagesValue(request, "��ѯ����Ϣ�б����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("actionType", forward);
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}

	/**
	 * �����������ʱ��ʾ�������б�
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

		// ����ǵ�һ�ν���ҳ�棬����ѯ���
		if (!"1".equals(isFirst)) {
			try {
				IntervenorCategoryBO.getInstance().queryIntervenorVOList(page,
						id, name);
			} catch (BOException e) {
				logger.error(e);
				this.saveMessagesValue(request, "�������ʱ��ʾ�������б����");
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
	 * ������ʾ��ǰ���������Ĺ������
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
			// ���ں����ı䶯�����ﲻ���õڶ��ַ�����ȡ���������������ôҪ��ȫ���ж������в�ѯ�������붼��һ��������
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
			this.saveMessagesValue(request, "��ѯ���������Ĺ������ʱ����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		if(vo == null)
		{
			forward = Constants.FORWARD_COMMON_SUCCESS;
			this.saveMessagesValue(request, "ɾ�����������Ĺ����ɹ���");
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
			this.saveMessagesValue(request, "��ѯ���������Ĺ������ʱ����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("actionType", forward);
		request.setAttribute("list", list);
		return mapping.findForward(forward);
	}

	/**
	 * �������ð���������������Ϣ
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

		// �������л�ȡ
		String categoryId = this.getParameter(request, "id").trim();
		String intervenorId = this.getParameter(request, "intervenorId").trim();
		String sortId = this.getParameter(request, "sortId").trim();

		String actionType = "���ð�������������";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId + "-" + intervenorId + "-" + sortId;
		
		if (logger.isDebugEnabled()) {
			logger.debug("�������л�ȡ��ԤcategoryId��" + categoryId + ", intervenorId="
					+ intervenorId + ", sortId=" + sortId);
		}

		try {
			// ���ð���������������Ϣ
			IntervenorCategoryBO.getInstance().setSortId(categoryId,
					intervenorId, sortId);
		} catch (BOException e) {
			// д������־
			actionResult = false;
			actionDesc = "���ð�������������ʱ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			logger.error(e);
			this.saveMessagesValue(request, "���ð�������������ʱ����");
			return mapping.findForward(forward);
		}
		// д������־
		actionDesc = "���ð�������������ɹ�!";
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
	 * ����ɾ�����е�������Ϣ
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

		// �������л�ȡ
		String categoryId = this.getParameter(request, "categoryId").trim();
		String intervenorId = this.getParameter(request, "intervenorId").trim();
		String type = "&type=1";
		
		String actionType = "ɾ�����е�������Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId + "-" + intervenorId;

		if (logger.isDebugEnabled()) {
			logger.debug("�������л�ȡ��ԤcategoryId��" + categoryId + ", intervenorId="
					+ intervenorId);
		}

		try {
			// ���ð���������������Ϣ
			IntervenorCategoryBO.getInstance().delIntervenorIdByCategoryId(
					categoryId, intervenorId);
		} catch (BOException e) {
			// д������־
			actionResult = false;
			actionDesc = "ɾ�����е�������Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			logger.error(e);
			this.saveMessagesValue(request, "ɾ�����е�������Ϣʱ����");
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
		// д������־
		actionDesc = "ɾ�����е�������Ϣ�ɹ�!";
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
	 * ���ڹ�������Ϣ���˹���Ԥ�����Ĺ�ϵ
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
		
		// �������л�ȡ��Ϣ
		String categoryId = this.getParameter(request, "categoryId").trim();
		String intervenorId = this.getParameter(request, "intervenorId").trim();
		String type = this.getParameter(request, "type").trim();
		
		String actionType = "�����˹���Ԥ������Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId + "-" + intervenorId;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�������л�ȡ��Ϣ��categoryId=" + categoryId
					+ ", intervenorId=" + intervenorId);
		}
		
		String[] c = categoryId.split(",");
		for (int i = 0; i < c.length; i++)
		{
			try
			{
				// �����˹���Ԥ����
				if (!IntervenorCategoryBO.getInstance().hasInternor(c[i],
						intervenorId))
				{
					IntervenorCategoryBO.getInstance()
							.addCategoryVOByIntervenor(c[i], intervenorId, type);
				}
			}
			catch (BOException e)
			{
				// д������־
				actionResult = false;
				actionDesc = "�����˹���Ԥ������Ϣ����!";
		        this.actionLog(request,
		                       actionType,
		                       actionTarget,
		                       actionResult,
		                       actionDesc);
		        
				logger.error(e);
				this.saveMessagesValue(request, "�����˹���Ԥ������Ϣ����");
				return mapping.findForward(forward);
			}
			
		}
		// д������־
		actionDesc = "�����˹���Ԥ������Ϣ�ɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "�����˹���Ԥ������Ϣ�ɹ���");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"categoryList.do?actionType=listCategory");
		return mapping.findForward(forward);
	}

	/**
	 * ִ��������Ԥ����
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

		// �������л�ȡ��Ϣ
		String categoryId = this.getParameter(request, "id").trim();

		String actionType = "ִ��������Ԥ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		
		if (logger.isDebugEnabled()) {
			logger.debug("�������л�ȡ��Ϣ��categoryId=" + categoryId);
		}

		// ִ��������Ԥ����
		try {
			IntervenorTools.getInstance().intervenorCategory(categoryId,
					RepositoryConstants.SYN_HIS_NO);
		} catch (BOException e) {
			// д������־
			actionResult = false;
			actionDesc = "ִ��������Ԥ��������!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			logger.error(e);
			this.saveMessagesValue(request, "ִ��������Ԥ��������");
			return mapping.findForward(forward);
		}
		// д������־
		actionDesc = "ִ��������Ԥ�ɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "ִ��������Ԥ�ɹ���");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"categoryList.do?actionType=listCategory");
		return mapping.findForward(forward);
	}

	/**
	 * ִ�����������������߸�Ԥ����
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

		// �������л�ȡ��Ϣ
		String[] categoryIds = request.getParameterValues("exeId");
		System.out.println(categoryIds + "==============");
		if (logger.isDebugEnabled()) {
			logger.debug("�������л�ȡ��Ϣ��categoryId=" + categoryIds);
		}

		// ִ��������Ԥ����
		try {
			for (int i = 0; i < categoryIds.length; i++) {
				String temp = categoryIds[i];
				IntervenorTools.getInstance().intervenorCategory(temp,
						RepositoryConstants.SYN_HIS_YES);
				
				//��ѯt_a_messages���е�categoryId
				String categoryId = IntervenorCategoryBO.getInstance().queryCategoryListById(temp);
				//���ݲ�ѯ����categoryId,ȥ��Ϣ��������Ϣ
				IntervenorCategoryBO.getInstance().insertMessagesList(categoryId) ;
				
			}
		} catch (BOException e) {
			logger.error(e);
			this.saveMessagesValue(request, "ִ�����������������߸�Ԥ��������");
			return mapping.findForward(forward);
		}
	
		this.saveMessagesValue(request, "ִ�����������������߸�Ԥ�ɹ���");
		
		
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"categoryList.do?actionType=listCategory");
		return mapping.findForward(forward);
	}
}
