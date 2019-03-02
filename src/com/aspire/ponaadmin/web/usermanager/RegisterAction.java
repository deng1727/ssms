package com.aspire.ponaadmin.web.usermanager ;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.GenerateVerifyImg;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerConstant;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.actionlog.ActionLogBO;
import com.aspire.ponaadmin.web.constant.ErrorCode;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.constant.ResourceUtil;

/**
 * <p>�û�Ԥע���action��</p>
 * <p>�����û���Ԥע����Ϣ��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RegisterAction
    extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(RegisterAction.class) ;

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
    	LOG.debug("RegisterAction()");
        String userID = this.getParameter(request, "userID").trim();
        String password = this.getParameter(request, "password").trim();
        String imgCode = this.getParameter(request, "imgCode").trim();

        //������
        if (userID.equals("") || password.equals("") || (imgCode.equals("")))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);

        }
        
        String userReg = "[\\w,\\u4e00-\\u9fa5]+";
        
        if(!userID.matches(userReg) || userID.length() > 20)
        {
            throw new BOException("user data check error !", UserManagerConstant.INVALID_PARA);
        }

        if(!GenerateVerifyImg.verify(imgCode, request.getSession()))
        {
            //�����벻��
            this.saveMessages(request, ResourceConstants.WEB_ERR_IMGCODE);
            //�����벻��ȷ��ʱ����Ҫ��ʾ�û���ǰ������ʺ���
            request.setAttribute("userID", userID);
            //д������־
            {
                String logActionType = "�û�Ԥע��";
                boolean actionResult = false;
                String logDesc = "";
                ActionLogBO.getInstance().log(userID, "", "", logActionType,
                                              actionResult, "",
                                              request.getRemoteAddr(), logDesc) ;
            }
            return mapping.findForward("registerAgain");
        }

        //����ҵ���߼�������
        int result = ErrorCode.FAIL;
        try
        {
            result = UserManagerBO.getInstance().registerReq(userID, password);
        }
        catch(Exception e)
        {
        	LOG.error(e);
            result = ErrorCode.FAIL;
        }

        String forward = "";
        String logDesc = "";
        boolean actionResult = false;
        if(result == UserManagerConstant.SUCC)
        {
            //�����ɹ�
            actionResult = true;
            UserVO userVO = new UserVO();
            userVO.setUserID(userID);
            request.setAttribute("userVO", userVO);
            forward = "registerInfo";
        }
        else if(result == UserManagerConstant.USER_EXISTED)
        {
            //�û��Ѿ�����
            this.saveMessages(request, ResourceConstants.WEB_ERR_REG_USERNAME);
            forward = "registerAgain";
            logDesc = ResourceUtil.getResource(ResourceConstants.WEB_ERR_REG_USERNAME);
        }
        else
        {
            //�ڲ�����
            this.saveMessages(request, ResourceConstants.WEB_INF_REG_FAIL);
            forward = "registerAgain";
        }
        //д������־
        {
            String logActionType = "�û�Ԥע��";
            ActionLogBO.getInstance().log(userID, "", "", logActionType,
                                          actionResult, "",
                                          request.getRemoteAddr(), logDesc) ;
        }
        return mapping.findForward(forward);
    }
}
