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
 * <p>解锁用户的action类</p>
 * <p>解锁用户的action类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UnlockUserAction
    extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(UnlockUserAction.class) ;

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
    	LOG.debug("UnlockUserAction()");
        String userID = this.getParameter(request, "userID").trim();

        //检查参数
        //userID不能为空；
        if (userID.equals(""))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }

        //调用业务逻辑处理方法
        int result = UserManagerBO.getInstance().unlockUser(userID);

        String forward = Constants.FORWARD_COMMON_SUCCESS;
        boolean actionResult = false;
        if(result == UserManagerConstant.SUCC)
        {
            //成功
            actionResult = true;
            this.saveMessages(request, ResourceConstants.WEB_INF_UNLOCKUSER_OK);
            request.setAttribute(Constants.PARA_GOURL, "queryUser.do");
            forward = Constants.FORWARD_COMMON_SUCCESS;
        }
        else
        {
            //其它错误
            this.saveMessages(request, ResourceConstants.WEB_INF_UNLOCKUSER_ERR);
            forward = Constants.FORWARD_COMMON_FAILURE;
        }
        //写操作日志
        {
            String actionType = "用户解锁";
            String actionTarget = userID;
            String desc = "";
            this.actionLog(request, actionType, actionTarget, actionResult, desc);
        }

        return mapping.findForward(forward);
    }
}
