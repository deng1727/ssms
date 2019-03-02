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
 * ���˹���
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
            LOG.debug("����doPerform()����");
        }

        // �������л�ȡ��������
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
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
    }

    /**
     * ��ѯ�����������б���Ϣ
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
            LOG.debug("���� query() ����");
        }
        String actionType = "��ѯ������Ϣ";
        String actionDesc = "��ѯ������Ϣ�ɹ�!";
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
            actionDesc = "��ѯ������Ϣ����";
            LOG.error(actionDesc, e);
            // д������־
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

        // д������־
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�뿪 query() ����");
        }
        return mapping.findForward(Constants.FORWARD_QUERY_TOKEN);
    }

    /**
     * ��ѯ������ϸ�б���Ϣ
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
            LOG.debug("���� queryDetail() ����");
        }

        String actionType = "��ѯ������Ϣ";
        String actionDesc = "��ѯ������Ϣ�ɹ�!";
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
            actionDesc = "��ѯ������Ϣ����";
            LOG.error(actionDesc, e);
            // д������־
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
        
        // д������־
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�뿪 query() ����");
        }
        return mapping.findForward("queryDetail");
    }

}
