/*
 * 
 */
package com.aspire.ponaadmin.web.channeladmin.action;

import java.util.ArrayList;

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
import com.aspire.ponaadmin.web.channeladmin.bo.CategoryQueryBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;


/**
 * @author x_wangml
 *
 */
public class CategoryQueryAction extends BaseAction
{
    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryQueryAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }
        String forward = "list";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String categoryName = this.getParameter(request, "categoryName").trim();
        String isFirst = this.getParameter(request, "isFirst").trim();
        
        PageResult page = new PageResult(request);
        
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // ���ݻ�ȡ�Ĳ�����ѯ���ܲ��Թ������Ϣ
        if(isFirst.equals("1"))//��һ�β���Ҫ��ѯ
        {
        	page.excute(new ArrayList(0));
        	request.setAttribute("notice", "������������ѯ");
        }else
        {
        	CategoryQueryBO.getInstance().queryCategoryList(page, categoryId, categoryName);
        }
        
        
        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
}
