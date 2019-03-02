package com.aspire.ponaadmin.web.usermanager ;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerConstant;
import com.aspire.ponaadmin.web.BaseAction;

/**
 * <p>�趨�û���ɫ��action��</p>
 * <p>�趨�û���ɫ��action��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class EditUserRoleAction
    extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(EditUserRoleAction.class) ;

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
    	LOG.debug("EditUserRoleAction()");
        String userID = this.getParameter(request, "userID").trim();

        //������
        //userID����Ϊ�գ�
        if (userID.equals(""))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }

        //����ҵ���߼�����������ȡ�û����еĽ�ɫ�����еĽ�ɫ��
        RightManagerBO rightManagerBO = RightManagerBO.getInstance();
        List userRoleList = rightManagerBO.getUserRoles(userID);
        List allRoleList = rightManagerBO.getAllRole();

        //���ݸ�ҳ��
        request.setAttribute("userID", userID);
        request.setAttribute("userRoleList", userRoleList);
        request.setAttribute("allRoleList", allRoleList);

        String forward = "editUserRole";

        return mapping.findForward(forward);
    }
}
