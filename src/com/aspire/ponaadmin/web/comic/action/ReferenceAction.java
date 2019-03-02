package com.aspire.ponaadmin.web.comic.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.VideoReferenceBO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.comic.bo.CategoryApprovalBO;
import com.aspire.ponaadmin.web.comic.bo.ReferenceBO;
import com.aspire.ponaadmin.web.comic.vo.ComicChapterVO;
import com.aspire.ponaadmin.web.comic.vo.ReferenceVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

public class ReferenceAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ReferenceAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        // 从请求中获取操作类型
        String method = this.getParameter(request, "method").trim();
        String perTypeFlag=this.getParameter(request, "perTypeFlag");
        boolean flag=false;
        if("export".equals(perTypeFlag)){
        	flag=false;
        }else{
        	flag=true;
        }
        if ("export".equals(method))
        {
            return doExport(mapping, form, request, response, flag);
        }  
        if ("query".equals(method))
        {
            return query(mapping, form, request, response);
        }
        else if ("setSort".equals(method))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryItem".equals(method))
        {
            return queryItem(mapping, form, request, response);
        }
        else if ("remove".equals(method))
        {
            return remove(mapping, form, request, response);
        }
        else if ("add".equals(method))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(method))
        {
            return importData(mapping, form, request, response);
        }
        else if ("queryChapter".equals(method))
        {
            return queryChapter(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }
    /**
     * 导出数据
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param isAll
     * @return
     * @throws BOException
     */
    public ActionForward doExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isAll) throws BOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("doExport in......");
			log.debug("excel导出.......");
		}
		
		String actionType = "导出动漫商品信息";
		boolean actionResult = true;
		String actionDesc = "导出动漫商品信息成功";
		String actionTarget = "";
		
		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "vo_comicreference_" + System.currentTimeMillis()
				+ ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);

			ReferenceBO.getInstance().exportComicReferenceData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "导出动漫商品信息出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
		}
		finally
		{
			try
			{
				if (wwb != null)
				{
					wwb.write();
					wwb.close();
				}
				if (os != null)
				{
					os.close();
				}
			}
			catch (Exception e)
			{}
		}
		response.setHeader("Content-disposition", "attachment;filename="
				+ excelName);
		response.setContentType("application/msexcel");
		try
		{
			FileInputStream fileInputStream = new FileInputStream(excelName);
			OutputStream out = response.getOutputStream();
			int i = 0;
			while ((i = fileInputStream.read()) != -1)
			{
				out.write(i);
			}
			fileInputStream.close();
			File file = new File(excelName);
			file.delete();
		}
		catch (FileNotFoundException e)
		{
			log.error(e);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			log.error(e);
			e.printStackTrace();
		}
		
		// 写操作日志
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		return null;
	}

    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
    	
    	String categoryId = request.getParameter("categoryId")==null?"":request.getParameter("categoryId");
    	String contentId = request.getParameter("contentId")==null?"":request.getParameter("contentId");
    	String contentName = request.getParameter("contentName")==null?"":request.getParameter("contentName");
    	String approvalStatus = request.getParameter("approvalStatus")==null?"":request.getParameter("approvalStatus");

    	String actionType = "查询动漫货架下商品信息";
		boolean actionResult = true;
		String actionDesc = "查询动漫货架下商品信息成功";
		String actionTarget = categoryId;
    	
        String forward = "query";
        ReferenceVO vo = new ReferenceVO();
        PageResult page = new PageResult(request);
        Map<String,Object> map = new HashMap<String,Object>();

        // 从请求中获取货架内码

        vo.setCategoryId(categoryId);
        vo.setContentId(contentId);
        vo.setContentName(contentName);
        vo.setVerify_status(approvalStatus);
        try
        {
        	map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
            ReferenceBO.getInstance().queryReferenceList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "查询动漫货架下商品信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "查询指定货架下的商品列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
       
        request.setAttribute("categoryContent", map);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("contentId", contentId);
        request.setAttribute("contentName", contentName);
        request.setAttribute("approvalStatus", approvalStatus);
        return mapping.findForward(forward);
    }


    public ActionForward remove(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = "remove";

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String[] id = request.getParameterValues("dealRef");
        
        String actionType = "删除动漫货架下商品信息";
		boolean actionResult = true;
		String actionDesc = "删除动漫货架下商品信息成功";
		String actionTarget = categoryId;
		
        try
        {
            ReferenceBO.getInstance().removeReference(categoryId, id);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "删除动漫货架下商品信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "删除商品成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "referenceTree.do?method=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }

    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "add";

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        
        String actionType = "新增动漫货架下商品";
		boolean actionResult = true;
		String actionDesc = "新增动漫货架下商品成功";
		String actionTarget = categoryId;

        try
        {
            String ret = ReferenceBO.getInstance().isHasReference(categoryId,
            		contentId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "原货架存在用户所选以下内容ID：" + ret);
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查看原货架是否存在用户将要新增时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            ReferenceBO.getInstance().addReference(categoryId, contentId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "新增动漫货架下商品出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于添加指定的商品至货架时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "添加新商品成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "referenceTree.do?method=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }


    public ActionForward queryItem(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "queryItem";

        // 从请求中获取货架内码
        String isFirst = this.getParameter(request, "isFirst").trim();
        String contentName = this.getParameter(request, "contentName").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        ReferenceVO vo = new ReferenceVO();
        PageResult page = new PageResult(request);
        
        // 如果是第一次。跳过
        if("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("method", forward);
            request.setAttribute("PageResult", page);
            request.setAttribute("contentName", contentName);
            request.setAttribute("contentId", contentId);
            
            return mapping.findForward(forward);
        }
        
        vo.setContentId(contentId);
        vo.setContentName(contentName);
        
       

        try
        {
            ReferenceBO.getInstance().queryList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询内容列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("method", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("contentName", contentName);
        request.setAttribute("contentId", contentId);
        
        return mapping.findForward(forward);
    }

    /**
     * 章节查询
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryChapter(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws BOException
 {
		String forward = "queryChapter";

		// 从请求中获取货架内码
	
		String contentId = this.getParameter(request, "contentId").trim();
		ComicChapterVO vo = new ComicChapterVO();
		PageResult page = new PageResult(request);

		
		vo.setContentId(contentId);
		
		try {
			ReferenceBO.getInstance().queryChapterList(page, vo);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "查询内容列表出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("method", forward);
		request.setAttribute("PageResult", page);
		
		request.setAttribute("contentId", contentId);

		return mapping.findForward(forward);
}

    
    public ActionForward setSort(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();
        
        String actionType = "修改动漫货架下商品排序信息";
		boolean actionResult = true;
		String actionDesc = "修改动漫货架下商品排序信息成功";
		String actionTarget = categoryId;
		
        try
        {
            // 新增指定货架上
            ReferenceBO.getInstance().setSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "修改动漫货架下商品排序信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于设置商品排序值时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "设置商品排序值成功");
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "referenceTree.do?method=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    public ActionForward importData(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "importData";
        String ret = "";
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addType = this.getParameter(request, "addType").trim();
        DataImportForm iForm=(DataImportForm)form;
        
        String actionType = "批量导入动漫货架下商品信息";
		boolean actionResult = true;
		String actionDesc = "批量导入动漫货架下商品信息成功";
		String actionTarget = categoryId;
        
		//这个在页面JS上判断 removed by aiyan 2012-12-24
//        // 校验文件后缀名
//        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
//        {
//            this.saveMessages(request, "文件后缀名出错！");
//            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
//        }
        
        FormFile dataFile = iForm.getDataFile();
        try
        {
        	if("ADD".equals(addType)){
        		ret =ReferenceBO.getInstance().importContentADD(dataFile, categoryId);
        	}else if("ALL".equals(addType)){
        		ret = ReferenceBO.getInstance().importContentALL(dataFile, categoryId);
        	}
        	ReferenceBO.getInstance().approvalCategoryGoods(categoryId);
            
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
	        
            this.saveMessagesValue(request, "用于文件批量导入商品上架出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "文件批量导入商品上架操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "referenceTree.do?method=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
}
