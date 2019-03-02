package com.aspire.dotcard.syncData.tactic;

/**
 * CMS内容同步策略常量
 * @author x_liyouli
 *
 */
public class TacticConstants
{
	/**
	 * 策略内容类型：支持全部内容类型
	 */
	public static String CONTENT_TYPE_ALL = "all";
	
	/**
	 * 策略业务通道类型：支持全部业务通道
	 */
	public static String UMFLAG_ALL = "0";
	
	/**
	 * 策略tag关系：没有指定
	 */
	public static int TABRELATION_NULL = 0;
	
	/**
	 * 策略tag关系：and
	 */
	public static int TABRELATION_AND = 1;
	
	/**
	 * 策略tag关系：or
	 */
	public static int TABRELATION_OR = 2;
}
