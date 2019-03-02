package com.aspire.ponaadmin.web.rightmanager.action ;

import java.util.ArrayList;
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
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
import com.aspire.ponaadmin.common.rightmanager.RightModel;
import com.aspire.ponaadmin.common.rightmanager.RoleVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>���ý�ɫȨ�޵�action��</p>
 * <p>���ý�ɫȨ�޵�action��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */


public class RoleRightsAction
    extends BaseAction
{

    /**
     * ���debug��־
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger(RoleRightsAction.class) ;

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
        LOGGER.debug("RoleRightsAction()") ;
        String roleID = request.getParameter("roleID") ;
        //�����޸ĳ�������Ա
        if(roleID.equals("1"))
        {
            throw new BOException("A hijack access found!Can't modify role ��������Ա!");
        }
        RoleVO vo = RightManagerBO.getInstance().getRoleByID(roleID);
        String name = vo.getName();
        //ҳ���ϴ��ݹ�������һ����:�ָ����ַ�������Ҫ����split����
        String rightIDs = this.getParameter(request, "rightIDs");
        String[] id = rightIDs.split(":");
        RightManagerBO rightManager = RightManagerBO.getInstance() ;
        String forward = null ;
        //Ȩ�������ǲ���Ȩ��
        int rightType = RightModel.getInstance().getType() ;
        if (roleID.equals(""))
        {
            throw new BOException("invalid parameter!",
                                  RightManagerConstant.INVALID_PARA) ;
        }

        List IDList = new ArrayList() ;
        for (int i = 0 ; (id != null) && (i < id.length) ; i++)
        {
            if(!id[i].trim().equals(""))
            {
                IDList.add(id[i]) ;
            }
        }
        boolean actionResult = false ;
        try
        {
            rightManager.setRoleRights(roleID, rightType, IDList) ;
            forward = Constants.FORWARD_COMMON_SUCCESS ;
            request.setAttribute(Constants.PARA_GOURL, "roleList.do") ;
            this.saveMessages(request, ResourceConstants.WEB_INF_SAVERIGHT_OK) ;
            actionResult = true ;
        }
        catch (BOException e)
        {
            LOGGER.error(e) ;
            forward = Constants.FORWARD_COMMON_FAILURE ;
            this.saveMessages(request, ResourceConstants.WEB_INF_SAVERIGHT_ERR) ;
        }
        //д������־
        {
            String logActionType = "�趨��ɫ��Ȩ��" ;
            String logActionTarget = name ;
            String logDesc = "" ;
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }
        return mapping.findForward(forward) ;
    }
}
