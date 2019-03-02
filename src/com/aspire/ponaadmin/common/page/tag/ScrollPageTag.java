package com.aspire.ponaadmin.common.page.tag ;

import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.ponaadmin.common.page.PageConstants ;

/**
 * ��ҳ����Tag��������ҳTag�̳�
 * @author   achang
 * @version  1.0  2003��5��7��
 */

public abstract class ScrollPageTag
    extends TagSupport
{

    /**
     * ��־����
     */
	protected static JLogger log = LoggerFactory.getLogger(ScrollPageTag.class) ;

    private String label ;

    private String src ;

    private String action ;

    private String href ;

    protected String alt ;

    /**
     * Process the start tag for this instance.
     *
     * When this method is invoked, the body has not yet been evaluated.
     *
     * @return  EVAL_BODY_BUFFERED if the tag alters the body content,
     *          EVAL_BODY_AGAIN if the tag iterates over the body content without changing it,
     *          EVAL_BODY_INCLUDE if the tag just evaluates and includes the body content,
     *          SKIP_BODY if it does not want to process it
     * @throws  JspException
     */

    public int doStartTag ()
        throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut() ;
            out.print(getLocation()) ;
            return SKIP_BODY ;
        }
        catch (IOException ex)
        {
            log.error(ex) ;
            throw new JspException(ex.getMessage()) ;
        }
    }

    public void setLabel (String label)
    {
        this.label = label ;
    }

    public void setSrc (String src)
    {
        this.src = src ;
    }

    public void setAlt (String alt)
    {
        this.alt = alt ;
    }

    public String getLocation ()
    {
        PagerTag tag = (PagerTag) TagSupport.findAncestorWithClass(this, PagerTag.class) ;
        action = tag.getAction() ;
        HttpServletRequest request ;
        request = (HttpServletRequest) pageContext.getRequest() ;
        StringBuffer buf = new StringBuffer() ;
        if (action != null)
        {
            buf.append(request.getContextPath() + action) ;
        }
        else
        {
            buf.append(request.getRequestURL()) ;
        }
        if (buf.indexOf("?") > 0)
        {
            buf.append("&") ;
        }
        else
        {
            buf.append("?") ;
            //���������в���
        }
        buf.append(PageConstants.PAGE_RECCOUNT_NAME) ;
        buf.append("=") ;
        buf.append(tag.getTotalRows()) ;
        buf.append("&") ;
        //ҳ�򣬱��в���
        int newPageNumber = getPageNumber() ;
        buf.append(PageConstants.PAGE_INDEX_NAME) ;
        buf.append("=") ;
        buf.append(newPageNumber) ;
        //��ѯ��������ѡ
        String query = null ;
        Object para = tag.getParams() ;
        if (para instanceof HashMap) //����������Ĳ�����HashMap�Ĵ���
        {
            HashMap paramMap = (HashMap) tag.getParams() ;
            if (paramMap != null)
            {
                Iterator iter = paramMap.keySet().iterator() ;
                StringBuffer paramSB = new StringBuffer() ;
                while (iter.hasNext())
                {
                    String paramKey = (String) iter.next() ;
                    String paramVal = (String) paramMap.get(paramKey) ;
                    if (paramVal != null && !"null".equalsIgnoreCase(paramVal))
                    {
                        paramSB.append("&") ;
                        paramSB.append(paramKey + "=" + paramVal) ;
                    }
                }
                query = paramSB.toString() ;
            }
        }
        else if (para instanceof String) //����������Ĳ�����String�Ĵ���
        {
            query = "&" + (String) para ;
        }
        if (query != null)
        {
            buf.append(query) ;
        }

        href = buf.toString() ;

        String form = tag.getForm() ;

        buf = new StringBuffer() ;
        if ((newPageNumber < 1) || (newPageNumber > tag.getTotalPages()) ||
            (newPageNumber == tag.getPageNo()))
        {
            if (label != null)
            {
                buf.append(label) ;
            }
            else
            {
                if (alt != null)
                {
                    buf.append("<img border=\"0\" src=\"") ;
                    buf.append(src) ;
                    buf.append("\" alt=\"") ;
                    buf.append(alt) ;
                    buf.append("\">") ;
                }
                else
                {
                    buf.append("<img border=\"0\" src=\"") ;
                    buf.append(src) ;
                    buf.append("\">") ;
                }
            }
        }
        else
        {
            buf.append("<a href=\"") ;
            if (form != null) //�����form����form.submit����ʽ�ύ����
            {
                buf.append("javascript:");
                buf.append(form);
                buf.append(".action='");
                buf.append(href);
                buf.append("';");
                buf.append(form);
                buf.append(".submit();");
            }
            else
            {
                buf.append(href) ;

            }
            buf.append("\">") ;
            if (label != null)
            {
                buf.append(label) ;
            }
            else
            {
                if (alt != null)
                {
                    buf.append("<img border=\"0\" src=\"") ;
                    buf.append(src) ;
                    buf.append("\" alt=\"") ;
                    buf.append(alt) ;
                    buf.append("\">") ;
                }
                else
                {
                    buf.append("<img border=\"0\" src=\"") ;
                    buf.append(src) ;
                    buf.append("\">") ;
                }
            }
            buf.append("</a>") ;
        }
        return buf.toString() ;

    }

    /**
     * ��ȡҳ��
     * @return ҳ��
     */
    protected abstract int getPageNumber () ;

    /**
     * �ͷ���Դ
     */
    public void release ()
    {
        label = null ;
        src = null ;
        action = null ;
        alt = null ;
    }
}
