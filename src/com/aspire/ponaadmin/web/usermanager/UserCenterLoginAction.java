package com.aspire.ponaadmin.web.usermanager ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.GenerateVerifyImg;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserAccessInfoVO;
import com.aspire.ponaadmin.common.usermanager.UserCenterManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerConstant;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.actionlog.ActionLogBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>�û���¼��action��</p>
 * <p>�û���¼��action��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserCenterLoginAction
    extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(UserCenterLoginAction.class) ;

    /**
     * forward loginHint
     */
    private static final String FORWARD_LOGIN_HINT = "loginHint";

    /**
     * forward relogin
     */
    private static final String FORWARD_RELOGIN = "relogin";

    /**
     * forward main
     */
    private static final String FORWARD_MAIN = "main";

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
        
        String tokenId = this.getParameter(request, "MT").trim();
       // String password = this.getParameter(request, "password").trim();
        LOG.debug("UserCenterLoginAction();tokenId="+tokenId);
        //������
        if (tokenId.equals("") )
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);

        }
        
        HashMap hm = UserCenterManagerBO.getInstance().checkLogin(tokenId);
        if(hm == null ){
        	  throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }
        String userID = (String) hm.get("userName");
        if(userID == null ){
        	throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }
        //����ҵ���߼�������
        int result = UserCenterManagerBO.getInstance().userCenterLogin(userID);
        LOG.debug("login result:"+result+".;userid="+userID);
        String forward = "commonSuccess";
        boolean actionResult = false;
        String logDesc = "";
        String logUserName = "";
        String logUserRole = "";
        try
        {
            if(result == UserManagerConstant.SUCC)
            {
                //��¼�ɹ�
                actionResult = true;
                //�����û�������Ϣ�ͽ�ɫ��Ϣ��
                RightManagerBO rightManagerBO = RightManagerBO.getInstance();
                UserVO user = UserManagerBO.getInstance().getUserByID(userID);
                user.setRoleList(rightManagerBO.getUserRoles(user.getUserID()));
                //��¼�û�������Ϣ
                UserAccessInfoVO accessInfo = new UserAccessInfoVO();
                accessInfo.setUserID(userID);
                accessInfo.setLoginTime(System.currentTimeMillis());
                accessInfo.setIP(request.getRemoteAddr());
                //�û�Ȩ��
                List rightList = new ArrayList();

                //�����û���״̬�ж�һ�²���Ҫ�Ĵ���
                if(user.getState() == UserManagerConstant.STATE_NORMAL)
                {
                    //�����û�
                    forward = FORWARD_MAIN;
                    //�����û�Ȩ��
                    rightList = rightManagerBO.getUserRights(userID);
                }
                else if(user.getState() == UserManagerConstant.STATE_PRE_REGISTER)
                {
                    //Ԥע��
                    forward = FORWARD_LOGIN_HINT;
                    request.setAttribute(Constants.PARA_GOURL, "usermanager/editUserInfo.do");
                    this.saveMessages(request, ResourceConstants.WEB_INF_REG_USER_DETAIL);
                    logDesc = "Ԥע���û�";
                    //�������û�Ȩ��
                }
                else if(user.getState() == UserManagerConstant.STATE_TO_BE_CHECK)
                {
                    //������û�
                    forward = FORWARD_RELOGIN;
                    this.saveMessages(request, ResourceConstants.WEB_INF_USER_WAIT_FOR_CHECK);
                    logDesc = "������û�";
                    actionResult = false;
                    //�������û�Ȩ��
                }
                else if(user.getState() == UserManagerConstant.STATE_CHECK_FAIL)
                {
                    //ע����˲�ͨ���û�
                    forward = FORWARD_LOGIN_HINT;
                    request.setAttribute(Constants.PARA_GOURL, "usermanager/editUserInfo.do");
                    this.saveMessages(request, ResourceConstants.WEB_INF_USER_REG_CHECK_FAIL
                                , user.getCheckDESC());
                    logDesc = "ע����˲�ͨ�����û�";
                    //�������û�Ȩ��
                }
                else if(user.getState() == UserManagerConstant.STATE_LOCKED)
                {
                    //�����û�
                    forward = FORWARD_LOGIN_HINT ;
                    request.setAttribute(Constants.PARA_GOURL, "index.jsp") ;
                    this.saveMessages(request, ResourceConstants.WEB_INF_USER_LOCKED);
                    logDesc = "���������û�";
                    actionResult = false;
                    //�������û�Ȩ��
                }
                else if(user.getState() == UserManagerConstant.STATE_PWD_RESET)
                {
                    //���뱻��λ�û�
                    forward = FORWARD_LOGIN_HINT ;
                    request.setAttribute(Constants.PARA_GOURL, "usermanager/modify_pwd.jsp") ;
                    this.saveMessages(request, ResourceConstants.WEB_INF_PWD_RESETED);
                    logDesc = "���뱻��λ���û�";
                    //�������û�Ȩ�ޣ����û��޸������������
                }
                else
                {
                    throw new RuntimeException("unknowned user state!!!");
                }
                //���û����й���Ϣ�����浽UserSessionVO�����У������浽session��
                UserSessionVO userSession = new UserSessionVO();
                userSession.setUser(user);
                userSession.setAccessInfo(accessInfo);
                userSession.setRights(rightList);
                UserManagerBO.getInstance().setUserSessionVO(request.getSession(), userSession);
                if(user != null)
                {
                    logUserName = user.getName() ;
                    logUserRole = user.getUserRolesInfo() ;
                }

            }
            else if(result == UserManagerConstant.USER_NOT_EXISTED)
            {
                forward = FORWARD_LOGIN_HINT;
                request.setAttribute(Constants.PARA_GOURL, "index.jsp");
                this.saveMessages(request, ResourceConstants.WEB_ERR_USER_NOT_EXISTED, userID);
                logDesc = "δע����û�";
            }
            else if(result == UserManagerConstant.INVALID_PWD)
            {
                forward = FORWARD_LOGIN_HINT;
                request.setAttribute(Constants.PARA_GOURL, "index.jsp");
                this.saveMessages(request, ResourceConstants.WEB_INF_INVALID_PWD);
                logDesc = "�������";
            }
            else
            {
                //��¼ʧ��
                request.setAttribute(Constants.PARA_GOURL, "index.jsp");
                forward = FORWARD_LOGIN_HINT;
            }
        }
        catch(Exception e)
        {
            LOG.error(e) ;
            actionResult = false ;
            result = UserManagerConstant.FAIL ;
            forward = FORWARD_LOGIN_HINT;
            request.setAttribute(Constants.PARA_GOURL, "index.jsp");
        }
        request.setAttribute("loginResult", new Integer(result));
        //д������־
        {
            String logActionType = "�û���¼";
            ActionLogBO.getInstance().log(userID, logUserName, logUserRole, logActionType,
                                          actionResult, "",
                                          request.getRemoteAddr(), logDesc) ;
        }
        return mapping.findForward(forward);
    }
}

