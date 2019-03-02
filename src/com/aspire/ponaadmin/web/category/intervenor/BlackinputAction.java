package com.aspire.ponaadmin.web.category.intervenor;

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
import com.aspire.ponaadmin.web.category.intervenor.category.IntervenorCategoryAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

public class BlackinputAction  extends BaseAction {

	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(BlackinputAction.class);

	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("doPerform()");
		}

		// �������л�ȡ��������
		String action = this.getParameter(request, "actionType").trim();
		if ("blackinput".equals(action))
	        {
	            return blackinput(mapping, form, request, response);
	        }
		if ("importfile".equals(action))
        {
            return importfile(mapping, form, request, response);
        }
	        else
	        {
	            String forward = Constants.FORWARD_COMMON_FAILURE;
	            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
	            return mapping.findForward(forward);
	        }

}

	private ActionForward blackinput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "blackinput";
		return mapping.findForward(forward);
	}
	private ActionForward importfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_FAILURE;


        // �õ����������ļ���Ϣ
        DataImportForm iForm = ( DataImportForm ) form;
        int a=1;
        FormFile dataFile = iForm.getDataFile();

		String actionType = "����񵥺�������";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = dataFile.getFileName();
		
        // У���ļ���׺��
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        

        try
        {
            // ���������ļ�����
            BlackinputBo.getInstance().importFile( dataFile);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
			// д������־
			actionResult = false;
			actionDesc = "����񵥺�����ʱ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "����񵥺�����ʱ����");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "����񵥺����������ļ���Ϣ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "����񵥺������ļ���Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "blackinput.do?actionType=blackinput");
        return mapping.findForward(forward);
	}
}
