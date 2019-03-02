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
     * ��־����
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

        // �������л�ȡ������Ϣ
        String ruleId = this.getParameter(request, "ruleId").trim();
        String ruleName = this.getParameter(request, "ruleName").trim();
        String ruleType = this.getParameter(request, "ruleType");
        String intervalType = this.getParameter(request, "intervalType");

        PageResult page = new PageResult(request);

        page.setPageSize(12);

        // ���ݻ�ȡ�Ĳ�����ѯ���ܲ��Թ������Ϣ
        RuleBO.getInstance().queryRuleList(page,
                                           ruleId,
                                           ruleName,
                                           ruleType,
                                           intervalType);
        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("ruleId", ruleId);
        request.setAttribute("ruleName", ruleName);
        request.setAttribute("ruleType", ruleType);
        request.setAttribute("intervalType", intervalType);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }

}
