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
import com.aspire.ponaadmin.web.actionlog.ActionLogBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>Ԥע�ᡢ��˲�ͨ�����û���д������Ϣ�������action��</p>
 * <p>����ɹ���Ҫ���û�״̬�޸�Ϊ�����</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RegisterInfoAction
    extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(RegisterInfoAction.class) ;

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
        LOG.debug("RegisterInfoAction()");
        String userID = this.getParameter(request, "userID").trim();
        String name = this.getParameter(request, "name").trim();
        String sex = this.getParameter(request, "sex").trim();
        String birthday = this.getParameter(request, "birthday").trim();
        String certType = this.getParameter(request, "certType").trim();
        String certID = this.getParameter(request, "certID").trim();
        String companyName = this.getParameter(request, "companyName").trim();
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
        userVO.setName(name);
        userVO.setSex(sex);
        userVO.setBirthday(birthday);
        userVO.setCertType(certType);
        userVO.setCertID(certID);
        userVO.setCompanyName(companyName);
        userVO.setCompanyAddr(companyAddr);
        userVO.setPostcode(postcode);
        userVO.setPhone(phone);
        userVO.setMobile(mobile);
        userVO.setEmail(email);
        userVO.setQQ(QQ);
        userVO.setMSN(MSN);
        //�޸���Ϣ��״̬Ϊ�����
        userVO.setState(UserManagerConstant.STATE_TO_BE_CHECK);

        //����ҵ���߼�������
        String forward = Constants.FORWARD_COMMON_FAILURE;
        boolean actionResult = false;
        try
        {
            //ֻ��Ԥע�����˲�ͨ�����û����ܽ��в���
            UserVO user = UserManagerBO.getInstance().getUserByID(userID);
            if ((user.getState() != UserManagerConstant.STATE_PRE_REGISTER) &&
                (user.getState() != UserManagerConstant.STATE_CHECK_FAIL))
            {
                this.saveMessages(request, ResourceConstants.WEB_FILLINFO_ERR);
                request.setAttribute(Constants.PARA_GOURL, "../index.jsp") ;
                request.setAttribute(Constants.PARA_ISSTANDALONE, "true") ;
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            UserManagerBO.getInstance().modifyUserInfo(userVO);
            actionResult = true;
            forward = Constants.FORWARD_COMMON_SUCCESS;
            request.setAttribute(Constants.PARA_GOURL, "../index.jsp") ;
            request.setAttribute(Constants.PARA_ISSTANDALONE, "true") ;
            this.saveMessages(request, ResourceConstants.WEB_INF_REGISTER_SUCC);
        }
        catch(Exception e)
        {
            LOG.error(e);
            actionResult = false;
            forward = Constants.FORWARD_COMMON_FAILURE;//ת�������ҳ
            request.setAttribute(Constants.PARA_ISSTANDALONE, "true") ;
            this.saveMessages(request, ResourceConstants.WEB_INF_MODUSERINFO_ERR);
        }

        //д������־
        {
            String logActionType = "�趨������Ϣ";
            String logActionTarget = userID;
            String logDesc = "";
            String IP = request.getRemoteAddr() ;
            ActionLogBO.getInstance().log(userID, name,
                                          "", logActionType, actionResult,
                                          logActionTarget, IP, logDesc) ;
        }

        return mapping.findForward(forward);
    }
}
