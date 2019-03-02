package com.aspire.ponaadmin.web.rightmanager.action ;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
import com.aspire.ponaadmin.common.rightmanager.RoleVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>删除角色信息的action</p>
 * <p>根据roleID参数用于识别，result 表示删除的结果；</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class DelRoleAction
    extends BaseAction
{

    /**
     * 输出debug日志
     */
    protected static JLogger loggers = LoggerFactory.getLogger(DelRoleAction.class) ;

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
        loggers.debug("DelRoleAction()") ;
        String forward = null ;
        String roleID = request.getParameter("roleID") ;
        loggers.debug("roleID===" + roleID) ;
        //不能修改超级管理员
        if(roleID.equals("1"))
        {
            throw new BOException("A hijack access found!Can't modify role 超级管理员!");
        }

        RightManagerBO rightManager = RightManagerBO.getInstance() ;
        String name = roleID;
        try
        {
            RoleVO vo = rightManager.getRoleByID(roleID);
            name = vo.getName();
        }
        catch(Exception e)
        {
            loggers.error(e);
            name = roleID;
        }
        int result = rightManager.delRole(roleID) ;
        boolean actionResult = false;
        if (result == RightManagerConstant.SUCC)
        {
            //成功
            loggers.debug("delete Nomal!") ;
            forward = Constants.FORWARD_COMMON_SUCCESS ;
            request.setAttribute(Constants.PARA_GOURL, "roleList.do") ;
            this.saveMessages(request, ResourceConstants.WEB_INF_DELROLE_OK) ;
            actionResult = true;
        }
        else if (result == RightManagerConstant.ROLE_USED)
        {
            //角色被使用
            loggers.debug("role used!") ;
            this.saveMessages(request, ResourceConstants.WEB_ERR_ROLE_USED) ;
            forward = Constants.FORWARD_COMMON_FAILURE ;
        }
        else
        {
            //其它内部错误
            loggers.debug("delete error!") ;
            this.saveMessages(request, ResourceConstants.WEB_INF_DELROLE_ERR) ;
            forward = Constants.FORWARD_COMMON_FAILURE ;
        }

        //写操作日志
        {
            String logActionType = "删除角色";
            String logActionTarget = name;
            String logDesc = "";
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }

        return mapping.findForward(forward) ;
    }
}
