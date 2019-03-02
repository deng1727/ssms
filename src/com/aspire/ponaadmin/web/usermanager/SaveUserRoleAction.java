package com.aspire.ponaadmin.web.usermanager ;

import java.util.ArrayList;

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
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>保存用户角色的action类</p>
 * <p>保存用户角色的action类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class SaveUserRoleAction
    extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(SaveUserRoleAction.class) ;

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
    	LOG.debug("SaveUserRoleAction()");
        String[] roleIDs = request.getParameterValues("roleIDs");
        String userID = this.getParameter(request, "userID");

        //检查参数
        //userID不能为空；
        if (userID.equals(""))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }
        //系统内部用户ponaadmin的角色是不能修改的。
        if(userID.equals("ponaadmin"))
        {
            throw new BOException("A hijack access found!Can't modify user ponaadmin!");
        }

        ArrayList roleList = new ArrayList();
        for(int i = 0; (roleIDs != null) && (i < roleIDs.length); i++)
        {
            roleList.add(roleIDs[i]);
        }

        //定义操作是否成功的标志
        boolean result = true;
        try
        {
            RightManagerBO rightManagerBO = RightManagerBO.getInstance();
            rightManagerBO.setUserRoles(userID, roleList);
        }
        catch(BOException e)
        {
        	LOG.error(e);
            result = false;
        }
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        boolean actionResult = false;
        if(result)
        {
            //成功
            actionResult = true;
            forward = Constants.FORWARD_COMMON_SUCCESS;
            request.setAttribute(Constants.PARA_GOURL, "queryUser.do");
            this.saveMessages(request, ResourceConstants.WEB_INF_USERROLE_OK);
        }
        else
        {
            //成功
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, ResourceConstants.WEB_INF_USERROLE_ERR);
        }
        //写操作日志
        {
            String actionType = "设定用户角色";
            String actionTarget = userID;
            String desc = "";
            this.actionLog(request, actionType, actionTarget, actionResult, desc);
        }
        return mapping.findForward(forward);
    }
}
