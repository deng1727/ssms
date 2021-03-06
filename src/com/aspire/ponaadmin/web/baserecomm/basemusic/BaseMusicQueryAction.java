/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basemusic;

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
public class BaseMusicQueryAction extends BaseAction
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseMusicQueryAction.class);

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

        // 从请求中获取基地音乐查询条件
        String songName = this.getParameter(request, "songName").trim();
        String singer = this.getParameter(request, "singer").trim();

        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // 根据获取的参数查询基地音乐表信息
        BaseRecommBO.getInstance().queryBaseMusicList(page, songName, singer);

        // 将list放置到page中用于分页展示
        request.setAttribute("songName", songName);
        request.setAttribute("singer", singer);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
}
