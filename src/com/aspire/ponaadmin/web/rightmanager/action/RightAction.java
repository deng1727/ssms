package com.aspire.ponaadmin.web.rightmanager.action ;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightModel;
import com.aspire.ponaadmin.web.BaseAction;

/**
 * <p>获取一个角色的权限的action类</p>
 * <p>获取到后就进行修改</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RightAction extends BaseAction
{

	/**
	 * 输出debug日志
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger (RightAction.class) ;

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
    public ActionForward doPerform (ActionMapping mapping, ActionForm form
                                    , HttpServletRequest request,
                                    HttpServletResponse response)
         throws BOException
     {
         LOGGER.debug("RightAction()") ;
         String forward = null ;
         String roleID = request.getParameter("roleID") ;

         RightManagerBO rightManager = RightManagerBO.getInstance();
         List roleRightList = rightManager.getRoleRights(roleID, RightModel
				.getInstance().getType());
         request.setAttribute("roleRight", roleRightList) ;

         forward = "editRoleRights";

         return mapping.findForward(forward) ;
     }

}
