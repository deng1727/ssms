package com.aspire.ponaadmin.web.system;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author shidr
 * @version 
 */
public class SetCharacterEncodingFilter implements Filter {

    /**
     * The default character encoding to set for requests that pass through
     * this filter.
     */
    protected String encoding = null;

    /**
     * The filter configuration object we are associated with.  If this value
     * is null, this filter instance is not currently configured.
     */
    protected FilterConfig filterConfig = null;

    /**
     * Should a character encoding specified by the client be ignored?
     */
    protected boolean ignore = true;

    /**
     * Take this filter out of service.
     */
    public void destroy() {

        this.encoding = null;
        this.filterConfig = null;

    }

    /**
     * Select and set (if specified) the character encoding to be used to
     * interpret request parameters for this request.
     *
     * @param request http request
     * @param response  http response
     * @param chain filter chain
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
        // Conditionally select and set the character encoding to be used
        if (ignore)
        {
            request.setCharacterEncoding(this.encoding);
        }
        // Pass control on to the next filter
        chain.doFilter(request, response);

    }

    /**
     * Place this filter into service.
     *
     * @param filterConfig The filter configuration object
     * @throws javax.servlet.ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        System.out.println("encoding="+this.encoding);
        System.out.println("ignore="+this.ignore);
        if (value == null)
        {
            this.ignore = true;
        }
        else if (value.equalsIgnoreCase("true"))
        {
            this.ignore = true ;
        }
        else if (value.equalsIgnoreCase("yes"))
        {
            this.ignore = true ;
        }
        else
        {
            this.ignore = false ;
        }
    }
}
