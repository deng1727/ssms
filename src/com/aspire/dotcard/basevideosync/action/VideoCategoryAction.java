package com.aspire.dotcard.basevideosync.action;

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
import com.aspire.dotcard.basevideosync.bo.VideoBO;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.vo.VideoCategoryVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.UploadFileKeyResUtil;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

public class VideoCategoryAction extends BaseAction{

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(VideoCategoryAction.class);

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
		}else if ("approval".equals(perType))
		{
			return approval(mapping, form, request, response);
		}
		else
		{
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
			return mapping.findForward(forward);
		}
	}
	
	/**
     * ��ѯ����Ƶ������Ϣ
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
		String operation = this.getParameter(request, "operation").trim();
		
		String actionType = "��ѯ����Ƶ������Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		String path = "";
		
		VideoCategoryVO videoCategoryVO;
		try
		{
			// ��ѯ������Ϣ
			videoCategoryVO = VideoBO.getInstance().queryVideoCategoryVO(categoryId);
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 5);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ����Ƶ������Ϣ����");
			
			// д������־
			actionResult = false;
			actionDesc = "��ѯ����Ƶ������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// д������־
		actionDesc = "��ѯ��Ƶ�����б�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("path", path);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("operation", operation);
		request.setAttribute("videoCategoryVO", videoCategoryVO);

		return mapping.findForward(forward);
	}

	/**
     * ɾ������Ƶ����
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
		
		String actionType = "ɾ������Ƶ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		
		try
		{
			// �Ƿ�����ӻ���
			if (0 < VideoBO.getInstance().hasChild(categoryId))
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
			if (0 < VideoBO.getInstance().hasReference(categoryId))
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
			VideoBO.getInstance().delVideoCategory(categoryId);

		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "ɾ������Ƶ���ܳ���");
			
			// д������־
			actionResult = false;
			actionDesc = "ɾ������Ƶ���ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "ɾ������Ƶ���ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
						"../newvideo/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// д������־
		actionDesc = "ɾ������Ƶ���ܳɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}

	/**
     * ��ת����������Ƶ����ҳ��
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
     * ��������Ƶ����
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
		String actionType = "��������Ƶ����";
		boolean actionResult = true;
		String actionDesc = "";
		
		VideoCategoryVO videoCategoryVO = new VideoCategoryVO();
		String pCategoryId = this.getParameter(request, "pCategoryId").trim();
		String cname = this.getParameter(request, "cname").trim();
		String cdesc = this.getParameter(request, "cdesc").trim();
		String sortId = this.getParameter(request, "sortId").trim();
		String isShow = this.getParameter(request, "isShow").trim();
		String categoryId = VideoBO.getInstance().getVideoCategoryId();

		videoCategoryVO.setCategoryId(categoryId);
		videoCategoryVO.setParentcId(pCategoryId);
		videoCategoryVO.setCname(cname);
		videoCategoryVO.setCdesc(cdesc);
		videoCategoryVO.setSortId(Integer.parseInt(sortId));
		videoCategoryVO.setIsShow(Integer.parseInt(isShow));
		videoCategoryVO.setVideoStatus("0");
		
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
			String resourcePicFilePath = BaseVideoConfig.VideoCategoryPicFTPDir;
			
			String picture = UploadFileKeyResUtil.getInstance()
			.upLoadfileToResServer(uploadFile, resourcePicFilePath, "",
					categoryId, "");
			
			videoCategoryVO.setPicture(picture);
			// ������������Ƶ����
			VideoBO.getInstance().addVideoCategory(videoCategoryVO);

		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��������Ƶ���ܳ���");
			
			// д������־
			actionResult = false;
			actionDesc = "��������Ƶ���ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "��������Ƶ���ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../newvideo/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// д������־
		actionDesc = "��������Ƶ���ܳɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}

	/**
     * ��ת���޸�����Ƶ����ҳ��
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
		VideoCategoryVO videoCategoryVO;
		
		String actionType = "�޸�����Ƶ����ǰ׼����ѯ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		String path = "";

		try
		{
			// �õ���ǰ������Ϣ
			videoCategoryVO = VideoBO.getInstance().queryVideoCategoryVO(
					categoryId);
			
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 5);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ����Ƶ�����б����");
			
			// д������־
			actionResult = false;
			actionDesc = "�޸�����Ƶ����ǰ׼����ѯ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("path", path);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("videoCategoryVO", videoCategoryVO);
		
		// д������־
		actionDesc = "�޸�����Ƶ����ǰ׼����ѯ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}

	/**
     * �޸�����Ƶ������Ϣ
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

		VideoCategoryVO videoCategoryVO = new VideoCategoryVO();
		String categoryId = this.getParameter(request, "categoryId").trim();
		String cname = this.getParameter(request, "cname").trim();
		String cdesc = this.getParameter(request, "cdesc").trim();
		String sortId = this.getParameter(request, "sortId").trim();
		String isShow = this.getParameter(request, "isShow").trim();
		String oldPicture = this.getParameter(request, "oldPicture").trim();
		String clearPicture = request.getParameter("clear_picture");
		
		videoCategoryVO.setCategoryId(categoryId);
		videoCategoryVO.setCname(cname);
		videoCategoryVO.setCdesc(cdesc);
		videoCategoryVO.setSortId(Integer.parseInt(sortId));
		videoCategoryVO.setIsShow(Integer.parseInt(isShow));
		videoCategoryVO.setPicture(oldPicture);
		//���û��ܱ�Ϊ�༭״̬
		videoCategoryVO.setVideoStatus("0");
		
		String actionType = "�޸�����Ƶ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

		
		try
		{
			if(clearPicture != null && clearPicture.equals("1")){
				videoCategoryVO.setPicture("");
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
				String resourcePicFilePath = BaseVideoConfig.VideoCategoryPicFTPDir;
				if (uploadFile.getFileName() != null
						&& !uploadFile.getFileName().equals("")) {
					String picture = UploadFileKeyResUtil.getInstance()
					.upLoadfileToResServer(uploadFile,
							resourcePicFilePath, "", categoryId, "");
					videoCategoryVO.setPicture(picture);
				}
			}

			// ���ڱ����Ƶ����
			VideoBO.getInstance().updateVideoCategory(videoCategoryVO);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "�޸�����Ƶ�����б����");

			// д������־
			actionResult = false;
			actionDesc = "�޸�����Ƶ���ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "�޸�����Ƶ���ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../newvideo/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// д������־
		actionDesc = "�޸�����Ƶ���ܳɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}
	 /**
	 * ��Ƶ�����ύ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		
		try {
			VideoBO.getInstance().approvalCategory(request,categoryId);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "�ύ������ƵPOMS���ܳ���");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "POMS���ܹ����ύ�����ɹ�");
		request.setAttribute(Constants.PARA_GOURL, "videoCategory.do?perType=query&categoryId=" + categoryId);
		return mapping.findForward(forward);
	}
}
