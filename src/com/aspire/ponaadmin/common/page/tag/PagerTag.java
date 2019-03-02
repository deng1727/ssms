package com.aspire.ponaadmin.common.page.tag ;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * ��ҳTAG
 * @author   achang<achang_hu@21cn.com>
 * @version  1.0  2003��5��7��
 */
public class PagerTag
    extends BodyTagSupport
{

    private static final long serialVersionUID = 1L;

    /**
     * ��־����
     */
    protected static JLogger log = LoggerFactory.getLogger(PagerTag.class) ;

    /**
     * ��ʾ��ǰ�ڼ�ҳ 
     */
    private int pageNo ;

    /**
     * ��ʾ��ҳ�� 
     */
    private int totalPages ;

    /**
     * �ܼ�¼�� 
     */
    private int totalRows;

    /**
     * ��ҳ��URL
     */
    private String action;

    /**
     * ��ҳ����ʵ��ΪPageResult����
     */
    private String name;

    /**
     * �����б�
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
     * �ж��Ƿ��з�ҳ���Լ����÷�ҳ�����ʵ��������
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
           throw new JspException("�����Ķ�����һ��PageResult");
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
        //����ҳ���ݴ��������ǰҳ��С��1��Ĭ��Ϊ��һҳ�������ǰҳ�������ܵ�ҳ����Ĭ��Ϊ
        //���ҳ��
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
