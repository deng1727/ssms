/*
 * 文件名：CategoryDeviceAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.repository.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.CityVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.repository.CategoryCityBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class NewMusicCategoryCityAction extends BaseAction
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(NewMusicCategoryCityAction.class) ;
    
    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
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
        if(isFirst.equals("1"))//第一次不需要查询
        {
            page.excute(new ArrayList(0));
            request.setAttribute("notice", "请输入条件查询");
        }else
        {
            CityVO city = new CityVO();
            city.setCityName(cityName);
            city.setPvcName(pvcName);
            
            CategoryCityBO.getInstance().queryNewMusicCityList(page, city, categoryID, pCategoryID);
        }
        
        
        // 将list放置到page中用于分页展示
        request.setAttribute("cityName", cityName);
        request.setAttribute("pvcName", pvcName);
        request.setAttribute("categoryID", categoryID);
        request.setAttribute("pCategoryID", pCategoryID);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }

}
