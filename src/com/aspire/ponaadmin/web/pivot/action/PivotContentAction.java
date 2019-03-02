/*
 * 文件名：PivotContentAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.pivot.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.pivot.bo.PivotContentBO;
import com.aspire.ponaadmin.web.pivot.vo.PivotContentVO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class PivotContentAction extends BaseAction
{
    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(PivotContentAction.class);

    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        // 从请求中获取操作类型
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }
        else if("detail".equals(perType))
        {
            return detail(mapping, form, request, response);
        }
        else if("downloadData".equals(perType))
        {
            return downloadData(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
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
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "query";
        PageResult page = new PageResult(request);
        
        String contentId = this.getParameter(request, "contentId").trim();
        String contentName = this.getParameter(request, "contentName").trim();
        String apCode = this.getParameter(request, "apCode").trim(); 
        String apName = this.getParameter(request, "apName").trim();
        
        PivotContentVO vo = new PivotContentVO();
        vo.setContentId(contentId);
        vo.setContentName(contentName);
        vo.setApCode(apCode);
        vo.setApName(apName);
        
        try
        {
            PivotContentBO.getInstance().queryPivotContentList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询重点内容列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("PageResult", page);
        request.setAttribute("contentId", contentId);
        request.setAttribute("contentName", contentName);
        request.setAttribute("apCode", apCode);
        request.setAttribute("apName", apName);

        return mapping.findForward(forward);
    }
    
    /**
     * 用于移除指定重点内容
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

        // 从请求中获取重点内容ID
        String[] contentId = request.getParameterValues("dealRef");

		String actionType = "用于移除指定重点内容";
		boolean actionResult = true;
		String actionDesc = "";
		StringBuffer sb = new StringBuffer();
		for(String temp : contentId)
		{
			sb.append(temp).append(".");
		}
		String actionTarget = sb.toString();
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("用于移除指定重点内容");
        }

        try
        {
            PivotContentBO.getInstance().removeContentID(contentId);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "用于移除指定重点内容出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            LOG.error(e);
            this.saveMessagesValue(request, "用于移除指定重点内容出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
		// 写操作日志
		actionDesc = "删除重点内容成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "删除重点内容成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "content.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * 添加重点内容列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "importData";
        String ret = "";

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action请求开始");
        }
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
        
		String actionType = "用于添加重点内容列表";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = dataFile.getFileName();
		
//      校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 

        try
        {
            // 添加重点内容列表
            ret = PivotContentBO.getInstance().importPivotContent(dataFile);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "用于添加重点内容列表出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            LOG.error(e);
            
            if (e.getErrorCode() == RepositoryBOCode.CATEGORY_DEVICE)
            {
                this.saveMessages(request, e.getMessage());
            }
            else
            {
                this.saveMessagesValue(request, "用于添加重点内容列表出错");
            }
            
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
		// 写操作日志
		actionDesc = "添加重点内容列表操作成功，" + ret;
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "添加重点内容列表操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "content.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * 查看重点内容详情
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "detail";
        
        String contentId = this.getParameter(request, "contentId").trim();
        Object content;
        
        try
        {
            String id = PivotContentBO.getInstance().queryContentId(contentId);
            // 找出内容信息
            Node node = Repository.getInstance().getNode(id);
            LOG.debug("the node type is :" + node.getType());
            content = Repository.getInstance().getNode(id,
                                                       node.getType());
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查看重点内容详情出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        request.setAttribute("content", content);
        return mapping.findForward(forward);
    }
    
    /**
     * 下载重点数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward downloadData(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String fileName = "";
        
        try
        {
            fileName = PivotContentBO.getInstance().downloadData();
            PivotContentBO.getInstance().downloadFile(fileName, response);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "下载重点数据出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        return null;
    }
}
