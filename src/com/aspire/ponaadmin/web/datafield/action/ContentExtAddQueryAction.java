
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
 * ��ѯҪ��ӻ��Ӧ���б��Action
 * </p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class ContentExtAddQueryAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentExtAddQueryAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        logger.debug("ContentExtQueryAction............doPerform()");
        String contentID = this.getParameter(request, "contentID");
        String name = this.getParameter(request, "name");
        String spName = this.getParameter(request, "spName");

        String pageSize = this.getParameter(request, "pageSize");
        if (logger.isDebugEnabled())
        {
            logger.debug(" contentID=" + contentID + " name=" + name
                         + " spName=" + spName + " pageSize=" + pageSize);
        }

        if ("".equals(pageSize.trim()))
        {
            pageSize = PageSizeConstants.page_DEFAULT;
        }

        // ��ȡ�����µ�����
        PageResult page = new PageResult(request, Integer.parseInt(pageSize));
        
        
        //add by aiyan 2011-11-16��������������û��ֵ��ʱ���ǲ��ò�ѯ�ġ��Լ���������ѯѹ����
        if((name!=null&&!name.trim().equals(""))||(spName!=null&&!spName.trim().equals(""))){
        	ContentExtBO.getInstance().queryContentExtQueryList(page,
                    contentID,
                    name,
                    spName);
        }

        

        if (logger.isDebugEnabled())
        {
            logger.debug("��ѯContentExtListAction�ɹ�");
        }
        request.setAttribute("PageResult", page);
        request.setAttribute("pageSize", pageSize);

        String forward = "showList";

        return mapping.findForward(forward);
    }
}
