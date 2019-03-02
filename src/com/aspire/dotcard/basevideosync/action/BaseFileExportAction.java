package com.aspire.dotcard.basevideosync.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.bo.BaseVideoBO;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileFactory;
import com.aspire.dotcard.basevideosync.sync.BaseVideoTask;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

public class BaseFileExportAction extends BaseAction{

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(BaseFileExportAction.class);
	
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
        LOG.debug("doPerform()");
		
		// �������л�ȡ��������
		String perType = this.getParameter(request, "perType").trim();
		
		if ("synVideo".equals(perType))
		{
			return synVideo(mapping, form, request, response);
		}
		else if ("renameDataSync".equals(perType))
		{
			return renameDataSync(mapping, form, request, response);
		}
		else if ("synVideoAll".equals(perType))
		{
			return synVideoAll(mapping, form, request, response);
		}
		else
		{
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
			return mapping.findForward(forward);
		}
	}
	
	/**
     * �»�����Ƶ�����ļ�ͬ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	private ActionForward synVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("�»�����Ƶ�����ļ�ͬ����ʼ");
		}
		// �������л�ȡ��������
		String synVideoType = this.getParameter(request, "synVideoType").trim();
		
		String actionType = "�»�����Ƶ�����ļ�ͬ��";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = synVideoType;
		
		StringBuffer mailText = new StringBuffer();
		
		mailText.append(BaseFileFactory.getInstance().getBaseFile(synVideoType)
				.execution(true));
		
		actionDesc = "�»�����Ƶ�����ļ�ͬ�����";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "�»�����Ƶ�����ļ�ͬ�����");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
	
	/**
     * �»�����Ƶ����ȫ��ͬ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	private ActionForward synVideoAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("ȫ���»�����Ƶ����ͬ���ࣨ�����м���л���������ʼ");
		}
		// �������л�ȡ��������
		String synVideoType = this.getParameter(request, "synVideoAll").trim();
		
		String actionType = "ȫ���»�����Ƶ����ͬ���ࣨ�����м���л�������";
		boolean actionResult = true;
		String actionDesc = "ȫ���»�����Ƶ����ͬ����ɣ������м���л�������";
		String actionTarget = synVideoType;
		
		new BaseVideoTask().run();
		
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "actionDesc");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
	
	/**
     * �»�����Ƶ��Ŀ��������м���л�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	private ActionForward renameDataSync(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("�»�����Ƶ��Ŀ��������м���л���ʼ");
		}
		
		String actionType = "�»�����Ƶ��Ŀ��������м���л�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";
		
		// ���ô洢���� ����ִ���м������ʽ��������ת��
		BaseVideoBO.getInstance().syncMidTableData();
		
		// ���ô洢���� ����ִ�н�Ŀ��Ʒ�ϼܲ���
		BaseVideoBO.getInstance().updateCategoryReference();
		
		actionDesc = "�»�����Ƶ��Ŀ��������м���л����";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, actionDesc);
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}

}
