package com.aspire.ponaadmin.web.audit.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.audit.bo.CheckLogBO;
import com.aspire.dotcard.audit.vo.CheckDetailVO;
import com.aspire.dotcard.audit.vo.CheckLogVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

/**
 * 稽核管理
 */
public class CheckLogAction extends BaseAction
{

    private static final JLogger LOG = LoggerFactory.getLogger(CheckLogAction.class);

    /**
     * ACTION
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入doPerform()方法");
        }

        // 从请求中获取操作类型
        String method = this.getParameter(request, "method");

        if ("query".equals(method))
        {
            return query(mapping, form, request, response);
        }
        else if ("queryDetail".equals(method))
        {
            return queryDetail(mapping, form, request, response);
        }
        else
        {
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
    }

    /**
     * 查询动漫黑名单列表信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 query() 方法");
        }
        String actionType = "查询稽核信息";
        String actionDesc = "查询稽核信息成功!";
        String actionTarget = "CheckLogAction.query()";
        boolean actionResult = true;

        String taskid = request.getParameter("taskid") == null ? "" : request.getParameter("taskid");
        String categoryid = request.getParameter("categoryid") == null ? "" : request.getParameter("categoryid");
        String ip = request.getParameter("ip") == null ? "" : request.getParameter("ip");
        String resultcount = request.getParameter("resultcount") == null ? "" : request.getParameter("resultcount");
        String beginDate = this.getParameter(request, "beginDate") == null ? "" : request.getParameter("beginDate");
        String endDate = this.getParameter(request, "endDate") == null ? "" : request.getParameter("endDate");
        
        PageResult page = new PageResult(request);
        CheckLogVO vo = new CheckLogVO();
        vo.setTaskid(taskid);
        vo.setCategoryid(categoryid);
        vo.setIp(ip);
        vo.setBeginDate(beginDate);
        vo.setEndDate(endDate);
        vo.setResultcount(resultcount);
        try
        {
            CheckLogBO.getInstance().queryCheckLogList(page, vo);
        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "查询稽核信息出错";
            LOG.error(actionDesc, e);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute("PageResult", page);
        request.setAttribute("taskid", taskid);
        request.setAttribute("ip", ip);
        request.setAttribute("resultcount", resultcount);
        
        request.setAttribute("categoryid", categoryid);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);

        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 query() 方法");
        }
        return mapping.findForward(Constants.FORWARD_QUERY_TOKEN);
    }

    /**
     * 查询稽核详细列表信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 queryDetail() 方法");
        }

        String actionType = "查询稽核信息";
        String actionDesc = "查询稽核信息成功!";
        String actionTarget = "CheckLogAction.queryDetail()";
        boolean actionResult = true;

        String taskid = request.getParameter("taskid") == null ? "" : request.getParameter("taskid");
        String categoryid = request.getParameter("categoryid") == null ? "" : request.getParameter("categoryid");
        String ip = request.getParameter("ip") == null ? "" : request.getParameter("ip");
        String statusinfo = request.getParameter("statusinfo") == null ? "" : request.getParameter("statusinfo");
        String beginDate = this.getParameter(request, "beginDate") == null ? "" : request.getParameter("beginDate");
        String endDate = this.getParameter(request, "endDate") == null ? "" : request.getParameter("endDate");
        
        PageResult page = new PageResult(request);
        CheckDetailVO vo = new CheckDetailVO();
        vo.setTaskid(taskid);
        vo.setCategoryid(categoryid);
        vo.setIp(ip);
        vo.setBeginDate(beginDate);
        vo.setEndDate(endDate);
        vo.setStatusinfo(statusinfo);
        try
        {
            CheckLogBO.getInstance().queryCheckDetailList(page, vo);
        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "查询稽核信息出错";
            LOG.error(actionDesc, e);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute("PageResult", page);
        request.setAttribute("taskid", taskid);
        request.setAttribute("ip", ip);
        request.setAttribute("categoryid", categoryid);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        
        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 query() 方法");
        }
        return mapping.findForward("queryDetail");
    }

}
