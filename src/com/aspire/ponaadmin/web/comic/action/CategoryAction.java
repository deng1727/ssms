package com.aspire.ponaadmin.web.comic.action;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.comic.bo.CategoryBO;
import com.aspire.ponaadmin.web.comic.vo.CategoryVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.UploadFileKeyResUtil;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

public class CategoryAction extends BaseAction {

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(CategoryAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

		// �������л�ȡ��������
		String method = this.getParameter(request, "method").trim();

		if ("query".equals(method)) {
			return query(mapping, form, request, response);
		} else if ("add".equals(method)) {
			return add(mapping, form, request, response);
		} else if ("save".equals(method)) {
			return save(mapping, form, request, response);
		} else if ("mod".equals(method)) {
			return mod(mapping, form, request, response);
		} else if ("update".equals(method)) {
			return update(mapping, form, request, response);
		} else if ("del".equals(method)) {
			return del(mapping, form, request, response);
		} else if ("approval".equals(method)) {
			return approval(mapping, form, request, response);
		}  else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
			return mapping.findForward(forward);
		}
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "query";
		String categoryId = this.getParameter(request, "categoryId").trim();
		String operation = this.getParameter(request, "operation");
		
		String actionType = "��ѯ����������Ϣ";
		boolean actionResult = true;
		String actionDesc = "��ѯ����������Ϣ�ɹ�";
		String actionTarget = categoryId;
		String path = "";
		
		
		CategoryVO categoryVO = null;
		try {
			// ��ѯ������Ϣ
			categoryVO = CategoryBO.getInstance().queryCategoryVO(categoryId);
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 4);
		} catch (BOException e) {
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "��ѯ���������������";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "��ѯ���������������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("path", path);
		request.setAttribute("categoryVO", categoryVO);
		request.setAttribute("operation", operation);
		return mapping.findForward(forward);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "add";
		String parentCategoryId = this
				.getParameter(request, "parentCategoryId").trim();
		CategoryVO categoryVO = new CategoryVO();
		categoryVO.setParentCategoryId(parentCategoryId);
		request.setAttribute("categoryVO", categoryVO);

		return mapping.findForward(forward);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");
		String forward = Constants.FORWARD_COMMON_FAILURE;

		CategoryVO categoryVO = new CategoryVO();
		String categoryName = this.getParameter(request, "categoryName").trim();
		String parentCategoryId = this
				.getParameter(request, "parentCategoryId").trim();
		// String pictrue = this.getParameter(request, "pictrue").trim();
		String delFlag = this.getParameter(request, "delFlag").trim();
		String categoryDesc = this.getParameter(request, "categoryDesc").trim();
		String sortId = this.getParameter(request, "sortId").trim();

		String cid = CategoryBO.getInstance().getComicCategoryId();

		String actionType = "������������";
		boolean actionResult = true;
		String actionDesc = "�����������ܳɹ�";
		String actionTarget = cid;
		
		// ��չ�ֶ����
		FileForm fileForm = (FileForm) form;

        // У���ļ���׺��
        if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		Hashtable files = fileForm.getMultipartRequestHandler().getFileElements();
		FormFile uploadFile = (FormFile) files.get("picture");
		String resourcePicFilePath = ConfigFactory.getSystemConfig()
				.getModuleConfig("music139").getItemValue("DestAlbumFTPDir");

		String picture = UploadFileKeyResUtil.getInstance()
				.upLoadfileToResServer(uploadFile, resourcePicFilePath, "",
						cid, "");

		categoryVO.setCategoryId(cid);
		categoryVO.setParentCategoryId(parentCategoryId);
		categoryVO.setCategoryName(categoryName);
		categoryVO.setCategoryDesc(categoryDesc);
		
		if("0".equals(sortId))
		{
			categoryVO.setSortId(String.valueOf(SEQCategoryUtil.getInstance()
					.getSEQByCategoryType(5)));
		}
		else
		{
			categoryVO.setSortId(sortId);
		}
		
		
		categoryVO.setPicture(picture);
		categoryVO.setDelFlag(delFlag);

		try {
			// �������������ֻ���
			CategoryBO.getInstance().saveCategory(categoryVO);

		} catch (BOException e) {
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "�����������ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "�����������ܳ���");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "�����������ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../comic/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		return mapping.findForward(forward);
	}

	public ActionForward mod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "mod";
		String categoryId = this.getParameter(request, "categoryId").trim();
		CategoryVO categoryVO = null;
		String path = "";
		try {
			// �õ���ǰ������Ϣ
			categoryVO = CategoryBO.getInstance().queryCategoryVO(categoryId);
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 4);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ���������б����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		request.setAttribute("path", path);
		request.setAttribute("categoryVO", categoryVO);
		return mapping.findForward(forward);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

		String forward = Constants.FORWARD_COMMON_FAILURE;

		CategoryVO categoryVO = new CategoryVO();
		String categoryId = this.getParameter(request, "categoryId").trim();
		String categoryName = this.getParameter(request, "categoryName").trim();
		String categoryDesc = this.getParameter(request, "categoryDesc").trim();
		String sortId = this.getParameter(request, "sortId").trim();
		String delFlag = this.getParameter(request, "delFlag").trim();
		
		String actionType = "���¶���������Ϣ";
		boolean actionResult = true;
		String actionDesc = "���¶���������Ϣ�ɹ�";
		String actionTarget = categoryId;

		FileForm fileForm = (FileForm) form;
        
        // У���ļ���׺��
        if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		categoryVO.setCategoryId(categoryId);
		categoryVO.setCategoryName(categoryName);
		categoryVO.setCategoryDesc(categoryDesc);
		categoryVO.setSortId(sortId);
		categoryVO.setDelFlag(delFlag);

		try {
			Hashtable files = fileForm.getMultipartRequestHandler()
					.getFileElements();
			FormFile uploadFile = (FormFile) files.get("picture");
			String resourcePicFilePath = ConfigFactory.getSystemConfig()
					.getModuleConfig("music139")
					.getItemValue("DestAlbumFTPDir");
			String clearPicture = request.getParameter("clear_picture");

			// �õ���ǰ������Ϣ
			CategoryVO oldCategoryVO = CategoryBO
					.getInstance().queryCategoryVO(categoryId);

			categoryVO.setPicture(oldCategoryVO.getPicture());

			if (clearPicture != null && clearPicture.equals("1")) {
				categoryVO.setPicture("");
			} else {
				if (uploadFile.getFileName() != null
						&& !uploadFile.getFileName().equals("")) {
					String picture = UploadFileKeyResUtil.getInstance()
							.upLoadfileToResServer(uploadFile,
									resourcePicFilePath, "", categoryId, "");
					categoryVO.setPicture(picture);
				}
			}
			// ���ڱ�������ֻ���
			CategoryBO.getInstance().updateCategory(categoryVO);

		} catch (BOException e) {
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "���¶���������Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "���¶��������б����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "���¶������ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"../comic/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		return mapping.findForward(forward);
	}
	
	   public ActionForward del(ActionMapping mapping, ActionForm form,
               HttpServletRequest request,
               HttpServletResponse response) throws BOException
		{
		LOG.debug("doPerform()");
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		
		String actionType = "ɾ������������Ϣ";
		boolean actionResult = true;
		String actionDesc = "ɾ������������Ϣ�ɹ�";
		String actionTarget = categoryId;
		
		try
		{
		// �Ƿ�����ӻ���
		if (0 < CategoryBO.getInstance()
		                        .hasChild(categoryId))
		{
		  this.saveMessagesValue(request, "�����������ӻ��ܲ�����ɾ����");
		  return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// �Ƿ������Ʒ
		if (0 < CategoryBO.getInstance().hasReference(categoryId))
		{
		  this.saveMessagesValue(request, "������������Ʒ������ɾ����");
		  return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// ����ɾ��ָ������
		CategoryBO.getInstance().delCategory(categoryId);
		
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
        
		this.saveMessagesValue(request, "ɾ�����������б����");
		return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "ɾ���������ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
		               "../comic/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		return mapping.findForward(forward);
		}
	   
	   public ActionForward approval(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws BOException {
			String forward = Constants.FORWARD_COMMON_SUCCESS;
			String categoryId = this.getParameter(request, "categoryId").trim();
			CategoryVO categoryVO = null;
			String path = "";
			try {
				CategoryBO.getInstance().approvalCategory(request,categoryId);
			} catch (BOException e) {
				LOG.error(e);
				this.saveMessagesValue(request, "�ύ�����������ܳ���");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			this.saveMessages(request, "RESOURCE_ANIMALCATEGORY_RESULT_001");
			request.setAttribute(Constants.PARA_GOURL, "categoryTree.do?method=query&categoryId=" + categoryId);
			return mapping.findForward(forward);
		}

}
