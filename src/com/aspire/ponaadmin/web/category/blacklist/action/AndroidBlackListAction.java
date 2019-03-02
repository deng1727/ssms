package com.aspire.ponaadmin.web.category.blacklist.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.category.blacklist.bo.AndroidBlackListBO;
import com.aspire.ponaadmin.web.constant.Constants;

public class AndroidBlackListAction extends BaseAction{

	/**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(AndroidBlackListAction.class);

    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }
    
    /**
     * ��ѯ�񵥺�����Ԫ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    private ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "query";
        PageResult page = new PageResult(request);
        
        String name = this.getParameter(request, "name").trim();
        String contentId = this.getParameter(request, "contentId");
        
        String actionType = "��ѯ�񵥺�����Ԫ�����б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = name;
        
        try
        {
            AndroidBlackListBO.getInstance().queryTagList(page, name, contentId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ�񵥺�����Ԫ�����б����");
            // д������־
			actionResult = false;
			actionDesc = "��ѯ�񵥺�����Ԫ�����б����";
	        this.actionLog(request,actionType,actionTarget,actionResult,actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        actionDesc = "��ѯ�񵥺�����Ԫ�����б�ɹ�";
        this.actionLog(request,actionType,actionTarget,actionResult,actionDesc);
        
        request.setAttribute("PageResult", page);
        request.setAttribute("name", name);
        request.setAttribute("contentId", contentId);
        
        return mapping.findForward(forward);
    }
    
}
