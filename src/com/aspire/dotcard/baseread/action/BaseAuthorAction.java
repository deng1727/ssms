package com.aspire.dotcard.baseread.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
import com.aspire.dotcard.baseread.bo.BaseAuthorBO;
import com.aspire.dotcard.baseread.bo.ReadCategoryBO;
import com.aspire.dotcard.baseread.vo.BookAuthorVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.UploadFileKeyResUtil;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * @author wangminlong
 * 
 */
public class BaseAuthorAction extends BaseAction
{

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(BaseAuthorAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");

		// �������л�ȡ��������
		String perType = this.getParameter(request, "perType").trim();

		if ("query".equals(perType))
		{
			return query(mapping, form, request, response);
		}
		else if ("mod".equals(perType))
		{
			return mod(mapping, form, request, response);
		}
		else if ("update".equals(perType))
		{
			return update(mapping, form, request, response);
		}
		else if ("input".equals(perType))
		{
			return input(mapping, form, request, response);
		}
		else if ("export".equals(perType))
		{
			return doExport(mapping, form, request, response, false);
		}
		else if ("allExport".equals(perType))
		{
			return doExport(mapping, form, request, response, true);
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
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "query";
		PageResult page = new PageResult(request);
		String authorId = this.getParameter(request, "authorId").trim();
		String authorName = this.getParameter(request, "authorName").trim();
		String isFirst = this.getParameter(request, "isFirst").trim();

        String actionType = "��ѯͼ�������б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = authorId;
		
		BookAuthorVO vo = new BookAuthorVO();
		vo.setAuthorId(authorId);
		vo.setAuthorName(authorName);

		if (LOG.isDebugEnabled())
		{
			LOG.info("�������л�ȡ��ѯ������authorId = " + authorId + ",authorName="
					+ authorName);
		}

		if (!"1".equals(isFirst))
		{
			try
			{
				BaseAuthorBO.getInstance().queryBaseAuthorList(page, vo);
			}
			catch (BOException e)
			{
				LOG.error(e);
				
				// д������־
				actionResult = false;
				actionDesc = "��ѯͼ�������б����";
		        this.actionLog(request,
		                       actionType,
		                       actionTarget,
		                       actionResult,
		                       actionDesc);
				
				this.saveMessagesValue(request, "��ѯ�����Ķ�������Ϣ�б����");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
		}
		
		// д������־
		actionDesc = "��ѯͼ�������б�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
		
		request.setAttribute("isFirst", isFirst);
		request.setAttribute("perType", forward);
		request.setAttribute("PageResult", page);
		request.setAttribute("authorId", authorId);
		request.setAttribute("authorName", authorName);
		return mapping.findForward(forward);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward mod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "mod";
		String authorId = this.getParameter(request, "authorId").trim();
		List keyBaseList = null;
		BookAuthorVO vo = null;
		
		String actionType = "�޸�ͼ������ǰ��ѯ";
		boolean actionResult = true;
		String actionDesc = "�޸�ͼ������ǰ��ѯ�ɹ�";
		String actionTarget = authorId;
		
		try
		{
			// �õ���ǰ������Ϣ
			vo = BaseAuthorBO.getInstance().queryBaseAuthorVO(authorId);
			keyBaseList = BaseAuthorBO.getInstance().queryAuthorIdKeyResource(
					authorId);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "�޸�ͼ������ǰ��ѯ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "��ѯ�Ķ�����������Ϣ����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		request.setAttribute("keyBaseList", keyBaseList);
		request.setAttribute("bookAuthorVO", vo);
		return mapping.findForward(forward);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");

		String forward = Constants.FORWARD_COMMON_FAILURE;
		FileForm fileForm = (FileForm) form;

		BookAuthorVO authorVO = new BookAuthorVO();
		String authorId = this.getParameter(request, "authorId").trim();
		String nameLetter = this.getParameter(request, "nameLetter").trim();
		String isoriginal = this.getParameter(request, "isOriginal").trim();
		String ispublish = this.getParameter(request, "isPublish").trim();
		String desc = this.getParameter(request, "desc").trim();
		
		 Hashtable  files = fileForm.getMultipartRequestHandler().getFileElements();
	        FormFile uploadFile = (FormFile)files.get("authorPic");
	        
		String recommend_manual = this
				.getParameter(request, "recommendManual").trim();
		
		String actionType = "�޸�ͼ��������Ϣ";
		boolean actionResult = true;
		String actionDesc = "�޸�ͼ��������Ϣ�ɹ�";
		String actionTarget = authorId;

		List keyBaseList = BaseAuthorBO.getInstance().queryAuthorKeyBaseList(
				authorId);

		authorVO.setAuthorId(authorId);
		authorVO.setNameLetter(nameLetter);
		authorVO.setIsOriginal(isoriginal);
		authorVO.setIsPublish(ispublish);
		authorVO.setRecommendManual(recommend_manual);	
		authorVO.setDescription(desc);
		
		int fsize = uploadFile.getFileSize();
		if(fsize>0){
	
		String authorPic = BaseAuthorBO.getInstance().uploadAuthorPicURL(uploadFile, authorId);
		authorVO.setAuthorPic(authorPic);
		
		}
		try
		{
			// ���ڱ��ͼ�����
			BaseAuthorBO.getInstance().updateReadAuthor(authorVO);

			if (keyBaseList != null && keyBaseList.size() > 0)
			{
				this.saveReadAuthorKeyResource(keyBaseList, fileForm, authorId,
						request);
			}
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "�޸�ͼ��������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "���»����Ķ�ָ�������������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "���»����Ķ�ָ�����߳ɹ�");
		request.setAttribute(Constants.PARA_GOURL,
				"queryAuthor.do?perType=query&isFirst=1");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		return mapping.findForward(forward);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
        String forward = "input";
        String ret = "";

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action����ʼ");
        }
        
		String actionType = "�����޸�ͼ��������Ϣ";
		boolean actionResult = true;
		String actionDesc = "�����޸�ͼ��������Ϣ�ɹ�";
		String actionTarget = "";
        
        // �������л�ȡ��������
        DataImportForm iForm=(DataImportForm)form;
        
        // У���ļ���׺��
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        FormFile dataFile = iForm.getDataFile();
        
        try
        {
            // �ļ���������ͼ����Ʒ�ϼ�
            ret = BaseAuthorBO.getInstance().importAuthorData(dataFile);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�����޸�ͼ��������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�����ļ�������������Ķ����߳���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�ļ�������������Ķ����߲����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryAuthor.do?perType=query&isFirst=1");
        return mapping.findForward(forward);
    }

	/**
	 * ���ڵ�����ǰ����
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
		
		String actionType = "����ͼ��������Ϣ";
		boolean actionResult = true;
		String actionDesc = "����ͼ��������Ϣ�ɹ�";
		String actionTarget = "";

		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_author_"
				+ System.currentTimeMillis() + ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			BaseAuthorBO.getInstance().exportQueryData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "����ͼ��������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
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
			{
			}
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
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
		return null;
	}

	/**
	 * @desc ������չ�ֶ�
	 * @author dongke Aug 8, 2011
	 * @throws BOException
	 */
	public void saveReadAuthorKeyResource(List keyBaseList, FileForm fileForm,
			String cid, HttpServletRequest request) throws BOException
	{
		// ��Դ��������ģ��·��
		String resServerPath = ConfigFactory.getSystemConfig().getModuleConfig(
				"baseRead").getItemValue("resServerPath");
		List delResourcelist = new ArrayList();

		this.saveKeyResource(keyBaseList, delResourcelist, fileForm, cid,
				request, resServerPath, "author");

		ReadCategoryBO.getInstance().saveKeyResource(keyBaseList);

		KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
	}
}
