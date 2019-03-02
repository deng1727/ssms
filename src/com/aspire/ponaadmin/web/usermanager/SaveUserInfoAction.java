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
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>�����û�������Ϣ��action</p>
 * <p>�����û�������Ϣ��action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class SaveUserInfoAction
    extends BaseAction
{

    /**
     * ��־����
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger(SaveUserInfoAction.class) ;

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
        LOGGER.debug("SaveUserInfoAction()");
        String userID = this.getParameter(request, "userID").trim();
        String companyAddr = this.getParameter(request, "companyAddr").trim();
        String postcode = this.getParameter(request, "postcode").trim();
        String phone = this.getParameter(request, "phone").trim();
        String mobile = this.getParameter(request, "mobile").trim();
        String email = this.getParameter(request, "email").trim();
        if(!email.equals(""))
        {
            email = PublicUtil.replace(email,"'","''");
        }
        String QQ = this.getParameter(request, "QQ").trim();
        String MSN = this.getParameter(request, "MSN").trim();

        //������

        //���ݲ��������û�VO����
        UserVO userVO = new UserVO();
        userVO.setUserID(userID);
        userVO.setCompanyAddr(companyAddr);
        userVO.setPostcode(postcode);
        userVO.setPhone(phone);
        userVO.setMobile(mobile);
        userVO.setEmail(email);
        userVO.setQQ(QQ);
        userVO.setMSN(MSN);


        //����ҵ���߼�������
        String forward = Constants.FORWARD_COMMON_FAILURE;
        boolean actionResult = false;
        try
        {
            //ֻ������״̬���û����ܽ��в���
            UserVO user = UserManagerBO.getInstance().getUserByID(userID);
            if (user.getState() != UserManagerConstant.STATE_NORMAL)
            {
                this.saveMessages(request, ResourceConstants.WEB_INF_NO_RIGHT);
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            UserManagerBO.getInstance().modifyUserInfo(userVO);
            this.saveMessages(request, ResourceConstants.WEB_INF_MODUSERINFO_OK);
            actionResult = true;
            forward = Constants.FORWARD_COMMON_SUCCESS;
        }
        catch(Exception e)
        {
            LOGGER.error(e);
            actionResult = false;
            forward = Constants.FORWARD_COMMON_FAILURE;//ת�������ҳ
            this.saveMessages(request, ResourceConstants.WEB_INF_MODUSERINFO_ERR);
        }

        //д������־
        {
            String logActionType = "�趨������Ϣ";
            String logActionTarget = userID;
            String logDesc = "";
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }

        return mapping.findForward(forward);
    }
}
