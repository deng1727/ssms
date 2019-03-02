package com.aspire.ponaadmin.web.repository.web ;

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
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.repository.ContentBO;

/**
 * <p>删除内容资源的Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentDelAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ContentDelAction.class) ;

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
        LOG.debug("doPerform()");
        String categoryID = this.getParameter(request, "categoryID");
        String[] dealContents = request.getParameterValues("dealContent");
        String actionType = "删除资源";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
        String forward = null;
        try
        {
            actionTarget = ContentBO.getInstance().delContent(dealContents);
            this.saveMessages(request, "RESOURCE_CONTENT_RESULT_003") ;
            request.setAttribute(Constants.PARA_GOURL,
                                 "../../web/resourcemgr/contentList.do?categoryID="+categoryID) ;
            forward = Constants.FORWARD_COMMON_SUCCESS ;
        }
        catch(BOException e)
        {
            LOG.error(e);
            actionResult = false;
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM) ;
        }
        //写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        return mapping.findForward(forward);
    }
}

