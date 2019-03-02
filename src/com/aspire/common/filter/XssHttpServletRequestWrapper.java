package com.aspire.common.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;
    private Map<String, String[]> params;
 
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }
 
    public XssHttpServletRequestWrapper(HttpServletRequest request,
            Map<String, String[]> newParams) {
        super(request);
        orgRequest = request;
        this.params = newParams;
    }
 
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = xssEncode(values[i]);
        }
        return encodedValues;
    }
 
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
 
        value = xssEncode(value);
 
        return value;
    }
 
    @Override
    public String getHeader(String name) {
 
        String value = super.getHeader(xssEncode(name));
        value = xssEncode(value);
        return value;
    }
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map getParameterMap() {
        return params;
    }
 

    private static String xssEncode(String s) {
        if (StringUtils.isBlank(s) || s.isEmpty()) {
            return s;
        }
        return XSSUtil.stripXSS(s);
    }
 

    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }
 

    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
 
        return req;
    }
}