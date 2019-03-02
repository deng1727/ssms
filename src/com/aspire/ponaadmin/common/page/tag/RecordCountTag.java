package com.aspire.ponaadmin.common.page.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.log.proxy.JLogger;

/**
 * <p>Title: coWorker工具类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RecordCountTag
    extends TagSupport {

    private static final long serialVersionUID = 1L;
/**
   * 日志引用
   */
  protected static JLogger log = LoggerFactory.getLogger(RecordCountTag.class);

  public int doStartTag() throws JspException {
    PagerTag tag = (PagerTag) TagSupport.findAncestorWithClass(this, PagerTag.class);
    try {
      pageContext.getOut().println(tag.getTotalRows());
      return SKIP_BODY;
    }
    catch (IOException ex) {
      log.error(ex);
      throw new JspException(ex.getMessage());
    }
  }

}
