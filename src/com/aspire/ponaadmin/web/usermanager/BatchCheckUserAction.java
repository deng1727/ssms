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
 * <p>批量审核用户注册的action类</p>
 * <p>批量审核都是通过的，不能是不通过的。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class BatchCheckUserAction
    extends BaseAction
{

    /**
     * 日志引用
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
        //检查参数
        //至少应该有一个userID
        if (userIDs.length == 0)
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }
        //调用业务逻辑处理方法
        for(int i = 0; i < userIDs.length; i++)
        {
            UserManagerBO.getInstance().checkRegister(userIDs[i], true, null);
        }
        String forward = "commonSuccess";
        return mapping.findForward(forward);
    }
}
