package com.aspire.ponaadmin.common.page.tag;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>��ҳ�е�ǰһҳtag</p>
 * <p>��ҳ�е�ǰһҳtag</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author maobinggui
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class PreviousPageTag
    extends ScrollPageTag {

    private static final long serialVersionUID = 1L;

public PreviousPageTag() {
	  alt = "��һҳ";
  }

  protected int getPageNumber() {
    PagerTag tag = (PagerTag) TagSupport.findAncestorWithClass(this, PagerTag.class);
    return tag.getPageNo() - 1;
  }
}
