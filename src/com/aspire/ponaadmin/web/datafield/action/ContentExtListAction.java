
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
import com.aspire.ponaadmin.web.datafield.bo.ContentExtBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * <p>
 * 应用活动属性管理查询栏目下列表的Action
 * </p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class ContentExtListAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentExtListAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        logger.debug("ContentExtListAction............doPerform()");
        String contentID = this.getParameter(request, "contentID");
        String name = this.getParameter(request, "name");
        String spName = this.getParameter(request, "spName");
        String type = this.getParameter(request, "type");
        String isrecomm = this.getParameter(request, "isrecomm");
        String date = this.getParameter(request, "date");
        String pageSize = this.getParameter(request, "pageSize");
        if (logger.isDebugEnabled())
        {
            logger.debug(" contentID=" + contentID + " name=" + name
                         + " spName=" + spName + " type=" + type + " pageSize="
                         + pageSize);
        }

        if ("".equals(pageSize.trim()))
        {
            pageSize = PageSizeConstants.page_DEFAULT;
        }

        // 获取分类下的内容
        PageResult page = new PageResult(request, Integer.parseInt(pageSize));
        ContentExtBO.getInstance().queryContentExtList(page,
                                                       contentID,
                                                       name,
                                                       spName,
                                                       type,
                                                       isrecomm,date.replaceAll("-", ""));

        if (logger.isDebugEnabled())
        {
            logger.debug("查询ContentExtListAction成功");
        }
        request.setAttribute("contentID", contentID);
        request.setAttribute("name", name);
        request.setAttribute("spName", spName);
        request.setAttribute("type", type);
        request.setAttribute("isrecomm", isrecomm);
        request.setAttribute("date", date);
        request.setAttribute("PageResult", page);
        request.setAttribute("pageSize", pageSize);

        String forward = "showList";

        return mapping.findForward(forward);
    }
}
