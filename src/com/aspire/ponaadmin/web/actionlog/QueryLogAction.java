package com.aspire.ponaadmin.web.actionlog ;

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
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>查询操作日志的action类</p>
 * <p>通过关键字、时间进行查找</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class QueryLogAction  extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(QueryLogAction.class) ;

    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws BOException
     * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
     */
    public ActionForward doPerform (ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
        throws BOException
    {
    	LOG.debug("QueryLogAction()");
        String key = request.getParameter("key");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String result = request.getParameter("result")==null||request.getParameter("result").equals("")?"-1":request.getParameter("result");
        //把参数的格式由YYYY-MM-DD改变为YYYYMMDD
        if(startDate != null && !startDate.equals(""))
        {
            startDate = PublicUtil.replace(startDate, "-", "")+"000000";
        }
        if(endDate != null && !endDate.equals(""))
        {
            endDate = PublicUtil.replace(endDate, "-", "")+"240000";
        }
        //实现分页，列表对象是保存在分页对象PageResult中的。
        PageResult page = new PageResult(request, 9+9) ;
        ActionLogBO.getInstance().queryLog(page, key, startDate, endDate,result);
        request.setAttribute("PageResult", page);
        request.setAttribute("result",result);
        //把查询到的数据传递给页面
        String forward = "logList";
        return mapping.findForward(forward);
    }

}
