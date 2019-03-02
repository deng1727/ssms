
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
import com.aspire.ponaadmin.web.datafield.bo.ContentExtBO;
import com.aspire.ponaadmin.web.datafield.vo.ContentExtVO;

/**
 * <p>
 * 查询属性字段详情的Action
 * </p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class ContentExtInfoAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentExtInfoAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ContentExtInfoAction...doPerform()");
        }

        String id = this.getParameter(request, "id");
        if (logger.isDebugEnabled())
        {
            logger.debug("id" + id);
        }
        ContentExtVO vo = ContentExtBO.getInstance().getContentExtID(id);
        request.setAttribute("vo", vo);

        String forward = "showInfo";

        return mapping.findForward(forward);

    }
}
