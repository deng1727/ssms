package com.aspire.ponaadmin.web.blacklist.action;

import java.util.ArrayList;
import java.util.List;

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
import com.aspire.ponaadmin.web.blacklist.biz.BlackListBo;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * ���������
 * 
 * @author x_zhailiqing
 * 
 */
public class BlackImportAction extends BaseAction
{
    private static final JLogger LOG = LoggerFactory.getLogger(BlackImportAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("importBlackList() is start");
        }
        String forward = Constants.FORWARD_COMMON_SUCCESS;

        DataImportForm iForm = ( DataImportForm ) form;
        FormFile dataFile = iForm.getDataFile();
        List err = new ArrayList();
        
        // У���ļ���׺��
        if(!iForm.checkFileNameExtension(new String[]{"txt"}))
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(forward);
        }
        
        try
        {
            int successCount = BlackListBo.getInstance().importBlack(dataFile,
                                                                     err);
            StringBuffer message = new StringBuffer();
            message.append("���ݵ���ɹ������ɹ�����" + successCount + "������<br />");
            LOG.info(message.toString());
            message.append("����ʧ�����ݣ�");
            for (int i = 0; i < err.size(); i++)
            {
                message.append(( String ) err.get(i) + "<br />");
            }
            this.actionLog(request,
                           "���������ݵ���",
                           dataFile.getFileName(),
                           true,
                           "��������" + successCount + "������");
            this.saveMessagesValue(request, message.toString());

        }
        catch (Exception e)
        {
            LOG.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "���ݵ���ʧ��");
        }
        request.setAttribute(Constants.PARA_GOURL, "black.do");
        return mapping.findForward(forward);
    }

}
