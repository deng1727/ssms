package com.aspire.ponaadmin.common.page.tag ;

import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.ponaadmin.common.page.PageConstants ;

/**
 *
 * <p>��λ��ĳҳ��TAG</p>
 * <p>Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author maobg
 * @version 1.0.0.0
 *
 */
public class LocationTag
    extends TagSupport
{

    private static final long serialVersionUID = 1L;

    /**
     * ��־����
     */
    protected static JLogger log = LoggerFactory.getLogger(LocationTag.class) ;

    /**
     * ��һ��ҳ���ж��ҳ����ת�ĵط�ʱ����������ID���ԣ�������������ͬ�Ķ�λ��ť��IDֵ������ͬ
     * ��ҳ����ֻ��һ����תʱ���ɲ������룬����:<pager:location id="2"/>
     */
    private String did = "0" ;

    public void setId (String id)
    {
        this.did = id ;
    }

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
        PagerTag tag = (PagerTag) TagSupport.findAncestorWithClass(this, PagerTag.class) ;
        String action = tag.getAction() ;
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
        //��¼���������в���
        if (buf.indexOf("?") > 0)
        {
            buf.append("&") ;
        }
        else
        {
            buf.append("?") ;

        }
        buf.append(PageConstants.PAGE_RECCOUNT_NAME) ;
        buf.append("=") ;
        buf.append(tag.getTotalRows()) ;

        /*
        buf.append("&") ;
        //ҳ�򣬱��в���
        int newPageNumber = getPageNumber() ;
        buf.append(PageConstants.PAGE_INDEX_NAME) ;
        buf.append("=") ;
        buf.append(newPageNumber) ;
        */
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

        String form = tag.getForm();
        StringBuffer script = new StringBuffer() ;

        //����js��ֻ���������֣����ҷ�Χ�����ơ�
        script.append("onclick=\"javascript:{var r=/^[0-9]*$/g;if(document.all('pageNum") ;
        script.append(did + "').value<=0 || document.all('pageNum") ;
        script.append(did + "').value>") ;
        script.append(tag.getTotalPages()) ;
        script.append(" || !r.test(document.all('pageNum" + did +"').value)");
        script.append("){alert('���������0С�ڵ���"+tag.getTotalPages());
        script.append("����ֵ'); return false;};");
        if(form != null)
            script.append(form+".action='");
        else
            script.append("window.location.href='");
        script.append(buf.toString());
        script.append("&");
        script.append(PageConstants.PAGE_INDEX_NAME);
        script.append("='+");
        script.append("document.all('pageNum" + did +"').value;");
        if(form != null)
            script.append(form+".submit();");
        script.append("}\"");
        try
        {
            StringBuffer lbuf = new StringBuffer() ;
            lbuf.append("���� <input type=\"text\" name=\"pageNum" + did +
                "\" size=\"2\" maxlength=\"5\" ") ;
            lbuf.append("value=\"") ;
            lbuf.append(tag.getPageNo()) ;
            lbuf.append("\"> ҳ ") ;
            lbuf.append("<input type=\"button\" value=\"go\" " + script + ">") ;
            pageContext.getOut().println(lbuf.toString()) ;
            return SKIP_BODY ;
        }
        catch (IOException ex)
        {
            log.error(ex) ;
            throw new JspException(ex.getMessage()) ;
        }
    }
}
