
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
import com.aspire.ponaadmin.web.datafield.KeyBaseConfig;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * <p>
 * 查询KEYBASE列表的Action
 * </p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class KeyBaseListAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(KeyBaseListAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        logger.debug("KeyBaseListAction............doPerform()");

        String keyid = this.getParameter(request, "keyid");
        String keyname = this.getParameter(request, "keyname");
        String keytable = this.getParameter(request, "keytable");
        String keydesc = this.getParameter(request, "keydesc");

        String pageSize = this.getParameter(request, "pageSize");
        if (logger.isDebugEnabled())
        {
            logger.debug(" keyid=" + keyid + " keyname=" + keyname
                         + " keytable=" + keytable + " =keydesc" + keydesc);
        }

        if ("".equals(pageSize.trim()))
        {
            pageSize = PageSizeConstants.page_DEFAULT;
        }

        // 获取分类下的内容
        PageResult page = new PageResult(request, Integer.parseInt(pageSize));
        KeyBaseBO.getInstance().queryKeyBaseList(page,
                                                 keyid,
                                                 keyname,
                                                 keytable,
                                                 keydesc);
        String[] keytableArr = KeyBaseConfig.get("keytable").trim().split(",");
        if (logger.isDebugEnabled())
        {
            logger.debug("查询KEYBASE列表成功");
        }
        request.setAttribute("PageResult", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keytableArr", keytableArr);
        String forward = "showList";

        return mapping.findForward(forward);
    }
}
