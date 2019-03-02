
package com.aspire.ponaadmin.web.datafield.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.datafield.bo.ContentBO;
import com.aspire.ponaadmin.web.datafield.vo.ContentVO;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicCategoryBO;

/**
 * <p>
 * 应用内容带动态属性字段查询的Action
 * </p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class ContentInfoAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentInfoAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ContentInfoAction...doPerform()");
        }

        String id = this.getParameter(request, "id");
        if (logger.isDebugEnabled())
        {
            logger.debug("id" + id);
        }
        ContentVO vo = ContentBO.getInstance().getContentByID(id);
        if (logger.isDebugEnabled())
        {
            logger.debug("vo.getContentID()=" + vo.getContentID());
        }
       // Map map = ContentBO.getInstance().getResourceList(vo.getContentID());
        
      List  keyBaseList = ContentBO.getInstance().queryContentKeyBaseList(vo.getContentID());
        
        request.setAttribute("vo", vo);
        request.setAttribute("map", keyBaseList);

        String forward = "showList";

        return mapping.findForward(forward);

    }
}
