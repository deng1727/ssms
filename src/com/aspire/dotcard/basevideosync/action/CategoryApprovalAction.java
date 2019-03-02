package com.aspire.dotcard.basevideosync.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.bo.CategoryApprovalBO;
import com.aspire.dotcard.basevideosync.bo.VideoBO;
import com.aspire.dotcard.basevideosync.vo.VideoReferenceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
public class CategoryApprovalAction extends BaseAction {

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(CategoryApprovalAction.class);

	//热点内容的跟货架
	private String hotContentPCategoryId="105";
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

		// 从请求中获取操作类型
		String method = this.getParameter(request, "method").trim();

		if ("pass".equals(method)) {
			return pass(mapping, form, request, response);
		} else if ("nopass".equals(method)) {
			return nopass(mapping, form, request, response);
		} else if ("query".equals(method)) {
			return query(mapping, form, request, response);
		}else if("approval".equals(method)){
			return approval(mapping, form, request, response);
		} else if("accept".equals(method)){
			return accept(mapping, form, request, response);
		}else if("refuse".equals(method)){
			return refuse(mapping, form, request, response);
		}else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "query";
        VideoReferenceVO vo = new VideoReferenceVO();
        PageResult page = new PageResult(request);

        // 从请求中获取货架内码
        String refId = this.getParameter(request, "refId").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String programId = this.getParameter(request, "programId").trim();
        String programName = this.getParameter(request, "programName").trim();
        String cmsId = this.getParameter(request, "cmsId").trim();
        String subcateName = this.getParameter(request, "subcateName").trim();
        String keyName = this.getParameter(request, "keyName").trim();
        String approvalStatus = this.getParameter(request, "approvalStatus").trim();
        
        vo.setRefId(refId);
        vo.setCategoryId(categoryId);
        vo.setProgramId(programId);
        vo.setProgramName(programName);
        vo.setCmsId(cmsId);
        vo.setSubcateName(subcateName);
        vo.setKeyName(keyName);
        vo.setVerifyStatus(approvalStatus);
        
        String actionType = "查询新视频货架下商品";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        try
        {
       	    map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
       	    //如果是热点货架，则查询的是热点内容表，否则是查询节目详情表
       	    if(VideoBO.getInstance().isSubCategory(categoryId, hotContentPCategoryId)){
       	     VideoBO.getInstance().queryHotContentReferenceList(page, vo);
       	    }else{
       	     VideoBO.getInstance().queryVideoReferenceList(page, vo);
       	    }
           
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询新视频指定货架下的商品列表出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "查询新视频货架下商品出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("programId", programId);
        request.setAttribute("programName", programName);
        request.setAttribute("cmsId", cmsId);
        request.setAttribute("subcateName", subcateName);
        request.setAttribute("keyName", keyName);
        request.setAttribute("categoryContent", map);
        request.setAttribute("approvalStatus", approvalStatus);
		// 写操作日志
		actionDesc = "查询新视频货架下商品成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
	}
	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
    	String approval = this.getParameter(request, "approval").trim();
    	String[] dealRef = request.getParameterValues("dealRef");
		try {
			CategoryApprovalBO.getInstance().categoryGoodsApproval(request, categoryId,approval,dealRef);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "POMS商品提交审批出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_POMSCATEGORY_RESULT_004");
		request.setAttribute(Constants.PARA_GOURL,
				"../newVideo/queryReference.do?perType=query&categoryId=" + categoryId);
		return mapping.findForward(forward);
	}
	public ActionForward accept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "success";
		String categoryId = this.getParameter(request, "categoryId").trim();
    	String[] dealRef = request.getParameterValues("dealRef");
		try {
			CategoryApprovalBO.getInstance().accept(request, categoryId,dealRef);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "POMS商品审批发布出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_POMSCATEGORY_RESULT_005");
		return mapping.findForward(forward);
	}
	
	public ActionForward refuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "success";
		String categoryId = this.getParameter(request, "categoryId").trim();
    	String[] dealRef = request.getParameterValues("dealRef");
		try {
			CategoryApprovalBO.getInstance().refuse(request, categoryId,dealRef);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "POMS商品审批不通过出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_POMSCATEGORY_RESULT_006");
		return mapping.findForward(forward);
	}
	public ActionForward pass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		String operation = this.getParameter(request, "operation").trim();
		try {
			if (operation != null && !"".equals(operation)
					&& "1".equals(operation)) {
				CategoryApprovalBO.getInstance().categoryApprovalPass(request,
						categoryId);
			} else {
				CategoryApprovalBO.getInstance().goodsApprovalPass(request,
						categoryId);
			}
		} catch (BOException e) {
			LOG.error(e);
			if (operation != null && !"".equals(operation)
					&& "1".equals(operation)) {
				this.saveMessagesValue(request, "视频货架审批发布出错");
			} else {
				this.saveMessagesValue(request, "视频商品审批发布出错");
			}
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		if (operation != null && !"".equals(operation) && "1".equals(operation)) {
			this.saveMessages(request, "RESOURCE_VIDEOCATEGORY_RESULT_002");
		} else {
			this.saveMessages(request, "RESOURCE_VIDEOCATEGORY_RESULT_005");
		}
		request.setAttribute(Constants.PARA_GOURL,
				"categoryApprovalList.do?approvalStatus=2&operation="
						+ operation);
		return mapping.findForward(forward);
		
		
	}

	public ActionForward nopass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String categoryId = this.getParameter(request, "categoryId").trim();
		String operation = this.getParameter(request, "operation").trim();
		try {
			if (operation != null && !"".equals(operation)
					&& "1".equals(operation)) {
				CategoryApprovalBO.getInstance().categoryApprovalNoPass(
						request, categoryId);
			} else {
				CategoryApprovalBO.getInstance().goodsApprovalNoPass(request,
						categoryId);
			}
		} catch (BOException e) {
			LOG.error(e);
			if (operation != null && !"".equals(operation)
					&& "1".equals(operation)) {
				this.saveMessagesValue(request, "视频货架审批不通过出错");
			} else {
				this.saveMessagesValue(request, "视频商品审批不通过出错");
			}
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		if (operation != null && !"".equals(operation) && "1".equals(operation)) {
			this.saveMessages(request, "RESOURCE_VIDEOCATEGORY_RESULT_003");
		} else {
			this.saveMessages(request, "RESOURCE_VIDEOCATEGORY_RESULT_006");
		}
		request.setAttribute(Constants.PARA_GOURL,
				"categoryApprovalList.do?approvalStatus=2&operation="
						+ operation);
		return mapping.findForward(forward);
	}

}
