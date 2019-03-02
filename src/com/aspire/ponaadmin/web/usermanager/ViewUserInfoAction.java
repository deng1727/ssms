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
 * <p>�鿴һ���û�������Ϣ��action��</p>
 * <p>�鿴һ���û�������Ϣ��action��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ViewUserInfoAction
    extends BaseAction
{

    /**
     * ��־����
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

        //����ҵ���߼�������
        UserVO userVO = UserManagerBO.getInstance().getUserByID(userID);

        //���û�VO���󴫵ݸ���д�û���Ϣҳ��
        request.setAttribute("userVO", userVO);

        String forward = "viewUserInfo";
        return mapping.findForward(forward);
    }
}
