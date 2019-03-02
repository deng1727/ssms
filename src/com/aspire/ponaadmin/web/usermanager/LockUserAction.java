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
 * <p>�����û���action��</p>
 * <p>�����û���action��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class LockUserAction
    extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(LockUserAction.class) ;

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
    	LOG.debug("LockUserAction()");
        String userID = this.getParameter(request, "userID").trim();

        //������
        //userID����Ϊ�գ�
        if (userID.equals(""))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }

        //����ҵ���߼�������
        int result = UserManagerBO.getInstance().lockUser(userID);

        String forward = Constants.FORWARD_COMMON_SUCCESS;
        boolean actionResult = false;
        if(result == UserManagerConstant.SUCC)
        {
            //�ɹ�
            actionResult = true;
            forward = Constants.FORWARD_COMMON_SUCCESS;
            this.saveMessages(request, ResourceConstants.WEB_INF_LOCKUSER_OK);
            request.setAttribute(Constants.PARA_GOURL, "queryUser.do");
        }
        else
        {
            //��������
            this.saveMessages(request, ResourceConstants.WEB_INF_LOCKUSER_ERR);
            forward = Constants.FORWARD_COMMON_FAILURE;
        }

        //д������־
        {
            String logActionType = "�û�����";
            String logActionTarget = userID;
            String logDesc = "";
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }

        return mapping.findForward(forward);
    }
}
