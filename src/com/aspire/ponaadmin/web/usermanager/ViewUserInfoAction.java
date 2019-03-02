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
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;

/**
 * <p>查看一个用户个人信息的action类</p>
 * <p>查看一个用户个人信息的action类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ViewUserInfoAction
    extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ViewUserInfoAction.class) ;

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
    	LOG.debug("ViewUserInfoAction()");

        String userID = this.getParameter(request, "userID").trim();

        //调用业务逻辑处理方法
        UserVO userVO = UserManagerBO.getInstance().getUserByID(userID);

        //把用户VO对象传递给填写用户信息页面
        request.setAttribute("userVO", userVO);

        String forward = "viewUserInfo";
        return mapping.findForward(forward);
    }
}
