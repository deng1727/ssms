/*
 * 文件名：BookCategoryAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.book.action;

import java.util.ArrayList;
import java.util.Hashtable;
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
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.book.bo.BookCategoryBO;
import com.aspire.ponaadmin.web.book.vo.BookCategoryVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;

import com.aspire.ponaadmin.web.repository.CategoryCityBO;
import com.aspire.ponaadmin.web.repository.CategoryPlatFormBO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BookCategoryAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BookCategoryAction.class);
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
    public ActionForward tree(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "tree";

        List categoryList = null;
        try
        {
            categoryList = BookCategoryBO.getInstance()
                                             .queryBookCategoryList();
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询图书货架列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("categoryList", categoryList);

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
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "query";
        String categoryId = this.getParameter(request, "categoryId").trim();
        String cityName = null;
        String platFormName = null;
        BookCategoryVO categoryVO;
        List keyBaseList = null;
        try
        {
            // 查询货架信息
            categoryVO = BookCategoryBO.getInstance()
                                                   .queryBookCategoryVO(categoryId);
            // 查询城市信息
            cityName = CategoryCityBO.getInstance()
                                     .queryCityByCityIds(categoryVO.getCityId());
            // 查询平台信息
            platFormName = CategoryPlatFormBO.getInstance()
                                             .queryPlatFormByPlatFormIds(categoryVO.getPlatForm());
            keyBaseList = BookCategoryBO.getInstance().queryBookCategoryKeyBaseList(categoryId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询图书货架列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("cityName", cityName);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("categoryVO", categoryVO);

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
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String categoryId = this.getParameter(request, "categoryId").trim();
        try
        {
            // 是否存在子货架
            if (0 < BookCategoryBO.getInstance()
                                      .hasBookChild(categoryId))
            {
                this.saveMessagesValue(request, "本货架下有子货架不可以删除！");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            // 是否存在商品
            if (0 < BookCategoryBO.getInstance().hasBook(categoryId))
            {
                this.saveMessagesValue(request, "本货架下有商品不可以删除！");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            // 用于删除指定货架
            BookCategoryBO.getInstance().delBookCategory(categoryId);
            //删除扩展字段值
            BookCategoryBO.getInstance().delBookCategoryKeyResource(categoryId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询图书货架是否满足可删除条件出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "删除图书货架成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "../book/category_main.jsp");
        request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
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
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "add";

        String pCategoryId = this.getParameter(request, "parentCategoryId")
                                 .trim();
        BookCategoryVO bookCategory = new BookCategoryVO();

        bookCategory.setParentId(pCategoryId);
        List keyBaseList = BookCategoryBO.getInstance().queryBookCategoryKeyBaseList(null);
        
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("bookCategory", bookCategory);
        request.setAttribute("pCategoryId", pCategoryId);

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
                              HttpServletRequest request,
                              HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = Constants.FORWARD_COMMON_FAILURE;
        
       // DataImportForm iForm = ( DataImportForm ) form;
//      扩展字段添加
		FileForm fileForm = (FileForm) form;
        
        // 校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
      //  FormFile uploadFile = fileForm.getDataFile();
        Hashtable  files = fileForm.getMultipartRequestHandler().getFileElements();
        FormFile uploadFile = (FormFile)files.get("dataFile");
        
        BookCategoryVO bookVO = new BookCategoryVO();
        String pCategoryId = this.getParameter(request, "pCategoryId").trim();
        String categoryName = this.getParameter(request, "name").trim();
        String decrisption = this.getParameter(request, "decrisption").trim();
        String type = this.getParameter(request, "type").trim();
        String catalogType = this.getParameter(request, "catalogType").trim();
        String platForms = formatListToSearchString(request.getParameterValues("platForms"));
        String citys = formatListToSearchString(request.getParameterValues("citys"));
        String sortId = this.getParameter(request, "sortId").trim();
        String catepicURL = "";
        String cid  = BookCategoryBO.getInstance().getBookVOId();
    	

		List keyBaseList = BookCategoryBO.getInstance()
				.queryBookCategoryKeyBaseList(cid);
        
        bookVO.setId(cid);
        bookVO.setParentId(pCategoryId);
        bookVO.setCategoryName(categoryName);
        bookVO.setDecrisption(decrisption);
        bookVO.setType(type);
        bookVO.setCatalogType(catalogType);
        bookVO.setPlatForm(platForms);
        bookVO.setCityId(citys);
        bookVO.setSortId(Integer.parseInt(sortId));

        try
        {
            // 当前父货架下是否已存在相同名称货架
            BookCategoryBO.getInstance().hasBookNameInParentId(pCategoryId,
                                                               categoryName);

            // 判断新增货架地域信息是否为父货架地域信息子集
            CategoryCityBO.getInstance()
                          .isHasBookPCityIds(pCategoryId,
                                             request.getParameterValues("citys"));

            // 得到主键id
            bookVO.setCategoryId(cid);

            // 保存图片
            if (uploadFile.getFileName()!= null &&  !"".equals(uploadFile.getFileName()))
            {
            	/*String extfile = uploadFile.getFileName();
        		if (extfile != null  &&!  extfile.equals("")&& !extfile.toUpperCase().endsWith("PNG")&&!extfile.toUpperCase().endsWith("JPG"))
        		{
        			this.saveMessages(request, "图书货架图片不是png或jpg格式");
        			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        		}*/
                catepicURL = BookCategoryBO.getInstance()
                                           .uploadCatePicURL(uploadFile, bookVO.getCategoryId());

                bookVO.setPicUrl(catepicURL);
            }
            
            // 用于新增新音乐货架
            BookCategoryBO.getInstance().saveBookCategory(bookVO);
            
        	if (keyBaseList != null)
			{
				this.saveBookCategoryKeyResource(keyBaseList,fileForm,cid,request);
			}
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "新增图书货架列表出错");

            if (e.getErrorCode() == RepositoryBOCode.ADD_CATEGORY_CITY)
            {
                this.saveMessages(request, "因新增货架地域适配信息时大于父货架地域信息集合而失败");
            }
            else if (e.getErrorCode() == RepositoryBOCode.ADD_UPDATE_CATEGORY_NAME)
            {
                this.saveMessages(request, "此货架名称在同父货架下已存在");
            }

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "新增图书货架成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "../book/category_main.jsp");
        request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
        return mapping.findForward(forward);
    }

    /**
     * 用于把数组信息组合成数据库中为了搜索应用的格式
     * 
     * @param valueList
     * @return ｛｝，｛｝
     */
    private String formatListToSearchString(String[] valueList)
    {
        StringBuffer sb = new StringBuffer();

        // 如果为空。为全部应用
        if (null == valueList)
        {
            sb.append("{").append(Constants.ALL_CITY_PLATFORM).append("}");

            return sb.toString();
        }

        // 迭代处理
        for (int i = 0; i < valueList.length; i++)
        {
            String temp = valueList[i];

            sb.append("{").append(temp).append("}").append(",");
        }

        // 删除最后一个信息
        sb.delete(sb.length() - 1, sb.length());

        return sb.toString();
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
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "mod";
        String categoryId = this.getParameter(request, "categoryId").trim();
        String parentId = this.getParameter(request, "parentId").trim();
        BookCategoryVO bookVO;
        String cityName = null;
        String platFormName = null;
        List platFormList = null;
        List cityList = null;
        List keyBaseList = null;
        try
        {
            // 得到当前货架信息
            bookVO = BookCategoryBO.getInstance()
                                   .queryBookCategoryVO(categoryId);

            // 得到当前货架城市信息
            cityName = CategoryCityBO.getInstance()
                                     .queryCityByCityIds(bookVO.getCityId());
            cityList = CategoryCityBO.getInstance()
                                     .queryListByCityId(bookVO.getCityId());
            // 得到当前货架平台信息
            platFormName = CategoryPlatFormBO.getInstance()
                                             .queryPlatFormByPlatFormIds(bookVO.getPlatForm());
            platFormList = CategoryCityBO.getInstance()
                                         .queryListByCityId(bookVO.getPlatForm());
            keyBaseList = BookCategoryBO.getInstance().queryBookCategoryKeyBaseList(categoryId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询图书货架列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("keyBaseList", keyBaseList);
        
        request.setAttribute("pCategoryId", parentId);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("cityName", cityName);
        request.setAttribute("cityList", cityList);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("platFormList", platFormList);
        request.setAttribute("bookVO", bookVO);
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
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        String forward = Constants.FORWARD_COMMON_FAILURE;
        //DataImportForm iForm = ( DataImportForm ) form;
        FileForm fileForm = (FileForm) form;
        
        // 校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png", "jpg", "gif"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        //FormFile uploadFile = fileForm.getDataFile();
        Hashtable  files = fileForm.getMultipartRequestHandler().getFileElements();
        FormFile uploadFile = (FormFile)files.get("dataFile");
        
        BookCategoryVO bookVO = new BookCategoryVO();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String pCategoryId = this.getParameter(request, "pCategoryId").trim();
        String categoryName = this.getParameter(request, "name").trim();
        String oldName = this.getParameter(request, "oldName").trim();
        String decrisption = this.getParameter(request, "decrisption").trim();
        String type = this.getParameter(request, "type").trim();
        String catalogType = this.getParameter(request, "catalogType").trim();
        String platForms = formatListToSearchString(request.getParameterValues("platForms"));
        String citys = formatListToSearchString(request.getParameterValues("citys"));
        String oldPicURL = this.getParameter(request, "oldPicURL").trim();
        String clearPicUrl = this.getParameter(request, "clearPicUrl").trim();
        String sortId = this.getParameter(request, "sortId").trim();
        
        String catepicURL = "";
        
        
		List keyBaseList = BookCategoryBO.getInstance()
				.queryBookCategoryKeyBaseList(categoryId);
		
        bookVO.setCategoryId(categoryId);
        bookVO.setParentId(pCategoryId);
        bookVO.setCategoryName(categoryName);
        bookVO.setDecrisption(decrisption);
        bookVO.setCatalogType(catalogType);
        bookVO.setType(type);
        bookVO.setPlatForm(platForms);
        bookVO.setCityId(citys);
        bookVO.setSortId(Integer.parseInt(sortId));

        try
        {
            // 如果要清空图片信息。
            if("1".equals(clearPicUrl))
            {
                bookVO.setPicUrl("");
            }
            // 如果有改。
            else if (uploadFile != null && !"".equals(uploadFile.getFileName()))
            {
            	/*String extfile = uploadFile.getFileName();
        		if (extfile != null  &&!  extfile.equals("")&& !extfile.toUpperCase().endsWith("PNG")&&!extfile.toUpperCase().endsWith("JPG"))
        		{
        			this.saveMessages(request, "图书货架图片不是png或jpg格式");
        			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        		}*/
                catepicURL = BookCategoryBO.getInstance()
                                   .uploadCatePicURL(uploadFile,
                                           bookVO.getCategoryId());
                
                bookVO.setPicUrl(catepicURL);
            }
            // 如果没有改。
            else
            {
                bookVO.setPicUrl(oldPicURL);
            }
            
            // 如果没有改变货架名称
            if(!oldName.equals(categoryName))
            {
                // 当前父货架下是否已存在相同名称货架
                BookCategoryBO.getInstance().hasBookNameInParentId(pCategoryId, categoryName);   
            }
            
            if (null != request.getParameterValues("citys"))
            {
                // 查看当前变更的地域信息是否少于子货架的地域信息集合
                CategoryCityBO.getInstance()
                              .isHasBookCity(categoryId,
                                                 request.getParameterValues("citys"));
            }

            // 判断变更的货架地域信息是否为父货架地域信息子集
            CategoryCityBO.getInstance()
                          .isHasBookPCityIds(pCategoryId,
                                                 request.getParameterValues("citys"));

            // 用于变更图书货架
            BookCategoryBO.getInstance()
                              .updateBookCategory(bookVO);
            
            if (keyBaseList != null)
    		{
    			this.saveBookCategoryKeyResource(keyBaseList,fileForm,categoryId,request);
    			
    		}
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "更新图书货架列表出错");

            if (e.getErrorCode() == RepositoryBOCode.UPDATE_CATEGORY_CITY)
            {
                this.saveMessages(request, "因变更货架地域适配信息时小于子货架地域信息集合而失败");
            }
            else if (e.getErrorCode() == RepositoryBOCode.ADD_CATEGORY_CITY)
            {
                this.saveMessages(request, "因变更货架地域适配信息时大于父货架地域信息集合而失败");
            }
            else if (e.getErrorCode() == RepositoryBOCode.ADD_UPDATE_CATEGORY_NAME)
            {
                this.saveMessages(request, "此货架名称在同父货架下已存在");
            }

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "更新图书货架成功");
        request.setAttribute(Constants.PARA_GOURL,
                             "../../web/book/categoryTree.do?categoryId="+ categoryId +"&perType=query&isReload=yes") ;
        forward = Constants.FORWARD_COMMON_SUCCESS ;
        return mapping.findForward(forward);
    }
    /**
     * 
     *@desc 保存扩展字段
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveBookCategoryKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//资源服务器本模块路径
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("basebook").getItemValue("resServerPath");  
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"category");  
    	BookCategoryBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
}
