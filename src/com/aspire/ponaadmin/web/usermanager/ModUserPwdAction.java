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
 * <p>�޸��û������action��</p>
 * <p>�޸��û������action��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ModUserPwdAction
    extends BaseAction
{

    /**
     * ��־����
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
        //��session��ȡ���û���Ϣ
        UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(
            request.getSession()) ;
        UserVO user = userSession.getUser();
        String userID = userSession.getUser().getUserID();
        String newPwd = this.getParameter(request, "newPwd").trim();
        String oldPwd = this.getParameter(request, "oldPwd").trim();

        //������
        if (newPwd.equals("") || oldPwd.equals(""))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }

        //ֻ�����������븴λ���û������޸�����
        if ((user.getState() != UserManagerConstant.STATE_NORMAL) &&
            (user.getState() != UserManagerConstant.STATE_PWD_RESET))
        {
            this.saveMessages(request, ResourceConstants.WEB_INF_NO_RIGHT);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        //����ҵ���߼�������
        int result = UserManagerBO.getInstance()
		        .modifyUserPW(userID, oldPwd, newPwd, user.getState());

        String forward = "";
        boolean actionResult = false;
        if(result == UserManagerConstant.SUCC)
        {
            //�ɹ�
            this.saveMessages(request, ResourceConstants.WEB_INF_MODPWD_OK);
            forward = Constants.FORWARD_COMMON_SUCCESS;
            request.setAttribute(Constants.PARA_GOURL, "modify_pwd.jsp") ;
            //��������뱻��λ���û���Ҫ�趨session�����״̬��������Ȩ��
            if(user.getState() == UserManagerConstant.STATE_PWD_RESET)
            {
                //��������븴λ���û����ɹ�ȷ�Ϻ�Ӧ�õ���ҳ��ȥ��
                request.setAttribute(Constants.PARA_GOURL, "../main.jsp") ;
                user.setState(UserManagerConstant.STATE_NORMAL);
                //�����û�Ȩ��
                userSession.setRights(RightManagerBO.getInstance().getUserRights(userID));
            }
            actionResult = true;
        }
        else if(result == UserManagerConstant.INVALID_PWD)
        {
            //���������
            this.saveMessages(request, ResourceConstants.WEB_INF_PWD_ERROR);
            forward = "redo";
        }
        else
        {
            //��������
            this.saveMessages(request, ResourceConstants.WEB_INF_MODPWD_ERR);
            forward = Constants.FORWARD_COMMON_FAILURE;
        }

        //д������־
        {
            String logActionType = "�޸ĸ�������";
            String logActionTarget = userID;
            String logDesc = "";
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }

        return mapping.findForward(forward);
    }
}
