package com.aspire.ponaadmin.common.page.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 显示页数
 *
 * @author   JSP Tag Wizard
 * @version  1.0  2003年5月7日
 */

public class PageNumberTag
    extends TagSupport {

    private static final long serialVersionUID = 1L;

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

  public int doStartTag() throws JspException {
    PagerTag tag = (PagerTag) TagSupport.findAncestorWithClass(this, PagerTag.class);
    try {
      pageContext.getOut().println(tag.getPageNo());
      return SKIP_BODY;
    }
    catch (IOException ex) {
      ex.printStackTrace();
      throw new JspException(ex.getMessage());
    }
  }
}
