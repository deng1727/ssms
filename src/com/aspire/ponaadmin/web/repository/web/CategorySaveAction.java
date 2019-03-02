
package com.aspire.ponaadmin.web.repository.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryCityBO;
import com.aspire.ponaadmin.web.repository.CategoryDeviceBO;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.system.SystemConfig;

/**
 * <p>
 * 保存分类信息的Action
 * </p>
 * <p>
 * categoryID有表示修改，没有表示新增
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CategorySaveAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryBO.class);

    /**
     * 
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws BOException
     * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("doPerform()");
        }
//        DataImportForm iForm = ( DataImportForm ) form;
//        FormFile uploadFile = iForm.getDataFile();

//      扩展字段添加
		FileForm fileForm = (FileForm) form;
		Hashtable  files = fileForm.getMultipartRequestHandler().getFileElements();
		/**
		 * 审批标识。当approvalFlag==yes时。表示该操作需要经过审批。
		 */
		String approvalFlag = this.getParameter(request, "approvalFlag");
		
		String categorypic = ConfigFactory.getSystemConfig()
		.getModuleConfig("ssms").getItemValue("categorypic");  
		
        FormFile uploadFile = (FormFile)files.get(categorypic);
        //fileForm.getMultipartRequestHandler().getFileElements().remove("category_pic");
        
        String categoryID = this.getParameter(request, "categoryID");
        String pCategoryID = this.getParameter(request, "pCategoryID");
        String name = this.getParameter(request, "name");
        String desc = this.getParameter(request, "desc");
        String cgyPath = this.getParameter(request, "cgyPath");
        String ctype = this.getParameter(request, "ctype");
        String deviceCategory = this.getParameter(request, "deviceCategory");
        String platForm = formatListToSearchString(request.getParameterValues("platForms"));
        String[] citys = request.getParameterValues("citys");
        String city = formatListToSearchString(citys);
        String sortId =  this.getParameter(request, "sortID");
        String startDate =  this.getParameter(request, "startDate");
        String endDate =  this.getParameter(request, "endDate");
        
        String multiurl =  this.getParameter(request, "multiurl");
        String othernet = this.getParameter(request, "othernet");
        
//      校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png","jpg","gif"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 门户是否显示状态值
        String state = this.getParameter(request, "state");
        if (state.equals(""))
        {
            state = "1";
        }

        int intstate = Integer.valueOf(state).intValue();

        // 获取关联门店属性
        String[] relation = request.getParameterValues("relation");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; (relation != null) && (i < relation.length); i++)
        {
            sb.append(relation[i]);
            if (i < relation.length - 1)
            {
                sb.append(",");
            }
        }
        String forward = null;

        String actionType = "";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
        try
        {
            if ("".equals(categoryID))// 新增
            {
                actionType = "增加货架";
                
                // 判断新增货架地域信息是否为父货架地域信息子集
                CategoryCityBO.getInstance().isHasPCityIds(pCategoryID, citys);
                
                actionTarget = name;
                Category category = new Category();
                category.setName(name);
                category.setDesc(desc);
                category.setRelation(sb.toString());
                
                if("0".equals(sortId))
        		{
                	category.setSortID(SEQCategoryUtil.getInstance()
        					.getSEQByCategoryType(1));
        		}
        		else
        		{
        			category.setSortID(Integer.valueOf(sortId).intValue());
        		}
                
                
                category.setCtype(0);
                category.setChangeDate(new Date());
                category.setDelFlag(0);
               // category.setDelFlag(0);
                category.setState(intstate);//add by dongke 20110428
                category.setDeviceCategory(Integer.parseInt(deviceCategory));
                category.setCityId(city);
                category.setPlatForm(platForm);

                category.setStartDate(startDate);
                category.setEndDate(endDate);
                
                category.setMultiurl(multiurl);// add by aiyan 2011-11-11
                category.setOthernet(othernet);// add by wml 2011-12-12
                if("yes".equals(approvalFlag)){
                	category.setClassifyStatus("0");
                	category.setGoodsStatus("0");
                }else{
                	category.setClassifyStatus("1");
                }
                String newCategoryID = CategoryTools.createCategory(pCategoryID,
                                                                    category);
                
                List keyBaseList =CategoryBO.getInstance().queryCategoryKeyBaseList(category.getCategoryID());

                //特殊处理一变四的图片
                for(int t = 0 ; t < keyBaseList.size();t ++){
                	ResourceVO vo =  (ResourceVO)keyBaseList.get(t);
                	if(vo.getKeyname().equals(categorypic)){
                		keyBaseList.remove(t);
                		break;
                	}
                }
                ////扩展属性--图片
                String catepicURL = null;
                if (!"".equals(uploadFile.getFileName()))
                {
                	catepicURL = CategoryBO.getInstance()
                                       .uploadCatePicURL(uploadFile,
                                    		   newCategoryID);
                	
                	category.setKeyValue(categorypic,catepicURL);
                }

                if ("11".equals(ctype))
                {
                    String picURL = null;
                    if (!"".equals(uploadFile.getFileName()))
                    {
                        picURL = CategoryBO.getInstance()
                                           .uploadCatePicURL(uploadFile,
                                                             newCategoryID);
                    }
                    category = ( Category ) Repository.getInstance()
                                                      .getNode(newCategoryID,
                                                               RepositoryConstants.TYPE_CATEGORY);
                    category.setPicURL(picURL);
                    category.setCtype(11);
                    if("yes".equals(approvalFlag)){
                    	category.setClassifyStatus("0");
                    }else{
                    	category.setClassifyStatus("1");
                    }
                    category.save();
                }

                // 如果定义为机型货架,添加货架对应机型信息
                if ("1".equals(deviceCategory))
                {
                    String[] devices = request.getParameterValues("devices");
                    CategoryDeviceBO.getInstance()
                                    .saveDeviceToCategory(newCategoryID,
                                                          devices);
                }
                if (keyBaseList != null)
        		{
        			this.saveCategoryKeyResource(keyBaseList,fileForm,category.getCategoryID(),request);
        			
        		}
                this.saveMessages(request, "RESOURCE_CATE_RESULT_001");
                cgyPath = cgyPath + ">>" + name;
                request.setAttribute(Constants.PARA_GOURL,
                                     "../../web/resourcemgr/categoryInfo.do?categoryID="
                                                     + newCategoryID
                                                     + "&cgyPath=" + cgyPath);
                request.setAttribute(Constants.PARA_REFRESH_TREE_KEY,
                                     pCategoryID);
                
              
                
                forward = Constants.FORWARD_COMMON_SUCCESS;
                //如果不用审批。则要通知到数据中心
                if(!"yes".equals(approvalFlag)){
                	 //这里加点消息动作,就是这个新建货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
    				ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
    				String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
    				String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
                    if(SSMSDAO.getInstance().isAndroidCategory(newCategoryID, rootCategoryId)
                    ||SSMSDAO.getInstance().isOperateCategory(newCategoryID, operateCategoryId)){//modify by aiyan 2013-05-18	 ){
//                    	Catogoryid	必须	String	货架ID
//                    	Action	必须	String	0：新建
//                    	1：货架描述信息变更（包含扩展字段）
//                    	9：删除
//                    	Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。

                    	try {
    						SSMSDAO.getInstance().addMessages(MSGType.CatogoryModifyReq, newCategoryID+":0");
    					} catch (DAOException e) {
    						// TODO Auto-generated catch block
    						LOG.error("新增货架记录消息是出错，新货架ID:"+newCategoryID,e);					
    					}
                    }
                }
               
            }
            else
            // 是修改
            {
                actionType = "修改货架";
                actionTarget = name;
                Category category = ( Category ) Repository.getInstance()
                                                           .getNode(categoryID,
                                                                    RepositoryConstants.TYPE_CATEGORY);
                // 为空。为全部应用,如果不为空
                if(null != citys)
                {
                    // 查看当前变更的地域信息是否少于子货架的地域信息集合 
                    CategoryCityBO.getInstance().isHasCity(category.getCategoryID(), citys);
                }
                
                if ("11".equals(ctype))
                {
                    if (!"".equals(uploadFile.getFileName()))
                    {
                        String picURL = CategoryBO.getInstance()
                                                  .uploadCatePicURL(uploadFile,
                                                                    category.getId());
                        category.setPicURL(picURL);
                    }
                }
                // 如果定义为机型货架,添加货架对应机型信息
                if ("0".equals(deviceCategory))
                {
                    CategoryDeviceBO.getInstance()
                                    .delDeviceToCategory(categoryID);
                }
                else if ("1".equals(deviceCategory))
                {
                    String[] devices = request.getParameterValues("devices");
                    // 如果没进行重新选择机型。保持原值不变
                    if (devices != null)
                    {
                        CategoryDeviceBO.getInstance()
                                        .delDeviceToCategory(categoryID);
                        CategoryDeviceBO.getInstance()
                                        .saveDeviceToCategory(categoryID,
                                                              devices);
                    }
                }

                
                category.setClassifyStatus("0");
                category.setName(name);
                category.setDesc(desc);
                category.setState(intstate);
                category.setDeviceCategory(Integer.parseInt(deviceCategory));
                category.setCityId(city);
                category.setPlatForm(platForm);
                category.setSortID(Integer.valueOf(sortId).intValue());
                category.setRelation(sb.toString());
                category.setChangeDate(new Date());// add by wml 2013-07-25
                category.setStartDate(startDate);
                category.setEndDate(endDate);
                
                category.setMultiurl(multiurl);// add by aiyan 2011-11-11
                category.setOthernet(othernet);// add by wml 2011-12-12
                
                List keyBaseList = CategoryBO.getInstance().queryCategoryKeyBaseList(category.getCategoryID());

//              特殊处理一变四的图片
                for(int t = 0 ; t < keyBaseList.size();t ++){
                	ResourceVO vo =  (ResourceVO)keyBaseList.get(t);
                	if(vo.getKeyname().equals(categorypic)){
                		keyBaseList.remove(t);
                		break;
                	}
                }
                
                ////扩展属性--图片
                String catepicURL = null;
                if (!"".equals(uploadFile.getFileName()))
                {
                	catepicURL = CategoryBO.getInstance()
                                       .uploadCatePicURL(uploadFile,
                                    		   categoryID);
                	
                	category.setKeyValue(categorypic,catepicURL);
                }
                if (keyBaseList != null)
        		{
        			this.saveCategoryKeyResource(keyBaseList,fileForm,category.getCategoryID(),request);
        			
        		}
                if("yes".equals(approvalFlag)){
                	category.setClassifyStatus("0");
                }else{
                	category.setClassifyStatus("1");
                }
                CategoryBO.getInstance().modCategory(pCategoryID, category);
                this.saveMessages(request, "RESOURCE_CATE_RESULT_003");
                request.setAttribute(Constants.PARA_GOURL,
                                     "../../web/resourcemgr/categoryInfo.do?categoryID="
                                                     + categoryID + "&cgyPath="
                                                     + cgyPath);
                request.setAttribute(Constants.PARA_REFRESH_TREE_KEY,
                                     pCategoryID);
               
                forward = Constants.FORWARD_COMMON_SUCCESS;
                if(!"yes".equals(approvalFlag)){
                	//这里加点消息动作,就是这个修改货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
    				ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
    				String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
    				String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
                    if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
                       ||SSMSDAO.getInstance().isOperateCategory(categoryID, operateCategoryId)){//modify by aiyan 2013-05-18	 )){
//                    	Catogoryid	必须	String	货架ID
//                    	Action	必须	String	0：新建
//                    	1：货架描述信息变更（包含扩展字段）
//                    	9：删除
//                    	Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。

                    	try {
    						SSMSDAO.getInstance().addMessages(MSGType.CatogoryModifyReq, category.getId()+":1");
    					} catch (DAOException e) {
    						// TODO Auto-generated catch block
    						LOG.error("修改货架记录消息是出错，被修改的货架ID:"+category.getId(),e);					
    					}
                    }
                }
                
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            // 如果是分类名称已经存在，要提示不同的错误信息
            if (e.getErrorCode() == RepositoryBOCode.CATEGORY_NAME_EXISTED)
            {
                actionDesc = "货架名称重复";
                this.saveMessages(request, "RESOURCE_CATE_BO_CHECK_001");
            }
            // add by tungke 20090911
            else if (e.getErrorCode() == RepositoryBOCode.CATEGORY_RELATION_PARERR)
            {
                actionDesc = "无法添加父货架不包含的关联门店,必须先添加父货架上该关联门店";
                this.saveMessages(request, "RESOURCE_CATE_BO_CHECK_003");
            }
            else if (e.getErrorCode() == RepositoryBOCode.CATEGORY_RELATION_SUBERR)
            {
                actionDesc = "无法删除子货架包含的关联门店,必须先删除子货架上该关联门店";
                this.saveMessages(request, "RESOURCE_CATE_BO_CHECK_004");
            }
            else if (e.getErrorCode() == RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD)
            {
                actionDesc = "图片上传失败";
                this.saveMessages(request, "RESOURCE_CATE_BO_UPLOAD_1010");
            }
            else if (e.getErrorCode() == RepositoryBOCode.CATEGORY_DEVICE)
            {
                actionDesc = "设计货架关联机型失败";
                this.saveMessages(request, "RESOURCE_CATEGORY_DEVICE_1020");
            }
            else if (e.getErrorCode() == RepositoryBOCode.DEL_CATEGORY_DEVICE)
            {
                actionDesc = "删除货架关联机型失败";
                this.saveMessages(request, "RESOURCE_DEL_CATEGORY_DEVICE_1021");
            }
            else if (e.getErrorCode() == RepositoryBOCode.UPDATE_CATEGORY_CITY)
            {
                actionDesc = "因变更货架地域适配信息时小于子货架地域信息集合而失败";
                this.saveMessages(request, "RESOURCE_UPDATE_CATEGORY_CITY_1022");
            }
            else if (e.getErrorCode() == RepositoryBOCode.ADD_CATEGORY_CITY)
            {
                actionDesc = "因新增货架地域适配信息时大于父货架地域信息集合而失败";
                this.saveMessages(request, "RESOURCE_ADD_CATEGORY_CITY_1023");
            }
            else
            {
                this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM);
            }
            actionResult = false;
            forward = Constants.FORWARD_COMMON_FAILURE;
        }
        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        return mapping.findForward(forward);
    }
    
    /**
     * 用于把数组信息组合成数据库中为了搜索应用的格式
     * @param valueList
     * @return ｛｝，｛｝
     */
    private String formatListToSearchString(String[] valueList)
    {
        StringBuffer sb = new StringBuffer();
        
        // 如果为空。为全部应用
        if(null == valueList)
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
        sb.delete(sb.length()-1, sb.length());
        
        return sb.toString();
    }
    
    /**
     * 
     *@desc 保存扩展字段
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveCategoryKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//资源服务器本模块路径
    	String resServerPath = SystemConfig.FTPDIR_CATEGORYPICURL;  	
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"category"); 
    	
    	CategoryBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
}
