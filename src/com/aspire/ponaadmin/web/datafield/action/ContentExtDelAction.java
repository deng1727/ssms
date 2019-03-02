
package com.aspire.ponaadmin.web.datafield.action;

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
import com.aspire.ponaadmin.web.datafield.bo.ContentExtBO;

/**
 * <p>
 * 删除应用活动扩展属性的Action
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentExtDelAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentExtDelAction.class);

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
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ContentExtDelAction()");
        }
        String[] idDel = request.getParameterValues("idDel");
        if (logger.isDebugEnabled())
        {
            logger.debug("idDel");
        }
        String forward = null;
        try
        {
            ContentExtBO.getInstance().idDel(idDel);
            this.saveMessages(request, "删除应用活动扩展属性成功!");
            forward = Constants.FORWARD_COMMON_SUCCESS;
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessages(request, "删除应用活动扩展属性成功!");
            forward = Constants.FORWARD_COMMON_FAILURE;

        }
        request.setAttribute(Constants.PARA_GOURL, "contentExtList.do");
        return mapping.findForward(forward);
    }
}
