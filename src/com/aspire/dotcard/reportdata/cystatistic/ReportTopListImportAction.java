
package com.aspire.dotcard.reportdata.cystatistic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.cystatistic.bo.CyListBO;
import com.aspire.dotcard.reportdata.cystatistic.bo.TopListBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

/**
 * Ӧ����TOP�񵥼�ÿ�մ�ҵ������Ʒ��Ӫ���������ֶ����룬����ࡣ
 * 
 * @author zhangwei
 * 
 */
public class ReportTopListImportAction extends BaseAction
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ReportTopListImportAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ReportTopListImportAction...");
        }
        String actionType = this.getParameter(request, "action");
        if (logger.isDebugEnabled())
        {
            logger.debug("actionType=" + actionType);
        }
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        executImportTask(actionType, request);
        return mapping.findForward(forward);
    }

    /**
     * ִ�б�����task
     * 
     * @param taskName
     */
    private void executImportTask(String taskName, HttpServletRequest request)
                    throws BOException
    {

        if (taskName.equals("cylist"))
        {
            CyListBO.getInstance().service();
            saveMessagesValue(request, "ÿ�մ�ҵ������Ʒ��Ӫ�������ݵ���ɹ�");
        }
        else if (taskName.equals("0"))
        {
            TopListBO.getInstance().service(0);
            saveMessagesValue(request, "TOP������ȫ������ɹ�");
        }
        else if (taskName.equals("1"))
        {
            TopListBO.getInstance().service(1);
            saveMessagesValue(request, "�����Ϸ����Ʒ�����ۺ��Ƽ�ָ��TOP60�����ݵ���ɹ�");
        }
        else if (taskName.equals("2"))
        {
            TopListBO.getInstance().service(2);
            saveMessagesValue(request, "�����������Ʒ�����ۺ��Ƽ�ָ��TOP30�����ݵ���ɹ�");
        }
        else if (taskName.equals("3"))
        {
            TopListBO.getInstance().service(3);
            saveMessagesValue(request, "�����Ϸ����Ʒ��̽�Ƽ��÷�TOP50�����ݵ���ɹ�");
        }
        else if (taskName.equals("4"))
        {
            TopListBO.getInstance().service(4);
            saveMessagesValue(request, "�����������Ʒ��̽�Ƽ��÷�TOP50�����ݵ���ɹ�");
        }
        else if (taskName.equals("5"))
        {
            TopListBO.getInstance().service(5);
            saveMessagesValue(request, "�г�PKӦ���ۼ��������ݵ���ɹ�");
        }
        else
        {
            throw new BOException("û�д��������͡�type=" + taskName);
        }
    }

}
