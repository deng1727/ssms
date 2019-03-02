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
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryUpdateQueryAction.class);

    /**
     * ��ѯ���ܲ��Թ����
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

        // �������л�ȡ���ܵĻ�������
        String cid = this.getParameter(request, "cid").trim();
        // �������л�ȡ�����������ܶ�Ӧ�Ĺ���ID
        String ruleID = this.getParameter(request, "ruleId").trim();
        
        String cName = this.getParameter(request, "cName").trim();
        
        String ruleName = this.getParameter(request, "ruleName").trim();
        
        PageResult page = new PageResult(request);
        
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // ���ݻ�ȡ�Ĳ�����ѯ���ܲ��Թ������Ϣ
        CategoryUpdateBO.getInstance().queryCategoryUpdateList(page,
                                                               cid,
                                                               ruleID,
                                                               cName,
                                                               ruleName);
        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("cid", cid);
        request.setAttribute("ruleId", ruleID);
        request.setAttribute("cName", cName);
        request.setAttribute("ruleName", ruleName);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
}
