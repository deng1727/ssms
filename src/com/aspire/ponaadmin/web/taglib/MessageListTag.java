package com.aspire.ponaadmin.web.taglib ;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.ponaadmin.web.constant.Constants;

/**
 * @author dongxk
 * @version 
 */
public class MessageListTag extends TagSupport
{

    /**
     */
    private static final JLogger LOG = LoggerFactory.getLogger(MessageListTag.class);

    /**
     */
    public MessageListTag()
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
        StringBuffer str = new StringBuffer();
        Object obj = pageContext.getAttribute(Constants.REQ_KEY_MESSAGE, PageContext.REQUEST_SCOPE);
        if (obj != null)
        {
            if (obj instanceof Vector)
            {
                Vector msgs = (Vector) obj;
                for (int i = 0; i < msgs.size(); i++)
                {
                    String msg = (String) msgs.get(i);
                    str.append(msg);
                    str.append("<br>");
                }
                //release the space
                msgs.clear();
                msgs = null;
                try
                {
                    jspwriter.write(str.toString());
                }
                catch(IOException ex)
                {
                    LOG.error("Error in MessageDisplayTag", ex);
                }

            }
        }
        return EVAL_PAGE;//OK
    }
}
