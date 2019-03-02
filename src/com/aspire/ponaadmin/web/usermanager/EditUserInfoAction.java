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
 * <p>�޸��û�������Ϣ��action��</p>
 * <p>Ԥע���û�����˲�ͨ��ת��ע����д��Ϣҳ��ȥ��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class EditUserInfoAction
    extends BaseAction
{

    /**
     * ��־����
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
        //��session��ȡ���û���Ϣ
        UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(
            request.getSession()) ;
        String userID = userSession.getUser().getUserID();

        //����ҵ���߼�������
        UserVO userVO = UserManagerBO.getInstance().getUserByID(userID);

        //���������븴λ���û��ǲ��ܽ����޸ĸ�����Ϣ������
        if ((userVO.getState() == UserManagerConstant.STATE_LOCKED) ||
            (userVO.getState() == UserManagerConstant.STATE_PWD_RESET))
        {
            this.saveMessages(request, ResourceConstants.WEB_INF_NO_RIGHT);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }


        //���û�VO���󴫵ݸ���д�û���Ϣҳ��
        request.setAttribute("userVO", userVO);

        String forward = "editUserInfo";
        int userState = userVO.getState();
        if ((userState == UserManagerConstant.STATE_PRE_REGISTER) ||
            (userState == UserManagerConstant.STATE_CHECK_FAIL))
        {
            //Ԥע���û�����˲�ͨ��ת��ע����д��Ϣҳ��ȥ��
            forward = "registerInfo";
        }
        else if(userState == UserManagerConstant.STATE_TO_BE_CHECK)
        {
            //������û������޸��û���Ϣ
            //������ʱ�Ȳ������ַ��������Ӧ��ͨ�������ɫ������ġ�
            this.saveMessages(request, ResourceConstants.WEB_INF_NO_RIGHT) ;
            forward = Constants.FORWARD_COMMON_FAILURE;
        }
        return mapping.findForward(forward);
    }
}
