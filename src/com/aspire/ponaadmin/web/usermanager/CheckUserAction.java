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
 * <p>审核用户注册的action类</p>
 * <p>审核用户注册的action类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class CheckUserAction
    extends BaseAction
{

    /**
     * 日志引用
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger(CheckUserAction.class) ;

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
        LOGGER.debug("CheckUserAction()");
        String userID = this.getParameter(request, "userID").trim();
        //审核是否通过，接受参数：y通过，其它不通过。
        String resultStr = this.getParameter(request, "result").trim();
        boolean result = false;
        if(resultStr.equals("y"))
        {
            result = true;
        }
        String desc = this.getParameter(request, "desc").trim();

        //检查参数
        //userID不能为空；如果审核不通过，描述不能为空
        if (userID.equals("") || (!result && desc.equals("")))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }

        //调用业务逻辑处理方法
        boolean actionResult = false;
        String logDesc = "";
        String logActionType = "注册审核通过";
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        if(result)
        {
            logActionType = "注册审核通过";
        }
        else
        {
            logDesc = desc;
            logActionType = "注册审核不通过";
        }
        try
        {
            UserManagerBO.getInstance().checkRegister(userID, result, desc);
            actionResult = true;
            request.setAttribute(Constants.PARA_GOURL, "queryUncheckUser.do") ;
            forward = Constants.FORWARD_COMMON_SUCCESS;
            //根据是否审核通过显示不同的信息
            if(result)
            {
                this.saveMessages(request, ResourceConstants.WEB_INF_REG_CHECK_SUCC, userID);
            }
            else
            {
                this.saveMessages(request, ResourceConstants.WEB_INF_REG_CHECK_FAIL, userID);
            }
        }
        catch(BOException e)
        {
            LOGGER.error(e);
            actionResult = false;
            this.saveMessages(request, ResourceConstants.WEB_INF_REG_CHECK_ERR);
            forward = Constants.FORWARD_COMMON_FAILURE;
        }

        //写操作日志
        {
            String logActionTarget = userID;
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }

        return mapping.findForward(forward);
    }
}
