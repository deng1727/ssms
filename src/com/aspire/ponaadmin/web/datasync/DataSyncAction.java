package com.aspire.ponaadmin.web.datasync;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

public class DataSyncAction extends BaseAction
{
	private static JLogger logger=LoggerFactory.getLogger(DataSyncAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		String action=request.getParameter("action");
		String forward;
		if("taskList".equals(action))
		{
			forward=taskList(request, response);
		}else if("doTask".equals(action))
		{
			forward=startTask(request, response);
		}else if("queryTask".equals(action))
		{
			forward=queryTaskInfo(request, response);
		}
		else
		{
			throw new BOException("没有此请求参数："+action);
		}
		return mapping.findForward(forward);
	}
	
	private String taskList(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward="dataImport";
		if(logger.isDebugEnabled())
		{
			logger.debug("获取当前数据同步的任务列表");
		}
		try
		{
			Collection collection=DataSyncTaskCenter.getInstance().getAllTask();
			request.setAttribute("collection", collection);
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "获取当前数据同步的任务列表失败");
			forward=Constants.FORWARD_COMMON_FAILURE;
		}
		return forward;
	}
	private String startTask(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward="waitPage";
        String  taskName=this.getParameter(request, "taskName");
        if(logger.isDebugEnabled())
		{
			logger.debug("数据同步任务:"+taskName+"手动导入开始。。。。");
		}
        DataSyncTask task= DataSyncTaskCenter.getInstance().getDataSynTask(taskName);
        if(task==null)
        {
        	forward=Constants.FORWARD_COMMON_FAILURE;
        	saveMessagesValue(request, "没有此任务："+taskName);
        }else if(task.getState()==DataSyncConstants.TASK_FREE)
        {
        	DataSyncTaskCenter.getInstance().runTask(taskName);
        }else
        {
        	/*forward=Constants.FORWARD_COMMON_FAILURE;
        	saveMessagesValue(request, "任务："+taskDesc+",正在运行，请稍后再试。");*/
        	//转入等待页面
        	return queryTaskInfo(request,response);
        }
		
		//saveMessagesValue(request, taskDesc+"已经开始执行，结果请查看管理员邮件");
        String user=UserManagerBO.getInstance().getUserSessionVO(request.getSession()).getUser().getName();
        request.setAttribute("user", user);
        request.setAttribute("taskName", taskName);
        request.setAttribute("taskDesc", task.getDesc());
        request.setAttribute("startDate", new Date());
        request.setAttribute("processTime", "00:00:00");
		
		return forward;
	}
	private String queryTaskInfo(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward="waitPage";
        String  taskName=this.getParameter(request, "taskName");
        DataSyncTask task= DataSyncTaskCenter.getInstance().getDataSynTask(taskName);
        if(task==null)
        {
        	forward=Constants.FORWARD_COMMON_FAILURE;
        	saveMessagesValue(request, "没有此任务："+taskName);
        	return forward;
        }else if(task.getState()==DataSyncConstants.TASK_FREE)
        {
        	if(task.getExcuteResult())//执行成功
        	{
        		forward=Constants.FORWARD_COMMON_SUCCESS;
        		int successCount=task.getSuccessAdd()+task.getSuccessDelete()+task.getSuccessUpdate();
        		int failureCount=task.getFailureCheck()+task.getFailureProcess();
            	saveMessagesValue(request, task.getDesc()+"已经执行成功。其中成功处理"+successCount+"条，失败处理"+failureCount+"条");
            	request.setAttribute(Constants.PARA_GOURL, "data_import.jsp?subSystem=ssms");
                 //(request, Constants.PARA_GOURL, "javascript:history.go(-2);");
            	return forward;
        	}else   //失败
        	{
        		forward=Constants.FORWARD_COMMON_FAILURE;
            	saveMessagesValue(request, task.getDesc()+"执行失败，原因："+task.getFailureReason());
            	request.setAttribute(Constants.PARA_GOURL, "data_import.jsp?subSystem=ssms");
                 //(request, Constants.PARA_GOURL, "javascript:history.go(-2);");
            	return forward;
        	}
        	
        }
        //获取当前任务的信息。
        Date startDate=task.startDate;
        Date curDate=new Date();
        long between=(curDate.getTime()-startDate.getTime())/1000;//除以1000是为了转换成秒

       // long day=between/(24*3600);
        long hour=between/3600;
        long minute=between%3600/60;
        long second=between%60;
        String processTime=hour+":"+minute+":"+second;
        String user=UserManagerBO.getInstance().getUserSessionVO(request.getSession()).getUser().getName();
        request.setAttribute("user", user);
        request.setAttribute("taskName", taskName);
        request.setAttribute("taskDesc", task.getDesc());
        request.setAttribute("startDate", startDate);
        request.setAttribute("processTime", processTime);
		
		return forward;
	}
	

}
