package com.aspire.ponaadmin.web.repository.web ;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryCityBO;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.repository.CategoryDeviceBO;
import com.aspire.ponaadmin.web.repository.CategoryPlatFormBO;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.DateUtil;

/**
 * <p>编辑货架信息的Action</p>
 * <p>action=new表示新增加;action=edit表示对原有货架进行修改</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CategoryEditAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryEditAction.class) ;

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
    public ActionForward doPerform (ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
        throws BOException
    {
        LOG.debug("doPerform()");
        String action = this.getParameter(request, "action");
        String categoryID = this.getParameter(request, "categoryID");
        String pCategoryID = this.getParameter(request, "pCategoryID");
        String cgyPath=this.getParameter(request, "cgyPath");
        String platFormName="";
        String cityName="";
        String deviceName = "";
        List deviceList = null;
        List platFormList = null;
        List cityList = null;
        //如果没有传id过来，默认查询出跟节点
        Category category = null;
        Category pCategory = null;
        if(categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
        {
        	pCategory=( Category ) Repository.getInstance()
			   .getNode(RepositoryConstants.ROOT_CATEGORY_ID,
                       RepositoryConstants.TYPE_CATEGORY);
        }else
        {
        	 pCategory = ( Category ) Repository.getInstance()
			   .getNode(pCategoryID,
                      RepositoryConstants.TYPE_CATEGORY);
        }
       
        if(action.equals("new"))
        {

            category = new Category();
            category.setName("");
            category.setDesc("");
            category.setCityId("");
            category.setPlatForm("");
            category.setCtype(pCategory.getCtype());
            category.setDeviceCategory(0);
            category.setSortID(0);
            category.setState(1);//新增默认在门户展示
            category.setOthernet("1");
            //默认新增货架的关联门店属性与父货架相同
            category.setRelation(pCategory.getRelation());
            String startDate = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
            category.setStartDate(startDate);
            category.setEndDate("2099-01-01");
            
        }
        else
        {
            category = CategoryBO.getInstance().getCategory(categoryID);
            
            if(1 == category.getDeviceCategory())
            {
                deviceName = CategoryDeviceBO.getInstance().queryDeviceByCategoryId(categoryID);
                deviceList = CategoryDeviceBO.getInstance().queryDeviceList(categoryID);
            }
            if(category.getStartDate() == null ||category.getStartDate().equals("")){
            	
            	 String startDate = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
                 category.setStartDate(startDate);
                 
            } if(category.getEndDate() == null ||category.getEndDate().equals("")){
            	 category.setEndDate("2099-01-01");
            }
           
            
            
            platFormName = CategoryPlatFormBO.getInstance().queryPlatFormByPlatFormIds(category.getPlatForm());
            platFormList = CategoryCityBO.getInstance().queryListByCityId(category.getPlatForm());
            
            cityName = CategoryCityBO.getInstance().queryCityByCityIds(category.getCityId());
            cityList = CategoryCityBO.getInstance().queryListByCityId(category.getCityId());
        }
        String subRelation=null;
        try
		{
        	subRelation=CategoryDAO.getInstance().getSubCategoryRelation(categoryID);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//List keyResourceList = category.getAllKeyResourceByCid();
		List keyResourceList =   CategoryBO.getInstance().queryCategoryKeyBaseList(category.getCategoryID());
		
		if(keyResourceList == null){
			keyResourceList = new ArrayList();
		}
		request.setAttribute("cgyPath", cgyPath);
        request.setAttribute("category", category);
        request.setAttribute("pCategory", pCategory);
        request.setAttribute("subRelation", subRelation);
        request.setAttribute("deviceName", deviceName);
        request.setAttribute("deviceList", deviceList);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("platFormList", platFormList);
        request.setAttribute("cityName", cityName);
        request.setAttribute("cityList", cityList);
        request.setAttribute("relation", category.getRelation());
        request.setAttribute("prelation", pCategory.getRelation());//add  by tungke 
        
       // request.setAttribute("keyBaseList", category.getKeyBase());//add  by tungke 
        request.setAttribute("keyResourceList", keyResourceList);//add  by tungke
        
        return mapping.findForward("edit");
    }
}
