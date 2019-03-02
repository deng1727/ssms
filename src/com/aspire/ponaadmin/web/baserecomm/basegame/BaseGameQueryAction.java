/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basegame;


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
import com.aspire.ponaadmin.web.baserecomm.BaseRecommBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author x_wangml
 * 
 */
public class BaseGameQueryAction extends BaseAction
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseGameQueryAction.class);

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

        // 从请求中获取基地游戏查询条件
        String gameName = this.getParameter(request, "gameName").trim();
        String gameDesc = this.getParameter(request, "gameDesc").trim();
        String isFirst = this.getParameter(request, "isFirst").trim();

        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // 根据获取的参数查询基地游戏表信息
        if (isFirst.equals("1"))// 第一次不需要查询
        {
            page.excute(new ArrayList(0));
            request.setAttribute("notice", "请输入条件查询");
        }
        else
        {
            BaseRecommBO.getInstance()
                      .queryBaseGameList(page, gameName, gameDesc);
        }

        // 将list放置到page中用于分页展示
        request.setAttribute("gameName", gameName);
        request.setAttribute("gameDesc", gameDesc);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
}
