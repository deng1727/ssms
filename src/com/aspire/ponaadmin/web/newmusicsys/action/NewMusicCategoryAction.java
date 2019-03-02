/**
 * 文件名：NewMusicCategoryAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.newmusicsys.action;

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
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicCategoryBO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicCategoryVO;
import com.aspire.ponaadmin.web.repository.CategoryCityBO;
import com.aspire.ponaadmin.web.repository.CategoryPlatFormBO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;
import com.aspire.ponaadmin.web.datafield.UploadFileKeyResUtil;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class NewMusicCategoryAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(NewMusicCategoryAction.class);

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

        if ("tree".equals(perType))
        {
            return tree(mapping, form, request, response);
        }
        else if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
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
        else if ("del".equals(perType))
        {
            return del(mapping, form, request, response);
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
	 * 音乐货架提交审批
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
			NewMusicCategoryBO.getInstance().approvalCategory(request,categoryId);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "提交审批音乐货架出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		this.saveMessages(request, "RESOURCE_MUSICCATEGORY_RESULT_001");
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
    public ActionForward tree(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "tree";

        List categoryList = null;
        try
        {
            categoryList = NewMusicCategoryBO.getInstance()
                                             .queryNewMusicCategoryList();
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询新音乐货架列表出错");
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
        String operation = this.getParameter(request, "operation");
        String cityName = null;
        String platFormName = null;
        NewMusicCategoryVO newMusicCategoryVO;
        List keyBaseList = null;
        
        String actionType = "查询基地音乐货架信息";
		boolean actionResult = true;
		String actionDesc = "查询基地音乐货架信息成功";
		String actionTarget = categoryId;
		String path = "";
		
        try
        {
            // 查询货架信息
            newMusicCategoryVO = NewMusicCategoryBO.getInstance()
                                                   .queryNewMusicCategoryVO(categoryId);
            // 查询城市信息
            cityName = CategoryCityBO.getInstance()
                                     .queryCityByCityIds(newMusicCategoryVO.getCityId());
            // 查询平台信息
            platFormName = CategoryPlatFormBO.getInstance()
                                             .queryPlatFormByPlatFormIds(newMusicCategoryVO.getPlatForm());
            
            keyBaseList = NewMusicCategoryBO.getInstance().queryNewMusicCategoryKeyBaseList(categoryId);
            
            path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 3);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "查询基地音乐货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "查询新音乐货架列表出错");
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
        request.setAttribute("newMusicCategoryVO", newMusicCategoryVO);

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
        
        String actionType = "删除基地音乐货架信息";
		boolean actionResult = true;
		String actionDesc = "删除基地音乐货架信息成功";
		String actionTarget = categoryId;
		
        try
        {
            // 是否存在子货架
            if (0 < NewMusicCategoryBO.getInstance()
                                      .hasNewMusicChild(categoryId))
            {
                this.saveMessagesValue(request, "本货架下有子货架不可以删除！");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            // 是否存在商品
            if (0 < NewMusicCategoryBO.getInstance().hasNewMusic(categoryId))
            {
                this.saveMessagesValue(request, "本货架下有商品不可以删除！");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            // 用于删除指定货架
            //NewMusicCategoryBO.getInstance().delNewMusicCategory(categoryId);
            //删除扩展字段值
            //NewMusicCategoryBO.getInstance().delNewMusicCategoryKeyResource(categoryId);
            NewMusicCategoryBO.getInstance().delMusicCategoryItem(categoryId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "删除基地音乐货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "删除新音乐货架列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "删除新音乐货架成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "../newmusicsys/category_main.jsp");
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
        NewMusicCategoryVO newMusicCategory = new NewMusicCategoryVO();

        newMusicCategory.setParentCategoryId(pCategoryId);

        List keyBaseList = NewMusicCategoryBO.getInstance().queryNewMusicCategoryKeyBaseList(null);
        
        request.setAttribute("newMusicCategory", newMusicCategory);
        request.setAttribute("keyBaseList", keyBaseList);
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
		

		NewMusicCategoryVO newMusicCategory = new NewMusicCategoryVO();
		String pCategoryId = this.getParameter(request, "pCategoryId").trim();
		String categoryName = this.getParameter(request, "name").trim();
		String desc = this.getParameter(request, "desc").trim();
		String sortId = this.getParameter(request, "sortId").trim();
		String type = this.getParameter(request, "type").trim();
		String cateType = this.getParameter(request, "cateType").trim();
		String platForms = formatListToSearchString(request
				.getParameterValues("platForms"));
		String citys = formatListToSearchString(request.getParameterValues("citys"));
		String cid  = NewMusicCategoryBO.getInstance().getNewMusicCategoryId();
	
		//扩展字段添加
		FileForm fileForm = (FileForm) form;
		
        String actionType = "新增基地音乐货架信息";
		boolean actionResult = true;
		String actionDesc = "新增基地音乐货架信息成功";
		String actionTarget = cid;
	        
		List keyBaseList = NewMusicCategoryBO.getInstance()
				.queryNewMusicCategoryKeyBaseList(cid);
		
		 Hashtable  files = fileForm.getMultipartRequestHandler().getFileElements();
	        FormFile uploadFile = (FormFile)files.get("albumPic");
		String resourcePicFilePath = ConfigFactory.getSystemConfig()
		.getModuleConfig("music139").getItemValue("DestAlbumFTPDir");
		
		/*String extfile = uploadFile.getFileName();
		if (extfile != null  &&!  extfile.equals("")&& !extfile.toUpperCase().endsWith("PNG"))
		{
			this.saveMessages(request, "音乐货架图片不是png格式");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}*/

		  // 校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png","jpg","gif"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 

		

		String albumPic = UploadFileKeyResUtil.getInstance().upLoadfileToResServer(uploadFile,resourcePicFilePath,"",cid,"");
		
		newMusicCategory.setCategoryId(cid);		
		newMusicCategory.setParentCategoryId(pCategoryId);
		newMusicCategory.setCategoryName(categoryName);
		newMusicCategory.setDesc(desc);
		
		if("0".equals(sortId))
		{
			newMusicCategory.setSortId(SEQCategoryUtil.getInstance()
					.getSEQByCategoryType(4));
		}
		else
		{
			newMusicCategory.setSortId(Integer.parseInt(sortId));
		}
		
		newMusicCategory.setType(type);
		newMusicCategory.setPlatForm(platForms);
		newMusicCategory.setCityId(citys);
		newMusicCategory.setAlbumPic(albumPic);
		newMusicCategory.setCateType(cateType);
		try
		{
			// 判断新增货架地域信息是否为父货架地域信息子集
			CategoryCityBO.getInstance().isHasNewMusicPCityIds(pCategoryId,
					request.getParameterValues("citys"));
			// 用于新增新音乐货架
			NewMusicCategoryBO.getInstance().saveNewMusicCategory(newMusicCategory);
			if (keyBaseList != null)
			{
				this.saveNewMusicCategoryKeyResource(keyBaseList,fileForm,cid,request);
				/*hm = new HashMap();
				for (int i = 0; i < keyBaseList.size(); i++)
				{
					ResourceVO vo = (ResourceVO) keyBaseList.get(i);
					if (vo.getKeyType().equals("2"))
					{// 图片（文件）域
						//hm.put(vo.getKeyname(), null);	
						String fileUrlValue = UploadFileKeyResUtil.getInstance().getFileUrl(fileForm,resServerPath,cid,vo.getKeyname());
						if(vo.getValue()!= null && !vo.getValue().equals("")){
							//本身有值，则更新
							vo.setValue(fileUrlValue);
							vo.setTid(cid);
						int r =	NewMusicCategoryBO.getInstance().updateNewMusicCategoryKeyResource(vo);
							LOG.debug("更新了"+r+"条扩展字段name="+vo.getKeyname()+";value="+vo.getValue());
						}else{
						//没有值，则插入			
						vo.setValue(fileUrlValue);
						vo.setTid(cid);
						int r =	NewMusicCategoryBO.getInstance().insertNewMusicCategoryKeyResource(vo);
						LOG.debug("插入了"+r+"条扩展字段name="+vo.getKeyname()+";value="+vo.getValue());
						}
					}else if(vo.getKeyType().equals("1")){
						//普通文本域
						if(vo.getValue()!= null && !vo.getValue().equals("")){
							//本身有值，则更新
							vo.setValue(this.getParameter(request, vo.getKeyname()).trim());
							vo.setTid(cid);
							
							int r =	NewMusicCategoryBO.getInstance().updateNewMusicCategoryKeyResource(vo);
							LOG.debug("更新了"+r+"条扩展字段name="+vo.getKeyname()+";value="+vo.getValue());
						}else{
							//没有值，则插入
						vo.setValue(this.getParameter(request, vo.getKeyname()).trim());
						vo.setTid(cid);
						int r =	NewMusicCategoryBO.getInstance().insertNewMusicCategoryKeyResource(vo);
						LOG.debug("插入了"+r+"条扩展字段name="+vo.getKeyname()+";value="+vo.getValue());
						}
					}else{
						
						LOG.debug("keyType is not reg:vo.getKeyType() = "+vo.getKeyType());	
					}
				}*/
			}
		} catch (BOException e)
		{
			LOG.error(e);
			
            // 写操作日志
			actionResult = false;
			actionDesc = "新增基地音乐货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "新增新音乐货架列表出错");

			if (e.getErrorCode() == RepositoryBOCode.ADD_CATEGORY_CITY)
			{
				this.saveMessages(request, "因新增货架地域适配信息时大于父货架地域信息集合而失败");
			}

			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "新增新音乐货架成功");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL, "../newmusicsys/category_main.jsp");
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
        String parentCategoryId = this.getParameter(request, "parentCategoryId")
                                      .trim();
        NewMusicCategoryVO newMusicCategoryVO;
        String cityName = null;
        String platFormName = null;
        List platFormList = null;
        List cityList = null;
        List keyBaseList = null;
        String path = "";
        try
        {
            // 得到当前货架信息
            newMusicCategoryVO = NewMusicCategoryBO.getInstance()
                                                   .queryNewMusicCategoryVO(categoryId);

            // 得到当前货架城市信息
            cityName = CategoryCityBO.getInstance()
                                     .queryCityByCityIds(newMusicCategoryVO.getCityId());
            cityList = CategoryCityBO.getInstance()
                                     .queryListByCityId(newMusicCategoryVO.getCityId());
            // 得到当前货架平台信息
            platFormName = CategoryPlatFormBO.getInstance()
                                             .queryPlatFormByPlatFormIds(newMusicCategoryVO.getPlatForm());
            platFormList = CategoryCityBO.getInstance()
                                         .queryListByCityId(newMusicCategoryVO.getPlatForm());
            
            path = SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 3);
            
            keyBaseList = NewMusicCategoryBO.getInstance().queryNewMusicCategoryKeyBaseList(categoryId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询新音乐货架列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("path", path);
        request.setAttribute("pCategoryId", parentCategoryId);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("cityName", cityName);
        request.setAttribute("cityList", cityList);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("platFormList", platFormList);
        request.setAttribute("newMusicCategory", newMusicCategoryVO);
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
       
		
        NewMusicCategoryVO newMusicCategory = new NewMusicCategoryVO();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String pCategoryId = this.getParameter(request, "pCategoryId").trim();
        String categoryName = this.getParameter(request, "name").trim();
        String desc = this.getParameter(request, "desc").trim();
        String sortId = this.getParameter(request, "sortId").trim();
        String type = this.getParameter(request, "type").trim();
        String cateType = this.getParameter(request, "cateType").trim();
        String platForms = formatListToSearchString(request.getParameterValues("platForms"));
        String citys = formatListToSearchString(request.getParameterValues("citys"));
        
        String actionType = "修改基地音乐货架信息";
		boolean actionResult = true;
		String actionDesc = "修改基地音乐货架信息成功";
		String actionTarget = categoryId;
        
        FileForm fileForm = (FileForm) form;
		List keyBaseList = NewMusicCategoryBO.getInstance()
				.queryNewMusicCategoryKeyBaseList(categoryId);
		
        newMusicCategory.setCategoryId(categoryId);
        newMusicCategory.setParentCategoryId(pCategoryId);
        newMusicCategory.setCategoryName(categoryName);
        newMusicCategory.setDesc(desc);
        newMusicCategory.setSortId(Integer.parseInt(sortId));
        newMusicCategory.setType(type);
        newMusicCategory.setCateType(cateType);
        newMusicCategory.setPlatForm(platForms);
        newMusicCategory.setCityId(citys);

        
        // 校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png","jpg","gif"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        
        try
        {
            if (null != request.getParameterValues("citys"))
            {
                // 查看当前变更的地域信息是否少于子货架的地域信息集合
                CategoryCityBO.getInstance()
                              .isHasNewMusicCity(categoryId,
                                                 request.getParameterValues("citys"));
            }

            // 判断变更的货架地域信息是否为父货架地域信息子集
            CategoryCityBO.getInstance()
                          .isHasNewMusicPCityIds(pCategoryId,
                                                 request.getParameterValues("citys"));
            Hashtable  files = fileForm.getMultipartRequestHandler().getFileElements();
	        FormFile uploadFile = (FormFile)files.get("albumPic");
		String resourcePicFilePath = ConfigFactory.getSystemConfig()
		.getModuleConfig("music139").getItemValue("DestAlbumFTPDir");
		String clearAlbumPic = request.getParameter("clear_albumPic");
		
//		 得到当前货架信息
		NewMusicCategoryVO   oldMusicCategoryVO = NewMusicCategoryBO.getInstance()
                                               .queryNewMusicCategoryVO(categoryId);

		newMusicCategory.setAlbumPic(oldMusicCategoryVO.getAlbumPic());
		
		if( clearAlbumPic != null && clearAlbumPic.equals("1")){	
			newMusicCategory.setAlbumPic("");
		}else{
			if(uploadFile.getFileName() != null && !uploadFile.getFileName().equals("")){
				/*String extfile = uploadFile.getFileName();
				if (!extfile.toUpperCase().endsWith("PNG"))
				{
					this.saveMessages(request, "音乐货架图片不是png格式");
					return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
				}*/
				 String albumPic = UploadFileKeyResUtil.getInstance().upLoadfileToResServer(uploadFile,resourcePicFilePath,"",categoryId,"");
				 newMusicCategory.setAlbumPic(albumPic);
			}
		}
            // 用于变更新音乐货架
            NewMusicCategoryBO.getInstance()
                              .updateNewMusicCategory(newMusicCategory);
            
            if (keyBaseList != null)
    		{
    			this.saveNewMusicCategoryKeyResource(keyBaseList,fileForm,categoryId,request);
    			
    		}
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "修改基地音乐货架信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "更新新音乐货架列表出错");

            if (e.getErrorCode() == RepositoryBOCode.UPDATE_CATEGORY_CITY)
            {
                this.saveMessages(request, "因变更货架地域适配信息时小于子货架地域信息集合而失败");
            }
            else if (e.getErrorCode() == RepositoryBOCode.ADD_CATEGORY_CITY)
            {
                this.saveMessages(request, "因变更货架地域适配信息时大于父货架地域信息集合而失败");
            }

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "更新新音乐货架成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "../newmusicsys/category_main.jsp");
        request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
        return mapping.findForward(forward);
    }
    
    /**
     * 
     *@desc 保存扩展字段
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveNewMusicCategoryKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//资源服务器本模块路径
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("basemusic").getItemValue("resServerPath");  	
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"category"); 
    	
    	NewMusicCategoryBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
}
