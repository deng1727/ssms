package com.aspire.dotcard.baseVideo.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.VideoBO;
import com.aspire.dotcard.baseVideo.vo.VideoCategoryVO;
import com.aspire.dotcard.baseread.bo.ReadCategoryBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

public class VideoAction extends BaseAction
{
	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(VideoAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");

		// �������л�ȡ��������
		String perType = this.getParameter(request, "perType").trim();

		if ("tree".equals(perType))
		{
			return tree(mapping, form, request, response);
		}
		else if ("query".equals(perType))
		{
			return query(mapping, form, request, response);
		}
		else if ("del".equals(perType))
		{
			return del(mapping, form, request, response);
		}
		else if ("add".equals(perType))
		{
			return add(mapping, form, request, response);
		}
		else if ("save".equals(perType))
		{
			return save(mapping, form, request, response);
		}
		else if ("mod".equals(perType))
		{
			return mod(mapping, form, request, response);
		}
		else if ("update".equals(perType))
		{
			return update(mapping, form, request, response);
		}
		else if ("approval".equals(perType))
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
			this.saveMessagesValue(request, "�ύ������Ƶ���ܳ���");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_VIDEOCATEGORY_RESULT_001");
		request.setAttribute(Constants.PARA_GOURL, "categoryTree.do?perType=query&categoryId=" + categoryId);
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
	public ActionForward tree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "tree";
		String actionType = "��ѯ��Ƶ������";
		String actionTarget = "VideoTree";
		boolean actionResult = true;
		String actionDesc = "";

		List videoList = null;
		try
		{
			videoList = VideoBO.getInstance().queryVideoCategoryList();
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ��Ƶ����������");
			// д������־
			actionResult = false;
			actionDesc = "��ѯ��Ƶ����������";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		actionDesc = "��ѯ��Ƶ�������ɹ�";
		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
		request.setAttribute("videoList", videoList);

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
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "query";
		String id = this.getParameter(request, "categoryId").trim();
		String operation = this.getParameter(request, "operation");
		
		String actionType = "��ѯ��Ƶ�����б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		String path = "";
		
		VideoCategoryVO videoCategoryVO;
		List keyBaseList = null;
		try
		{
			// ��ѯ������Ϣ
			videoCategoryVO = VideoBO.getInstance().queryVideoCategoryVO(id);
			keyBaseList = VideoBO.getInstance().queryVideoCategoryKeyBaseList(
					id);
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(id, 2);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ��Ƶ�����б����");
			
			// д������־
			actionResult = false;
			actionDesc = "��ѯ��Ƶ�����б����";
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
		request.setAttribute("keyBaseList", keyBaseList);
		request.setAttribute("categoryId", id);
		request.setAttribute("operation", operation);
		request.setAttribute("videoCategoryVO", videoCategoryVO);

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
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String id = this.getParameter(request, "categoryId").trim();
		
		String actionType = "ɾ����Ƶ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		
		try
		{
			// �Ƿ�����ӻ���
			if (0 < VideoBO.getInstance().hasChild(id))
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
			if (0 < VideoBO.getInstance().hasVideo(id))
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
			/*VideoBO.getInstance().delVideoCategory(id);

			VideoBO.getInstance().delVideoCategoryKeyResource(id);*/
			
			VideoBO.getInstance().updateVideoCategoryDelStatus(id);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "ɾ����Ƶ���ܳ���");
			
			// д������־
			actionResult = false;
			actionDesc = "ɾ����Ƶ���ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "ɾ����Ƶ���ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../video/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// д������־
		actionDesc = "ɾ����Ƶ���ܳɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
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
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "add";

		String pCategoryId = this.getParameter(request, "parentCategoryId")
				.trim();

		String actionType = "������Ƶ����ǰ��ѯ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = pCategoryId;
		
		VideoCategoryVO videoCategoryVO;
		List keyBaseList = null;

		try
		{
			// ��ѯ������Ϣ
			videoCategoryVO = VideoBO.getInstance().queryVideoCategoryVO(
					pCategoryId);
			keyBaseList = VideoBO.getInstance()
					.queryVideoCategoryKeyBaseList(null);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ��Ƶ�����б����");
			
			// д������־
			actionResult = false;
			actionDesc = "������Ƶ����ǰ��ѯ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		videoCategoryVO.setParentId(pCategoryId);

		request.setAttribute("keyBaseList", keyBaseList);
		request.setAttribute("videoCategoryVO", videoCategoryVO);
		request.setAttribute("pCategoryId", pCategoryId);

		// д������־
		actionDesc = "������Ƶ����ǰ��ѯ��Ϣ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
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
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = Constants.FORWARD_COMMON_FAILURE;

        FileForm fileForm = ( FileForm ) form;

        // У���ļ���׺��
        if (!fileForm.checkFileNameExtension(new String[] { "png", "jpg", "gif" }))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		VideoCategoryVO videoCategoryVO = new VideoCategoryVO();
		String pCategoryId = this.getParameter(request, "pCategoryId").trim();
		String pBaseCategoryId = this.getParameter(request, "pBaseCategoryId")
				.trim();
		String baseName = this.getParameter(request, "name").trim();
		String desc = this.getParameter(request, "desc").trim();
		String sortId = this.getParameter(request, "sortId").trim();
		String baseType = this.getParameter(request, "baseType").trim();
		String isShow = this.getParameter(request, "isShow").trim();
		String cid = VideoBO.getInstance().getVideoCategoryId();

		List keyBaseList = VideoBO.getInstance()
				.queryVideoCategoryKeyBaseList(cid);

		videoCategoryVO.setId(cid);
		videoCategoryVO.setParentId(pCategoryId);
		videoCategoryVO.setBaseParentId(pBaseCategoryId);
		// videoCategoryVO.setBaseId("0");remvoe by aiyan ��m��ͷ1000���ϵ��ַ������С�
		videoCategoryVO.setBaseName(baseName);
		videoCategoryVO.setDesc(desc);
		
		if("0".equals(sortId))
		{
			videoCategoryVO.setSortId(SEQCategoryUtil.getInstance()
					.getSEQByCategoryType(3));
		}
		else
		{
			videoCategoryVO.setSortId(Integer.parseInt(sortId));
		}
		
		videoCategoryVO.setBaseType(baseType);
		videoCategoryVO.setIsShow(Integer.parseInt(isShow));
		
		 
        String actionType = "������Ƶ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = cid;

		try
		{
			// �������������ֻ���
			VideoBO.getInstance().saveVideoCategory(videoCategoryVO);

			if (keyBaseList != null && keyBaseList.size() > 0)
			{
				this.saveVideoCategoryKeyResource(keyBaseList, fileForm, cid,
						request);
			}
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "������Ƶ�����б����");
			
			// д������־
			actionResult = false;
			actionDesc = "������Ƶ���ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "������Ƶ���ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../video/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// д������־
		actionDesc = "������Ƶ���ܳɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
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
		String categoryId = this.getParameter(request, "categoryId").trim();
		String parentCategoryId = this.getParameter(request, "parentId").trim();
		VideoCategoryVO videoCategoryVO;
		List keyBaseList = null;
		
		String actionType = "�޸���Ƶ����ǰ׼����ѯ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		String path = "";

		try
		{
			// �õ���ǰ������Ϣ
			videoCategoryVO = VideoBO.getInstance().queryVideoCategoryVO(
					categoryId);
			
			keyBaseList = VideoBO.getInstance()
            .queryVideoCategoryKeyBaseList(categoryId);
			path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 2);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ��Ƶ�����б����");
			
			// д������־
			actionResult = false;
			actionDesc = "�޸���Ƶ����ǰ׼����ѯ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("path", path);
		request.setAttribute("keyBaseList", keyBaseList);
		request.setAttribute("pCategoryId", parentCategoryId);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("videoCategoryVO", videoCategoryVO);
		
		// д������־
		actionDesc = "�޸���Ƶ����ǰ׼����ѯ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
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
		
        FileForm fileForm = ( FileForm ) form;

        // У���ļ���׺��
        if (!fileForm.checkFileNameExtension(new String[] { "png", "jpg", "gif" }))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

		VideoCategoryVO videoCategoryVO = new VideoCategoryVO();
		String cid = this.getParameter(request, "categoryId").trim();
		String parentId = this.getParameter(request, "pCategoryId").trim();
		String baseParentId = this.getParameter(request, "pBaseCategoryId")
				.trim();
		String baseType = this.getParameter(request, "baseType").trim();
		String baseName = this.getParameter(request, "baseName").trim();
		String desc = this.getParameter(request, "desc").trim();
		String sortId = this.getParameter(request, "sortId").trim();
		String isShow = this.getParameter(request, "isShow").trim();

		List keyBaseList = VideoBO.getInstance()
        .queryVideoCategoryKeyBaseList(cid);
		
		videoCategoryVO.setId(cid);
		videoCategoryVO.setParentId(parentId);
		videoCategoryVO.setBaseParentId(baseParentId);
		videoCategoryVO.setBaseId("0");
		videoCategoryVO.setBaseName(baseName);
		videoCategoryVO.setDesc(desc);
		videoCategoryVO.setSortId(Integer.parseInt(sortId));
		videoCategoryVO.setBaseType(baseType);
		videoCategoryVO.setIsShow(Integer.parseInt(isShow));
		
		String actionType = "�޸���Ƶ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = cid;

		try
		{
			// ���ڱ����Ƶ����
			VideoBO.getInstance().updateVideoCategory(videoCategoryVO);
			
			if (keyBaseList != null && keyBaseList.size() > 0)
            {
                this.saveVideoCategoryKeyResource(keyBaseList,
                                                 fileForm,
                                                 cid,
                                                 request);
            }
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "������Ƶ�����б����");

			// д������־
			actionResult = false;
			actionDesc = "�޸���Ƶ���ܳ���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		this.saveMessagesValue(request, "������Ƶ���ܳɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request
				.setAttribute(Constants.PARA_GOURL,
						"../video/category_main.jsp");
		request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		
		// д������־
		actionDesc = "�޸���Ƶ���ܳɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return mapping.findForward(forward);
	}
	
	/**
     * 
     * @desc ������չ�ֶ�
     * @author dongke Aug 8, 2011
     * @throws BOException
     */
    public void saveVideoCategoryKeyResource(List keyBaseList,
                                            FileForm fileForm, String cid,
                                            HttpServletRequest request)
                    throws BOException
    {

        // ��Դ��������ģ��·��
        String resServerPath = ConfigFactory.getSystemConfig()
                                            .getModuleConfig("BaseVideoFileConfig")
                                            .getItemValue("resServerPath");
        List delResourcelist = new ArrayList();
        this.saveKeyResource(keyBaseList,
                             delResourcelist,
                             fileForm,
                             cid,
                             request,
                             resServerPath,
                             "category");
        ReadCategoryBO.getInstance().saveKeyResource(keyBaseList);
        KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
    
    
}
