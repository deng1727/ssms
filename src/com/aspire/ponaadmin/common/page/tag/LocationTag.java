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
 * <p>定位到某页的TAG</p>
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
     * 日志引用
     */
    protected static JLogger log = LoggerFactory.getLogger(LocationTag.class) ;

    /**
     * 当一个页面有多个页面跳转的地方时，必须输入ID属性，用于区别多个不同的定位按钮，ID值不能相同
     * 当页面上只有一个跳转时，可不用输入，用例:<pager:location id="2"/>
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
        //记录总数，必有参数
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
        //页序，必有参数
        int newPageNumber = getPageNumber() ;
        buf.append(PageConstants.PAGE_INDEX_NAME) ;
        buf.append("=") ;
        buf.append(newPageNumber) ;
        */
        //查询参数，可选
        String query = null ;
        Object para = tag.getParams() ;
        if (para instanceof HashMap) //如果传进来的参数是HashMap的处理
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
        else if (para instanceof String) //如果传进来的参数是String的处理
        {
            query = "&" + (String) para ;
        }
        if (query != null)
        {
            buf.append(query) ;
        }

        String form = tag.getForm();
        StringBuffer script = new StringBuffer() ;

        //生成js，只能输入数字，并且范围有限制。
        script.append("onclick=\"javascript:{var r=/^[0-9]*$/g;if(document.all('pageNum") ;
        script.append(did + "').value<=0 || document.all('pageNum") ;
        script.append(did + "').value>") ;
        script.append(tag.getTotalPages()) ;
        script.append(" || !r.test(document.all('pageNum" + did +"').value)");
        script.append("){alert('请输入大于0小于等于"+tag.getTotalPages());
        script.append("的数值'); return false;};");
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
            lbuf.append("到第 <input type=\"text\" name=\"pageNum" + did +
                "\" size=\"2\" maxlength=\"5\" ") ;
            lbuf.append("value=\"") ;
            lbuf.append(tag.getPageNo()) ;
            lbuf.append("\"> 页 ") ;
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
