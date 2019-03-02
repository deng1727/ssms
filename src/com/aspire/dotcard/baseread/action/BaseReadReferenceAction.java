/*
 * 文件名：BookRefAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.dotcard.baseread.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.bo.CategoryApprovalBO;
import com.aspire.dotcard.baseread.bo.ReadReferenceBO;
import com.aspire.dotcard.baseread.vo.ReadReferenceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicRefBO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BaseReadReferenceAction extends BaseAction
{
    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BaseReadReferenceAction.class);
    
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
        String perTypeFlag=this.getParameter(request, "perTypeFlag");
        boolean flag=false;
        if("export".equals(perTypeFlag)){
        	flag=false;
        }else{
        	flag=true;
        }
        if ("export".equals(perType))
        {
            return doExport(mapping, form, request, response, flag);
        }  
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
        else if ("queryRead".equals(perType))
        {
            return queryRead(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }else if ("showRead".equals(perType))
        {
            return showRead(mapping, form, request, response);
        }else if ("saveRead".equals(perType))
        {
            return saveRead(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }

    /**
     * 得到图书指定货架下的商品列表
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
        ReadReferenceVO vo = new ReadReferenceVO();
        PageResult page = new PageResult(request);

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String bookId = this.getParameter(request, "bookId").trim();
        String bookName = this.getParameter(request, "bookName").trim();
        String authorName = this.getParameter(request, "authorName").trim();
        String typeName = this.getParameter(request, "typeName").trim();
        String chargeType = this.getParameter(request, "chargeType").trim();
        String approvalStatus = this.getParameter(request, "approvalStatus");

        String actionType = "查询阅读货架下商品信息";
		boolean actionResult = true;
		String actionDesc = "查询阅读货架下商品信息成功";
		String actionTarget = categoryId;
		
        vo.setCId(categoryId);
        vo.setBookId(bookId);
        vo.setBookName(bookName);
        vo.setAuthorName(authorName);
        vo.setTypeName(typeName);
        vo.setChargeType(chargeType);
        vo.setVerify_status(approvalStatus);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }
        Map<String,Object> map = new HashMap<String,Object>();

        try
        {
        	map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
            ReadReferenceBO.getInstance().queryReadRefList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "查询阅读货架下商品信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "查询指定阅读货架下的商品列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("bookId", bookId);
        request.setAttribute("bookName", bookName);
        request.setAttribute("authorName", authorName);
        request.setAttribute("typeName", typeName);
        request.setAttribute("chargeType", chargeType);
        request.setAttribute("categoryContent", map);
        request.setAttribute("approvalStatus", approvalStatus);
        return mapping.findForward(forward);
    }
    
    /**
     * 用于移除指定货架下指定的图书
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
        String[] bookId = request.getParameterValues("dealRef");

        String actionType = "删除阅读货架下商品信息";
		boolean actionResult = true;
		String actionDesc = "删除阅读货架下商品信息成功";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            ReadReferenceBO.getInstance().removeReadRefs(categoryId, bookId);
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
	        
            this.saveMessagesValue(request, "用于移除指定货架下指定的图书出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "删除图书成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * 用于设置阅读货架下图书商品排序值
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
                             HttpServletResponse response) throws BOException
    {
        String forward = "setSort";

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();
        
        String actionType = "修改阅读货架下商品排序号信息";
		boolean actionResult = true;
		String actionDesc = "修改阅读货架下商品排序号信息成功";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 设置图书货架下图书商品排序值
            ReadReferenceBO.getInstance().setReadSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "修改阅读货架下商品排序号信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于设置阅读货架下音乐图书排序值时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "设置图书商品排序值成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * 用于查询图书列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryRead(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "queryRead";

        // 从请求中获取货架内码
        String isFirst = this.getParameter(request, "isFirst").trim();
        String bookName = this.getParameter(request, "bookName").trim();
        String bookId = this.getParameter(request, "bookId").trim();
        String authorName = this.getParameter(request, "authorName").trim();
        String typeName = this.getParameter(request, "typeName").trim();
        String chargeType = this.getParameter(request, "chargeType").trim();
        ReadReferenceVO vo = new ReadReferenceVO();
        PageResult page = new PageResult(request);
        
        // 如果是第一次。跳过
        if("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("perType", forward);
            request.setAttribute("PageResult", page);
            request.setAttribute("bookName", bookName);
            request.setAttribute("bookId", bookId);
            request.setAttribute("authorName", authorName);
            request.setAttribute("typeName", typeName);
            request.setAttribute("chargeType", chargeType);
            
            return mapping.findForward(forward);
        }
        
        vo.setBookId(bookId);
        vo.setBookName(bookName);
        vo.setAuthorName(authorName);
        vo.setTypeName(typeName);
        vo.setChargeType(chargeType);
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取图书条件：bookName=" + bookName + ", bookId="
                      + bookId + ", authorName=" + authorName);
        }

        try
        {
            // 查询图书列表。用以上架至新货架上
            ReadReferenceBO.getInstance().queryReadList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询图书列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("bookName", bookName);
        request.setAttribute("bookId", bookId);
        request.setAttribute("authorName", authorName);
        request.setAttribute("typeName", typeName);
        request.setAttribute("chargeType", chargeType);
        
        return mapping.findForward(forward);
    }
    
    /**
     * 用于添加指定的图书至货架中
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
        String addBookId = this.getParameter(request, "addBookId").trim();

        String actionType = "新增阅读货架下商品信息";
		boolean actionResult = true;
		String actionDesc = "新增阅读货架下商品信息成功";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 查看原货架是否存在用户将要新增的音乐信息
            String ret = ReadReferenceBO.getInstance().isHasReadRefs(categoryId,
                                                                    addBookId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "原货架存在用户所选以下图书：" + ret);
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "新增阅读货架下商品信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "查看原货架是否存在用户将要新增时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // 新增指定图书至指定货架上
            ReadReferenceBO.getInstance().addReadRefs(categoryId, addBookId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "新增阅读货架下商品信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于添加指定的图书至货架时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "添加图书成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * 文件批量导入图书商品上架
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
        
        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addType = this.getParameter(request, "addType").trim();
        DataImportForm iForm=(DataImportForm)form;
        
        String actionType = "导入阅读货架下商品信息";
		boolean actionResult = true;
		String actionDesc = "导入阅读货架下商品信息成功";
		String actionTarget = categoryId;
        
//        // 校验文件后缀名  //removed by aiyan 2012-12-25 这些东西再页面JS处理。
//        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
//        {
//            this.saveMessages(request, "文件后缀名出错！");
//            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
//        }
        
        FormFile dataFile = iForm.getDataFile();
        
        try
        {
        	if("ADD".equals(addType)){
        		ret =ReadReferenceBO.getInstance().importContentADD(dataFile, categoryId);
        	}else if("ALL".equals(addType)){
        		ret = ReadReferenceBO.getInstance().importContentALL(dataFile, categoryId);
        	}
            
        }
        
//        try
//        {
//            // 文件批量导入图书商品上架
//            ret = ReadReferenceBO.getInstance().importContentExigence(dataFile, categoryId);
//        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "导入阅读货架下商品信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于文件批量导入图书商品上架出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "文件批量导入图书商品上架操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    
    /**
     * 用于查询图书详情
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward showRead(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "showRead";

        // 从请求中获取货架内码
     
        String bookId = this.getParameter(request, "bookId").trim();
       
		
        ReadReferenceVO vo = null;
        List keyBaseList = null;
        try
        {
            // 查询图书详情
        	vo =  ReadReferenceBO.getInstance().queryReadInfo(bookId);
        	keyBaseList = ReadReferenceBO.getInstance().queryReadKeyResource(bookId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询图书详情出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }    
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("bookId", bookId);
        return mapping.findForward(forward);
    }
    
    /**
     * 用于保存图书扩展属性
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward saveRead(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "saveRead";

        // 从请求中获取货架内码
        String bookId = this.getParameter(request, "bookId").trim();
        FileForm fileForm = (FileForm) form;
        
        String actionType = "修改阅读货架下商品信息详情";
		boolean actionResult = true;
		String actionDesc = "修改阅读货架下商品信息详情成功";
		String actionTarget = bookId;
		
        // 校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        ReadReferenceVO vo = null;
        List keyBaseList = null;
        try
        {
            // 查询图书详情
        	vo =  ReadReferenceBO.getInstance().queryReadInfo(bookId);
        	keyBaseList = ReadReferenceBO.getInstance().queryReadKeyResource(bookId);
        	
        	if (keyBaseList != null && keyBaseList.size() > 0)
    		{
    			this.saveReadKeyResource(keyBaseList,fileForm,bookId,request);
    			
    		}
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "删除阅读货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "保存图书扩展信息出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        this.saveMessages(request, "更新图书扩展属性值成功!");

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        forward = Constants.FORWARD_COMMON_SUCCESS;  
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("bookId", bookId);
        return mapping.findForward(forward);
    }
    
    /**
     * 
     *@desc 保存扩展字段
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveReadKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//资源服务器本模块路径
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("baseRead").getItemValue("resServerPath");  
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"read");  
    	
    	NewMusicRefBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
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
		
		String actionType = "导出图书商品信息";
		boolean actionResult = true;
		String actionDesc = "导出图书商品信息成功";
		String actionTarget = "";
		
		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "vo_newbookreference_" + System.currentTimeMillis()
				+ ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			ReadReferenceBO.getInstance()
					.exportNewBookReferenceData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "导出音乐商品信息出错";
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
}
