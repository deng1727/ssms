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
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>修改用户个人信息的action类</p>
 * <p>预注册用户和审核不通过转到注册填写信息页面去。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class EditUserInfoAction
    extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(EditUserInfoAction.class) ;

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
    	LOG.debug("EditUserInfoAction()");
        //从session中取得用户信息
        UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(
            request.getSession()) ;
        String userID = userSession.getUser().getUserID();

        //调用业务逻辑处理方法
        UserVO userVO = UserManagerBO.getInstance().getUserByID(userID);

        //锁定和密码复位的用户是不能进行修改个人信息操作的
        if ((userVO.getState() == UserManagerConstant.STATE_LOCKED) ||
            (userVO.getState() == UserManagerConstant.STATE_PWD_RESET))
        {
            this.saveMessages(request, ResourceConstants.WEB_INF_NO_RIGHT);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }


        //把用户VO对象传递给填写用户信息页面
        request.setAttribute("userVO", userVO);

        String forward = "editUserInfo";
        int userState = userVO.getState();
        if ((userState == UserManagerConstant.STATE_PRE_REGISTER) ||
            (userState == UserManagerConstant.STATE_CHECK_FAIL))
        {
            //预注册用户和审核不通过转到注册填写信息页面去。
            forward = "registerInfo";
        }
        else if(userState == UserManagerConstant.STATE_TO_BE_CHECK)
        {
            //待审核用户不能修改用户信息
            //◎◎◎暂时先采用这种方法，最后应该通过虚拟角色来处理的。
            this.saveMessages(request, ResourceConstants.WEB_INF_NO_RIGHT) ;
            forward = Constants.FORWARD_COMMON_FAILURE;
        }
        return mapping.findForward(forward);
    }
}
