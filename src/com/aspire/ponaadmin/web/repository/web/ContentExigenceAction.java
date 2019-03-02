/*
 * 文件名：ContentExigenceAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.repository.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class ContentExigenceAction extends BaseAction
{
    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ContentExigenceAction.class);
    
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
        else if("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if("exe".equals(perType))
        {
            return exe(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }

    /**
     * 查询定义的将要紧急上线的内容列表
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
        PageResult page = new PageResult(request);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action请求动作");
        }

        try
        {
            ContentExigenceBO.getInstance().queryContentExigenceList(page);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询定义的将要紧急上线的内容列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }
    
    /**
     * 删除紧急上线的内容列表
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
                               HttpServletResponse response) throws BOException
    {
        String forward = "remove";

        // 从请求中获取货架内码
        String[] ids = request.getParameterValues("dealRef");

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action请求开始");
        }

        try
        {
            // 删除紧急上线内容
            ContentExigenceBO.getInstance().delContentExigence(ids);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于移除紧急上线内容出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "删除紧急上线内容成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryExigence.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * 添加紧急上线的内容列表
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
        String forward = "remove";
        String ret = "";

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action请求开始");
        }
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
//      校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 

        
        try
        {
            // 添加紧急上线内容
            ret = ContentExigenceBO.getInstance().importContentExigence(dataFile);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于添加紧急上线内容出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "添加紧急上线内容操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryExigence.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * 同步紧急上线内容
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward exe(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
		
		boolean isLock = DataSyncBO.getInstance().isLock();
		if (isLock)
		{
			LOG.info("不能重复执行内容同步业务！,hehe...");
			this.saveMessagesValue(request, "不能重复执行内容同步业务！");
			throw new BOException("不能重复执行内容同步业务！");
		}
		
		String forward = "exe";
		String ret = "";
		
		// 从请求中获取操作类型
		String exeContent = this.getParameter(request, "exeContent").trim();
		String exeDeviceType = this.getParameter(request, "exeDeviceType")
				.trim();
		try
		{
			if (DataSyncBO.getInstance().isLock())
			{
				this.saveMessagesValue(request,
						"同步紧急上线内容任务执行时发现，当前内容同步正在执行中！！！此次任务暂停！！！");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			else
			{
				// 同步紧急上线内容
				if (!ContentExigenceBO.getInstance().isLock())
				{
					ContentExigenceBO.getInstance().SyncContentExigence(
							exeContent, false, exeDeviceType);
				}
				else
				{
					this.saveMessagesValue(request,
							"同步紧急上线内容任务正在进行中，为避免重复，请稍后在执行！！！");
					return mapping
							.findForward(Constants.FORWARD_COMMON_FAILURE);
				}
			}
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "同步紧急上线内容出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		this.saveMessagesValue(request, "同步紧急上线内容操作成功，" + ret);
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"queryExigence.do?perType=query");
		return mapping.findForward(forward);
	}
}
