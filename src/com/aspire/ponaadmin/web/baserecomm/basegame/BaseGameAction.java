/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basegame;


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
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author x_wangml
 * 
 */
public class BaseGameAction extends BaseAction
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseGameAction.class);

    private String DATATYPE = "baseGame";

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }

        // 从请求中获取操作类型
        String action = this.getParameter(request, "actionType").trim();

        if ("add".equals(action))
        {
            return add(mapping, form, request, response);
        }
        if ("del".equals(action))
        {
            return del(mapping, form, request, response);
        }
        if ("toFile".equals(action))
        {
            return toFile(mapping, form, request, response);
        }
        else
        {
            return query(mapping, form, request, response);
        }

    }

    /**
     * 把基地游戏数据存入会话中
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
    {
        // 从请求中获取基地游戏查询条件
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 基地游戏id列表
        String gameId[] = request.getParameterValues("selectGame");

        // 如果集合为空时
        if (null == gameId && gameId.length == 0)
        {
            this.saveMessagesValue(request, "没有可加入的基地游戏数据！");
            return mapping.findForward(forward);
        }

        // 数据存入临时表中
        try
        {
            BaseRecommBO.getInstance().addBaseData(gameId, DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // 返回信息
        this.saveMessagesValue(request, "新增基地游戏信息成功！");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseGameQuery.do");
        return mapping.findForward(forward);
    }

    /**
     * 查询基地游戏数据临时表信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException 
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }

        String forward = "list";

        // 从请求中获取基地游戏查询条件
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // 根据获取的参数查询基地游戏表信息
        BaseRecommBO.getInstance().queryBaseDateTempList(page,DATATYPE);

        // 将list放置到page中用于分页展示
        request.setAttribute("PageResult", page);
        
        return mapping.findForward(forward);
    }

    /**
     * 把基地游戏数据从临时表中删除
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward del(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
    {
        // 从请求中获取基地游戏查询条件
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 基地游戏id列表
        String gameId[] = request.getParameterValues("selectGame");

        // 数据存入临时表中
        try
        {
            BaseRecommBO.getInstance().delBaseData(gameId, DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // 返回信息
        this.saveMessagesValue(request, "删除基地游戏信息成功！");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseGameTemp.do");
        return mapping.findForward(forward);
    }
    
    /**
     * 把基地游戏数据导出成文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward toFile(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
    {
        // 从请求中获取基地游戏查询条件
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 数据存入临时表中
        try
        {
            BaseRecommBO.getInstance().toFile(DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // 返回信息
        this.saveMessagesValue(request, "导出基地游戏推荐文件信息成功！");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseGameTemp.do");
        return mapping.findForward(forward);
    }
}
