/*
 * �ļ�����BookRefAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.dotcard.baseread.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.bo.CategoryApprovalBO;
import com.aspire.dotcard.baseread.bo.ReadReferenceBO;
import com.aspire.dotcard.baseread.vo.ReadReferenceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicRefBO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BaseReadReferenceAction extends BaseAction
{
    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BaseReadReferenceAction.class);
    
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
        String perTypeFlag=this.getParameter(request, "perTypeFlag");
        boolean flag=false;
        if("export".equals(perTypeFlag)){
        	flag=false;
        }else{
        	flag=true;
        }
        if ("export".equals(perType))
        {
            return doExport(mapping, form, request, response, flag);
        }  
        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if ("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if ("setSort".equals(perType))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryRead".equals(perType))
        {
            return queryRead(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }else if ("showRead".equals(perType))
        {
            return showRead(mapping, form, request, response);
        }else if ("saveRead".equals(perType))
        {
            return saveRead(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }

    /**
     * �õ�ͼ��ָ�������µ���Ʒ�б�
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
        ReadReferenceVO vo = new ReadReferenceVO();
        PageResult page = new PageResult(request);

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String bookId = this.getParameter(request, "bookId").trim();
        String bookName = this.getParameter(request, "bookName").trim();
        String authorName = this.getParameter(request, "authorName").trim();
        String typeName = this.getParameter(request, "typeName").trim();
        String chargeType = this.getParameter(request, "chargeType").trim();
        String approvalStatus = this.getParameter(request, "approvalStatus");

        String actionType = "��ѯ�Ķ���������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "��ѯ�Ķ���������Ʒ��Ϣ�ɹ�";
		String actionTarget = categoryId;
		
        vo.setCId(categoryId);
        vo.setBookId(bookId);
        vo.setBookName(bookName);
        vo.setAuthorName(authorName);
        vo.setTypeName(typeName);
        vo.setChargeType(chargeType);
        vo.setVerify_status(approvalStatus);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }
        Map<String,Object> map = new HashMap<String,Object>();

        try
        {
        	map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
            ReadReferenceBO.getInstance().queryReadRefList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "��ѯ�Ķ���������Ʒ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "��ѯָ���Ķ������µ���Ʒ�б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("bookId", bookId);
        request.setAttribute("bookName", bookName);
        request.setAttribute("authorName", authorName);
        request.setAttribute("typeName", typeName);
        request.setAttribute("chargeType", chargeType);
        request.setAttribute("categoryContent", map);
        request.setAttribute("approvalStatus", approvalStatus);
        return mapping.findForward(forward);
    }
    
    /**
     * �����Ƴ�ָ��������ָ����ͼ��
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

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String[] bookId = request.getParameterValues("dealRef");

        String actionType = "ɾ���Ķ���������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "ɾ���Ķ���������Ʒ��Ϣ�ɹ�";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            ReadReferenceBO.getInstance().removeReadRefs(categoryId, bookId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "ɾ���Ķ���������Ʒ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�����Ƴ�ָ��������ָ����ͼ�����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "ɾ��ͼ��ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * ���������Ķ�������ͼ����Ʒ����ֵ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward setSort(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "setSort";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();
        
        String actionType = "�޸��Ķ���������Ʒ�������Ϣ";
		boolean actionResult = true;
		String actionDesc = "�޸��Ķ���������Ʒ�������Ϣ�ɹ�";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // ����ͼ�������ͼ����Ʒ����ֵ
            ReadReferenceBO.getInstance().setReadSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�޸��Ķ���������Ʒ�������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "���������Ķ�����������ͼ������ֵʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "����ͼ����Ʒ����ֵ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * ���ڲ�ѯͼ���б�
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryRead(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "queryRead";

        // �������л�ȡ��������
        String isFirst = this.getParameter(request, "isFirst").trim();
        String bookName = this.getParameter(request, "bookName").trim();
        String bookId = this.getParameter(request, "bookId").trim();
        String authorName = this.getParameter(request, "authorName").trim();
        String typeName = this.getParameter(request, "typeName").trim();
        String chargeType = this.getParameter(request, "chargeType").trim();
        ReadReferenceVO vo = new ReadReferenceVO();
        PageResult page = new PageResult(request);
        
        // ����ǵ�һ�Ρ�����
        if("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("perType", forward);
            request.setAttribute("PageResult", page);
            request.setAttribute("bookName", bookName);
            request.setAttribute("bookId", bookId);
            request.setAttribute("authorName", authorName);
            request.setAttribute("typeName", typeName);
            request.setAttribute("chargeType", chargeType);
            
            return mapping.findForward(forward);
        }
        
        vo.setBookId(bookId);
        vo.setBookName(bookName);
        vo.setAuthorName(authorName);
        vo.setTypeName(typeName);
        vo.setChargeType(chargeType);
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡͼ��������bookName=" + bookName + ", bookId="
                      + bookId + ", authorName=" + authorName);
        }

        try
        {
            // ��ѯͼ���б������ϼ����»�����
            ReadReferenceBO.getInstance().queryReadList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯͼ���б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("bookName", bookName);
        request.setAttribute("bookId", bookId);
        request.setAttribute("authorName", authorName);
        request.setAttribute("typeName", typeName);
        request.setAttribute("chargeType", chargeType);
        
        return mapping.findForward(forward);
    }
    
    /**
     * �������ָ����ͼ����������
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
        String forward = "add";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addBookId = this.getParameter(request, "addBookId").trim();

        String actionType = "�����Ķ���������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "�����Ķ���������Ʒ��Ϣ�ɹ�";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // �鿴ԭ�����Ƿ�����û���Ҫ������������Ϣ
            String ret = ReadReferenceBO.getInstance().isHasReadRefs(categoryId,
                                                                    addBookId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "ԭ���ܴ����û���ѡ����ͼ�飺" + ret);
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�����Ķ���������Ʒ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�鿴ԭ�����Ƿ�����û���Ҫ����ʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // ����ָ��ͼ����ָ��������
            ReadReferenceBO.getInstance().addReadRefs(categoryId, addBookId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�����Ķ���������Ʒ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�������ָ����ͼ��������ʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "���ͼ��ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * �ļ���������ͼ����Ʒ�ϼ�
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
        
        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addType = this.getParameter(request, "addType").trim();
        DataImportForm iForm=(DataImportForm)form;
        
        String actionType = "�����Ķ���������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "�����Ķ���������Ʒ��Ϣ�ɹ�";
		String actionTarget = categoryId;
        
//        // У���ļ���׺��  //removed by aiyan 2012-12-25 ��Щ������ҳ��JS����
//        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
//        {
//            this.saveMessages(request, "�ļ���׺������");
//            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
//        }
        
        FormFile dataFile = iForm.getDataFile();
        
        try
        {
        	if("ADD".equals(addType)){
        		ret =ReadReferenceBO.getInstance().importContentADD(dataFile, categoryId);
        	}else if("ALL".equals(addType)){
        		ret = ReadReferenceBO.getInstance().importContentALL(dataFile, categoryId);
        	}
            
        }
        
//        try
//        {
//            // �ļ���������ͼ����Ʒ�ϼ�
//            ret = ReadReferenceBO.getInstance().importContentExigence(dataFile, categoryId);
//        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�����Ķ���������Ʒ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�����ļ���������ͼ����Ʒ�ϼܳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�ļ���������ͼ����Ʒ�ϼܲ����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    
    /**
     * ���ڲ�ѯͼ������
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward showRead(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "showRead";

        // �������л�ȡ��������
     
        String bookId = this.getParameter(request, "bookId").trim();
       
		
        ReadReferenceVO vo = null;
        List keyBaseList = null;
        try
        {
            // ��ѯͼ������
        	vo =  ReadReferenceBO.getInstance().queryReadInfo(bookId);
        	keyBaseList = ReadReferenceBO.getInstance().queryReadKeyResource(bookId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯͼ���������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }    
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("bookId", bookId);
        return mapping.findForward(forward);
    }
    
    /**
     * ���ڱ���ͼ����չ����
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward saveRead(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "saveRead";

        // �������л�ȡ��������
        String bookId = this.getParameter(request, "bookId").trim();
        FileForm fileForm = (FileForm) form;
        
        String actionType = "�޸��Ķ���������Ʒ��Ϣ����";
		boolean actionResult = true;
		String actionDesc = "�޸��Ķ���������Ʒ��Ϣ����ɹ�";
		String actionTarget = bookId;
		
        // У���ļ���׺��
        if(!fileForm.checkFileNameExtension(new String[]{"png"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        ReadReferenceVO vo = null;
        List keyBaseList = null;
        try
        {
            // ��ѯͼ������
        	vo =  ReadReferenceBO.getInstance().queryReadInfo(bookId);
        	keyBaseList = ReadReferenceBO.getInstance().queryReadKeyResource(bookId);
        	
        	if (keyBaseList != null && keyBaseList.size() > 0)
    		{
    			this.saveReadKeyResource(keyBaseList,fileForm,bookId,request);
    			
    		}
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "ɾ���Ķ�������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "����ͼ����չ��Ϣ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        this.saveMessages(request, "����ͼ����չ����ֵ�ɹ�!");

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        forward = Constants.FORWARD_COMMON_SUCCESS;  
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("bookId", bookId);
        return mapping.findForward(forward);
    }
    
    /**
     * 
     *@desc ������չ�ֶ�
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveReadKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//��Դ��������ģ��·��
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("baseRead").getItemValue("resServerPath");  
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"read");  
    	
    	NewMusicRefBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
    /**
     * ��������
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param isAll
     * @return
     * @throws BOException
     */
    public ActionForward doExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isAll) throws BOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("doExport in......");
			log.debug("excel����.......");
		}
		
		String actionType = "����ͼ����Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "����ͼ����Ʒ��Ϣ�ɹ�";
		String actionTarget = "";
		
		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "vo_newbookreference_" + System.currentTimeMillis()
				+ ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			ReadReferenceBO.getInstance()
					.exportNewBookReferenceData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "����������Ʒ��Ϣ����";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
		}
		finally
		{
			try
			{
				if (wwb != null)
				{
					wwb.write();
					wwb.close();
				}
				if (os != null)
				{
					os.close();
				}
			}
			catch (Exception e)
			{}
		}
		response.setHeader("Content-disposition", "attachment;filename="
				+ excelName);
		response.setContentType("application/msexcel");
		try
		{
			FileInputStream fileInputStream = new FileInputStream(excelName);
			OutputStream out = response.getOutputStream();
			int i = 0;
			while ((i = fileInputStream.read()) != -1)
			{
				out.write(i);
			}
			fileInputStream.close();
			File file = new File(excelName);
			file.delete();
		}
		catch (FileNotFoundException e)
		{
			log.error(e);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			log.error(e);
			e.printStackTrace();
		}
		
		// д������־
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		return null;
	}
}
