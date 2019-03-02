package com.aspire.ponaadmin.web.system;

/**
 * 定义用户自定义分页查询页面大小的常量
 * @author zhangwei
 *
 */
public class PageSizeConstants
{
	/**
	 * 分页大小允许的最小值
	 */
	public static String Page_MIN="1";
	/**
	 * 设置分页大小允许最大值
	 */
	public static String page_MAX="10";
	/**
	 * 分页大小的默认值
	 */
    public static String page_DEFAULT="5";
    static
    {
    	Page_MIN=Config.getInstance().getModuleConfig().getItemValue("Page_MIN");
    	page_MAX=Config.getInstance().getModuleConfig().getItemValue("page_MAX");
    	page_DEFAULT=Config.getInstance().getModuleConfig().getItemValue("page_DEFAULT");
    }
}
