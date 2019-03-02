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
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;

/**
 * <p>��ѯ�û��б��action��</p>
 * <p>������������Ͳ�����е��û���������������͸���userID��name����ģ�����ҡ�</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class QueryUserListAction
    extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(QueryUserListAction.class) ;

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
    	LOG.debug("QueryUserListAction()");
        String userID = this.getParameter(request, "userID");
        String name = this.getParameter(request, "name");
        String companyName = this.getParameter(request, "companyName");
        String state = this.getParameter(request, "state");
        if(state.equals(""))
        {
            state = "0";
        }
        String roleID = this.getParameter(request, "roleID");
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


        //ʵ�ַ�ҳ���б�����Ǳ����ڷ�ҳ����PageResult�еġ�
        PageResult page = new PageResult(request) ;
        UserManagerBO.getInstance().searchUser(page, userVO, roleID,
                                               Integer.parseInt(rightType), rightID) ;

        //��Ҫ��ѯ���û��Ľ�ɫ��Ϣ��
        RightManagerBO rightManager = RightManagerBO.getInstance();
        List userList = page.getPageInfo();
        for(int i = 0; i < userList.size(); i++)
        {
            UserVO user = (UserVO) userList.get(i);
            user.setRoleList(rightManager.getUserRoles(user.getUserID()));
        }
        request.setAttribute("PageResult", page);
        request.setAttribute("allRoleList", RightManagerBO.getInstance().getAllRole());

        String forward = "userList";

        return mapping.findForward(forward);
    }
}
