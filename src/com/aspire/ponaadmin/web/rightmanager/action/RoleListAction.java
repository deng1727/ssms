package com.aspire.ponaadmin.web.rightmanager.action;

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
import com.aspire.ponaadmin.web.rightmanager.RightBO;

/**
 * <p>获取系统中所有的角色信息的action</p>
 * <p>获取系统中所有的角色信息的action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RoleListAction extends BaseAction
{

    /**
     * 输出debug日志
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger (RoleListAction.class) ;

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
                    HttpServletResponse response) throws BOException
    {
        LOGGER.debug("RoleListAction()") ;
        String forward = null;

        String name = this.getParameter(request, "name");
        String desc = this.getParameter(request, "desc");

        String rightID = this.getParameter(request, "rightID");
        String rightType = this.getParameter(request, "rightType");
        if(rightType.equals("") || rightID.equals(""))
        {
            rightType = "0";
        }

        RightBO rightBO = RightBO.getInstance();

        //实现分页的查询
        PageResult page = new PageResult(request) ;
        rightBO.searchRole(page, name, desc, Integer.parseInt(rightType), rightID) ;
        request.setAttribute("PageResult", page);
        forward = "getAllRole" ;
        return mapping.findForward(forward) ;
    }

}
