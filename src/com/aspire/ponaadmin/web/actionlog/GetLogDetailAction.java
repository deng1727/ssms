package com.aspire.ponaadmin.web.actionlog ;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;

/**
 * <p>��ѯһ��������־��ϸ��Ϣ��action��</p>
 * <p>��ѯһ��������־��ϸ��Ϣ��action��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class GetLogDetailAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(GetLogDetailAction.class) ;

    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws BOException
     * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
     */
    public ActionForward doPerform (ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
        throws BOException
    {
    	LOG.debug("GetLogDetailAction()");
        String logID = request.getParameter("logID");
        ActionLogVO log = ActionLogBO.getInstance().getLogByID(logID);
        //�Ѳ�ѯ�������ݴ��ݸ�ҳ��
        request.setAttribute("log", log);
        String forward = "logDetail";
        return mapping.findForward(forward);
    }
}
