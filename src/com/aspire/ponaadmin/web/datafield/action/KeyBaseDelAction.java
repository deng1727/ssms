
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
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;

/**
 * <p>删除扩展属性的Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class KeyBaseDelAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(KeyBaseDelAction.class);

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
            logger.debug("KeyBaseDelAction()");
        }
        logger.debug("doPerform()");
        String[] keyBaseDel = request.getParameterValues("keyBaseDel");
        if (logger.isDebugEnabled())
        {
            logger.debug("keyBaseDel");
        }
        String forward = null;
        
		String actionType = "删除扩展属性";
		boolean actionResult = true;
		String actionDesc = "";
		StringBuffer sb = new StringBuffer();
		for(String temp : keyBaseDel)
		{
			sb.append(temp).append(".");
		}
		String actionTarget = sb.toString();
		
        try
        {
            KeyBaseBO.getInstance().keyBaseDel(keyBaseDel);
            this.saveMessages(request, "删除扩展属性成功!");
            forward = Constants.FORWARD_COMMON_SUCCESS;
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessages(request, "删除扩展属性失败!");
            forward = Constants.FORWARD_COMMON_FAILURE;

			// 写操作日志
			actionResult = false;
			actionDesc = "删除扩展属性失败!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
        }
        
		// 写操作日志
		actionDesc = "删除扩展属性成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute(Constants.PARA_GOURL, "keyBaseList.do");
        return mapping.findForward(forward);
    }
}
