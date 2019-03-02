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
 * <p>XP_DB00011612  ������ҳ��ǩ</p>
 * <p>Description:��ҳ�еİ�����ֵ��ҳ��ǩ WAP PORTAL3.0.3  2006.10.22 </p>
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
   * ��־����
   */
  protected static JLogger log = LoggerFactory.getLogger(StepPageTag.class);

  /**
   * ��ҳ��ǩ��action·��
   */
  private String action;

  /**
   * �����ַ���
   */
  private String href;

  /**
   * ��ϸ�����ַ���
   */
  private String detailsHref="";

  /**
   * �����������ǩ��ҳ����ʾ��ֵ
   */
  private final String DISPLAYPAGENUMBER="DISPLAYPAGENUMBER";

  /**
   *����ֵ,Ĭ��Ϊ10
   */
  private String step = "10";

  /**
   * ��������ֵ
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
     * ���캯��
     */
    public StepPageTag() {
    }

    /**
     * ��ʼ����ǩ
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

        buf.append(PageConstants.PAGE_INDEX_NAME);
        buf.append("=");
        buf.append(DISPLAYPAGENUMBER);
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
        } else if (para instanceof String) { //����������Ĳ�����String�Ĵ���
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

        //��ǰҳ���ڵ�����
        int temp=(tag.getPageNo()-1)/stepInteger;
        //��ʼҳ��
        int startPage=temp*stepInteger+1;
        //����ҳ��
        int endPage=temp*stepInteger+stepInteger;

        //�����쳣����
        if ( startPage<1 || startPage>tag.getTotalPages() ){
            startPage=1;
            if ( tag.getTotalPages()>stepInteger ){
                endPage=stepInteger;
            }else{
                endPage=tag.getTotalPages();
            }
        }

        //�����ǩ����stepIntegerҳʱ��ʾʣ��ҳ��
        if ( endPage>=tag.getTotalPages() ){
            endPage=tag.getTotalPages();
        }

        String form = tag.getForm();

        buf = new StringBuffer();

        //��startPageҳ��endPageҳ��װ��ǩ��ʾ����
        for ( ;startPage<=endPage;startPage++ ){
            //�滻href�еĵ�ǰҳ��ʾΪ����ҳ��
            detailsHref=href.replaceFirst(DISPLAYPAGENUMBER,String.valueOf(startPage));
            //������ǵ�ǰҳ�Գ����ӵ���ʽ��ʾ
            if ( startPage!=tag.getPageNo() ){
                buf.append("<a href=\"");
                if (form != null) { //�����form����form.submit����ʽ�ύ����
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
            //�ǵ�ǰҳ�ú�ɫ�Ĵ����ı�������ʾ
            }else{
                buf.append("<font color='red'><b>"+String.valueOf(startPage)+"</font></b>") ;
            }
            buf.append("&nbsp;");
        }
        return buf.toString() ;
    }

}
