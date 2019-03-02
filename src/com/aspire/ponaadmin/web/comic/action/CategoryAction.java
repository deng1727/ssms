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
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(CategoryAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

		// 从请求中获取操作类型
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
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "query";
		String categoryId = this.getParameter(request, "categoryId").trim();
		String operation = this.getParameter(request, "operation");
		
		String actionType = "查询动漫货架信息";
		boolean actionResult = true;
		String actionDesc = "查询动漫货架信息成功";
		String actionTarget = categoryId;
		String path = "";
		
		
		CategoryVO categoryVO = null;
		try {
			// 查询货架信息
			categoryVO = CategoryBO.getInstance().queryCategoryVO(categoryId);
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 4);
		} catch (BOException e) {
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "查询动漫货架详情出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "查询动漫货架详情出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 写操作日志
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

		String actionType = "新增动漫货架";
		boolean actionResult = true;
		String actionDesc = "新增动漫货架成功";
		String actionTarget = cid;
		
		// 扩展字段添加
		FileForm fileForm = (FileForm) form;

        // 校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
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
			// 用于新增新音乐货架
			CategoryBO.getInstance().saveCategory(categoryVO);

		} catch (BOException e) {
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "新增动漫货架出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "新增动漫货架出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "新增动漫货架成功");
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
			// 得到当前货架信息
			categoryVO = CategoryBO.getInstance().queryCategoryVO(categoryId);
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 4);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "查询动漫货架列表出错");
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
		
		String actionType = "更新动漫货架信息";
		boolean actionResult = true;
		String actionDesc = "更新动漫货架信息成功";
		String actionTarget = categoryId;

		FileForm fileForm = (FileForm) form;
        
        // 校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
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

			// 得到当前货架信息
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
			// 用于变更新音乐货架
			CategoryBO.getInstance().updateCategory(categoryVO);

		} catch (BOException e) {
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "更新动漫货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "更新动漫货架列表出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "更新动漫货架成功");
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
		
		String actionType = "删除动漫货架信息";
		boolean actionResult = true;
		String actionDesc = "删除动漫货架信息成功";
		String actionTarget = categoryId;
		
		try
		{
		// 是否存在子货架
		if (0 < CategoryBO.getInstance()
		                        .hasChild(categoryId))
		{
		  this.saveMessagesValue(request, "本货架下有子货架不可以删除！");
		  return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 是否存在商品
		if (0 < CategoryBO.getInstance().hasReference(categoryId))
		{
		  this.saveMessagesValue(request, "本货架下有商品不可以删除！");
		  return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 用于删除指定货架
		CategoryBO.getInstance().delCategory(categoryId);
		
		}
		catch (BOException e)
		{
		LOG.error(e);
		
        // 写操作日志
		actionResult = false;
		actionDesc = "删除阅读货架下商品信息出错";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "删除动漫货架列表出错");
		return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "删除动漫货架成功");
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
				this.saveMessagesValue(request, "提交审批动漫货架出错");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			this.saveMessages(request, "RESOURCE_ANIMALCATEGORY_RESULT_001");
			request.setAttribute(Constants.PARA_GOURL, "categoryTree.do?method=query&categoryId=" + categoryId);
			return mapping.findForward(forward);
		}

}
