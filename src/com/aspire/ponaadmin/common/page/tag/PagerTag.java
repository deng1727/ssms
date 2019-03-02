package com.aspire.ponaadmin.common.page.tag ;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * 分页TAG
 * @author   achang<achang_hu@21cn.com>
 * @version  1.0  2003年5月7日
 */
public class PagerTag
    extends BodyTagSupport
{

    private static final long serialVersionUID = 1L;

    /**
     * 日志引用
     */
    protected static JLogger log = LoggerFactory.getLogger(PagerTag.class) ;

    /**
     * 表示当前第几页 
     */
    private int pageNo ;

    /**
     * 表示总页数 
     */
    private int totalPages ;

    /**
     * 总记录数 
     */
    private int totalRows;

    /**
     * 分页的URL
     */
    private String action;

    /**
     * 分页对象实例为PageResult对象
     */
    private String name;

    /**
     * 参数列表
     */
    private Object params;

    private String form;

    public void setTotalRows (int totalRows)
    {
        this.totalRows = totalRows;
    }

    public int getTotalRows ()
    {
        return totalRows;
    }

    public int getPageNo ()
    {
        return pageNo;
    }

    public int getTotalPages ()
    {
        return totalPages;
    }

    /**
     * 判断是否有分页，以及设置分页的相关实例变量。
     *
     * @return  EVAL_BODY_TAG if the tag iterates over or alters the body content,
     *          EVAL_BODY_INCLUDE if the tag just evaluates and includes the body content,
     *          SKIP_BODY if it does not want to process it
     * @throws  JspException
     */

    public int doStartTag ()
        throws JspException
    {
        log.debug("enter into  doStartTag.................") ;
        Object value = TagUtils.getInstance().lookup(pageContext, name, null, null);
        if(value instanceof PageResult)
        {
           value = TagUtils.getInstance().lookup(pageContext, name, "totalPages", null);
           if (value == null)
               return SKIP_BODY;  // Nothing to output
           totalPages = new Integer(String.valueOf(value)).intValue() ;
           value = TagUtils.getInstance().lookup(pageContext, name, "totalRows", null);
           if (value == null)
               return SKIP_BODY;  // Nothing to output
           totalRows = new Integer(String.valueOf(value)).intValue() ;

           value = TagUtils.getInstance().lookup(pageContext, name, "currentPageNo", null);
           if (value == null)
               return SKIP_BODY;  // Nothing to output
           pageNo = new Integer(String.valueOf(value)).intValue();
       }
       else
       {
           throw new JspException("给定的对象不是一个PageResult");
       }
/*
            String temp = (String)pageContext.findAttribute(Constants.PAGE_PAGECOUNT_NAME);
            if(null == temp || temp.trim().length()==0)
                this.totalPages = -1;
            else
                this.totalPages = Integer.parseInt(temp);

            temp= pageContext.getRequest().getParameter(Constants.PAGE_INDEX_NAME) ;
            if (null != temp && !temp.trim().equals(""))
                this.pageNo = Integer.parseInt(temp) ;
            else
                this.pageNo = 1 ;
*/
        //进行页数容错处理，如果当前页数小于1，默认为第一页，如果当前页数大于总的页数，默认为
        //最大页。
        if (this.pageNo < 1)
            this.pageNo = 1 ;
        else if (this.pageNo > this.totalPages)
            this.pageNo = this.totalPages ;
        return EVAL_BODY_INCLUDE ;
    }

    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Object getParams()
    {
        return params;
    }

    public void setParams(Object params)
    {
        this.params = params;
    }

    public String getForm()
    {
        return form;
    }

    public void setForm(String form)
    {
        this.form = form;
    }

}
