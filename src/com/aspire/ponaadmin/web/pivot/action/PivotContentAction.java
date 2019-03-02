/*
 * �ļ�����PivotContentAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.pivot.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.pivot.bo.PivotContentBO;
import com.aspire.ponaadmin.web.pivot.vo.PivotContentVO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class PivotContentAction extends BaseAction
{
    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(PivotContentAction.class);

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
        else if("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }
        else if("detail".equals(perType))
        {
            return detail(mapping, form, request, response);
        }
        else if("downloadData".equals(perType))
        {
            return downloadData(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "query";
        PageResult page = new PageResult(request);
        
        String contentId = this.getParameter(request, "contentId").trim();
        String contentName = this.getParameter(request, "contentName").trim();
        String apCode = this.getParameter(request, "apCode").trim(); 
        String apName = this.getParameter(request, "apName").trim();
        
        PivotContentVO vo = new PivotContentVO();
        vo.setContentId(contentId);
        vo.setContentName(contentName);
        vo.setApCode(apCode);
        vo.setApName(apName);
        
        try
        {
            PivotContentBO.getInstance().queryPivotContentList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ�ص������б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("PageResult", page);
        request.setAttribute("contentId", contentId);
        request.setAttribute("contentName", contentName);
        request.setAttribute("apCode", apCode);
        request.setAttribute("apName", apName);

        return mapping.findForward(forward);
    }
    
    /**
     * �����Ƴ�ָ���ص�����
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
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = "remove";

        // �������л�ȡ�ص�����ID
        String[] contentId = request.getParameterValues("dealRef");

		String actionType = "�����Ƴ�ָ���ص�����";
		boolean actionResult = true;
		String actionDesc = "";
		StringBuffer sb = new StringBuffer();
		for(String temp : contentId)
		{
			sb.append(temp).append(".");
		}
		String actionTarget = sb.toString();
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�����Ƴ�ָ���ص�����");
        }

        try
        {
            PivotContentBO.getInstance().removeContentID(contentId);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "�����Ƴ�ָ���ص����ݳ���!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            LOG.error(e);
            this.saveMessagesValue(request, "�����Ƴ�ָ���ص����ݳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
		// д������־
		actionDesc = "ɾ���ص����ݳɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "ɾ���ص����ݳɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "content.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * ����ص������б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "importData";
        String ret = "";

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action����ʼ");
        }
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
        
		String actionType = "��������ص������б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = dataFile.getFileName();
		
//      У���ļ���׺��
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 

        try
        {
            // ����ص������б�
            ret = PivotContentBO.getInstance().importPivotContent(dataFile);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "��������ص������б����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            LOG.error(e);
            
            if (e.getErrorCode() == RepositoryBOCode.CATEGORY_DEVICE)
            {
                this.saveMessages(request, e.getMessage());
            }
            else
            {
                this.saveMessagesValue(request, "��������ص������б����");
            }
            
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
		// д������־
		actionDesc = "����ص������б�����ɹ���" + ret;
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "����ص������б�����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "content.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * �鿴�ص���������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "detail";
        
        String contentId = this.getParameter(request, "contentId").trim();
        Object content;
        
        try
        {
            String id = PivotContentBO.getInstance().queryContentId(contentId);
            // �ҳ�������Ϣ
            Node node = Repository.getInstance().getNode(id);
            LOG.debug("the node type is :" + node.getType());
            content = Repository.getInstance().getNode(id,
                                                       node.getType());
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�鿴�ص������������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        request.setAttribute("content", content);
        return mapping.findForward(forward);
    }
    
    /**
     * �����ص�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward downloadData(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String fileName = "";
        
        try
        {
            fileName = PivotContentBO.getInstance().downloadData();
            PivotContentBO.getInstance().downloadFile(fileName, response);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�����ص����ݳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        return null;
    }
}
