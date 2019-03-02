package com.aspire.ponaadmin.web.comic.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.VideoReferenceBO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.comic.bo.CategoryApprovalBO;
import com.aspire.ponaadmin.web.comic.bo.ReferenceBO;
import com.aspire.ponaadmin.web.comic.vo.ComicChapterVO;
import com.aspire.ponaadmin.web.comic.vo.ReferenceVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

public class ReferenceAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ReferenceAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        // �������л�ȡ��������
        String method = this.getParameter(request, "method").trim();
        String perTypeFlag=this.getParameter(request, "perTypeFlag");
        boolean flag=false;
        if("export".equals(perTypeFlag)){
        	flag=false;
        }else{
        	flag=true;
        }
        if ("export".equals(method))
        {
            return doExport(mapping, form, request, response, flag);
        }  
        if ("query".equals(method))
        {
            return query(mapping, form, request, response);
        }
        else if ("setSort".equals(method))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryItem".equals(method))
        {
            return queryItem(mapping, form, request, response);
        }
        else if ("remove".equals(method))
        {
            return remove(mapping, form, request, response);
        }
        else if ("add".equals(method))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(method))
        {
            return importData(mapping, form, request, response);
        }
        else if ("queryChapter".equals(method))
        {
            return queryChapter(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
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
		
		String actionType = "����������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "����������Ʒ��Ϣ�ɹ�";
		String actionTarget = "";
		
		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "vo_comicreference_" + System.currentTimeMillis()
				+ ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);

			ReferenceBO.getInstance().exportComicReferenceData(request, wwb, isAll);
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

    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
    	
    	String categoryId = request.getParameter("categoryId")==null?"":request.getParameter("categoryId");
    	String contentId = request.getParameter("contentId")==null?"":request.getParameter("contentId");
    	String contentName = request.getParameter("contentName")==null?"":request.getParameter("contentName");
    	String approvalStatus = request.getParameter("approvalStatus")==null?"":request.getParameter("approvalStatus");

    	String actionType = "��ѯ������������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "��ѯ������������Ʒ��Ϣ�ɹ�";
		String actionTarget = categoryId;
    	
        String forward = "query";
        ReferenceVO vo = new ReferenceVO();
        PageResult page = new PageResult(request);
        Map<String,Object> map = new HashMap<String,Object>();

        // �������л�ȡ��������

        vo.setCategoryId(categoryId);
        vo.setContentId(contentId);
        vo.setContentName(contentName);
        vo.setVerify_status(approvalStatus);
        try
        {
        	map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
            ReferenceBO.getInstance().queryReferenceList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "��ѯ������������Ʒ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "��ѯָ�������µ���Ʒ�б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
       
        request.setAttribute("categoryContent", map);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("contentId", contentId);
        request.setAttribute("contentName", contentName);
        request.setAttribute("approvalStatus", approvalStatus);
        return mapping.findForward(forward);
    }


    public ActionForward remove(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = "remove";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String[] id = request.getParameterValues("dealRef");
        
        String actionType = "ɾ��������������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "ɾ��������������Ʒ��Ϣ�ɹ�";
		String actionTarget = categoryId;
		
        try
        {
            ReferenceBO.getInstance().removeReference(categoryId, id);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "ɾ��������������Ʒ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "ɾ����Ʒ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "referenceTree.do?method=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }

    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "add";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        
        String actionType = "����������������Ʒ";
		boolean actionResult = true;
		String actionDesc = "����������������Ʒ�ɹ�";
		String actionTarget = categoryId;

        try
        {
            String ret = ReferenceBO.getInstance().isHasReference(categoryId,
            		contentId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "ԭ���ܴ����û���ѡ��������ID��" + ret);
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�鿴ԭ�����Ƿ�����û���Ҫ����ʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            ReferenceBO.getInstance().addReference(categoryId, contentId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "����������������Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�������ָ������Ʒ������ʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�������Ʒ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "referenceTree.do?method=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }


    public ActionForward queryItem(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "queryItem";

        // �������л�ȡ��������
        String isFirst = this.getParameter(request, "isFirst").trim();
        String contentName = this.getParameter(request, "contentName").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        ReferenceVO vo = new ReferenceVO();
        PageResult page = new PageResult(request);
        
        // ����ǵ�һ�Ρ�����
        if("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("method", forward);
            request.setAttribute("PageResult", page);
            request.setAttribute("contentName", contentName);
            request.setAttribute("contentId", contentId);
            
            return mapping.findForward(forward);
        }
        
        vo.setContentId(contentId);
        vo.setContentName(contentName);
        
       

        try
        {
            ReferenceBO.getInstance().queryList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ�����б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("method", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("contentName", contentName);
        request.setAttribute("contentId", contentId);
        
        return mapping.findForward(forward);
    }

    /**
     * �½ڲ�ѯ
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryChapter(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws BOException
 {
		String forward = "queryChapter";

		// �������л�ȡ��������
	
		String contentId = this.getParameter(request, "contentId").trim();
		ComicChapterVO vo = new ComicChapterVO();
		PageResult page = new PageResult(request);

		
		vo.setContentId(contentId);
		
		try {
			ReferenceBO.getInstance().queryChapterList(page, vo);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ�����б����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("method", forward);
		request.setAttribute("PageResult", page);
		
		request.setAttribute("contentId", contentId);

		return mapping.findForward(forward);
}

    
    public ActionForward setSort(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();
        
        String actionType = "�޸Ķ�����������Ʒ������Ϣ";
		boolean actionResult = true;
		String actionDesc = "�޸Ķ�����������Ʒ������Ϣ�ɹ�";
		String actionTarget = categoryId;
		
        try
        {
            // ����ָ��������
            ReferenceBO.getInstance().setSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�޸Ķ�����������Ʒ������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "����������Ʒ����ֵʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "������Ʒ����ֵ�ɹ�");
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "referenceTree.do?method=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    public ActionForward importData(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "importData";
        String ret = "";
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addType = this.getParameter(request, "addType").trim();
        DataImportForm iForm=(DataImportForm)form;
        
        String actionType = "�������붯����������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "�������붯����������Ʒ��Ϣ�ɹ�";
		String actionTarget = categoryId;
        
		//�����ҳ��JS���ж� removed by aiyan 2012-12-24
//        // У���ļ���׺��
//        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
//        {
//            this.saveMessages(request, "�ļ���׺������");
//            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
//        }
        
        FormFile dataFile = iForm.getDataFile();
        try
        {
        	if("ADD".equals(addType)){
        		ret =ReferenceBO.getInstance().importContentADD(dataFile, categoryId);
        	}else if("ALL".equals(addType)){
        		ret = ReferenceBO.getInstance().importContentALL(dataFile, categoryId);
        	}
        	ReferenceBO.getInstance().approvalCategoryGoods(categoryId);
            
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
	        
            this.saveMessagesValue(request, "�����ļ�����������Ʒ�ϼܳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�ļ�����������Ʒ�ϼܲ����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "referenceTree.do?method=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
}
