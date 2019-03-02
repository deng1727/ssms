package com.aspire.ponaadmin.web.system;

/**
 * �����û��Զ����ҳ��ѯҳ���С�ĳ���
 * @author zhangwei
 *
 */
public class PageSizeConstants
{
	/**
	 * ��ҳ��С�������Сֵ
	 */
	public static String Page_MIN="1";
	/**
	 * ���÷�ҳ��С�������ֵ
	 */
	public static String page_MAX="10";
	/**
	 * ��ҳ��С��Ĭ��ֵ
	 */
    public static String page_DEFAULT="5";
    static
    {
    	Page_MIN=Config.getInstance().getModuleConfig().getItemValue("Page_MIN");
    	page_MAX=Config.getInstance().getModuleConfig().getItemValue("page_MAX");
    	page_DEFAULT=Config.getInstance().getModuleConfig().getItemValue("page_DEFAULT");
    }
}
