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
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>����û�ע���action��</p>
 * <p>����û�ע���action��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class CheckUserAction
    extends BaseAction
{

    /**
     * ��־����
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger(CheckUserAction.class) ;

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
        LOGGER.debug("CheckUserAction()");
        String userID = this.getParameter(request, "userID").trim();
        //����Ƿ�ͨ�������ܲ�����yͨ����������ͨ����
        String resultStr = this.getParameter(request, "result").trim();
        boolean result = false;
        if(resultStr.equals("y"))
        {
            result = true;
        }
        String desc = this.getParameter(request, "desc").trim();

        //������
        //userID����Ϊ�գ������˲�ͨ������������Ϊ��
        if (userID.equals("") || (!result && desc.equals("")))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }

        //����ҵ���߼�������
        boolean actionResult = false;
        String logDesc = "";
        String logActionType = "ע�����ͨ��";
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        if(result)
        {
            logActionType = "ע�����ͨ��";
        }
        else
        {
            logDesc = desc;
            logActionType = "ע����˲�ͨ��";
        }
        try
        {
            UserManagerBO.getInstance().checkRegister(userID, result, desc);
            actionResult = true;
            request.setAttribute(Constants.PARA_GOURL, "queryUncheckUser.do") ;
            forward = Constants.FORWARD_COMMON_SUCCESS;
            //�����Ƿ����ͨ����ʾ��ͬ����Ϣ
            if(result)
            {
                this.saveMessages(request, ResourceConstants.WEB_INF_REG_CHECK_SUCC, userID);
            }
            else
            {
                this.saveMessages(request, ResourceConstants.WEB_INF_REG_CHECK_FAIL, userID);
            }
        }
        catch(BOException e)
        {
            LOGGER.error(e);
            actionResult = false;
            this.saveMessages(request, ResourceConstants.WEB_INF_REG_CHECK_ERR);
            forward = Constants.FORWARD_COMMON_FAILURE;
        }

        //д������־
        {
            String logActionTarget = userID;
            this.actionLog(request, logActionType, logActionTarget,
                           actionResult, logDesc) ;
        }

        return mapping.findForward(forward);
    }
}
