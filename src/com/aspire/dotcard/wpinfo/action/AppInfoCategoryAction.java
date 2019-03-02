package com.aspire.dotcard.wpinfo.action;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.wpinfo.bo.AppInfoBO;
import com.aspire.dotcard.wpinfo.config.AppInfoConfig;
import com.aspire.dotcard.wpinfo.vo.AppInfoCategoryVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.UploadFileKeyResUtil;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

public class AppInfoCategoryAction extends BaseAction{

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(AppInfoCategoryAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		// �������л�ȡ��������
		String perType = this.getParameter(request, "perType").trim();

		if ("query".equals(perType))
		{
			return query(mapping, form, request, response);
		}
		else if ("del".equals(perType))
		{
			return del(mapping, form, request, response);
		}
		else if ("toAdd".equals(perType))
		{
			return toAdd(mapping, form, request, response);
		}
		else if ("add".equals(perType))
		{
			return add(mapping, form, request, response);
		}
		else if ("toUpdate".equals(perType))
		{
			return toUpdate(mapping, form, request, response);
		}
		else if ("update".equals(perType))
		{
			return update(mapping, form, request, response);
		}
		else
		{
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
			return mapping.findForward(forward);
		}
	}
	
	/**
     * wp��ۻ�����Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "query";
		String categoryId = this.getParameter(request, "categoryId").trim();
		
		String actionType = "wp��ۻ�����Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		String path = "";
		
		AppInfoCategoryVO appInfoCategoryVO;
		try
		{
			// ��ѯ������Ϣ
			appInfoCategoryVO = AppInfoBO.getInstance().queryAppInfoCategoryVO(categoryId);
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 6);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯwp���Ӧ�û�����Ϣ����");
			
			// д������־
			actionResult = false;
			actionDesc = "��ѯwp���Ӧ�û�����Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// д������־
		actionDesc = "��ѯwp���Ӧ�û����б�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("path", path);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("appInfoCategoryVO", appInfoCategoryVO);

		return mapping.findForward(forward);
	}

	/**
     * ɾ��wp��ۻ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		
		String actionType = "ɾ��wp��ۻ���";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		
		try
		{
			// �Ƿ�����ӻ���
			if (0 < AppInfoBO.getInstance().hasChild(categoryId))
			{
				this.saveMessagesValue(request, "�����������ӻ��ܲ�����ɾ����");
				
				// д������־
				actionResult = false;
				actionDesc = "�����������ӻ��ܲ�����ɾ����";
		        this.actionLog(request,
		                       actionType,
		                       actionTarget,
		                       actionResult,
		                       actionDesc);
		        
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}

			// �Ƿ������Ʒ
			if (0 < AppInfoBO.getInstance().hasReference(categoryId))
			{
				this.saveMessagesValue(request, "������������Ʒ������ɾ����");
				
				// д������־
				actionResult = false;
				actionDesc = "������������Ʒ������ɾ����";
		        this.actionLog(request,
		                       actionType,
		                       actionTarget,
		                       actionResult,
		                       actionDesc);
		        
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}

			// ����ɾ��ָ������
			AppInfoBO.getInstance().delAppInfoCategory(categoryId);

		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "ɾ��wp��ۻ��ܳ���");
			
			// д������־
			actionResult = false;
			actionDesc = "ɾ��wp��ۻ��ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "ɾ��wp��ۻ��ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
						"../wpinfo/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// д������־
		actionDesc = "ɾ��wp��ۻ��ܳɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}

	/**
     * ��ת������wp��ۻ���ҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	public ActionForward toAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		String forward = "toAdd";
		String pCategoryId = this.getParameter(request, "pCategoryId")
				.trim();
		request.setAttribute("pCategoryId", pCategoryId);
		
		return mapping.findForward(forward);
	}

	/**
     * ����wp��ۻ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = Constants.FORWARD_COMMON_FAILURE;
		String actionType = "����wp��ۻ���";
		boolean actionResult = true;
		String actionDesc = "";
		
		AppInfoCategoryVO appInfoCategoryVO = new AppInfoCategoryVO();
		String pCategoryId = this.getParameter(request, "pCategoryId").trim();
		String cname = this.getParameter(request, "cname").trim();
		String cdesc = this.getParameter(request, "cdesc").trim();
		String sortId = this.getParameter(request, "sortId").trim();
		String isShow = this.getParameter(request, "isShow").trim();
		String categoryId = AppInfoBO.getInstance().getAppInfoCategoryId();

		appInfoCategoryVO.setCategoryId(categoryId);
		appInfoCategoryVO.setParentcId(pCategoryId);
		appInfoCategoryVO.setCname(cname);
		appInfoCategoryVO.setCdesc(cdesc);
		appInfoCategoryVO.setSortId(Integer.parseInt(sortId));
		appInfoCategoryVO.setIsShow(Integer.parseInt(isShow));
		
		// ��չ�ֶ����
		FileForm fileForm = (FileForm) form;
		
		// У���ļ���׺��
		if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
		{
			this.saveMessages(request, "�ļ���׺������");
			return mapping.findForward(forward);
		}
		
		String actionTarget = categoryId;

		try
		{
			Hashtable files = fileForm.getMultipartRequestHandler().getFileElements();
			FormFile uploadFile = (FormFile) files.get("picture");
			String resourcePicFilePath = AppInfoConfig.AppInfoCategoryPicFTPDir;
			
			String picture = UploadFileKeyResUtil.getInstance()
			.upLoadfileToResServer(uploadFile, resourcePicFilePath, "",
					categoryId, "");
			
			appInfoCategoryVO.setPicture(picture);
			// ��������wp��ۻ���
			AppInfoBO.getInstance().addAppInfoCategory(appInfoCategoryVO);

		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "����wp��ۻ��ܳ���");
			
			// д������־
			actionResult = false;
			actionDesc = "����wp��ۻ��ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "����wp��ۻ��ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../wpinfo/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// д������־
		actionDesc = "����wp��ۻ��ܳɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}

	/**
     * ��ת���޸�wp��ۻ���ҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	public ActionForward toUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "toUpdate";
		String categoryId = this.getParameter(request, "categoryId").trim();
		AppInfoCategoryVO appInfoCategoryVO;
		
		String actionType = "�޸�wp��ۻ���ǰ׼����ѯ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		String path = "";

		try
		{
			// �õ���ǰ������Ϣ
			appInfoCategoryVO = AppInfoBO.getInstance().queryAppInfoCategoryVO(
					categoryId);
			
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 6);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯwp��ۻ����б����");
			
			// д������־
			actionResult = false;
			actionDesc = "�޸�wp��ۻ���ǰ׼����ѯ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("path", path);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("appInfoCategoryVO", appInfoCategoryVO);
		
		// д������־
		actionDesc = "�޸�wp��ۻ���ǰ׼����ѯ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}

	/**
     * �޸�wp��ۻ�����Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");

		String forward = Constants.FORWARD_COMMON_FAILURE;

		AppInfoCategoryVO appInfoCategoryVO = new AppInfoCategoryVO();
		String categoryId = this.getParameter(request, "categoryId").trim();
		String cname = this.getParameter(request, "cname").trim();
		String cdesc = this.getParameter(request, "cdesc").trim();
		String sortId = this.getParameter(request, "sortId").trim();
		String isShow = this.getParameter(request, "isShow").trim();
		String oldPicture = this.getParameter(request, "oldPicture").trim();
		String clearPicture = request.getParameter("clear_picture");
		
		appInfoCategoryVO.setCategoryId(categoryId);
		appInfoCategoryVO.setCname(cname);
		appInfoCategoryVO.setCdesc(cdesc);
		appInfoCategoryVO.setSortId(Integer.parseInt(sortId));
		appInfoCategoryVO.setIsShow(Integer.parseInt(isShow));
		appInfoCategoryVO.setPicture(oldPicture);
		
		String actionType = "�޸�wp��ۻ���";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

		
		try
		{
			if(clearPicture != null && clearPicture.equals("1")){
				appInfoCategoryVO.setPicture("");
			}else{
				// ��չ�ֶ����
				FileForm fileForm = (FileForm) form;
				
				// У���ļ���׺��
				if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
				{
					this.saveMessages(request, "�ļ���׺������");
					return mapping.findForward(forward);
				}
				Hashtable files = fileForm.getMultipartRequestHandler().getFileElements();
				FormFile uploadFile = (FormFile) files.get("picture");
				String resourcePicFilePath = AppInfoConfig.AppInfoCategoryPicFTPDir;
				if (uploadFile.getFileName() != null
						&& !uploadFile.getFileName().equals("")) {
					String picture = UploadFileKeyResUtil.getInstance()
					.upLoadfileToResServer(uploadFile,
							resourcePicFilePath, "", categoryId, "");
					appInfoCategoryVO.setPicture(picture);
				}
			}

			// ���ڱ��wp��ۻ���
			AppInfoBO.getInstance().updateAppInfoCategory(appInfoCategoryVO);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "�޸�wp��ۻ����б����");

			// д������־
			actionResult = false;
			actionDesc = "�޸�wp��ۻ��ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "�޸�wp��ۻ��ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../wpinfo/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// д������־
		actionDesc = "�޸�wp��ۻ��ܳɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}
	
}
