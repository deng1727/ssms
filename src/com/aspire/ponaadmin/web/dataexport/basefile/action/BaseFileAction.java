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
import com.aspire.ponaadmin.web.dataexport.basefile.impl.AppBaseFile;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.DeviceBaseFile;

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
public class BaseFileAction extends BaseAction
{

    /**
     * ��¼��־��ʵ������
     */
    private static JLogger log = LoggerFactory.getLogger(BaseFileAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        log.debug("begin  BaseFileAction");

        String fileType = this.getParameter(request, "exportType");
        String forward;

        // ���ɻ�����������
        if (fileType.equals("appBase"))
        {
            forward = exportAppBase(mapping, form, request, response);
        }
        // ���ɻ�����Ϣ����
        else if (fileType.equals("deviceBase"))
        {
            forward = exportDeviceBase(mapping, form, request, response);
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
    private String exportAppBase(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String actionType = "���ɻ��������ļ�����";

        try
        {
            AppBaseFile app = new AppBaseFile();
            app.createFile();
        }
        catch (Exception e)
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "���ɻ��������ļ���������ʧ��");
            this.actionLog(request, actionType, actionType, false, "");
            log.error(e);
            return forward;
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        this.saveMessages(request, "���ɻ��������ļ����������ɹ�");
        this.actionLog(request, actionType, actionType, true, "");

        log.debug("end  BaseFileAction");
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
    private String exportDeviceBase(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String actionType = "����Ϣͬ���ļ�����";

        try
        {
            DeviceBaseFile device = new DeviceBaseFile();
            device.createFile();
        }
        catch (Exception e)
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "����Ϣͬ���ļ���������ʧ��");
            this.actionLog(request, actionType, actionType, false, "");
            log.error(e);
            return forward;
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        this.saveMessages(request, "����Ϣͬ���ļ����������ɹ�");
        this.actionLog(request, actionType, actionType, true, "");

        log.debug("end  BaseFileAction");
        return forward;
    }

}
