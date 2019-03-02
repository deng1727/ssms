package com.aspire.dotcard.reportdata;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.dotcard.reportdata.a8statistic.A8StatisticImportTask;
import com.aspire.dotcard.reportdata.appstatistic.AppStatisticImportTask;
import com.aspire.dotcard.reportdata.gcontent.task.ContentDayTask;
import com.aspire.dotcard.reportdata.gcontent.task.ContentMonthTask;
import com.aspire.dotcard.reportdata.gcontent.task.ContentWeekTask;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
/**
 * �����������ֶ����룬����ࡣ
 * @author zhangwei
 *
 */
public class ReportImportAction extends BaseAction
{

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		String actionType=this.getParameter(request, "action");
		String forward=Constants.FORWARD_COMMON_SUCCESS;				
		executImportTask(actionType,request);		
		return mapping.findForward(forward);
	}
	/**
	 * ִ�б�����task
	 * @param taskName
	 */
	private void executImportTask(String taskName,HttpServletRequest request)throws BOException
	{
		if(taskName.equals("a8statistic"))
		{
			new A8StatisticImportTask().run();
			saveMessagesValue(request, "A8����ͳ�Ƶ���ɹ�");
		}else if(taskName.equals("appstatistic"))
		{
			new AppStatisticImportTask().run();
			saveMessagesValue(request, "Ӧ������(�Ƽ�)(��Ʒ)(������)(С���Ƽ�)���ݵ���ɹ�");
		}else if(taskName.equals("contentOfDay"))
		{
			new ContentDayTask().run();
			saveMessagesValue(request, "Ӧ������Ӫ���ݵ���ɹ�");
		}else if(taskName.equals("contentOfWeek"))
		{
			new ContentWeekTask().run();
			saveMessagesValue(request, "Ӧ������Ӫ���ݵ���ɹ�");
		}else if(taskName.equals("contentOfMonth"))
		{
			new ContentMonthTask().run();
			saveMessagesValue(request, "Ӧ������Ӫ���ݵ���ɹ�");
		}else
		{
			throw new BOException("û�д��������͡�type="+taskName);
		}
	}

}
