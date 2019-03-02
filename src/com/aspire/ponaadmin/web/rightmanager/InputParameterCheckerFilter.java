package com.aspire.ponaadmin.web.rightmanager;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.system.SystemInfo;

public class InputParameterCheckerFilter implements Filter
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(InputParameterCheckerFilter.class);

    /**
     * 未登录的页面入口
     */
    private static final String ERROR_PAGE = "/web/inputerror.html";

    /**
     * destroy
     * 
     * @todo Implement this javax.servlet.Filter method
     */
    public void destroy()
    {
    }

    /**
     * doFilter
     * 
     * @param servletRequest ServletRequest
     * @param servletResponse ServletResponse
     * @param filterChain FilterChain
     * @throws IOException
     * @throws ServletException
     * @todo Implement this javax.servlet.Filter method
     */
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException,
                    ServletException
    {
        HttpServletRequest req = ( HttpServletRequest ) servletRequest;
        HttpServletResponse rsp = ( HttpServletResponse ) servletResponse;
        String context = req.getContextPath();
        String uri = req.getRequestURI();
        String valueReg = "[\\w,\\u4e00-\\u9fa5 -:;=]+";

        // 这边用于获取web应用的上下文，这种方法比较土，不过暂时没有更好的办法。
        if (SystemInfo.getInstance().getWebAppContext() == null)
        {
            SystemInfo.getInstance().setWebAppContext(context);
        }

        if ((uri.indexOf("/web/notLogin.do") == -1)
            && (uri.indexOf("/web/login.do") == -1)
            && (uri.indexOf("web/userCenterLogin.do") == -1)
            && (uri.indexOf("/web/usermanager/register.do") == -1)
            && (uri.indexOf("/web/usermanager/registerInfo.do") == -1))
        {
            for (Iterator iter = req.getParameterMap().keySet().iterator(); iter.hasNext();)
            {
                String key = ( String ) iter.next();
                String value = req.getParameter(key);

                if(!key.equals("cgyPath") && !key.equals("backURL"))
                {
                    if (!"".equals(value) && !value.matches(valueReg))
                    {
                        // 用户输入非法字符
                        LOG.debug("用户输入非法字符！key='" + key + "', value='" + value + "'");
                        rsp.sendRedirect(context + ERROR_PAGE);
                        return;
                    }
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * init
     * 
     * @param filterConfig FilterConfig
     * @throws ServletException
     * @todo Implement this javax.servlet.Filter method
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

}
