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

/**
 * <p>��������û�ע���action��</p>
 * <p>������˶���ͨ���ģ������ǲ�ͨ���ġ�</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class BatchCheckUserAction
    extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BatchCheckUserAction.class) ;

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
        LOG.debug("BatchCheckUserAction()");
        String[] userIDs = request.getParameterValues("userIDs");
        //������
        //����Ӧ����һ��userID
        if (userIDs.length == 0)
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }
        //����ҵ���߼�������
        for(int i = 0; i < userIDs.length; i++)
        {
            UserManagerBO.getInstance().checkRegister(userIDs[i], true, null);
        }
        String forward = "commonSuccess";
        return mapping.findForward(forward);
    }
}
