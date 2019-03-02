package com.aspire.ponaadmin.web.catesyn;

import java.util.Calendar;
import java.util.Date;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.DateUtil;

public class Config {
//	public static final String WAP_APP_ID;
//
//	public static final String WAP_GAME_ID;
//
//	public static final String WAP_THEME_ID;
//
//	public static final String WWW_GAME_ID;
//
//	public static final String WWW_APP_ID;
//
//	public static final String WWW_THEME_ID;
//
//	public static final String TERM_APP_ID;
//
//	public static final String TERM_GAME_ID;
//
//	public static final String TERM_THEME_ID;

	public static final String START_TIME;

	public static final String FREQUENCY;

	public static final String[] MAIL_TO;
	static {
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"AppcateSyn");
//		WAP_APP_ID = module.getItemValue("wapAppCategoryid");
//		WAP_GAME_ID = module.getItemValue("wapGameCategoryid");
//		WAP_THEME_ID = module.getItemValue("wapThemeCategoryid");
//
//		WWW_APP_ID = module.getItemValue("wwwAppCategoryid");
//		WWW_GAME_ID = module.getItemValue("wwwGameCategoryid");
//		WWW_THEME_ID = module.getItemValue("wwwThemeCategoryid");
//
//		TERM_APP_ID = module.getItemValue("termAppCategoryid");
//		TERM_GAME_ID = module.getItemValue("termGameCategoryid");
//		TERM_THEME_ID = module.getItemValue("termThemeCategoryid");

		START_TIME = module.getItemValue("startTime");
		FREQUENCY = module.getItemValue("frequency");
		String temp = module.getItemValue("mailTo");
		if (temp != null) {
			MAIL_TO = temp.split(";");
		} else {
			MAIL_TO = null;
		}

	}
	protected static JLogger logger = LoggerFactory.getLogger(Config.class);

	
	public static Date getStartTime(){
//		 系统默认的基地音乐同步执行时间 05:00
		int hour = 5;
		int minute = 0;
		// 从配置项中获取配置值
		try {
			String rbStartTime = START_TIME;
			hour = Integer.parseInt(rbStartTime.split(":")[0]);
			minute = Integer.parseInt(rbStartTime.split(":")[1]);
		} catch (Exception ex) {
			logger.error("从配置项中获取基地图书同步的时间AppTopStarttime解析出错！");
		}
		if (hour < 0 || hour > 23) {
			// 配置值不正确，就使用默认值
			hour = 5;
		}
		if (minute < 0 || minute > 59) {
			// 配置值不正确，就使用默认值
			minute = 0;
		}
		Date firstStartTime = null;
		try {
			// 取当前时间
			Date date = new Date();
			// 构造第一次任务运行时间
			firstStartTime = getOneTimeByHourAndMinute(date, hour, minute);
			// 如果第一次运行时间早于当前时间，需要把第一次运行时间加一天
			if (firstStartTime.before(date)) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date tommorrow = calendar.getTime();
				firstStartTime = getOneTimeByHourAndMinute(tommorrow, hour,
						minute);
			}
		} catch (Exception ex) {
			// 设置时间有异常，就用当前的时间吧，不过如果程序没有bug，这个异常应该是不可能出现的
			firstStartTime = new Date();
		}
		return firstStartTime;
	}
	
	private static Date getOneTimeByHourAndMinute(Date date, int hour,
			int minute) {
		String c = DateUtil.formatDate(date, "yyyyMMdd");
		if (hour < 10) {
			c = c + '0';
		}
		c = c + hour + minute + "00";
		return DateUtil.stringToDate(c, "yyyyMMddHHmmss");
	}
	
	public static long getInterval() {
		if ("week".equals(FREQUENCY)) {
			return 7 * 24 * 60 * 60 * 1000l;
		}
		return 24 * 60 * 60 * 1000l;
	}
}
