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
 * 处理报表数据手动导入，入口类。
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
	 * 执行报表导入task
	 * @param taskName
	 */
	private void executImportTask(String taskName,HttpServletRequest request)throws BOException
	{
		if(taskName.equals("a8statistic"))
		{
			new A8StatisticImportTask().run();
			saveMessagesValue(request, "A8报表统计导入成功");
		}else if(taskName.equals("appstatistic"))
		{
			new AppStatisticImportTask().run();
			saveMessagesValue(request, "应用最新(推荐)(精品)(黑名单)(小编推荐)数据导入成功");
		}else if(taskName.equals("contentOfDay"))
		{
			new ContentDayTask().run();
			saveMessagesValue(request, "应用日运营数据导入成功");
		}else if(taskName.equals("contentOfWeek"))
		{
			new ContentWeekTask().run();
			saveMessagesValue(request, "应用周运营数据导入成功");
		}else if(taskName.equals("contentOfMonth"))
		{
			new ContentMonthTask().run();
			saveMessagesValue(request, "应用月运营数据导入成功");
		}else
		{
			throw new BOException("没有此任务类型。type="+taskName);
		}
	}

}
