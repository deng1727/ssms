package com.aspire.ponaadmin.web.rightmanager.action ;

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
 * <p>选择权限的action类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ChooseRightAction
    extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOGGER = LoggerFactory.getLogger(ChooseRightAction.class) ;

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
        LOGGER.debug("ChooseRightAction()");
//        String rightType = this.getParameter(request, "rightType");

        String forward = "right";
//        if(Integer.parseInt(rightType) == RightManagerConstant.TYPE_SITE)
//        {
//            forward = "siteRight";
//            
//            SiteNode siteNode = NodeMgr.getInstance().getSiteNode();
//            request.setAttribute("siteModel", siteNode);
//        }

        return mapping.findForward(forward);
    }
}
