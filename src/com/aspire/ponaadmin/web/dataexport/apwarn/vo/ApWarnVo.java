package com.aspire.ponaadmin.web.dataexport.apwarn.vo;

public class ApWarnVo {

	/**
	 * 内容ID
	 */
	private String contentId;
	
	/**
	 * 内容名称	
	 */
	private String name;
	
	/**
	 * 预警类型  刷榜类型 1:下载量波动大   
	 * 2下载时间密集且单位下载时间过短  
	 *  3:连号消费现象   
	 *  4:下载用户重叠率  
	 *  5:地市消费增幅巨大  
	 *  6:消费时间固定
	 */
	private int warnType;
	
	/**
	 * 当天下载次数
	 */
	private int dayDLTimes;
	
	/**
	 * 昨天下载次数
	 */
	private int yesterdayDLTimes;
	
	/**
	 * 7天下载次数
	 */
	private int dl7DaysCounts;
	
	/**
	 * 付费类型
	 */
	private String payType;
	
	/**
	 * 预警描述
	 */
	private String warnDesc;
	
	/**
	 * 内容价格
	 */
	private int price;
	
	/**
	 * 内容类型 'nt:gcontent:appGame','1','nt:gcontent:appSoftWare','0','nt:gcontent:appTheme','2'
	 */
	private String type;
	
	private String appCateName;
	
	private String appCateId;
	
	private String warnDate;
	
	private String spName;
	
	private String marketDate;

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getMarketDate() {
		return marketDate;
	}

	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}

	public String getWarnDate() {
		return warnDate;
	}

	public void setWarnDate(String warnDate) {
		this.warnDate = warnDate;
	}

	public String getAppCateName() {
		return appCateName;
	}

	public void setAppCateName(String appCateName) {
		this.appCateName = appCateName;
	}

	public String getAppCateId() {
		return appCateId;
	}

	public void setAppCateId(String appCateId) {
		this.appCateId = appCateId;
	}

	public int getWarnType() {
		return warnType;
	}

	public void setWarnType(int warnType) {
		this.warnType = warnType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getDayDLTimes() {
		return dayDLTimes;
	}

	public void setDayDLTimes(int dayDLTimes) {
		this.dayDLTimes = dayDLTimes;
	}

	public int getYesterdayDLTimes() {
		return yesterdayDLTimes;
	}

	public void setYesterdayDLTimes(int yesterdayDLTimes) {
		this.yesterdayDLTimes = yesterdayDLTimes;
	}

	public int getDl7DaysCounts() {
		return dl7DaysCounts;
	}

	public void setDl7DaysCounts(int dl7DaysCounts) {
		this.dl7DaysCounts = dl7DaysCounts;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getWarnDesc() {
		return warnDesc;
	}

	public void setWarnDesc(String warnDesc) {
		this.warnDesc = warnDesc;
	}
	
}
