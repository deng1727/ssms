package com.aspire.ponaadmin.web.repository.web ;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.ContentBO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * <p>编辑分类信息的Action</p>
 * <p>action=new表示新增加;action=edit表示对原有分类进行修改</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentEditAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ContentEditAction.class) ;

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
        String contentID = this.getParameter(request, "contentID");

        Category category = CategoryBO.getInstance().getCategory(categoryID);
        if (categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
        {
            category.setName("未分类资源") ;
        }

        GContent content = null;
        List refCategoryList = new ArrayList();
        if(action.equals("new"))
        {
        	String value = request.getParameter("contentType");
        	request.setAttribute("contentType",value);
        }
        else
        {
            
            //找出内容信息
            Node node = Repository.getInstance().getNode(contentID);
            LOG.debug("the node type is :"+node.getType());
            content = (GContent)Repository.getInstance().getNode(contentID,node.getType());
            //找出内容所在的分类
            refCategoryList = ContentBO.getInstance().getContentCategory(contentID);
        }

        request.setAttribute("refCategoryList", refCategoryList) ;

        request.setAttribute("category", category);
        request.setAttribute("content", content);
        return mapping.findForward("edit");
    }
}
