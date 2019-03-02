/*
 * 文件名：BookRefAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.book.action;

import java.util.ArrayList;
import java.util.List;

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
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.book.bo.BookRefBO;
import com.aspire.ponaadmin.web.book.vo.BookRefVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicRefBO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BookRefAction extends BaseAction
{
    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BookRefAction.class);
    
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
        else if ("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if ("setSort".equals(perType))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryBook".equals(perType))
        {
            return queryBook(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }else if ("showBook".equals(perType))
        {
            return showBook(mapping, form, request, response);
        }else if ("saveBook".equals(perType))
        {
            return saveBook(mapping, form, request, response);
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
        BookRefVO vo = new BookRefVO();
        PageResult page = new PageResult(request);

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String bookId = this.getParameter(request, "bookId").trim();
        String bookName = this.getParameter(request, "bookName").trim();
        String authorName = this.getParameter(request, "authorName").trim();
        String typeName = this.getParameter(request, "typeName").trim();

        vo.setCId(categoryId);
        vo.setBookId(bookId);
        vo.setBookName(bookName);
        vo.setAuthorName(authorName);
        vo.setTypeName(typeName);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            BookRefBO.getInstance().queryBookRefList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询图书指定货架下的商品列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("bookId", bookId);
        request.setAttribute("bookName", bookName);
        request.setAttribute("authorName", authorName);
        request.setAttribute("typeName", typeName);
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
        String[] musicId = request.getParameterValues("dealRef");

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            BookRefBO.getInstance().removeBookRefs(categoryId, musicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于移除指定货架下指定的图书出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "删除图书成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * 用于设置图书货架下图书商品排序值
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

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 设置图书货架下图书商品排序值
            BookRefBO.getInstance().setBookSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于设置图书货架下音乐图书排序值时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

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
    public ActionForward queryBook(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "queryBook";

        // 从请求中获取货架内码
        String isFirst = this.getParameter(request, "isFirst").trim();
        String bookName = this.getParameter(request, "bookName").trim();
        String bookId = this.getParameter(request, "bookId").trim();
        String authorName = this.getParameter(request, "authorName").trim();
        String typeName = this.getParameter(request, "typeName").trim();
        BookRefVO vo = new BookRefVO();
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
            
            return mapping.findForward(forward);
        }
        
        vo.setBookId(bookId);
        vo.setBookName(bookName);
        vo.setAuthorName(authorName);
        vo.setTypeName(typeName);
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取图书条件：bookName=" + bookName + ", bookId="
                      + bookId + ", authorName=" + authorName);
        }

        try
        {
            // 查询图书列表。用以上架至新货架上
            BookRefBO.getInstance().queryBookList(page, vo);
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

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 查看原货架是否存在用户将要新增的音乐信息
            String ret = BookRefBO.getInstance().isHasBookRefs(categoryId,
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
            this.saveMessagesValue(request, "查看原货架是否存在用户将要新增时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // 新增指定图书至指定货架上
            BookRefBO.getInstance().addBookRefs(categoryId, addBookId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于添加指定的图书至货架时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

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
        DataImportForm iForm=(DataImportForm)form;
        
        // 校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        FormFile dataFile = iForm.getDataFile();
        try
        {
            // 文件批量导入图书商品上架
            ret = BookRefBO.getInstance().importContentExigence(dataFile, categoryId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于文件批量导入图书商品上架出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

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
    public ActionForward showBook(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "showBook";

        // 从请求中获取货架内码
     
        String bookId = this.getParameter(request, "bookId").trim();
       
		
        BookRefVO vo = null;
        List keyBaseList = null;
        try
        {
            // 查询图书详情
        	vo =  BookRefBO.getInstance().queryBookInfo(bookId);
        	keyBaseList = BookRefBO.getInstance().queryBookKeyResource(bookId);
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
    public ActionForward saveBook(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "saveBook";

        // 从请求中获取货架内码
     
        String bookId = this.getParameter(request, "bookId").trim();
        FileForm fileForm = (FileForm) form;
		
        // 校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        BookRefVO vo = null;
        List keyBaseList = null;
        try
        {
            // 查询图书详情
        	vo =  BookRefBO.getInstance().queryBookInfo(bookId);
        	keyBaseList = BookRefBO.getInstance().queryBookKeyResource(bookId);
        	
        	if (keyBaseList != null)
    		{
    			this.saveBookKeyResource(keyBaseList,fileForm,bookId,request);
    			
    		}
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "保存图书扩展信息出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        this.saveMessages(request, "更新图书扩展属性值成功!");

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
    public void saveBookKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//资源服务器本模块路径
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("basebook").getItemValue("resServerPath");  
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"book");  
    	
    	NewMusicRefBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
}
