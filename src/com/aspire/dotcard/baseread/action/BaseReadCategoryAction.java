package com.aspire.dotcard.baseread.action;

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
import com.aspire.dotcard.baseread.bo.ReadCategoryBO;
import com.aspire.dotcard.baseread.dao.BookCategoryDao;
import com.aspire.dotcard.baseread.vo.ReadCategoryVO;
import com.aspire.dotcard.gcontent.CityVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.CategoryCityBO;
import com.aspire.ponaadmin.web.repository.CategoryPlatFormBO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

public class BaseReadCategoryAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BaseReadCategoryAction.class);

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
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
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
        else if ("city".equals(perType))
        {
            return city(mapping, form, request, response);
        }
        else if ("categoryQuery".equals(perType))
        {
            return categoryQuery(mapping, form, request, response);
        }
        else if ("approval".equals(perType))
		{
			return approval(mapping, form, request, response);
		}
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }
    
    /**
	 * 图书货架提交审批
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
			ReadCategoryBO.getInstance().approvalCategory(request,categoryId);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "提交审批图书货架出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_BOOKCATEGORY_RESULT_001");
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
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "query";
        String categoryId = this.getParameter(request, "categoryId").trim();
        String operation = this.getParameter(request, "operation");
        String cityName = null;
        String platFormName = null;
        ReadCategoryVO categoryVO;
        List keyBaseList = null;
        
		String actionType = "查询阅读货架信息";
		boolean actionResult = true;
		String actionDesc = "查询阅读货架信息成功";
		String actionTarget = categoryId;
		String path = "";
		
        try
        {
            // 查询货架信息
            categoryVO = ReadCategoryBO.getInstance()
                                       .queryReadCategoryVO(categoryId);
            // 查询城市信息
            cityName = CategoryCityBO.getInstance()
                                     .queryCityByCityIds(categoryVO.getCityId());
            // 查询平台信息
            platFormName = CategoryPlatFormBO.getInstance()
                                             .queryPlatFormByPlatFormIds(categoryVO.getPlatForm());

            keyBaseList = ReadCategoryBO.getInstance()
                                        .queryReadCategoryKeyBaseList(categoryId);
            
            path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 1);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "查询阅读货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "查询基地阅读货架列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("path", path);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("cityName", cityName);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("operation", operation);
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
        
        String actionType = "删除阅读货架信息";
		boolean actionResult = true;
		String actionDesc = "删除阅读货架信息成功";
		String actionTarget = categoryId;
		
        try
        {
            // 是否存在子货架
            if (0 < ReadCategoryBO.getInstance().hasReadChild(categoryId))
            {
                this.saveMessagesValue(request, "本货架下有子货架不可以删除！");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            // 是否存在商品
            if (0 < ReadCategoryBO.getInstance().hasRead(categoryId))
            {
                this.saveMessagesValue(request, "本货架下有商品不可以删除！");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            // 用于删除指定货架
            //ReadCategoryBO.getInstance().delReadCategory(categoryId);
            // 删除扩展字段值
            //ReadCategoryBO.getInstance().delReadCategoryKeyResource(categoryId);
            ReadCategoryBO.getInstance().delBookCategoryItem(categoryId);
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
	        
            this.saveMessagesValue(request, "查询基地阅读货架是否满足可删除条件出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "删除基地阅读货架成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "../book/new_category_main.jsp");
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
        ReadCategoryVO readCategory = new ReadCategoryVO();

        readCategory.setParentId(pCategoryId);
        List keyBaseList = ReadCategoryBO.getInstance()
                                         .queryReadCategoryKeyBaseList(null);

        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("readCategory", readCategory);
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

        FileForm fileForm = ( FileForm ) form;

        // 校验文件后缀名
        if (!fileForm.checkFileNameExtension(new String[] { "png", "jpg", "gif" }))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        Hashtable files = fileForm.getMultipartRequestHandler()
                                  .getFileElements();
        FormFile uploadFile = ( FormFile ) files.get("dataFile");

        ReadCategoryVO readVO = new ReadCategoryVO();
        String pCategoryId = this.getParameter(request, "pCategoryId").trim();
        String categoryName = this.getParameter(request, "name").trim();
        String decrisption = this.getParameter(request, "decrisption").trim();
        String type = this.getParameter(request, "type").trim();
        String catalogType = this.getParameter(request, "catalogType").trim();
        String platForms = formatListToSearchString(request.getParameterValues("platForms"));
        String citys = formatListToSearchString(request.getParameterValues("citys"));
        String sortId = this.getParameter(request, "sortId").trim();
        String catepicURL = "";
        String cid = ReadCategoryBO.getInstance().getReadVOId();
        String categoryId = ReadCategoryBO.getInstance().getReadVOCategoryId();
        
        String actionType = "新增阅读货架信息";
		boolean actionResult = true;
		String actionDesc = "新增阅读货架信息成功";
		String actionTarget = pCategoryId;

        List keyBaseList = ReadCategoryBO.getInstance()
                                         .queryReadCategoryKeyBaseList(cid);

        readVO.setParentId(pCategoryId);
        readVO.setCategoryName(categoryName);
        readVO.setDecrisption(decrisption);
        readVO.setType(type);
        readVO.setCatalogType(catalogType);
        readVO.setPlatForm(platForms);
        readVO.setCityId(citys);
        
        if("0".equals(sortId))
		{
        	readVO.setSortId(SEQCategoryUtil.getInstance()
					.getSEQByCategoryType(2));
		}
		else
		{
			readVO.setSortId(Integer.parseInt(sortId));
		}
        

        try
        {
            // 当前父货架下是否已存在相同名称货架
            ReadCategoryBO.getInstance().hasReadNameInParentId(pCategoryId,
                                                               categoryName);

            // 判断新增货架地域信息是否为父货架地域信息子集
            CategoryCityBO.getInstance()
                          .isHasReadPCityIds(pCategoryId,
                                             request.getParameterValues("citys"));

            // 得到主键id
            readVO.setId(cid);
            readVO.setCategoryId(categoryId);

            // 保存图片
            if (uploadFile.getFileName() != null
                && !"".equals(uploadFile.getFileName()))
            {
                catepicURL = ReadCategoryBO.getInstance()
                                           .uploadCatePicURL(uploadFile,
                                                             readVO.getId());

                readVO.setPicUrl(catepicURL);
            }

            // 用于新增新音乐货架
            ReadCategoryBO.getInstance().saveReadCategory(readVO);

            if (keyBaseList != null && keyBaseList.size() > 0)
            {
                this.saveReadCategoryKeyResource(keyBaseList,
                                                 fileForm,
                                                 cid,
                                                 request);
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

			// 写操作日志
			actionResult = false;
			actionDesc = "新增阅读货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
            
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "新增阅读货架成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "../book/new_category_main.jsp");
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
        ReadCategoryVO readVO;
        String cityName = null;
        String platFormName = null;
        List platFormList = null;
        List cityList = null;
        List keyBaseList = null;
        String path = "";
        try
        {
            // 得到当前货架信息
            readVO = ReadCategoryBO.getInstance()
                                   .queryReadCategoryVO(categoryId);

            // 得到当前货架城市信息
            cityName = CategoryCityBO.getInstance()
                                     .queryCityByCityIds(readVO.getCityId());
            cityList = CategoryCityBO.getInstance()
                                     .queryListByCityId(readVO.getCityId());
            // 得到当前货架平台信息
            platFormName = CategoryPlatFormBO.getInstance()
                                             .queryPlatFormByPlatFormIds(readVO.getPlatForm());
            platFormList = CategoryCityBO.getInstance()
                                         .queryListByCityId(readVO.getPlatForm());
            keyBaseList = ReadCategoryBO.getInstance()
                                        .queryReadCategoryKeyBaseList(categoryId);
            
            path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 1);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询阅读货架列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("path", path);
        request.setAttribute("pCategoryId", parentId);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("cityName", cityName);
        request.setAttribute("cityList", cityList);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("platFormList", platFormList);
        request.setAttribute("readVO", readVO);
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
        FileForm fileForm = ( FileForm ) form;

        // 校验文件后缀名
        if (!fileForm.checkFileNameExtension(new String[] { "png", "jpg", "gif" }))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        Hashtable files = fileForm.getMultipartRequestHandler()
                                  .getFileElements();
        FormFile uploadFile = ( FormFile ) files.get("dataFile");

        ReadCategoryVO readVO = new ReadCategoryVO();
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
        
        String actionType = "修改阅读货架信息";
		boolean actionResult = true;
		String actionDesc = "修改阅读货架信息成功";
		String actionTarget = categoryId;
		

        List keyBaseList = ReadCategoryBO.getInstance()
                                         .queryReadCategoryKeyBaseList(categoryId);

        readVO.setCategoryId(categoryId);
        readVO.setParentId(pCategoryId);
        readVO.setCategoryName(categoryName);
        readVO.setDecrisption(decrisption);
        readVO.setCatalogType(catalogType);
        readVO.setType(type);
        readVO.setPlatForm(platForms);
        readVO.setCityId(citys);
        readVO.setSortId(Integer.parseInt(sortId));

        try
        {
            // 如果要清空图片信息。
            if ("1".equals(clearPicUrl))
            {
                readVO.setPicUrl("");
            }
            // 如果有改。
            else if (uploadFile != null && !"".equals(uploadFile.getFileName()))
            {
                catepicURL = ReadCategoryBO.getInstance()
                                           .uploadCatePicURL(uploadFile,
                                                             readVO.getCategoryId());

                readVO.setPicUrl(catepicURL);
            }
            // 如果没有改。
            else
            {
                readVO.setPicUrl(oldPicURL);
            }

            // 如果没有改变货架名称
            if (!oldName.equals(categoryName))
            {
                // 当前父货架下是否已存在相同名称货架
                ReadCategoryBO.getInstance()
                              .hasReadNameInParentId(pCategoryId, categoryName);
            }

            if (null != request.getParameterValues("citys"))
            {
                // 查看当前变更的地域信息是否少于子货架的地域信息集合
                CategoryCityBO.getInstance()
                              .isHasReadCity(categoryId,
                                             request.getParameterValues("citys"));
            }

            // 判断变更的货架地域信息是否为父货架地域信息子集
            CategoryCityBO.getInstance()
                          .isHasReadPCityIds(pCategoryId,
                                             request.getParameterValues("citys"));

            // 用于变更图书货架
            ReadCategoryBO.getInstance().updateReadCategory(readVO);

            if (keyBaseList != null && keyBaseList.size() > 0)
            {
                this.saveReadCategoryKeyResource(keyBaseList,
                                                 fileForm,
                                                 categoryId,
                                                 request);

            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "更新阅读货架列表出错");

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

			// 写操作日志
			actionResult = false;
			actionDesc = "修改阅读货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "更新阅读货架成功");
        request.setAttribute(Constants.PARA_GOURL,
                             "../../web/baseRead/categoryTree.do?categoryId="
                                             + categoryId
                                             + "&perType=query&isReload=yes");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        return mapping.findForward(forward);
    }

    /**
     * 
     * @desc 保存扩展字段
     * @author dongke Aug 8, 2011
     * @throws BOException
     */
    public void saveReadCategoryKeyResource(List keyBaseList,
                                            FileForm fileForm, String cid,
                                            HttpServletRequest request)
                    throws BOException
    {

        // 资源服务器本模块路径
        String resServerPath = ConfigFactory.getSystemConfig()
                                            .getModuleConfig("baseRead")
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

    public ActionForward city(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("city doPerform()");
        }
        String forward = "city";

        // 从请求中获取货架条件
        String cityName = this.getParameter(request, "cityName").trim();
        String pvcName = this.getParameter(request, "pvcName").trim();
        String categoryID = this.getParameter(request, "categoryID");
        String pCategoryID = this.getParameter(request, "pCategoryID");
        String isFirst = this.getParameter(request, "isFirst").trim();

        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // 根据获取的参数查机型表信息
        if (isFirst.equals("1"))// 第一次不需要查询
        {
            page.excute(new ArrayList(0));
            request.setAttribute("notice", "请输入条件查询");
        }
        else
        {
            CityVO city = new CityVO();
            city.setCityName(cityName);
            city.setPvcName(pvcName);

            CategoryCityBO.getInstance().queryReadCityList(page,
                                                           city,
                                                           categoryID,
                                                           pCategoryID);
        }

        // 将list放置到page中用于分页展示
        request.setAttribute("cityName", cityName);
        request.setAttribute("pvcName", pvcName);
        request.setAttribute("categoryID", categoryID);
        request.setAttribute("pCategoryID", pCategoryID);
        request.setAttribute("PageResult", page);

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
    public ActionForward categoryQuery(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "categoryQuery";
        String categoryId = this.getParameter(request, "categoryId").trim();
        String categoryName = this.getParameter(request, "categoryName").trim();
        String isFirst = this.getParameter(request, "isFirst").trim();
        PageResult page = new PageResult(request);
        
		String actionType = "查询阅读货架详情信息";
		boolean actionResult = true;
		String actionDesc = "查询阅读货架详情信息成功";
		String actionTarget = "";
		
		if("1".equals(isFirst))
		{
	        request.setAttribute("categoryId", categoryId);
	        request.setAttribute("categoryName", categoryName);
	        request.setAttribute("perType", forward);
	        request.setAttribute("PageResult", page);
	        return mapping.findForward(forward);
		}
		
        try
        {
        	ReadCategoryVO categoryVO = new ReadCategoryVO();
        	categoryVO.setId(categoryId);
        	categoryVO.setCategoryName(categoryName);
        	
            // 查询货架详情信息
        	ReadCategoryBO.getInstance().queryCategoryDescList(page, categoryVO);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "查询阅读货架详情信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "查询基地阅读货架详情列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }
}
