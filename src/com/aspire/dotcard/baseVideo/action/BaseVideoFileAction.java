/*
 * �ļ�����BaseVideoFileAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoNewFileBO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileFactory;
import com.aspire.dotcard.baseVideoNew.sync.BaseVideoNewTask;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

/**
 * <p>
 * Title:������Ƶ����ͬ����
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class BaseVideoFileAction extends BaseAction
{
	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(BaseVideoFileAction.class);
	
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
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
	
	ActionForward synVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("������Ƶ����ͬ���࿪ʼ");
		}
		// �������л�ȡ��������
		String synVideoType = this.getParameter(request, "synVideoType").trim();
		
		String actionType = "������Ƶ����ͬ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = synVideoType;
		
		StringBuffer mailText = new StringBuffer();
		
		mailText.append(BaseFileFactory.getInstance().getBaseFile(synVideoType)
				.execution(true));
		
		actionDesc = "������Ƶ����ͬ�����";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "������Ƶ����ͬ�����");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
	
	ActionForward synVideoAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("ȫ��������Ƶ����ͬ���ࣨ������ʱ���л���������ʼ");
		}
		// �������л�ȡ��������
		String synVideoType = this.getParameter(request, "synVideoAll").trim();
		
		String actionType = "ȫ��������Ƶ����ͬ���ࣨ������ʱ���л�������";
		boolean actionResult = true;
		String actionDesc = "ȫ��������Ƶ����ͬ����ɣ�������ʱ���л�������";
		String actionTarget = synVideoType;
		
		new BaseVideoNewTask().run();
		
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "ȫ��������Ƶ����ͬ����ɣ�������ʱ���л�������");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
	
	ActionForward renameDataSync(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("�޸�ͬ����ʱ��Ϊ��ʽ��ʼ");
		}
		
		StringBuffer sb = new StringBuffer();
		String actionType = "�޸���Ƶͬ����ʱ��Ϊ��ʽ��";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";
		
		// ���ô洢���� ����ִ���м������ʽ��������ת��
		boolean syncTrue = BaseVideoNewFileBO.getInstance().syncVideoData();
		
		// ���ִ����ȷ
		if (syncTrue)
		{
			sb.append("���ô洢���� ����ִ���м������ʽ��������ת����ɣ�");
			
			// ������վɵ�ģ������ݣ�������ȫ������
			BaseVideoNewFileBO.getInstance().cleanOldSimulationData(sb);
			
			// �������б��������װģ���
			BaseVideoNewFileBO.getInstance().diyDataTable(sb);
			
			// ����ͳ������Ŀ������Ŀ�½�Ŀ��
			BaseVideoNewFileBO.getInstance().updateCategoryNodeNum();
		}
		else
		{
			sb.append("���ô洢���� ����ִ���м������ʽ��������ת��ʱ�������󣬺�������ȡ����");
		}
		
        BaseVideoFileBO.getInstance().sendResultMail("������Ƶ��װģ����ܽ���ʼ�", sb);
		
		actionDesc = "�޸���Ƶͬ����ʱ��Ϊ��ʽ�����";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "�޸���Ƶͬ����ʱ��Ϊ��ʽ�����");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
}
