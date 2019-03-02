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
import com.aspire.ponaadmin.common.rightmanager.RoleVO;
import com.aspire.ponaadmin.web.BaseAction;

/**
 * <p>通过roleID得到角色信息的action</p>
 * <p>action表示后续动作，=view表示查看用户信息，=edit表示修改用户信息。action为其它值就抛异常。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class GetRoleByIDAction
    extends BaseAction
{

	/**
	 * 输出debug日志
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger(GetRoleByIDAction.class) ;

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

    public ActionForward doPerform (ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
        throws BOException
    {
        LOGGER.debug("GetRoleByIDAction()") ;

        String roleID = request.getParameter("roleID") ;
        LOGGER.debug("roleID="+roleID) ;
        String action = request.getParameter("action") ;
        LOGGER.debug("action="+action) ;

        RightManagerBO rightManager = RightManagerBO.getInstance();
        String forward = null ;
        RoleVO roleVO = rightManager.getRoleByID(roleID) ;
        request.setAttribute("roleVO", roleVO) ;
        //获取操作权限
        List list = rightManager.getRoleRights(roleID, RightModel.getInstance().getType()) ;
        request.setAttribute("roleRight", list) ;
        if ("view".equals(action))
        {
            forward = "viewRoleInfo" ;
        }
        else if("edit".equals(action))
        {
            forward = "editRoleInfo" ;
        }
        else
        {
            throw new BOException("action [" + action + "]  not support!");
        }

        return mapping.findForward(forward) ;

    }

}
