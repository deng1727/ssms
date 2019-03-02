/*
 * �ļ�����ContentExigenceAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.repository.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class ContentExigenceAction extends BaseAction
{
    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ContentExigenceAction.class);
    
    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if("exe".equals(perType))
        {
            return exe(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }

    /**
     * ��ѯ����Ľ�Ҫ�������ߵ������б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "query";
        PageResult page = new PageResult(request);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action������");
        }

        try
        {
            ContentExigenceBO.getInstance().queryContentExigenceList(page);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ����Ľ�Ҫ�������ߵ������б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }
    
    /**
     * ɾ���������ߵ������б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "remove";

        // �������л�ȡ��������
        String[] ids = request.getParameterValues("dealRef");

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action����ʼ");
        }

        try
        {
            // ɾ��������������
            ContentExigenceBO.getInstance().delContentExigence(ids);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�����Ƴ������������ݳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "ɾ�������������ݳɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryExigence.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * ��ӽ������ߵ������б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "remove";
        String ret = "";

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action����ʼ");
        }
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
//      У���ļ���׺��
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 

        
        try
        {
            // ��ӽ�����������
            ret = ContentExigenceBO.getInstance().importContentExigence(dataFile);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "������ӽ����������ݳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "��ӽ����������ݲ����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryExigence.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * ͬ��������������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward exe(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
		
		boolean isLock = DataSyncBO.getInstance().isLock();
		if (isLock)
		{
			LOG.info("�����ظ�ִ������ͬ��ҵ��,hehe...");
			this.saveMessagesValue(request, "�����ظ�ִ������ͬ��ҵ��");
			throw new BOException("�����ظ�ִ������ͬ��ҵ��");
		}
		
		String forward = "exe";
		String ret = "";
		
		// �������л�ȡ��������
		String exeContent = this.getParameter(request, "exeContent").trim();
		String exeDeviceType = this.getParameter(request, "exeDeviceType")
				.trim();
		try
		{
			if (DataSyncBO.getInstance().isLock())
			{
				this.saveMessagesValue(request,
						"ͬ������������������ִ��ʱ���֣���ǰ����ͬ������ִ���У������˴�������ͣ������");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			else
			{
				// ͬ��������������
				if (!ContentExigenceBO.getInstance().isLock())
				{
					ContentExigenceBO.getInstance().SyncContentExigence(
							exeContent, false, exeDeviceType);
				}
				else
				{
					this.saveMessagesValue(request,
							"ͬ���������������������ڽ����У�Ϊ�����ظ������Ժ���ִ�У�����");
					return mapping
							.findForward(Constants.FORWARD_COMMON_FAILURE);
				}
			}
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "ͬ�������������ݳ���");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		this.saveMessagesValue(request, "ͬ�������������ݲ����ɹ���" + ret);
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"queryExigence.do?perType=query");
		return mapping.findForward(forward);
	}
}
