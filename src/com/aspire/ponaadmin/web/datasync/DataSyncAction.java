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
			throw new BOException("û�д����������"+action);
		}
		return mapping.findForward(forward);
	}
	
	private String taskList(HttpServletRequest request,HttpServletResponse response)
	{
        String  forward="dataImport";
		if(logger.isDebugEnabled())
		{
			logger.debug("��ȡ��ǰ����ͬ���������б�");
		}
		try
		{
			Collection collection=DataSyncTaskCenter.getInstance().getAllTask();
			request.setAttribute("collection", collection);
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "��ȡ��ǰ����ͬ���������б�ʧ��");
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
			logger.debug("����ͬ������:"+taskName+"�ֶ����뿪ʼ��������");
		}
        DataSyncTask task= DataSyncTaskCenter.getInstance().getDataSynTask(taskName);
        if(task==null)
        {
        	forward=Constants.FORWARD_COMMON_FAILURE;
        	saveMessagesValue(request, "û�д�����"+taskName);
        }else if(task.getState()==DataSyncConstants.TASK_FREE)
        {
        	DataSyncTaskCenter.getInstance().runTask(taskName);
        }else
        {
        	/*forward=Constants.FORWARD_COMMON_FAILURE;
        	saveMessagesValue(request, "����"+taskDesc+",�������У����Ժ����ԡ�");*/
        	//ת��ȴ�ҳ��
        	return queryTaskInfo(request,response);
        }
		
		//saveMessagesValue(request, taskDesc+"�Ѿ���ʼִ�У������鿴����Ա�ʼ�");
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
        	saveMessagesValue(request, "û�д�����"+taskName);
        	return forward;
        }else if(task.getState()==DataSyncConstants.TASK_FREE)
        {
        	if(task.getExcuteResult())//ִ�гɹ�
        	{
        		forward=Constants.FORWARD_COMMON_SUCCESS;
        		int successCount=task.getSuccessAdd()+task.getSuccessDelete()+task.getSuccessUpdate();
        		int failureCount=task.getFailureCheck()+task.getFailureProcess();
            	saveMessagesValue(request, task.getDesc()+"�Ѿ�ִ�гɹ������гɹ�����"+successCount+"����ʧ�ܴ���"+failureCount+"��");
            	request.setAttribute(Constants.PARA_GOURL, "data_import.jsp?subSystem=ssms");
                 //(request, Constants.PARA_GOURL, "javascript:history.go(-2);");
            	return forward;
        	}else   //ʧ��
        	{
        		forward=Constants.FORWARD_COMMON_FAILURE;
            	saveMessagesValue(request, task.getDesc()+"ִ��ʧ�ܣ�ԭ��"+task.getFailureReason());
            	request.setAttribute(Constants.PARA_GOURL, "data_import.jsp?subSystem=ssms");
                 //(request, Constants.PARA_GOURL, "javascript:history.go(-2);");
            	return forward;
        	}
        	
        }
        //��ȡ��ǰ�������Ϣ��
        Date startDate=task.startDate;
        Date curDate=new Date();
        long between=(curDate.getTime()-startDate.getTime())/1000;//����1000��Ϊ��ת������

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
