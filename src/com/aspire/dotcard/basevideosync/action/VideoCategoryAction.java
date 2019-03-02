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
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(VideoCategoryAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		// 从请求中获取操作类型
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
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}
	}
	
	/**
     * 查询新视频货架信息
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
		
		String actionType = "查询新视频货架信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		String path = "";
		
		VideoCategoryVO videoCategoryVO;
		try
		{
			// 查询货架信息
			videoCategoryVO = VideoBO.getInstance().queryVideoCategoryVO(categoryId);
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 5);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "查询新视频货架信息出错");
			
			// 写操作日志
			actionResult = false;
			actionDesc = "查询新视频货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// 写操作日志
		actionDesc = "查询视频货架列表成功";
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
     * 删除新视频货架
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
		
		String actionType = "删除新视频货架";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		
		try
		{
			// 是否存在子货架
			if (0 < VideoBO.getInstance().hasChild(categoryId))
			{
				this.saveMessagesValue(request, "本货架下有子货架不可以删除！");
				
				// 写操作日志
				actionResult = false;
				actionDesc = "本货架下有子货架不可以删除！";
		        this.actionLog(request,
		                       actionType,
		                       actionTarget,
		                       actionResult,
		                       actionDesc);
		        
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}

			// 是否存在商品
			if (0 < VideoBO.getInstance().hasReference(categoryId))
			{
				this.saveMessagesValue(request, "本货架下有商品不可以删除！");
				
				// 写操作日志
				actionResult = false;
				actionDesc = "本货架下有商品不可以删除！";
		        this.actionLog(request,
		                       actionType,
		                       actionTarget,
		                       actionResult,
		                       actionDesc);
		        
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}

			// 用于删除指定货架
			VideoBO.getInstance().delVideoCategory(categoryId);

		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "删除新视频货架出错");
			
			// 写操作日志
			actionResult = false;
			actionDesc = "删除新视频货架出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "删除新视频货架成功");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
						"../newvideo/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// 写操作日志
		actionDesc = "删除新视频货架成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}

	/**
     * 跳转到新增新视频货架页面
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
     * 新增新视频货架
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
		String actionType = "新增新视频货架";
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
		
		// 扩展字段添加
		FileForm fileForm = (FileForm) form;
		
		// 校验文件后缀名
		if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
		{
			this.saveMessages(request, "文件后缀名出错！");
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
			// 用于新增新视频货架
			VideoBO.getInstance().addVideoCategory(videoCategoryVO);

		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "新增新视频货架出错");
			
			// 写操作日志
			actionResult = false;
			actionDesc = "新增新视频货架出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "新增新视频货架成功");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../newvideo/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// 写操作日志
		actionDesc = "新增新视频货架成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}

	/**
     * 跳转到修改新视频货架页面
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
		
		String actionType = "修改新视频货架前准备查询";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		String path = "";

		try
		{
			// 得到当前货架信息
			videoCategoryVO = VideoBO.getInstance().queryVideoCategoryVO(
					categoryId);
			
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 5);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "查询新视频货架列表出错");
			
			// 写操作日志
			actionResult = false;
			actionDesc = "修改新视频货架前准备查询出错";
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
		
		// 写操作日志
		actionDesc = "修改新视频货架前准备查询成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}

	/**
     * 修改新视频货架信息
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
		//将该货架变为编辑状态
		videoCategoryVO.setVideoStatus("0");
		
		String actionType = "修改新视频货架";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

		
		try
		{
			if(clearPicture != null && clearPicture.equals("1")){
				videoCategoryVO.setPicture("");
			}else{
				// 扩展字段添加
				FileForm fileForm = (FileForm) form;
				
				// 校验文件后缀名
				if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
				{
					this.saveMessages(request, "文件后缀名出错！");
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

			// 用于变更视频货架
			VideoBO.getInstance().updateVideoCategory(videoCategoryVO);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "修改新视频货架列表出错");

			// 写操作日志
			actionResult = false;
			actionDesc = "修改新视频货架出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "修改新视频货架成功");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../newvideo/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// 写操作日志
		actionDesc = "修改新视频货架成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}
	 /**
	 * 视频货架提交审批
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
			this.saveMessagesValue(request, "提交审批视频POMS货架出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "POMS货架管理提交审批成功");
		request.setAttribute(Constants.PARA_GOURL, "videoCategory.do?perType=query&categoryId=" + categoryId);
		return mapping.findForward(forward);
	}
}
