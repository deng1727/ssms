
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
 * ����keybase����չ���Ե�Action
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

public class KeyBaseInsertAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(KeyBaseInsertAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("KeyBaseInsertAction...doPerform()");
        }
        String forward = "";

		String actionType = "��չ��������";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = this.getParameter(request, "keyname").toUpperCase();
		
        try
        {
            String keytable = this.getParameter(request, "keytable");
            String keyname = this.getParameter(request, "keyname").toUpperCase();
            String keydesc = this.getParameter(request, "keydesc");
            String keytype = this.getParameter(request, "keytype");
            if (logger.isDebugEnabled())
            {
                logger.debug("keytable=" + keytable + " keyname=" + keyname
                             + " keydesc=" + keydesc+"keytype="+keytype);
            }
            boolean ret = KeyBaseBO.getInstance().isKebBase(keytable, keyname);
            if (!ret)
            {
                throw new BOException("��չ�����ظ� keytable=" + keytable
                                      + " keyname=" + keyname);
            }
            KeyBaseBO.getInstance().insertKeyBase(keytable, keyname, keydesc,keytype);

            this.saveMessages(request, "������չ���Գɹ�!");

            forward = Constants.FORWARD_COMMON_SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;// ת�������ҳ
            this.saveMessages(request, "������չ����ʧ��!");
            
			// д������־
			actionResult = false;
			actionDesc = "������չ����ʧ��!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
        }
        
		// д������־
		actionDesc = "������չ���Գɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute(Constants.PARA_GOURL, "keyBaseList.do");
        return mapping.findForward(forward);

    }
}
