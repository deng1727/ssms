package com.aspire.ponaadmin.web.taglib;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;
import com.aspire.ponaadmin.web.util.StringTool;

/**
 * <p>Title: www_cms</p>
 * <p>Description: www_cms program file</p>
 * <p>Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * <p>@author maobg</p>
 *
 * @version 1.0.0.0
 * @author dongxk
 */

public class StrWrapTag extends TagSupport
{

    /**
     * Name of the bean that contains the data we will be rendering.
     */
    protected String name = null;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Name of the property to be accessed on the specified bean.
     */
    protected String property = null;

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Ìî²¹×Ö·û´®
     * value to String.
     */
    protected String padStr = null;

    public String getPadStr() {
        return this.padStr;
    }

    public void setPadStr(String padStr) {
        this.padStr = padStr;
    }

    /**
     * ½Ø¶ÏµÄ³¤¶È
     */
    protected int wraplen = -1;

    public int getWraplen() {
        return this.wraplen;
    }

    public void setWraplen(int wraplen) {
        this.wraplen = wraplen;
    }

    /**
     * The scope to be searched to retrieve the specified bean.
     */
    protected String scope = null;

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Process the start tag.
     *
     * @exception JspException if a JSP exception has occurred
     * @return int
     */
    public int doStartTag() throws JspException {
        // Look up the requested property value
        Object value =
        	TagUtils.getInstance().lookup(pageContext, name, property, scope);
        if (value == null)
            return SKIP_BODY;  // Nothing to output

        // Convert value to the String with some formatting
        String output = "";
        if (value instanceof String)
            output = StringTool.formatByLen((String) value, wraplen, padStr);
        else
            output = value.toString();

        TagUtils.getInstance().write(pageContext, output);

        // Continue processing this page
        return SKIP_BODY;

    }

    /**
     * Release all allocated resources.
     */
    public void release() {

        super.release();
        name = null;
        property = null;
        scope = null;
        padStr = null;
        wraplen = -1;
    }

}
