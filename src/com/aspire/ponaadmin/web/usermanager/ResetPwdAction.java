package com.aspire.ponaadmin.web.usermanager ;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerConstant;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>重设用户密码的action类</p>
 * <p>重设用户密码的action类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ResetPwdAction
    extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ResetPwdAction.class) ;

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
    	LOG.debug("ResetPwdAction()");
        String userID = this.getParameter(request, "userID").trim();

        //检查参数
        //userID不能为空；
        if (userID.equals(""))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }

        //调用业务逻辑处理方法
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        boolean actionResult = false;
        try
        {
            String password = UserManagerBO.getInstance().resetUserPW(userID);
            String[] addInfos = {userID, password};
            this.saveMessages(request, ResourceConstants.WEB_INF_RESETPWD_OK, addInfos);
            request.setAttribute(Constants.PARA_GOURL, "queryUser.do");
            forward = Constants.FORWARD_COMMON_SUCCESS;
            actionResult = true;
        }
        catch(BOException e)
        {
        	LOG.error(e);
            this.saveMessages(request, ResourceConstants.WEB_INF_RESETPWD_ERR);
            forward = Constants.FORWARD_COMMON_FAILURE;
            actionResult = false;
        }

        //写操作日志
        {
            String logActionType = "用户密码复位";
            String logActionTarget = userID;
            String logDesc = "";
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }

        return mapping.findForward(forward);
    }
}
