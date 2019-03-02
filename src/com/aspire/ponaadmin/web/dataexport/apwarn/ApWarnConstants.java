package com.aspire.ponaadmin.web.dataexport.apwarn;

public class ApWarnConstants {

	/**
	 * 1:下载量波动大   
	 */
	public static final int WARN_DL_WAVE = 1;
	
	/**
	 * 2下载时间密集且单位下载时间过短   
	 */
	public static final int WARN_DL_TIME = 2;
	
	/**
	 * 3:连号消费现象  
	 */
	public static final int WARN_DL_PHONE = 3;
	
	/**
	 * 4:下载用户重叠率
	 */
	public static final int WARN_DL_USER_REPEAT = 4;
	
	/**
	 * 5:地市消费增幅巨大  
	 */
	public static final int WARN_DL_AREA = 5;
	
	/**
	 * 6:消费时间固定';
	 */
	public static final int WARN_DL_TIME_FIT = 6;

	//'nt:gcontent:appGame','1','nt:gcontent:appSoftWare','0','nt:gcontent:appTheme','2'
	public static final String APP_TYPE_GAME = "1";
	
	public static final String APP_TYPE_SOFT = "0";
	
	public static final String APP_TYPE_THEME = "2";
}
