package com.aspire.dotcard.wpinfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.wpinfo.bo.AppInfoBO;
import com.aspire.dotcard.wpinfo.vo.AppInfoReferenceVO;
import com.aspire.dotcard.wpinfo.vo.AppInfoVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

public class AppInfoReferenceAction extends BaseAction{

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(AppInfoReferenceAction.class);
	
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

        // 从请求中获取操作类型
        String perType = this.getParameter(request, "perType").trim();
        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if ("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if ("setSort".equals(perType))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryProgram".equals(perType))
        {
            return queryProgram(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }

        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
	}
	
	/**
     * 查询wp汇聚货架下的商品列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "query";
        AppInfoReferenceVO vo = new AppInfoReferenceVO();
        PageResult page = new PageResult(request);

        // 从请求中获取货架内码
        String refId = this.getParameter(request, "refId").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String appId = this.getParameter(request, "appId").trim();
        String appName = this.getParameter(request, "appName").trim();

        vo.setRefId(refId);
        vo.setCategoryId(categoryId);
        vo.setAppId(appId);
        vo.setAppName(appName);

        
        String actionType = "查询wp汇聚货架下商品";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            AppInfoBO.getInstance().queryAppInfoReferenceList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询wp汇聚指定货架下的商品列表出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "查询wp汇聚货架下商品出错";
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
        request.setAttribute("appId", appId);
        request.setAttribute("appName", appName);
        
		// 写操作日志
		actionDesc = "查询wp汇聚货架下商品成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 删除wp汇聚架下的商品
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = "remove";

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String[] refId = request.getParameterValues("dealRef");
        
        String actionType = "删除wp汇聚货架下商品";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            AppInfoBO.getInstance().delAppInfoReferences(categoryId, refId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于移除指定货架下指定的商品出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "删除wp汇聚货架下商品出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "删除wp汇聚商品成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "appInfoReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// 写操作日志
		actionDesc = "删除wp汇聚货架下商品成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 设置wp汇聚货架下wp汇聚商品排序值
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward setSort(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                    throws BOException
    {
        String forward = "setSort";

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();
        
        String actionType = "修改wp汇聚货架下商品排序";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 设置wp汇聚货架下wp汇聚商品排序值
            AppInfoBO.getInstance().setAppInfoReferenceSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于设置wp汇聚货架下音乐wp汇聚排序值时出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "修改wp汇聚货架下商品排序出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "设置wp汇聚商品排序值成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "appInfoReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// 写操作日志
		actionDesc = "修改wp汇聚货架下商品排序成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 查询wp汇聚节目列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryProgram(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        String forward = "queryProgram";
        
        
        String actionType = "用于新增wp汇聚货架下商品的wp汇聚节目查询";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";

        // 从请求中获取货架内码
        String isFirst = this.getParameter(request, "isFirst").trim();
        String appName = this.getParameter(request, "appName").trim();
        String appId = this.getParameter(request, "appId").trim();
        AppInfoVO vo = new AppInfoVO();
        PageResult page = new PageResult(request);

        // 如果是第一次。跳过
        if ("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("PageResult", page);
            request.setAttribute("appName", appName);
            request.setAttribute("appId", appId);

            return mapping.findForward(forward);
        }

        vo.setAppId(appId);
        vo.setAppName(appName);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取wp汇聚条件：appName=" + appName + ", programId=" + appId);
        }

        try
        {
            // 查询wp汇聚节目列表。用以上架至新货架上
            AppInfoBO.getInstance().queryAppInfoList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询wp汇聚列表出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "用于新增wp汇聚货架下商品的wp汇聚查询出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("appName", appName);
        request.setAttribute("appId", appId);

		// 写操作日志
		actionDesc = "用于新增wp汇聚货架下商品的wp汇聚查询成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 添加wp汇聚节目至货架中
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "add";

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String appInfoId = this.getParameter(request, "addAppInfoId").trim();
        
        String actionType = "新增wp汇聚货架下商品";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 查看原货架是否存在用户将要新增的商品信息
            String ret = AppInfoBO.getInstance().isHasReferences(categoryId, appInfoId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "原货架存在用户所选以下wp汇聚节目：" + ret);
                
    			// 写操作日志
    			actionResult = false;
    			actionDesc = "新增wp汇聚货架下商品出错!商品已存在";
    	        this.actionLog(request,
    	                       actionType,
    	                       actionTarget,
    	                       actionResult,
    	                       actionDesc);
    	        
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查看原货架是否存在用户将要新增时出错");
            
            // 写操作日志
			actionResult = false;
			actionDesc = "新增wp汇聚货架下商品出错!查看原货架是否存在用户将要新增时出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // 新增指定wp汇聚节目商品至指定货架上
            AppInfoBO.getInstance().addAppInfoReferences(categoryId, appInfoId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于添加指定的wp汇聚节目至货架时出错");
            
            // 写操作日志
			actionResult = false;
			actionDesc = "新增wp汇聚货架下商品出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "添加wp汇聚货架下商品成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "appInfoReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// 写操作日志
		actionDesc = "新增wp汇聚货架下商品成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

}
