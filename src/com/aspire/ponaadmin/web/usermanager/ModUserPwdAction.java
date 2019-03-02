package com.aspire.ponaadmin.web.usermanager ;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerConstant;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>修改用户密码的action类</p>
 * <p>修改用户密码的action类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ModUserPwdAction
    extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ModUserPwdAction.class) ;

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
    	LOG.debug("ModUserPwdAction()");
        //从session中取得用户信息
        UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(
            request.getSession()) ;
        UserVO user = userSession.getUser();
        String userID = userSession.getUser().getUserID();
        String newPwd = this.getParameter(request, "newPwd").trim();
        String oldPwd = this.getParameter(request, "oldPwd").trim();

        //检查参数
        if (newPwd.equals("") || oldPwd.equals(""))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }

        //只有正常和密码复位的用户才能修改密码
        if ((user.getState() != UserManagerConstant.STATE_NORMAL) &&
            (user.getState() != UserManagerConstant.STATE_PWD_RESET))
        {
            this.saveMessages(request, ResourceConstants.WEB_INF_NO_RIGHT);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        //调用业务逻辑处理方法
        int result = UserManagerBO.getInstance()
		        .modifyUserPW(userID, oldPwd, newPwd, user.getState());

        String forward = "";
        boolean actionResult = false;
        if(result == UserManagerConstant.SUCC)
        {
            //成功
            this.saveMessages(request, ResourceConstants.WEB_INF_MODPWD_OK);
            forward = Constants.FORWARD_COMMON_SUCCESS;
            request.setAttribute(Constants.PARA_GOURL, "modify_pwd.jsp") ;
            //如果是密码被复位的用户：要设定session里面的状态，并载入权限
            if(user.getState() == UserManagerConstant.STATE_PWD_RESET)
            {
                //如果是密码复位的用户，成功确认后应该到首页上去。
                request.setAttribute(Constants.PARA_GOURL, "../main.jsp") ;
                user.setState(UserManagerConstant.STATE_NORMAL);
                //载入用户权限
                userSession.setRights(RightManagerBO.getInstance().getUserRights(userID));
            }
            actionResult = true;
        }
        else if(result == UserManagerConstant.INVALID_PWD)
        {
            //旧密码错误
            this.saveMessages(request, ResourceConstants.WEB_INF_PWD_ERROR);
            forward = "redo";
        }
        else
        {
            //其它错误
            this.saveMessages(request, ResourceConstants.WEB_INF_MODPWD_ERR);
            forward = Constants.FORWARD_COMMON_FAILURE;
        }

        //写操作日志
        {
            String logActionType = "修改个人密码";
            String logActionTarget = userID;
            String logDesc = "";
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }

        return mapping.findForward(forward);
    }
}
