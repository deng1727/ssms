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
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author x_wangml
 * 
 */
public class BaseBookAction extends BaseAction
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseBookAction.class);

    private String DATATYPE = "baseBook";

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
     * 把基地图书数据存入会话中
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
        // 从请求中获取基地图书查询条件
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 基地图书id列表
        String bookId[] = request.getParameterValues("selectBook");

        // 如果集合为空时
        if (null == bookId && bookId.length == 0)
        {
            this.saveMessagesValue(request, "没有可加入的基地图书数据！");
            return mapping.findForward(forward);
        }

        // 数据存入临时表中
        try
        {
            BaseRecommBO.getInstance().addBaseData(bookId, DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // 返回信息
        this.saveMessagesValue(request, "新增基地图书信息成功！");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseBookQuery.do");
        return mapping.findForward(forward);
    }

    /**
     * 查询基地图书数据临时表信息
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

        // 从请求中获取基地图书查询条件
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // 根据获取的参数查询基地图书表信息
        BaseRecommBO.getInstance().queryBaseDateTempList(page,DATATYPE);

        // 将list放置到page中用于分页展示
        request.setAttribute("PageResult", page);
        
        return mapping.findForward(forward);
    }

    /**
     * 把基地图书数据从临时表中删除
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
        // 从请求中获取基地音乐查询条件
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 基地音乐id列表
        String musicId[] = request.getParameterValues("selectBook");

        // 数据存入临时表中
        try
        {
            BaseRecommBO.getInstance().delBaseData(musicId, DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // 返回信息
        this.saveMessagesValue(request, "删除基地图书信息成功！");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseBookTemp.do");
        return mapping.findForward(forward);
    }
    
    /**
     * 把基地图书数据导出成文件
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
        // 从请求中获取基地图书查询条件
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
        this.saveMessagesValue(request, "导出基地图书推荐文件信息成功！");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseBookTemp.do");
        return mapping.findForward(forward);
    }
}
