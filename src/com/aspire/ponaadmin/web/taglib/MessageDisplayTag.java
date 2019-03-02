package com.aspire.ponaadmin.web.taglib ;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.ponaadmin.web.constant.ResourceUtil;

/**
 * @author dongxk
 * @version 
 */
public class MessageDisplayTag extends TagSupport
{

    /**
     */
    private static final JLogger LOG = LoggerFactory.getLogger(MessageDisplayTag.class);

    /**
     */
    private String key;//the key in resource

    /**
     */
    public MessageDisplayTag()
    {
    }

    /**
     * @return int
     * @throws javax.servlet.jsp.JspTagException 
     */
    public int doEndTag()
        throws JspTagException
    {

        JspWriter jspwriter = pageContext.getOut();
        String msg = "";
        if ((key != null) && !key.equals(""))
        {
            msg = ResourceUtil.getResource(key);
        }
        try
        {
            jspwriter.write(msg);
        } catch(IOException ex)
        {
            LOG.error("Error in MessageDisplayTag", ex);
        }
        return EVAL_PAGE;//OK
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}
