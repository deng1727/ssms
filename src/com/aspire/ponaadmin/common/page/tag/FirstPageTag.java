package com.aspire.ponaadmin.common.page.tag;

/**
 * <p>��ҳ�е���ҳtag</p>
 * <p>��ҳ�е���ҳtag</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author maobinggui
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class FirstPageTag
    extends ScrollPageTag {

    private static final long serialVersionUID = 1L;

/**
   * ���췽��
   */
  public FirstPageTag() {
	  alt = "��ҳ" ;
  }

  /**
   * ��ȡҳ��
   * @return ҳ�� 
   */
  protected int getPageNumber() {
    return 1;
  }
}
