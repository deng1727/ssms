package com.aspire.ponaadmin.common.page.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * 显示总页数
 *
 * @author   achang
 * @version  1.0  2003年5月7日
 */

public class PageCountTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    /**
	 * 日志引用
	 */
	protected static JLogger log = LoggerFactory.getLogger(PageCountTag.class);

	/**
	 * Process the start tag for this instance.
	 *
	 * When this method is invoked, the body has not yet been evaluated.
	 *
	 * @return  EVAL_BODY_TAG if the tag iterates over or alters the body content,
	 *          EVAL_BODY_INCLUDE if the tag just evaluates and includes the body content,
	 *          SKIP_BODY if it does not want to process it
	 * @throws  JspException
	 */

	public int doStartTag() throws JspException {
		PagerTag tag = (PagerTag) TagSupport.findAncestorWithClass(this,
				PagerTag.class);
		try {
			pageContext.getOut().println(tag.getTotalPages());
			return SKIP_BODY;
		} catch (IOException ex) {
			log.error(ex);
			throw new JspException(ex.getMessage());
		}

	}
}