
package com.aspire.dotcard.reportdata.cystatistic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.cystatistic.bo.CyListBO;
import com.aspire.dotcard.reportdata.cystatistic.bo.TopListBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

/**
 * 应用类TOP榜单及每日创业大赛作品运营属性数据手动导入，入口类。
 * 
 * @author zhangwei
 * 
 */
public class ReportTopListImportAction extends BaseAction
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ReportTopListImportAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ReportTopListImportAction...");
        }
        String actionType = this.getParameter(request, "action");
        if (logger.isDebugEnabled())
        {
            logger.debug("actionType=" + actionType);
        }
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        executImportTask(actionType, request);
        return mapping.findForward(forward);
    }

    /**
     * 执行报表导入task
     * 
     * @param taskName
     */
    private void executImportTask(String taskName, HttpServletRequest request)
                    throws BOException
    {

        if (taskName.equals("cylist"))
        {
            CyListBO.getInstance().service();
            saveMessagesValue(request, "每日创业大赛作品运营属性数据导入成功");
        }
        else if (taskName.equals("0"))
        {
            TopListBO.getInstance().service(0);
            saveMessagesValue(request, "TOP榜单数据全部导入成功");
        }
        else if (taskName.equals("1"))
        {
            TopListBO.getInstance().service(1);
            saveMessagesValue(request, "软件游戏类作品人气综合推荐指数TOP60榜单数据导入成功");
        }
        else if (taskName.equals("2"))
        {
            TopListBO.getInstance().service(2);
            saveMessagesValue(request, "创意孵化类作品人气综合推荐指数TOP30榜单数据导入成功");
        }
        else if (taskName.equals("3"))
        {
            TopListBO.getInstance().service(3);
            saveMessagesValue(request, "软件游戏类作品星探推荐得分TOP50榜单数据导入成功");
        }
        else if (taskName.equals("4"))
        {
            TopListBO.getInstance().service(4);
            saveMessagesValue(request, "创意孵化类作品星探推荐得分TOP50榜单数据导入成功");
        }
        else if (taskName.equals("5"))
        {
            TopListBO.getInstance().service(5);
            saveMessagesValue(request, "市场PK应用累计收入数据导入成功");
        }
        else
        {
            throw new BOException("没有此任务类型。type=" + taskName);
        }
    }

}
