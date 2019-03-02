package com.aspire.ponaadmin.common.page.tag;

/**
 * <p>分页中的首页tag</p>
 * <p>分页中的首页tag</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author maobinggui
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class FirstPageTag
    extends ScrollPageTag {

    private static final long serialVersionUID = 1L;

/**
   * 构造方法
   */
  public FirstPageTag() {
	  alt = "首页" ;
  }

  /**
   * 获取页码
   * @return 页码 
   */
  protected int getPageNumber() {
    return 1;
  }
}
