
package com.aspire.ponaadmin.web.datafield.action;

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
import com.aspire.ponaadmin.web.datafield.bo.ContentBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * <p>
 * ��ѯ��Ŀ�������б��Action
 * </p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class ContentListAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentListAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        logger.debug("ContentListAction............doPerform()");

        String name = this.getParameter(request, "name");
        String spName = this.getParameter(request, "spName");
        String icpServId = this.getParameter(request, "icpServId");
        String icpCode = this.getParameter(request, "icpCode");
        String contentID = this.getParameter(request, "contentID");

        String provider = this.getParameter(request, "provider");
        String cateName = "";
        String subtype = "";
        if ("O".equals(provider))
        {
            subtype = this.getParameter(request, "subtype");
            cateName = this.getParameter(request, "cateName");
        }

        String pageSize = this.getParameter(request, "pageSize");
        if (logger.isDebugEnabled())
        {
            logger.debug(" name=" + name + " spName=" + spName + " icpServId="
                         + icpServId + " =icpCode" + icpCode + " contentID="
                         + contentID + " provider=" + provider + " cateName="
                         + cateName + " subtype=" + subtype);
        }

        if ("".equals(pageSize.trim()))
        {
            pageSize = PageSizeConstants.page_DEFAULT;
        }

        // ��ȡ�����µ�����
        PageResult page = new PageResult(request, Integer.parseInt(pageSize));
        if (!"".equals(provider))
        {

            ContentBO.getInstance().queryContentList(page,
                                                     name,
                                                     spName,
                                                     icpServId,
                                                     icpCode,
                                                     contentID,
                                                     provider,
                                                     cateName,
                                                     subtype);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("��ѯContentList�ɹ�");
        }
        request.setAttribute("PageResult", page);
        request.setAttribute("pageSize", pageSize);

        String forward = "showList";

        return mapping.findForward(forward);
    }
}
