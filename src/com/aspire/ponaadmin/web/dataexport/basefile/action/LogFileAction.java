/*
 * �ļ�����BaseFileAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.dataexport.basefile.action;

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
import com.aspire.ponaadmin.web.dataexport.basefile.impl.CategoryBaseFile;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.ReferenceBaseFile;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class LogFileAction extends BaseAction
{

    /**
     * ��¼��־��ʵ������
     */
    private static JLogger log = LoggerFactory.getLogger(LogFileAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        log.debug("begin  LogFileAction");

        String fileType = this.getParameter(request, "exportType");
        String forward;

        // ���ɻ�����������
        if (fileType.equals("categoryFile"))
        {
            forward = exportCategoryBase(mapping, form, request, response);
        }
        // ���ɻ�����Ϣ����
        else if (fileType.equals("referenceFile"))
        {
            forward = exportReferenceBase(mapping, form, request, response);
        }
        else
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "û�д˵�������");
        }

        return mapping.findForward(forward);
    }

    /**
     * �������ɻ�����������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private String exportCategoryBase(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String actionType = "���ɻ���ά����־�ļ�����";

        try
        {
            CategoryBaseFile cate = new CategoryBaseFile();
            cate.createFile();
        }
        catch (Exception e)
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "���ɻ���ά����־�ļ���������ʧ��");
            this.actionLog(request, actionType, actionType, false, "");
            log.error(e);
            return forward;
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        this.saveMessages(request, "���ɻ���ά����־�ļ����������ɹ�");
        this.actionLog(request, actionType, actionType, true, "");

        log.debug("end  LogFileAction");
        return forward;
    }

    /**
     * �������ɻ�����Ϣͬ������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private String exportReferenceBase(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String actionType = "������Ʒά����־�ļ�����";

        try
        {
            ReferenceBaseFile reference = new ReferenceBaseFile();
            reference.createFile();
        }
        catch (Exception e)
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "������Ʒά����־�ļ���������ʧ��");
            this.actionLog(request, actionType, actionType, false, "");
            log.error(e);
            return forward;
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        this.saveMessages(request, "������Ʒά����־�ļ����������ɹ�");
        this.actionLog(request, actionType, actionType, true, "");

        log.debug("end  LogFileAction");
        return forward;
    }

}
