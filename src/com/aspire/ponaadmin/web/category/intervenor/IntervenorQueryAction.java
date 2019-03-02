/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor;

import java.util.List;

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
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * 人工干预容器处理类
 * 
 * @author x_wangml
 * 
 */
public class IntervenorQueryAction extends BaseAction
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(IntervenorQueryAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }

        // 从请求中获取操作类型
        String action = this.getParameter(request, "actionType").trim();

        if ("list".equals(action))
        {
            return list(mapping, form, request, response);
        }
        else if ("editView".equals(action))
        {
            return editView(mapping, form, request, response);
        }
        else if ("view".equals(action))
        {
            return view(mapping, form, request, response);
        }
        else if ("delete".equals(action))
        {
            return delete(mapping, form, request, response);
        }
        else if ("deleteContent".equals(action))
        {
            return deleteContent(mapping, form, request, response);
        }
        else if ("edit".equals(action))
        {
            return edit(mapping, form, request, response);
        }
        else if ("setSort".equals(action))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("add".equals(action))
        {
            return add(mapping, form, request, response);
        }
        else if ("addContent".equals(action))
        {
            return addContent(mapping, form, request, response);
        }
        else if ("queryContent".equals(action))
        {
            return queryContent(mapping, form, request, response);
        }
        else if ("importFile".equals(action))
        {
            return importFile(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }

    /**
     * 查询所有人工干预容器
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    private ActionForward list(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {

        String forward = "list";
        PageResult page = new PageResult(request);
        String where = this.getParameter(request, "where").trim();

        List list = null;
        try
        {
            list = IntervenorBO.getInstance().queryIntervenorVOList(where);
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "查询所有人工干预容器列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        page.setPageInfo(list);
        request.setAttribute("where", where);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }

    /**
     * 显示人工干预容器信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward editView(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
    {

        String forward = "editView";
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // 从请求中获取条件信息
        String id = this.getParameter(request, "id").trim();
        IntervenorVO vo = new IntervenorVO();

        try
        {
            vo = IntervenorBO.getInstance().queryInternorVOById(id);
            IntervenorBO.getInstance()
                        .queryGcontentListByIntervenorId(page, id);
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "显示人工干预容器信息出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        request.setAttribute("actionType", forward);
        request.setAttribute("id", id);
        request.setAttribute("intervenorVO", vo);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }

    private ActionForward view(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {

        String forward = "view";
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // 从请求中获取条件信息
        String id = this.getParameter(request, "id").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        IntervenorVO vo = new IntervenorVO();

        try
        {
            vo = IntervenorBO.getInstance().queryInternorVOById(id);
            IntervenorBO.getInstance()
                        .queryGcontentListByIntervenorId(page, id);
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "显示人工干预容器信息出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        request.setAttribute("actionType", forward);
        request.setAttribute("id", id);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("intervenorVO", vo);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }

    /**
     * 用于删除容器
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward delete(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取干预id
        String intervenorId = this.getParameter(request, "id").trim();

		String actionType = "通过干预id删除相应的干预容器";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = intervenorId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取干预id：" + intervenorId);

        }
        // 通过干预id删除相应的干预容器
        try
        {
            // 通过干预id删除相应的干预容器
            IntervenorBO.getInstance().deleteInternorVOById(intervenorId);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "通过干预id删除相应的干预容器出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "通过干预id删除相应的干预容器出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "通过干预id删除相应的干预容器成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "通过干预id删除相应的干预容器成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "intervenor_main_notes.jsp");
        return mapping.findForward(forward);
    }

    /**
     * 用于删除指定容器中的指定内容
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward deleteContent(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request,
                                        HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取干预id
        String intervenorId = this.getParameter(request, "id").trim();
        String contentId = this.getParameter(request, "contentId").trim();

		String actionType = "删除指定容器中的指定内容";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = intervenorId + "-" + contentId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取干预id：" + intervenorId + ", contentId="
                         + contentId);

        }

        try
        {
            // 删除指定容器中的指定内容
            IntervenorBO.getInstance().deleteGcontentById(intervenorId,
                                                          contentId);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "删除指定容器中的指定内容出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "删除指定容器中的指定内容出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "删除指定容器中的指定内容成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/web/intervenor/intervenorView.do?actionType=editView&id="
                              + intervenorId);
        return actionForward;
    }

    /**
     * 修改容器信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward edit(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取容器信息
        String intervenorId = this.getParameter(request, "id").trim();
        String name = this.getParameter(request, "name").trim();
        String oldName = this.getParameter(request, "oldName").trim();
        String startSortId = this.getParameter(request, "startSortId").trim();
        String endSortId = this.getParameter(request, "endSortId").trim();
        String startDate = this.getParameter(request, "startDate")
                               .trim()
                               .replaceAll("-", "");
        String endDate = this.getParameter(request, "endDate")
                             .trim()
                             .replaceAll("-", "");
        
		String actionType = "修改人工干预容器信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = intervenorId;

        IntervenorVO vo = new IntervenorVO();
        vo.setId(Integer.parseInt(intervenorId));
        vo.setName(name);
        vo.setStartSortId(Integer.parseInt(startSortId));
        vo.setEndSortId(Integer.parseInt(endSortId));
        vo.setStartDate(startDate);
        vo.setEndDate(endDate);

        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取信息：" + vo.toString());

        }

        // 如果修改了容器名称时
        if (!name.equals(oldName))
        {
            try
            {
                // 查看容器名称是否存在
                if (IntervenorBO.getInstance().hasInternorName(name))
                {
        			// 写操作日志
        			actionResult = false;
        			actionDesc = "修改人工干预容器信息出错，已存在此容器名称!";
        	        this.actionLog(request,
        	                       actionType,
        	                       actionTarget,
        	                       actionResult,
        	                       actionDesc);
        	        
                    this.saveMessagesValue(request,
                                           "修改人工干预容器信息出错，已存在此容器名称。请重新输入！");
                    return mapping.findForward(forward);
                }
            }
            catch (BOException e)
            {
    			// 写操作日志
    			actionResult = false;
    			actionDesc = "修改容器信息出错!";
    	        this.actionLog(request,
    	                       actionType,
    	                       actionTarget,
    	                       actionResult,
    	                       actionDesc);
    	        
                logger.error(e);
                this.saveMessagesValue(request, "修改容器信息出错");
                return mapping.findForward(forward);
            }
        }

        // 修改容器信息
        try
        {
            // 通过干预id删除相应的干预容器
            IntervenorBO.getInstance().editInternorVO(vo);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "修改容器信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "修改容器信息出错");
            return mapping.findForward(forward);
        }

		// 写操作日志
		actionDesc = "修改容器信息成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "修改容器信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "intervenorView.do?actionType=editView&isReload=yes&id="
                                             + intervenorId);
        return mapping.findForward(forward);
    }

    /**
     * 用于设置容器内内容的排序
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward setSort(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取干预id
        String intervenorId = this.getParameter(request, "id").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        
		String actionType = "设置容器内内容的排序";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = intervenorId + "-" + contentId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取干预id：" + intervenorId + ", contentId="
                         + contentId);
        }

        String[] sortValue = contentId.split(";");

        try
        {
            // 设置容器内内容的排序
            IntervenorBO.getInstance().editContentSort(intervenorId, sortValue);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "设置容器内内容的排序时出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
        	
            logger.error(e);
            this.saveMessagesValue(request, "设置容器内内容的排序时出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "设置容器内内容的排序成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/web/intervenor/intervenorView.do?actionType=editView&id="
                              + intervenorId);
        return actionForward;
    }

    /**
     * 新增人工干预容器
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward add(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取容器信息
        String name = this.getParameter(request, "name").trim();
        String startSortId = this.getParameter(request, "startSortId").trim();
        String endSortId = this.getParameter(request, "endSortId").trim();
        String startDate = this.getParameter(request, "startDate")
                               .trim()
                               .replaceAll("-", "");
        String endDate = this.getParameter(request, "endDate")
                             .trim()
                             .replaceAll("-", "");
        
        String actionType = "新增人工干预容器";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = name;
		
        int id = -1;

        IntervenorVO vo = new IntervenorVO();
        vo.setName(name);
        vo.setStartSortId(Integer.parseInt(startSortId));
        vo.setEndSortId(Integer.parseInt(endSortId));
        vo.setStartDate(startDate);
        vo.setEndDate(endDate);

        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取信息：" + vo.toString());
        }

        // 查看容器名称是否存在
        try
        {
            if (IntervenorBO.getInstance().hasInternorName(name))
            {
            	// 写操作日志
    			actionResult = false;
    			actionDesc = "新增人工干预容器信息出错，已存在此容器名称!";
    	        this.actionLog(request,
    	                       actionType,
    	                       actionTarget,
    	                       actionResult,
    	                       actionDesc);
    	        
                this.saveMessagesValue(request, "新增人工干预容器信息出错，已存在此容器名称。请重新输入！");
                return mapping.findForward(forward);
            }
        }
        catch (BOException e)
        {
        	// 写操作日志
			actionResult = false;
			actionDesc = "新增人工干预容器信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "新增人工干预容器信息出错");
            return mapping.findForward(forward);
        }

        // 修改容器信息
        try
        {
            // 得到id
            id = IntervenorBO.getInstance().getInternorId();
            vo.setId(id);
            // 通过干预id删除相应的干预容器
            IntervenorBO.getInstance().addInternorVO(vo);
        }
        catch (BOException e)
        {
        	// 写操作日志
			actionResult = false;
			actionDesc = "新增人工干预容器信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "新增人工干预容器信息出错");
            return mapping.findForward(forward);
        }
        // 写操作日志
		actionDesc = "新增人工干预容器信息成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "新增人工干预容器信息成功！容器编码为" + id);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "intervenorView.do?actionType=editView&isReload=yes&id="
                                             + id);
        return mapping.findForward(forward);
    }

    /**
     * 新增内容至人工干预容器
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward addContent(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取容器信息
        String id = this.getParameter(request, "id").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        
        String actionType = "新增内容至人工干预容器";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id + "-" + contentId;

        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取信息：id=" + id + ", contentId=" + contentId);

        }

        String[] contentIds = contentId.split(",");

        for (int i = 0; i < contentIds.length; i++)
        {
            String temp = contentIds[i];

            try
            {
                IntervenorGcontentVO vo = IntervenorBO.getInstance()
                                                      .getContentVO(id, temp);

                if (temp.equals(vo.getId()) && id.equals(vo.getIntervenorId()))
                {
        			// 写操作日志
        			actionResult = false;
        			actionDesc = "新增内容至人工干预容器出错，容器中已存在内容!";
        	        this.actionLog(request,
        	                       actionType,
        	                       actionTarget,
        	                       actionResult,
        	                       actionDesc);
        	        
                    this.saveMessagesValue(request, "新增内容至人工干预容器出错，容器中已存在内容 "
                                                    + temp);
                    return mapping.findForward(forward);
                }
            }
            catch (BOException e)
            {
    			// 写操作日志
    			actionResult = false;
    			actionDesc = "查询内容是否存在于人工干预容器时出错!";
    	        this.actionLog(request,
    	                       actionType,
    	                       actionTarget,
    	                       actionResult,
    	                       actionDesc);
    	        
                logger.error(e);
                this.saveMessagesValue(request, "查询内容是否存在于人工干预容器时出错!");
                return mapping.findForward(forward);
            }
        }

        try
        {
            // 新增内容至人工干预容器
            IntervenorBO.getInstance()
                        .addGcontentToIntervenorId(id, contentIds);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "新增内容至人工干预容器出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "新增内容至人工干预容器出错");
            return mapping.findForward(forward);
        }
        // 写操作日志
		actionDesc = "新增内容至人工干预容器信息成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "新增内容至人工干预容器信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "intervenorView.do?actionType=editView&id=" + id);
        return mapping.findForward(forward);
    }

    /**
     * 用于查询内容信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward queryContent(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response)
    {

        String forward = "queryContent";

        // 从请求中获取容器信息
        String id = this.getParameter(request, "id").trim();
        String name = this.getParameter(request, "name").trim();
        String spName = this.getParameter(request, "spName").trim();
        String keywordsDesc = this.getParameter(request, "keywordsDesc").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        String contentTag = this.getParameter(request, "contentTag").trim();

        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取信息：id=" + id + ", name=" + name);

        }

        try
        {
            // 用于查询内容信息
            IntervenorBO.getInstance().queryGcontentList(page,
                                                         id,
                                                         name,
                                                         spName,
                                                         keywordsDesc,
                                                         contentId,
                                                         contentTag);
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "查询内容信息出错");
            return mapping.findForward(forward);
        }

        request.setAttribute("actionType", forward);
        request.setAttribute("id", id);
        request.setAttribute("name", name);
        request.setAttribute("spName", spName);
        request.setAttribute("keywordsDesc", keywordsDesc);
        request.setAttribute("contentId", contentId);
        request.setAttribute("contentTag", contentTag);
        request.setAttribute("PageResult", page);
        request.setAttribute("isQuery", "true");
        request.setAttribute("size", String.valueOf(page.getPageInfo().size()));
        return mapping.findForward(forward);
    }

    /**
     * 导入容器内容数据文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward importFile(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取容器id
        String id = this.getParameter(request, "id").trim();

        // 得到导入内容文件信息
        DataImportForm iForm = ( DataImportForm ) form;
        
        FormFile dataFile = iForm.getDataFile();

		String actionType = "导入内容文件数据至指定容器中";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = dataFile.getFileName();
		
        // 校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取容器id信息：id=" + id);

        }

        try
        {
            // 容器内容文件导入
            IntervenorBO.getInstance().importFileById(id, dataFile);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "导入内容文件数据至指定容器中时出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "导入内容文件数据至指定容器中时出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "导入容器内容数据文件信息成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "导入容器内容数据文件信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "intervenorView.do?actionType=editView&id=" + id);
        return mapping.findForward(forward);
    }
}
