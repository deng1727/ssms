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
 * <p>XP_DB00011612  ������ҳ��ǩ</p>
 * <p>Description:��ҳ�е��Ϸ�ҳ WAP PORTAL3.0.3  2006.10.22 </p>
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
   * ��־����
   */
  protected static JLogger log = LoggerFactory.getLogger(PreviousStepPageTag.class) ;

  /**
   * ����
   */
  private String label ;

  /**
   * ͼƬԴ·��
   */
  private String src ;

  /**
   * ��ҳ��ǩ��action·��
   */
  private String action ;

  /**
   * �����ַ���
   */
  private String href ;

  /**
   * ͼƬ��ʾ��Ϣ
   */
  private String alt ;

  /**
   * �Ϸ�ҳ�Ĳ���ֵ,Ĭ��Ϊ10
   */
  private String step="10";

  /**
   * ��������ֵ
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
     *���캯��
     */
    public PreviousStepPageTag() {
        alt = "�Ϸ�ҳ";
    }

    /**
     * ��ȡ�Ϸ�ҳ��ҳ��
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
     * ��ʼ����ǩ
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
    * ��ǩ���ɷ����������жϱ�ǩ����ʾ���ݼ�����
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
            //���������в���
        }
        buf.append(PageConstants.PAGE_RECCOUNT_NAME);
        buf.append("=");
        buf.append(tag.getTotalRows());
        buf.append("&");
        //ҳ�򣬱��в���
        int newPageNumber = getPageNumber();
        buf.append(PageConstants.PAGE_INDEX_NAME);
        buf.append("=");
        buf.append(newPageNumber);
        //��ѯ��������ѡ
        String query = null;
        Object para = tag.getParams();
        if (para instanceof HashMap) { //����������Ĳ�����HashMap�Ĵ���
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
        } else if (para instanceof String) { //����������Ĳ�����String�Ĵ���aa
            query = "&" + (String) para;
        }
        if (query != null) {
            buf.append(query);
        }

        href = buf.toString();

        String form = tag.getForm();

        buf = new StringBuffer();
        //��ǰҳ����step����
        int currentScope=(tag.getPageNo()-1)/stepInteger;
        //��ҳ����step����
        int firstScope=(1-1)/stepInteger;
        //��ǰҳ��������С�ڻ������ҳ�������䣬���Ϸ�ҳ��ǩ���ı���ʽ��ʾ������������
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
        //��ǰҳ�������������ҳ�������䣬���Ϸ�ҳ��ǩ��������ʽ��ʾ����ָ����һ������ĵ�һҳ
        } else {
            buf.append("<a href=\"");
            if (form != null) { //�����form����form.submit����ʽ�ύ����
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
