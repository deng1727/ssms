package com.aspire.dotcard.basemusic.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.aspire.dotcard.basemusic.bo.BaseSingerBO;
import com.aspire.dotcard.basemusic.vo.BaseSingerVO;
import com.aspire.dotcard.baseread.bo.ReadCategoryBO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * @author wangminlong
 * 
 */
public class BaseSingerAction extends BaseAction
{

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(BaseSingerAction.class);

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
		String singerId = this.getParameter(request, "singerId").trim();
		String singerName = this.getParameter(request, "singerName").trim();
		String isFirst = this.getParameter(request, "isFirst").trim();

        String actionType = "��ѯ�����б�";
		boolean actionResult = true;
		String actionDesc = "��ѯ�����б�ɹ�";
		String actionTarget = singerId;
		
		BaseSingerVO vo = new BaseSingerVO();
		vo.setSingerId(singerId);
		vo.setSingerName(singerName);

		if (LOG.isDebugEnabled())
		{
			LOG.info("�������л�ȡ��ѯ������singerId = " + singerId + ",singerName="
					+ singerName);
		}

		if (!"1".equals(isFirst))
		{
			try
			{
				BaseSingerBO.getInstance().queryBaseSingerList(page, vo);
			}
			catch (BOException e)
			{
				LOG.error(e);
				
	            // д������־
				actionResult = false;
				actionDesc = "��ѯ�����б����";
		        this.actionLog(request,
		                       actionType,
		                       actionTarget,
		                       actionResult,
		                       actionDesc);
		        
				this.saveMessagesValue(request, "��ѯ�������ָ�����Ϣ�б����");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
		}
		
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		request.setAttribute("isFirst", isFirst);
		request.setAttribute("perType", forward);
		request.setAttribute("PageResult", page);
		request.setAttribute("singerId", singerId);
		request.setAttribute("singerName", singerName);
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
		String singerId = this.getParameter(request, "singerId").trim();
		List keyBaseList = null;
		BaseSingerVO vo = null;
		try
		{
			// �õ���ǰ������Ϣ
			vo = BaseSingerBO.getInstance().queryBaseSingerVO(singerId);
			keyBaseList = BaseSingerBO.getInstance().querySingerIdKeyResource(
					singerId);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ�������ָ���������Ϣ����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("keyBaseList", keyBaseList);
		request.setAttribute("baseSingerVO", vo);
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

		BaseSingerVO singerVO = new BaseSingerVO();
		String singerId = this.getParameter(request, "singerId").trim();
		String type = this.getParameter(request, "type").trim();
		
        String actionType = "�޸ĸ�����Ϣ";
		boolean actionResult = true;
		String actionDesc = "�޸ĸ�����Ϣ�ɹ�";
		String actionTarget = singerId;

		List keyBaseList = BaseSingerBO.getInstance().querySingerKeyBaseList(
				singerId);

		singerVO.setSingerId(singerId);
		singerVO.setType(type);

		try
		{
			// ���ڱ��ͼ�����
			BaseSingerBO.getInstance().updateBaseSinger(singerVO);

			if (keyBaseList != null && keyBaseList.size() > 0)
			{
				this.saveBaseSingerKeyResource(keyBaseList, fileForm, singerId,
						request);
			}
		}
		catch (BOException e)
		{
			LOG.error(e);
			
            // д������־
			actionResult = false;
			actionDesc = "�޸ĸ�����Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "���»������ָ����������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "���»������ָ��ֳɹ�");
		request.setAttribute(Constants.PARA_GOURL,
				"querySinger.do?perType=query&isFirst=1");
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
        
        String actionType = "�ļ���������������ָ���";
		boolean actionResult = true;
		String actionDesc = "�ļ���������������ָ��ֳɹ�";
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
            ret = BaseSingerBO.getInstance().importSingerData(dataFile);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�ļ���������������ָ��ֳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�����ļ���������������ָ��ֳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�ļ���������������ָ��ֲ����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "querySinger.do?perType=query&isFirst=1");
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
		
		String actionType = "�����������ָ���";
		boolean actionResult = true;
		String actionDesc = "�����������ָ��ֳɹ�";
		String actionTarget = "";

		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_singer_"
				+ System.currentTimeMillis() + ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			BaseSingerBO.getInstance().exportQueryData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
            // д������־
			actionResult = false;
			actionDesc = "�����������ָ��ֳ���";
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
	public void saveBaseSingerKeyResource(List keyBaseList, FileForm fileForm,
			String cid, HttpServletRequest request) throws BOException
	{
		// ��Դ��������ģ��·��
		String resServerPath = ConfigFactory.getSystemConfig().getModuleConfig(
				"basemusic").getItemValue("resServerPath");
		List delResourcelist = new ArrayList();

		this.saveKeyResource(keyBaseList, delResourcelist, fileForm, cid,
				request, resServerPath, "singer");

		ReadCategoryBO.getInstance().saveKeyResource(keyBaseList);

		KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
	}
}
