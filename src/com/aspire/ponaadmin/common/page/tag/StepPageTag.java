package com.aspire.ponaadmin.common.page.tag;

import javax.servlet.jsp.JspException;
import com.aspire.common.log.proxy.JLogger;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import javax.servlet.jsp.JspWriter;
import java.util.Iterator;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageConstants;

/**
 * <p>Title: StepPageTag</p>
 * <p>XP_DB00011612  新增分页标签</p>
 * <p>Description:分页中的按步长值分页标签 WAP PORTAL3.0.3  2006.10.22 </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: Aspire</p>
 *
 * @author huiqiang
 * @version 1.0
 */
public class StepPageTag extends TagSupport {

    private static final long serialVersionUID = 1L;

/**
   * 日志引用
   */
  protected static JLogger log = LoggerFactory.getLogger(StepPageTag.class);

  /**
   * 分页标签的action路径
   */
  private String action;

  /**
   * 链接字符串
   */
  private String href;

  /**
   * 详细链接字符串
   */
  private String detailsHref="";

  /**
   * 常量，代表标签中页的显示数值
   */
  private final String DISPLAYPAGENUMBER="DISPLAYPAGENUMBER";

  /**
   *步长值,默认为10
   */
  private String step = "10";

  /**
   * 步长整型值
   */
  private int stepInteger;

  /**
   * setStep
   * @param step
   */
    public void setStep(String step) {
        this.step = step;
    }

    /**
     * 构造函数
     */
    public StepPageTag() {
    }

    /**
     * 初始化标签
     * @return int
     * @throws JspException
     */
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print(getLocation());
            return SKIP_BODY;
        } catch (IOException ex) {
            log.error(ex);
            throw new JspException(ex.getMessage());
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

        buf.append(PageConstants.PAGE_INDEX_NAME);
        buf.append("=");
        buf.append(DISPLAYPAGENUMBER);
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
        } else if (para instanceof String) { //如果传进来的参数是String的处理
            query = "&" + (String) para;
        }
        if (query != null) {
            buf.append(query);
        }

        href = buf.toString();

        try{
            stepInteger = Integer.parseInt(step);
        }catch(Exception ex){
            log.error("step transform exception:"+ex+"\n"+ex.getMessage());
            stepInteger=10;
        }

        //当前页所在的区间
        int temp=(tag.getPageNo()-1)/stepInteger;
        //起始页数
        int startPage=temp*stepInteger+1;
        //结束页数
        int endPage=temp*stepInteger+stepInteger;

        //过滤异常数据
        if ( startPage<1 || startPage>tag.getTotalPages() ){
            startPage=1;
            if ( tag.getTotalPages()>stepInteger ){
                endPage=stepInteger;
            }else{
                endPage=tag.getTotalPages();
            }
        }

        //如果标签不足stepInteger页时显示剩余页面
        if ( endPage>=tag.getTotalPages() ){
            endPage=tag.getTotalPages();
        }

        String form = tag.getForm();

        buf = new StringBuffer();

        //从startPage页到endPage页组装标签显示内容
        for ( ;startPage<=endPage;startPage++ ){
            //替换href中的当前页显示为具体页数
            detailsHref=href.replaceFirst(DISPLAYPAGENUMBER,String.valueOf(startPage));
            //如果不是当前页以超链接的形式显示
            if ( startPage!=tag.getPageNo() ){
                buf.append("<a href=\"");
                if (form != null) { //如果有form则以form.submit的形式提交请求
                    buf.append("javascript:");
                    buf.append(form);
                    buf.append(".action='");
                    buf.append(detailsHref);
                    buf.append("';");
                    buf.append(form);
                    buf.append(".submit();");
                } else {
                    buf.append(detailsHref);
                }
                buf.append("\">"+String.valueOf(startPage)+"</a>");
            //是当前页用红色的粗体文本内容显示
            }else{
                buf.append("<font color='red'><b>"+String.valueOf(startPage)+"</font></b>") ;
            }
            buf.append("&nbsp;");
        }
        return buf.toString() ;
    }

}
