package com.aspire.dotcard.a8;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

public class A8DataImportAction extends BaseAction
{
	private static JLogger logger=LoggerFactory.getLogger(A8DataImportAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		String action=request.getParameter("action");
		String forward;
		if("music".equals(action))
		{
			forward=musicImport(request, response);
		}else if("singer".equals(action))
		{
			forward=singerImport(request, response);
		}else if("topList".equals(action))
		{
			forward=topListImport(request, response);
		}else
		{
			throw new BOException("û�д����������"+action);
		}
		return mapping.findForward(forward);
	}
	
	private String musicImport(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward=Constants.FORWARD_COMMON_SUCCESS;
		if(logger.isDebugEnabled())
		{
			logger.debug("A8�������� �ֶ�������ʼ");
		}
		try
		{
			final A8DataImport  task= new A8MusicImport();
			//Ϊ�˱���ǰ̨ҳ��ȴ�ʱ��̫���������̵߳ķ�ʽʵ��
			new Thread() {
				public void run()
				{
					task.process();
				}
			}.start();
			
			saveMessagesValue(request, "A8���ֵ�����ɣ������鿴����Ա�ʼ�");
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "A8���ֵ���ʧ��");
			forward=Constants.FORWARD_COMMON_FAILURE;
		}
		return forward;
	}
	private String singerImport(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward=Constants.FORWARD_COMMON_SUCCESS;
        if(logger.isDebugEnabled())
		{
			logger.debug("A8���ֵ��� �ֶ�������ʼ");
		}
		try
		{
			final A8DataImport  task= new A8SingerImport();
			//Ϊ�˱���ǰ̨ҳ��ȴ�ʱ��̫���������̵߳ķ�ʽʵ��
			new Thread() {
				public void run()
				{
					task.process();
				}
			}.start();
			saveMessagesValue(request, "A8���ֵ�����ɣ������鿴����Ա�ʼ�");
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "A8���ֵ���ʧ��");
			forward=Constants.FORWARD_COMMON_FAILURE;
		}
		return forward;
	}
	private String topListImport(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward=Constants.FORWARD_COMMON_SUCCESS;
        if(logger.isDebugEnabled())
		{
			logger.debug("A8�񵥵��� �ֶ�������ʼ");
		}
		try
		{
			final A8DataImport  task= new A8TopListImport();
			//Ϊ�˱���ǰ̨ҳ��ȴ�ʱ��̫���������̵߳ķ�ʽʵ��
			new Thread() {
				public void run()
				{
					task.process();
				}
			}.start();
			
			saveMessagesValue(request, "A8�񵥵�����ɣ������鿴����Ա�ʼ�");
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "A8�񵥵���ʧ��");
			forward=Constants.FORWARD_COMMON_FAILURE;
		}
		return forward;
	}



}
