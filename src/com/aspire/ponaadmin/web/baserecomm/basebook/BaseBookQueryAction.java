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
     * 日志引用
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

        // 从请求中获取基地图书查询条件
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

        // 根据获取的参数查询基地图书表信息
        BaseRecommBO.getInstance().queryBaseBookList(page, vo);

        // 将list放置到page中用于分页展示
        request.setAttribute("bookName", bookName);
        request.setAttribute("authorName", authorName);
        request.setAttribute("key", key);
        request.setAttribute("bookDesc", bookDesc);
        request.setAttribute("bookType", bookType);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
}
