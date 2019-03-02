package com.aspire.ponaadmin.common.page.tag;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import com.aspire.common.log.proxy.JLogger;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import javax.servlet.jsp.JspWriter;
import java.util.Iterator;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageConstants;

/**
 * <p>Title:PreviousStepPageTag </p>
 * <p>XP_DB00011612  新增分页标签</p>
 * <p>Description:分页中的上翻页 WAP PORTAL3.0.3  2006.10.22 </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: Aspire</p>
 *
 * @author huiqiang
 * @version 1.0
 */
public class PreviousStepPageTag extends TagSupport{

    private static final long serialVersionUID = 1L;

/**
   * 日志引用
   */
  protected static JLogger log = LoggerFactory.getLogger(PreviousStepPageTag.class) ;

  /**
   * 标题
   */
  private String label ;

  /**
   * 图片源路径
   */
  private String src ;

  /**
   * 分页标签的action路径
   */
  private String action ;

  /**
   * 链接字符串
   */
  private String href ;

  /**
   * 图片提示信息
   */
  private String alt ;

  /**
   * 上翻页的步长值,默认为10
   */
  private String step="10";

  /**
   * 步长整型值
   */
  private int stepInteger;

  /**
   * setLabel
   * @param label
   */
    public void setLabel (String label)
    {
        this.label = label ;
    }

    /**
     * setSrc
     * @param src
     */
    public void setSrc (String src)
    {
        this.src = src ;
    }

    /**
     * setAlt
     * @param alt
     */
    public void setAlt (String alt)
    {
        this.alt = alt ;
    }

    /**
     * setStep
     * @param step
     */
    public void setStep(String step){
        this.step=step;
    }

    /**
     *构造函数
     */
    public PreviousStepPageTag() {
        alt = "上翻页";
    }

    /**
     * 获取上翻页的页数
     * @return int
     */
    protected int getPageNumber() {
        PagerTag tag = (PagerTag) TagSupport.findAncestorWithClass(this, PagerTag.class);
        try{
            stepInteger = Integer.parseInt(step);
        }catch(Exception ex){
            log.error("step transform exception:"+ex+"\n"+ex.getMessage());
            stepInteger=10;
        }
        int temp=(tag.getPageNo()-stepInteger-1)/stepInteger;
        return temp*stepInteger+1;
    }

    /**
     * 初始化标签
     * @return int
     * @throws JspException
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

   /**
    * 标签生成方法，用来判断标签的显示内容及链接
    * @return String
    */
    public String getLocation() {
        PagerTag tag = (PagerTag) TagSupport.findAncestorWithClass(this,
                PagerTag.class);
        action = tag.getAction();
        HttpServletRequest request;
        request = (HttpServletRequest) pageContext.getRequest();
        StringBuffer buf = new StringBuffer();
        if (action != null) {
            buf.append(request.getContextPath() + action);
        } else {
            buf.append(request.getRequestURL());
        }
        if (buf.indexOf("?") > 0) {
            buf.append("&");
        } else {
            buf.append("?");
            //总数，必有参数
        }
        buf.append(PageConstants.PAGE_RECCOUNT_NAME);
        buf.append("=");
        buf.append(tag.getTotalRows());
        buf.append("&");
        //页序，必有参数
        int newPageNumber = getPageNumber();
        buf.append(PageConstants.PAGE_INDEX_NAME);
        buf.append("=");
        buf.append(newPageNumber);
        //查询参数，可选
        String query = null;
        Object para = tag.getParams();
        if (para instanceof HashMap) { //如果传进来的参数是HashMap的处理
            HashMap paramMap = (HashMap) tag.getParams();
            if (paramMap != null) {
                Iterator iter = paramMap.keySet().iterator();
                StringBuffer paramSB = new StringBuffer();
                while (iter.hasNext()) {
                    String paramKey = (String) iter.next();
                    String paramVal = (String) paramMap.get(paramKey);
                    if (paramVal != null && !"null".equalsIgnoreCase(paramVal)) {
                        paramSB.append("&");
                        paramSB.append(paramKey + "=" + paramVal);
                    }
                }
                query = paramSB.toString();
            }
        } else if (para instanceof String) { //如果传进来的参数是String的处理aa
            query = "&" + (String) para;
        }
        if (query != null) {
            buf.append(query);
        }

        href = buf.toString();

        String form = tag.getForm();

        buf = new StringBuffer();
        //当前页所在step区间
        int currentScope=(tag.getPageNo()-1)/stepInteger;
        //首页所在step区间
        int firstScope=(1-1)/stepInteger;
        //当前页所在区间小于或等于首页所在区间，则上翻页标签以文本形式显示，无链接内容
        if (currentScope<=firstScope)
        {
            if (label != null) {
                buf.append(label);
            } else {
                if (alt != null) {
                    buf.append("<img border=\"0\" src=\"");
                    buf.append(src);
                    buf.append("\" alt=\"");
                    buf.append(alt);
                    buf.append("\">");
                } else {
                    buf.append("<img border=\"0\" src=\"");
                    buf.append(src);
                    buf.append("\">");
                }
            }
        //当前页所在区间大于首页所在区间，则上翻页标签以链接形式显示，并指向上一个区间的第一页
        } else {
            buf.append("<a href=\"");
            if (form != null) { //如果有form则以form.submit的形式提交请求
                buf.append("javascript:");
                buf.append(form);
                buf.append(".action='");
                buf.append(href);
                buf.append("';");
                buf.append(form);
                buf.append(".submit();");
            } else {
                buf.append(href);

            }
            buf.append("\">");
            if (label != null) {
                buf.append(label);
            } else {
                if (alt != null) {
                    buf.append("<img border=\"0\" src=\"");
                    buf.append(src);
                    buf.append("\" alt=\"");
                    buf.append(alt);
                    buf.append("\">");
                } else {
                    buf.append("<img border=\"0\" src=\"");
                    buf.append(src);
                    buf.append("\">");
                }
            }
            buf.append("</a>");
        }
        return buf.toString();

    }

}
