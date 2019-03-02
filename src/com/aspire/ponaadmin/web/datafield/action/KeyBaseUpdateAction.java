
package com.aspire.ponaadmin.web.datafield.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;

/**
 * <p>
 * ����keyid����keybase����չ���Ե�Action
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class KeyBaseUpdateAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(KeyBaseUpdateAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("KeyBaseUpdateAction...doPerform()");
        }
        String forward = "";

        try
        {

            String keyid = this.getParameter(request, "keyid");
            String keytable = this.getParameter(request, "keytable");
            String keyname = this.getParameter(request, "keyname").toUpperCase();;
            String keydesc = this.getParameter(request, "keydesc");
            String keytype = this.getParameter(request, "keytype");
            if (logger.isDebugEnabled())
            {
                logger.debug("keyid=" + keyid + " keytable=" + keytable
                             + " keyname=" + keyname + " keydesc=" + keydesc+"keytype="+keytype);
            }
            KeyBaseBO.getInstance().updateKeyBase(keyid,
                                                  keytable,
                                                  keyname,
                                                  keydesc,
                                                  keytype);

            this.saveMessages(request, "������չ���Գɹ�!");

            forward = Constants.FORWARD_COMMON_SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;// ת�������ҳ
            this.saveMessages(request, "������չ����ʧ��!");
        }
        request.setAttribute(Constants.PARA_GOURL, "keyBaseList.do");
        return mapping.findForward(forward);

    }
}
