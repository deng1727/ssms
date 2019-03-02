package com.aspire.ponaadmin.web.channelUser.action ;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryCityBO;
import com.aspire.ponaadmin.web.repository.CategoryDeviceBO;
import com.aspire.ponaadmin.web.repository.CategoryPlatFormBO;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * <p>�鿴������Ϣ��Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CategoryInfoAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryInfoAction.class) ;

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
    	if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "�û�δ��½���ߵ�½�ѳ�ʱ��");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
        LOG.debug("doPerform()");
        String categoryID = this.getParameter(request, "categoryID");
        String cgyPath=this.getParameter(request, "cgyPath");
        String deviceName="";
        String platFormName="";
        String cityName="";

        //���û�д�id������Ĭ�ϲ�ѯ�����ڵ�
        if(categoryID.equals(""))
        {
            categoryID = RepositoryConstants.ROOT_CATEGORY_ID;
        }
        
        Category category = CategoryBO.getInstance().getCategory(categoryID);
        
        // ����ǻ��ͻ���
        if(1 == category.getDeviceCategory())
        {
            deviceName = CategoryDeviceBO.getInstance().queryDeviceByCategoryId(categoryID);
        }
        cityName = CategoryCityBO.getInstance().queryCityByCityIds(category.getCityId());
        platFormName = CategoryPlatFormBO.getInstance().queryPlatFormByPlatFormIds(category.getPlatForm());
        
        List keyResourceList =   CategoryBO.getInstance().queryCategoryKeyBaseList(category.getCategoryID());
		
        
        request.setAttribute("category", category);
        request.setAttribute("cgyPath", cgyPath);
        request.setAttribute("deviceName", deviceName);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("cityName", cityName);
        request.setAttribute("keyResourceList", keyResourceList);//add  by tungke 
        
        return mapping.findForward("show");
    }
}
