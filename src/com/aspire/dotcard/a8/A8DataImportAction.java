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
			throw new BOException("没有此请求参数："+action);
		}
		return mapping.findForward(forward);
	}
	
	private String musicImport(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward=Constants.FORWARD_COMMON_SUCCESS;
		if(logger.isDebugEnabled())
		{
			logger.debug("A8歌曲导入 手动触发开始");
		}
		try
		{
			final A8DataImport  task= new A8MusicImport();
			//为了避免前台页面等待时间太长，采用线程的方式实现
			new Thread() {
				public void run()
				{
					task.process();
				}
			}.start();
			
			saveMessagesValue(request, "A8音乐导入完成，结果请查看管理员邮件");
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "A8音乐导入失败");
			forward=Constants.FORWARD_COMMON_FAILURE;
		}
		return forward;
	}
	private String singerImport(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward=Constants.FORWARD_COMMON_SUCCESS;
        if(logger.isDebugEnabled())
		{
			logger.debug("A8歌手导入 手动触发开始");
		}
		try
		{
			final A8DataImport  task= new A8SingerImport();
			//为了避免前台页面等待时间太长，采用线程的方式实现
			new Thread() {
				public void run()
				{
					task.process();
				}
			}.start();
			saveMessagesValue(request, "A8歌手导入完成，结果请查看管理员邮件");
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "A8歌手导入失败");
			forward=Constants.FORWARD_COMMON_FAILURE;
		}
		return forward;
	}
	private String topListImport(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward=Constants.FORWARD_COMMON_SUCCESS;
        if(logger.isDebugEnabled())
		{
			logger.debug("A8榜单导入 手动触发开始");
		}
		try
		{
			final A8DataImport  task= new A8TopListImport();
			//为了避免前台页面等待时间太长，采用线程的方式实现
			new Thread() {
				public void run()
				{
					task.process();
				}
			}.start();
			
			saveMessagesValue(request, "A8榜单导入完成，结果请查看管理员邮件");
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "A8榜单导入失败");
			forward=Constants.FORWARD_COMMON_FAILURE;
		}
		return forward;
	}



}
