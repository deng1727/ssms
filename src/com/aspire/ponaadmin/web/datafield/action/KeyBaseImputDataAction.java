
package com.aspire.ponaadmin.web.datafield.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

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

public class KeyBaseImputDataAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(KeyBaseImputDataAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("KeyBaseImputDataAction...doPerform()");
        }
        String forward;
        String ret = "";

        // �������л�ȡkeyid
        String keytable = this.getParameter(request, "keytable").trim();
        String keyid = this.getParameter(request, "keyid").trim();
        
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
        
		String actionType = "�ļ�����������չ����������Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = keytable + "-" + keyid + "-" + dataFile.getFileName();
        
        // У���ļ���׺��
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        try
        {
            // �ļ�����������չ����������Ϣ
            ret = KeyBaseBO.getInstance().imputKeyBase(dataFile, keytable, keyid);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "�ļ�����������չ����������Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "�ļ�����������չ����������Ϣ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

		// д������־
		actionDesc = "�ļ�����������չ����������Ϣ�����ɹ�!" + ret;
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�ļ�����������չ����������Ϣ�����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "keyBaseList.do");

        return mapping.findForward(forward);

    }
}
