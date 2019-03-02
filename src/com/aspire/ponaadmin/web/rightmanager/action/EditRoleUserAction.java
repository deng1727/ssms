package com.aspire.ponaadmin.web.rightmanager.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RoleVO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;

/**
 * <p>修改角色用户pre的action类</p>
 * <p>修改角色用户pre的action类<</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class EditRoleUserAction
    extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOGGER = LoggerFactory.getLogger(EditRoleUserAction.class) ;

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
        LOGGER.debug("EditRoleUserAction()");

        String roleID = this.getParameter(request, "roleID").trim();

        //查询用户
        String userID = this.getParameter(request, "userID");
        String name = this.getParameter(request, "name");
        String companyName = this.getParameter(request, "companyName");
        String state = this.getParameter(request, "state");
        if(state.equals(""))
        {
            state = "0";
        }
        String qRoleID = this.getParameter(request, "qRoleID");
        String rightID = this.getParameter(request, "rightID");
        String rightType = this.getParameter(request, "rightType");
        if(rightType.equals("") || rightID.equals(""))
        {
            rightType = "0";
        }

        UserVO userVO = new UserVO();
        userVO.setUserID(userID);
        userVO.setName(name);
        userVO.setCompanyName(companyName);
        userVO.setState(Integer.parseInt(state));


        //实现分页，列表对象是保存在分页对象PageResult中的。
        PageResult page = new PageResult(request) ;
        UserManagerBO.getInstance().searchUser(page, userVO, qRoleID,
                                               Integer.parseInt(rightType), rightID) ;

        //调用业务逻辑处理方法，获取用户具有的角色和所有的角色。
        RightManagerBO rightManagerBO = RightManagerBO.getInstance();
        List roleUserList = rightManagerBO.getRoleUsers(roleID);
        RoleVO role = rightManagerBO.getRoleByID(roleID);
        //传递给页面
        request.setAttribute("PageResult", page);
        request.setAttribute("role", role);
        request.setAttribute("roleUserList", roleUserList);
        request.setAttribute("allRoleList", RightManagerBO.getInstance().getAllRole());

        String forward = "editRoleUser";

        return mapping.findForward(forward);
    }
}
