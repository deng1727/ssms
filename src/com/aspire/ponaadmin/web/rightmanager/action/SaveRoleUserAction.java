package com.aspire.ponaadmin.web.rightmanager.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
import com.aspire.ponaadmin.common.rightmanager.RoleVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>�����ɫ�����û���action��</p>
 * <p>�����ɫ�����û���action��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class SaveRoleUserAction
    extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOGGER = LoggerFactory.getLogger(SaveRoleUserAction.class) ;

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
        LOGGER.debug("SaveRoleUserAction()");
        String roleID = this.getParameter(request, "roleID");
        RoleVO vo = RightManagerBO.getInstance().getRoleByID(roleID);
        String name = vo.getName();
        //ҳ���ϴ��ݹ�������һ����:�ָ����ַ�������Ҫ����split����
        String[] addUserIDs = request.getParameterValues("userIDs") ;
        if(addUserIDs == null)
        {
            addUserIDs = new String[] {} ;
        }
        String[] delUserIDs = this.splitRequestString(request, "delUserIDs", ":") ;

        //������
        //roleIDs����Ϊ�գ�
        if (roleID.equals(""))
        {
            throw new BOException("invalid parameter!", RightManagerConstant.INVALID_PARA);
        }

        //��������Ƿ�ɹ��ı�־
        boolean actionResult = false;
        String forward = "";
        try
        {
            RightManagerBO rightManagerBO = RightManagerBO.getInstance();
            rightManagerBO.modRoleUsers(roleID, addUserIDs, delUserIDs) ;
            this.saveMessages(request, ResourceConstants.WEB_INF_ROLE_USER_OK);
            forward = Constants.FORWARD_COMMON_SUCCESS;
            request.setAttribute(Constants.PARA_GOURL, "roleList.do");
            actionResult = true;
        }
        catch(BOException e)
        {
            LOGGER.error(e);
            this.saveMessages(request, ResourceConstants.WEB_INF_ROLE_USER_ERR);
            forward = Constants.FORWARD_COMMON_FAILURE;
        }

        //д������־
        {
            String logActionType = "�趨��ɫ���û�";
            String logActionTarget = name;
            String logDesc = "";
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }

        return mapping.findForward(forward) ;
    }

    /**
     * �ָ��ַ��������Դ�ַ���Ϊ�շ���lengthΪ0������
     * @param request HttpServletRequest
     * @param paraName String
     * @param reg String
     * @return String[]
     */
    private String[] splitRequestString (HttpServletRequest request,
                                         String paraName, String reg)
    {
        String src = this.getParameter(request, paraName);
        if (src == null || src.trim().equals(""))
        {
            return new String[]{};
        }
        return src.split(reg);
    }
}
