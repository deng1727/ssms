/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author x_wangml
 * 
 */
public class CategoryUpdateQueryAction extends BaseAction
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryUpdateQueryAction.class);

    /**
     * 查询货架策略规则表
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
        String forward = "list";

        // 从请求中获取货架的货架内码
        String cid = this.getParameter(request, "cid").trim();
        // 从请求中获取搜索条件货架对应的规则ID
        String ruleID = this.getParameter(request, "ruleId").trim();
        
        String cName = this.getParameter(request, "cName").trim();
        
        String ruleName = this.getParameter(request, "ruleName").trim();
        
        PageResult page = new PageResult(request);
        
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // 根据获取的参数查询货架策略规则表信息
        CategoryUpdateBO.getInstance().queryCategoryUpdateList(page,
                                                               cid,
                                                               ruleID,
                                                               cName,
                                                               ruleName);
        // 将list放置到page中用于分页展示
        request.setAttribute("cid", cid);
        request.setAttribute("ruleId", ruleID);
        request.setAttribute("cName", cName);
        request.setAttribute("ruleName", ruleName);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
}
