/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basebook;

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
import com.aspire.ponaadmin.web.baserecomm.BaseRecommBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author x_wangml
 * 
 */
public class BaseBookQueryAction extends BaseAction
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseBookQueryAction.class);

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

        // �������л�ȡ����ͼ���ѯ����
        String bookName = this.getParameter(request, "bookName").trim();
        String authorName = this.getParameter(request, "authorName").trim();
        String key = this.getParameter(request, "key").trim();
        String bookType = this.getParameter(request, "bookType").trim();
        String bookDesc = this.getParameter(request, "bookDesc").trim();

        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
        
        BaseBookVO vo = new BaseBookVO();
        vo.setBookName(bookName);
        vo.setAuthorName(authorName);
        vo.setKey(key);
        vo.setBookType(bookType);
        vo.setBookDesc(bookDesc);

        // ���ݻ�ȡ�Ĳ�����ѯ����ͼ�����Ϣ
        BaseRecommBO.getInstance().queryBaseBookList(page, vo);

        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("bookName", bookName);
        request.setAttribute("authorName", authorName);
        request.setAttribute("key", key);
        request.setAttribute("bookDesc", bookDesc);
        request.setAttribute("bookType", bookType);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
}
