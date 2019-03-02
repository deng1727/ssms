/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui.rule;

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

/**
 * @author x_wangml
 * 
 */
public class RuleQueryAction extends BaseAction
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(RuleQueryAction.class);

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

        // 从请求中获取条件信息
        String ruleId = this.getParameter(request, "ruleId").trim();
        String ruleName = this.getParameter(request, "ruleName").trim();
        String ruleType = this.getParameter(request, "ruleType");
        String intervalType = this.getParameter(request, "intervalType");

        PageResult page = new PageResult(request);

        page.setPageSize(12);

        // 根据获取的参数查询货架策略规则表信息
        RuleBO.getInstance().queryRuleList(page,
                                           ruleId,
                                           ruleName,
                                           ruleType,
                                           intervalType);
        // 将list放置到page中用于分页展示
        request.setAttribute("ruleId", ruleId);
        request.setAttribute("ruleName", ruleName);
        request.setAttribute("ruleType", ruleType);
        request.setAttribute("intervalType", intervalType);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }

}
